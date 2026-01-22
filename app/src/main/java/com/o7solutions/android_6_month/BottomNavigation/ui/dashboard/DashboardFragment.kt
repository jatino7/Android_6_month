package com.o7solutions.android_6_month.BottomNavigation.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.o7solutions.android_6_month.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    lateinit var binding : FragmentDashboardBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.appBar.title = "Navbar"
        binding.appBar.subtitle = "Green"

        binding.appBar.setNavigationOnClickListener {
            Toast.makeText(requireContext(), "Email clicked", Toast.LENGTH_SHORT).show()
        }
    }


}