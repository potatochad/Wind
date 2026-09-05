

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import kotlin.math.roundToInt


// ============================================================
// DATA
// ============================================================

data class StoreApp(
    val name: String,
    val developer: String,
    val icon: String,
    val description: String,
    val longDescription: String,
    val rating: Double,
    val downloads: String,
    val size: String,
    val category: String,
    val color: Color,
    val accent: Color,
    val screenshots: List<String>,
    val reviews: List<Review>,
    val installed: Boolean = false
)

data class Review(
    val user: String,
    val rating: Int,
    val text: String,
    val time: String
)


// ============================================================
// SAMPLE DATA
// ============================================================

val storeApps = listOf(

    StoreApp(
        name = "Brain Dump",
        developer = "Some Guy",
        icon = "🧠",
        description = "Put thoughts somewhere else.",
        longDescription =
            "Brain Dump is a tiny place for all those thoughts that " +
            "appear at 2:13 AM and disappear exactly when you need them. " +
            "No accounts. No complicated productivity system. Just write stuff down.",
        rating = 4.7,
        downloads = "128K+",
        size = "8.4 MB",
        category = "Productivity",
        color = Color(0xFFE9E3FF),
        accent = Color(0xFF7357FF),
        screenshots = listOf("Thoughts", "Ideas", "More thoughts"),
        reviews = listOf(
            Review("Milo", 5, "Actually use this every day.", "2 days ago"),
            Review("alex.exe", 5, "Very stupid. Very useful.", "1 week ago"),
            Review("J", 4, "The button is slightly too far right.", "3 weeks ago")
        )
    ),

    StoreApp(
        name = "Weather-ish",
        developer = "Cloud Department",
        icon = "🌦️",
        description = "Weather. Probably.",
        longDescription =
            "Weather-ish tells you what the weather is doing. " +
            "Sometimes it gets it right. Sometimes the sky has other plans.",
        rating = 3.8,
        downloads = "42K+",
        size = "13 MB",
        category = "Weather",
        color = Color(0xFFDDF5FF),
        accent = Color(0xFF189BD7),
        screenshots = listOf("Cloud", "Rain?", "Sun"),
        reviews = listOf(
            Review("cloudlover", 4, "It told me it would rain. It did not.", "Yesterday"),
            Review("Tom", 3, "Good enough.", "4 days ago"),
            Review("☁️", 5, "I like clouds.", "1 month ago")
        )
    ),

    StoreApp(
        name = "LoFi Something",
        developer = "Night Shift",
        icon = "🎧",
        description = "Music for doing absolutely nothing.",
        longDescription =
            "A collection of calm sounds for studying, coding, reading, " +
            "or staring at your ceiling while reconsidering your life choices.",
        rating = 4.9,
        downloads = "890K+",
        size = "37 MB",
        category = "Music",
        color = Color(0xFFFFE6EF),
        accent = Color(0xFFFF4D8D),
        screenshots = listOf("Rain", "Night", "Desk"),
        reviews = listOf(
            Review("sarah", 5, "The rain loop is dangerously good.", "3 hours ago"),
            Review("404", 5, "Downloaded for studying. Forgot to study.", "2 days ago"),
            Review("Ben", 5, "10/10 ceiling staring.", "2 weeks ago")
        )
    ),

    StoreApp(
        name = "CleanMyLife",
        developer = "Probably Not A Company",
        icon = "🧹",
        description = "Organize your life. Allegedly.",
        longDescription =
            "CleanMyLife lets you create tasks, lists, habits, goals, " +
            "and other things you will probably ignore tomorrow.",
        rating = 4.2,
        downloads = "76K+",
        size = "21 MB",
        category = "Productivity",
        color = Color(0xFFE3FFE8),
        accent = Color(0xFF3DAD61),
        screenshots = listOf("Tasks", "Habits", "Oops"),
        reviews = listOf(
            Review("M", 4, "I have 47 tasks now.", "5 days ago"),
            Review("Joe", 5, "This fixed everything.", "1 week ago"),
            Review("anonymous", 2, "Too productive.", "1 month ago")
        )
    ),

    StoreApp(
        name = "Calculator+",
        developer = "Normal Calculator Company",
        icon = "🧮",
        description = "It's a calculator.",
        longDescription =
            "A calculator. It calculates numbers. There isn't really much else to say.",
        rating = 4.4,
        downloads = "2.1M+",
        size = "4.2 MB",
        category = "Tools",
        color = Color(0xFFFFF0D9),
        accent = Color(0xFFE89125),
        screenshots = listOf("Calculator", "Numbers"),
        reviews = listOf(
            Review("math guy", 5, "It does math.", "Today"),
            Review("L", 4, "Needs a square root button.", "2 weeks ago")
        )
    ),

    StoreApp(
        name = "Plant Friend",
        developer = "Leaf Labs",
        icon = "🌱",
        description = "Reminds you that your plant exists.",
        longDescription =
            "Plant Friend sends you friendly reminders to check your plants. " +
            "It cannot actually water them. We tried.",
        rating = 4.6,
        downloads = "55K+",
        size = "17 MB",
        category = "Lifestyle",
        color = Color(0xFFE6F8D9),
        accent = Color(0xFF65A936),
        screenshots = listOf("Plants", "Water", "Happy plant"),
        reviews = listOf(
            Review("leaf", 5, "My plant is alive.", "3 days ago"),
            Review("Greg", 4, "Forgot anyway.", "1 month ago")
        )
    )
)


