package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class MealsUtil {
    public static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Lock locker = new ReentrantLock();
        locker.lock();
        try {
            Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                    .collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));

            return meals.stream()
                    .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                    .map(meal -> createTo(meal.getId(), meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                    .collect(Collectors.toList());
        }
        finally {
            locker.unlock();
        }
    }
    public static MealTo createTo(Integer id, Meal meal, boolean excess) {
        return new MealTo(id, meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
