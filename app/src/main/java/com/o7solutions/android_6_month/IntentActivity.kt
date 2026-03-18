package com.o7solutions.android_6_month

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class IntentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent)

        // Initialize Buttons
        val btnSms: Button = findViewById(R.id.btnSms)
        val btnDial: Button = findViewById(R.id.btnDial)
        val btnWhatsApp: Button = findViewById(R.id.btnWhatsApp)
        val btnInstagram: Button = findViewById(R.id.btnInstagram)

        // 1. SMS Intent
        btnSms.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("smsto:1234567890")
                putExtra("sms_body", "Hi, check this out!")
            }
            startActivity(intent)
        }

        // 2. Telephone Dialer Intent
        btnDial.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:1234567890")
            }
            startActivity(intent)
        }

        // 3. WhatsApp Intent
        btnWhatsApp.setOnClickListener {
            val phoneNumber = "+1234567890" // Include country code
            val message = "Hello from my Android app!"
            val url = "https://api.whatsapp.com/send?phone=$phoneNumber&text=${Uri.encode(message)}"

            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            }
            startActivity(intent)
        }

        // 4. Instagram Intent (with fallback to Browser)
        btnInstagram.setOnClickListener {
            val username = "google"
            val appUri = Uri.parse("http://instagram.com/_u/$username")
            val browserUri = Uri.parse("http://instagram.com/$username")

            val intent = Intent(Intent.ACTION_VIEW, appUri).apply {
                setPackage("com.instagram.android")
            }

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                // If the app isn't installed, open in the web browser
                startActivity(Intent(Intent.ACTION_VIEW, browserUri))
            }
        }
    }
}