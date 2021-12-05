package com.example.moviebox.ui.maps

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.moviebox.R
import com.example.moviebox.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class MapsFragment : Fragment() {

    private lateinit var map: GoogleMap
    private var location: Location? = null
    private var locationGranted = false
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private val handlerThread = HandlerThread("AddressHandlerThread")
    private var handler: Handler

    init {
        handlerThread.start()
        handler = Handler(handlerThread.looper)
    }

    // проверка разрешения на получение геоданных
    @SuppressLint("MissingPermission")
    private val getLocationPermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                map.isMyLocationEnabled = true
                getLocation()
                locationGranted = true
            } else {
                Toast.makeText(
                    context,
                    getString(R.string.need_permissions_to_get_location),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    // слушатель изменений геолокации (получаем координаты локации и передаем в метод определения адреса)
    private val onLocationListener = LocationListener { thisLocation ->
        location = thisLocation
        getAddressAsync(thisLocation)
    }

    // колбэк, вызываемый как только карта загрузилась
    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        map.uiSettings.isMyLocationButtonEnabled = true  // разрешаем отображение кнопки центрирования по метоположению
        map.uiSettings.isZoomControlsEnabled = true      // разрешаем отображение кнопок управления зумом

        if (locationGranted) {
            map.isMyLocationEnabled = true
            getLocation()
        } else {
            checkGetLocationPermission()                 // проверяем наличие разрешения на получение геоданных
        }

        val initialPlace =
            LatLng(location?.latitude ?: 56.0, location?.longitude ?: 38.0)
        val marker = map.addMarker(MarkerOptions().position(initialPlace))

        map.moveCamera(CameraUpdateFactory.newLatLng(initialPlace))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        // проверяем наличие разрешения на получение геоданных
        checkGetLocationPermission()
        if (locationGranted) {
            getLocation()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        handlerThread.quit()
    }

    private fun checkGetLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationGranted = true
        } else {
            getLocationPermissionResult.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        activity?.let { context ->
            // получаем менеджер геолокаций
            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                val provider = locationManager.getProvider(LocationManager.GPS_PROVIDER)
                provider?.let {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        REFRESH_PERIOD,
                        MINIMAL_DISTANCE,
                        onLocationListener
                    )
                }
            } else {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (location == null) {
                    showDialog(R.string.gps_turned_off, R.string.last_location_unknown)
                } else {
                    getAddressAsync(location!!)
                    showDialog(R.string.gps_turned_off, R.string.last_known_location)
                }
            }
        }
    }

    private fun getAddressAsync(location: Location) {
        val geoCoder = Geocoder(context)
        Thread {
            try {
                val address = geoCoder.getFromLocation(location.latitude, location.longitude, 1)
                binding.root.post {
                    Toast.makeText(requireContext(), address[0].getAddressLine(0), Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun showDialog(titleId: Int, messageId: Int) {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(titleId))
                .setMessage(getString(messageId))
                .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }


    companion object {

        private const val REFRESH_PERIOD = 1000L
        private const val MINIMAL_DISTANCE = 100f

        fun newInstance() = MapsFragment()
    }
}