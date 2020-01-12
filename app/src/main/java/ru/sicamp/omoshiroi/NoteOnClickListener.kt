package ru.sicamp.omoshiroi

import android.content.Intent
import android.view.View

class NoteOnClickListener(private val activity: MainActivity) : View.OnClickListener {
    override fun onClick(view: View) {
        if (view.id % 100 == 77) {
            val intent = Intent(activity, NoteEditorActivity::class.java)
            intent.putExtra("noteId", view.id)
            activity.startActivity(intent)
        }
    }
}