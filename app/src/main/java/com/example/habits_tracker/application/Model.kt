package com.example.habits_tracker.application

import com.example.habits_tracker.application.database.DatabaseRepository
import com.example.habits_tracker.application.network.ServerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

object Model {

    suspend fun updateDatabaseFromServer() = withContext(Dispatchers.IO) {
        val habitsFromServerDeferred = async {
            ServerRepository.getAllHabitsFromServer()
        }
        val habitsFromServer = habitsFromServerDeferred.await()
        DatabaseRepository.updateAllHabits(habitsFromServer)
    }

    suspend fun updateServerFromDatabase() = withContext(Dispatchers.IO) {
        val habitsFromDatabaseDeferred = async {
            DatabaseRepository.getAllHabits()
        }
        val habitsFromDatabase = habitsFromDatabaseDeferred.await()
        for (habit in habitsFromDatabase) {
            val response = ServerRepository.updateHabitOnServer(habit)
            if (response.isSuccessful && habit.serverId == null) {
                habit.serverId = response.body()?.get("uid")?.asString
                DatabaseRepository.updateHabit(habit)
            }
        }
    }
}