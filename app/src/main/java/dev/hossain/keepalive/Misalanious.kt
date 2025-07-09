package dev.hossain.keepalive

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.hossain.keepalive.data.PermissionType
import dev.hossain.keepalive.data.logging.AppActivityLogger
import dev.hossain.keepalive.ui.screen.Permissions_Screen
import dev.hossain.keepalive.util.NotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

//region BACKGROUND TASK

class WatchdogService : Service() {
    companion object { private const val NOTIFICATION_ID = 1 }
    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
    private val notificationHelper = NotificationHelper(this)
    private lateinit var activityLogger: AppActivityLogger
    private var currentServiceInstanceId: Int = 0
    override fun onBind(intent: Intent?): IBinder? {
        Timber.d("onBind: $intent")
        return null
    }

    //* FUNCTION RESPONSIBLE FOR WHAT HAPPENS WHEN BACKGROUND TASK RUNS
    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        Timber.d("onStartCommand() called with: intent = $intent, flags = $flags, startId = $startId")
        currentServiceInstanceId = startId
        notificationHelper.createNotificationChannel()
        activityLogger = AppActivityLogger(applicationContext)
        startForeground(NOTIFICATION_ID, notificationHelper.buildNotification(),)


        serviceScope.launch {


        }
        return START_STICKY
    }

    override fun onDestroy() { super.onDestroy(); serviceScope.cancel() }
}


//endregion
//region NavController
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(navController: NavHostController, startDestination: String, modifier: Modifier = Modifier, activityResultLauncher: ActivityResultLauncher<Intent>?, requestPermissionLauncher: ActivityResultLauncher<Array<String>>?, allPermissionsGranted: Boolean = false, permissionType: PermissionType, showPermissionRequestDialog: MutableState<Boolean>, onRequestPermissions: () -> Unit, totalRequiredCount: Int, grantedCount: Int) {
    androidx.navigation.compose.NavHost(navController = navController, startDestination = startDestination) {
        composable("PERMISSIONS"){
            Permissions_Screen(
                navController = navController,
                allPermissionsGranted = allPermissionsGranted,
                activityResultLauncher = activityResultLauncher,
                requestPermissionLauncher = requestPermissionLauncher,
                permissionType = permissionType,
                showPermissionRequestDialog = showPermissionRequestDialog,
                onRequestPermissions = onRequestPermissions,
                totalRequiredCount = totalRequiredCount,
                grantedCount = grantedCount,
                configuredAppsCount = grantedCount,
            )
        }
        composable("Main") {
            val navController = rememberNavController()
            Main(navController)
        }
        composable("FunScreen") {
            val navController = rememberNavController()
            FunScreen(navController)
        }
    }
}


//endregion






//region MICALANIOUS UI
@Composable
fun ChillTimeButton(navController: NavController){
    Button(
        onClick = { if (Bar.funTime > 0) { navController.navigate("FunScreen") } else { Toast.makeText(Global1.context, "GET BACK TO WORK", Toast.LENGTH_SHORT).show() }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Chill Time 🌴")
    }
}

//endregion

