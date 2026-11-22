package com.productivity.wind.Imports.Utils.AppsAndDevice            

import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.ToX.*
import android.app.*
import androidx.compose.ui.unit.*
import androidx.compose.runtime.*
import android.os.*
import android.content.*
import androidx.compose.ui.platform.*
import kotlinx.coroutines.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import androidx.activity.result.contract.ActivityResultContracts
import com.productivity.wind.*
import androidx.compose.runtime.getValue
import android.content.Intent
import androidx.navigation.NavHostController
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.view.*
import com.productivity.wind.Imports.Utils.*
import androidx.lifecycle.*
import com.productivity.wind.Imports.UI_visible.*
import java.lang.ref.WeakReference


fun CloseMyApp() {
    AppActivity.it?.finishAffinity()
}


enum class WindErrors {
    UVST//UNSUPPORTED_VAR_SAVE_TYPE
}



//!!difficult to use ON PURPOSE. Activity should be used only when necessary
//*component activity, same as activity, just more UI features*
object AppActivity {
    private var activityRef: WeakReference<ComponentActivity>? = null

    var it: ComponentActivity?
        get() = activityRef?.get()
        set(value) {
            activityRef = value?.let { WeakReference(it) }
        }
}


lateinit var App: Context
lateinit var AppNav: NavHostController
lateinit var permission: ActivityResultLauncher<Str>

var AppH by mState(0.dp)
var AppW by mState(0.dp)
var AppLazyH by mState(0.dp)
var AppDensity by mState(0f)
val AppPkg = "com.productivity.wind"

//compose scope (lives as long as compose: not recommended)
lateinit var scope: CoroutineScope

//app scope (default)
lateinit var appScope: CoroutineScope

lateinit var appNonUIScope: CoroutineScope
lateinit var appWorker: OneWorker


class TheApp : Application() {
    override fun onCreate() {
        super.onCreate()
        App = this
		appWorker = OneWorker()

		try {
			AppCrash.printLastCrash(this)
			AppCrash.install(this)
		} catch(e: Exception){
			Vlog("Logs crashed: ${e.message}")
		}
    }
}




	
class AppUI : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

		WindowCompat.enableEdgeToEdge(window)
		
		AppActivity.it = this
		App = this.applicationContext
		appScope = lifecycleScope
		appNonUIScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
		
		permission = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            log("permission granted?: $granted")
		}
		
		HandleIntent(intent)
		
		AppStart_beforeUI()
		

        setContent { 
			AppDensity = LocalDensity.current.density
			scope = rememberCoroutineScope()

			AppNav = rememberNavController()
			AppH = LocalConfiguration.current.screenHeightDp.dp
			AppW = LocalConfiguration.current.screenWidthDp.dp
			AppLazyH = AppH - Device.bottomBarHeight()

			Bar.userLocation = toLatLng(Bar.userLatLng)
			
			BottomAppBarPadding{
				AppContent()
			}

			
			
        }
    }

    override fun onResume() {
        super.onResume()
        OnResume()
    }
	
	override fun onDestroy() {
		super.onDestroy()

		// prevents accidentally clearing a newer Activity.
		if (AppActivity.it === this) {
			AppActivity.it = null
		}
	}

	// happens for small interruptions too (dialogs, permission screens, another Activity)
	override fun onPause() {
		super.onPause()
		
		OnLeaveApp()
	}

	override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        HandleIntent(intent)
	}

	
}
