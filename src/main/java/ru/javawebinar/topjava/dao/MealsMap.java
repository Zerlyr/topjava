package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealsMap implements MealsDao {
    private final Map<Integer, Meal> database;
    private final AtomicInteger id = new AtomicInteger(0);

    public MealsMap() {
        database = new ConcurrentHashMap<>();
        final List<Meal> MEALS = Arrays.asList(
                new Meal(id.get(),LocalDateTime.of( 2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(id.incrementAndGet(),LocalDateTime.of( 2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(id.incrementAndGet(),LocalDateTime.of( 2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(id.incrementAndGet(),LocalDateTime.of( 2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(id.incrementAndGet(),LocalDateTime.of( 2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(id.incrementAndGet(),LocalDateTime.of( 2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        MEALS.forEach(m -> database.put(m.getId(), m));
    }

    @Override
    public Meal add(Meal meal) {
        meal.setId(id.incrementAndGet());
        database.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(Integer id) {
        database.remove(id);
    }

    @Override
    public Meal update(Meal meal) {
        database.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal read(Integer id) {
        return database.get(id);
    }

    @Override
    public List<Meal> getAllList() {
        return new ArrayList<>(database.values());
    }

}