// ============================================================
// MAIN
// ============================================================

@Composable
fun CreativePlayStore() {

    var selectedTab by remember { mutableStateOf(0) }
    var selectedApp by remember { mutableStateOf<StoreApp?>(null) }
    var search by remember { mutableStateOf("") }

    val filteredApps = remember(search) {
        if (search.isBlank()) storeApps
        else storeApps.filter {
            it.name.contains(search, true) ||
            it.category.contains(search, true) ||
            it.developer.contains(search, true)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFFFCF7)
    ) {

        Column {

            when {
                selectedApp != null -> {
                    AppDetails(
                        app = selectedApp!!,
                        onBack = { selectedApp = null }
                    )
                }

                selectedTab == 0 -> {
                    HomePage(
                        apps = filteredApps,
                        search = search,
                        onSearchChange = { search = it },
                        onAppClick = { selectedApp = it }
                    )
                }

                selectedTab == 1 -> {
                    SearchPage(
                        search = search,
                        onSearchChange = { search = it },
                        apps = filteredApps,
                        onAppClick = { selectedApp = it }
                    )
                }

                selectedTab == 2 -> {
                    CategoriesPage(
                        apps = storeApps,
                        onAppClick = { selectedApp = it }
                    )
                }

                selectedTab == 3 -> {
                    ProfilePage()
                }
            }

            if (selectedApp == null) {
                BottomNavigation(
                    selected = selectedTab,
                    onSelect = { selectedTab = it }
                )
            }
        }
    }
}


// ============================================================
// HOME
// ============================================================

@Composable
fun HomePage(
    apps: List<StoreApp>,
    search: String,
    onSearchChange: (String) -> Unit,
    onAppClick: (StoreApp) -> Unit
) {

    LazyColumn(
        modifier = Modifier.weight(1f),
        contentPadding = PaddingValues(
            start = 18.dp,
            end = 18.dp,
            top = 18.dp,
            bottom = 30.dp
        ),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {

        item {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {

                    Text(
                        "Jœ Store",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Black
                    )

                    Text(
                        "things people made",
                        color = Color(0xFF77716A),
                        fontSize = 14.sp
                    )
                }

                Spacer(Modifier.weight(1f))

                Text(
                    "◉",
                    fontSize = 28.sp
                )
            }
        }

        item {
            SearchBar(
                value = search,
                onValueChange = onSearchChange
            )
        }

        item {
            Text(
                "✦ Picked for you",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            FeaturedCard(
                app = apps.firstOrNull() ?: storeApps.first(),
                onClick = {
                    onAppClick(apps.firstOrNull() ?: storeApps.first())
                }
            )
        }

        item {
            SectionTitle("Popular right now")
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(13.dp)
            ) {

                items(apps.take(4)) { app ->

                    SmallAppCard(
                        app = app,
                        onClick = { onAppClick(app) }
                    )
                }
            }
        }

        item {
            Spacer(Modifier.height(4.dp))

            SectionTitle("Because you downloaded one calculator")
        }

        item {
            RecommendationCard(
                app = storeApps[2],
                onClick = { onAppClick(storeApps[2]) }
            )
        }

        item {
            SectionTitle("People are making things")

            Spacer(Modifier.height(10.dp))

            apps.drop(1).forEach { app ->
                AppListItem(
                    app = app,
                    onClick = { onAppClick(app) }
                )

                Spacer(Modifier.height(10.dp))
            }
        }
    }
}


// ============================================================
// SEARCH
// ============================================================

