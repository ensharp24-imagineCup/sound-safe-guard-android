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
        val SELECTED_SOUND_LIST_KEY = stringPreferencesKey("selected_sound_list_key")
    }

    val sounds = context.dataStore.data.map { preferences ->
        val jsonString = preferences[SOUND_LIST_KEY] ?: "[]"
        Gson().fromJson(jsonString, Array<String>::class.java).toList()
    }

    val selectedSounds = context.dataStore.data.map { preferences ->
        val jsonString = preferences[SELECTED_SOUND_LIST_KEY] ?: "[]"
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

    /**
     * @param sound 선택한 사운드
     *
     * @param sound 를 SELECTED_SOUND_LIST_KEY에 저장하고, SOUND_LIST_KEY에서 제거한다.
     */
    suspend fun selectSound(sound: String) {
        context.dataStore.edit { preferences ->
            val currentSoundsJson = preferences[SOUND_LIST_KEY] ?: "[]"
            val selectedSoundsJson = preferences[SELECTED_SOUND_LIST_KEY] ?: "[]"
            val currentSounds =
                Gson().fromJson(currentSoundsJson, Array<String>::class.java).toMutableList()
            val selectedSounds =
                Gson().fromJson(selectedSoundsJson, Array<String>::class.java).toMutableList()

            if (currentSounds.contains(sound)) {
                currentSounds.remove(sound)
                preferences[SOUND_LIST_KEY] = Gson().toJson(currentSounds)
            }

            if (!selectedSounds.contains(sound)) {
                selectedSounds.add(sound)
                selectedSounds.sort()
                preferences[SELECTED_SOUND_LIST_KEY] = Gson().toJson(selectedSounds)
            }
        }
    }

    /**
     * @param sound 선택한 사운드
     *
     * @param sound 를 SOUND_LIST_KEYY에 저장하고, SELECTED_SOUND_LIST_KE에서 제거한다.
     */
    suspend fun unselectSound(sound: String) {
        context.dataStore.edit { preferences ->
            val currentSoundsJson = preferences[SOUND_LIST_KEY] ?: "[]"
            val selectedSoundsJson = preferences[SELECTED_SOUND_LIST_KEY] ?: "[]"
            val currentSounds =
                Gson().fromJson(currentSoundsJson, Array<String>::class.java).toMutableList()
            val selectedSounds =
                Gson().fromJson(selectedSoundsJson, Array<String>::class.java).toMutableList()

            if (selectedSounds.contains(sound)) {
                selectedSounds.remove(sound)
                preferences[SELECTED_SOUND_LIST_KEY] = Gson().toJson(selectedSounds)
            }

            if (!currentSounds.contains(sound)) {
                currentSounds.add(sound)
                currentSounds.sort()
                preferences[SOUND_LIST_KEY] = Gson().toJson(currentSounds)
            }
        }
    }
}