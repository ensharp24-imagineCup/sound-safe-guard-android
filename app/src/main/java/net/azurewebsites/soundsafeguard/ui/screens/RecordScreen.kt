package net.azurewebsites.soundsafeguard.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import net.azurewebsites.soundsafeguard.R
import net.azurewebsites.soundsafeguard.ui.MainActivity
import net.azurewebsites.soundsafeguard.ui.components.CustomText
import org.tensorflow.lite.task.audio.classifier.AudioClassifier
import java.util.Timer
import java.util.TimerTask
import kotlin.coroutines.coroutineContext

@Preview(showBackground = true)
@Composable
fun RecordScreen(){
    val modelPath = "yamnet.tflite"
    var isSuccess = true
    var setText by remember { mutableStateOf("hi") }
    var probabilityThreshold = 0.3f
    val context = LocalContext.current
    var text1 = ""
    val audioClassifier = AudioClassifier.createFromFile(context, modelPath)
    //예외처리
    if (audioClassifier == null) {
        Text(text = "Failed to load model")
        isSuccess = false
        return
    }
    if(isSuccess)text1 = "success"
    val tensor = audioClassifier.createInputTensorAudio()

    val format = audioClassifier.requiredTensorAudioFormat
    val recordSpecs = "Number Of Channels: ${format.channels}\n" +
            "Sample Rate: ${format.sampleRate}"

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

    Timer().schedule(object:TimerTask() {
        override fun run() {
            val numberOfSamples = tensor.load(record)
            val output = audioClassifier.classify(tensor)

            var filteredModelOutuput =
                output[0].categories.filter {
                    it.score > probabilityThreshold
                }


            if(filteredModelOutuput.isNotEmpty()){
                val outputStr = filteredModelOutuput.sortedBy { -it.score }
                    .joinToString(separator = "\n") { "${it.label} -> ${it.score} " }
                setText = outputStr
            }

        }

    },1,500)



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

