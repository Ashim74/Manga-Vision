package com.droidnova.mangavision.presentation.screens.manga

import com.droidnova.mangavision.domain.data.NetworkStatus

data class MangaUiState(
    val networkStatus: NetworkStatus = NetworkStatus.Connected
)