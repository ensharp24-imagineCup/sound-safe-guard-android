package net.azurewebsites.soundsafeguard.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import net.azurewebsites.soundsafeguard.model.SoundRepository

class SoundViewModel(private val repository: SoundRepository): ViewModel() {
    val sound: Flow<String?> = repository.sound

    init {
        viewModelScope.launch {
            repository.initializeSound()
        }
    }

    fun setSound(sound: String) {
        viewModelScope.launch {
            repository.setSound(sound)
        }
    }
}

class SoundViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SoundViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SoundViewModel(SoundRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}