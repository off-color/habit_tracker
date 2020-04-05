package com.example.habits_tracker.ui.view_holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.habits_tracker.R
import com.example.habits_tracker.domain.Habit
import com.example.habits_tracker.ui.OnItemClickListener


class HabitsAdapter(
    private var habits: List<Habit>, private val listener: OnItemClickListener
) : RecyclerView.Adapter<HabitsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HabitsViewHolder(
            inflater.inflate(R.layout.habit_layout, parent, false)
        )
    }

    override fun getItemCount() = habits.size

    override fun onBindViewHolder(holder: HabitsViewHolder, position: Int) =
        holder.bind(habits[position], listener)

    fun setData(habits: List<Habit>) {
        val postDiffCallback = HabitDiffUtilCallback(this.habits, habits)
        val diffResult = DiffUtil.calculateDiff(postDiffCallback)
        this.habits = habits
        diffResult.dispatchUpdatesTo(this)
    }
}