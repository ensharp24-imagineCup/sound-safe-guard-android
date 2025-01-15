package net.azurewebsites.soundsafeguard.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.azurewebsites.soundsafeguard.model.SoundRepository

class SoundViewModel(private val repository: SoundRepository) : ViewModel() {
    val sounds: Flow<List<String>> = repository.sounds
    val selectedSound: Flow<List<String>> = repository.selectedSounds

    init {
        runBlocking {
            repository.initializeSound()
        }
    }

    fun selectSound(sound: String) {
        viewModelScope.launch {
            repository.selectSound(sound)
        }
    }

    fun unselectSound(sound: String) {
        viewModelScope.launch {
            repository.unselectSound(sound)
        }
    }
}

class SoundViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SoundViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SoundViewModel(SoundRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}