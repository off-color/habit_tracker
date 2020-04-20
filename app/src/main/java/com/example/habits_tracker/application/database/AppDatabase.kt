package com.example.habits_tracker.application.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.habits_tracker.domain.Habit

@Database(entities = [Habit::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}