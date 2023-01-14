package com.silverorange.videoplayer.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.exoplayer2.*
import com.silverorange.videoplayer.R
import com.silverorange.videoplayer.databinding.FragmentPlayerBinding
import dagger.hilt.android.AndroidEntryPoint
import io.noties.markwon.Markwon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PlayerFragment : Fragment(R.layout.fragment_player), Player.Listener {

    private lateinit var binding: FragmentPlayerBinding
    private val viewModel by viewModels<PlayerViewModel>()
    private val showError = MutableStateFlow(false)
    private val currentMediaIndex = MutableStateFlow(0)
    private lateinit var markwon: Markwon
    private lateinit var player: Player

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlayerBinding.bind(view)
        markwon = Markwon.create(view.context)

        viewModel.fetchVideoData()

        player = ExoPlayer.Builder(view.context).build()
        player.addListener(this)
        binding.playerView.player = player

        setObservables()
    }

    override fun onEvents(player: Player, events: Player.Events) {
        super.onEvents(player, events)
        if (events.contains(Player.EVENT_PLAYBACK_STATE_CHANGED)
            || events.contains(Player.EVENT_PLAY_WHEN_READY_CHANGED)
        ) {
            currentMediaIndex.value = player.currentMediaItemIndex
        }
    }

    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)
        showError.value = true
    }

    private fun setObservables() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.videoList.collect { videoList ->
                    var shouldPrepare = false
                    videoList.forEach {
                        val mediaItem = MediaItem.fromUri(it.fullURL ?: "")
                        player.addMediaItem(mediaItem)
                        shouldPrepare = true
                    }
                    if (shouldPrepare) {
                        player.prepare()
                        player.play()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                showError.collect {
                    if (it) {
                        Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                currentMediaIndex.collect { index ->
                    val list = viewModel.videoList.value
                    if (list.isNotEmpty()) {
                        val item = viewModel.videoList.value[index]
                        val author = "${item.author?.name}"
                        val title = "${item.title} by "
                        val description = "$title  $author \n ${item.description}"
                        markwon.setMarkdown(
                            binding.textVideDescription,
                            description
                        )
                    }
                }
            }
        }
    }
}
