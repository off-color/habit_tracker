package com.example.habits_tracker.application.database

import com.example.habits_tracker.domain.Habit
import com.example.habits_tracker.infrastructure.Sorting

object DatabaseRepository {

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

    fun addHabit(habit: Habit) = appDatabase.habitDao().insertHabit(habit)

    fun updateHabit(habit: Habit) {
        appDatabase.habitDao().updateHabit(habit)
    }

    fun getIdsByServerId(serverId: String) =
        appDatabase.habitDao().getIdByServerId(serverId)

}