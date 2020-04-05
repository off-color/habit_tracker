package com.example.habits_tracker.ui.view_holder

import androidx.recyclerview.widget.DiffUtil
import com.example.habits_tracker.domain.Habit

class HabitDiffUtilCallback(private val oldList: List<Habit>, private val newList: List<Habit>) :
    DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id
}