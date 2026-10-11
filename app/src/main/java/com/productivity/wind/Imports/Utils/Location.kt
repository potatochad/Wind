package com.productivity.wind.Imports.Utils

import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import com.productivity.wind.Imports.Utils.ToX.*
import androidx.core.app.*
import androidx.compose.runtime.*
import android.os.*
import com.productivity.wind.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import android.content.pm.*
import com.productivity.wind.Imports.*
import android.location.*
import androidx.core.content.*
import com.google.android.gms.maps.model.*
import com.google.android.gms.location.*
import android.Manifest.permission.*
import com.google.maps.android.compose.*
import com.google.maps.android.*
import com.productivity.wind.Imports.UI_visible.*

typealias Marker = MarkerState
val Marker.it: LatLng
    get() = this.position

@Composable
fun marker(x: LatLng) = rememberMarkerState(position = x)

@Composable
fun Marker.onChange(Do: Do_<LatLng>) {
    RunOnce(this) {
        var first = yes
        snapshotFlow { this@onChange.it }
            .collect {
                if (first) {
                    first = no // ignore first emission
                } else {
                    Do(it)
                }
            }
    }
}


fun distance(latLng1: LatLng, latLng2: LatLng): Double {
    val result = FloatArray(1)
    Location.distanceBetween(
        latLng1.latitude,
        latLng1.longitude,
        latLng2.latitude,
        latLng2.longitude,
        result
    )
    return result[0].toDouble()
}

fun locationOn(): Bool {
    val lm = App.getSystemService(LocationManager::class.java)
    return lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
           lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}

fun location(Do: Do = {}) {
    if (ContextCompat.checkSelfPermission( App, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
		if (locationOn()) Do()
		else Vlog("turn on location")
	} else {
		AppActivity.it?.let {
			ActivityCompat.requestPermissions(it, arrayOf(ACCESS_FINE_LOCATION), 100)
		}
    }
}







fun getUserLocation(
    fusedLocationClient: FusedLocationProviderClient,
    each: Any = 1000L,
    onLocation: Do_<LatLng>,
) {
    val locationRequest = com.google.android.gms.location.LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY, toL(each)
    ).build()

    val callback = object : com.google.android.gms.location.LocationCallback() {
        override fun onLocationResult(result: com.google.android.gms.location.LocationResult) {
            result.lastLocation?.let { loc ->
                onLocation(LatLng(loc.latitude, loc.longitude))
            }
        }
    }

    fusedLocationClient.requestLocationUpdates(locationRequest, callback, Looper.getMainLooper())
}



fun insideGeoCircle(point: LatLng, center: LatLng, radiusMeters: Double) = SphericalUtil.computeDistanceBetween(point, center) <= radiusMeters









@Composable
fun GeoCircle(
	geo: GeoCircle,
	selected: mStateBool = mState(no),
	onSelect: DoStr = {},
){
	var center = LatLng(geo.Lat, geo.Lng)
	var borderColor by r(white)
	var borderW by r(2f)
	
	val pin = marker(center)
	pin.onChange { drag ->
		onSelect(geo.id)
		selected.it = yes
		geo.edit{
			geo.Lat = drag.latitude
			geo.Lng = drag.longitude
		}
	}
	
	
	MarkerComposable(
		state = pin,
		onClick = { 
			onSelect(geo.id)
			selected.it = yes
			yes
		},
		draggable = yes,
	) {
		drawPin()
	}

	if (selected.it) {
		borderColor = gold
		borderW = 3f
	}

	Circle(
        center = pin.it,
        radius = toD(geo.radius),
        strokeColor = borderColor,
		strokeWidth = borderW,
        fillColor = faded(gold, 0.4f),
    )
}












