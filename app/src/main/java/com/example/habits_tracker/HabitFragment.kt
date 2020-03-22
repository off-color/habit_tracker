package com.example.habits_tracker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.habits_tracker.ui.Habit
import com.example.habits_tracker.ui.HabitsAdapter
import com.example.habits_tracker.ui.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_habit.*


class HabitFragment : Fragment() {

    private var habitsHolder: HabitsHolder? = null
    private var isGood = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        configureRecyclerView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        habitsHolder = activity as HabitsHolder
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            isGood = it.getBoolean(IS_GOOD)
        }
    }

    private fun configureRecyclerView() {
        habitsRecyclerView.adapter = habitsHolder?.habits?.filter { it.isGood == isGood }?.let {
            HabitsAdapter(it, object :
                OnItemClickListener {
                override fun onItemClick(habit: Habit, position: Int) {
                    val fragment = EditFragment.newInstance(false, habit, position)
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.fragmentHolder, fragment)
                        ?.addToBackStack(EDIT_FRAGMENT)?.commit()
                }
            })
        }
        val dividerItemDecoration = DividerItemDecoration(
            habitsRecyclerView.context, DividerItemDecoration.VERTICAL
        )
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.vertical_divider))
        habitsRecyclerView.addItemDecoration(dividerItemDecoration)
    }

    companion object {
        const val EDIT_FRAGMENT = "edit_fragment"
        private const val IS_GOOD = "is_good"

        @JvmStatic
        fun newInstance(isGood: Boolean) = HabitFragment().apply {
            arguments = Bundle().apply {
                putBoolean(IS_GOOD, isGood)
            }
        }
    }
}
