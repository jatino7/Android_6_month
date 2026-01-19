package com.o7solutions.android_6_month.BottomNavigation

import android.os.Bundle

import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.o7solutions.android_6_month.R
//import android.R
import com.o7solutions.android_6_month.databinding.ActivityBottomNavigationBinding

class BottomNavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        bottom navigation
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.bottom)
        navView.setupWithNavController(navController)
    }
}