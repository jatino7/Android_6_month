package com.o7solutions.android_6_month.Recycler_View

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.o7solutions.android_6_month.R
import com.o7solutions.android_6_month.databinding.ActivityMainBinding
import com.o7solutions.android_6_month.databinding.ActivityRecyclerViewBinding

class RecyclerViewActivity : AppCompatActivity() {


    lateinit var adapter: StudentAdapter

    var studentList = ArrayList<Student>()
    lateinit var binding: ActivityRecyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        adapter = StudentAdapter(studentList)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter


        addStudentData()

    }


    fun addStudentData() {

        studentList.add(Student(id = 1,name = "Jatin",age = 20,marks = 100))
        studentList.add(Student(id = 1,name = "Jatin Mehmi",age = 20,marks = 100))
        studentList.add(Student(id = 1,name = "Jatin",age = 20,marks = 100))
        studentList.add(Student(id = 1,name = "Jatin",age = 20,marks = 100))
        studentList.add(Student(id = 1,name = "Jatin",age = 20,marks = 100))
        studentList.add(Student(id = 1,name = "Jatin",age = 20,marks = 100))
        studentList.add(Student(id = 1,name = "Jatin",age = 20,marks = 100))
        studentList.add(Student(id = 1,name = "Jatin",age = 20,marks = 100))

        adapter.notifyDataSetChanged()




    }
}