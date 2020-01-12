package ru.sicamp.omoshiroi

class Note {
    var id = 0
    var title = ""
    var text = ""
    var isPinned = false

    fun copy(): Note {
        val note = Note()
        note.id = id
        note.title = title
        note.text = text
        note.isPinned = isPinned
        return note
    }


}