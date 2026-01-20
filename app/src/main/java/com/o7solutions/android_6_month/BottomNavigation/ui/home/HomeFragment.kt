package com.o7solutions.android_6_month.BottomNavigation.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.o7solutions.android_6_month.R
import com.o7solutions.android_6_month.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            alertDialog.setOnClickListener {


                AlertDialog.Builder(requireContext())
                    .setTitle("Delete Item")
                    .setMessage("Are you sure you want to delete this?")
                    .setPositiveButton("Yes") { dialog, _ ->

                        Toast.makeText(requireContext(), "Yes button clicked", Toast.LENGTH_SHORT)
                            .show()
                        dialog.dismiss()
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        Toast.makeText(requireContext(), "No button clicked", Toast.LENGTH_SHORT)
                            .show()

                        dialog.dismiss()
                    }
                    .show()

            }

            customAlertDialog.setOnClickListener {
                val dialogView = layoutInflater.inflate(R.layout.dialog_custom_alert,null)

                val dialog = AlertDialog.Builder(requireContext())
                    .setView(dialogView)
                    .create()


                val et = dialogView.findViewById<EditText>(R.id.etConfirm)
                dialogView.findViewById<Button>(R.id.btnCancel).setOnClickListener {

                    var text = et.text.toString()
                    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }

                dialogView.findViewById<Button>(R.id.btnConfirm).setOnClickListener {
                    dialog.dismiss()
                }

                dialog.show()

            }
        }
    }


}