package com.droidnova.mangavision.presentation.screens.face

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.droidnova.mangavision.presentation.components.CameraPreview
import com.droidnova.mangavision.utils.DetectionBoxConfig
import com.droidnova.mangavision.utils.defaultDetectionBoxConfig

@Composable
fun FaceScreen() {
    val viewModel: FaceViewModel = hiltViewModel()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val state = viewModel.faceUiState

    var permissionGranted by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        permissionGranted = granted
    }

    LaunchedEffect(Unit) {
        val granted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (granted) {
            permissionGranted = true
        } else {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    if (permissionGranted) {
        LaunchedEffect(Unit) {
            viewModel.startCamera(context, lifecycleOwner)
        }
        if (state.isCameraInitialized) {
            FaceScreenContent(viewModel, state)
        }
    } else {
        PermissionDeniedContent()
    }
}

@Composable
fun FaceScreenContent(viewModel: FaceViewModel, state: FaceUiState) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val previewWidth = screenWidth / 1.5f
    val previewHeight = previewWidth

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {

        CameraPreview(
            modifier = Modifier
                .size(width = previewWidth, height = previewHeight)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
            cameraProvider = viewModel.cameraProvider,
            preview = viewModel.preview
        )
        FaceOverlay(faceInRect = state.isFaceInRectState,previewWidth = previewWidth, previewHeight = previewHeight)

    }
}


@Composable
fun PermissionDeniedContent() {
    Text("Camera permission is required to use this feature.")
}

@Composable
fun FaceOverlay(
    modifier: Modifier = Modifier,
    faceInRect: Boolean,
    previewWidth: Dp,
    previewHeight: Dp,
    config: DetectionBoxConfig = defaultDetectionBoxConfig
) {
    Canvas(modifier = modifier.size(height = previewHeight, width = previewWidth)) {
        val hPadPx = size.width * config.horizontalPaddingRatio
        val vPadPx = size.height * config.verticalPaddingRatio

        val rectSize = Size(size.width - 2 * hPadPx, size.height - 2 * vPadPx)
        val topLeft = Offset(hPadPx, vPadPx)

        drawRect(
            color = if (faceInRect) Color.Green else Color.Red,
            topLeft = topLeft,
            size = rectSize,
            style = Stroke(width = 2.dp.toPx())
        )
    }
}
