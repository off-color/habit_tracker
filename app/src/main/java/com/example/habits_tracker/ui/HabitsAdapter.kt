package com.example.habits_tracker.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habits_tracker.R

class HabitsAdapter(
    private val habits: List<Habit>, private val listener: OnItemClickListener
) : RecyclerView.Adapter<HabitsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HabitsViewHolder(inflater.inflate(R.layout.habit_layout, parent, false))
    }

    override fun getItemCount() = habits.size

    override fun onBindViewHolder(holder: HabitsViewHolder, position: Int) {
        holder.bind(habits[position], listener, position)
    }
}