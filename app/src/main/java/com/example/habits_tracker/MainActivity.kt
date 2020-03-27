package com.example.habits_tracker

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.habits_tracker.application.Habit
import com.example.habits_tracker.infrastructure.hideKeyboard
import com.example.habits_tracker.ui.HabitsHolder
import com.example.habits_tracker.ui.OnSaveCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    OnSaveCallback, HabitsHolder {

    companion object {
        private const val HABITS = "habits"
        private const val MAIN_FRAGMENT = "main_fragment"
        private const val INFO_FRAGMENT = "info_fragment"
    }

    override var habits: MutableList<Habit> = mutableListOf()
    private var drawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureNavigation()

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

        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun configureNavigation() {
        supportFragmentManager.addOnBackStackChangedListener {
            drawerToggle?.isDrawerIndicatorEnabled = supportFragmentManager.backStackEntryCount == 0
        }

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
        supportActionBar?.setDisplayShowHomeEnabled(true)

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
                    .addToBackStack(INFO_FRAGMENT)
                    .commit()
                navigationDrawerLayout.closeDrawers()
                true
            }

            else -> false
        }
    }

    private fun replaceToMainFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHolder, MainFragment.newInstance(), MAIN_FRAGMENT)
            .commit()
    }
}

