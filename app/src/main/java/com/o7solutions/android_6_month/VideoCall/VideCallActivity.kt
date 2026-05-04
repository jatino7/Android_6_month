package com.o7solutions.android_6_month.VideoCall

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.o7solutions.android_6_month.R
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallConfig
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallFragment

class VideCallActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vide_call)

        val appID: Long = 1683641312
        val appSign = "559f6f9a3837515eb008fbb287e1b7e7be16076879e9305b80abdb684d55fbe2"

        val userID = "user_${System.currentTimeMillis()}" // unique user
        val userName = "Jatin"

        val etCallId = findViewById<EditText>(R.id.etCallId)
        val btnStartCall = findViewById<Button>(R.id.btnStartCall)

        btnStartCall.setOnClickListener {

            val callID = etCallId.text.toString().trim()

            if (callID.isEmpty()) {
                Toast.makeText(this, "Enter Call ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val config = ZegoUIKitPrebuiltCallConfig.oneOnOneVideoCall()

            val fragment = ZegoUIKitPrebuiltCallFragment.newInstance(
                appID,
                appSign,
                userID,
                userName,
                callID,
                config
            )

            supportFragmentManager.beginTransaction()
                .replace(R.id.callContainer, fragment)
                .commit()
        }
    }
}