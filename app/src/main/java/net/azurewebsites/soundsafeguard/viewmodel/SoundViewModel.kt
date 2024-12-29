package net.azurewebsites.soundsafeguard.viewmodel

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import net.azurewebsites.soundsafeguard.dataStore
import net.azurewebsites.soundsafeguard.model.PreferencesKeys

class SoundViewModel(context: Context): ViewModel() {
    private val dataStore = context.dataStore

    val sound: Flow<String?> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.SOUND]
    }

    fun saveSound(s: String) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.SOUND] = s
            }
        }
    }
}