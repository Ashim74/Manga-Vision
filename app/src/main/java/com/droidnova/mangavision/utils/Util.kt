package com.droidnova.mangavision.utils

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.graphics.RectF
import android.graphics.YuvImage
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.camera.core.ImageProxy
import androidx.datastore.preferences.preferencesDataStore
import coil.Coil
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.google.mediapipe.tasks.components.containers.Detection
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

val Context.dataStore by preferencesDataStore(name = "user_prefs")

fun isConnected(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

// utils/FaceDetectionUtils.kt
fun isFaceFullyInBox(
    detection: Detection,
    imageWidth: Int,
    imageHeight: Int,
    config: DetectionBoxConfig = defaultDetectionBoxConfig
): Boolean {
    val box = detection.boundingBox()

    val left = box.left / imageWidth.toFloat()
    val top = box.top / imageHeight.toFloat()
    val right = box.right / imageWidth.toFloat()
    val bottom = box.bottom / imageHeight.toFloat()

    val hPad = config.horizontalPaddingRatio +.15f
    val vPad = config.verticalPaddingRatio + 0.05f

    return left >= hPad &&
            right <= 1 - hPad &&
            top >= vPad &&
            bottom <= 1 - vPad
}

suspend fun preloadImageToCache(url: String,appContext: Context) {
    val request = ImageRequest.Builder(appContext) // Use ApplicationContext here
        .data(url)
        .diskCachePolicy(CachePolicy.ENABLED) // Ensures caching
        .memoryCachePolicy(CachePolicy.DISABLED) // Optional: save memory
        .allowHardware(false)
        .build()

    // Enqueue request and wait for result to ensure disk cache is written
    Coil.imageLoader(appContext).execute(request)
}


