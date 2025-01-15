package net.azurewebsites.soundsafeguard.service

import android.content.Context
import android.util.Log
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.Wearable

class DataClientService : DataClient.OnDataChangedListener {

    fun registerDataClient(context: Context) {
        Wearable.getDataClient(context).addListener(this)
    }

    fun unregisterDataClient(context: Context) {
        Wearable.getDataClient(context).removeListener(this)
    }

    override fun onDataChanged(dataEventBuffer: DataEventBuffer) {
        for (event in dataEventBuffer) {
            if (event.type == DataEvent.TYPE_CHANGED && event.dataItem.uri.path == "/audio_record") {
                val dataMap = DataMapItem.fromDataItem(event.dataItem).dataMap
                val audioData = dataMap.getByteArray("audio")
                val timestamp = dataMap.getLong("timestamp")

                useAudio(audioData, timestamp)
            }
        }
    }

    private fun useAudio(audioData: ByteArray?, timestamp: Long) {
        Log.d(
            "DataClientService",
            "Received audio data: ${audioData?.size} bytes, timestamp: $timestamp"
        )
    }
}