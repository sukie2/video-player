package com.silverorange.videoplayer.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.silverorange.videoplayer.R
import com.silverorange.videoplayer.databinding.FragmentPlayerBinding


class PlayerFragment : Fragment(R.layout.fragment_player) {
    private lateinit var binding: FragmentPlayerBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlayerBinding.bind(view)
    }
}
