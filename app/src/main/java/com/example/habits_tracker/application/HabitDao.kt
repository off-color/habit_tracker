package com.example.habits_tracker.application

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
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
    fun insertHabit(habit: Habit)

    @Update
    fun updateHabit(habit: Habit)
}