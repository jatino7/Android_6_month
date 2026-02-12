package com.o7solutions.android_6_month.Firebase

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.o7solutions.android_6_month.R
import com.o7solutions.android_6_month.Recycler_View.StudentAdapter
import com.o7solutions.android_6_month.databinding.ActivityRecyclerDbactivityBinding

class RecyclerDBActivity : AppCompatActivity() {


    lateinit var binding: ActivityRecyclerDbactivityBinding
    lateinit var adapter: RealDBAdapter
    var listOfUsers = arrayListOf<User>()
    var db = Firebase.database.reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivityRecyclerDbactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


//        adapter initialized
        adapter = RealDBAdapter(listOfUsers)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter



        getUserData()
    }


    fun getUserData() {

        var ref = db.child("users")

        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                for(userInList in snapshot.children) {

                    var user = userInList.getValue(User::class.java)

                    user?.let { listOfUsers.add(it) }
                }


                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

                Log.d("RealDBActivity",error.message)
            }

        })

    }
}