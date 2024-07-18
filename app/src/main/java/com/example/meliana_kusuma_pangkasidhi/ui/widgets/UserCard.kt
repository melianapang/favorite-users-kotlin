package com.example.meliana_kusuma_pangkasidhi.ui.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meliana_kusuma_pangkasidhi.ui.theme.teal100
import com.example.meliana_kusuma_pangkasidhi.ui.theme.teal300
import com.example.meliana_kusuma_pangkasidhi.ui.theme.teal600

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCard(
    username: String,
    avatarUrl: String,
    navigateToDetailScreen: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 4.dp),
        onClick = navigateToDetailScreen,
        shape = RoundedCornerShape(16),
        border = BorderStroke(1.dp, teal300),
        colors = CardDefaults.cardColors(
            containerColor = teal100,
        ),
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            content = {
                ProfileImage(
                    imageUrl = avatarUrl,
                    size = 42.dp,
                )
                Text(
                    text = username,
                    modifier = Modifier.padding(horizontal = 10.dp),
                    color = teal600,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Start,
                )
            }
        )

    }
}