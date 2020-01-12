package ru.sicamp.omoshiroi

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class HideStartupService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        Log.d("MyLogs", "HideStartupService onBind begin")
        Log.d("MyLogs", "HideStartupService onBind end")
        return null!!
    }

    override fun onCreate() {
        Log.d("MyLogs", "HideStartupService onCreate begin")

        super.onCreate()

        Log.d("MyLogs", "HideStartupService onCreate end")
    }
}