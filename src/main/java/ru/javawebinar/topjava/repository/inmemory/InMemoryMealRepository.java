package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {

    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();

    private final AtomicInteger counter = new AtomicInteger(0);

    {
        final Integer userId = 1;
        MealsUtil.meals.forEach(meal -> save(meal, userId));

        final Integer adminId = 2;
        save(new Meal(LocalDateTime.of(2022, Month.JANUARY, 12, 14, 32), "обед", 650), adminId);
    }

    @Override
    public synchronized Meal save(Meal meal, Integer userId) {
        Map<Integer, Meal> meals;
        if (!repository.containsKey(userId)) {
            meals = new ConcurrentHashMap<>();
            repository.put(userId, meals);
        } else meals = repository.get(userId);

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, Integer userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals != null && meals.remove(id) != null;
    }

    @Override
    public Meal get(int id, Integer userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public List<Meal> getAll(Integer userId) {
        return getFiltered(userId, m -> true);
    }

    @Override
    public List<Meal> getFilteredByDate(Integer userId, LocalDate start, LocalDate end) {
        return getFiltered(userId, m -> DateTimeUtil.isBetweenHalfOpen(m.getDate(), start, end));
    }

    private List<Meal> getFiltered(Integer userId, Predicate<Meal> condition) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ? Collections.emptyList() : meals.values().stream()
                .filter(condition)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

