package net.azurewebsites.soundsafeguard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.azurewebsites.soundsafeguard.model.SoundPreferencesRepository

class SoundViewModel(private val repository: SoundPreferencesRepository): ViewModel() {
}

class SoundViewModelFactory(private val repository: SoundPreferencesRepository): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SoundViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SoundViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}