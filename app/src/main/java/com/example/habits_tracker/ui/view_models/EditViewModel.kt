package com.example.habits_tracker.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habits_tracker.application.database.DatabaseRepository
import com.example.habits_tracker.domain.Habit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditViewModel(
    private val isAdd: Boolean,
    private val initialHabit: Habit?
) : ViewModel() {

    fun saveHabit(habit: Habit) {
        if (isAdd) addHabit(habit) else editHabit(habit)
    }

    private fun addHabit(habit: Habit) = viewModelScope.launch(Dispatchers.IO) {
        DatabaseRepository.addHabit(habit)
    }

    private fun editHabit(habit: Habit) = viewModelScope.launch(Dispatchers.IO) {
        initialHabit?.let { DatabaseRepository.editHabit(habit, it) }
    }

}