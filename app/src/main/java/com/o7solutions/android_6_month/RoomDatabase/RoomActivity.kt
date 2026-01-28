package com.o7solutions.android_6_month.RoomDatabase

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.o7solutions.android_6_month.R
import com.o7solutions.android_6_month.databinding.ActivityRoomBinding
import kotlinx.coroutines.launch

class RoomActivity : AppCompatActivity() {


    private lateinit var binding: ActivityRoomBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val db = AppDatabase.getDatabase(this)
        val userDao = db.userDao()


        lifecycleScope.launch {
            var userList = userDao.getAllUSer()
            Log.d("List of Users",userList.toString())
        }



        binding.submitBtn.setOnClickListener {
            var name = binding.name.text.toString()
            var age = binding.age.text.toString().toInt()

            lifecycleScope.launch {
                userDao.insertUser(User(name = name,age = age))
            }
        }



    }
}