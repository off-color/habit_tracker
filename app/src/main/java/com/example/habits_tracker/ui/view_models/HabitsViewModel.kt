package com.example.habits_tracker.ui.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habits_tracker.application.Model
import com.example.habits_tracker.domain.Habit
import com.example.habits_tracker.infrastructure.Sorting

class HabitsViewModel : ViewModel() {
    private val mutableFilterLiveData: MutableLiveData<String> = MutableLiveData()
    private val mutableSortingLiveData: MutableLiveData<Sorting> = MutableLiveData()

    val filterLiveData: LiveData<String> = mutableFilterLiveData
    val sortingLiveData: LiveData<Sorting> = mutableSortingLiveData

    val habits: LiveData<List<Habit>> = Model.getAllHabits()

    init {
        mutableFilterLiveData.value = ""
        mutableSortingLiveData.value = Sorting.NotSorted
    }

    fun filterHabitsBySubstring(string: String) {
        mutableFilterLiveData.value = string
    }

    fun sortHabitsByPriority(isDescending: Boolean) {
        mutableSortingLiveData.value =
            if (isDescending) Sorting.SortedByDescending else Sorting.Sorted
    }

    fun resetSorting() {
        mutableSortingLiveData.value = Sorting.NotSorted
    }

    fun getSortedAndFilteredHabits(
        isGood: Boolean,
        filter: String?,
        sorting: Sorting?
    ): List<Habit> {
        val filteredByType = habits.value?.filter { it.isGood == isGood } ?: listOf()
        return getSortedHabits(
            getFilteredHabits(filteredByType, filter ?: ""),
            sorting ?: Sorting.NotSorted
        )
    }

    private fun getSortedHabits(habits: List<Habit>, sorting: Sorting): List<Habit> {
        return when (sorting) {
            Sorting.NotSorted -> habits
            Sorting.Sorted -> habits.sortedBy { habit -> habit.priority }
            Sorting.SortedByDescending -> habits.sortedByDescending { habit -> habit.priority }
        }
    }

    private fun getFilteredHabits(habits: List<Habit>, filter: String): List<Habit> {
        return if (filter.isEmpty()) habits
        else habits.filter { it.title.contains(filter) }
    }
}