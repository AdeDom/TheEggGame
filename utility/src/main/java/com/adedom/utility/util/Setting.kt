package com.adedom.utility.util

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Setting(val activity: Activity, val context: Context) : AppCompatActivity() {

    init {
        requestPermission()
        locationListener()
        locationSetting()
    }

    companion object {
        private lateinit var mLocationSwitchStateReceiver: BroadcastReceiver

        fun locationListener(activity: Activity, boolean: Boolean) {
            if (boolean) {
                val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
                filter.addAction(Intent.ACTION_PROVIDER_CHANGED)
                activity.registerReceiver(mLocationSwitchStateReceiver, filter)
            } else {
                activity.unregisterReceiver(mLocationSwitchStateReceiver)
            }
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            val result =
                ContextCompat.checkSelfPermission(context, Manifest.permission.GET_ACCOUNTS)
            val permission = result == PackageManager.PERMISSION_GRANTED
            if (!permission) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1
                )
            }
        }
    }

    private fun locationListener() {
        mLocationSwitchStateReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (LocationManager.PROVIDERS_CHANGED_ACTION == intent.action) {
                    val locationManager =
                        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                    val isGpsEnabled =
                        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) //NETWORK_PROVIDER

                    if (!isGpsEnabled) {
                        locationSetting()
                    }
                }
            }
        }
    }

    private fun locationSetting() {
        if (!locationSetting(context)) {
            activity.startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1234)
            activity.finishAffinity()
        }
    }

    private fun locationSetting(context: Context): Boolean {
        val contentResolver = context.contentResolver
        return Settings.Secure.isLocationProviderEnabled(
            contentResolver,
            LocationManager.GPS_PROVIDER
        )
    }
}