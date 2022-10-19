package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealsDao {
    Meal add(Meal meal);
    void delete(Integer id);
    Meal update(Meal meal);
    Meal read(Integer id);
    List<Meal> getAllList();
}
