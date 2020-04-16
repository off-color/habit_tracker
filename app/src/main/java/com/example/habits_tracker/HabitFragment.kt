package com.example.habits_tracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.habits_tracker.domain.Habit
import com.example.habits_tracker.ui.OnItemClickListener
import com.example.habits_tracker.ui.view_holder.HabitsAdapter
import com.example.habits_tracker.ui.view_models.HabitsViewModel
import kotlinx.android.synthetic.main.fragment_habit.*


class HabitFragment : Fragment() {

    private var isGood = false
    private val habitsViewModel: HabitsViewModel by activityViewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        configureRecyclerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isGood = arguments?.run {
            getBoolean(IS_GOOD)
        } ?: false

        val habits = if (isGood) habitsViewModel.goodHabits else habitsViewModel.badHabits

        habits.observe(viewLifecycleOwner, Observer {
            (habitsRecyclerView.adapter as HabitsAdapter).setData(
                it
            )
        })
    }

    private fun configureRecyclerView() {
        habitsRecyclerView.adapter = HabitsAdapter(object :
            OnItemClickListener {
            override fun onItemClick(habit: Habit) {
                val fragment = EditFragment.newInstance(false, habit)
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragmentHolder, fragment)
                    ?.addToBackStack(EDIT_FRAGMENT)?.commit()
            }
        })
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
