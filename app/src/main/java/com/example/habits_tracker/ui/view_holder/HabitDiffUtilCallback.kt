package com.example.habits_tracker.ui.view_holder

import androidx.recyclerview.widget.DiffUtil
import com.example.habits_tracker.domain.Habit

class HabitDiffUtilCallback :
    DiffUtil.ItemCallback<Habit>() {

    override fun areItemsTheSame(oldItem: Habit, newItem: Habit) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Habit, newItem: Habit) = oldItem == newItem
}