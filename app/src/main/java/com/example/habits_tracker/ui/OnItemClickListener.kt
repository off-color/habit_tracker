package com.example.habits_tracker.ui

import com.example.habits_tracker.application.Habit

interface OnItemClickListener {
    fun onItemClick(habit: Habit, position: Int)
}