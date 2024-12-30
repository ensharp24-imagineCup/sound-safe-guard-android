package net.azurewebsites.soundsafeguard.model

import androidx.datastore.preferences.core.stringPreferencesKey

class SoundPreferencesRepository {
    private object PreferencesKeys {
        val SOUND = stringPreferencesKey("sound")
    }
}