package com.example.habits_tracker.ui.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habits_tracker.application.Model
import kotlinx.coroutines.launch

class BackupViewModel(private val isNetworkAvailable: () -> Boolean) : ViewModel() {

    private val mutableIsSuccessLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isSuccessLiveData = mutableIsSuccessLiveData

    fun downloadHabitsFromServer() = viewModelScope.launch {
        val isNetworkAvailableResult = isNetworkAvailable()
        if (isNetworkAvailableResult) {
            Model.updateDatabaseFromServer()
        }
        isSuccessLiveData.value = isNetworkAvailableResult
    }

    fun uploadHabitsToServer() = viewModelScope.launch {
        val isNetworkAvailableResult = isNetworkAvailable()
        if (isNetworkAvailableResult) {
            Model.updateServerFromDatabase()
        }
        isSuccessLiveData.value = isNetworkAvailableResult
    }

}