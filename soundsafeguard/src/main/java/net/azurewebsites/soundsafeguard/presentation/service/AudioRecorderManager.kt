package net.azurewebsites.soundsafeguard.presentation.service

import android.app.Activity
import android.content.Context
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.azurewebsites.soundsafeguard.presentation.utils.AudioConfig

class AudioRecorderManager(
    private val context: Context,
    private val dataSender: DataSender
) {
    private val permissionHandler = PermissionHandler(context as ComponentActivity)
    private val audioConfig = AudioConfig()

    fun startRecording() {
        if (!permissionHandler.isAudioPermissionGranted()) {
            Toast.makeText(
                context,
                "RECORD_AUDIO permission is required to record audio",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val completeAudioData = mutableListOf<Byte>()
        audioConfig.isRecording.value = true

        CoroutineScope(Dispatchers.IO).launch {
            val audioRecord = setupAudioRecord()
            if (audioRecord == null) {
                audioConfig.isRecording.value = false
                return@launch
            }

            audioRecord.startRecording()
            val buffer = ByteArray(audioConfig.bufferSize)


            while (audioConfig.isRecording.value) {
                val bytesRead = audioRecord.read(buffer, 0, buffer.size)
                if (bytesRead > 0) {
                    synchronized(completeAudioData) {
                        completeAudioData.addAll(buffer.copyOf(bytesRead).toList())

                        // 누적 데이터 크기가 1초 분량(32,000바이트) 이상이면 처리
                        if (completeAudioData.size >= 32000) {
                            val audioData = completeAudioData.toByteArray()

                            dataSender.sendDataToMobile(audioData)
                            completeAudioData.clear()
                        }
                    }
                }
            }

            audioRecord.stop()
            audioRecord.release()
        }
    }

    fun stopRecording() {
        audioConfig.isRecording.value = false
    }

    private fun setupAudioRecord(): AudioRecord? {
        if (!permissionHandler.isAudioPermissionGranted()) {
            (context as Activity).runOnUiThread {
                Toast.makeText(
                    context,
                    "RECORD_AUDIO permission is required to record audio",
                    Toast.LENGTH_SHORT
                ).show()
            }
            return null
        }

        return try {
            AudioRecord(
                MediaRecorder.AudioSource.MIC,
                audioConfig.sampleRate,
                audioConfig.channelConfig,
                audioConfig.audioFormat,
                audioConfig.bufferSize
            )
        } catch (e: SecurityException) {
            (context as Activity).runOnUiThread {
                Toast.makeText(
                    context,
                    "Failed to access the microphone: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            null
        } catch (e: Exception) {
            (context as Activity).runOnUiThread {
                Toast.makeText(
                    context,
                    "An unexpected error occurred: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            null
        }
    }
}