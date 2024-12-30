package net.azurewebsites.soundsafeguard.model

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "sound_preferences")

class SoundPreferencesRepository(private val context: Context) {
    private companion object PreferencesKeys {
        val SOUND = stringPreferencesKey("sound")
    }

    val sound = context.dataStore.data.map { preferences ->
        preferences[SOUND]
    }

    suspend fun setSound(sound: String) {
        context.dataStore.edit { preferences ->
            preferences[SOUND] = sound
        }
    }
}