package net.azurewebsites.soundsafeguard.ui.components.soundsetting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.azurewebsites.soundsafeguard.R

@Composable
fun SearchBar(
    searchedName: String,
    modifier: Modifier = Modifier,
    onSearchQueryChanged: (String) -> Unit) {
    Row(
        modifier = modifier
    ) {
        BasicTextField(
            value = searchedName,
            onValueChange = { newValue ->
                onSearchQueryChanged(newValue)
            },
            singleLine = true,
            modifier = Modifier
                .background(color = Color(0xFFEAEAEA), shape = RoundedCornerShape(12.dp))
                .width(172.dp)
                .fillMaxHeight(),
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    if (searchedName.isEmpty()) {
                        Text(
                            text = "Name",
                            fontSize = 16.sp,
                            color = Color(0xFF888888)
                        )
                    }

                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        // category
        CategoryComposable()
    }
}

@Composable
fun CategoryComposable() {
    Row(
        modifier = Modifier
            .background(Color.Black, shape = RoundedCornerShape(12.dp))
            .fillMaxHeight()
            .width(120.dp)
            .padding(11.dp)
    ) {
        Text(
            text = "Category",
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.category_selected_icon),
            contentDescription = "Siren Icon",
            modifier = Modifier
                .size(13.dp)
                .align(Alignment.CenterVertically)
        )
    }
}