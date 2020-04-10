package com.example.habits_tracker.ui.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.habits_tracker.application.Model
import com.example.habits_tracker.domain.Habit
import com.example.habits_tracker.infrastructure.DoubleLiveData
import com.example.habits_tracker.infrastructure.Sorting

class HabitsViewModel : ViewModel() {
    private val mutableSortingLiveData: MutableLiveData<Sorting> = MutableLiveData()
    private val mutableFilterLiveData: MutableLiveData<String> = MutableLiveData()

    var goodHabits: LiveData<List<Habit>> = constructHabitsLiveData(true)
    var badHabits: LiveData<List<Habit>> = constructHabitsLiveData(false)

    init {
        mutableSortingLiveData.value = Sorting.NotSorted
        mutableFilterLiveData.value = ""
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

    private fun constructHabitsLiveData(isGood: Boolean) =
        Transformations.switchMap(
            DoubleLiveData(
                mutableSortingLiveData,
                mutableFilterLiveData
            )
        ) {
            Model.getFilteredAndSortedHabits(isGood, it.second ?: "", it.first ?: Sorting.NotSorted)
        }

}