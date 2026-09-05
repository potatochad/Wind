package com.productivity.wind.Screens.Settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun TestScreen() = CreativePlayStore()
// ============================================================
// ENTRY POINT
// ============================================================

@Composable
fun CreativePlayStore() {

    val nav = rememberNavController()

    MaterialTheme(
        colorScheme = lightColorScheme(
            background = Color(0xFFFFFCF6),
            surface = Color(0xFFFFFCF6),
            primary = Color(0xFF6750A4)
        )
    ) {

        NavHost(
            navController = nav,
            startDestination = "home"
        ) {

            composable("home") {
                StoreShell(nav, "home") {
                    HomeScreen(nav)
                }
            }

            composable("games") {
                StoreShell(nav, "games") {
                    GamesScreen(nav)
                }
            }

            composable("apps") {
                StoreShell(nav, "apps") {
                    AppsScreen(nav)
                }
            }

            composable("profile") {
                StoreShell(nav, "profile") {
                    ProfileScreen(nav)
                }
            }

            composable("search") {
                SearchScreen(nav)
            }

            composable("category/{name}") { entry ->
                val name = entry.arguments?.getString("name") ?: "Stuff"

                CategoryScreen(
                    nav = nav,
                    category = name
                )
            }

            composable("app/{id}") { entry ->
                val id = entry.arguments?.getString("id") ?: "0"

                AppDetailsScreen(
                    nav = nav,
                    app = apps.firstOrNull { it.id == id } ?: apps.first()
                )
            }
        }
    }
}


// ============================================================
// DATA
// ============================================================

data class StoreApp(
    val id: String,
    val name: String,
    val icon: String,
    val developer: String,
    val description: String,
    val category: String,
    val rating: Double,
    val downloads: String,
    val size: String,
    val color: Color,
    val featured: Boolean = false
)

val apps = listOf(

    StoreApp(
        id = "brain",
        name = "Brain Dump",
        icon = "🧠",
        developer = "Probably Me",
        description = "A tiny place to throw your thoughts before they start throwing themselves at you.",
        category = "Productivity",
        rating = 4.7,
        downloads = "12K+",
        size = "8.2 MB",
        color = Color(0xFFE7DFFF),
        featured = true
    ),

    StoreApp(
        id = "weather",
        name = "Weather-ish",
        icon = "🌦️",
        developer = "Cloud Department",
        description = "Tells you what the weather is. Sometimes correctly.",
        category = "Weather",
        rating = 3.8,
        downloads = "82K+",
        size = "14 MB",
        color = Color(0xFFDFF3FF)
    ),

    StoreApp(
        id = "lofi",
        name = "LoFi Something",
        icon = "🎧",
        developer = "night.wav",
        description = "Music for studying, working, staring at the ceiling, or doing absolutely nothing.",
        category = "Music",
        rating = 4.9,
        downloads = "240K+",
        size = "31 MB",
        color = Color(0xFFFFE2ED)
    ),

    StoreApp(
        id = "clean",
        name = "CleanMyLife",
        icon = "🧹",
        developer = "Monday Labs",
        description = "Organize your life into 47 categories you will forget about tomorrow.",
        category = "Productivity",
        rating = 4.2,
        downloads = "56K+",
        size = "21 MB",
        color = Color(0xFFE1F6E8)
    ),

    StoreApp(
        id = "pixel",
        name = "Pixel Garden",
        icon = "🌱",
        developer = "Tiny Potato",
        description = "Grow tiny pixel plants. They don't need watering. Probably.",
        category = "Games",
        rating = 4.6,
        downloads = "91K+",
        size = "74 MB",
        color = Color(0xFFE4F1D1)
    ),

    StoreApp(
        id = "calculator",
        name = "Calculator",
        icon = "🧮",
        developer = "Calculator Team",
        description = "It adds numbers.",
        category = "Tools",
        rating = 4.5,
        downloads = "1M+",
        size = "3 MB",
        color = Color(0xFFFFEBCB)
    ),

    StoreApp(
        id = "nothing",
        name = "Nothing",
        icon = "⬜",
        developer = "Nothing Inc.",
        description = "Literally nothing. You asked for it.",
        category = "Entertainment",
        rating = 4.1,
        downloads = "19K+",
        size = "420 KB",
        color = Color(0xFFEDEDED)
    ),

    StoreApp(
        id = "focus",
        name = "Focus-ish",
        icon = "🎯",
        developer = "Some Guy",
        description = "Blocks distractions for exactly 17 minutes because that seems reasonable.",
        category = "Productivity",
        rating = 4.4,
        downloads = "38K+",
        size = "11 MB",
        color = Color(0xFFFFE0D5)
    )
)


