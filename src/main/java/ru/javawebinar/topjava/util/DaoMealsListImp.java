package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.time.LocalTime.MAX;
import static java.time.LocalTime.MIDNIGHT;

public class DaoMealsListImp implements DaoMealsUtil {
    private CopyOnWriteArrayList<MealTo> database;
    private final int MAX_CALORIES;

    private int findIndexById(int id) {
        return IntStream.range(0, database.size())
                .filter(i -> database.get(i).getId().equals(id))
                .findFirst()
                .orElse(-1);
    }

    public DaoMealsListImp(List<Meal> database, int maxCalories) {
        MAX_CALORIES = maxCalories;
        this.database = new CopyOnWriteArrayList<>(MealsUtil.filteredByStreams(database, MIDNIGHT, MAX, maxCalories));
    }

    @Override
    public void add(Meal meal) {
        List<Meal> tmpDatabase = database.stream()
                .map(m -> new Meal(m.getDateTime(), m.getDescription(), m.getCalories()))
                .collect(Collectors.toList());
        tmpDatabase.add(meal);
        this.database = new CopyOnWriteArrayList<>(MealsUtil.filteredByStreams(tmpDatabase, MIDNIGHT, MAX, MAX_CALORIES));
    }

    @Override
    public void delete(int id) {
        List<MealTo> tmpDatabase = new ArrayList<>(database);
        tmpDatabase.removeIf(m -> m.getId().equals(id));
        this.database = new CopyOnWriteArrayList<>(MealsUtil.filteredTo(tmpDatabase, MIDNIGHT, MAX, MAX_CALORIES));
    }

    @Override
    public void update(MealTo meal) {
        List<MealTo> tmpDatabase = new ArrayList<>(database);
        tmpDatabase.set(findIndexById(meal.getId()), meal);
        this.database = new CopyOnWriteArrayList<>(MealsUtil.filteredTo(tmpDatabase, MIDNIGHT, MAX, MAX_CALORIES));
    }

    @Override
    public MealTo read(int id) {
        return database.get(findIndexById(id));
    }

    @Override
    public List<MealTo> getAllList() {
        return database;
    }

}
