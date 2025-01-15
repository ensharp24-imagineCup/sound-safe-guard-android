package net.azurewebsites.soundsafeguard.presentation.utils

import android.media.AudioFormat
import android.media.AudioRecord
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class AudioConfig(
    val sampleRate: Int = 16000,
    val channelConfig: Int = AudioFormat.CHANNEL_IN_MONO,
    val audioFormat: Int = AudioFormat.ENCODING_PCM_16BIT,
    val bufferSize: Int = AudioRecord.getMinBufferSize(16000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT),
    val isRecording: MutableState<Boolean> = mutableStateOf(false)
)
