package com.amilien.prometheus.view.maps

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.fragment.findNavController
import com.amilien.prometheus.R
import com.amilien.prometheus.utils.createObserverWithError
import com.amilien.prometheus.view.BaseFragment
import com.amilien.prometheus.viewmodel.FeedViewModel
import com.amilien.prometheus.viewmodel.MapsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_maps.*

private const val BUSINESS_LOCATION_ZOOM_LEVEL = 15f

class MapsFragment : BaseFragment(R.layout.fragment_maps), OnMapReadyCallback {

    private val mapsViewModel: MapsViewModel by createViewModel()
    private val feedViewModel: FeedViewModel by createActivityViewModel()

    private var marker: Marker? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (childFragmentManager.findFragmentById(R.id.add_location_map_view) as SupportMapFragment).getMapAsync(this)
        add_location_fab.isEnabled = false
        add_location_fab.setOnClickListener {
            mapsViewModel.weather?.let { feedViewModel.saveWeather(it) }
            findNavController().popBackStack()
        }
        mapsViewModel.weatherLiveData.createObserverWithError(
            viewLifecycleOwner,
            { Toast.makeText(requireContext(), "Server responded with error: $it", Toast.LENGTH_LONG).show() },
            {
                add_location_timezone.text = mapsViewModel.placeName
                add_location_coordinates.text =
                    getString(R.string.coordinates, mapsViewModel.location.latitude, mapsViewModel.location.longitude)
                add_location_fab.isEnabled = true
            }
        )
    }

    override fun onMapReady(map: GoogleMap) {
        setupMarker(map)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mapsViewModel.location, BUSINESS_LOCATION_ZOOM_LEVEL))
        map.setOnMapClickListener { updateMarker(it) }
    }

    private fun setupMarker(map: GoogleMap) {
        if (marker == null) {
            val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(
                requireNotNull(ContextCompat.getDrawable(requireContext(), R.drawable.ic_location_marker)).toBitmap()
            )
            marker = map.addMarker(MarkerOptions().position(mapsViewModel.location).icon(bitmapDescriptor))
        } else {
            marker?.position = mapsViewModel.location
        }
        mapsViewModel.getWeather()
    }

    private fun updateMarker(latLng: LatLng) {
        mapsViewModel.location = latLng
        marker?.position = latLng
        mapsViewModel.getWeather()
    }
}
