package ru.sicamp.omoshiroi

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.get
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(private val activity: MainActivity) : RecyclerView.Adapter<NoteViewHolder>() {
    private var noteList = mutableListOf<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val noteLayout = layoutInflater.inflate(R.layout.note_view, parent, false) as LinearLayout
        return NoteViewHolder(noteLayout)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = noteList[position]
        holder.noteLayout.id = note.id
        holder.noteTitleTextView.text = note.title
        if (note.title.isEmpty()) holder.noteLayout[0].visibility = View.GONE
        holder.noteTextTextView.text = note.text
        if (note.text.isEmpty()) holder.noteLayout[1].visibility = View.GONE
        holder.noteTypeTextView.text = if (note.isPinned) "(pinned)" else "(simple)"
        holder.noteLayout.setOnClickListener(activity.noteOnClickListener)
        holder.noteLayout.setOnLongClickListener(activity.noteOnLongClickListener)
    }

    override fun getItemCount() = noteList.size

    fun swapItems(newNoteList: List<Note>) {
        val diffUtilCallback = NoteDiffUtil(noteList, newNoteList)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback)
        diffUtilResult.dispatchUpdatesTo(this)
        noteList.clear()
        noteList.addAll(newNoteList)
    }
}