@Composable
fun SearchPage(
    search: String,
    onSearchChange: (String) -> Unit,
    apps: List<StoreApp>,
    onAppClick: (StoreApp) -> Unit
) {

    Column(
        modifier = Modifier
            .weight(1f)
            .padding(18.dp)
    ) {

        Text(
            "Find something",
            fontSize = 29.sp,
            fontWeight = FontWeight.Black
        )

        Spacer(Modifier.height(15.dp))

        SearchBar(
            value = search,
            onValueChange = onSearchChange
        )

        Spacer(Modifier.height(20.dp))

        if (search.isBlank()) {

            Text(
                "Try:",
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                SearchChip("music") {
                    onSearchChange("music")
                }

                SearchChip("productivity") {
                    onSearchChange("productivity")
                }

                SearchChip("weather") {
                    onSearchChange("weather")
                }
            }

        } else {

            Text(
                "${apps.size} results",
                color = Color.Gray
            )

            Spacer(Modifier.height(10.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(apps) {
                    AppListItem(
                        app = it,
                        onClick = { onAppClick(it) }
                    )
                }
            }
        }
    }
}


// ============================================================
// CATEGORIES
// ============================================================

@Composable
fun CategoriesPage(
    apps: List<StoreApp>,
    onAppClick: (StoreApp) -> Unit
) {

    LazyColumn(
        modifier = Modifier.weight(1f),
        contentPadding = PaddingValues(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        item {
            Text(
                "Browse",
                fontSize = 30.sp,
                fontWeight = FontWeight.Black
            )
        }

        val categories = apps
            .groupBy { it.category }

        categories.forEach { (category, categoryApps) ->

            item {

                Column {

                    Text(
                        category,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        categoryApps.take(2).forEach {
                            SmallAppCard(
                                app = it,
                                onClick = { onAppClick(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}


// ============================================================
// PROFILE
// ============================================================

@Composable
fun ProfilePage() {

    Column(
        modifier = Modifier
            .weight(1f)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        Text(
            "Your stuff",
            fontSize = 30.sp,
            fontWeight = FontWeight.Black
        )

        Spacer(Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(27.dp))
                .background(Color(0xFFFFE7B8))
                .padding(20.dp)
        ) {

            Column {

                Text(
                    "👤",
                    fontSize = 38.sp
                )

                Text(
                    "Anonymous Human",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "Member since approximately now",
                    color = Color(0xFF756D60)
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        ProfileRow("⬇", "Installed apps", "12")

        ProfileRow("♡", "Wishlist", "4")

        ProfileRow("★", "Reviews written", "7")

        ProfileRow("⚙", "Settings", "")

        Spacer(Modifier.height(30.dp))

        Text(
            "You have excellent taste.",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Text(
            "We have no evidence for this.",
            color = Color.Gray
        )
    }
}


// ============================================================
// APP DETAILS
// ============================================================

@Composable
fun AppDetails(
    app: StoreApp,
    onBack: () -> Unit
) {

    var installed by remember { mutableStateOf(false) }
    var showReviews by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                "‹",
                fontSize = 38.sp,
                modifier = Modifier.clickable { onBack() }
            )

            Spacer(Modifier.width(12.dp))

            Text(
                "App details",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                bottom = 30.dp
            )
        ) {

            item {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    BigIcon(
                        app = app,
                        size = 105.dp
                    )

                    Spacer(Modifier.width(18.dp))

                    Column {

                        Text(
                            app.name,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Black
                        )

                        Text(
                            app.developer,
                            color = app.accent
                        )

                        Spacer(Modifier.height(5.dp))

                        Text(
                            "${app.category} · ${app.size}",
                            color = Color.Gray,
                            fontSize = 13.sp
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                Text(
                    app.description,
                    fontSize = 17.sp
                )

                Spacer(Modifier.height(15.dp))

                InstallButton(
                    installed = installed,
                    color = app.accent,
                    onClick = {
                        installed = !installed
                    }
                )
            }

            item {

                Spacer(Modifier.height(25.dp))

                AppStats(app)

                Spacer(Modifier.height(25.dp))

                Text(
                    "About this app",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    app.longDescription,
                    lineHeight = 22.sp,
                    color = Color(0xFF4F4A45)
                )
            }

            item {

                Spacer(Modifier.height(25.dp))

                Text(
                    "Screenshots",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(10.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    items(app.screenshots) {
                        FakeScreenshot(
                            title = it,
                            app = app
                        )
                    }
                }
            }

            item {

                Spacer(Modifier.height(28.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        "Reviews",
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.weight(1f))

                    Text(
                        "See all →",
                        color = app.accent,
                        modifier = Modifier.clickable {
                            showReviews = true
                        }
                    )
                }

                Spacer(Modifier.height(10.dp))

                app.reviews.take(2).forEach {
                    ReviewCard(it)
                    Spacer(Modifier.height(10.dp))
                }
            }
        }
    }

    if (showReviews) {

        Dialog(
            onDismissRequest = {
                showReviews = false
            }
        ) {

            Surface(
                shape = RoundedCornerShape(25.dp)
            ) {

                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .heightIn(max = 500.dp)
                ) {

                    Text(
                        "All reviews",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black
                    )

                    Spacer(Modifier.height(10.dp))

                    LazyColumn {
                        items(app.reviews) {
                            ReviewCard(it)
                            Spacer(Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}


// ============================================================
// COMPONENTS
// ============================================================

@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit
) {

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp)),
        placeholder = {
            Text("Search apps... maybe")
        },
        leadingIcon = {
            Text("⌕", fontSize = 25.sp)
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF0ECE6),
            unfocusedContainerColor = Color(0xFFF0ECE6),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}


@Composable
fun FeaturedCard(
    app: StoreApp,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(205.dp)
            .offset(x = (-2).dp) // intentionally slightly wrong
            .clip(RoundedCornerShape(28.dp))
            .background(app.color)
            .clickable { onClick() }
            .padding(20.dp)
    ) {

        Column {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                BigIcon(
                    app = app,
                    size = 67.dp
                )

                Spacer(Modifier.width(13.dp))

                Column {

                    Text(
                        "EDITOR'S PICK",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = app.accent
                    )

                    Text(
                        app.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black
                    )

                    Text(
                        app.description,
                        color = Color(0xFF5F5953)
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    "★ ${app.rating}",
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.width(12.dp))

                Text(
                    app.downloads,
                    color = Color.Gray
                )

                Spacer(Modifier.weight(1f))

                Text(
                    "FREE",
                    color = app.accent,
                    fontWeight = FontWeight.Black
                )
            }
        }

        Text(
            "NEW",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 5.dp, y = (-5).dp),
            color = Color(0xFFFF4D8D),
            fontWeight = FontWeight.Black,
            fontSize = 12.sp
        )
    }
}


@Composable
fun SmallAppCard(
    app: StoreApp,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .width(145.dp)
            .clickable { onClick() }
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(125.dp)
                .clip(RoundedCornerShape(21.dp))
                .background(app.color)
                .padding(15.dp)
        ) {

            Text(
                app.icon,
                fontSize = 48.sp
            )

            Text(
                "★ ${app.rating}",
                modifier = Modifier
                    .align(Alignment.BottomStart),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(7.dp))

        Text(
            app.name,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )

        Text(
            app.description,
            fontSize = 12.sp,
            color = Color.Gray,
            maxLines = 1
        )
    }
}


@Composable
fun RecommendationCard(
    app: StoreApp,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFF3EFE9))
            .clickable { onClick() }
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        BigIcon(
            app = app,
            size = 62.dp
        )

        Spacer(Modifier.width(14.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                app.name,
                fontWeight = FontWeight.Bold
            )

            Text(
                app.description,
                fontSize = 13.sp,
                color = Color.Gray
            )

            Text(
                "★ ${app.rating}",
                fontSize = 12.sp
            )
        }

        Text(
            "GET",
            color = app.accent,
            fontWeight = FontWeight.Black
        )
    }
}


@Composable
fun AppListItem(
    app: StoreApp,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable { onClick() }
            .padding(vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        BigIcon(
            app = app,
            size = 58.dp
        )

        Spacer(Modifier.width(13.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                app.name,
                fontWeight = FontWeight.Bold
            )

            Text(
                app.developer,
                fontSize = 12.sp,
                color = Color.Gray
            )

            Text(
                "★ ${app.rating} · ${app.downloads}",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        Text(
            "GET",
            modifier = Modifier.offset(x = 2.dp),
            color = app.accent,
            fontWeight = FontWeight.Black
        )
    }
}


@Composable
fun BigIcon(
    app: StoreApp,
    size: Dp
) {

    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(23.dp))
            .background(app.color),
        contentAlignment = Alignment.Center
    ) {

        Text(
            app.icon,
            fontSize = (size.value * .42f).sp
        )
    }
}


@Composable
fun InstallButton(
    installed: Boolean,
    color: Color,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .offset(x = 1.dp),
        shape = RoundedCornerShape(17.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (installed)
                Color(0xFFE8E5E0)
            else
                color,
            contentColor = if (installed)
                Color(0xFF625D57)
            else
                Color.White
        )
    ) {

        Text(
            if (installed) "✓ Installed" else "Install",
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun AppStats(app: StoreApp) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        Stat(
            "★",
            app.rating.toString(),
            "Rating"
        )

        Stat(
            "↓",
            app.downloads,
            "Downloads"
        )

        Stat(
            "▣",
            app.size,
            "Size"
        )
    }
}


@Composable
fun Stat(
    icon: String,
    value: String,
    label: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "$icon $value",
            fontWeight = FontWeight.Black
        )

        Text(
            label,
            fontSize = 11.sp,
            color = Color.Gray
        )
    }
}


@Composable
fun FakeScreenshot(
    title: String,
    app: StoreApp
) {

    Box(
        modifier = Modifier
            .width(170.dp)
            .height(230.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(app.color)
            .padding(15.dp)
    ) {

        Column {

            Text(
                title,
                fontWeight = FontWeight.Black,
                fontSize = 18.sp
            )

            Spacer(Modifier.height(15.dp))

            repeat(4) { index ->

                Box(
                    modifier = Modifier
                        .fillMaxWidth(
                            if (index == 2) .72f else .92f
                        )
                        .height(20.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (index == 1)
                                app.accent.copy(alpha = .25f)
                            else
                                Color.White.copy(alpha = .65f)
                        )
                )

                Spacer(Modifier.height(8.dp))
            }
        }

        Text(
            app.icon,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 4.dp, y = 4.dp),
            fontSize = 40.sp
        )
    }
}


@Composable
fun ReviewCard(review: Review) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(17.dp))
            .background(Color(0xFFF5F1EB))
            .padding(14.dp)
    ) {

        Row {

            Text(
                review.user,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.weight(1f))

            Text(
                "★".repeat(review.rating),
                fontSize = 12.sp
            )
        }

        Spacer(Modifier.height(6.dp))

        Text(
            review.text,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(4.dp))

        Text(
            review.time,
            fontSize = 11.sp,
            color = Color.Gray
        )
    }
}


@Composable
fun SectionTitle(title: String) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.weight(1f))

        Text(
            "→",
            fontSize = 20.sp,
            color = Color.Gray
        )
    }
}


@Composable
fun SearchChip(
    text: String,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFF0ECE6))
            .clickable { onClick() }
            .padding(
                horizontal = 15.dp,
                vertical = 9.dp
            )
    ) {

        Text(
            text,
            fontSize = 13.sp
        )
    }
}


