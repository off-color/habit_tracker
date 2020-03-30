package com.example.habits_tracker.ui.tab_layout

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.habits_tracker.HabitFragment

class HabitTabsAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int) = HabitFragment.newInstance(position == 0)
}