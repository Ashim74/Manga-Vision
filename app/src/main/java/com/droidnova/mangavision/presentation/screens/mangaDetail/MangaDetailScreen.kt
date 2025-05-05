package com.droidnova.mangavision.presentation.screens.mangaDetail

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.droidnova.mangavision.R
import com.droidnova.mangavision.domain.data.Manga

@Composable
fun MangaDetailScreen(manga: Manga?) {
    val viewModel: MangaDetailViewModel = hiltViewModel()
    Log.e("myTag", "Manga: $manga")
    manga?.let { it ->
        MangaDetailScreenContent(it)
    }
}

@Composable
fun MangaDetailScreenContent(manga: Manga) {
    var failedToLoadImage by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .scrollable(rememberScrollState(), orientation = Orientation.Vertical)) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            if (failedToLoadImage){
                Icon(
                    imageVector = Icons.Default.ImageNotSupported,
                    contentDescription = "Image failed to load",
                    modifier = Modifier.fillMaxWidth(.5f).aspectRatio(1f),
                    tint = MaterialTheme.colorScheme.error
                )
            }else{
                AsyncImage(
                    model = manga.thumb,
                    contentDescription = manga.title,
                    modifier = Modifier.weight(1f).aspectRatio(.5f),
                    contentScale = ContentScale.Crop,
                    onError = {
                        failedToLoadImage = true
                    }
                )
            }


            Spacer(modifier = Modifier.width(8.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = manga.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Status: ${manga.status}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Chapters: ${manga.totalChapter}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Summary",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = manga.summary,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}
