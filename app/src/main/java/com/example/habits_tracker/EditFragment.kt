package com.example.habits_tracker

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.example.habits_tracker.application.Habit
import com.example.habits_tracker.ui.OnSaveCallback
import kotlinx.android.synthetic.main.fragment_edit.*

class EditFragment : Fragment() {

    private var callback: OnSaveCallback? = null
    private var isModeAdd = false
    private var mainView: View? = null
    private var position: Int = 0
    private var habit: Habit? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = activity as OnSaveCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = inflater.inflate(R.layout.fragment_edit, container, false)
        return mainView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            isModeAdd = it.getBoolean(MODE_ADD)
            position = it.getInt(POSITION)
            if (!isModeAdd) {
                habit = it.getParcelable(HABIT)
                initEditMode(habit)
            }
        }

        saveButton.setOnClickListener {
            if (allFieldsFilled()) {
                onSave()
            }
        }
    }

    companion object {
        private const val MODE_ADD = "mode_add"
        private const val HABIT = "habit"
        private const val POSITION = "position"

        @JvmStatic
        fun newInstance(isModeAdd: Boolean, habit: Habit? = null, position: Int = 0) =
            EditFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(MODE_ADD, isModeAdd)
                    putParcelable(HABIT, habit)
                    putInt(POSITION, position)
                }
            }
    }

    private fun initEditMode(habit: Habit?) {
        if (habit != null) {
            titleTextField.setText(habit.title)
            descriptionTextField.setText(habit.description)
            prioritySpinner.setSelection(habit.priority - 1)
            typeRadioGroup.check(if (habit.isGood) R.id.good else R.id.bad)
            countTextField.setText(habit.count.toString())
            periodicityTextField.setText(habit.periodicity.toString())
        }
    }

    private fun allFieldsFilled(): Boolean {
        val requiredTextViews = listOf(
            titleTextField, countTextField, periodicityTextField
        )
        if (requiredTextViews.all { !TextUtils.isEmpty(it.text) }) {
            return true
        }
        requiredTextViews.first { TextUtils.isEmpty(it.text) }.apply {
            error = "Обязательно к заполнению"
            requestFocus()
        }
        return false
    }

    private fun onSave() {
        val title = titleTextField.text.toString()
        val description = descriptionTextField.text.toString()
        val priority = prioritySpinner.selectedItem.toString().toInt()
        val isGood =
            (mainView?.findViewById<RadioButton>(typeRadioGroup.checkedRadioButtonId)?.text.toString()
                    == resources.getString(R.string.good))
        val count = countTextField.text.toString().toInt()
        val periodicity = periodicityTextField.text.toString().toInt()
        callback?.onSave(
            Habit(
                title,
                description,
                priority,
                isGood,
                count,
                periodicity
            ),
            isModeAdd,
            position,
            habit
        )
    }
}