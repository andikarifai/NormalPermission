package com.and.chap5

import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class NormalPermission : AppCompatActivity() {
    lateinit var wifiManager: WifiManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "KotlinApp"
    }
    fun enableWifi(view: View) {
        wifiManager.isWifiEnabled = true
        Toast.makeText(this, "Wifi enabled", Toast.LENGTH_SHORT).show()
    }
    fun disableWifi(view: View) {
        wifiManager.isWifiEnabled = false
        Toast.makeText(this, "Wifi disabled", Toast.LENGTH_SHORT).show()
    }
}