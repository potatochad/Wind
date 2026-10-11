package com.productivity.wind.Imports.UI_visible

import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.ToX.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import kotlin.collections.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import java.time.*
import com.productivity.wind.Imports.Utils.*
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
    value: Any? = null,
    onDateSelected: DoStr,
    showModeToggle: Bool = no,
    colors: DatePickerColors = DatePickerDefaults.colors(
       containerColor = MaterialTheme.colorScheme.surface,
       selectedDayContainerColor = orange,
       selectedDayContentColor = white,
       todayDateBorderColor = transparent,
       todayContentColor = orange,              
       titleContentColor = white,         
       headlineContentColor = white,      
       weekdayContentColor = white        
    ),
    shape: Shape = MaterialTheme.shapes.medium,
    onDismiss: Do
) {
    val today = LocalDate.now()

    val minYear = today.minusYears(99).toMillis()
    val maxYear = today.plusYears(99).toMillis()

    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Bool {
                return utcTimeMillis in minYear..maxYear  
            }
        }
    )

    fun dateSelected(){
        datePickerState.selectedDateMillis?.let {
            val date = toLocalDate(it)
            onDateSelected("$date")
            onDismiss()
        }
    }
    RunOnce { 
        datePickerState.date(toLocalDate(value))
        Vlog("${toLocalDate(value)}")
    }

    DatePickerDialog(
        onDismissRequest = onDismiss,
        colors = DatePickerDefaults.colors().copy(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        shape = shape,
        confirmButton = {
            Btn("Confirm") {
                dateSelected()
            }
        },
        dismissButton = {
            Btn("Cancel"){
               onDismiss()
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            colors = colors,
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
              TinyInput(repeatEvery, Mod.w(80), maxLetters = 5, isInt = yes){  
                  repeatEvery = toInt(it)
              }
          }
          LazzyRow {
              var showDatePicker by r(no)

              Text("Start date: ")
              Ctext(
                  beginDate.words,
                  mod = Mod.space(5),
                  animate = yes,
              ) {
                  showDatePicker = yes
              }
              
              if (showDatePicker){
                   PickDate(
                       beginDate,
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












