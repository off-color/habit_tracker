package com.example.habits_tracker.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.habits_tracker.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.habit_layout.*

class HabitsViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(habit: Habit, listener: OnItemClickListener, position: Int) {
        containerView.setOnClickListener {
            listener.onItemClick(habit, position)
        }
        titleView.text = habit.title
        periodicityView.text = containerView.context.getString(
            R.string.countAndPeriodicity, containerView.context.resources.getQuantityString(
                R.plurals.countPlurals,
                habit.count,
                habit.count
            ), containerView.context.resources.getQuantityString(
                R.plurals.daysPlurals,
                habit.periodicity,
                habit.periodicity
            )
        )
        descriptionView.text = habit.description
        priorityView.text = habit.priority.toString()
    }
}