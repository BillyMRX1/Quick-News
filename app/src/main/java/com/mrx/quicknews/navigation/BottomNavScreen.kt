package com.mrx.quicknews.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavScreen(
    val title: String,
    val activeIcon: ImageVector,
    val inactiveIcon: ImageVector
) {
    data object Local : BottomNavScreen("Local", Icons.Filled.Place, Icons.Outlined.Place)
    data object Search : BottomNavScreen("Search", Icons.Filled.Search, Icons.Outlined.Search)
    data object Source : BottomNavScreen("Source", Icons.Filled.Check, Icons.Outlined.Check)
    data object Favorite : BottomNavScreen("Favorite", Icons.Filled.Favorite, Icons.Outlined.Favorite)
    data object Settings : BottomNavScreen("Settings", Icons.Filled.Settings, Icons.Outlined.Settings)
}