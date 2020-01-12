package ru.sicamp.omoshiroi

import androidx.recyclerview.widget.DiffUtil

class NoteDiffUtil(private val oldNoteList: List<Note>, private val newNoteList: List<Note>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldNoteList.size

    override fun getNewListSize(): Int = newNoteList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldNoteList[oldItemPosition]
        val newNote = newNoteList[newItemPosition]
        return oldNote.id == newNote.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldNoteList[oldItemPosition]
        val newNote = newNoteList[newItemPosition]
        return (
            oldNote.id == newNote.id &&
            oldNote.title == newNote.title &&
            oldNote.text == newNote.text &&
            oldNote.isPinned == newNote.isPinned
        )
    }

}