package com.droidnova.mangavision

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.droidnova.mangavision.domain.data.AuthStatus
import com.droidnova.mangavision.presentation.navigation.AppNavGraph
import com.droidnova.mangavision.presentation.navigation.Screen
import com.droidnova.mangavision.presentation.screens.auth.AuthViewModel
import com.droidnova.mangavision.presentation.theme.MangaVisionTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MangaVisionTheme {
                val authStatus = authViewModel.state.status
                if (authStatus != AuthStatus.Idle){
                    val startingDestination = when (authStatus){
                        AuthStatus.Authenticated -> Screen.Manga.route
                        else -> Screen.Auth.route
                    }
                    AppNavGraph(authViewModel,startingDestination)
                }
            }
        }
    }
}
