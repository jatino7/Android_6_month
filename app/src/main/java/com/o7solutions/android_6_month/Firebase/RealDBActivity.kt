package com.o7solutions.android_6_month.Firebase

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.o7solutions.android_6_month.R

class RealDBActivity : AppCompatActivity() {


    val database = Firebase.database.reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_real_dbactivity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        writeNewUser("1234","Jatin","jatin@gmail.com")
        writeNewUser("12345","Tanish","tanish@gmail.com")
        writeNewUser("123456","Deepali","deepali@gmail.com")
    }

    fun writeNewUser(userId: String, name: String, email: String) {
        val user = User(name, email)

        // This creates a node: users -> userId -> {username: "...", email: "..."}
        database.child("users").child(userId).setValue(user)
            .addOnSuccessListener {
            }
            .addOnFailureListener {
            }
    }

    fun getUserData(userId: String) {
        val userRef = database.child("users").child(userId)

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Convert the snapshot back into your User object
                val user = snapshot.getValue(User::class.java)

                if (user != null) {
                    println("User name: ${user.username}, Email: ${user.email}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors here
                Log.w("Firebase", "loadPost:onCancelled", error.toException())
            }


        })
    }
}