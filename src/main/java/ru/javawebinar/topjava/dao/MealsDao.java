package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealsDao {
    Meal add(Meal meal);

    void delete(int id);

    Meal update(Meal meal);

    Meal read(int id);

    List<Meal> getAllList();
}
