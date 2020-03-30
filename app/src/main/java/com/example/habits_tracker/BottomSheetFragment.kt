package com.example.habits_tracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.example.habits_tracker.ui.view_models.HabitsViewModel
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*

class BottomSheetFragment : Fragment() {

    private val habitsViewModel: HabitsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchTextField.addTextChangedListener {
            habitsViewModel.filterHabitsBySubstring(it.toString())
        }
        buttonToggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                habitsViewModel.sortHabitsByPriority(checkedId == R.id.buttonSortUp)
            } else {
                if (group.checkedButtonId == View.NO_ID)
                    habitsViewModel.resetSorting()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = BottomSheetFragment()
    }
}
