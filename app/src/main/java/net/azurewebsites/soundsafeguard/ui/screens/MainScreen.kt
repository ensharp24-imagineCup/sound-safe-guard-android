package net.azurewebsites.soundsafeguard.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import net.azurewebsites.soundsafeguard.R
import net.azurewebsites.soundsafeguard.ui.components.CustomText
import net.azurewebsites.soundsafeguard.viewmodel.MainViewModel
import net.azurewebsites.soundsafeguard.viewmodel.SoundViewModel
import org.tensorflow.lite.task.audio.classifier.AudioClassifier

@Composable
fun MainScreen(
    viewModel: SoundViewModel,
    mainViewModel: MainViewModel
) {
    //사용자가 선택한 사운드
    val selectedSounds by viewModel.selectedSound.collectAsState(initial = emptyList())
    val isActivated by mainViewModel.isActivated
    //현재의 LocalContext
    val context = LocalContext.current

    var setText by remember { mutableStateOf("hi") }
    val offset = Offset(5.0f, 10.0f)

    val probabilityThreshold = 0.3f
    val audioClassifier = AudioClassifier.createFromFile(context, "yamnet.tflite")
    val tensor = audioClassifier.createInputTensorAudio()

    val record = try {
        audioClassifier.createAudioRecord()
    } catch (e: Exception) {
        Log.e("AudioRecord", "Failed to create AudioRecord", e)
        null
    }

    //활성화 상태일 때 녹음 시작
    LaunchedEffect(isActivated) {
        if (isActivated) {
            record?.startRecording()
            while (isActivated) {
                withContext(Dispatchers.IO) {
                    val numberOfSamples = tensor.load(record)
                    val output = audioClassifier.classify(tensor)

                    val filteredModelOutput = output[0].categories.filter {
                        it.score > probabilityThreshold
                    }

                    if (filteredModelOutput.isNotEmpty()) {
                        //Test용 코드 label -> score 출력
                        val outputStr = filteredModelOutput.sortedBy { -it.score }
                            .joinToString(separator = "\n") { "${it.label} -> ${it.score}" }
                        withContext(Dispatchers.Main) {
                            setText = outputStr
                        }

                        val matchingCategories =
                            filteredModelOutput.filter { it.label in selectedSounds }
                        println(matchingCategories)
                        if (matchingCategories.isNotEmpty()) {
                            matchingCategories.map {
                                var builder = NotificationCompat.Builder(context, "SSG_CHANNEL")
                                    .setSmallIcon(R.drawable.siren_icon)
                                    .setContentTitle("Alarm Notification!")
                                    .setContentText(it.label)
                                    .setStyle(
                                        NotificationCompat.BigTextStyle()
                                            .bigText("catched " + it.label + "!")
                                    )
                                    .setAutoCancel(true)
                                    .setPriority(NotificationCompat.PRIORITY_HIGH) // 중요도 설정
                                noticeAlarm(context, builder)
                            }
                        }
                    }
                }
                delay(500)
            }
            record?.stop()
        } else {
            record?.stop()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (isActivated) {
                        Modifier.background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFDFF5F9),
                                    Color(0xFFF5EFE2),
                                    Color(0xFFFFF2C7)
                                )
                            )
                        )
                    } else {
                        Modifier.background(Color(0xFFF7F8FA))
                    }
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painterResource(id = if (isActivated) R.drawable.app_icon_shadow else R.drawable.app_icon_off),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
                if (isActivated) {
                    CustomText("Activated!", offset)
                } else {
                    CustomText("Start SSG", offset)
                }

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = setText,
                    color = Color.Black
                )
                if (isActivated) {
                    Text(
                        "SSG listening to the sound.",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.inter_regular)),
                        lineHeight = 18.sp,
                        color = Color.Gray
                    )
                } else {
                    Text(
                        "Try the personalized sound alert service!",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.inter_regular)),
                        lineHeight = 18.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Switch(
                    checked = isActivated,
                    onCheckedChange = { mainViewModel.isActivated.value = it },
                    modifier = Modifier.size(77.dp, 32.dp)
                )
            }
        }
    }
}

fun noticeAlarm(
    context: Context,
    builder: NotificationCompat.Builder
) {
    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //권한이 없을 때 처리 함수
            return
        }

        notify(R.string.channel_name, builder.build())
    }
}