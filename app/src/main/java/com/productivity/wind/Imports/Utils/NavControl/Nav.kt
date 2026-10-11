package com.productivity.wind.Imports.Utils.NavControl              

import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import androidx.compose.runtime.*
import android.os.*
import com.productivity.wind.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.annotation.RequiresApi
import androidx.navigation.*
import com.productivity.wind.Imports.Utils.*
import androidx.navigation.compose.*


typealias Nav = NavHostController
typealias NavBuilder = NavHostController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyNavGraph(navController: NavHostController) {
    NavHost(
		navController = navController, 
		startDestination = if (Bar.appCrashed) "LogsScreen" else defaultScreen
	) {
        ScreenNav()
    }
}

fun navBack() { AppNav.popBackStack() }

inline fun <reified T> NavBackStackEntry.url(key: Str): T? {
    val v = arguments?.get(key)
    if (v == "_") return null
    return v as? T
}



var AppNavUrlChanged by mState(no)
fun goTo(route: Str){ 
    Do {
        AppNavUrlChanged = yes
		AppNav.navigate(route) 

		wait(1000)
		AppNavUrlChanged = no
	}
}
fun pop(route: Str){ 
   AppNav.navigate(route) 
}

fun NavGraphBuilder.url(txt: Str, UI: ui_<NavBackStackEntry>) {
    composable(txt) { UI(it) }
}
fun NavGraphBuilder.popup(txt: Str, UI: ui_<NavBackStackEntry>) {
    dialog(txt){ UI(it) }
}