// ============================================================
// SHELL
// ============================================================

@Composable
fun StoreShell(
    nav: NavHostController,
    selected: String,
    content: @Composable () -> Unit
) {

    Scaffold(
        containerColor = Color(0xFFFFFCF6),

        bottomBar = {
            BottomNavigation(
                nav = nav,
                selected = selected
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            content()
        }
    }
}


// ============================================================
// BOTTOM NAV
// ============================================================

@Composable
fun BottomNavigation(
    nav: NavHostController,
    selected: String
) {

    NavigationBar(
        containerColor = Color(0xFFFFFCF6)
    ) {

        NavigationBarItem(
            selected = selected == "home",
            onClick = {
                nav.navigate("home") {
                    popUpTo("home")
                    launchSingleTop = true
                }
            },
            icon = { Text("⌂", fontSize = 25.sp) },
            label = { Text("Home") }
        )

        NavigationBarItem(
            selected = selected == "games",
            onClick = {
                nav.navigate("games") {
                    launchSingleTop = true
                }
            },
            icon = { Text("🎮") },
            label = { Text("Games") }
        )

        NavigationBarItem(
            selected = selected == "apps",
            onClick = {
                nav.navigate("apps") {
                    launchSingleTop = true
                }
            },
            icon = { Text("▦", fontSize = 24.sp) },
            label = { Text("Apps") }
        )

        NavigationBarItem(
            selected = selected == "profile",
            onClick = {
                nav.navigate("profile") {
                    launchSingleTop = true
                }
            },
            icon = { Text("◉", fontSize = 22.sp) },
            label = { Text("You") }
        )
    }
}


// ============================================================
// HOME
// ============================================================

@Composable
fun HomeScreen(nav: NavHostController) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 18.dp,
            end = 18.dp,
            top = 18.dp,
            bottom = 20.dp
        ),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        item {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        "Jœ Store",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Black
                    )

                    Text(
                        "apps made by questionable humans",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Box(
                    modifier = Modifier
                        .size(43.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE9E0FF))
                        .clickable {
                            nav.navigate("profile")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text("J", fontWeight = FontWeight.Bold)
                }
            }
        }

        item {

            SearchBar(
                nav = nav
            )
        }

        item {

            Text(
                "✦ Today's weird picks",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {

            FeaturedCard(
                app = apps.first { it.featured },
                onClick = {
                    nav.navigate("app/${apps.first().id}")
                }
            )
        }

        item {

            SectionTitle(
                title = "Popular",
                action = "See all →"
            )
        }

        item {

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(13.dp)
            ) {

                items(apps.take(5)) { app ->

                    SmallAppCard(
                        app = app,
                        onClick = {
                            nav.navigate("app/${app.id}")
                        }
                    )
                }
            }
        }

        item {

            CategoryStrip(
                nav = nav
            )
        }

        item {

            SectionTitle(
                title = "Because you downloaded one calculator",
                action = ""
            )
        }

        item {

            RecommendationCard(
                apps.random()
            ) {
                nav.navigate("app/${apps.random().id}")
            }
        }

        item {

            Spacer(Modifier.height(10.dp))

            Text(
                "That's it. Go outside.",
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                color = Color.LightGray,
                fontSize = 12.sp
            )
        }
    }
}


// ============================================================
// SEARCH
// ============================================================

@Composable
fun SearchBar(nav: NavHostController) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .clip(RoundedCornerShape(19.dp))
            .background(Color(0xFFF0ECE5))
            .clickable {
                nav.navigate("search")
            }
            .padding(horizontal = 17.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            "⌕",
            fontSize = 27.sp
        )

        Spacer(Modifier.width(12.dp))

        Text(
            "Search apps & games...",
            color = Color.Gray
        )
    }
}


