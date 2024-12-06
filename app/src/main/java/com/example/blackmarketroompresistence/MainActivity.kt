package com.example.blackmarketroompresistence

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.blackmarketroompresistence.room.MainViewModel
import com.example.blackmarketroompresistence.room.NoteAdapter
import com.example.blackmarketroompresistence.ui.AddNoteActivity

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var mainViewModel: MainViewModel
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewNotes)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mainViewModel.allNotes.observe(this, Observer { notes ->
            val noteAdapter = NoteAdapter(notes)
            recyclerView.adapter = noteAdapter
        })

        val buttonAddNote: Button = findViewById(R.id.buttonAddNote)
        buttonAddNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

    }
}