package net.azurewebsites.soundsafeguard.ui.components.soundsetting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.azurewebsites.soundsafeguard.R

@Composable
fun SearchBar(
    searchQuery: String,
    modifier: Modifier = Modifier,
    onSearchQueryChanged: (String) -> Unit
) {
    Row(
        modifier = modifier
    ) {
        BasicTextField(
            value = searchQuery,
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
                    if (searchQuery.isEmpty()) {
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
            .padding(start = 11.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = "Category",
            fontSize = 15.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterVertically),
            fontFamily = FontFamily(Font(R.font.inter_semibold)),
        )

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.CenterVertically),
            onClick = { }) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Category DropDown Icon",
                tint = Color.White
            )
        }
    }
}