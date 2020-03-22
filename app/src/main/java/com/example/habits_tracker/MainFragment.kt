package com.example.habits_tracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.habits_tracker.ui.HabitTabsAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var habitTabsAdapter: HabitTabsAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fab.setOnClickListener {
            val fragment = EditFragment.newInstance(true)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentHolder, fragment)
                ?.addToBackStack(HabitFragment.EDIT_FRAGMENT)?.commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        habitTabsAdapter = HabitTabsAdapter(this)
        pager.adapter = habitTabsAdapter
        TabLayoutMediator(tab_layout, pager) { tab, position ->
            tab.text =
                if (position == 0) resources.getString(R.string.goods) else resources.getString(R.string.bads)
        }.attach()
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}
