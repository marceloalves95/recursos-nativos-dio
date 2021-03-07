package chellotech.br.recursosnativosdio.views

import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import chellotech.br.recursosnativosdio.R
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private lateinit var lastLocation:Location
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private var locationUpdateState = false

    companion object{

        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val REQUEST_CHECK_SETTINGS = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {


            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)

                lastLocation = p0.lastLocation
                placeMarkerOnMap(LatLng(lastLocation.latitude, lastLocation.longitude))

            }
        }
        createLocationRequest()
    }

    override fun onMapReady(googleMap: GoogleMap) {

        map = googleMap


        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMapClickListener { this }

        setUpMap()

        /*
        // Add a marker in Sydney and move the camera
        val myPlace = LatLng(40.73, -73.99)
        map.addMarker(MarkerOptions().position(myPlace).title("Minha cidade preferida"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myPlace,12.0f))
        */

    }

    private fun setUpMap(){

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)

            return


        }

        map.isMyLocationEnabled = true

        //map.mapType = GoogleMap.MAP_TYPE_SATELLITE

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->


            if (location != null){

                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLng)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12.0f))

            }

        }


    }



    private fun placeMarkerOnMap(location: LatLng){

        val markerOptions = MarkerOptions().position(location)
        //markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)))
        val titleStr = getAddress(location)
        markerOptions.title(titleStr)

        map.addMarker(markerOptions)

    }

    private fun getAddress(latLng: LatLng):String{

        val listAddress:List<Address>
        val geocoder = Geocoder(this, Locale.getDefault())

        listAddress = geocoder.getFromLocation(latLng.latitude, latLng.longitude,1)

        val address = listAddress[0].getAddressLine(0)
        val city = listAddress[0].locality
        val state = listAddress[0].adminArea
        val country = listAddress[0].countryName

        return address

    }

    private fun startLocationUpdates(){

        if (ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)

            return


        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback,null)

    }

    private fun createLocationRequest(){

        locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {

            locationUpdateState = true
            startLocationUpdates()

        }
        task.addOnFailureListener { e ->

            if (e is ResolvableApiException){


                try {

                    e.startResolutionForResult(this@MapsActivity,
                    REQUEST_CHECK_SETTINGS)
                }catch (sendEx:IntentSender.SendIntentException){


                }


            }

        }

    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        if (!locationUpdateState){

            startLocationUpdates()

        }
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        TODO("Not yet implemented")
    }


}