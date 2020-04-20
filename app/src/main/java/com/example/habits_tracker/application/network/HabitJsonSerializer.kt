package com.example.habits_tracker.application.network

import com.example.habits_tracker.domain.Habit
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class HabitJsonSerializer : JsonSerializer<Habit> {
    override fun serialize(
        src: Habit,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement = JsonObject().apply {
        addProperty("count", src.count)
        addProperty("date", src.date)
        addProperty("description", src.description)
        addProperty("frequency", src.periodicity)
        addProperty("priority", src.priority - 1)
        addProperty("title", src.title)
        addProperty("type", if (src.isGood) 1 else 0)
        if (src.serverId != null) addProperty("uid", src.serverId)
    }
}