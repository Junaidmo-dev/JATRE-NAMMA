package com.yielders.jatrenammapride.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.filled.Assistant
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Toys
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Luggage
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.yielders.jatrenammapride.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yielders.jatrenammapride.JatreViewModel
import com.yielders.jatrenammapride.data.CrowdLevel
import com.yielders.jatrenammapride.data.CulturalStory
import com.yielders.jatrenammapride.data.EventStatus
import com.yielders.jatrenammapride.data.JatreEvent
import com.yielders.jatrenammapride.data.LostFoundPost
import com.yielders.jatrenammapride.data.MapLocation
import kotlinx.coroutines.delay

// Brand tokens - Refined for Premium Feel
internal val Saffron       = Color(0xFFFF9933)
internal val SaffronDark   = Color(0xFFE67E22)
internal val TealPrimary   = Color(0xFF064E3B)   // Deeper, more premium emerald teal
internal val TealSecondary = Color(0xFF10B981)   // Vibrant emerald accent
internal val TealLight     = Color(0xFFF0FDF4)
internal val Danger        = Color(0xFFEF4444)
internal val Green         = Color(0xFF10B981)
internal val Marigold      = Color(0xFFF59E0B)
internal val Ink           = Color(0xFF0F172A)   // Slate-900 for modern deep black
internal val SubText       = Color(0xFF64748B)   // Slate-500 for subtle secondary text
internal val PageBg        = Color(0xFFF8FAFC)   // Slate-50 near-white
internal val Hairline      = Color(0xFFE2E8F0)
internal val SoftPanel     = Color(0xFFF1F5F9)

// Gradient Sets
internal val PremiumGradient = Brush.verticalGradient(listOf(TealPrimary, Color(0xFF065F46)))
internal val SaffronGradient = Brush.horizontalGradient(listOf(Saffron, SaffronDark))

// Light color scheme
private val AppColorScheme = androidx.compose.material3.lightColorScheme(
    primary          = TealPrimary,
    onPrimary        = Color.White,
    primaryContainer = TealLight,
    onPrimaryContainer = TealPrimary,
    secondary        = Saffron,
    onSecondary      = Color.White,
    secondaryContainer = Color(0xFFFFF3E0),
    onSecondaryContainer = SaffronDark,
    tertiary         = Green,
    onTertiary       = Color.White,
    background       = PageBg,
    onBackground     = Ink,
    surface          = Color.White,
    onSurface        = Ink,
    surfaceVariant   = Color(0xFFF1F5F9),
    onSurfaceVariant = SubText,
    error            = Danger,
    onError          = Color.White,
    outline          = Color(0xFFE2E8F0)
)

@Composable
fun JatreTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = AppColorScheme, content = content)
}

fun Modifier.bounceClick(
    interactionSource: MutableInteractionSource
) = composed {
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "bounce"
    )
    this.graphicsLayer {
        scaleX = scale
        scaleY = scale
    }
}

/**
 * A full-bleed image with a gradient scrim at the bottom so overlaid text is always readable.
 * Falls back gracefully to a solid gradient if the image fails to load.
 */
@Composable
private fun FestivalImage(
    resId: Int,
    modifier: Modifier = Modifier,
    scrimColors: List<Color> = listOf(Color.Transparent, Color(0xCC000000))
) {
    Box(modifier) {
        AsyncImage(
            model = resId,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        // gradient scrim for text legibility
        Box(
            Modifier
                .matchParentSize()
                .background(Brush.verticalGradient(scrimColors))
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JatreApp(viewModel: JatreViewModel = viewModel()) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route.orEmpty()
    var showChat by remember { mutableStateOf(false) }
    val showBars = route != "splash" && !route.startsWith("event/") && !route.startsWith("story/")

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            if (showBars) {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                "Jatre Namma Pride",
                                fontWeight = FontWeight.ExtraBold,
                                style = MaterialTheme.typography.titleLarge,
                                color = TealPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White.copy(alpha = 0.95f),
                        titleContentColor = TealPrimary,
                        actionIconContentColor = TealPrimary
                    ),
                    actions = {
                        Surface(
                            onClick = { showChat = true },
                            shape = CircleShape,
                            color = TealLight,
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Icon(
                                Icons.Outlined.AutoAwesome,
                                contentDescription = "AI Guide",
                                tint = TealPrimary,
                                modifier = Modifier.padding(8.dp).size(20.dp)
                            )
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (showBars) JatreBottomBar(navController)
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {
            composable("home") { HomeScreen(viewModel, navController, onOpenChat = { showChat = true }) }
            composable("schedule") { ScheduleScreen(viewModel, navController) }
            composable(
                "event/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { entry ->
                val events by viewModel.events.collectAsState()
                val event = events.firstOrNull { it.id == entry.arguments?.getString("id") }
                EventDetailScreen(event, navController)
            }
            composable("lostfound") { LostFoundScreen(viewModel) }
            composable("map") { MapScreen(viewModel) }
            composable("stories") { StoriesScreen(viewModel, navController) }
            composable(
                "story/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { entry ->
                val stories by viewModel.stories.collectAsState()
                val story = stories.firstOrNull { it.id == entry.arguments?.getString("id") }
                StoryDetailScreen(story, navController)
            }
            composable("safety") { SafetyScreen(viewModel) }
        }
    }

    if (showChat) {
        ChatSheet(viewModel, onDismiss = { showChat = false })
    }
}

@Composable
private fun JatreBottomBar(navController: NavController) {
    val entry by navController.currentBackStackEntryAsState()
    val currentRoute = entry?.destination?.route
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {
        listOf(
            BottomItem("home",      "Home",     Icons.Outlined.Home),
            BottomItem("schedule",  "Schedule", Icons.Outlined.CalendarMonth),
            BottomItem("lostfound", "Lost",     Icons.Outlined.Search),
            BottomItem("map",       "Map",      Icons.Outlined.Explore),
            BottomItem("safety",    "Safety",   Icons.Outlined.Shield)
        ).forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label, style = MaterialTheme.typography.labelSmall) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor   = Color.White,
                    selectedTextColor   = TealPrimary,
                    indicatorColor      = TealPrimary,
                    unselectedIconColor = SubText,
                    unselectedTextColor = SubText
                )
            )
        }
    }
}

