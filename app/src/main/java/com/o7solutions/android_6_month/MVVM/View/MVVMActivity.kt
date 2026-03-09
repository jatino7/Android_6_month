package com.o7solutions.android_6_month.MVVM.View

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.o7solutions.android_6_month.MVVM.Model.ApiInterface
import com.o7solutions.android_6_month.MVVM.Model.CommentRepository
import com.o7solutions.android_6_month.MVVM.Model.RetrofitInstance
import com.o7solutions.android_6_month.MVVM.ViewModel.CommentViewModel
import com.o7solutions.android_6_month.R
import com.o7solutions.android_6_month.databinding.ActivityMvvmactivityBinding

class MVVMActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMvvmactivityBinding
    private lateinit var viewModel: CommentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mvvmactivity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val apiService = RetrofitInstance.api
        val repository = CommentRepository(apiService)
        val factory = CommentViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory)[CommentViewModel::class.java]


        viewModel.comments.observe(this) { list ->
            Log.d("List ",list.toString())
        }

        viewModel.fetchComments()
    }
}


class CommentViewModelFactory(private val repository: CommentRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentViewModel(repository) as T
    }
}