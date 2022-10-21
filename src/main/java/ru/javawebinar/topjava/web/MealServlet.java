package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.dao.MealsDatabase;
import ru.javawebinar.topjava.dao.MealsDao;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private static final int CALORIES_PER_DAY = 2000;
    private MealsDao dao;

    @Override
    public void init() {
        dao = new MealsDatabase();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        if (action == null) action = "";
        String forward;

        if (action.equalsIgnoreCase("add")) {
            log.info("meals edit page");
            request.setAttribute("action", "Add");
            forward = "meal.jsp";
        } else {
            log.info("meals table");
            forward = "mealsTable.jsp";
            request.setAttribute("meals", MealsUtil.filteredByStreams(dao.getAllList(), LocalTime.MIDNIGHT, LocalTime.MAX, CALORIES_PER_DAY));
        }
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action.toLowerCase()) {
            case "delete": {
                Integer mealId = getId(request);
                log.info("meals with id {} delete", mealId );
                dao.delete(mealId);
                response.sendRedirect(request.getContextPath() + "/meals");
                break;
            }
            case "update": {
                Integer mealId = getId(request);
                log.info("meals with id {} update", mealId );
                Meal meal = dao.read(mealId);
                request.setAttribute("meal", meal);
                request.setAttribute("action", "Update");
                RequestDispatcher view = request.getRequestDispatcher("meal.jsp");
                view.forward(request, response);
                break;
            }
            case "add-update": {
                Meal meal = new Meal(LocalDateTime.parse(request.getParameter("date")), request.getParameter("description"),
                        Integer.parseInt(request.getParameter("calories")));
                String mealId = request.getParameter("mealId");
                if (mealId == null || mealId.isEmpty()) {
                    log.info("meals add");
                    dao.add(meal);
                }
                else {
                    log.info("meals with id {} update", mealId );
                    meal.setId(Integer.valueOf(mealId));
                    dao.update(meal);
                }
                response.sendRedirect(request.getContextPath() + "/meals");
                break;
            }
            case "reset": {
                response.sendRedirect(request.getContextPath() + "/meals");
                break;
            }
        }
    }

    private Integer getId(HttpServletRequest request) {
        return Integer.valueOf(request.getParameter("mealId"));
    }
}
