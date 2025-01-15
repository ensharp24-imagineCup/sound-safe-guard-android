package net.azurewebsites.soundsafeguard.presentation.service

import android.util.Log
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.PutDataRequest
import java.util.Date

class DataSender(private val dataClient: DataClient) {

    fun sendDataToMobile(audioData: ByteArray) {
        val putDataReq: PutDataRequest = PutDataMapRequest.create("/audio_record").run {
            dataMap.putByteArray("audio", audioData)
            dataMap.putLong("timestamp", Date().time)
            asPutDataRequest()
        }

        dataClient.putDataItem(putDataReq)
            .addOnSuccessListener {
                Log.d("MobileDataService", "Data sent successfully")
            }
            .addOnFailureListener {
                Log.e("MobileDataService", "Failed to send data", it)
            }
    }
}