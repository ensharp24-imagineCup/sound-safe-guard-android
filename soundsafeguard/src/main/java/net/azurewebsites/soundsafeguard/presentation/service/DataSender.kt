package net.azurewebsites.soundsafeguard.presentation.service

import android.content.Context
import android.util.Log
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.PutDataRequest
import com.google.android.gms.wearable.Wearable

class DataSender {

    fun sendDataToMobile(context: Context, message: String) {
        val putDataReq: PutDataRequest = PutDataMapRequest.create("/dataPath").run {
            dataMap.putString("message_key", message)
            asPutDataRequest()
        }

        Wearable.getDataClient(context).putDataItem(putDataReq)
            .addOnSuccessListener {
                Log.d("MobileDataService", message)
            }
            .addOnFailureListener {
                Log.e("MobileDataService", "Failed to send data", it)
            }
    }
}