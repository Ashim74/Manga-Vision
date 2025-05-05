package com.droidnova.mangavision.presentation.screens.manga

import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.droidnova.mangavision.domain.data.Manga
import com.droidnova.mangavision.domain.data.NetworkStatus
@Composable
fun MangaScreen(
    viewModel: MangaViewModel = hiltViewModel(),
    onItemClick: (Manga) -> Unit,
    onSignOut: () -> Unit
) {
    val uiState = viewModel.mangaUiState
    val mangaItems = viewModel.mangaPagingFlow.collectAsLazyPagingItems()

    Column(modifier = Modifier.fillMaxWidth()) {
        SignOutButton(onSignOut)
        NetworkStatusBanner(uiState.networkStatus)
        Spacer(modifier = Modifier.height(8.dp))
        MangaGrid(mangaItems = mangaItems, onItemClick = onItemClick)
    }
}

@Composable
private fun ColumnScope.SignOutButton(onSignOut: () -> Unit) {
    TextButton(
        onClick = onSignOut,
        modifier = Modifier
            .align(Alignment.End)
            .padding(8.dp)
    ) {
        Text("Sign Out")
    }
}

@Composable
private fun NetworkStatusBanner(status: NetworkStatus) {
    AnimatedVisibility(
        visible = status != NetworkStatus.Connected,
        enter = fadeIn(animationSpec = tween(1000)),
        exit = fadeOut(animationSpec = tween(1000))
    ) {
        val (message, containerColor, textColor) = when (status) {
            NetworkStatus.Disconnected -> Triple(
                "You're offline. Please check your connection.",
                Color(0xFFFFE0E0),
                Color.Red
            )
            NetworkStatus.Reconnected -> Triple(
                "Back online",
                Color(0xFFE0FFE0),
                Color(0xFF2E7D32)
            )
            else -> return@AnimatedVisibility
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(containerColor)
                .padding(12.dp)
        ) {
            Text(
                text = message,
                color = textColor,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MangaGrid(
    mangaItems: LazyPagingItems<Manga>,
    onItemClick: (Manga) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(mangaItems.itemCount) { index ->
            mangaItems[index]?.let { manga ->
                MangaGridItem(manga = manga, onClick = { onItemClick(manga) })
            }
        }

        mangaItems.apply {
            when {
                loadState.refresh is LoadState.Loading ||
                        loadState.append is LoadState.Loading -> {
                    item(span = { GridItemSpan(3) }) {
                        LoadingIndicator()
                    }
                }

                loadState.append is LoadState.Error -> {
                    item(span = { GridItemSpan(3) }) {
                        ErrorIndicator()
                    }
                }
            }
        }
    }
}

@Composable
private fun MangaGridItem(manga: Manga, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.7f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        AsyncImage(
            model = manga.thumb,
            contentDescription = manga.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorIndicator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("Error loading more", color = Color.Red)
    }
}
