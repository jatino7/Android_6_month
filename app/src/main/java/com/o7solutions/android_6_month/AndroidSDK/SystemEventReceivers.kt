package com.o7solutions.android_6_month.AndroidSDK
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.util.Log
import android.widget.Toast

class SystemEventReceivers(
    private val onUpdate: (String) -> Unit
) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {

            Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                val state = intent.getBooleanExtra("state", false)
                onUpdate("Airplane Mode: ${if (state) "ON" else "OFF"}")
            }

            "android.net.conn.CONNECTIVITY_CHANGE" -> {
                onUpdate("Network status changed")
            }

            Intent.ACTION_BATTERY_CHANGED -> {
                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                val percent = (level * 100 / scale.toFloat()).toInt()
                onUpdate("Battery: $percent%")
            }

            "com.example.ACTION_ALARM_TRIGGERED" -> {
                Toast.makeText(context, "ALARM RINGING!", Toast.LENGTH_LONG).show()
            }

            Intent.ACTION_BOOT_COMPLETED -> {
                Log.d("Boot", "Device Rebooted Successfully")
            }
        }
    }
}

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d("Boot", "System restarted, app logic triggered.")
        }
    }
}