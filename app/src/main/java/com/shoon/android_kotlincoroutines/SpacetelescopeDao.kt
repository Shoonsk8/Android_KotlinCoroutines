package com.shoon.android_kotlincoroutines


import android.graphics.Bitmap
import androidx.annotation.WorkerThread

import kotlinx.serialization.json.Json

object SpacetelescopeDao {
    const val SPACETELESCOPE_URL = "http://hubblesite.org/api/v3/"
    const val VIDEO_URL = "videos/all"

    interface VideosCallback{
        fun callback(list: List<VideosList>)
    }

  /*  fun getVideos(callback: String?, offset: Int = 0, limit: Int = 10){
        NetworkAdapter.httpGetRequest(
            "$SPACETELESCOPE_URL$VIDEO_URL",
            object: NetworkAdapter.NetworkCallback{
                override fun returnResult(success: Boolean?, result: String) {
                    val videos = Json.parse(VideosList.serializer(), result)
                    callback.callback(videos ?: listOf())
                }
            })
    }*/

    @WorkerThread
    suspend fun getVideoSuspend(offset: Int = 0, limit: Int = 10): List<VideosList> {
        val result = NetworkAdapter.httpGetRequestCoroutine("${SPACETELESCOPE_URL}${VIDEO_URL}")
        val videosList = Json.parse(VideoListList.serializer(), result)
        return videosList.results ?: listOf()
    }

    @WorkerThread
    suspend fun getVideoImage(videoList: VideosList?): Bitmap? {
        val result = NetworkAdapter.getBitmapFromURL(videoList?.image ?: "")

        return if (result.first && result.second != null){
            result.second
        } else {
            null
        }
    }
}