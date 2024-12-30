package net.azurewebsites.soundsafeguard

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "sound_preferences")
