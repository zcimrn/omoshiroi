package ru.sicamp.omoshiroi

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.renderscript.ScriptIntrinsicYuvToRGB
import android.util.Log
import android.view.View
import android.view.WindowId
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File
import java.lang.Exception
import kotlin.random.Random

class NoteEditorActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var noteTypeSeletorSimple: TextView
    private lateinit var noteTypeSeletorPinned: TextView
    private lateinit var noteTypeSeletorTerminal: TextView
    private lateinit var noteTitleEditText: EditText
    private lateinit var noteTextEditText: EditText
    private lateinit var noteBase: NoteBase
    private lateinit var note: Note
    private var noteIsPinned = false

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MyLogs", "NoteEditorActivity onCreate begin")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_editor_activity)

        noteTypeSeletorSimple = findViewById(R.id.noteTypeSelectorSimple)
        noteTypeSeletorSimple.setOnClickListener(this)
        noteTypeSeletorPinned = findViewById(R.id.noteTypeSelectorPinned)
        noteTypeSeletorPinned.setOnClickListener(this)
//        noteTypeSeletorTerminal = findViewById(R.id.noteTypeSelectorTerminal)
//        noteTypeSeletorTerminal.setOnClickListener(this)
        noteTitleEditText = findViewById(R.id.noteTitleEditText)
        noteTextEditText = findViewById(R.id.noteTextEditText)

        noteBase = (application as MainApplication).noteBase

        note = noteBase.getNote(intent.getIntExtra("noteId", 0))

        noteTitleEditText.setText(note.title)
        noteTextEditText.setText(note.text)
        if (note.isPinned) selectPinned() else selectSimple()

        Log.d("MyLogs", "NoteEditorActivity onCreate end")
    }

    private fun saveNote() {
        Log.d("MyLogs", "NoteEditorActivity saveNote begin")

        val newNoteTitle = noteTitleEditText.text.toString()
        val newNoteText = noteTextEditText.text.toString()

        if (
            newNoteTitle.isEmpty() &&
            newNoteText.isEmpty() &&
            !noteIsPinned
        ) {
            noteBase.deleteNote(note.id)
        } else/* if (
            note.title != newNoteTitle ||
            note.text != newNoteText ||
            note.isPinned != noteIsPinned

        ) */{
            noteBase.addNote(
                note.id,
                newNoteTitle,
                newNoteText,
                noteIsPinned
            )
        }

        Log.d("MyLogs", "NoteEditorActivity saveNote end")
    }

    override fun onBackPressed() {
        Log.d("MyLogs", "NoteEditorActivity onBackPressed begin")

        saveNote()

        super.onBackPressed()

        Log.d("MyLogs", "NoteEditorActivity onBackPressed end")
    }

    private fun selectSimple() {
        Log.d("MyLogs", "NoteEditorActivity selectSimple begin")

        noteIsPinned = false
        saveNote()
        noteTypeSeletorSimple.setBackgroundResource(R.drawable.note_type_left_button_black)
        noteTypeSeletorSimple.setTextColor(Color.WHITE)
        noteTypeSeletorPinned.setBackgroundResource(R.drawable.note_type_right_button_white)
        noteTypeSeletorPinned.setTextColor(Color.BLACK)

        Log.d("MyLogs", "NoteEditorActivity selectSimple end")
    }

    private fun selectPinned() {
        Log.d("MyLogs", "NoteEditorActivity selectPinned begin")

        noteIsPinned = true
        saveNote()
        noteTypeSeletorSimple.setBackgroundResource(R.drawable.note_type_left_button_white)
        noteTypeSeletorSimple.setTextColor(Color.BLACK)
        noteTypeSeletorPinned.setBackgroundResource(R.drawable.note_type_right_button_black)
        noteTypeSeletorPinned.setTextColor(Color.WHITE)

        Log.d("MyLogs", "NoteEditorActivity selectPinned end")
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.noteTypeSelectorSimple -> {
                selectSimple()
            }
            R.id.noteTypeSelectorPinned -> {
                selectPinned()
            }
        }
    }
}