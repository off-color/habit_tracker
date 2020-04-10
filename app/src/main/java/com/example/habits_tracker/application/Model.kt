package com.example.habits_tracker.application

import com.example.habits_tracker.domain.Habit
import com.example.habits_tracker.infrastructure.Sorting

object Model {

    lateinit var appDatabase: AppDatabase

    fun getFilteredAndSortedHabits(
        isGood: Boolean,
        filter: String,
        sorting: Sorting
    ) = when (sorting) {
        Sorting.NotSorted -> appDatabase.habitDao().getFilteredHabits(isGood, filter)
        Sorting.Sorted -> appDatabase.habitDao()
            .getFilteredAndSortedByAscendingHabits(isGood, filter)
        Sorting.SortedByDescending -> appDatabase.habitDao()
            .getFilteredAndSortedByDescendingHabits(isGood, filter)
    }

    fun addHabit(habit: Habit) {
        appDatabase.habitDao().insertHabit(habit)
    }

    fun editHabit(habit: Habit, initialHabit: Habit) {
        habit.id = initialHabit.id
        appDatabase.habitDao().updateHabit(habit)
    }
}