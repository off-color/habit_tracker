package com.example.habits_tracker

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.habits_tracker.application.database.AppDatabase
import com.example.habits_tracker.application.database.DatabaseRepository
import com.example.habits_tracker.application.network.ServerRepository
import com.example.habits_tracker.infrastructure.hideKeyboard
import com.example.habits_tracker.ui.OnSaveCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnSaveCallback {

    companion object {
        private const val MAIN_FRAGMENT = "main_fragment"
        private const val INFO_FRAGMENT = "info_fragment"
        private const val BACKUP_FRAGMENT = "backup_fragment"
    }

    private var drawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureNavigation()

        if (savedInstanceState == null) {
            DatabaseRepository.appDatabase =
                Room.databaseBuilder(applicationContext, AppDatabase::class.java, "HabitReader")
                    .build()
            ServerRepository.context = applicationContext
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentHolder, MainFragment.newInstance(), MAIN_FRAGMENT).commit()
        }
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

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        replaceToMainFragment()
    }

    override fun onSaveHabit() {
        supportFragmentManager.popBackStack()
        hideKeyboard()
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

        navigationDrawer.setNavigationItemSelectedListener(this@MainActivity::onItemMenuSelect)
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

            R.id.menuItemBackup -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentHolder, BackupFragment.newInstance())
                    .addToBackStack(BACKUP_FRAGMENT)
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

