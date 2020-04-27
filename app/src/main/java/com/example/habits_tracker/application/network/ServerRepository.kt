package com.example.habits_tracker.application.network

import android.content.Context
import com.example.habits_tracker.R
import com.example.habits_tracker.domain.Habit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServerRepository {

    lateinit var context: Context
    lateinit var isNetworkAvailable: () -> Boolean

    private const val timeout = 5000L

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

    suspend fun getAllHabitsFromServer() = withContext(Dispatchers.IO) {
        performRequest {
            service.habitsList(context.getString(R.string.apiToken))
        }
    }

    suspend fun updateHabitOnServer(habit: Habit) = withContext(Dispatchers.IO) {
        performRequest {
            service.putHabit(context.getString(R.string.apiToken), habit)
        }
    }

    fun getServerId(responseBody: JsonObject?) = responseBody?.get("uid")?.asString

    private suspend fun <T> performRequest(makeRequest: suspend () -> Response<T>): T? {
        while (true) {
            if (isNetworkAvailable()) {
                val response = makeRequest()
                if (response.isSuccessful) {
                    return response.body()
                }
            }
            delay(timeout)
        }
    }
}