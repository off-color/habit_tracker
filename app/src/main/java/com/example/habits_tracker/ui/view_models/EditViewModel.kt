package com.example.habits_tracker.ui.view_models

import androidx.lifecycle.ViewModel
import com.example.habits_tracker.application.Model
import com.example.habits_tracker.domain.Habit
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class EditViewModel(
    private val isAdd: Boolean,
    private val initialHabit: Habit?
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + SupervisorJob() + CoroutineExceptionHandler { _, throwable -> throw throwable }

    fun saveHabit(habit: Habit) {
        if (isAdd) addHabit(habit) else editHabit(habit)
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }

    private fun addHabit(habit: Habit) = launch {
        Model.addHabit(habit)
    }

    private fun editHabit(habit: Habit) = launch {
        initialHabit?.let { Model.editHabit(habit, it) }
    }

}