package com.droidnova.mangavision.presentation.navigation

sealed class Screen(val route: String) {
    data object Auth: Screen("auth")
    data object Manga: Screen("manga")
    data object MangaDetail: Screen("mangaDetail")
    data object Face: Screen("face")
}