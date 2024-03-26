package com.claudiogalvaodev.camaraaberta.ui.components

import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.claudiogalvaodev.camaraaberta.data.enums.EventStatus

@Composable
fun AgendaCard(
    modifier: Modifier,
    color: Color,
    timeStart: String,
    timeEnd: String?,
    title: String,
    type: String,
    eventStatus: EventStatus,
    local: String,
    onClick: () -> Unit
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .drawBehind {
                drawRoundRect(
                    color = color,
                    topLeft = Offset(0f, 0f),
                    size = size.copy(width = size.width - 20),
                    cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                )
            }
            .border(
                width = if (eventStatus.isInProgress()) 2.dp else 0.dp,
                color = color,
                shape = CardDefaults.shape
            )
            .padding(start = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = Icons.Default.AccessTime.name,
                        tint = color
                    )
                    Text(
                        text = timeStart + if (timeEnd != null) " - $timeEnd" else "",
                        fontWeight = FontWeight.Bold,
                        color = color
                    )
                }
                if (eventStatus.isFinished() || eventStatus.isInProgress()) {
                    ColoredLabel(
                        text = eventStatus.text.uppercase(),
                        backgroundColor = eventStatus.color
                    )
                }
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
        color = Color(0xFF22A87E).copy(alpha = 0.3f),
        timeStart = "09:00",
        timeEnd = "12:00",
        title = "Legislação que regulamenta a profissão de motorista de aplicativo",
        type = "Audiencia Pública",
        eventStatus = EventStatus.CANCELED,
        local = "Anexo II, Plenário 06",
        onClick = {}
    )
}