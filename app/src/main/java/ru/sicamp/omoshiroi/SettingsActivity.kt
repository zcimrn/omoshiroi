package ru.sicamp.omoshiroi

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MyLogs", "SettingsActivity onCreate begin")

        Log.d("MyLogs", "settings onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        Log.d("MyLogs", "SettingsActivity onCreate end")
    }
}