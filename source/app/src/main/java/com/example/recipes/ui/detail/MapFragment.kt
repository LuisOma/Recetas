package com.example.recipes.ui.detail

import android.os.Bundle
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.recipes.base.BaseFragment
import com.example.recipes.databinding.FragmentMapBinding
import com.example.recipes.ui.util.Constants.Companion.LAT
import com.example.recipes.ui.util.Constants.Companion.LON


class MapFragment : BaseFragment<FragmentMapBinding>(FragmentMapBinding::inflate) {
    lateinit var mapView: MapView
    lateinit var googleMap: GoogleMap
    private var lat: Double = 0.0
    private var lon: Double = 0.0

    override fun initComponents(savedInstanceState: Bundle?) {
        arguments?.let {
            lat = it.getDouble(LAT)
            lon = it.getDouble(LON)
        }
        mapView = binding.mapFragment
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { googleMap ->
            this.googleMap = googleMap
            val markerOptions = MarkerOptions()
            val latLng = LatLng(lat, lon)
            markerOptions.position(latLng)
            markerOptions.title("Creación")
            googleMap.addMarker(markerOptions)
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 5f)
            googleMap.moveCamera(cameraUpdate)
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
