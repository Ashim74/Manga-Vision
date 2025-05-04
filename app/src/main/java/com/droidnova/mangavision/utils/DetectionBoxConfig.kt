// utils/DetectionBoxConfig.kt
package com.droidnova.mangavision.utils

data class DetectionBoxConfig(
    val horizontalPaddingRatio: Float = 0.2f, // 0.2 => 20% left and right
    val verticalPaddingRatio: Float = 0.2f    // 0.2 => 20% top and bottom
)

val defaultDetectionBoxConfig = DetectionBoxConfig()
