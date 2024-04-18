package com.claudiogalvaodev.camaraaberta.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PropositionItem(
    title: String,
    topic: String? = null,
    category: String? = null,
    description: String,
    onClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val cardWidth = configuration.screenWidthDp.dp - 48.dp

    Card(
        modifier = Modifier
            .width(cardWidth)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE5E5E5))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            topic?.let {
                Text(
                    text = topic,
                    color = Color(0xFF22A87E),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
            category?.let {
                Text(
                    text = category,
                    color = Color(0xFF22A87E),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
            Text(
                text = description,
                color = Color.DarkGray,
                fontSize = 14.sp,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}