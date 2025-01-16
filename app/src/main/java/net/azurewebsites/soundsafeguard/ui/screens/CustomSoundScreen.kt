package net.azurewebsites.soundsafeguard.ui.screens

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import net.azurewebsites.soundsafeguard.R
import net.azurewebsites.soundsafeguard.viewmodel.MainViewModel
import net.azurewebsites.soundsafeguard.viewmodel.SoundViewModel
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import org.json.JSONArray
import java.io.InputStream
import kotlinx.coroutines.delay
import net.azurewebsites.soundsafeguard.model.AudioRecoder

@Composable
fun CustomSoundScreen(
    navController: NavController,
    viewModel: SoundViewModel,
    mainViewModel: MainViewModel,
    audioRecoder: AudioRecoder
){
    val isActivated by mainViewModel.isActivated
    var isRecording by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var showConfirmationDialog by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("Select Category") }
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val categories = remember { mutableStateOf(listOf<String>()) }
    var dotCount by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        val inputStream: InputStream = context.assets.open("sound_list.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val jsonArray = JSONArray(jsonString)
        val categoryList = mutableListOf<String>()

        for (i in 0 until jsonArray.length()) {
            categoryList.add(jsonArray.getString(i))
        }

        categories.value = categoryList

        while (true) {
            delay(800)
            dotCount = (dotCount + 1) % 4
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().then(
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
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
        ){
            CustomSoundSettingText(
                modifier = Modifier.padding(top = 43.4.dp, bottom = 10.dp),
                subModifier = Modifier.padding(bottom = 25.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(top = 16.dp)
                    .background(Color.White, shape = RoundedCornerShape(5))
                    .clickable { expanded = !expanded },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Category",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(16.dp),
                    color = Color.Black,
                )
                Text(
                    text = selectedCategory + " >",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp),
                    color = Color.Gray,
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                categories.value.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            selectedCategory = category
                            expanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(80.dp))

            Box(
                modifier = Modifier
                    .size(170.dp)
                    .background(
                        Color(0xFFD1D1D1),
                        CircleShape
                    )
                    .clickable(
                        onClick = {
                            isRecording = !isRecording
                            if (isRecording) {
                                audioRecoder.startCustomRecord() // 녹음 시작
                                showDialog = false
                            } else {
                                audioRecoder.stopCustomRecord() // 녹음 종료
                                showDialog = true
                            }
                        },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                if (isRecording) {
                    WaveEffect()
                }
                if (isRecording) {
                    Box(
                        modifier = Modifier
                            .size(170.dp)
                            .background(Color.White, CircleShape)
                    )
                }
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Mic Icon",
                    modifier = Modifier.size(80.dp),
                    tint = if (isRecording) Color(0xFF00A980) else Color(0xFF959595)
                )
            }

            Spacer(modifier = Modifier.height(180.dp))

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = if (isRecording) "Recording" else "Waiting${".".repeat(dotCount)}",
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.inter_regular)),
                lineHeight = 18.sp,
                color = Color(0xFF888888)
            )
        }

        if (showDialog) {
            var soundName by remember { mutableStateOf("") }
            Dialog(onDismissRequest = { showDialog = false }) {
                Box(
                    modifier = Modifier
                        .size(355.dp, 235.dp)
                        .background(Color.White, shape = RoundedCornerShape(15.dp))
                        .padding(16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        TextField(
                            value = soundName,
                            onValueChange = { soundName = it },
                            placeholder = { Text("Sound name") },
                            modifier = Modifier.size(252.dp, 51.dp)
                        )

                        Spacer(modifier = Modifier.height(19.dp))

                        Text(
                            "Please input the name of the sound",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.inter_regular)),
                            lineHeight = 18.sp,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(17.dp))

                        Text(
                            "It will be saved as the name you entered.",
                            fontSize = 13.sp,
                            fontFamily = FontFamily(Font(R.font.inter_regular)),
                            lineHeight = 15.sp,
                            color = Color(0xFF888888)
                        )

                        Spacer(modifier = Modifier.height(19.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                onClick = { showDialog = false },
                                colors = ButtonDefaults.buttonColors(Color(0xFFE0E3EB)),
                                modifier = Modifier
                                    .size(115.dp, 35.dp)
                                    .background(Color(0xFFE0E3EB), shape = RoundedCornerShape(5.dp))
                            ) {
                                Text(
                                    "cancel",
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.inter_regular)),
                                    lineHeight = 18.sp,
                                    color = Color(0xFF888888)
                                )
                            }

                            Button(
                                onClick = {
                                    handleSave(selectedCategory, soundName, audioRecoder.recordedAudio)
                                    showDialog = false
                                    showConfirmationDialog = true
                                },
                                colors = ButtonDefaults.buttonColors(Color(0xFF5664E4)),
                                modifier = Modifier
                                    .size(115.dp, 35.dp)
                                    .background(Color(0xFF5664E4), shape = RoundedCornerShape(5.dp))
                            ) {
                                Text(
                                    "next",
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.inter_regular)),
                                    lineHeight = 18.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }

        if (showConfirmationDialog) {
            ConfirmationDialog(onDismiss = { showConfirmationDialog = false })
        }
    }
}

@Composable
fun CustomSoundSettingText(
    modifier: Modifier = Modifier,
    subModifier: Modifier = Modifier
) {
    // Title and Subtitle
    Text(
        text = "Sound Setting",
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier,
        color = Color.Black
    )
    Text(
        text = "Please don't let me hear the surrounding noise.",
        fontSize = 16.sp,
        color = Color.Gray,
        modifier = subModifier,
        lineHeight = 18.sp
    )
}

@Composable
fun WaveEffect() {
    val infiniteTransition = rememberInfiniteTransition()
    val waveAnimation by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(modifier = Modifier.size(170.dp)) {
        drawCircle(
            color = Color(0xFF00A980).copy(alpha = 0.5f),
            radius = 90.dp.toPx() * waveAnimation,
            center = center
        )
    }
}





@Composable
fun ConfirmationDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .size(355.dp, 235.dp)
                .background(Color.White, shape = RoundedCornerShape(15.dp))
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Filled.CheckCircleOutline,
                    contentDescription = "CheckCircleOutline Icon",
                    modifier = Modifier.size(65.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Do you want to save the recording?",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter_regular)),
                    lineHeight = 18.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "It will be saved as the name you entered.",
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.inter_regular)),
                    lineHeight = 15.sp,
                    color = Color(0xFF888888)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(Color(0xFFE0E3EB)),
                        modifier = Modifier
                            .size(115.dp, 35.dp)
                            .background(Color(0xFFE0E3EB), shape = RoundedCornerShape(5.dp))
                    ) {
                        Text(
                            "cancel",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.inter_regular)),
                            lineHeight = 18.sp,
                            color = Color(0xFF888888)
                        )
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Button(
                        onClick = {
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFF5664E4)),
                        modifier = Modifier
                            .size(115.dp, 35.dp)
                            .background(Color(0xFF5664E4), shape = RoundedCornerShape(5.dp))
                    ) {
                        Text(
                            "save",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.inter_regular)),
                            lineHeight = 18.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

// 녹음된 파일, 카테고리, 이름을 처리하는 로직을 추가
fun handleSave(category: String, soundName: String, recordedAudio: ByteArray?) {
    if (recordedAudio != null) {
        // 파일 저장 로직을 여기에 추가해주십쇼 행님 카테고리, 이름, 오디오 값 가져왔으니까 이거 머신러닝 서버?에 올리든 해서 학습시키는 걸로 채워주시면 됩니다잉

    }
}