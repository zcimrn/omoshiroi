package ru.sicamp.omoshiroi

import android.app.Application
import android.content.Context
import android.util.Log

class MainApplication : Application() {
    lateinit var noteBase: NoteBase

    override fun onCreate() {
        Log.d("MyLogs", "MainApplication onCreate begin")

        super.onCreate()

        noteBase = NoteBase(baseContext)

        Log.d("MyLogs", "MainApplication onCreate end")
    }
}