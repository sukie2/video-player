package com.silverorange.videoplayer.data

import com.silverorange.videoplayer.domain.data.VideoData
import retrofit2.Response
import retrofit2.http.GET

interface VideoApi {
    @GET("videos")
    suspend fun getVideoData(): List<VideoData>
}