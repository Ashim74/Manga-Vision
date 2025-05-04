package com.droidnova.mangavision.presentation.screens.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.droidnova.mangavision.domain.usecase.MangaUseCases
import com.droidnova.mangavision.utils.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaViewModel @Inject constructor(
    private val useCases: MangaUseCases,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    val mangaPagingFlow = useCases.getPagedManga().cachedIn(viewModelScope)

    init {
        observeNetwork()
    }

    private fun observeNetwork() {
        viewModelScope.launch {
            networkMonitor.isConnected.collect { connected ->
                if (connected) {
                    // Paging will auto-refresh when data source emits
                }
            }
        }
    }
}
