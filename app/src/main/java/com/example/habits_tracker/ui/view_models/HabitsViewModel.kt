package com.example.habits_tracker.ui.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habits_tracker.application.Model
import com.example.habits_tracker.domain.Habit
import com.example.habits_tracker.infrastructure.Sorting

class HabitsViewModel : ViewModel() {
    private val mutableHabits: MutableLiveData<List<Habit>> = MutableLiveData()
    private var filter = ""
    private var sorting = Sorting.NotSorted

    val habits: LiveData<List<Habit>> = mutableHabits

    init {
        mutableHabits.value = Model.habits
    }

    fun getFilteredHabits(isGood: Boolean) =
        habits.value?.filter { it.isGood == isGood } ?: listOf()

    fun filterHabitsBySubstring(string: String) {
        val sortedHabits = getSortedHabits(Model.habits)
        filter = string
        mutableHabits.value = getFilteredHabits(sortedHabits)
    }

    fun sortHabitsByPriority(isDescending: Boolean) {
        val filteredHabits = getFilteredHabits(Model.habits)
        sorting = if (isDescending) Sorting.SortedByDescending else Sorting.Sorted
        mutableHabits.value = getSortedHabits(filteredHabits)
    }

    fun resetSorting() {
        sorting = Sorting.NotSorted
        mutableHabits.value = getFilteredHabits(Model.habits)
    }

    private fun getSortedHabits(habits: List<Habit>): List<Habit> {
        return when (sorting) {
            Sorting.NotSorted -> habits
            Sorting.Sorted -> habits.sortedBy { habit -> habit.priority }
            Sorting.SortedByDescending -> habits.sortedByDescending { habit -> habit.priority }
        }
    }

    private fun getFilteredHabits(habits: List<Habit>): List<Habit> {
        return if (filter.isEmpty()) habits
        else habits.filter { it.title.contains(filter) }
    }
}