package com.o7solutions.android_6_month.Firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.o7solutions.android_6_month.BottomNavigation.BottomNavigationActivity
import com.o7solutions.android_6_month.R
import com.o7solutions.android_6_month.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {


    lateinit var binding: ActivityRegisterBinding

    var auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.registerBTN.setOnClickListener {


            val email = binding.emailET.text.toString()
            val password = binding.passwordET.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(this, "please fill email", Toast.LENGTH_SHORT).show()
            } else if(password.isEmpty()) {
                Toast.makeText(this, "please fill password", Toast.LENGTH_SHORT).show()
            } else {

                auth.createUserWithEmailAndPassword(email,password)
                    .addOnSuccessListener {
                        Toast.makeText(this, "User created successfully!", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, BottomNavigationActivity::class.java)
                        startActivity(intent)
                        finish()

                    }
                    .addOnFailureListener { e->
                        Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
            }

        }



    }
}