package com.example.habits_tracker.application

import com.example.habits_tracker.domain.Habit

object Model {

    lateinit var appDatabase: AppDatabase

    fun getAllHabits() = appDatabase.habitDao().getAllHabits()

    fun addHabit(habit: Habit) {
        appDatabase.habitDao().insertHabit(habit)
    }

    fun editHabit(habit: Habit, initialHabit: Habit) {
        habit.id = initialHabit.id
        appDatabase.habitDao().updateHabit(habit)
    }
}