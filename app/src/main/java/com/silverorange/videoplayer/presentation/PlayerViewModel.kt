package com.silverorange.videoplayer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silverorange.videoplayer.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(val repository: Repository): ViewModel() {
    fun fetchVideoData() {
        viewModelScope.launch {
            repository.getVideoData()
                .flowOn(Dispatchers.IO)
                .collect{
                    val x = it
                }
        }
    }
}