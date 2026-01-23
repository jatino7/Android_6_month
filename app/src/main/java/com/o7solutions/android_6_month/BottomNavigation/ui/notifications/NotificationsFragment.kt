package com.o7solutions.android_6_month.BottomNavigation.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.o7solutions.android_6_month.R
import com.o7solutions.android_6_month.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    lateinit var binding: FragmentNotificationsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        list of spinner items
        val options = arrayListOf<String>("Jatin","Mehmi","Karan","Arshdeep")

        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,options)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerAtTop.adapter = adapter

        binding.spinnerAtTop.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                p1: View?,
                position: Int,
                p3: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                Toast.makeText(requireContext(), selectedItem, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }


        binding.navBtn.setOnClickListener {


            findNavController().navigate(R.id.profileFragment)
        }
    }


}