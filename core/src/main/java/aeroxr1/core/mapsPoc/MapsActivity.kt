package aeroxr1.core.mapsPoc

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import aeroxr1.core.R
import aeroxr1.core.R.*
import com.huawei.hmf.tasks.OnSuccessListener
import com.huawei.hmf.tasks.Task
import com.huawei.hms.location.FusedLocationProviderClient
import com.huawei.hms.location.LocationServices
import com.huawei.hms.maps.*
import com.huawei.hms.maps.model.LatLng


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var hMap: HuaweiMap? = null
    private var mMapFragment: MapFragment? = null
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_maps)

        if (!hasLocationPermission()) {
            ActivityCompat.requestPermissions(this, PERMISION, REQUEST_CODE)
        } else{
            initMap()
        }
    }

    private fun initMap(){
        mMapFragment = fragmentManager.findFragmentByTag("map_fragment") as MapFragment?
        if (mMapFragment == null) {
            val huaweiMapOptions = HuaweiMapOptions()
            huaweiMapOptions.compassEnabled(true)
            huaweiMapOptions.zoomGesturesEnabled(true)
            mMapFragment = MapFragment.newInstance(huaweiMapOptions)
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.frame_mapfragmentcode, mMapFragment!!, "map_fragment")
            fragmentTransaction.commit()
        }
        mMapFragment?.onAttach(this)
        mMapFragment?.getMapAsync(this)
        // Creating a Location Service Client
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getBaseContext());
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " permission setting successfully", Toast.LENGTH_LONG).show()
                    initMap()
                } else {
                    Toast.makeText(this, permissions[i] + " permission setting failed", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
    }

    override fun onMapReady(map: HuaweiMap) {
        Log.d(TAG, "onMapReady: ")
        hMap = map
        if (isGPSOpen(this)) {
            hMap?.isMyLocationEnabled = true
            hMap?.uiSettings?.isMyLocationButtonEnabled = true
        } else {
            hMap?.isMyLocationEnabled = false
            hMap?.uiSettings?.isMyLocationButtonEnabled = false
        }
        hMap?.isBuildingsEnabled = true
        hMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(48.893478, 2.334595), 10f))
        getLastLocation()
    }

    /**
     * Determine whether to turn on GPS
     */
    private fun isGPSOpen(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return gps || network
    }

    /**
     * Determine if you have the location permission
     */
    private fun hasLocationPermission(): Boolean {
        for (permission in PERMISION) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    private fun getLastLocation() {
        try {
            val lastLocation: Task<Location>? = mFusedLocationProviderClient?.getLastLocation()
            lastLocation?.addOnSuccessListener(OnSuccessListener {
                val position = "latitude: ${it.latitude} longitude: ${it.longitude}"
                findViewById<TextView>(R.id.location).text = position
            })?.addOnFailureListener {

            }
        } catch (e: Exception) {

        }
    }


    companion object {
        private const val TAG = "MapFragmentDemoActivity"
        const val REQUEST_CODE = 0X01
        private val PERMISION = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    }
}