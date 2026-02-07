package com.o7solutions.android_6_month.Permissions

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.o7solutions.android_6_month.R
import com.o7solutions.android_6_month.databinding.ActivityPermissionsBinding

class PermissionsActivity : AppCompatActivity() {

    /* ---------------- SINGLE PERMISSION ---------------- */

    private val singlePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
                openAppSettings()
            }
        }

    /* ---------------- MULTIPLE PERMISSIONS ---------------- */

    private val multiplePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

            val cameraGranted = permissions[Manifest.permission.CAMERA] ?: false
            val micGranted = permissions[Manifest.permission.RECORD_AUDIO] ?: false

            if (cameraGranted && micGranted) {
                Toast.makeText(this, "All permissions granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Some permissions denied", Toast.LENGTH_SHORT).show()
                openAppSettings()
            }
        }


    private lateinit var binding: ActivityPermissionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPermissionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.singlePermission.setOnClickListener {
            requestSinglePermission()
        }


        binding.multiPermission.setOnClickListener {
            requestMultiplePermissions()
        }


    }

    /* ---------------- FUNCTIONS ---------------- */

    private fun requestSinglePermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(this, "Camera already allowed", Toast.LENGTH_SHORT).show()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                Toast.makeText(this, "Camera permission is required", Toast.LENGTH_LONG).show()
                singlePermissionLauncher.launch(Manifest.permission.CAMERA)
            }

            else -> {
                singlePermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun requestMultiplePermissions() {
        val permissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )

        val allGranted = permissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }

        if (allGranted) {
            Toast.makeText(this, "Permissions already granted", Toast.LENGTH_SHORT).show()
        } else {
            multiplePermissionLauncher.launch(permissions)
        }
    }

    private fun openAppSettings() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:$packageName")
        )
        startActivity(intent)
    }
}
