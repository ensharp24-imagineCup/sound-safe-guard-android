package net.azurewebsites.soundsafeguard.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon

@Composable
fun AudioRecorderScreen(
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit
) {
    var isRecording by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize() // 화면을 가득 채우는 Box
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isRecording) {
            // 녹음 중인 상태
            Button(
                onClick = {
                    isRecording = false
                    onStopRecording()
                },
                modifier = Modifier.fillMaxSize() // 버튼이 화면을 가득 채움
            ) {
                Icon(
                    imageVector = Icons.Default.Stop, // 정지 아이콘
                    contentDescription = "Stop Recording",
                    modifier = Modifier.size(64.dp) // 아이콘 크기
                )
            }
        } else {
            // 녹음 대기 상태
            Button(
                onClick = {
                    isRecording = true
                    onStartRecording()
                },
                modifier = Modifier.fillMaxSize() // 버튼이 화면을 가득 채움
            ) {
                Icon(
                    imageVector = Icons.Default.Mic, // 마이크 아이콘
                    contentDescription = "Start Recording",
                    modifier = Modifier.size(64.dp) // 아이콘 크기
                )
            }
        }
    }
}