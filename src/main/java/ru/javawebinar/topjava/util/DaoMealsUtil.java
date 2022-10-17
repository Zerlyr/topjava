package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface DaoMealsUtil {
    void add(Meal meal);
    void delete(int id);
    void update(MealTo meal);
    MealTo read(int id);
    List<MealTo> getAllList();
}
