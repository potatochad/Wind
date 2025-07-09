package dev.hossain.keepalive.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.hossain.keepalive.OtherScreens
import dev.hossain.keepalive.data.PermissionType
import dev.hossain.keepalive.ui.screen.AppActivityLogScreen
import dev.hossain.keepalive.ui.screen.AppConfigScreen
import dev.hossain.keepalive.ui.screen.MainLandingScreen
import dev.hossain.keepalive.ui.screen.SettingsScreen



@Composable
fun BottomNavigationWrapper(
    navController: NavHostController,
    context: Context,
    allPermissionsGranted: Boolean,
    activityResultLauncher: ActivityResultLauncher<Intent>?,
    requestPermissionLauncher: ActivityResultLauncher<Array<String>>?,
    permissionType: PermissionType,
    showPermissionRequestDialog: MutableState<Boolean>,
    onRequestPermissions: () -> Unit,
    totalRequiredCount: Int,
    grantedCount: Int,
    configuredAppsCount: Int,
) {
    Scaffold() { innerPadding ->
}

