package net.azurewebsites.soundsafeguard.presentation.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import net.azurewebsites.soundsafeguard.R

@Composable
fun RecordingScreen(sendDataToMobile: (String) -> Unit) {
    var inputString by remember { mutableStateOf("") }
    var isRecording by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = inputString,
            onValueChange = { inputString = it },
            modifier = Modifier.fillMaxSize()
        )
        Button(
            onClick = {
                isRecording = !isRecording
                if (isRecording)
                    sendDataToMobile(inputString)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_mic_24),
                contentDescription = "Recording",
                modifier = Modifier.fillMaxSize(),
                tint = if (isRecording) Color.Green else Color.Gray
            )
        }
    }
}

@Composable
fun VoiceInputScreen(context: Context) {
    // 음성 인식 결과를 저장할 상태
    var voiceInputText by remember { mutableStateOf("결과가 여기에 표시됩니다.") }

    // ActivityResult를 통해 음성 입력 결과 처리
    val voiceInputLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        activityResult.data?.let { data ->
            val results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            voiceInputText = results?.get(0) ?: "None"
        }
    }


    // 음성 입력 Intent를 시작하는 함수
    fun startVoiceRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_PROMPT, "말씀하세요")
        }
        try {
            voiceInputLauncher.launch(intent)
        } catch (e: Exception) {
            Toast.makeText(
                context,
                "음성 입력을 사용할 수 없습니다.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // UI 구성
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = { startVoiceRecognition() }) {
                Text(text = "음성 입력 시작")
            }
            Text(text = "Voice Result: $voiceInputText")
        }
    }
}