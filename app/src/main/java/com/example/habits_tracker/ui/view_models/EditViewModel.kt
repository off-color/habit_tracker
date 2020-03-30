package com.example.habits_tracker.ui.view_models

import androidx.lifecycle.ViewModel
import com.example.habits_tracker.application.Model
import com.example.habits_tracker.domain.Habit

class EditViewModel(
    private val isAdd: Boolean,
    private val initialHabit: Habit?
) : ViewModel() {

    fun saveHabit(habit: Habit) =
        if (isAdd) addHabit(habit) else editHabit(habit)

    private fun addHabit(habit: Habit) {
        Model.habits.add(habit)
    }

    private fun editHabit(habit: Habit) {
        val position = Model.habits.indexOf(initialHabit)
        if (position >= 0)
            Model.habits[position] = habit
    }

}