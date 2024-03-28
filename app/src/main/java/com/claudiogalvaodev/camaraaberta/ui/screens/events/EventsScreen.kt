package com.claudiogalvaodev.camaraaberta.ui.screens.events

import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.claudiogalvaodev.camaraaberta.data.enums.EventStatus
import com.claudiogalvaodev.camaraaberta.data.model.Event
import com.claudiogalvaodev.camaraaberta.data.model.Local
import com.claudiogalvaodev.camaraaberta.ui.components.AgendaCard
import com.claudiogalvaodev.camaraaberta.ui.components.BackToTopButton
import com.claudiogalvaodev.camaraaberta.ui.components.HighlightCard
import com.claudiogalvaodev.camaraaberta.ui.components.timeline.TimelineNode
import com.claudiogalvaodev.camaraaberta.ui.screens.common.BaseScreen
import com.claudiogalvaodev.camaraaberta.ui.screens.common.BaseScreenState
import com.claudiogalvaodev.camaraaberta.ui.theme.CamaraAbertaTheme
import com.claudiogalvaodev.camaraaberta.utils.getTime
import com.claudiogalvaodev.camaraaberta.utils.toLocalDateTime
import com.claudiogalvaodev.camaraaberta.utils.toReadableFullDate
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun EventsScreen(
    navigateToEventDetails: (Int) -> Unit
) {
    val viewModel: EventsViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val currentDate by viewModel.currentDate.collectAsState(initial = LocalDate.now())

    BaseScreen(
        state = state,
        header = {
            Header(
                currentDate = currentDate,
                onPreviousDateClicked = { viewModel.selectPreviousDate() },
                onNextDateClicked = { viewModel.selectNextDate() }
            )
        }
    ) { data ->
        EventsScreen(
            uiModel = data,
            currentDate = currentDate,
            onBackToTodayClicked = { viewModel.resetCurrentDate() },
            onEventClicked = { navigateToEventDetails(it) }
        )
    }
}

@Composable
fun EventsScreen(
    uiModel: EventsUiModel,
    currentDate: LocalDate,
    onBackToTodayClicked: () -> Unit,
    onEventClicked: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val isBackCurrentDateVisible by remember(currentDate) {
        mutableStateOf(currentDate != LocalDate.now())
    }
    val isBackToTopVisible by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }
    val isScrolling by remember {
        derivedStateOf { listState.isScrollInProgress }
    }

    Box {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 64.dp)
        ) {
            if (uiModel.eventsInProgress.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White)
                            .padding(vertical = 21.dp)
                    ) {
                        Column {
                            Text(
                                text = "Acontecendo agora",
                                modifier = Modifier.padding(start = 12.dp, bottom = 8.dp),
                                fontSize = 21.sp,
                                fontWeight = FontWeight.Bold
                            )

                            LazyRow(
                                horizontalArrangement = Arrangement
                                    .spacedBy(8.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp)
                            ) {
                                items(
                                    items = uiModel.eventsInProgress,
                                    key = { event -> event.id }
                                ) { event ->
                                    event.videoId?.let { videoId ->
                                        HighlightCard(
                                            title = event.title,
                                            type = event.descricaoTipo,
                                            videoId = videoId,
                                            onClick = { onEventClicked(event.id) }
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(vertical = 8.dp))
                }
            }

            item {
                Text(
                    text = "Agenda do dia",
                    modifier = Modifier.padding(12.dp),
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            items(
                items = uiModel.events,
                key = { event -> event.id }
            ) { event ->
                val eventStatus = EventStatus.get(event.situacao)
                val defaultColor = Color(0xFF22A87E)
                    .copy(alpha = if (event.isFinished.not()) 1f else 0.3f)
                TimelineNode(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    isLast = uiModel.events.last() == event,
                    color = defaultColor
                ) { modifier ->
                    AgendaCard(
                        modifier = modifier,
                        color = defaultColor,
                        timeStart = event.dataHoraInicio.toLocalDateTime().getTime(),
                        timeEnd = event.dataHoraFim?.toLocalDateTime()?.getTime(),
                        title = event.title,
                        type = event.descricaoTipo,
                        eventStatus = eventStatus,
                        local = event.local,
                        onClick = { onEventClicked(event.id) }
                    )
                }
            }
        }

        BackToTopButton(isVisible = isBackToTopVisible, isScrolling = isScrolling) {
            scope.launch {
                listState.animateScrollToItem(0)
            }
        }

        androidx.compose.animation.AnimatedVisibility(
            visible = isBackCurrentDateVisible,
            enter = slideInVertically(),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = onBackToTodayClicked
                ) {
                    Text("Voltar para hoje")
                }
            }
        }
    }
}

@Composable
fun Header(
    currentDate: LocalDate,
    onPreviousDateClicked: () -> Unit,
    onNextDateClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF22A87E))
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onPreviousDateClicked) {
            Icon(
                imageVector = Icons.Default.ArrowCircleLeft,
                contentDescription = Icons.Default.KeyboardArrowLeft.name,
                tint = Color.White
            )
        }
        Text(
            text = currentDate.toReadableFullDate().uppercase(),
            fontSize = 18.sp,
            modifier = Modifier.padding(16.dp),
            color = Color.White
        )
        IconButton(onClick = onNextDateClicked) {
            Icon(
                imageVector = Icons.Default.ArrowCircleRight,
                contentDescription = Icons.Default.KeyboardArrowRight.name,
                tint = Color.White
            )
        }
    }
}

private fun getEvents(): List<Event> {
    val events = mutableListOf<Event>()
    for (i in 1..5) {
        val event = Event(
            id = i,
            descricao = "Evento $i",
            descricaoTipo = "Tipo $i",
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
            urlRegistro = ""
        )
        events.add(event)
    }
    return events
}

@Preview
@Composable
fun EventsScreenPreview() {
    CamaraAbertaTheme {
        EventsScreen(
            uiModel = EventsUiModel(
                events = getEvents(),
                eventsInProgress = emptyList()
            ),
            currentDate = LocalDate.now(),
            onBackToTodayClicked = {},
            onEventClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    CamaraAbertaTheme {
        Header(
            currentDate = LocalDate.now(),
            onPreviousDateClicked = {},
            onNextDateClicked = {}
        )
    }
}