package com.example.habits_tracker.application

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.habits_tracker.R
import com.example.habits_tracker.StringHelper
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.habit_layout.*

class HabitsViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    private val stringHelper = StringHelper()

    fun bind(habit: Habit, listener: OnItemClickListener, position: Int) {
        containerView.setOnClickListener {
            listener.onItemClick(habit, position)
        }
        titleView.text = habit.title
        periodicityView.text = containerView.context.getString(
            R.string.countAndPeriodicity,
            habit.count,
            stringHelper.getCountWordFor(habit.count),
            habit.periodicity,
            stringHelper.getDaysWordFor(habit.periodicity)
        )
        descriptionView.text = habit.description
        typeIcon.setImageResource(
            if (habit.isGood) R.drawable.ic_sentiment_satisfied
            else R.drawable.ic_sentiment_dissatisfied
        )
        priorityView.text = habit.priority.toString()
    }
}