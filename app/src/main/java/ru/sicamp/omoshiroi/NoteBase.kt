package ru.sicamp.omoshiroi

import android.app.NotificationManager
import android.content.Context
import android.database.Cursor
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import java.lang.Exception

class NoteBase(private val context: Context) {
    private val database = context.openOrCreateDatabase("database", 0, null)
    private val notificationManager = NotificationManagerCompat.from(context)
    private var lastNoteId = 0
    private val notes = mutableMapOf<Int, Note>()

    init {
        Log.d("MyLogs", "NoteBase init begin")

        database.execSQL(
            "CREATE TABLE IF NOT EXISTS notes (id INTEGER, title TEXT, text TEXT, isPinned INTEGER);")

        loadAllNotes()

        Log.d("MyLogs", "NoteBase init end")
    }

    private fun addNoteIntoDatabase(noteId: Int) {
        Thread {
            Log.d("MyLogs", "NoteBase addNoteIntoDatabase begin")

            val note: Note = notes[noteId] ?: throw Exception("E")
            database.execSQL(
                "INSERT OR REPLACE INTO notes (id, title, text, isPinned) VALUES (" +
                    "${note.id}, " +
                    "'${note.title}', " +
                    "'${note.text}', " +
                    "${if (note.isPinned) 1 else 0}" +
                ");"
            )

            Log.d("MyLogs", "NoteBase addNoteIntoDatabase end")
        }.start()
    }

    fun addNote(
        noteId: Int,
        noteTitle: String,
        noteText: String,
        noteIsPinned: Boolean
    ) {
        Log.d("MyLogs", "NoteBase addNote begin")

        val note = Note().apply {
            id = noteId
            title = noteTitle
            text = noteText
            isPinned = noteIsPinned
        }
        notes[note.id] = note
        if (note.isPinned) makeNotification(note.id) else deleteNotification(note.id)
        addNoteIntoDatabase(note.id)

        Log.d("MyLogs", "NoteBase addNote end")
    }

    fun addEmptyNote() {
        Log.d("MyLogs", "NoteBase addEmptyNote begin")

        val note = Note()
        addNote(note.id, note.title, note.text, note.isPinned)

        Log.d("MyLogs", "NoteBase addEmptyNote end")
    }

    fun generateNoteId(): Int {
        Log.d("MyLogs", "NoteBase generateNoteId begin")

        while (notes.containsKey(lastNoteId * 100 + 77)) {
            lastNoteId++
        }

        Log.d("MyLogs", "NoteBase generateNoteId end")

        return lastNoteId * 100 + 77
    }

    private fun loadAllNotes() {
        Log.d("MyLogs", "NoteBase loadAllNotes begin")

        notes.clear()
        val cursor = database.rawQuery("SELECT * FROM notes;", null)
        loadNotes(cursor)

        Log.d("MyLogs", "NoteBase loadAllNotes end")
    }

    private fun loadNotes(cursor: Cursor) {
        Log.d("MyLogs", "NoteBase loadNotes begin")

        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val note = Note()
            note.id = cursor.getInt(0)
            note.title = cursor.getString(1)
            note.text =  cursor.getString(2)
            note.isPinned = cursor.getInt(3) == 1
            notes[note.id] = note
            cursor.moveToNext()
        }
        cursor.close()

        Log.d("MyLogs", "NoteBase loadNotes end")
    }

    private fun makeNotification(noteId: Int) {
        Log.d("MyLogs", "NoteBase makeNotification begin")

        deleteNotification(noteId)
        val note = notes[noteId] ?: throw Exception("E")

        val notification = NotificationCompat.Builder(context, "ru.sicamp.omoshiroi").apply {
            setSmallIcon(R.drawable.ic_launcher_background)
            setStyle(NotificationCompat.BigTextStyle().bigText(note.text))
            setContentTitle(note.title)
            setOngoing(true)
        }.build()
        notificationManager.notify(note.id, notification)

        Log.d("MyLogs", "NoteBase makeNotification end")
    }

    private fun deleteNotification(noteId: Int) {
        Log.d("MyLogs", "NoteBase deleteNotification begin")

        notificationManager.cancel(noteId)

        Log.d("MyLogs", "NoteBase deleteNotification end")
    }

    fun pinNote(noteId: Int) {
        Log.d("MyLogs", "NoteBase pinNote begin")

        (notes[noteId] ?: throw Exception("E")).isPinned = true
        makeNotification(noteId)
        addNoteIntoDatabase(noteId)

        Log.d("MyLogs", "NoteBase pinNote end")
    }

    fun unpinNote(noteId: Int) {
        Log.d("MyLogs", "NoteBase unpinNote begin")

        val note = notes[noteId] ?: throw Exception("E")
        note.isPinned = false
        notes[noteId] = note
        deleteNotification(noteId)
        addNoteIntoDatabase(noteId)

        Log.d("MyLogs", "NoteBase unpinNote end")
    }

    fun getAllNotes(): List<Note> {
        Log.d("MyLogs", "NoteBase getAllNotes begin")
        Log.d("MyLogs", "NoteBase getAllNotes end")

        return notes.values.toList().map { it.copy() }.sortedBy { !it.isPinned }
    }

    fun getNote(noteId: Int): Note {
        Log.d("MyLogs", "NoteBase getNote begin")
        Log.d("MyLogs", "NoteBase getNote end")

        return notes[noteId] ?: throw Exception("E")
    }

    fun deleteNote(noteId: Int) {
        Log.d("MyLogs", "NoteBase deleteNote begin")

        notes.remove(noteId)
        deleteNotification(noteId)
        deleteNoteFromDatabase(noteId)

        Log.d("MyLogs", "NoteBase deleteNote end")
    }

    private fun deleteNoteFromDatabase(noteId: Int) {
        Thread {
            Log.d("MyLogs", "NoteBase deleteNoteFromDatabase begin $noteId")

            database.execSQL("DELETE FROM notes WHERE id = $noteId;")

            Log.d("MyLogs", "NoteBase deleteNoteFromDatabase end $noteId")

        }.start()
    }
}