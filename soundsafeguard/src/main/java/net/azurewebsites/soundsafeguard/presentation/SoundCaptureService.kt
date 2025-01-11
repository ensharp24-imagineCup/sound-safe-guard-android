package net.azurewebsites.soundsafeguard.presentation

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.PutDataRequest
import com.google.android.gms.wearable.Wearable

class SoundCaptureService : DataClient.OnDataChangedListener {

    private var receivedMessage = mutableStateOf("")
    fun getReceivedMessage() = receivedMessage.value

    fun registerDataClient(context: Context) {
        Wearable.getDataClient(context).addListener(this)
    }

    fun unregisterDataClient(context: Context) {
        Wearable.getDataClient(context).removeListener(this)
    }

    fun sendDataToMobile(context: Context) {
        val putDataReq: PutDataRequest = PutDataMapRequest.create("/dataPath").run {
            dataMap.putString("message_key", "Wear OS 에서 보내는 값입니다.")
            asPutDataRequest()
        }
        Wearable.getDataClient(context).putDataItem(putDataReq)
            .addOnSuccessListener {
                Log.d("MobileDataService", "Wear OS 에서 보내는 값입니다.")
            }
            .addOnFailureListener {
                Log.e("MobileDataService", "Failed to send data", it)
            }
    }

    override fun onDataChanged(dataEventBuffer: DataEventBuffer) {
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