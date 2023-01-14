package com.silverorange.videoplayer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.exoplayer2.Player
import com.silverorange.videoplayer.domain.Repository
import com.silverorange.videoplayer.domain.data.VideoData
import com.silverorange.videoplayer.domain.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(val repository: Repository): ViewModel() {

    private val _videoList = MutableStateFlow<List<VideoData>>(listOf())
    val videoList: StateFlow<List<VideoData>> =  _videoList

    fun fetchVideoData() {
        viewModelScope.launch {
            repository.getVideoData()
                .flowOn(Dispatchers.IO)
                .collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Success -> {
                            _videoList.value = apiResult.data ?: listOf()
                        }
                        is ApiResult.Error -> {
                            _videoList.value = listOf()
                        }
                    }
                }
        }
    }
}