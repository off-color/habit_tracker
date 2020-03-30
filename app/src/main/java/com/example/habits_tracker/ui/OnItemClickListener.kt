package com.example.habits_tracker.ui

import com.example.habits_tracker.domain.Habit

interface OnItemClickListener {
    fun onItemClick(habit: Habit)
}