package com.claudiogalvaodev.camaraaberta.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AgendaCard(
    modifier: Modifier,
    timeStart: String,
    timeEnd: String,
    title: String,
    type: String,
    local: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = Icons.Default.AccessTime.name,
                    tint = Color(0xFF22A87E)
                )
                Text(
                    text = "$timeStart - $timeEnd",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF22A87E)
                )
            }

            Text(
                text = title,
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = type,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                color = Color.DarkGray
            )
            Text(
                text = local,
                fontSize = 16.sp,
                maxLines = 2,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
@Preview
fun AgendaCardPreview() {
    AgendaCard(
        modifier = Modifier,
        timeStart = "09:00",
        timeEnd = "12:00",
        title = "Legislação que regulamenta a profissão de motorista de aplicativo",
        type = "Audiencia Pública",
        local = "Anexo II, Plenário 06",
        onClick = {}
    )
}