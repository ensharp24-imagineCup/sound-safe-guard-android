package net.azurewebsites.soundsafeguard.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text

@Composable
fun AudioRecorderScreen(
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit
) {
    var isRecording by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isRecording) {
            Button(onClick = {
                isRecording = false
                onStopRecording()
            }) {
                Text(text = "Stop Recording", modifier = Modifier.fillMaxSize())
            }
        } else {
            Button(onClick = {
                isRecording = true
                onStartRecording()
            }) {
                Text(text = "Start Recording", modifier = Modifier.fillMaxSize())
            }
        }
    }
}