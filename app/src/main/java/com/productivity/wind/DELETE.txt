package com.productivity.wind


//region USER MANUAL

//region Usefull

/** HOW USE REGION

//region

requires a space of one between // and code to work well

//endregion

 */

/*HOW CODE
    REWRITE NONE UNIVERSAL CODE-TALKING SMALL 100
    all else rewriteee

    use region, one file, and yea
    plus, like to have functions, a section for universal functions

*/

/*HOW MAKE DRAWERS, SLIDY THINGIES, ETC...
    DO NOT USE THE ANDROID DEFAULTS FOR THAT
    NO XML

    *JUST MAKE POPUP, GOT IT, HANDLES LIKE 80% OF THE PAIN-
    ?TO LAZY TO MAKE UNIVERSAL THINGY, WILL DO LATER, ONECE FINISH THIS
    AND TRYING TO KEEP CUSTOMERSS
*/


//! IMPORTANT

//region DATA MANAGEMENT
// all you need
//coudn't be simpler::
/*NEEDED SETUP
* PUT IT HERE!!;
@RequiresApi(Build.VERSION_CODES.O)
fun AppStart_beforeUI(context: Context) {
    Global1.context = context
    SettingsSaved.init()
    SettingsSaved.Bsave()
}
*
*
*
class Settings {
    var show by mutableStateOf(false)
    var CurrentInput by mutableStateOf("")
}
*/
/*How Use
* YOU CAN READ THE DATA, CHANGE IT, AUTO UPDATE, saves, etc..
* WORKS FOR LISTS TOOOO
? HANDLES ABOUT 600 ITEMS MAX-recommended is 300
Bar.funTime += 1
    Bar.currentInput = "Testing input"
    Bar.highestCorrect = max(Bar.highestCorrect, 12)
    *
    *
   class Settings {
    var ShowMenu by mutableStateOf(false)
    val AppList = mutableStateListOf<Apps>()  // Apps- is a data class, enter in any you want
*/


/*?HOW CHANGE LIST
* ✅ Add
AppList.add(app)
AppList.addAll(listOfApps)
AppList.add(index, app)
*
❌ Remove
AppList.remove(app)
AppList.removeAt(index)
AppList.removeIf { it.Block }
AppList.clear()
*
🔄 Update
AppList[index] = app.copy(Block = true)
AppList[index].Block = true  // if mutable inside

* val app = AppList[0]  // 0 = first app in the list
val id = app.id       // this is the app's unique ID (UUID)

*
🔍 Find
val found = AppList.find { it.name == "YouTube" }
val exists = AppList.any { it.Block }
val count = AppList.count { it.Exists }
*
🧠 Smart Filtering
val onlyBlocked = AppList.filter { it.Block }
val top3 = AppList.sortedByDescending { it.TimeSpent }.take(3)
*
🔁 Loop
AppList.forEach { app -> println(app.name) }
for (i in AppList.indices) { AppList[i].Exists = false }*/
//endregion


/* Simple Synched:
    var funTime by Synched { (0) }
*/

/*
log:

Example usage:
log("Button clicked")
log("Error happened", "ErrorTag")
*/

/* SMALL THINGS
   val id: String = UUID.randomUUID().toString(),
   *a thing that exists, unique completly

 */

/*NO LAG
USE NO LAG COMPOSE
NoLagCompose

@Composable
fun ChillScreen()=NoLagCompose {}
* */

//endregion Usefull


//region ONCEs

/* ACCESABILITY PERMISSION
*
<service
            android:name=".WatchdogAccessibilityService"
            android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE"
            android:exported="false">
            <intent-filter>
                <action android:name="android.accessibilityservice.AccessibilityService" />

            </intent-filter>

            <meta-data
                android:name="android.accessibilityservice"
                android:resource="@xml/accessibility_service_config" />
        </service>
*/


//endregion

//endregion

