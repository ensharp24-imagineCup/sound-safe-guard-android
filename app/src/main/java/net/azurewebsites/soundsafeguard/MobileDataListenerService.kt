package net.azurewebsites.soundsafeguard

import android.util.Log
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.WearableListenerService

class MobileDataListenerService: WearableListenerService() {

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        super.onDataChanged(dataEvents)
        Log.d("MobileApp onDataChanged", "onDataChanged function of Service is running")
        
        for (event in dataEvents) {
            if (event.type == DataEvent.TYPE_CHANGED) {
                val dataItem = event.dataItem
                if (dataItem.uri.path == "/sound") {
                    val dataMap = DataMapItem.fromDataItem(dataItem).dataMap
                    val stringInput = dataMap.getString("string_input") // 문자열 데이터
                    val intInput = dataMap.getInt("int_input") // 정수 데이터

                    Log.d("MobileApp", "Received string_input: $stringInput")
                    Log.d("MobileApp", "Received int_input: $intInput")
                }
            }
        }
    }
}