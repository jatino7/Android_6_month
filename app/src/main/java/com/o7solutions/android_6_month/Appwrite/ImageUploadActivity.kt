package com.o7solutions.android_6_month.Appwrite

import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.o7solutions.android_6_month.R
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class ImageUploadActivity : AppCompatActivity() {

// bucketID = 6996d375001a97008da8
    private val appwriteManager by lazy { AppwriteManager.getInstance(applicationContext) }

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri->


        if(uri != null) {

            val file = uriToFile(uri)

            if(file != null) {

//                here i want to upload image

                startUpload(file)
            }

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_image_upload)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }


    fun startUpload(file: File) {
        lifecycleScope.launch {

            try {
                val bucketID = "6996d375001a97008da8"

                var result = appwriteManager.uploadImage(bucketID,file)

                println(result)
            } catch (e: Exception) {
                println(e.toString())
            }
        }
    }

    private fun uriToFile(uri: Uri): File {

        val inputStream = contentResolver.openInputStream(uri)


        var tempFile = File(cacheDir,"image.jpg")
        var outputStream = FileOutputStream(tempFile)

        inputStream?.copyTo(outputStream)

        inputStream?.close()
        outputStream.close()


        return tempFile



    }

}