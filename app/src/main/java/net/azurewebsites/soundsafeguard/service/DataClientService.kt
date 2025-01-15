package net.azurewebsites.soundsafeguard.service

import android.content.Context
import android.util.Log
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.Wearable

class DataClientService : DataClient.OnDataChangedListener {

    override fun onDataChanged(dataEventBuffer: DataEventBuffer) {
        Log.d("DataClientService", "Data received")
        for (event in dataEventBuffer) {
            if (event.type == DataEvent.TYPE_CHANGED && event.dataItem.uri.path == "/audio_record") {
                val dataMap = DataMapItem.fromDataItem(event.dataItem).dataMap
                val audioData = dataMap.getByteArray("audio")
                val timestamp = dataMap.getLong("timestamp")

                useAudio(audioData, timestamp)
            }
        }
    }

    fun registerDataClient(context: Context) {
        Wearable.getDataClient(context).addListener(this)
    }

    fun unregisterDataClient(context: Context) {
        Wearable.getDataClient(context).removeListener(this)
    }

    // Wear OS에서 받은 오디오 데이터를 사용하는 메서드
    private fun useAudio(audioData: ByteArray?, timestamp: Long) {
        Log.d(
            "DataClientService",
            "Received audio data: ${audioData?.size} bytes, timestamp: $timestamp"
        )
    }
}