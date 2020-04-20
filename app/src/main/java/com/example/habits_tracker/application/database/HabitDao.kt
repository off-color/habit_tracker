package com.example.habits_tracker.application.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habits_tracker.domain.Habit

@Dao
interface HabitDao {

    @Query("SELECT * FROM habit")
    fun getAllHabits(): List<Habit>

    @Transaction
    fun updateAllHabits(habits: List<Habit>) {
        deleteAllHabits()
        insertAllHabits(habits)
    }

    @Query("SELECT * FROM habit WHERE isGood = :isGood AND title LIKE :filter || '%'")
    fun getFilteredHabits(isGood: Boolean, filter: String): LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE isGood = :isGood AND title LIKE :filter || '%' ORDER BY priority")
    fun getFilteredAndSortedByAscendingHabits(
        isGood: Boolean,
        filter: String
    ): LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE isGood = :isGood AND title LIKE :filter || '%' ORDER BY priority DESC")
    fun getFilteredAndSortedByDescendingHabits(
        isGood: Boolean,
        filter: String
    ): LiveData<List<Habit>>

    @Insert
    fun insertHabit(habit: Habit)

    @Insert
    fun insertAllHabits(habits: List<Habit>)

    @Update
    fun updateHabit(habit: Habit)

    @Query("DELETE FROM habit")
    fun deleteAllHabits()
}