package net.azurewebsites.soundsafeguard.presentation.service

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class PermissionHandler(private val activity: ComponentActivity) {

    // 권한 확인 함수
    fun isAudioPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }

    // 권한 요청 Launcher 생성
    fun createPermissionLauncher() = activity.registerForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(activity, "Permission required for recording", Toast.LENGTH_SHORT).show()
        }
    }
}