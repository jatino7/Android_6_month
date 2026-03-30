package com.o7solutions.android_6_month.Maps

import com.o7solutions.android_6_month.R

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps) // Ensure your XML has the FragmentContainerView

        // Initialize the map fragment
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // 1. Set Default Location (e.g., New York)
        val initialLocation = LatLng(40.7128, -74.0060)

        // 2. Add a Custom Marker
        val mainMarker = mMap.addMarker(
            MarkerOptions()
                .position(initialLocation)
                .title("Starting Point")
                .snippet("Tap for more info")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .draggable(true)
        )


        // 3. Move Camera with Zoom (1.0 to 21.0 range)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 12f))

        // 4. Configure UI Settings
        with(mMap.uiSettings) {
            isZoomControlsEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }

        // 5. Handle Map Clicks (Add new marker on tap)
        mMap.setOnMapClickListener { latLng ->
            mMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title("New Marker")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
            )
            Toast.makeText(this, "Marker added at: ${latLng.latitude}", Toast.LENGTH_SHORT).show()
        }

        // 6. Handle Marker Clicks
        mMap.setOnMarkerClickListener { marker ->
            marker.showInfoWindow()
            // Return true to consume the event, false to allow default behavior
            false
        }

        // 7. Draw a Circle (e.g., a 1km radius)
        mMap.addCircle(
            CircleOptions()
                .center(initialLocation)
                .radius(1000.0) // In meters
                .strokeColor(Color.RED)
                .fillColor(0x22FF0000) // Transparent Red
                .strokeWidth(5f)
        )

        // 8. Enable "My Location" (Blue Dot)
        enableMyLocation()
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        } else {
            // Request permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, enable the blue dot
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                    mMap.isMyLocationEnabled = true
                }
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}