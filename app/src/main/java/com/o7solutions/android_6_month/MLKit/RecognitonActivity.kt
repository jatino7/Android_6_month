package com.o7solutions.android_6_month.MLKit

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceLandmark
import com.o7solutions.android_6_month.databinding.ActivityRecognitonBinding
import java.util.concurrent.Executors
import kotlin.math.sqrt

class RecognitionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecognitonBinding
    private val gson = Gson()
    private val prefs by lazy { getSharedPreferences("FaceData", MODE_PRIVATE) }

    // We use a FloatArray to store the relative positions of facial landmarks
    private var currentFaceEmbedding: FloatArray? = null

    private val faceDetector = FaceDetection.getClient(
        FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL) // Required to get coordinates
            .build()
    )

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            startCamera() // Your function to initialize CameraX
        } else {
            Toast.makeText(this, "Camera permission is required to use ML Kit", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecognitonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startCamera()

        checkCameraPermission()

        // --- BUTTON: SAVE LOCALLY ---
        binding.btnSave.setOnClickListener {
            currentFaceEmbedding?.let { embedding ->
                prefs.edit().putString("saved_face", gson.toJson(embedding)).apply()
                binding.tvStatus.text = "Status: Face Saved Locally!"
            } ?: run { binding.tvStatus.text = "Status: No Face Detected" }
        }

        // --- BUTTON: MATCH ---
        binding.btnMatch.setOnClickListener {
            val savedJson = prefs.getString("saved_face", null)
            if (savedJson != null && currentFaceEmbedding != null) {
                val savedEmbedding = gson.fromJson(savedJson, FloatArray::class.java)
                val distance = calculateDistance(currentFaceEmbedding!!, savedEmbedding)

                // Threshold for raw landmark distance is much higher than TFLite (try 40.0 - 100.0)
                if (distance < 50.0f) {
                    binding.tvStatus.text = "Status: MATCH! (Dist: ${"%.2f".format(distance)})"
                } else {
                    binding.tvStatus.text = "Status: NO MATCH (Dist: ${"%.2f".format(distance)})"
                }
            } else {
                binding.tvStatus.text = "Status: Register a face first!"
            }
        }

        binding.btnClear.setOnClickListener {
            prefs.edit().remove("saved_face").apply()
            binding.tvStatus.text = "Status: Data Cleared"
        }
    }


    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED -> {
                startCamera()
            }
            else -> {
                // 3. Ask for permission
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.previewView.surfaceProvider)
            }

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor()) { imageProxy ->
                processImageProxy(imageProxy)
            }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, CameraSelector.DEFAULT_FRONT_CAMERA, preview, imageAnalysis)
            } catch (e: Exception) {
                Log.e("RecognitionActivity", "Camera binding failed", e)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun processImageProxy(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image ?: return
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        faceDetector.process(image)
            .addOnSuccessListener { faces ->
                if (faces.isNotEmpty()) {
                    // Create a pseudo-embedding from landmarks (Eyes, Nose, Mouth positions)
                    currentFaceEmbedding = extractLandmarksAsEmbedding(faces[0])
                } else {
                    currentFaceEmbedding = null
                }
            }
            .addOnCompleteListener { imageProxy.close() }
    }

    /**
     * Converts ML Kit Face Landmarks into a FloatArray of coordinates.
     * This replaces the TFLite embedding logic.
     */
    private fun extractLandmarksAsEmbedding(face: com.google.mlkit.vision.face.Face): FloatArray {
        val landmarkList = mutableListOf<Float>()
        val types = intArrayOf(
            FaceLandmark.LEFT_EYE, FaceLandmark.RIGHT_EYE,
            FaceLandmark.NOSE_BASE, FaceLandmark.MOUTH_LEFT,
            FaceLandmark.MOUTH_RIGHT, FaceLandmark.LEFT_CHEEK,
            FaceLandmark.RIGHT_CHEEK
        )

        for (type in types) {
            val landmark = face.getLandmark(type)
            landmark?.position?.let {
                // We store X and Y relative to the bounding box to make it more scale-invariant
                landmarkList.add(it.x - face.boundingBox.left)
                landmarkList.add(it.y - face.boundingBox.top)
            }
        }
        return landmarkList.toFloatArray()
    }

    private fun calculateDistance(emb1: FloatArray, emb2: FloatArray): Float {
        if (emb1.size != emb2.size) return Float.MAX_VALUE
        var sum = 0f
        for (i in emb1.indices) {
            val diff = emb1[i] - emb2[i]
            sum += diff * diff
        }
        return sqrt(sum.toDouble()).toFloat()
    }
}