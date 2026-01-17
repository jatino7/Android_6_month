package com.o7solutions.android_6_month.ConstraintLayout

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.o7solutions.android_6_month.R
import com.o7solutions.android_6_month.databinding.ActivityConstraintBinding

class ConstraintActivity : AppCompatActivity() {

    lateinit var binding: ActivityConstraintBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityConstraintBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.btn2.setOnClickListener {
            Toast.makeText(this, "btn2", Toast.LENGTH_SHORT).show()
        }
    }
}