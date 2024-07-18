package com.example.meliana_kusuma_pangkasidhi.ui.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.meliana_kusuma_pangkasidhi.ui.theme.meliana_kusuma_pangkasidhiTheme
import com.example.meliana_kusuma_pangkasidhi.ui.theme.teal800

@Composable
fun ProfileImage(imageUrl: String, size: Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .border(1.dp, teal800, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Profile image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileImagePreview() {
    meliana_kusuma_pangkasidhiTheme {
        ProfileImage(
            imageUrl = "https://www.rollingstone.com/wp-content/uploads/2019/12/TaylorSwiftTimIngham.jpg?w=1581&h=1054&crop=1",
            size = 60.dp,
        )
    }
}
