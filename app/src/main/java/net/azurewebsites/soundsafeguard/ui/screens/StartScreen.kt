package net.azurewebsites.soundsafeguard.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
fun StartScreen() {
    val offset = Offset(5.0f, 10.0f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 방패 모양 이미지
        Image(
            painter = painterResource(id = R.drawable.app_icon_shadow),
            contentDescription = null,
            modifier = Modifier.size(108.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        CustomText("Start SSG", offset)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Try the personalized sound alert service!",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.inter_regular)), // Inter Regular 폰트 설정
            lineHeight = 18.sp, // 16포인트의 110%에 해당하는 줄 간격
            //letterSpacing = (-4).sp, // -4%의 글자 간격
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 이메일 입력 필드
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 제출 버튼
        Button(onClick = { /* TODO: 제출 로직 */ }) {
            Text("submit")
        }
    }
}