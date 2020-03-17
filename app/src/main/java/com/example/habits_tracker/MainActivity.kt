package com.example.habits_tracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.habits_tracker.application.Habit
import com.example.habits_tracker.application.HabitsAdapter
import com.example.habits_tracker.application.OnItemClickListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val ADD_REQUEST = 1
        const val EDIT_REQUEST = 2
        const val HABIT = "habit"
        const val HABITS = "habits"
    }

    private var habits : MutableList<Habit> = mutableListOf()
    private var editPosition : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            Intent(this, EditActivity::class.java).run {
                startActivityForResult(this, ADD_REQUEST)
            }
        }

        configureRecyclerView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == ADD_REQUEST || requestCode == EDIT_REQUEST) &&
            resultCode == Activity.RESULT_OK && data != null) {
            data.getParcelableExtra<Habit>(HABIT)?.run {
                if (requestCode == ADD_REQUEST) {
                    habits.add(this)
                    habitsRecyclerView.adapter?.notifyItemInserted(habits.size - 1)
                }
                else {
                    habits[editPosition] = this
                    habitsRecyclerView.adapter?.notifyItemChanged(editPosition)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(HABITS, ArrayList(habits))
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        habits = savedInstanceState.getParcelableArrayList<Habit>(HABITS) ?: mutableListOf()
        configureRecyclerView()
        super.onRestoreInstanceState(savedInstanceState)
    }

    private fun configureRecyclerView() {
        habitsRecyclerView.adapter = HabitsAdapter(habits, object :
            OnItemClickListener {
            override fun onItemClick(habit: Habit, position: Int) {
                editPosition = position
                Intent(this@MainActivity, EditActivity::class.java).run {
                    putExtra(EditActivity.HABIT, habit)
                    startActivityForResult(this, EDIT_REQUEST)
                }
            }
        })
        val dividerItemDecoration = DividerItemDecoration(
            habitsRecyclerView.context, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.vertical_divider))
        habitsRecyclerView.addItemDecoration(dividerItemDecoration)
    }
}
