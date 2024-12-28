package net.azurewebsites.soundsafeguard.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.graphics.Shadow
import net.azurewebsites.soundsafeguard.R

@Composable
fun CustomText(message: String, offset: Offset) {
    BasicText(
        text = message,
        style = TextStyle(
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily(Font(R.font.inter_semibold)),
            lineHeight = 18.sp,
            color = Color.Black,
            shadow = Shadow(
                color = Color.Gray, offset = offset, blurRadius = 1f
            )
        )
    )
} 