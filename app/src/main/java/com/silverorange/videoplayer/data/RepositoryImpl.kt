package com.silverorange.videoplayer.data

import com.silverorange.videoplayer.domain.Repository
import com.silverorange.videoplayer.domain.data.VideoData
import com.silverorange.videoplayer.domain.util.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepositoryImpl(private val api: VideoApi) : Repository {
    override suspend fun getVideoData(): Flow<ApiResult<List<VideoData>>> {
        return try {
            flow {
                emit(
                    ApiResult.Success(api.getVideoData())
                )
            }
        } catch (e: Exception) {
            flow {
                emit(ApiResult.Error(errorMessage = e.localizedMessage ?: ""))
            }
        }
    }
}