package net.azurewebsites.soundsafeguard.model

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "sound_preferences")

class SoundRepository(private val context: Context) {
    private companion object PreferencesKeys {
        val SOUND_LIST_KEY = stringPreferencesKey("sound_list_key")
    }

    val sounds = context.dataStore.data.map { preferences ->
        val jsonString = preferences[SOUND_LIST_KEY] ?: "[]"
        Gson().fromJson(jsonString, Array<String>::class.java).toList()
    }

    suspend fun initializeSound() {
        val currentSound = context.dataStore.data.map { preferences ->
            preferences[SOUND_LIST_KEY]
        }.first()

        if (currentSound != null) return

        context.dataStore.edit { preferences ->
            val jsonString = Gson().toJson(List(10) { "Siren$it" })
            preferences[SOUND_LIST_KEY] = jsonString
        }
    }

    suspend fun saveSound(sound: String) {
//        context.dataStore.edit { preferences ->
//            preferences[SOUND_LIST_KEY] = sound
//        }
    }
}