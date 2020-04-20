package com.example.habits_tracker.application.network

import com.example.habits_tracker.domain.Habit
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

interface HabitService {
    @GET("habit")
    suspend fun habitsList(@Header("Authorization") authorization: String): List<Habit>

    @PUT("habit")
    suspend fun putHabit(
        @Header("Authorization") authorization: String,
        @Body habit: Habit
    ): Response<JsonObject>
}