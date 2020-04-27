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
        val habitsFromServer = habitsFromServerDeferred.await() ?: return@withContext

        for (habit in habitsFromServer) {
            val serverId = habit.serverId
            if (serverId != null) {
                val ids = DatabaseRepository.getIdsByServerId(serverId)
                when (ids.size) {
                    0 -> DatabaseRepository.addHabit(habit)
                    else -> {
                        habit.id = ids[0]
                        DatabaseRepository.updateHabit(habit)
                    }
                }
            }
        }
    }

}