package com.mrx.quicknews.core.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.util.Locale

suspend fun getLocationCountryCode(context: Context): String {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val locationProvider = LocationManager.NETWORK_PROVIDER

    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        val lastKnownLocation: Location? = locationManager.getLastKnownLocation(locationProvider)
        return if (lastKnownLocation != null) {
            getCountryCodeFromLocation(
                context,
                lastKnownLocation.latitude,
                lastKnownLocation.longitude
            )
        } else {
            Locale.getDefault().country.lowercase()
        }
    } else {
        return "us"
    }
}

suspend fun getCountryCodeFromLocation(
    context: Context,
    latitude: Double,
    longitude: Double
): String {
    return withContext(Dispatchers.IO) {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        addresses?.firstOrNull()?.countryCode?.lowercase() ?: "us"
    }
}

fun getDateToString(dateTimeString: String): String {
    val givenTime = Instant.parse(dateTimeString)
    val currentTime = Instant.now()
    val duration = Duration.between(givenTime, currentTime)

    val seconds = duration.seconds
    val minutes = duration.toMinutes()
    val hours = duration.toHours()
    val days = duration.toDays()

    return when {
        seconds < 60 -> "${seconds}s"
        minutes < 60 -> "${minutes}m"
        hours < 24 -> "${hours}h"
        days < 7 -> "${days}d"
        days < 28 -> "${days / 7}w"
        else -> {
            val year = givenTime.atZone(ZoneId.systemDefault()).year
            val month = givenTime.atZone(ZoneId.systemDefault()).monthValue
            val day = givenTime.atZone(ZoneId.systemDefault()).dayOfMonth
            "$day/$month/$year"
        }
    }
}