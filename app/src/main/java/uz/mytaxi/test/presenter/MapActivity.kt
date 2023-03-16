package uz.mytaxi.test.presenter

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.logo.logo
import com.mapbox.maps.plugin.scalebar.scalebar
import com.mapbox.navigation.utils.internal.toPoint
import dagger.hilt.android.AndroidEntryPoint
import uz.mytaxi.test.R
import uz.mytaxi.test.data.model.LocationModel
import uz.mytaxi.test.databinding.MapActivityBinding
import uz.mytaxi.test.presenter.service.LocationService
import uz.mytaxi.test.presenter.viewmodel.MapViewModel
import uz.mytaxi.test.presenter.viewmodel.MapViewModelImpl
import uz.mytaxi.test.util.hasLocationPermission
import java.util.*


@AndroidEntryPoint
class MapActivity : AppCompatActivity(R.layout.map_activity) {
    private val binding by viewBinding(MapActivityBinding::bind)
    private val viewModel: MapViewModel by viewModels<MapViewModelImpl>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var zoom = 16.0

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_activity)
        requestPermission()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.mapView.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS
        ) {
            moveToCurrentLocation()
        }

        binding.plus.setOnClickListener {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let { loc ->
                    binding.mapView.getMapboxMap().flyTo(cameraOptions {
                        center(Point.fromLngLat(loc.longitude, loc.latitude))
                        zoom += 1
                        zoom(zoom)
                    }, mapAnimationOptions {
                        duration(100)
                    })
                }
            }
        }

        binding.myLocation.setOnClickListener {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let { loc ->
                    binding.mapView.getMapboxMap().flyTo(cameraOptions {
                        center(Point.fromLngLat(loc.longitude, loc.latitude))
                        zoom(16.0)
                    }, mapAnimationOptions {
                        duration(4000)
                    })
                }
            }
        }

        binding.minus.setOnClickListener {
            binding.mapView.getMapboxMap().flyTo(cameraOptions {
                zoom -= 1.0
                zoom(zoom)
            }, mapAnimationOptions {
                duration(100)
            })
        }


        binding.mapView.attribution.updateSettings {
            enabled = true
        }
        binding.mapView.logo.updateSettings {
            enabled = false
        }
        binding.mapView.scalebar.updateSettings {
            enabled = false
        }
        binding.mapView.compass.updateSettings {
            enabled = false
        }

    }

    override fun onResume() {
        notification()
        super.onResume()
    }

    private fun notification() {
        if (this.hasLocationPermission()) {
            Intent(applicationContext, LocationService::class.java).apply {
                action = LocationService.ACTION_START
                startService(this)
            }
        }
    }

    override fun onDestroy() {
        Intent(applicationContext, LocationService::class.java).apply {
            action = LocationService.ACTION_STOP
            startService(this)
        }
        super.onDestroy()
    }


    @SuppressLint("MissingPermission")
    fun requestPermission() {

        ActivityCompat.requestPermissions(
            this@MapActivity, arrayOf(
                ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION
            ), 0
        )
    }

    @SuppressLint("MissingPermission")
    private fun moveToCurrentLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let { loc ->
                binding.mapView.getMapboxMap().flyTo(cameraOptions {
                    createMapMarker(location.toPoint())
                    center(Point.fromLngLat(loc.longitude, loc.latitude))
                    zoom(zoom)
                }, mapAnimationOptions {
                    duration(4000)
                })
                viewModel.setLocation(LocationModel(0, loc.toString()))
            }
        }
    }

    private fun createMapMarker(point: Point) {
        val annotationApi = binding.mapView.annotations
        val pointAnnotationManager = annotationApi.createPointAnnotationManager()
        val pointAnnotationOptions: PointAnnotationOptions =
            PointAnnotationOptions().withPoint(point)
                .withIconImage(ContextCompat.getDrawable(this, R.drawable.ic_car)!!.toBitmap())
        pointAnnotationManager.create(pointAnnotationOptions)
    }
}