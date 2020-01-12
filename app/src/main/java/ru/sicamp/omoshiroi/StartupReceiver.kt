package ru.sicamp.omoshiroi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class StartupReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("MyLogs", "StartupReceiver onReceive begin")

//        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
//            context.startService(Intent(context, StartupService::class.java))
//        }

        Log.d("MyLogs", "StartupReceiver onReceive end")
    }
}