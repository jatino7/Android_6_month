package com.o7solutions.android_6_month.MLKit
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.ViewfinderView
import com.o7solutions.android_6_month.R

class BarcodeScanningActivity : AppCompatActivity(), DecoratedBarcodeView.TorchListener {

    private lateinit var capture: CaptureManager
    private lateinit var barcodeScannerView: DecoratedBarcodeView
    private lateinit var btnSwitchFlashlight: Button
    private var isFlashOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_code_scanniing)

        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner)
        btnSwitchFlashlight = findViewById(R.id.btn_switch_flashlight)

        // Set up Torch (Flashlight) listener
        barcodeScannerView.setTorchListener(this)

        // Initialize the CaptureManager to handle barcode logic
        capture = CaptureManager(this, barcodeScannerView)

        
        capture.initializeFromIntent(intent, savedInstanceState)

        // Starts the camera and looks for barcodes
        capture.decode()

        // Flashlight toggle logic
        btnSwitchFlashlight.setOnClickListener {
            if (isFlashOn) {
                barcodeScannerView.setTorchOff()
            } else {
                barcodeScannerView.setTorchOn()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        capture.onResume()
    }

    override fun onPause() {
        super.onPause()
        capture.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture.onSaveInstanceState(outState)
    }



    override fun onTorchOn() {
        isFlashOn = true
        btnSwitchFlashlight.text = "Turn Off Flash"
    }

    override fun onTorchOff() {
        isFlashOn = false
        btnSwitchFlashlight.text = "Turn On Flash"
    }
}