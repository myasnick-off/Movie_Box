package com.example.moviebox.ui.maps

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
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
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.lang.Exception

class MapsFragment : Fragment() {

    private lateinit var map: GoogleMap
    private var location: Location? = null
    private var locationGranted = false
    private var marker: Marker? = null
    private var addressLine: String? = null

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
        val currentPlace =
            LatLng(location?.latitude ?: 15.7, location?.longitude ?: 37.8)
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPlace, 15f))
    }

    // колбэк, вызываемый как только карта загрузилась
    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        map.uiSettings.isMyLocationButtonEnabled =
            true  // разрешаем отображение кнопки центрирования по метоположению
        map.uiSettings.isZoomControlsEnabled =
            true      // разрешаем отображение кнопок управления зумом

        if (locationGranted) {
            map.isMyLocationEnabled = true
        } else {
            checkGetLocationPermission()                 // проверяем наличие разрешения на получение геоданных
        }

        val initialPlace = LatLng(55.7, 37.8)
        map.moveCamera(CameraUpdateFactory.newLatLng(initialPlace))

        // обработка долгого нажатия на область карты
        map.setOnMapLongClickListener { latLng ->
            setNewMarker(latLng)                     // устанавливаем маркер и выводим диалог с адресом
        }
        // обработка короткого нажатия на область карты
        map.setOnMapClickListener {
            marker?.remove()                        // удаляем маркер
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        // проверяем наличие разрешения на получение геоданных
        checkGetLocationPermission()
        if (locationGranted) {
            getLocation()
        }

        // обработка нажатий кнопок меню
        mapToolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_normal -> {
                    map.mapType = GoogleMap.MAP_TYPE_NORMAL
                    true
                }
                R.id.action_satellite -> {
                    map.mapType = GoogleMap.MAP_TYPE_SATELLITE
                    true
                }
                R.id.action_hybrid -> {
                    map.mapType = GoogleMap.MAP_TYPE_HYBRID
                    true
                }
                else -> false
            }
        }

        // инициализация меню поиска
        val searchItem = mapToolBar.menu.findItem(R.id.action_map_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // обработка события при завершении ввода текста
            override fun onQueryTextSubmit(searchText: String?): Boolean {
                searchByAddress(searchText, root)
                return true
            }
            // обработка события при вводе текста
            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
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

    // метод поиска адреса по введенному тексту
    private fun searchByAddress(searchText: String?, view: View) {
        val geocoder = Geocoder(requireContext())
        Thread {
            try {
                val addresses = geocoder.getFromLocationName(searchText, 10)
                if (addresses.isNotEmpty() && searchText != null) {
                    view.post {
                        goToAddress(addresses, searchText)
                    }
                } else {
                    view.post {
                        showAddressDialog(getString(R.string.cant_find))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    // метод установки маркера по найденному адресу
    private fun goToAddress(addresses: MutableList<Address>, searchText: String) {
        val latLng = LatLng(addresses[0].latitude, addresses[0].longitude)
        setMarker(latLng, searchText)
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
    }

    // метод установки нового маркера на карте с указанием адреса
    private fun setNewMarker(latLng: LatLng) {
        marker?.remove()
        getAddressAsync(latLng)
        marker = map.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(addressLine)
        )
    }

    // метод установки нового маркера на карте с указанием адреса
    private fun setMarker(latLng: LatLng, addressText: String) {
        marker?.remove()
        marker = map.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(addressText)
        )
    }

    // метод нахождения адреса по геолокации (выводит тост с адресом)
    private fun getAddressAsync(location: Location) {
        val geoCoder = Geocoder(context)
        Thread {
            try {
                val address = geoCoder.getFromLocation(location.latitude, location.longitude, 1)
                binding.root.post {
                    Toast.makeText(
                        requireContext(),
                        address[0].getAddressLine(0),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    // метод нахождения адреса по широте и долготе (выводит диалоговое окно с адресом)
    private fun getAddressAsync(latLng: LatLng) {
        val geoCoder = Geocoder(context)
        Thread {
            try {
                val address = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                binding.root.post {
                    addressLine = address[0].getAddressLine(0)
                    showAddressDialog(address[0].getAddressLine(0))
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    // метод отображения диалога
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

    // метод отображения диалога с адресом
    private fun showAddressDialog(address: String) {
        activity?.let {
            AlertDialog.Builder(it)
                .setMessage(address)
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