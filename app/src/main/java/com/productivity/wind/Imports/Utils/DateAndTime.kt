package com.productivity.wind.Imports.Utils

import com.productivity.wind.Imports.Utils.ToX.*
import androidx.compose.runtime.*
import com.productivity.wind.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import java.time.LocalDate

fun now() = System.currentTimeMillis()
fun TimeMillis() = System.currentTimeMillis()

fun isNewDay(): Bool {
	if (Bar.lastDate == "") { Bar.lastDate = "${LocalDate.now()}"}
	
	val today = "${LocalDate.now()}"
	if (today != Bar.lastDate) {
		Bar.lastDate = today
		return yes
	}
	return no
}

@Composable
fun taskDueToday(x: Schedule): Bool {
	var date = Bar.lastDate //"${LocalDate.now()}"
	val todayWeekDay = toWeekDay(date) // "MO", "TU", etc.
	
	var show by r(no)

	if (date.isBefore(x.startDate)) return no 
	
	show = when (x.type) {
        "WEEKLY" -> {
            val scheduledDays = x.daysOfWeek.split(" ")
            todayWeekDay in scheduledDays// if in
        }
		"MONTHLY" -> no
		"YEARLY" -> no
		"CUSTOM" -> no
        else -> no
	}

	return show
	
}

fun Time(s: Int, pattern: Str = "HH:MM:SS"): Str {
    val h = s / 3600
    val m = (s % 3600) / 60
    val sec = s % 60

    return pattern
        .replace("HH", "%02d".format(h))
        .replace("H", h.toString())
        .replace("MM", "%02d".format(m))
        .replace("M", m.toString())
        .replace("SS", "%02d".format(sec))
        .replace("S", sec.toString())
}


@kotlinx.serialization.Serializable
data class Schedule(
    var type: Str = "", // WEEKLY MONTHLY YEARLY CUSTOM
    var every: Int = 1, // every 1 day, every 2 weeks
    var daysOfWeek: Str = "MO TU WE TH FR SA SU", // only for weekly: MO TU WE TH FR SA SU   
    var startDate: Str = Bar.lastDate, // 2020-11-29
)














