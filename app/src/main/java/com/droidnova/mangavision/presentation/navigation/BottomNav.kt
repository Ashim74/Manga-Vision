package com.droidnova.mangavision.presentation.navigation

import android.util.Log
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Face
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BottomNav(navController: NavController) {
    val items = listOf(
        BottomNavItem(Screen.Manga.route, "Manga", Icons.Default.Book),
        BottomNavItem(Screen.Face.route, "Face", Icons.Default.Face)
    )

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    if (currentRoute == Screen.Auth.route || currentRoute == null) {
        return
    }

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(Screen.Manga.route)
                        }
                    }
                }
            )
        }
    }
}

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)
