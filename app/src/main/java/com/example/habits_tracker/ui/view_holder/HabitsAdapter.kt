package com.example.habits_tracker.ui.view_holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.habits_tracker.R
import com.example.habits_tracker.domain.Habit
import com.example.habits_tracker.ui.OnItemClickListener


class HabitsAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<Habit, HabitsViewHolder>(HabitDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HabitsViewHolder(
            inflater.inflate(R.layout.habit_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HabitsViewHolder, position: Int) =
        holder.bind(getItem(position), listener)

    fun setData(habits: List<Habit>) {
        submitList(habits.toMutableList())
    }
}