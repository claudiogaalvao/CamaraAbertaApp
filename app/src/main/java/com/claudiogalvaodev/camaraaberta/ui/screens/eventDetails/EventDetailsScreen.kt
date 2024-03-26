package com.claudiogalvaodev.camaraaberta.ui.screens.eventDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.claudiogalvaodev.camaraaberta.ui.theme.CamaraAbertaTheme
import org.koin.androidx.compose.koinViewModel

private val descricaoMock = "Educação bilíngue de surdos à luz da LDB\\r\\n 1) MARISA DIAS LIMA (Confirmada)\\r\\n   Coordenadora-Geral de Atendimento Especializado\\r\\n   Secretaria de Educação Continuada, Alfabetização de Jovens e Adultos, Diversidade e Inclusão do Ministério da Educação\\r\\n\\r\\n2) REPRESENTANTE \\r\\n   FNDE - Fundo Nacional de Desenvolvimento da Educação\\r\\n\\r\\n3) MESSIAS RAMOS COSTA (Confirmado)\\r\\n   Coordenador substituto\\r\\n   Curso de Língua de Sinais Brasileira - Português como Segunda Língua\\r\\n\\r\\n4) MAGNO PRADO GAMA PRATES (Confirmado)\\r\\n   Vice-presidente\\r\\n   Federação Nacional de Educação e Integração dos Surdos\\r\\n\\r\\n5) RODRIGO ROSSO MARQUES (Confirmado)\\r\\n   Professor Adjunto\\r\\n   Departamento de Libras do Curso de Letras Libras da Universidade Federal de Santa Catarina\\r\\n\\r\\n\\r\\n(REQ. nº 67/2023-CPD, de autoria da deputada Amália Barros)"

@Composable
fun EventDetailsScreen(
    eventId: Int
) {
    val viewModel: EventDetailsViewModel = koinViewModel()
    val event by viewModel.event.collectAsState()

    LaunchedEffect(key1 = eventId) {
        viewModel.getEvent(eventId)
    }

    CamaraAbertaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFE5E5E5)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Header(event?.title ?: "")

                DescriptionSession()
            }
        }
    }
}

@Composable
fun Header(
    title: String
) {
    Column(
        modifier = Modifier
            .background(Color.White)
    ) {
        AsyncImage(
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth(),
            model = "",
            contentDescription = null,
            placeholder = ColorPainter(Color.Gray)
        )
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.height(30.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(Color.DarkGray, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    text = "Encerrada".uppercase(),
                    color = Color.White
                )
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(Color.DarkGray, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Videocam,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Text(
                        text = "Gravação".uppercase(),
                        color = Color.White
                    )
                }
            }
            Text(
                text = title,
                fontSize = 21.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Audiência pública",
                fontSize = 16.sp,
                color = Color.DarkGray
            )
            Text(
                text = "HOJE, 30 MAIO • 9:00",
                color = Color(0xFF22A87E),
                fontWeight = FontWeight.Bold
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
                Text(text = "Anexo II, Plenário 04")
            }
        }
    }
}

@Composable
fun DescriptionSession() {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Descrição do evento",
            fontWeight = FontWeight.Bold
        )
        Text(
            text = descricaoMock,
            maxLines = 8,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "Ver mais",
            color = Color(0xFF047BEA),
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        )
    }
}

@Composable
@Preview
fun EventDetailsScreenPreview() {
    EventDetailsScreen(1)
}