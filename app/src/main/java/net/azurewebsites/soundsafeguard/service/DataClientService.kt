package net.azurewebsites.soundsafeguard.service

import android.content.Context
import android.util.Log
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.Wearable
import com.microsoft.cognitiveservices.speech.audio.AudioConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.azurewebsites.soundsafeguard.R
import net.azurewebsites.soundsafeguard.model.AudioRecoder
import net.azurewebsites.soundsafeguard.utils.ArrayConvertor

class DataClientService(private val context: Context, audioRecoder: AudioRecoder) : DataClient.OnDataChangedListener {

    private val azureSTT = AzureSTT()
    var audioData:FloatArray? = null
    val audioRecoder = audioRecoder
    override fun onDataChanged(dataEventBuffer: DataEventBuffer) {
        for (event in dataEventBuffer) {
            if (event.type == DataEvent.TYPE_CHANGED && event.dataItem.uri.path == "/audio_record") {
                val dataMap = DataMapItem.fromDataItem(event.dataItem).dataMap
                val audioData = dataMap.getByteArray("audio")
                val timestamp = dataMap.getLong("timestamp")

                if (audioData != null) {
                    useAudio(audioData, timestamp)
                    sendAudioData(audioData)
                }
            }
        }
    }

    fun registerDataClient() {
        Wearable.getDataClient(context).addListener(this)
    }

    fun unregisterDataClient() {
        Wearable.getDataClient(context).removeListener(this)
    }

    private fun sendAudioData(audioData: ByteArray){
        var arrayConvertor = ArrayConvertor()
        audioRecoder.processAudioFrame(arrayConvertor.byteArrayToFloatArray(audioData))
    }

    // Wear OS에서 받은 오디오 데이터를 사용하는 메서드
    private fun useAudio(audioData: ByteArray, timestamp: Long) {
        convertSpeechToText(audioData, context.getString(R.string.korean_KR))
    }

    /*
     * Wear OS에서 받은 오디오 데이터를 사용하는 메서드
     * Azure Speech to Text API를 사용하여 음성을 텍스트로 변환
     */
    private fun convertSpeechToText(audioData: ByteArray, language: String) {
        val size=  audioData.size
        val sampleData = audioData.take(size)
        Log.d("AudioRecorderManager", "Audio data size: $size")
        Log.d("AudioRecorderManager", "Audio sample data: $sampleData")

        CoroutineScope(Dispatchers.Main).launch {
            val result =
                azureSTT.recognizeSpeechFromByteArray(audioData, language)
            Log.d("AzureSTT", "Recognized Text: $result")
        }
    }
}