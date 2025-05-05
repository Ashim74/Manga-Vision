package com.droidnova.mangavision.presentation.screens.manga

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.droidnova.mangavision.domain.data.NetworkStatus
import com.droidnova.mangavision.domain.usecase.MangaUseCases
import com.droidnova.mangavision.utils.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaViewModel @Inject constructor(
    private val useCases: MangaUseCases,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    var mangaUiState by mutableStateOf(MangaUiState())

    var mangaPagingFlow = useCases.getPagedManga().cachedIn(viewModelScope)

    init {
        observeNetwork()
    }

    private fun observeNetwork() {
        viewModelScope.launch {
            networkMonitor.isConnected.collect { connected ->
                if (connected){
                    if (mangaUiState.networkStatus == NetworkStatus.Disconnected){
                        mangaPagingFlow = useCases.getPagedManga().cachedIn(viewModelScope)
                        mangaUiState = mangaUiState.copy(networkStatus = NetworkStatus.Reconnected)
                        delay(4000)
                        mangaUiState = mangaUiState.copy(networkStatus = NetworkStatus.Connected)
                    }else{
                        mangaUiState = mangaUiState.copy(networkStatus = NetworkStatus.Connected)
                    }
                }else{
                    mangaUiState = mangaUiState.copy(networkStatus = NetworkStatus.Disconnected)
                }
            }
        }
    }
}
