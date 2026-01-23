package com.o7solutions.android_6_month.RelativeLayout

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.o7solutions.android_6_month.R

class RelativeLayoutActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_relative_layout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        getting data from intent
        val nameFromIntent  = intent.getStringExtra("name")
        val ageFromIntent = intent.getIntExtra("age",0)
        val marksFromIntent = intent.getIntExtra("marks",0)

        Toast.makeText(this, "$nameFromIntent $ageFromIntent $marksFromIntent", Toast.LENGTH_SHORT).show()
    }
}