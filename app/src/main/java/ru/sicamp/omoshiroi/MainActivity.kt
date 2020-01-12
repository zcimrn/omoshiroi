package ru.sicamp.omoshiroi

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var noteBase: NoteBase
    lateinit var noteAdapter: NoteAdapter
    lateinit var noteOnClickListener: NoteOnClickListener
    lateinit var noteOnLongClickListener: NoteOnLongClickListener
    lateinit var buttonOnClickListener: ButtonOnClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MyLogs", "MainActivity onCreate begin")

        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

        noteBase = (application as MainApplication).noteBase

        noteAdapter = NoteAdapter(this)

        findViewById<RecyclerView>(R.id.recyclerView).apply {
            adapter = noteAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        noteOnClickListener = NoteOnClickListener(this)
        noteOnLongClickListener = NoteOnLongClickListener(this)
        buttonOnClickListener = ButtonOnClickListener(this)

        findViewById<FloatingActionButton>(R.id.newNoteButton).apply {
            setOnClickListener(buttonOnClickListener)
        }

        findViewById<ImageButton>(R.id.informationButton).apply {
            setOnClickListener(buttonOnClickListener)
        }

        tempFunction()

        Log.d("MyLogs", "MainActivity onCreate end")
    }

    private fun tempFunction() {
        if (Build.VERSION.SDK_INT >= 26) {
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(
                    NotificationChannel(
                        "ru.sicamp.omoshiroi",
                        "ru.sicamp.omoshiroi",
                        NotificationManager.IMPORTANCE_DEFAULT

                    )
                )
        }
    }

    override fun onStart() {
        Log.d("MyLogs", "MainActivity onStart begin")

        super.onStart()

        Log.d("MyLogs", "MainActivity onStart end")
    }

    override fun onResume() {
        Log.d("MyLogs", "MainActivity onResume begin")

        super.onResume()

        noteAdapter.swapItems(noteBase.getAllNotes())

        Log.d("MyLogs", "MainActivity onResume end")
    }

    override fun onStop() {
        Log.d("MyLogs", "MainActivity onStop begin")

        super.onStop()

        Log.d("MyLogs", "MainActivity onStop end")
    }

    override fun onRestart() {
        Log.d("MyLogs", "MainActivity onRestart begin")

        super.onRestart()

        Log.d("MyLogs", "MainActivity onRestart end")
    }

    override fun onDestroy() {
        Log.d("MyLogs", "MainActivity onDestroy begin")

        super.onDestroy()

        Log.d("MyLogs", "MainActivity onDestroy end")
    }
}
