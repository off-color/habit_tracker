package com.example.habits_tracker.ui.view_models

import androidx.lifecycle.*
import com.example.habits_tracker.application.Model
import com.example.habits_tracker.application.database.DatabaseRepository
import com.example.habits_tracker.domain.Habit
import com.example.habits_tracker.infrastructure.Sorting
import com.example.habits_tracker.ui.FilterParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitsViewModel : ViewModel() {
    private val filterParametersLiveData: MutableLiveData<FilterParameters> = MutableLiveData()

    var goodHabits: LiveData<List<Habit>> = constructHabitsLiveData(true)
    var badHabits: LiveData<List<Habit>> = constructHabitsLiveData(false)

    init {
        filterParametersLiveData.value = FilterParameters("", Sorting.NotSorted)
        viewModelScope.launch(Dispatchers.IO) {
            Model.updateDatabaseFromServer()
        }
    }

    fun filterHabitsBySubstring(string: String) {
        filterParametersLiveData.value =
            FilterParameters(string, filterParametersLiveData.value?.sorting ?: Sorting.NotSorted)
    }

    fun sortHabitsByPriority(isDescending: Boolean) {
        filterParametersLiveData.value = FilterParameters(
            filterParametersLiveData.value?.stringFilter ?: "",
            if (isDescending) Sorting.SortedByDescending else Sorting.Sorted
        )
    }

    fun resetSorting() {
        filterParametersLiveData.value = FilterParameters(
            filterParametersLiveData.value?.stringFilter ?: "",
            Sorting.NotSorted
        )
    }

    private fun constructHabitsLiveData(isGood: Boolean) =
        Transformations.switchMap(
            filterParametersLiveData
        ) {
            DatabaseRepository.getFilteredAndSortedHabits(
                isGood,
                it.stringFilter,
                it.sorting
            )
        }

}