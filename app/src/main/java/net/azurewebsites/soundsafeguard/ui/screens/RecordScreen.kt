package net.azurewebsites.soundsafeguard.ui.screens

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import net.azurewebsites.soundsafeguard.model.AudioRecoder



@Composable
fun RecordScreen() {
    //context 불러오기
    val context = LocalContext.current

    //녹음 상태관리
    var isRecording by remember { mutableStateOf(false) }

    val assetManager = net.azurewebsites.soundsafeguard.model.AssetManager()
    val interpreter = assetManager.loadInterpreter(context, "yamnet.tflite")
    val inputShape = interpreter.getInputTensor(0).shape()  // 입력 텐서 크기
    val outputShape = interpreter.getOutputTensor(0).shape()  // 출력 텐서 크기
    println("Input shape: ${inputShape.joinToString()}")
    println("Output shape: ${outputShape.joinToString()}")
    //AudioRecorder 생성 (녹음, 데이터 출력 등 )
    val audioRecoder = AudioRecoder(context, interpreter)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Button (
            onClick = {

                isRecording = !isRecording

                if (isRecording) {
                    audioRecoder.startRecord()
                } else {
                    audioRecoder.stopRecord()
                }
            }
        ) {
            Text(text = if (isRecording) "녹음 정지" else "녹음 시작")
        }

        // 녹음 중 일 때 test
        if (isRecording) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("recording...")
        }
    }

}

