package com.and.chap5

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationManager
import android.net.wifi.WifiManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.and.chap5.databinding.ActivityMainBinding
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var wifiManager: WifiManager
    lateinit var location: LocationManager
    private val PERMISSION_REQUEST_LOCATION = 101

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        location = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        binding.btnCoba.setOnClickListener {
            Glide.with(this)
                .load("https://i.ibb.co/zJHYGBP/binarlogo.jpg")
                .circleCrop()
                .into(binding.ivCoba)
        }
        binding.buttonLocation.setOnClickListener {
            val permissionCheck = checkSelfPermission(ACCESS_FINE_LOCATION)
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Location DIIZINKAN", Toast.LENGTH_LONG).show()
                getLongLat()
            } else {
                Toast.makeText(this, "Permission Location DITOLAK", Toast.LENGTH_LONG).show()
                requestLocationPermission()
            }
        }
            binding.buttonEnable.setOnClickListener {
            //fungsi untuk memanggil method enableWifi
            enableWifi(it)
        }
        binding.buttonDisable.setOnClickListener {
            //fungsi untuk memanggil method disableWifi
            disableWifi(it)
        }
    }
        //fungsi untuk mengaktifkan Wifi
        fun enableWifi(view: View) {
            wifiManager.isWifiEnabled = true
            Toast.makeText(this, "Wifi enabled", Toast.LENGTH_SHORT).show()
        }

        //fungsi untuk menonaktifkan Wifi
        fun disableWifi(view: View) {
            wifiManager.isWifiEnabled = false
            Toast.makeText(this, "Wifi disabled", Toast.LENGTH_SHORT).show()
        }



    //fungsi untuk meminta permission akses location
    fun requestLocationPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION), PERMISSION_REQUEST_LOCATION)
    }

    //fungsi untuk mendapatkan long-lat location
    fun getLongLat() {
        val provider = location.getBestProvider(Criteria(), false)
        if (provider != null) {
            if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            val location = location.getLastKnownLocation(provider)
            if (location != null) {
                val longitude = location.longitude
                val latitude = location.latitude
                Toast.makeText(this, "Longitude: $longitude, Latitude: $latitude", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Location not found", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Provider not found", Toast.LENGTH_LONG).show()
        }
    }

    //fungsi untuk handle permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission diterima", Toast.LENGTH_LONG).show()
                    getLongLat()
                } else {
                    Toast.makeText(this, "Permission ditolak", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

