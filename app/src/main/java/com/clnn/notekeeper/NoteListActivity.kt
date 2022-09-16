package com.clnn.notekeeper

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.ListView
import com.clnn.notekeeper.databinding.ActivityNoteListBinding

class NoteListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoteListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        findViewById<ListView>(R.id.listNotes).adapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1,
             DataManager.notes)

        findViewById<ListView>(R.id.listNotes).setOnItemClickListener {parent, view, position, id ->
            val activityIntent = Intent(this, MainActivity::class.java)
            activityIntent.putExtra(NOTE_POSITION, position)
            startActivity(activityIntent)
        }

        binding.fab.setOnClickListener { view ->
            val activityIntent = Intent(this, MainActivity::class.java)
            startActivity(activityIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        (findViewById<ListView>(R.id.listNotes).adapter as ArrayAdapter<NoteInfo>).notifyDataSetChanged()
    }
}