@Composable
fun SearchScreen(nav: NavHostController) {

    var query by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFCF6))
            .padding(18.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                "‹",
                fontSize = 35.sp,
                modifier = Modifier
                    .clickable { nav.popBackStack() }
                    .padding(end = 10.dp)
            )

            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.weight(1f),
                singleLine = true,
                placeholder = {
                    Text("Search literally anything")
                },
                shape = RoundedCornerShape(18.dp)
            )
        }

        Spacer(Modifier.height(25.dp))

        val results = apps.filter {
            it.name.contains(query, true) ||
                    it.category.contains(query, true) ||
                    it.description.contains(query, true)
        }

        if (query.isEmpty()) {

            Text(
                "Try searching for:",
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(
                    listOf(
                        "music",
                        "productivity",
                        "games",
                        "something useless"
                    )
                ) {

                    AssistChip(
                        onClick = { query = it },
                        label = { Text(it) }
                    )
                }
            }

        } else {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                items(results) { app ->

                    AppListItem(
                        app = app,
                        onClick = {
                            nav.navigate("app/${app.id}")
                        }
                    )
                }
            }
        }
    }
}


// ============================================================
// FEATURED
// ============================================================

@Composable
fun FeaturedCard(
    app: StoreApp,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .offset(x = (-2).dp)
            .clip(RoundedCornerShape(28.dp))
            .background(app.color)
            .clickable { onClick() }
            .padding(21.dp)
    ) {

        Column {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    app.icon,
                    fontSize = 55.sp
                )

                Spacer(Modifier.weight(1f))

                Text(
                    "NEW",
                    modifier = Modifier.offset(x = 4.dp, y = (-5).dp),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFFFF4D8D)
                )
            }

            Spacer(Modifier.height(4.dp))

            Text(
                app.name,
                fontSize = 26.sp,
                fontWeight = FontWeight.Black
            )

            Text(
                app.description,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Color(0xFF595461)
            )

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
                    color = Color(0xFF7056E8),
                    fontWeight = FontWeight.Black
                )
            }
        }
    }
}


// ============================================================
// SMALL CARD
// ============================================================

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
                .size(145.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 23.dp,
                        topEnd = 23.dp,
                        bottomStart = 17.dp,
                        bottomEnd = 25.dp
                    )
                )
                .background(app.color),
            contentAlignment = Alignment.Center
        ) {

            Text(
                app.icon,
                fontSize = 57.sp
            )
        }

        Spacer(Modifier.height(8.dp))

        Text(
            app.name,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            app.developer,
            fontSize = 12.sp,
            color = Color.Gray
        )

        Text(
            "★ ${app.rating}",
            fontSize = 12.sp
        )
    }
}


// ============================================================
// CATEGORY STRIP
// ============================================================

@Composable
fun CategoryStrip(nav: NavHostController) {

    val categories = listOf(
        "Productivity" to "🧠",
        "Music" to "🎧",
        "Games" to "🎮",
        "Weather" to "🌦️",
        "Tools" to "🔧"
    )

    Column {

        Text(
            "Browse by mood",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(11.dp))

        Row(
            modifier = Modifier.horizontalScroll(
                rememberScrollState()
            ),
            horizontalArrangement = Arrangement.spacedBy(9.dp)
        ) {

            categories.forEach { (name, icon) ->

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFF1ECE4))
                        .clickable {
                            nav.navigate("category/$name")
                        }
                        .padding(
                            horizontal = 14.dp,
                            vertical = 10.dp
                        )
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(icon)

                        Spacer(Modifier.width(6.dp))

                        Text(
                            name,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}


// ============================================================
// RECOMMENDATION
// ============================================================

@Composable
fun RecommendationCard(
    app: StoreApp,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFF2EEE8))
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(17.dp))
                .background(app.color),
            contentAlignment = Alignment.Center
        ) {
            Text(app.icon, fontSize = 31.sp)
        }

        Spacer(Modifier.width(13.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                app.name,
                fontWeight = FontWeight.Bold
            )

            Text(
                app.description,
                fontSize = 12.sp,
                color = Color.Gray,
                maxLines = 2
            )
        }

        Text(
            "→",
            fontSize = 22.sp
        )
    }
}


// ============================================================
// SECTION TITLE
// ============================================================

@Composable
fun SectionTitle(
    title: String,
    action: String
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        if (action.isNotEmpty()) {

            Text(
                action,
                color = Color(0xFF7056E8),
                fontSize = 13.sp
            )
        }
    }
}


