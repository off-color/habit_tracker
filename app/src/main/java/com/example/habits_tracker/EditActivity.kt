package com.example.habits_tracker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.RadioButton
import com.example.habits_tracker.application.Habit
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    companion object {
        const val HABIT = "habit"
        const val MODE_ADD = "mode_add"
    }

    private var isModeAdd = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        isModeAdd = intent.getBooleanExtra(MODE_ADD, false)
        if (!isModeAdd) {
            initEditMode(intent.getParcelableExtra(HABIT))
        }

        saveButton.setOnClickListener {
            if (allFieldsFilled()) {
                onSave()
            }
        }
    }

    private fun initEditMode(habit: Habit?) {
        if (habit != null) {
            titleTextField.setText(habit.title)
            descriptionTextField.setText(habit.description)
            prioritySpinner.setSelection((habit.priority ?: 1) - 1)
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
        val intent = Intent().apply {
            val title = titleTextField.text.toString()
            val description = descriptionTextField.text.toString()
            val priority = prioritySpinner.selectedItem.toString().toInt()
            val isGood =
                (findViewById<RadioButton>(typeRadioGroup.checkedRadioButtonId).text.toString()
                        == resources.getString(R.string.good))
            val count = countTextField.text.toString().toInt()
            val periodicity = periodicityTextField.text.toString().toInt()
            putExtra(MainActivity.HABIT,
                Habit(title, description, priority, isGood, count, periodicity))
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
