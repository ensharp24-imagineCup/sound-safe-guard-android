package net.azurewebsites.soundsafeguard.ui.screens

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.azurewebsites.soundsafeguard.R
import net.azurewebsites.soundsafeguard.ui.components.CustomText

@Preview
@Composable
fun MainScreen() {
    var isActivated by remember { mutableStateOf(false) }
    val offset = Offset(5.0f, 10.0f)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = if (isActivated) Color(0xFFE0F7FA) else Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 아이콘 및 텍스트
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

            // 설명 텍스트
            if (isActivated) {
                Text(
                    "SSG listening to the sound.",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter_regular)), // Inter Regular 폰트 설정
                    lineHeight = 18.sp, // 16포인트의 110%에 해당하는 줄 간격
                    //letterSpacing = (-4).sp, // -4%의 글자 간격
                    color = Color.Gray
                )
            } else {
                Text(
                    "Try the personalized sound alert service!",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter_regular)), // Inter Regular 폰트 설정
                    lineHeight = 18.sp, // 16포인트의 110%에 해당하는 줄 간격
                    //letterSpacing = (-4).sp, // -4%의 글자 간격
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 스위치
            Switch(
                checked = isActivated,
                onCheckedChange = { isActivated = it },
                modifier = Modifier.size(77.dp, 32.dp)
            )
        }
    }
}