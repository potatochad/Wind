package com.productivity.wind.Imports.UI_visible
 
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
import java.time.*
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
import androidx.compose.ui.window.*
import com.productivity.wind.Imports.UI_visible.*
import android.os.Process.*
import android.content.ClipData
import android.content.ClipboardManager
import androidx.compose.ui.graphics.*



/**
 * A date picker dialog that allows the user to select a date range.
 *
 * @param title: The title of the dialog.
 * @param confirmText: The text for the confirm button.
 * @param cancelText: The text for the cancel button.
 * @param minSelectableDate: The minimum selectable date in milliseconds since epoch. If null, 99 years before today is used.
 * @param maxSelectableDate: The maximum selectable date in milliseconds since epoch. If null, 99 years from today is used.
 * @param onDateSelected: Callback that is called when a date range is selected. The Pair contains the start and end dates.
 * @param showModeToggle: Whether to show the mode toggle button.
 * @param confirmOnSelection: Whether to confirm the selection on date selection.
 * @param colors: The colors to use for the date picker.
 * @param onDismiss: Callback that is called when the dialog is dismissed.
 *
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickDate(
    confirmText: Str = "Confirm",
    cancelText: Str = "Cancel",
    minSelectableDate: Long? = null,
    maxSelectableDate: Long? = null,
    onDateSelected: DoStr,
    showModeToggle: Bool = no,
    confirmOnSelection: Bool = yes,
    colors: DatePickerColors = DatePickerDefaults.colors(
       containerColor = MaterialTheme.colorScheme.surface,
       selectedDayContainerColor = Color(0xFFFFA500), // orange circle
       selectedDayContentColor = Color.White,         // white text on selected
       todayDateBorderColor = Color.Transparent,      // no border for today
       todayContentColor = Color.Gray,                // today’s number gray
       titleContentColor = Color.White,         // title orange
       headlineContentColor = Color.White,      // headline orange
       weekdayContentColor = Color.White        // weekdays orange
    ),
    shape: Shape = MaterialTheme.shapes.medium,
    onDismiss: Do
) {
    val today = LocalDate.now()

    val ninetyNineYearsBefore = today.minusYears(99)
    val ninetyNineYearsLater = today.plusYears(99)

    val defaultMin = ninetyNineYearsBefore.toMillis()
    val defaultMax = ninetyNineYearsLater.toMillis()

    val minSelectableMillis = minSelectableDate ?: defaultMin
    val maxSelectableMillis = maxSelectableDate ?: defaultMax

    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Bool {
                return utcTimeMillis in minSelectableMillis..maxSelectableMillis
            }
        }
    )

    fun dateSelected(){
        datePickerState.selectedDateMillis?.let {
            val date = Instant.ofEpochMilli(it)
                .atOffset(ZoneOffset.UTC)
                .toLocalDate()
            onDateSelected("$date")
            onDismiss()
        }
    }

    RunOnce(datePickerState) {
        if(confirmOnSelection){
            dateSelected()
        }
    }

    DatePickerDialog(
        onDismissRequest = onDismiss,
        colors = DatePickerDefaults.colors().copy(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        shape = shape,
        confirmButton = {
            Btn(confirmText) {
                dateSelected()
            }
        },
        dismissButton = {
            Btn(cancelText){
               onDismiss()
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            colors = colors,
            headline = null,
            showModeToggle = showModeToggle,
            modifier = Mod.maxW().h(500)
        )
    }
}

/*

package com.isakaro.kwik.ui.date

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.ui.button.KwikButton
import com.isakaro.kwik.ui.button.KwikTextButton
import com.isakaro.kwik.ui.text.KwikText
import com.isakaro.kwik.ui.utils.toLocalDate
import com.isakaro.kwik.ui.utils.toMillis
import java.time.LocalDate

/**
 * A date range picker dialog that allows the user to select a date range.
 *
 * @param title: The title of the dialog.
 * @param confirmText: The text for the confirm button.
 * @param cancelText: The text for the cancel button.
 * @param minSelectableDate: The minimum selectable date in milliseconds since epoch. If null, 99 years before today is used.
 * @param maxSelectableDate: The maximum selectable date in milliseconds since epoch. If null, 99 years from today is used.
 * @param onDateRangeSelected: Callback that is called when a date range is selected. The Pair contains the start and end dates.
 * @param showModeToggle: Whether to show the mode toggle button.
 * @param colors: The colors to use for the date picker.
 * @param onDismiss: Callback that is called when the dialog is dismissed.
 *
 * * @see [KwikDateRangeButton] for a date field that shows this dialog when clicked.
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KwikDateRangePickerDialog(
    title: String = "Select date range",
    confirmText: String = "Confirm",
    cancelText: String = "Cancel",
    minSelectableDate: Long? = null,
    maxSelectableDate: Long? = null,
    onDateRangeSelected: (Pair<LocalDate, LocalDate>) -> Unit,
    showModeToggle: Boolean = false,
    colors: DatePickerColors = kwikDatePickerColors(),
    shape: Shape = MaterialTheme.shapes.medium,
    onDismiss: () -> Unit
) {
    val today = LocalDate.now()
    val ninetyNineYearsBefore = today.minusYears(99)
    val ninetyNineYearsLater = today.plusYears(99)

    val minSelectable = minSelectableDate ?: ninetyNineYearsBefore.toMillis()
    val maxSelectable = maxSelectableDate ?: ninetyNineYearsLater.toMillis()

    val dateRangePickerState = rememberDateRangePickerState(
        selectableDates = object: SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis in minSelectable..maxSelectable
            }
        }
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        colors = colors,
        shape = shape,
        confirmButton = {
            KwikButton(
                text = confirmText,
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    val startMillis = dateRangePickerState.selectedStartDateMillis
                    val endMillis = dateRangePickerState.selectedEndDateMillis

                    if (startMillis != null && endMillis != null) {
                        val startDate = startMillis.toLocalDate()
                        val endDate = endMillis.toLocalDate()
                        onDateRangeSelected(Pair(startDate, endDate))
                    }
                    onDismiss()
                }
            )
        },
        dismissButton = {
            KwikTextButton(
                onClick = onDismiss,
                text = {
                    KwikText.LabelMedium(
                        text = cancelText
                    )
                }
            )
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = {
                KwikText.TitleMedium(
                    text = title
                )
            },
            colors = colors,
            showModeToggle = showModeToggle,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(12.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun kwikDatePickerColors(): DatePickerColors {
    return DatePickerDefaults.colors().copy(
        containerColor = MaterialTheme.colorScheme.surface,
        selectedDayContainerColor = MaterialTheme.colorScheme.primary,
        dayInSelectionRangeContainerColor = MaterialTheme.colorScheme.secondary,
        dayInSelectionRangeContentColor = MaterialTheme.colorScheme.onSurface,
        selectedYearContainerColor = MaterialTheme.colorScheme.primary,
        disabledDayContentColor = Color.Gray
    )
}
*/





