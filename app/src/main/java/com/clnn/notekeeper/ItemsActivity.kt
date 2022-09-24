package com.clnn.notekeeper

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.clnn.notekeeper.databinding.ActivityItemsBinding

class ItemsActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityItemsBinding

    private val drawerLayout: DrawerLayout by lazy { binding.drawerLayout }
    private val navView: NavigationView by lazy { binding.navView }
    private val navController by lazy { findNavController(R.id.nav_host_fragment_content_items) }

    private val noteLayoutManager by lazy { LinearLayoutManager(this) }

    private val noteRecyclerAdapter by lazy { NoteRecyclerAdapter(this, DataManager.notes) }

    private val courseLayoutManager by lazy {
        GridLayoutManager(this, resources.getInteger(R.integer.course_grid_span))
    }

    private val courseRecyclerViewAdapter by lazy {
        CourseRecyclerAdapter(this, DataManager.courses.values.toList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarItems.toolbar)

        binding.appBarItems.fab.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        displayNotes()

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_notes, R.id.nav_courses,), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_notes -> {
                    displayNotes()
                    handleSelection(R.string.nav_notes_string)
                }

                R.id.nav_courses -> {
                    displayCourses()
                    handleSelection(R.string.nav_courses_string)
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    private fun displayNotes() {
        findViewById<RecyclerView>(R.id.listItems).layoutManager = noteLayoutManager
        findViewById<RecyclerView>(R.id.listItems).adapter = noteRecyclerAdapter

        navView.menu.findItem(R.id.nav_notes).isChecked = true
    }

    private fun displayCourses() {
        findViewById<RecyclerView>(R.id.listItems).layoutManager = courseLayoutManager
        findViewById<RecyclerView>(R.id.listItems).adapter = courseRecyclerViewAdapter

        navView.menu.findItem(R.id.nav_courses).isChecked = true
    }

    private fun handleSelection(stringId: Int) {
        Snackbar.make(findViewById(R.id.listItems),stringId, Snackbar.LENGTH_LONG).show()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        findViewById<RecyclerView>(R.id.listItems).adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.items, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_items)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}