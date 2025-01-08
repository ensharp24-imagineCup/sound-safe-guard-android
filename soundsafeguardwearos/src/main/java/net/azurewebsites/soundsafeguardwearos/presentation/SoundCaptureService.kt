package net.azurewebsites.soundsafeguardwearos.presentation

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable

class SoundCaptureService : Service() {

    private lateinit var dataClient: DataClient

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        dataClient = Wearable.getDataClient(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, createNotification())

        intent?.let {
            val command = it.getStringExtra("command")
            if (command == "startRecording") {
                val stringInput = it.getStringExtra("string_input") ?: ""
                val intInput = it.getIntExtra("int_input", 0)
                sendToMobile(stringInput, intInput)
            }
        }

        return START_STICKY
    }

    private fun sendToMobile(stringInput: String, intInput: Int) {
        val putDataMapReq = PutDataMapRequest.create("/sound").apply {
            dataMap.putString("string_input", stringInput)
            dataMap.putInt("int_input", intInput)
        }

        dataClient.putDataItem(putDataMapReq.asPutDataRequest())
            .addOnSuccessListener {
                Log.d("WearOS", "데이터 전송 성공")
            }
            .addOnFailureListener {
                Log.e("WearOS", "데이터 전송 실패", it)
            }
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, "SOUND_CHANNEL")
            .setContentTitle("Sound Capture Running")
            .setSmallIcon(android.R.drawable.ic_btn_speak_now)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "SOUND_CHANNEL",
            "Sound Service",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}