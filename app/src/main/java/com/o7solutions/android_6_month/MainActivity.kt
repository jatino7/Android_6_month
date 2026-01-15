package com.o7solutions.android_6_month

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat.enableEdgeToEdge
import androidx.core.view.WindowInsetsCompat
import com.o7solutions.android_6_month.RelativeLayout.RelativeLayoutActivity

class MainActivity : AppCompatActivity() {

//    variables declared here
    lateinit var postBtn: Button
    lateinit var et: EditText
    lateinit var tv: TextView
    var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        initializing variables
        postBtn = findViewById<Button>(R.id.postBtn)
        et = findViewById<EditText>(R.id.et1)
        tv = findViewById<TextView>(R.id.tv1)


        postBtn.setOnClickListener {
            var etText = et.text.toString()

            tv.text = etText;

            Toast.makeText(this, etText, Toast.LENGTH_SHORT).show()


            if(etText.toString() == "hello") {

                val intent = Intent(this, RelativeLayoutActivity::class.java)
                startActivity(intent)
            }
        }






    }
}