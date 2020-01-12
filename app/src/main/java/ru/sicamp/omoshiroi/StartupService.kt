package ru.sicamp.omoshiroi

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class StartupService : Service() {

    override fun onBind(p0: Intent?): IBinder? {
        Log.d("MyLogs", "StartupService onBind begin")
        Log.d("MyLogs", "StartupService onBind end")
        return null!!
    }

    override fun onCreate() {
        Log.d("MyLogs", "StartupService onCreate begin")

        super.onCreate()

        Log.d("MyLogs", "StartupService onCreate end")
    }
}
