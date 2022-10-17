package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.DaoMealsListImp;
import ru.javawebinar.topjava.util.DaoMealsUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private static final List<Meal> MEALS = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );
    private static final int CALORIES_PER_DAY = 2000;
    private final DaoMealsUtil dao;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public MealServlet() {
        super();
        dao = new DaoMealsListImp(MEALS, CALORIES_PER_DAY);
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
            forward = "meals.jsp";
            request.setAttribute("meals", dao.getAllList());
        } else if (action.equalsIgnoreCase("edit")) {
            log.debug("meals edit");
            forward = "add_meal.jsp";
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            MealTo meal = dao.read(mealId);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("insert")) {
            log.debug("meals insert");
            forward = "add_meal.jsp";
        } else {
            log.debug("meals default");
            forward = "meals.jsp";
            request.setAttribute("meals", dao.getAllList());
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("date"), formatter), request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        String id = request.getParameter("mealId");
        if (id == null || id.isEmpty()) {
            dao.add(meal);
        }
        else {
            boolean excess = Boolean.parseBoolean(request.getParameter("excess"));
            dao.update(MealsUtil.createTo(Integer.parseInt(id), meal, excess));
        }

        RequestDispatcher view = request.getRequestDispatcher("meals.jsp");
        request.setAttribute("meals", dao.getAllList());
        view.forward(request, response);
    }
}
