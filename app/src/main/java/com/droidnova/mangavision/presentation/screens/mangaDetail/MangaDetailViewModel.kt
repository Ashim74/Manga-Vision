package com.droidnova.mangavision.presentation.screens.mangaDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.droidnova.mangavision.domain.data.Manga
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MangaDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val manga: Manga? = savedStateHandle.get<Manga>("manga")
}
