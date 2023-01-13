package com.silverorange.videoplayer.domain

import com.silverorange.videoplayer.domain.data.VideoData
import com.silverorange.videoplayer.domain.util.ApiResult
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getVideoData(): Flow<ApiResult<List<VideoData>>>
}