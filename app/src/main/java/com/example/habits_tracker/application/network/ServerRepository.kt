package com.example.habits_tracker.application.network

import android.content.Context
import com.example.habits_tracker.R
import com.example.habits_tracker.domain.Habit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServerRepository {

    lateinit var context: Context

    private val gson: Gson =
        GsonBuilder().registerTypeAdapter(Habit::class.java, HabitJsonSerializer())
            .registerTypeAdapter(Habit::class.java, HabitJsonDeserializer()).create()
    private val retrofit: Retrofit =
        Retrofit.Builder().baseUrl("https://droid-test-server.doubletapp.ru/api/")
            .addConverterFactory(
                GsonConverterFactory.create(
                    gson
                )
            ).build()
    private val service = retrofit.create(HabitService::class.java)

    suspend fun getAllHabitsFromServer() =
        service.habitsList(context.getString(R.string.apiToken))

    suspend fun updateHabitOnServer(habit: Habit) =
        service.putHabit(context.getString(R.string.apiToken), habit)
}