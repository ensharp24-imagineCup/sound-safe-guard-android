package net.azurewebsites.soundsafeguard.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.tensorflow.lite.task.audio.classifier.AudioClassifier

@Preview(showBackground = true)
@Composable
fun RecordScreen() {
    var setText by remember { mutableStateOf("hi") }
    var text1 = ""
    val probabilityThreshold = 0.3f
    val audioClassifier = AudioClassifier.createFromFile(LocalContext.current, "yamnet.tflite")

    if (audioClassifier == null) {
        Text(text = "Failed to load model")
        return
    } else
        text1 = "success"

    val tensor = audioClassifier.createInputTensorAudio()
    val record = try {
        audioClassifier.createAudioRecord()
    } catch (e: Exception) {
        Log.e("AudioRecord", "Failed to create AudioRecord", e)
        null
    }
    if (record == null) {
        Log.e("AudioRecord", "AudioRecord creation failed.")
        return
    }

    record.startRecording()

    LaunchedEffect(Unit) {
        while (true) {
            withContext(Dispatchers.IO) {
                val numberOfSamples = tensor.load(record)
                val output = audioClassifier.classify(tensor)

                val filteredModelOutput = output[0].categories.filter {
                    it.score > probabilityThreshold
                }

                if (filteredModelOutput.isNotEmpty()) {
                    val outputStr = filteredModelOutput.sortedBy { -it.score }
                        .joinToString(separator = "\n") { "${it.label} -> ${it.score}" }
                    withContext(Dispatchers.Main) {
                        setText = outputStr
                    }
                }
            }
            delay(500)
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = setText)
            Text(text = text1)
        }
    }
}

