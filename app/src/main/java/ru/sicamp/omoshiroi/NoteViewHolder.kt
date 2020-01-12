package ru.sicamp.omoshiroi

import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView

class NoteViewHolder(val noteLayout: LinearLayout) : RecyclerView.ViewHolder(noteLayout) {
    val noteTitleTextView = noteLayout[0] as TextView
    val noteTextTextView = noteLayout[1] as TextView
    val noteTypeTextView = noteLayout[2] as TextView
}