private data class BottomItem(val route: String, val label: String, val icon: ImageVector)

@Composable
private fun PremiumCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val colors = CardDefaults.elevatedCardColors(
        containerColor = Color.White,
        contentColor = Ink
    )
    val elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.5.dp, pressedElevation = 0.dp)
    
    if (onClick == null) {
        ElevatedCard(
            modifier = modifier.border(BorderStroke(1.dp, Hairline), RoundedCornerShape(16.dp)),
            colors = colors,
            elevation = elevation,
            shape = RoundedCornerShape(16.dp)
        ) { content() }
    } else {
        val interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
        ElevatedCard(
            onClick = onClick,
            modifier = modifier
                .bounceClick(interactionSource)
                .border(BorderStroke(1.dp, Hairline), RoundedCornerShape(16.dp)),
            interactionSource = interactionSource,
            colors = colors,
            elevation = elevation,
            shape = RoundedCornerShape(16.dp)
        ) { content() }
    }
}

/**
 * StatusChip with two display modes:
 * - onDark=false (default): tinted pill on light card surface.
 * - onDark=true: used on gradient hero banners where the chip needs a solid dark scrim so white text is readable.
 */
@Composable
private fun StatusChip(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    onDark: Boolean = false
) {
    if (onDark) {
        // Solid semi-transparent dark scrim so white text always reads on gradient
        Surface(
            modifier = modifier,
            color = Ink.copy(alpha = 0.72f),
            shape = RoundedCornerShape(99.dp)
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                color = Color.White,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
        }
    } else {
        // Use a darker text color when chip background would be light (e.g. Gray, Marigold)
        // Rough luminance: if red+green+blue > threshold, pick dark text
        val isLightColor = (color.red * 0.299f + color.green * 0.587f + color.blue * 0.114f) > 0.55f
        val textColor = if (isLightColor) Ink else color
        Surface(
            modifier = modifier,
            color = color.copy(alpha = 0.10f),
            shape = RoundedCornerShape(99.dp),
            border = BorderStroke(1.dp, color.copy(alpha = 0.22f))
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                color = textColor,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ScreenIntro(title: String, subtitle: String, icon: ImageVector, imageRes: Int? = null) {
    PremiumCard {
        Box(Modifier.fillMaxWidth()) {
            if (imageRes != null) {
                FestivalImage(
                    resId = imageRes,
                    modifier = Modifier.fillMaxWidth().height(140.dp),
                    scrimColors = listOf(Color.Transparent, Ink.copy(alpha = 0.8f))
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (imageRes == null) {
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .height(44.dp)
                            .clip(RoundedCornerShape(99.dp))
                            .background(TealPrimary)
                    )
                }
                Spacer(Modifier.width(14.dp))
                Column {
                    val textColor = if (imageRes != null) Color.White else MaterialTheme.colorScheme.onSurface
                    val subTextColor = if (imageRes != null) Color.White.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant
                    Text(title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = textColor)
                    Text(subtitle, color = subTextColor)
                }
            }
        }
    }
}

@Composable
private fun SplashScreen(onDone: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000)
        onDone()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PremiumGradient),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                modifier = Modifier.size(120.dp),
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Outlined.Place, contentDescription = null, modifier = Modifier.size(64.dp), tint = TealPrimary)
                }
            }
            Spacer(Modifier.height(32.dp))
            Text("JATRE", color = Color.White, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Black, letterSpacing = androidx.compose.ui.unit.TextUnit(4f, androidx.compose.ui.unit.TextUnitType.Sp))
            Text("NAMMA PRIDE", color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, letterSpacing = androidx.compose.ui.unit.TextUnit(2f, androidx.compose.ui.unit.TextUnitType.Sp))
            Spacer(Modifier.height(48.dp))
            CircularProgressIndicator(color = Saffron, strokeWidth = 3.dp, modifier = Modifier.size(28.dp))
        }
    }
}

