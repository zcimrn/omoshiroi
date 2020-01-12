package ru.sicamp.omoshiroi

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.View
import java.lang.Exception

class NoteOnLongClickListener(private val activity: MainActivity) : View.OnLongClickListener {
    private val onPinNoteButtonClick = { noteId: Int -> Unit
        Log.d("MyLogs", "NoteOnLongClickListener onPinNoteButtonClick begin")

        activity.noteBase.pinNote(noteId)
        activity.noteAdapter.swapItems(activity.noteBase.getAllNotes())

            Log.d("MyLogs", "NoteOnLongClickListener onPinNoteButtonClick end")
    }

    private val onUnpinNoteButtonClick = { noteId: Int -> Unit
        Log.d("MyLogs", "NoteOnLongClickListener onUnpinNoteButtonClick begin")

        activity.noteBase.unpinNote(noteId)
        activity.noteAdapter.swapItems(activity.noteBase.getAllNotes())

        Log.d("MyLogs", "NoteOnLongClickListener onUnpinNoteButtonClick end")
    }

    private val onShareNoteButtonClick = { noteId: Int -> Unit
        Log.d("MyLogs", "NoteOnLongClickListener onShareNoteButtonClick begin")

        val note = activity.noteBase.getNote(noteId)
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT,
                "Note title:\n" +
                "${note.title}\n\n" +
                "Note text:\n" +
                "${note.text}"
            )
        }
        activity.startActivity(intent)

        Log.d("MyLogs", "NoteOnLongClickListener onShareNoteButtonClick end")
    }

    private val onDeleteNoteButtonClick = { noteId: Int -> Unit
        Log.d("MyLogs", "NoteOnLongClickListener onDeleteNoteButtonClick begin")

        activity.noteBase.deleteNote(noteId)
        activity.noteAdapter.swapItems(activity.noteBase.getAllNotes())

        Log.d("MyLogs", "NoteOnLongClickListener onDeleteNoteButtonClick end")
    }

    private val noteActions = mutableMapOf(
        "Pin" to onPinNoteButtonClick,
        "Unpin" to onUnpinNoteButtonClick,
        "Share" to onShareNoteButtonClick,
        "Delete" to onDeleteNoteButtonClick
    )

    override fun onLongClick(view: View): Boolean {
        Log.d("MyLogs", "NoteOnLongClickListener onLongClick begin")

        if (view.id % 100 == 77) {
            val note = activity.noteBase.getNote(view.id)
            val noteActionNameList = listOf(
                if (note.isPinned) "Unpin" else "Pin",
                "Share",
                "Delete"
            )

            AlertDialog.Builder(activity).setItems(noteActionNameList.toTypedArray(),
                DialogInterface.OnClickListener { dialogInterface, position ->
                    (noteActions[noteActionNameList[position]] ?: throw Exception("E"))(note.id)
                }
            ).create().show()
        }

        Log.d("MyLogs", "NoteOnLongClickListener onLongClick end")

        return true
    }
}