@Composable
fun ScheduleUI(
    schedule: Schedule,
    onChange: Do_<Schedule>
){
   var type by r(schedule.type)
   var repeatEvery by r(schedule.every)
   var weekDays by r(
       setOf("MO","TU","WE","TH","FR","SA","SU")
   )

   var beginDate by r(schedule.startDate)

   RunOnce {
       weekDays = schedule.daysOfWeek.split(" ").filter { it.isNotBlank() }.toSet()   
   }

   LaunchedEffect(type, repeatEvery, weekDays, beginDate) {
       onChange(
           Schedule(
              type = type,
              every = repeatEvery,
              daysOfWeek = weekDays.joinToString(" "),
              startDate = beginDate
           )
       )
   }
   
       Column {
          LazzyRow {
              listOf("WEEKLY","MONTHLY","YEARLY","CUSTOM").forEach {
                  
                  Ctext(
                        it,
                        mod = Mod.space(5),
                        animate = yes,
                        selected = if (type == it) yes else no,
                  ) {
                     if (it == "CUSTOM"){
                        Vlog("DOESNT WORK")
                     }
                     type = it
                  }
              }
          }
          if (type == "WEEKLY"){
              LazzyRow {
                  listOf("MO","TU","WE","TH","FR","SA","SU").forEach {
                      var Ifselect by r(it in weekDays)
                      Ctext(
                            it,
                            mod = Mod.space(5),
                            animate = yes,
                            selected = Ifselect,
                      ) {
                         Ifselect = !Ifselect
                         if (Ifselect) {
                            weekDays = weekDays + "$it"
                         } else {
                            weekDays = weekDays - "$it"
                         }
                         
                      }
                  }
              } 
          }
          
          LazzyRow {
              Text("Repeat every")
              TinyInput(repeatEvery, maxLetters = 5, isInt =yes, w = 80){  
                  repeatEvery = toInt(it)
              }
          }
          LazzyRow {
              var showDatePicker by r(no)

              Text("Start date: ")
              Ctext(
                  toRead(beginDate),
                  mod = Mod.space(5),
                  animate = yes,
              ) {
                  showDatePicker = yes
              }
              
              if (showDatePicker){
                   PickDate(
                       toLocalDate(beginDate),
                       onDateSelected = { 
                           beginDate = it
                       },
                       onDismiss = {
                           showDatePicker = no
                       }
                   )
              }
              
              
          }
          
          
          
       } 
}












