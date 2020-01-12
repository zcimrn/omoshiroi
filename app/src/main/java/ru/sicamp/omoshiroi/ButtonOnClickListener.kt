package ru.sicamp.omoshiroi

import android.app.AlertDialog
import android.content.Intent
import android.view.View

class ButtonOnClickListener(private val activity: MainActivity) : View.OnClickListener {
    override fun onClick(view: View) {
        if (view.id == R.id.newNoteButton) {
            onNewNoteButtonClick()
        }
        if (view.id == R.id.informationButton) {
            onInformationButtonClick(view)
        }
    }

    private fun onNewNoteButtonClick() {
        Thread {
            val noteId = activity.noteBase.generateNoteId()
            activity.noteBase.addNote(noteId, "", "", false)
            val intent = Intent(activity, NoteEditorActivity::class.java)
            intent.putExtra("noteId", noteId)
            activity.startActivity(intent)
        }.start()
    }

    private fun onInformationButtonClick(view: View) {
        AlertDialog.Builder(activity).apply {
            setTitle("Some info about omoshiroi")
            setMessage(
                "просто заметки\n" +
                "просто проект из sicamp\n" +
                "а я просто автор этой штуки\n" +
                "Захар Черемных\n" +
                "(pomodorcat)")
        }.create().show()
    }
}