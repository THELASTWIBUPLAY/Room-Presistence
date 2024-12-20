package com.example.blackmarketroompresistence.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.blackmarketroompresistence.R
import com.example.blackmarketroompresistence.room.MainViewModel
import com.example.blackmarketroompresistence.room.Note

class EditNoteActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var editTitle: EditText
    private lateinit var editContent: EditText
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    private var noteId: Int = 0
    private lateinit var originalNote: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_note)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val note = intent.getSerializableExtra("note") as? Note
        note?.let {
            originalNote = it
            noteId = it.id
        }

        editTitle = findViewById(R.id.editTitleField)
        editContent = findViewById(R.id.editContentField)
        saveButton = findViewById(R.id.buttonSave)
        cancelButton = findViewById(R.id.buttonCancel)

        editTitle.setText(originalNote.title)
        editContent.setText(originalNote.content)

        saveButton.setOnClickListener {
            val updatedTitle = editTitle.text.toString()
            val updatedContent = editContent.text.toString()

            if (updatedTitle.isNotEmpty() && updatedContent.isNotEmpty()) {
                val updatedNote = Note(
                    id = originalNote.id,
                    title = updatedTitle,
                    content = updatedContent,
                    timestamp = originalNote.timestamp
                )

                mainViewModel.updateNote(updatedNote)

                val resultIntent = Intent().apply {
                    putExtra("updatedNote", updatedNote)
                }

                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Title and Content Cannot be empty!", Toast.LENGTH_SHORT).show()
            }
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }
}