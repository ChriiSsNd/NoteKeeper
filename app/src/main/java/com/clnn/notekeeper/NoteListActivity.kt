package com.clnn.notekeeper

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.clnn.notekeeper.databinding.ActivityNoteListBinding

class NoteListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoteListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        findViewById<RecyclerView>(R.id.listItems).layoutManager = LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.listItems).adapter = NoteRecyclerAdapter(this, DataManager.notes)

        binding.fab.setOnClickListener { view ->
            val activityIntent = Intent(this, MainActivity::class.java)
            startActivity(activityIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        findViewById<RecyclerView>(R.id.listItems).adapter?.notifyDataSetChanged()
    }
}