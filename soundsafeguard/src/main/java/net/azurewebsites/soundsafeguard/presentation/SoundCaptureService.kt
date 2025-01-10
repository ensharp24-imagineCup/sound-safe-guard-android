package net.azurewebsites.soundsafeguard.presentation

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import net.azurewebsites.soundsafeguard.presentation.NotificationUtils.createNotificationChannel

class SoundCaptureService : Service() {

    private lateinit var dataClient: DataClient

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel(this)

        dataClient = Wearable.getDataClient(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = NotificationCompat.Builder(this, "SSG_CHANNEL")
            .setContentTitle("Sound SafeGuard")
            .setContentText("Recording in progress...")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setOngoing(true)
            .build()

        startForeground(1, notification)


        if (intent?.getStringExtra("command") == "startRecording") {
            // 녹음 시작 로직
            sendToMobile("Recording Started", 123)
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

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}