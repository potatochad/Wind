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
fun GeoPin(
	center: LatLng
){
	MarkerComposable(
		state = MarkerState(position = center)
	) {
		Column(
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Box(
				Mod.s(50).move(h = 10)
					.background(Gold, CircleShape)
					.border(3.dp, Color.White, CircleShape),
				contentAlignment = Alignment.Center
			) {
				Icon(
					imageVector = Icons.Default.VisibilityOff,
					contentDescription = null,
					tint = Color.White,
					modifier = Mod.s(24)
				)
			}

			// Triangle (pin tip)
			Canvas(Mod.w(36).h(24)) {
				// Draw gold filled triangle
				val path = Path().apply {
					moveTo(size.width / 2f, size.height) // bottom center
					lineTo(0f, 0f)                        // top left
					lineTo(size.width, 0f)                // top right
					close()
				}
				drawPath(path, Gold)
				
				// Draw left border
				drawLine(
					color = Color.White,
					start = Offset(0f, 0f),
					end = Offset(size.width / 2f, size.height),
					strokeWidth = 9.3f
				)

				// Draw right border
				drawLine(
					color = Color.White,
					start = Offset(size.width, 0f),
					end = Offset(size.width / 2f, size.height),
					strokeWidth = 9.3f
				)
			}
		}
	}
}


@Composable
fun GeoArea(center: LatLng, r: Any = 100) {
	Circle(
        center = center,
        radius = toD(r),
        strokeColor = Gold,
        fillColor = faded(Gold, 0.6f)
    )
	
	GeoPin(center)
}



fun detectGeoClicks(LatLng: LatLng, list: mList<GeoCircle>, cameraState: CameraPositionState) {
	val camera = cameraState.position
	var clickSize = 27.toMeters(cameraState)
	
    list.each {
		clickSize
		
        // it.Lat
		
		// it.Lng
        // Vlog("Clicked near GeoCircle visually!")
    }
}