// ============================================================
// APP LIST
// ============================================================

@Composable
fun AppListItem(
    app: StoreApp,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(9.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(66.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(app.color),
            contentAlignment = Alignment.Center
        ) {

            Text(
                app.icon,
                fontSize = 32.sp
            )
        }

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
                "★ ${app.rating}   ${app.category}",
                fontSize = 11.sp,
                color = Color.Gray
            )
        }

        Text(
            "›",
            fontSize = 27.sp,
            color = Color.Gray
        )
    }
}


// ============================================================
// GAMES
// ============================================================

@Composable
fun GamesScreen(nav: NavHostController) {

    CategoryPage(
        nav = nav,
        title = "Games",
        subtitle = "Games that probably won't ruin your evening.",
        filter = { it.category == "Games" }
    )
}


// ============================================================
// APPS
// ============================================================

@Composable
fun AppsScreen(nav: NavHostController) {

    CategoryPage(
        nav = nav,
        title = "Apps",
        subtitle = "Things your phone definitely needed.",
        filter = { it.category != "Games" }
    )
}


@Composable
fun CategoryPage(
    nav: NavHostController,
    title: String,
    subtitle: String,
    filter: (StoreApp) -> Boolean
) {

    val list = apps.filter(filter)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(18.dp),
        verticalArrangement = Arrangement.spacedBy(11.dp)
    ) {

        item {

            Text(
                title,
                fontSize = 31.sp,
                fontWeight = FontWeight.Black
            )

            Text(
                subtitle,
                color = Color.Gray,
                fontSize = 13.sp
            )

            Spacer(Modifier.height(15.dp))
        }

        items(list) { app ->

            AppListItem(
                app = app,
                onClick = {
                    nav.navigate("app/${app.id}")
                }
            )
        }
    }
}


// ============================================================
// CATEGORY DETAILS
// ============================================================

@Composable
fun CategoryScreen(
    nav: NavHostController,
    category: String
) {

    val categoryApps = apps.filter {
        it.category.equals(category, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFCF6))
            .padding(18.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                "‹",
                fontSize = 35.sp,
                modifier = Modifier.clickable {
                    nav.popBackStack()
                }
            )

            Spacer(Modifier.width(10.dp))

            Text(
                category,
                fontSize = 27.sp,
                fontWeight = FontWeight.Black
            )
        }

        Spacer(Modifier.height(20.dp))

        if (categoryApps.isEmpty()) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    "Nothing here yet.\nWe forgot this category.",
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    color = Color.Gray
                )
            }

        } else {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                items(categoryApps) { app ->

                    AppListItem(
                        app = app,
                        onClick = {
                            nav.navigate("app/${app.id}")
                        }
                    )
                }
            }
        }
    }
}


// ============================================================
// APP DETAILS
// ============================================================

