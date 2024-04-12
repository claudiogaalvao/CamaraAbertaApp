package com.claudiogalvaodev.camaraaberta.ui.screens.eventDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.claudiogalvaodev.camaraaberta.R
import com.claudiogalvaodev.camaraaberta.data.model.event.Event
import com.claudiogalvaodev.camaraaberta.data.model.event.Local
import com.claudiogalvaodev.camaraaberta.ui.screens.eventDetails.sessions.PropositionTab
import com.claudiogalvaodev.camaraaberta.ui.screens.eventDetails.sessions.PropositionsSession
import com.claudiogalvaodev.camaraaberta.ui.screens.eventDetails.sessions.pautaMock
import com.claudiogalvaodev.camaraaberta.ui.theme.CamaraAbertaTheme
import com.claudiogalvaodev.camaraaberta.utils.getThumbnailUrl
import com.claudiogalvaodev.camaraaberta.utils.isAfterToday
import com.claudiogalvaodev.camaraaberta.utils.openYoutube
import com.claudiogalvaodev.camaraaberta.utils.toLocalDateTime
import com.claudiogalvaodev.camaraaberta.utils.toReadableFullDate
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

private val descricaoMock = "Educação bilíngue de surdos à luz da LDB\\r\\n 1) MARISA DIAS LIMA (Confirmada)\\r\\n   Coordenadora-Geral de Atendimento Especializado\\r\\n   Secretaria de Educação Continuada, Alfabetização de Jovens e Adultos, Diversidade e Inclusão do Ministério da Educação\\r\\n\\r\\n2) REPRESENTANTE \\r\\n   FNDE - Fundo Nacional de Desenvolvimento da Educação\\r\\n\\r\\n3) MESSIAS RAMOS COSTA (Confirmado)\\r\\n   Coordenador substituto\\r\\n   Curso de Língua de Sinais Brasileira - Português como Segunda Língua\\r\\n\\r\\n4) MAGNO PRADO GAMA PRATES (Confirmado)\\r\\n   Vice-presidente\\r\\n   Federação Nacional de Educação e Integração dos Surdos\\r\\n\\r\\n5) RODRIGO ROSSO MARQUES (Confirmado)\\r\\n   Professor Adjunto\\r\\n   Departamento de Libras do Curso de Letras Libras da Universidade Federal de Santa Catarina\\r\\n\\r\\n\\r\\n(REQ. nº 67/2023-CPD, de autoria da deputada Amália Barros)"

@Composable
fun EventDetailsScreen(
    eventId: Int,
    navigateToPropositionDetails: (Int) -> Unit
) {
    val viewModel: EventDetailsViewModel = koinViewModel {
        parametersOf(eventId)
    }
    val eventDetailsUiModel by viewModel.eventDetailsUiModel.collectAsState()

    CamaraAbertaTheme {
        eventDetailsUiModel?.let { uiModel ->
            EventDetailsScreen(
                eventDetailsUiModel = uiModel,
                onPropositionClicked = navigateToPropositionDetails
            )
        }
    }
}

@Composable
private fun EventDetailsScreen(
    eventDetailsUiModel: EventDetailsUiModel,
    onPropositionClicked: (Int) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFE5E5E5)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Header(
                title = eventDetailsUiModel.event.title,
                status = eventDetailsUiModel.event.situacao,
                eventType = eventDetailsUiModel.event.descricaoTipo,
                initialDate = eventDetailsUiModel.event.dataHoraInicio,
                isFinished = eventDetailsUiModel.event.isFinished,
                location = eventDetailsUiModel.event.local,
                videoId = eventDetailsUiModel.event.videoId
            )

            if (eventDetailsUiModel.pauta.isNotEmpty()) {
                PropositionsSession(
                    tabs = PropositionTab.getTabs(
                        pauta = eventDetailsUiModel.pauta,
                        isAfterNow = eventDetailsUiModel.event.dataHoraInicio.toLocalDateTime().toLocalDate().isAfterToday()
                    ),
                    onPropositionClicked = onPropositionClicked
                )
            }

            // DescriptionSession()
        }
    }
}

@Composable
private fun Header(
    title: String,
    status: String,
    eventType: String,
    initialDate: String,
    isFinished: Boolean,
    location: String,
    videoId: String?
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier.clickable {
                videoId?.let { id -> context.openYoutube(id) }
            },
            contentAlignment = Alignment.TopEnd
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth(),
                model = if (LocalInspectionMode.current) "" else {
                    videoId?.let { id -> getThumbnailUrl(id) } ?: R.drawable.plenario_camara_dos_deputados
                },
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = ColorPainter(Color.Gray)
            )
            if (videoId != null) {
                val color = if (isFinished) Color.DarkGray else Color.Red
                val text = if (isFinished) "Registro" else "Ao vivo"
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(color, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Videocam,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Text(
                        text = text.uppercase(),
                        color = Color.White
                    )
                }
            }
        }
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
                    text = eventType,
                    fontSize = 16.sp,
                    color = Color.White
                )
                Text(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(Color.DarkGray, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    text = status.uppercase(),
                    color = Color.White
                )
            }
            Text(
                text = title,
                fontSize = 21.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = initialDate.toLocalDateTime().toLocalDate().toReadableFullDate().uppercase(),
                color = Color(0xFF22A87E),
                fontWeight = FontWeight.Bold
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
                Text(text = location)
            }
        }
    }
}

@Composable
private fun DescriptionSession() {
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

private val eventMock = Event(
    id = 1,
    descricao = "Evento 1",
    descricaoTipo = "Tipo 1",
    dataHoraInicio = "2024-03-25T11:00",
    dataHoraFim = "2024-03-25T12:00",
    localCamara = Local(
        andar = "1",
        nome = "A",
        predio = "B",
        sala = "1"
    ),
    orgaos = emptyList(),
    localExterno = null,
    situacao = "Realizado",
    uri = "",
    urlRegistro = null
)

@Composable
@Preview
private fun EventDetailsScreenPreview() {
    EventDetailsScreen(
        EventDetailsUiModel(
            event = eventMock,
            pauta = pautaMock
        ),
        onPropositionClicked = {}
    )
}