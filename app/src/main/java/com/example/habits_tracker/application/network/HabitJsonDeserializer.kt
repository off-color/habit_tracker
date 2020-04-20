package com.example.habits_tracker.application.network

import com.example.habits_tracker.domain.Habit
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class HabitJsonDeserializer : JsonDeserializer<Habit> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Habit {
        val jsonObject = json.asJsonObject
        val result = Habit(
            jsonObject.get("title").asString,
            jsonObject.get("description").asString,
            jsonObject.get("priority").asInt + 1,
            jsonObject.get("type").asInt == 1,
            jsonObject.get("count").asInt,
            jsonObject.get("frequency").asInt,
            jsonObject.get("date").asLong
        )
        result.serverId = jsonObject.get("uid").asString
        return result
    }
}