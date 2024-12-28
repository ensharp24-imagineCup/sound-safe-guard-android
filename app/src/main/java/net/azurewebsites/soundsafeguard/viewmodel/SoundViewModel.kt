package net.azurewebsites.soundsafeguard.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SoundViewModel: ViewModel() {
    private val _sounds = MutableStateFlow<List<String>>(emptyList())
    val sounds: StateFlow<List<String>> get() = _sounds
}