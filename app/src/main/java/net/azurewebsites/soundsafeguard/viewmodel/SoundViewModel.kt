package net.azurewebsites.soundsafeguard.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import net.azurewebsites.soundsafeguard.model.Sound
import net.azurewebsites.soundsafeguard.model.SoundRepository

class SoundViewModel(private val soundRepository: SoundRepository): ViewModel() {
    private val _sounds = MutableStateFlow<List<Sound>>(emptyList())
    val sounds: StateFlow<List<Sound>> get() = _sounds

    init {
        loadSounds()
    }

    private fun loadSounds() {
//        _sounds.value = soundRepository.getAllSounds()

    }
}