@Composable
fun ProfileRow(
    icon: String,
    title: String,
    value: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            icon,
            fontSize = 22.sp
        )

        Spacer(Modifier.width(15.dp))

        Text(
            title,
            fontWeight = FontWeight.Medium
        )

        Spacer(Modifier.weight(1f))

        Text(
            value,
            color = Color.Gray
        )

        Text(
            "›",
            fontSize = 25.sp,
            color = Color.Gray
        )
    }
}


// ============================================================
// BOTTOM NAV
// ============================================================

@Composable
fun BottomNavigation(
    selected: Int,
    onSelect: (Int) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(Color(0xFFFFFCF7))
            .padding(horizontal = 15.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        BottomItem(
            "⌂",
            "Home",
            selected == 0
        ) {
            onSelect(0)
        }

        BottomItem(
            "⌕",
            "Search",
            selected == 1
        ) {
            onSelect(1)
        }

        BottomItem(
            "▦",
            "Browse",
            selected == 2
        ) {
            onSelect(2)
        }

        BottomItem(
            "◉",
            "You",
            selected == 3
        ) {
            onSelect(3)
        }
    }
}


@Composable
fun BottomItem(
    icon: String,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .width(70.dp)
            .clickable { onClick() }
            .offset(
                x = if (selected) (-1).dp else 0.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            icon,
            fontSize = 25.sp,
            color = if (selected)
                Color(0xFF7357FF)
            else
                Color(0xFF817B74)
        )

        Text(
            label,
            fontSize = 11.sp,
            fontWeight = if (selected)
                FontWeight.Bold
            else
                FontWeight.Normal,
            color = if (selected)
                Color(0xFF7357FF)
            else
                Color(0xFF817B74)
        )
    }
}