@Composable
private fun HomeScreen(viewModel: JatreViewModel, navController: NavController, onOpenChat: () -> Unit) {
    val posts by viewModel.posts.collectAsState()
    val events by viewModel.events.collectAsState()
    val stories by viewModel.stories.collectAsState()
    
    val cards = listOf(
        FeatureCard("Live Schedule",   "${events.count { it.status == EventStatus.Ongoing }} ongoing",      Icons.Outlined.CalendarMonth,  "schedule"),
        FeatureCard("Lost & Found",    "${posts.count { it.status == "active" }} active posts",                        Icons.Outlined.Search,         "lostfound"),
        FeatureCard("Jatre Map",       "${viewModel.mapLocations.size} marked spots",                                  Icons.Outlined.Explore,        "map"),
        FeatureCard("Cultural Stories","${stories.size} legends",                                            Icons.Outlined.AutoStories,    "stories"),
        FeatureCard("Parking & Safety","Emergency contacts & zones",                                                   Icons.Outlined.GppMaybe,       "safety"),
        FeatureCard("AI Guide",        "Ask in Kannada or English",                                                    Icons.Outlined.AutoAwesome,    "chat")
    )

    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(maxLineSpan) }) {
            LiveNowHero(events.firstOrNull { it.status == EventStatus.Ongoing } ?: events.firstOrNull() ?: JatreEvent())
        }
        item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(maxLineSpan) }) {
            AssistantPrompt(onOpenChat)
        }
        item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(maxLineSpan) }) {
            UpcomingEventsStrip(events, navController)
        }
        items(cards) { card ->
            PremiumCard(
                onClick = {
                    if (card.route == "chat") onOpenChat() else navController.navigate(card.route)
                }
            ) {
                Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Surface(
                        Modifier.size(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = featureColor(card.route).copy(alpha = 0.12f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(card.icon, contentDescription = null, tint = featureColor(card.route), modifier = Modifier.size(26.dp))
                        }
                    }
                    Column {
                        Text(card.title, fontWeight = FontWeight.Black, style = MaterialTheme.typography.titleMedium,
                            color = Ink)
                        Spacer(Modifier.height(4.dp))
                        Text(card.subtitle, color = SubText, maxLines = 2, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

private data class FeatureCard(val title: String, val subtitle: String, val icon: ImageVector, val route: String)

@Composable
private fun LiveNowHero(event: JatreEvent) {
    PremiumCard {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 10f)
        ) {
            FestivalImage(
                resId = R.drawable.jatre_hero_banner,
                modifier = Modifier.matchParentSize(),
                scrimColors = listOf(
                    Color.Transparent,
                    Color(0x22000000),
                    Color(0xBB000000)
                )
            )
            
            // Pulsing Live Indicator
            Row(
                modifier = Modifier.align(Alignment.TopStart).padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = Danger,
                    shape = CircleShape,
                    modifier = Modifier.size(8.dp)
                ) {}
                Spacer(Modifier.width(6.dp))
                StatusChip("LIVE NOW", Color.White, onDark = true)
            }

            Column(
                Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    event.location.uppercase(), 
                    color = Color.White.copy(alpha = 0.7f), 
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = androidx.compose.ui.unit.TextUnit(1.5f, androidx.compose.ui.unit.TextUnitType.Sp)
                )
                Text(
                    event.name, 
                    color = Color.White, 
                    style = MaterialTheme.typography.headlineSmall, 
                    fontWeight = FontWeight.Black
                )
                Spacer(Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.PlayCircle, contentDescription = null, tint = Saffron, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(event.time, color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
private fun AssistantPrompt(onOpenChat: () -> Unit) {
    PremiumCard(onClick = onOpenChat) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
            Box(Modifier.size(42.dp).clip(RoundedCornerShape(12.dp)).background(TealLight), contentAlignment = Alignment.Center) {
                Icon(Icons.Outlined.AutoAwesome, contentDescription = null, tint = TealPrimary, modifier = Modifier.size(22.dp))
            }
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text("Jatre Assistant", color = TealPrimary, fontWeight = FontWeight.Bold)
                Text("Ask for stalls, toilets, parking, help desk, or event timings.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun UpcomingEventsStrip(events: List<JatreEvent>, navController: NavController) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Upcoming Events", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            TextButton(onClick = { navController.navigate("schedule") }) { Text("View All") }
        }
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(events) { event ->
                UpcomingEventCard(event) { navController.navigate("event/${event.id}") }
            }
        }
    }
}

@Composable
private fun UpcomingEventCard(event: JatreEvent, onClick: () -> Unit) {
    val imageRes = when (event.status) {
        EventStatus.Ongoing -> R.drawable.jatre_hero_banner
        EventStatus.Upcoming -> R.drawable.jatre_procession
        EventStatus.Completed -> R.drawable.jatre_stalls
    }
    PremiumCard(modifier = Modifier.width(240.dp), onClick = onClick) {
        Column(Modifier.padding(0.dp), verticalArrangement = Arrangement.spacedBy(0.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp))
            ) {
                FestivalImage(
                    resId = imageRes,
                    modifier = Modifier.matchParentSize(),
                    scrimColors = listOf(Color.Transparent, Color(0x99000000))
                )
                StatusChip(
                    event.status.label,
                    TealPrimary,
                    modifier = Modifier.align(Alignment.TopEnd).padding(8.dp),
                    onDark = true
                )
            }
            Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(event.name, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface)
                Text(event.time, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun ScheduleScreen(viewModel: JatreViewModel, navController: NavController) {
    val events by viewModel.events.collectAsState()
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            ScreenIntro("Live Schedule", "Tap an event for details, crowd level, and reminder actions.", Icons.Outlined.CalendarToday)
        }
        items(events) { event ->
            EventCard(event, onClick = { navController.navigate("event/${event.id}") })
        }
    }
}

@Composable
private fun EventCard(event: JatreEvent, onClick: () -> Unit) {
    val tint = statusColor(event.status)
    val imageRes = when {
        event.name.contains("Chariot") || event.name.contains("Procession") -> R.drawable.jatre_chariot_detail
        event.name.contains("Veeragase") || event.name.contains("Dance") -> R.drawable.jatre_dance_detail
        event.name.contains("Food") || event.name.contains("Mela") -> R.drawable.jatre_food_detail
        event.name.contains("Lamp") || event.name.contains("Deepotsava") -> R.drawable.jatre_temple_night
        else -> R.drawable.jatre_stalls
    }
    PremiumCard(onClick = onClick) {
        Row(
            Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(Modifier.size(80.dp).clip(RoundedCornerShape(10.dp))) {
                FestivalImage(resId = imageRes, modifier = Modifier.matchParentSize())
            }
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(event.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface)
                Text(event.time, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(event.location, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
            }
            StatusChip(event.status.label, tint)
        }
    }
}

@Composable
private fun EventDetailScreen(event: JatreEvent?, navController: NavController) {
    if (event == null) {
        MissingScreen("Event not found", navController)
        return
    }
    val context = LocalContext.current
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        HeroHeader(event.name, event.location, Icons.Outlined.DateRange)
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(event.time, modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                IconButton(onClick = {
                    shareText(context, "${event.name}\n${event.time}\n${event.location}\n\n${event.description}")
                }) { Icon(Icons.Outlined.Share, contentDescription = "Share event", modifier = Modifier.size(24.dp)) }
            }
            Text(event.description)
            CrowdMeter(event.crowdLevel)
            ExpandableSection("Cultural significance", event.significance)
            Button(
                onClick = { },
                enabled = event.status != EventStatus.Completed,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = TealPrimary)
            ) {
                Icon(Icons.Outlined.NotificationsActive, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text("Set Reminder")
            }
            OutlinedButton(onClick = { navController.popBackStack() }, modifier = Modifier.fillMaxWidth()) {
                Text("Back to Schedule")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LostFoundScreen(viewModel: JatreViewModel) {
    val posts by viewModel.posts.collectAsState()
    var tab by remember { mutableIntStateOf(0) }
    Column {
        TabRow(selectedTabIndex = tab) {
            Tab(selected = tab == 0, onClick = { tab = 0 }, text = { Text("View Feed") })
            Tab(selected = tab == 1, onClick = { tab = 1 }, text = { Text("Post Item") })
        }
        if (tab == 0) {
            LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                if (posts.isEmpty()) item { EmptyState("No posts yet. Be the first to help!") }
                items(posts) { post -> LostFoundCard(post, onResolve = { viewModel.markResolved(post.id) }) }
            }
        } else {
            LostFoundForm(viewModel, onSubmitted = { tab = 0 })
        }
    }
}

@Composable
private fun LostFoundCard(post: LostFoundPost, onResolve: () -> Unit) {
    PremiumCard {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
            Box(
                Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                FestivalImage(resId = R.drawable.jatre_lost_item, modifier = Modifier.matchParentSize())
            }
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                StatusChip(post.category, if (post.category == "Person") Danger else TealPrimary)
                Spacer(Modifier.weight(1f))
                StatusChip(if (post.status == "active") "Active" else "Resolved", if (post.status == "active") Green else Color.Gray)
            }
            Text(post.description, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            Text("Contact: ${maskPhone(post.contact)}", color = MaterialTheme.colorScheme.onSurfaceVariant)
            if (post.status == "active") {
                OutlinedButton(onClick = onResolve, border = BorderStroke(1.dp, Green.copy(alpha = 0.5f))) {
                    Icon(Icons.Outlined.CheckCircle, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Mark as Resolved")
                }
            }
            }
        }
    }
}

@Composable
private fun LostFoundForm(viewModel: JatreViewModel, onSubmitted: () -> Unit) {
    var category by remember { mutableStateOf("Item") }
    var description by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    Column(Modifier.padding(16.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        ScreenIntro("Submit Lost & Found Post", "Share clear details so volunteers can respond quickly.", Icons.Outlined.FindInPage)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Item", "Person").forEach {
                AssistChip(onClick = { category = it }, label = { Text(if (category == it) "$it selected" else it) })
            }
        }
        OutlinedTextField(value = description, onValueChange = { description = it.take(200) }, label = { Text("Description") }, minLines = 4, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = contact, onValueChange = { contact = it.filter(Char::isDigit).take(10) }, label = { Text("Contact number") }, modifier = Modifier.fillMaxWidth())
        Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(14.dp)) {
            Text(
                "Photo upload is ready for Firebase Storage integration. This offline build keeps posts text-only.",
                modifier = Modifier.padding(12.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        error?.let { Text(it, color = Danger) }
        Button(
            onClick = {
                error = viewModel.submitLostFound(category, description, contact)
                if (error == null) onSubmitted()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = TealPrimary)
        ) {
            Text("Submit")
        }
        TextButton(onClick = { description = ""; contact = ""; error = null }) { Text("Clear") }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun MapScreen(viewModel: JatreViewModel) {
    val context = LocalContext.current
    val mapLocations by viewModel.mapLocations.collectAsState()
    val filters = listOf("All", "Food", "Toy", "Parking", "First Aid", "Gate")
    var selectedFilter by remember { mutableStateOf("All") }
    val visibleLocations = remember(selectedFilter, mapLocations) {
        if (selectedFilter == "All") {
            mapLocations
        } else {
            mapLocations.filter { it.type == selectedFilter }
        }
    }
    Column(Modifier.fillMaxSize()) {
        Box(
            Modifier.fillMaxWidth().height(260.dp),
            contentAlignment = Alignment.Center
        ) {
            val groundCenter = LatLng(15.3655, 75.1250)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(groundCenter, 16f)
            }
            GoogleMap(
                modifier = Modifier.matchParentSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(zoomControlsEnabled = false)
            ) {
                visibleLocations.forEach { loc ->
                    Marker(
                        state = MarkerState(position = LatLng(loc.latitude, loc.longitude)),
                        title = loc.name,
                        snippet = loc.type
                    )
                }
            }
            Box(Modifier.matchParentSize().background(Brush.verticalGradient(listOf(Ink.copy(alpha = 0.4f), Color.Transparent, Ink.copy(alpha = 0.3f)))))
            Text("Ground Map", modifier = Modifier.align(Alignment.TopStart).padding(18.dp),
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }
        FlowRow(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            filters.forEach { filter ->
                FilterChip(
                    selected = selectedFilter == filter,
                    onClick = { selectedFilter = filter },
                    label = { Text(filter) },
                    colors = androidx.compose.material3.FilterChipDefaults.filterChipColors(
                        selectedContainerColor = TealPrimary,
                        selectedLabelColor = Color.White,
                        containerColor = Color.White,
                        labelColor = SubText
                    ),
                    border = androidx.compose.material3.FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = selectedFilter == filter,
                        borderColor = Hairline,
                        selectedBorderColor = TealPrimary,
                        borderWidth = 1.dp,
                        selectedBorderWidth = 1.dp
                    )
                )
            }
        }
        LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            if (visibleLocations.isEmpty()) {
                item { EmptyState("No locations found for $selectedFilter.") }
            }
            items(visibleLocations) { location ->
                LocationCard(location) {
                    openDirections(context, location.latitude, location.longitude)
                }
            }
        }
    }
}

@Composable
private fun FairgroundCanvas() {
    Box(Modifier.fillMaxSize().padding(24.dp)) {
        Box(Modifier.align(Alignment.Center).fillMaxWidth(0.82f).height(42.dp).clip(RoundedCornerShape(99.dp)).background(Hairline))
        MapMarker("Food", Icons.Outlined.Restaurant, SaffronDark, Modifier.align(Alignment.TopStart).padding(top = 48.dp, start = 28.dp))
        MapMarker("Toys", Icons.Outlined.SmartToy, Color(0xFF2563EB), Modifier.align(Alignment.CenterEnd).padding(end = 16.dp))
        MapMarker("Parking", Icons.Outlined.LocalParking, Green, Modifier.align(Alignment.TopEnd).padding(top = 42.dp, end = 28.dp))
        MapMarker("First Aid", Icons.Outlined.MedicalServices, Danger, Modifier.align(Alignment.BottomStart).padding(bottom = 34.dp, start = 48.dp))
        MapMarker("Gate", Icons.Outlined.DoorSliding, TealPrimary, Modifier.align(Alignment.BottomEnd).padding(bottom = 38.dp, end = 54.dp))
    }
}

@Composable
private fun MapMarker(label: String, icon: ImageVector, color: Color, modifier: Modifier) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(22.dp))
        }
        Surface(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(99.dp), shadowElevation = 1.dp) {
            Text(label, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
private fun LocationCard(location: MapLocation, onDirections: () -> Unit) {
    PremiumCard {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(64.dp).clip(RoundedCornerShape(10.dp))) {
                FestivalImage(
                    resId = when {
                        location.type == "Food" -> R.drawable.jatre_food_detail
                        location.type == "Parking" -> R.drawable.jatre_parking_aerial
                        location.type == "Toy" -> R.drawable.jatre_toys_detail
                        location.type == "First Aid" -> R.drawable.jatre_medical_detail
                        location.type == "Gate" -> R.drawable.jatre_gate_detail
                        else -> R.drawable.jatre_stalls
                    },
                    modifier = Modifier.matchParentSize()
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(location.name, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Text(location.type, color = TealPrimary, style = MaterialTheme.typography.labelSmall)
                Text(location.description, maxLines = 2, overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onDirections) {
                Icon(Icons.Outlined.Directions, contentDescription = "Directions", modifier = Modifier.size(24.dp), tint = TealPrimary)
            }
        }
    }
}

@Composable
private fun StoriesScreen(viewModel: JatreViewModel, navController: NavController) {
    val stories by viewModel.stories.collectAsState()
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            ScreenIntro("Cultural Stories", "Legends, songs, and local memories preserved for visitors.", Icons.Outlined.AutoStories)
        }
        items(stories) { story ->
            StoryCard(story, onClick = { navController.navigate("story/${story.id}") })
        }
    }
}

@Composable
private fun StoryCard(story: CulturalStory, onClick: () -> Unit) {
    PremiumCard(onClick = onClick) {
        Column {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentAlignment = Alignment.BottomStart
            ) {
                FestivalImage(
                    resId = when(story.id) {
                        "st_001" -> R.drawable.jatre_gate_detail
                        "st_002" -> R.drawable.jatre_toys_detail
                        "st_003" -> R.drawable.jatre_food_detail
                        else -> R.drawable.jatre_cultural_story
                    },
                    modifier = Modifier.matchParentSize(),
                    scrimColors = listOf(Color.Transparent, Color(0x33000000), Color(0xDD000000))
                )
                Column(Modifier.padding(20.dp)) {
                    StatusChip("LEGEND", Color.White, onDark = true)
                    Spacer(Modifier.height(8.dp))
                    Text(story.imageLabel, color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.labelMedium)
                    Text(story.title, color = Color.White, fontWeight = FontWeight.Black,
                        style = MaterialTheme.typography.titleLarge)
                }
            }
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(story.excerpt, maxLines = 2, overflow = TextOverflow.Ellipsis,
                    color = SubText, style = MaterialTheme.typography.bodyMedium)
                Text("EXPLORE STORY", color = TealPrimary, fontWeight = FontWeight.ExtraBold, 
                    style = MaterialTheme.typography.labelLarge, letterSpacing = androidx.compose.ui.unit.TextUnit(1f, androidx.compose.ui.unit.TextUnitType.Sp))
            }
        }
    }
}

@Composable
private fun StoryDetailScreen(story: CulturalStory?, navController: NavController) {
    if (story == null) {
        MissingScreen("Story not found", navController)
        return
    }
    val context = LocalContext.current
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        HeroHeader(story.title, story.imageLabel, Icons.Outlined.AutoStories)
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(story.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                IconButton(onClick = { shareText(context, "${story.title}\n\n${story.fullText}") }) {
                    Icon(Icons.Outlined.Share, contentDescription = "Share story", modifier = Modifier.size(24.dp))
                }
            }
            Text(story.fullText, style = MaterialTheme.typography.bodyLarge)
            OutlinedButton(onClick = { navController.popBackStack() }, modifier = Modifier.fillMaxWidth()) { Text("Back to Stories") }
        }
    }
}

@Composable
private fun SafetyScreen(viewModel: JatreViewModel) {
    val context = LocalContext.current
    val parkingZones by viewModel.parkingZones.collectAsState()
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            ScreenIntro(
                "Parking & Safety", 
                "Nearest zones, emergency contacts, and guidelines.", 
                Icons.Outlined.Shield,
                imageRes = R.drawable.jatre_parking_safety
            )
        }
        items(parkingZones) { zone ->
            PremiumCard {
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.LocalParking, contentDescription = null, tint = if (zone.capacity == "Available") Green else Danger, modifier = Modifier.size(24.dp))
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(zone.name, fontWeight = FontWeight.Bold)
                        Text("${zone.distance} - ${zone.type}")
                        Text(zone.capacity, color = if (zone.capacity == "Available") Green else Danger)
                    }
                    IconButton(onClick = { openDirections(context, 15.3655, 75.1250) }) {
                        Icon(Icons.Outlined.Directions, contentDescription = "Navigate", modifier = Modifier.size(24.dp))
                    }
                }
            }
        }
        item { SectionTitle("Emergency Contacts") }
        items(viewModel.emergencyContacts) { contact ->
            PremiumCard {
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (contact.category.contains("Police") || contact.category.contains("Medical")) Danger.copy(alpha = 0.1f) else TealLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Outlined.Emergency, 
                            contentDescription = null, 
                            tint = if (contact.category.contains("Police") || contact.category.contains("Medical")) Danger else TealPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(Modifier.width(14.dp))
                    Column(Modifier.weight(1f)) {
                        Text(contact.category, color = if (contact.category.contains("Police") || contact.category.contains("Medical")) Danger else TealPrimary, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
                        Text(contact.name, color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(contact.phone, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Surface(
                        onClick = { openDialer(context, contact.phone) },
                        color = Green.copy(alpha = 0.1f),
                        shape = CircleShape
                    ) {
                        Icon(
                            Icons.Outlined.Call, 
                            contentDescription = "Call", 
                            tint = Green, 
                            modifier = Modifier.padding(10.dp).size(24.dp)
                        )
                    }
                }
            }
        }
        item { SectionTitle("Safety Guidelines") }
        items(viewModel.safetyGuidelines) { guideline ->
            Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.Top) {
                Icon(Icons.Outlined.Info, contentDescription = null, tint = SubText, modifier = Modifier.size(22.dp))
                Spacer(Modifier.width(10.dp))
                Text(guideline)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatSheet(viewModel: JatreViewModel, onDismiss: () -> Unit) {
    val messages by viewModel.chatMessages.collectAsState()
    var input by remember { mutableStateOf("") }
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(Modifier.fillMaxWidth().height(580.dp).padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = TealPrimary,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Outlined.AutoAwesome, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                }
                Spacer(Modifier.width(16.dp))
                Column {
                    Text("Jatre Concierge", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black,
                        color = Ink)
                    Text("Powered by Gemini AI • Online", color = Green, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                }
            }
            HorizontalDivider(Modifier.padding(vertical = 16.dp), color = Hairline)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(bottom = 12.dp)) {
                item { 
                    AssistChip(
                        onClick = { viewModel.sendChat("Where is the food section?") }, 
                        label = { Text("Food Guide") },
                        leadingIcon = { Icon(Icons.Outlined.Restaurant, null, Modifier.size(16.dp)) },
                        shape = RoundedCornerShape(99.dp)
                    ) 
                }
                item { 
                    AssistChip(
                        onClick = { viewModel.sendChat("Show me today's schedule") }, 
                        label = { Text("Events") },
                        leadingIcon = { Icon(Icons.Outlined.CalendarMonth, null, Modifier.size(16.dp)) },
                        shape = RoundedCornerShape(99.dp)
                    ) 
                }
                item { 
                    AssistChip(
                        onClick = { viewModel.sendChat("Where is the nearest parking?") }, 
                        label = { Text("Parking") },
                        leadingIcon = { Icon(Icons.Outlined.LocalParking, null, Modifier.size(16.dp)) },
                        shape = RoundedCornerShape(99.dp)
                    ) 
                }
            }
            LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(messages) { message ->
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = if (message.fromUser) Arrangement.End else Arrangement.Start) {
                        Surface(
                            color = if (message.fromUser) TealPrimary else MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(
                                topStart = 18.dp,
                                topEnd = 18.dp,
                                bottomStart = if (message.fromUser) 18.dp else 4.dp,
                                bottomEnd = if (message.fromUser) 4.dp else 18.dp
                            ),
                            modifier = Modifier.fillMaxWidth(0.82f)
                        ) {
                            Column(Modifier.padding(12.dp)) {
                                Text(
                                    text = message.text,
                                    color = if (message.fromUser) Color.White else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = message.timestamp,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (message.fromUser) Color.White.copy(alpha = 0.85f) else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
                item {
                    val isTyping by viewModel.isTyping.collectAsState()
                    if (isTyping) {
                        Row(Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp, color = TealPrimary)
                            Spacer(Modifier.width(8.dp))
                            Text("Gemini is thinking...", style = MaterialTheme.typography.labelSmall, color = SubText)
                        }
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(value = input, onValueChange = { input = it }, modifier = Modifier.weight(1f), placeholder = { Text("Ask about Jatre") })
                IconButton(onClick = {
                    viewModel.sendChat(input)
                    input = ""
                }) {
                    Icon(Icons.AutoMirrored.Outlined.Send, contentDescription = "Send", tint = TealPrimary, modifier = Modifier.size(24.dp))
                }
            }
        }
    }
}

@Composable
private fun CrowdMeter(level: CrowdLevel) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Crowd level", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text(level.label, color = crowdColor(level), fontWeight = FontWeight.Bold)
        }
        LinearProgressIndicator(
            progress = { level.progress },
            color = crowdColor(level),
            trackColor = Color.LightGray.copy(alpha = 0.45f),
            modifier = Modifier.fillMaxWidth().height(10.dp).clip(RoundedCornerShape(99.dp))
        )
    }
}

@Composable
private fun ExpandableSection(title: String, body: String) {
    var expanded by remember { mutableStateOf(false) }
    PremiumCard {
        Column(Modifier.padding(16.dp)) {
            Row(Modifier.clickable { expanded = !expanded }, verticalAlignment = Alignment.CenterVertically) {
                Text(title, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Text(if (expanded) "Hide" else "Show", color = Saffron)
            }
            AnimatedVisibility(expanded) {
                Text(body, modifier = Modifier.padding(top = 12.dp))
            }
        }
    }
}

@Composable
private fun HeroHeader(title: String, subtitle: String, icon: ImageVector) {
    val imageRes = when (icon) {
        Icons.Outlined.CalendarMonth -> R.drawable.jatre_procession
        Icons.Outlined.AutoStories -> R.drawable.jatre_cultural_story
        else -> R.drawable.jatre_stalls
    }
    Box(
        Modifier
            .fillMaxWidth()
            .height(280.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        FestivalImage(
            resId = imageRes,
            modifier = Modifier.matchParentSize(),
            scrimColors = listOf(Color.Transparent, Color(0x55000000), Color(0xEE000000))
        )
        Column(Modifier.padding(20.dp)) {
            Text(title, color = Color.White, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text(subtitle, color = Color.White.copy(alpha = 0.92f))
        }
    }
}

@Composable
private fun MissingScreen(message: String, navController: NavController) {
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(message, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) { Text("Go Back") }
    }
}

@Composable
private fun EmptyState(message: String) {
    Column(Modifier.fillMaxWidth().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(Icons.Outlined.Info, contentDescription = null, tint = SubText, modifier = Modifier.size(48.dp))
        Spacer(Modifier.height(8.dp))
        Text(message, color = SubText)
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(text, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
}

private fun NavController.navigateAndClear(route: String) {
    navigate(route) {
        popUpTo("splash") { inclusive = true }
        launchSingleTop = true
    }
}

private fun statusColor(status: EventStatus): Color = when (status) {
    EventStatus.Ongoing -> Saffron
    EventStatus.Upcoming -> Green
    EventStatus.Completed -> Color.Gray
}

private fun crowdColor(level: CrowdLevel): Color = when (level) {
    CrowdLevel.Low -> Green
    CrowdLevel.Medium -> Color(0xFFFFB300)
    CrowdLevel.High -> Danger
}

private fun markerColor(type: String): Color = when (type) {
    "Food" -> Danger
    "Toy" -> Color(0xFF1565C0)
    "Parking" -> Green
    "First Aid" -> Saffron
    else -> Color(0xFF6A1B9A)
}

private fun featureColor(route: String): Color = when (route) {
    "schedule" -> SaffronDark
    "lostfound" -> Danger
    "map" -> TealPrimary
    "stories" -> Color(0xFF7C3AED)
    "safety" -> Green
    else -> TealPrimary
}

private fun maskPhone(phone: String): String = if (phone.length >= 5) "${phone.take(5)}XXXXX" else phone

private fun shareText(context: android.content.Context, text: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    context.startActivity(Intent.createChooser(intent, "Share with"))
}

private fun openDialer(context: android.content.Context, phone: String) {
    context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone")))
}

private fun openDirections(context: android.content.Context, latitude: Double, longitude: Double) {
    val uri = Uri.parse("google.navigation:q=$latitude,$longitude")
    val intent = Intent(Intent.ACTION_VIEW, uri).apply { setPackage("com.google.android.apps.maps") }
    runCatching { context.startActivity(intent) }.onFailure {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude")))
    }
}
