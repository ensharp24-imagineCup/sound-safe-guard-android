package net.azurewebsites.soundsafeguard.ui.screens

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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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

@Composable
fun CustomSoundScreen(
    navController: NavController,
    viewModel: SoundViewModel,
    mainViewModel: MainViewModel
){
    val isActivated by mainViewModel.isActivated
    var isRecording by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var showConfirmationDialog by remember { mutableStateOf(false) }

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

            Spacer(modifier = Modifier.height(150.dp))

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
                                showDialog = false
                            } else {
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
                text = if (isRecording) "Recording..." else "Waiting...",
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.inter_regular)),
                lineHeight = 18.sp, // 16포인트의 110%에 해당하는 줄 간격
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