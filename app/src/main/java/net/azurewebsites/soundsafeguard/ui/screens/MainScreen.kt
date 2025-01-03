package net.azurewebsites.soundsafeguard.ui.screens

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.azurewebsites.soundsafeguard.R
import net.azurewebsites.soundsafeguard.ui.components.CustomText
import net.azurewebsites.soundsafeguard.viewmodel.MainViewModel


@Composable
fun MainScreen(viewModel: MainViewModel) {
    var isActivated by viewModel.isActivated
    val offset = Offset(5.0f, 10.0f)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
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
                    onCheckedChange = { viewModel.isActivated.value = it },
                    modifier = Modifier.size(77.dp, 32.dp)
                )
            }
        }
    }
}