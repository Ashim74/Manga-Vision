package com.droidnova.mangavision.presentation.navigation

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.droidnova.mangavision.domain.data.Manga
import com.droidnova.mangavision.presentation.screens.auth.AuthScreen
import com.droidnova.mangavision.presentation.screens.auth.AuthViewModel
import com.droidnova.mangavision.presentation.screens.face.FaceScreen
import com.droidnova.mangavision.presentation.screens.manga.MangaScreen
import com.droidnova.mangavision.presentation.screens.mangaDetail.MangaDetailScreen

@Composable
fun AppNavGraph(authViewModel: AuthViewModel, startingDestination: String) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNav(navController)
        }
    ) { innerPadding ->

        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = startingDestination,
            enterTransition = {
                scaleIn(
                    animationSpec = tween(300),
                    initialScale = 0.8f
                ) + fadeIn(tween(300))
            },
            exitTransition = {
                scaleOut(
                    animationSpec = tween(300),
                    targetScale = 0.8f
                ) + fadeOut(tween(300))
            },
            popEnterTransition = {
                scaleIn(
                    animationSpec = tween(300),
                    initialScale = 0.8f
                ) + fadeIn(tween(300))
            },
            popExitTransition = {
                scaleOut(
                    animationSpec = tween(300),
                    targetScale = 0.8f
                ) + fadeOut(tween(300))
            }
        ) {
            composable(
                route = Screen.Auth.route
            ) {
                AuthScreen(authViewModel = authViewModel,
                    onLoginSuccess = {
                        navController.navigate(Screen.Manga.route) {
                            popUpTo(Screen.Auth.route) {
                                inclusive = true
                            }
                        }
                    })
            }

            composable(
                route = Screen.Manga.route
            ) {
                MangaScreen(
                    onItemClick = { selectedManga ->
                        Log.e("myTag", "Manga at manga screen: $selectedManga")
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "manga",
                            selectedManga
                        )
                        navController.navigate(Screen.MangaDetail.route)
                    },
                    onSignOut = {
                        navController.navigate(Screen.Auth.route) {
                            popUpTo(Screen.Manga.route) {
                                inclusive = true
                            }
                            authViewModel.logout()
                        }
                    }
                )
            }
            composable(
                route = Screen.MangaDetail.route
            ) {
                val manga =
                    navController.previousBackStackEntry?.savedStateHandle?.get<Manga>("manga")
                MangaDetailScreen(manga)
            }
            composable(
                route = Screen.Face.route
            ) {
                FaceScreen()
            }
        }
    }

}






