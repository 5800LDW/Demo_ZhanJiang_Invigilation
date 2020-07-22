package com.tecsun.jc.base.utils

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.utils.log.LogUtil

class LocationUtils {

    private var locationManager: LocationManager? = null
    private var provider: String? = null
    private var callback: LocationCallback? = null

    companion object {
        val instance: LocationUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            LocationUtils() }
    }

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            if (location != null) {
                callback?.onLocation(location)
                locationManager?.removeUpdates(this)
            }
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

        }

        override fun onProviderEnabled(provider: String?) {

        }

        override fun onProviderDisabled(provider: String?) {

        }
    }

    fun initLocationManager(context: Context, callback: LocationCallback) {
        this.callback = callback
        locationManager = JinLinApp.context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providerList = locationManager?.getProviders(true)
        if (providerList != null) {
            if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
                provider = LocationManager.NETWORK_PROVIDER
            } else if (providerList.contains(LocationManager.GPS_PROVIDER)) {
                provider = LocationManager.GPS_PROVIDER
            }
        }
        try {
            if(provider!=null){
                val location = locationManager?.getLastKnownLocation(provider)
                callback.onLocation(location)
                if (location == null) {
                    locationManager?.requestLocationUpdates(provider, 10000L, 0F, locationListener)
                }
            }

        } catch (e: SecurityException) {
          LogUtil.e(">>>>>>>>>>>>>>>>>> LocationUtils Exception")
          LogUtil.e(e)
        }
    }

    fun stopLocation() {
        locationManager?.removeUpdates(locationListener)
        locationManager = null
        callback = null
    }

    interface LocationCallback {

        fun onLocation(location: Location?)
    }
}