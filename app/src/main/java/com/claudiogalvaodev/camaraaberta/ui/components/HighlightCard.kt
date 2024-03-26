package com.claudiogalvaodev.camaraaberta.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.claudiogalvaodev.camaraaberta.utils.getThumbnailUrl
import com.claudiogalvaodev.camaraaberta.utils.openYoutube

@Composable
fun HighlightCard(
    title: String,
    type: String,
    videoId: String,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.width(300.dp)
    ) {
        Box(
            contentAlignment = Alignment.TopEnd
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        context.openYoutube(videoId)
                    },
                model = getThumbnailUrl(videoId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = ColorPainter(color = Color.Gray)
            )
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Red, RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                ,
                text = "AO VIVO",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            modifier = Modifier.clickable {
                onClick()
            },
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(type)
                }
                append(" - $title")
            },
            maxLines = 3,
            fontSize = 18.sp
        )
    }
}

@Composable
@Preview
fun HighlightCardPreview() {
    HighlightCard(
        title = "Legislação que regulamenta a profissão de motorista de aplicativo",
        type = "Audiência Pública",
        videoId = "videoLink",
        onClick = {}
    )
}
