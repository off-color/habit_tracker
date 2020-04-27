package com.example.habits_tracker.application.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habits_tracker.domain.Habit

@Dao
interface HabitDao {

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
    fun insertHabit(habit: Habit): Long

    @Update
    fun updateHabit(habit: Habit)

    @Query("SELECT id FROM habit WHERE serverId = :serverId")
    fun getIdByServerId(serverId: String): List<Long>
}