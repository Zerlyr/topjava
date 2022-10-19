package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.dao.MealsMap;
import ru.javawebinar.topjava.dao.MealsDao;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private static final int CALORIES_PER_DAY = 2000;
    private MealsDao dao;

    @Override
    public void init() {
        dao = new MealsMap();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String forward = "";
        String action = request.getParameter("action");
        if (action == null) action = "";

        if (action.equalsIgnoreCase("delete")) {
            log.debug("meals delete");
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            dao.delete(mealId);
            forward = "mealsTable.jsp";
            request.setAttribute("meals", dao.getAllList());
        } else if (action.equalsIgnoreCase("edit")) {
            log.debug("meals edit");
            forward = "meal.jsp";
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            MealTo meal = dao.read(mealId);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("insert")) {
            log.debug("meals insert");
            forward = "meal.jsp";
        } else {
            log.debug("meals default");
            forward = "mealsTable.jsp";
            request.setAttribute("meals", dao.getAllList());
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "";

        if (action.equalsIgnoreCase("delete")) {
            log.debug("meals delete");
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            dao.delete(mealId);
            request.setAttribute("meals", dao.getAllList());
            RequestDispatcher view = request.getRequestDispatcher("mealsTable.jsp");
            request.setAttribute("meals", dao.getAllList());
            view.forward(request, response);
            return;
        }

        Meal meal = new Meal(id, LocalDateTime.parse(request.getParameter("date")), request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        String id = request.getParameter("mealId");
        if (id == null || id.isEmpty()) {
            dao.add(meal);
        } else {
            boolean excess = Boolean.parseBoolean(request.getParameter("excess"));
            dao.update(MealsUtil.createTo(Integer.parseInt(id), meal, excess));
        }

        RequestDispatcher view = request.getRequestDispatcher("mealsTable.jsp");
        request.setAttribute("meals", dao.getAllList());
        view.forward(request, response);
    }
}
