package com.example.habits_tracker.ui

import com.example.habits_tracker.application.Habit

interface OnSaveCallback {
    fun onSave(habit: Habit, isModeAdd: Boolean, position: Int, initialHabit: Habit?)
}