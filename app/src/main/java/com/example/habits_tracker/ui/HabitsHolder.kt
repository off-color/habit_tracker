package com.example.habits_tracker.ui

import com.example.habits_tracker.application.Habit

interface HabitsHolder {
    var habits: MutableList<Habit>
}