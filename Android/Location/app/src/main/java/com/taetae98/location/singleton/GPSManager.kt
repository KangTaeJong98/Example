package com.taetae98.location.singleton

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import java.io.IOException
import java.util.*

class GPSManager private constructor(private val context: Context) {
    companion object {
        private var instance: GPSManager? = null

        fun getInstance(context: Context): GPSManager {
            return instance ?: synchronized(this) {
                GPSManager(context)
            }.also {
                instance = it
            }
        }
    }

    fun getLocation(): Location {
        if (context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val locationManager = (context.getSystemService(Context.LOCATION_SERVICE) as LocationManager)

            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let {
                    return it
                }
            }

            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)?.let {
                    return it
                }
            }
        }

        throw IllegalStateException("GPS 확인 불가능")
    }

    fun getAddress(location: Location = getLocation(), index: Int = 0, maxResults: Int = 2): String {
        return try {
            val address = Geocoder(context, Locale.getDefault()).getFromLocation(location.latitude, location.longitude, maxResults)

            if (address.isNotEmpty()) {
                address[index].getAddressLine(index).toString()
            } else {
                throw IllegalStateException("주소 확인 불가능")
            }
        } catch (e: IOException) {
            "위치 서비스 사용 불가"
        } catch (e: IllegalArgumentException) {
            "잘못된 GPS 좌표"
        }
    }
}