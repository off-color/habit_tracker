package com.example.habits_tracker.application

interface OnItemClickListener {
    fun onItemClick(habit: Habit, position: Int)
}