@Composable
fun AppDetailsScreen(
    nav: NavHostController,
    app: StoreApp
) {

    var installed by remember { mutableStateOf(false) }
    var liked by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFCF6))
    ) {

        item {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 15.dp,
                        end = 18.dp,
                        top = 15.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    "‹",
                    fontSize = 36.sp,
                    modifier = Modifier.clickable {
                        nav.popBackStack()
                    }
                )

                Spacer(Modifier.weight(1f))

                Text(
                    if (liked) "♥" else "♡",
                    fontSize = 28.sp,
                    modifier = Modifier.clickable {
                        liked = !liked
                    }
                )

                Spacer(Modifier.width(18.dp))

                Text(
                    "⋮",
                    fontSize = 28.sp
                )
            }
        }

        item {

            Spacer(Modifier.height(15.dp))

            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(105.dp)
                            .clip(RoundedCornerShape(29.dp))
                            .background(app.color),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            app.icon,
                            fontSize = 53.sp
                        )
                    }

                    Spacer(Modifier.width(17.dp))

                    Column {

                        Text(
                            app.name,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Black
                        )

                        Text(
                            app.developer,
                            color = Color(0xFF7056E8),
                            fontSize = 14.sp
                        )

                        Spacer(Modifier.height(5.dp))

                        Text(
                            "★ ${app.rating}  •  ${app.downloads}",
                            fontSize = 13.sp
                        )
                    }
                }

                Spacer(Modifier.height(22.dp))

                Button(
                    onClick = {
                        installed = !installed
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(53.dp),
                    shape = RoundedCornerShape(17.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            if (installed)
                                Color(0xFFDDEBDD)
                            else
                                Color(0xFF7056E8)
                    )
                ) {

                    Text(
                        if (installed) "✓ Installed" else "Install",
                        color =
                            if (installed)
                                Color(0xFF416644)
                            else
                                Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(17.dp))

                Text(
                    "Contains absolutely no ads*",
                    fontSize = 11.sp,
                    color = Color.Gray
                )

                Text(
                    "*we hope",
                    fontSize = 8.sp,
                    color = Color.LightGray,
                    modifier = Modifier.offset(x = 8.dp)
                )
            }
        }

        item {

            Spacer(Modifier.height(27.dp))

            HorizontalDivider()
        }

        item {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    "About this app",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(9.dp))

                Text(
                    app.description,
                    lineHeight = 22.sp
                )

                Spacer(Modifier.height(13.dp))

                Text(
                    "This app was lovingly assembled by someone who probably should have been sleeping.",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }

        item {

            InfoRow(
                "Version",
                "probably 1.0"
            )

            InfoRow(
                "Size",
                app.size
            )

            InfoRow(
                "Downloads",
                app.downloads
            )

            InfoRow(
                "Age",
                "Everyone"
            )
        }

        item {

            Spacer(Modifier.height(18.dp))

            HorizontalDivider()
        }

        item {

            ReviewsSection(app)
        }

        item {

            Spacer(Modifier.height(40.dp))

            Text(
                "You made it to the bottom.",
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                color = Color.LightGray,
                fontSize = 11.sp
            )

            Spacer(Modifier.height(30.dp))
        }
    }
}


// ============================================================
// INFO ROW
// ============================================================

@Composable
fun InfoRow(
    title: String,
    value: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp,
                vertical = 9.dp
            )
    ) {

        Text(
            title,
            color = Color.Gray,
            modifier = Modifier.weight(1f)
        )

        Text(
            value,
            fontWeight = FontWeight.Medium
        )
    }
}


// ============================================================
// REVIEWS
// ============================================================

@Composable
fun ReviewsSection(app: StoreApp) {

    val reviews = listOf(
        "Honestly better than expected.",
        "I downloaded this at 2:14am. No regrets.",
        "Why is the button slightly too far left 😂",
        "It does what it says.",
        "Needs dark mode.",
        "I like the little icon."
    )

    Column(
        modifier = Modifier.padding(20.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                "Ratings & reviews",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.weight(1f))

            Text(
                "See all",
                color = Color(0xFF7056E8),
                fontSize = 13.sp
            )
        }

        Spacer(Modifier.height(14.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                app.rating.toString(),
                fontSize = 43.sp,
                fontWeight = FontWeight.Black
            )

            Spacer(Modifier.width(17.dp))

            Column {

                Text("★★★★★")

                Text(
                    "Based on very real people",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(Modifier.height(17.dp))

        reviews.take(3).forEach { review ->

            ReviewCard(review)

            Spacer(Modifier.height(9.dp))
        }
    }
}


@Composable
fun ReviewCard(text: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(17.dp))
            .background(Color(0xFFF3EFE8))
            .padding(13.dp)
    ) {

        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(Color(0xFFE3D9FF)),
            contentAlignment = Alignment.Center
        ) {

            Text(
                listOf("A", "M", "J", "K").random(),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.width(11.dp))

        Column {

            Text(
                "Anonymous Human",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                "★★★★★",
                fontSize = 10.sp
            )

            Spacer(Modifier.height(3.dp))

            Text(
                text,
                fontSize = 13.sp
            )
        }
    }
}


// ============================================================
// PROFILE
// ============================================================

@Composable
fun ProfileScreen(nav: NavHostController) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(13.dp)
    ) {

        item {

            Spacer(Modifier.height(5.dp))

            Box(
                modifier = Modifier
                    .size(84.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE7DFFF)),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    "J",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Black
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                "You",
                fontSize = 29.sp,
                fontWeight = FontWeight.Black
            )

            Text(
                "Professional app downloader",
                color = Color.Gray
            )
        }
    }
}
        
