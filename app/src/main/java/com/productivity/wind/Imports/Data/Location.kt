package com.productivity.wind.Imports.Data
 
import android.annotation.SuppressLint
import timber.log.Timber
import java.text.*
import android.app.usage.UsageStatsManager
import androidx.compose.foundation.interaction.*
import android.app.*
import androidx.core.app.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.unit.*
import androidx.compose.material3.*
import androidx.compose.foundation.text.*
import androidx.compose.ui.text.input.*
import androidx.compose.runtime.*
import android.os.*
import android.content.*
import android.util.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.*
import kotlin.reflect.full.memberProperties
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.jvm.isAccessible
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.reflect.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import android.widget.Toast
import com.productivity.wind.*
import java.util.UUID
import java.lang.reflect.Type
import kotlin.collections.*
import android.content.*
import java.lang.reflect.ParameterizedType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.*
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import android.content.Intent
import java.time.LocalDate
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.productivity.wind.Screens.*
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.layout.*
import android.graphics.drawable.Drawable
import android.content.pm.*
import com.productivity.wind.Imports.*
import java.util.*
import com.productivity.wind.R
import kotlin.reflect.full.*
import androidx.compose.ui.focus.*
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.gestures.*
import androidx.compose.ui.input.pointer.*
import java.io.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.text.style.*
import androidx.compose.foundation.lazy.*
import java.util.*
import kotlin.concurrent.*
import androidx.annotation.RequiresApi
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.view.*
import kotlin.reflect.*
import kotlin.reflect.jvm.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import androidx.compose.foundation.text.selection.*
import kotlin.system.*
import androidx.navigation.*
import android.webkit.*
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler
import org.jetbrains.kotlin.cli.common.ExitCode
import com.productivity.wind.Imports.Data.*
import android.location.*
import androidx.core.content.*
import androidx.compose.ui.text.*
import androidx.navigation.compose.*
import android.util.*
import com.google.android.gms.maps.model.*
import com.google.android.gms.location.*
import android.content.*
import android.net.*
import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import android.*
import androidx.activity.compose.*
import android.Manifest.permission.*
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import com.google.android.gms.location.*
import android.os.*
import com.google.maps.android.*
import androidx.compose.ui.graphics.*
import androidx.compose.foundation.shape.*
import kotlin.math.*

typealias Marker = MarkerState
val Marker.it: LatLng
    get() = this.position

@Composable
fun marker(x: LatLng) = rememberMarkerState(position = x)

@Composable
fun Marker.onChange(Do: Do_<LatLng>) {
    RunOnce(this) {
        snapshotFlow { this.it }.collect { Do(it) }
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
        ActivityCompat.requestPermissions(App, arrayOf(ACCESS_FINE_LOCATION), 100)
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
	Init: LatLng, 
	r: Any = 100
){
	var selected by r(no)
	var center by r(Init)
	val pin = marker(center)
	pin.onChange {
		Vlog("dragging")
	}
	
	
	MarkerComposable(
		state = pin,
		onClick = { yes },
		draggable = yes,
	) {
		drawPin()
	}

	Circle(
        center = pin.it,
        radius = toD(r),
        strokeColor = Gold,
        fillColor = faded(Gold, 0.6f)
    )
}












