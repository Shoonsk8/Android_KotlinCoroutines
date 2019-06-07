package com.shoon.android_kotlincoroutines

import android.graphics.Bitmap
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class VideoListList(
    val error: String?,
    val limit: Int?,
    val offset: Int?,
    val number_of_page_results: Int?,
    val number_of_total_results: Int?,
    val status_code: Int?,
    val results: List<VideosList>?,
    val version: String?
)

@Serializable
data class VideosList(
    val id: Number?,
    val name: String?,
    val news_name: String?,
    val image: String?,
    val collection: String?,
    val mission: String?,
    val imageBitmap:Bitmap
)

