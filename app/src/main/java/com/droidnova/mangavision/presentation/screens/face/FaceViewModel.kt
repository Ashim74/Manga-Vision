package com.droidnova.mangavision.presentation.screens.face

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import com.droidnova.mangavision.utils.defaultDetectionBoxConfig
import com.droidnova.mangavision.utils.isFaceFullyInBox
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.Executors
import javax.inject.Inject
import kotlin.math.log


@HiltViewModel
class FaceViewModel @Inject constructor(
    private val app: Application
) : AndroidViewModel(app) {

    var faceUiState by mutableStateOf(FaceUiState())

    lateinit var cameraProvider: ProcessCameraProvider
    lateinit var preview: Preview
        private set

    private var imageAnalyzer: ImageAnalysis? = null
    private lateinit var faceDetectorHelper: FaceDetectorHelper

    fun startCamera(context: Context, lifecycleOwner: LifecycleOwner) {
        val providerFuture = ProcessCameraProvider.getInstance(context)
        providerFuture.addListener({
            runCatching {
                cameraProvider = providerFuture.get()
                preview = Preview.Builder().build()

                val boxConfig = defaultDetectionBoxConfig
                faceDetectorHelper = FaceDetectorHelper(context) { detections, width, height ->
                    val isInside = detections.isNotEmpty() && detections.all {
                        isFaceFullyInBox(it, width, height, boxConfig)
                    }

                    faceUiState = faceUiState.copy(isFaceInRectState = isInside)
                }


                imageAnalyzer = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build().apply {
                        setAnalyzer(Executors.newSingleThreadExecutor()) { imageProxy ->
                            val bitmap = imageProxy.toBitmap()
                            faceDetectorHelper.detect(bitmap)
                            imageProxy.close()
                        }
                    }

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_FRONT_CAMERA,
                    preview,
                    imageAnalyzer
                )
                faceUiState = faceUiState.copy(isCameraInitialized = true)
            }.onFailure {
                Log.e("FaceViewModel", "Camera initialization failed", it)
            }
        }, ContextCompat.getMainExecutor(context))
    }
}