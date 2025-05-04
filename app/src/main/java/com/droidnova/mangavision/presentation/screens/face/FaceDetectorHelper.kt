package com.droidnova.mangavision.presentation.screens.face

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.components.containers.Detection
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.core.Delegate
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.facedetector.FaceDetector

class FaceDetectorHelper(
    val context: Context,
    private val onFaceResult: (List<Detection>, Int, Int) -> Unit
) {
    private lateinit var faceDetector: FaceDetector

    init {
        setupFaceDetector()
    }

    private fun setupFaceDetector() {
        // Set general detection options, including number of used threads
        val baseOptionsBuilder = BaseOptions.builder()

        // Use the specified hardware for running the model. Default to CPU
        baseOptionsBuilder.setDelegate(Delegate.CPU)

        val modelName = "face_detection_short_range.tflite"

        baseOptionsBuilder.setModelAssetPath(modelName)

        try {
            val optionsBuilder =
                FaceDetector.FaceDetectorOptions.builder()
                    .setBaseOptions(baseOptionsBuilder.build())
                    .setMinDetectionConfidence(0.5f)
                    .setRunningMode(RunningMode.IMAGE)

            val options = optionsBuilder.build()
            faceDetector = FaceDetector.createFromOptions(context, options)
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    fun detect(image: Bitmap) {
        val mpImage = BitmapImageBuilder(image).build()
        faceDetector.detect(mpImage).also { detectionResult ->
            onFaceResult(detectionResult.detections(), image.width, image.height)
        }

    }
}
