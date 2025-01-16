package net.azurewebsites.soundsafeguard.service

import android.content.Context
import android.util.Log
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.azurewebsites.soundsafeguard.R

class DataClientService(private val context: Context) : DataClient.OnDataChangedListener {

    private val azureSTT = AzureSTT()

    override fun onDataChanged(dataEventBuffer: DataEventBuffer) {
        for (event in dataEventBuffer) {
            if (event.type == DataEvent.TYPE_CHANGED && event.dataItem.uri.path == "/audio_record") {
                val dataMap = DataMapItem.fromDataItem(event.dataItem).dataMap
                val audioData = dataMap.getByteArray("audio")
                val timestamp = dataMap.getLong("timestamp")

                if (audioData != null) {
                    useAudio(audioData, timestamp)
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

    // Wear OS에서 받은 오디오 데이터를 사용하는 메서드
    private fun useAudio(audioData: ByteArray, timestamp: Long) {
        convertSpeechToText(audioData, context.getString(R.string.korean_KR))
    }

    /*
     * Wear OS에서 받은 오디오 데이터를 사용하는 메서드
     * Azure Speech to Text API를 사용하여 음성을 텍스트로 변환
     */
    private fun convertSpeechToText(audioData: ByteArray, language: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val result =
                azureSTT.recognizeSpeechFromByteArray(audioData, language)
            Log.d("AzureSTT", "Recognized Text: $result")
        }
    }
}