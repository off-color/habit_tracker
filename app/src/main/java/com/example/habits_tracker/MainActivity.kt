package com.example.habits_tracker

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.habits_tracker.ui.Habit
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnSaveCallback, HabitsHolder {

    companion object {
        private const val HABITS = "habits"
        private const val MAIN_FRAGMENT = "main_fragment"
    }

    override var habits: MutableList<Habit> = mutableListOf()
    private var drawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureNavigationDrawer()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentHolder, MainFragment.newInstance(), MAIN_FRAGMENT).commit()
        }
    }

    override fun onSave(habit: Habit, isModeAdd: Boolean, position: Int, initialHabit: Habit?) {
        if (isModeAdd) {
            habits.add(habit)
        } else {
            val indices =
                habits.mapIndexed { index, h -> if (h.isGood == initialHabit?.isGood) index else null }
                    .filterNotNull()
            habits[indices[position]] = habit
        }
        supportFragmentManager.popBackStack()
        hideKeyboard()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(HABITS, ArrayList(habits))
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        habits = savedInstanceState.getParcelableArrayList(HABITS) ?: mutableListOf()
        replaceToMainFragment()
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle?.onOptionsItemSelected(item) == true) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configureNavigationDrawer() {
        drawerToggle = ActionBarDrawerToggle(
            this,
            navigationDrawerLayout,
            R.string.openDrawer,
            R.string.closeDrawer
        ).apply {
            navigationDrawerLayout.addDrawerListener(this)
            syncState()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navigationDrawer.setNavigationItemSelectedListener {
            onItemMenuSelect(it)
        }
    }

    private fun onItemMenuSelect(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuItemHome -> {
                replaceToMainFragment()
                navigationDrawerLayout.closeDrawers()
                true
            }

            R.id.menuItemInfo -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentHolder, InfoFragment.newInstance())
                    .commit()
                navigationDrawerLayout.closeDrawers()
                true
            }

            else -> false
        }
    }

    private fun hideKeyboard() {
        val view = currentFocus
        if (view != null) {
            val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    private fun replaceToMainFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHolder, MainFragment.newInstance(), MAIN_FRAGMENT)
            .commit()
    }
}

interface HabitsHolder {
    var habits: MutableList<Habit>
}
