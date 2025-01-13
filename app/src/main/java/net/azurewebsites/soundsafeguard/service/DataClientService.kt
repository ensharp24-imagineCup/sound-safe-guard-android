package net.azurewebsites.soundsafeguard.service

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.Wearable

class DataClientService : DataClient.OnDataChangedListener {

    private var receivedMessage = mutableStateOf("")
    fun getReceivedMessage() = receivedMessage.value

    fun registerDataClient(context: Context) {
        Log.d("DataClientService", "Data client registered")
        Wearable.getDataClient(context).addListener(this)
    }

    fun unregisterDataClient(context: Context) {
        Log.d("DataClientService", "Data client unregistered")
        Wearable.getDataClient(context).removeListener(this)
    }

    override fun onDataChanged(dataEventBuffer: DataEventBuffer) {
        Log.d("DataClientService", "Data changed")

        for (event in dataEventBuffer) {
            if (event.type == DataEvent.TYPE_CHANGED) {
                val path = event.dataItem.uri.path
                if (path == "/dataPath") {
                    val dataMap = DataMapItem.fromDataItem(event.dataItem).dataMap
                    receivedMessage.value = dataMap.getString("message_key") ?: "No message"

                    Log.d("DataClientService", "Data received: ${receivedMessage.value}")
                }
            }
        }
    }
}