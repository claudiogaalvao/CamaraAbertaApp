package com.claudiogalvaodev.camaraaberta.ui.screens.events

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.claudiogalvaodev.camaraaberta.data.enums.EventStatus
import com.claudiogalvaodev.camaraaberta.data.model.event.Event
import com.claudiogalvaodev.camaraaberta.data.model.event.Local
import com.claudiogalvaodev.camaraaberta.data.model.pauta.ProposicaoResumida
import com.claudiogalvaodev.camaraaberta.ui.components.AgendaCard
import com.claudiogalvaodev.camaraaberta.ui.components.BackToTopButton
import com.claudiogalvaodev.camaraaberta.ui.components.HighlightCard
import com.claudiogalvaodev.camaraaberta.ui.components.PropositionItem
import com.claudiogalvaodev.camaraaberta.ui.components.timeline.TimelineNode
import com.claudiogalvaodev.camaraaberta.ui.screens.common.BaseScreen
import com.claudiogalvaodev.camaraaberta.ui.screens.common.RequestState
import com.claudiogalvaodev.camaraaberta.ui.screens.common.rememberBaseScreenComponentState
import com.claudiogalvaodev.camaraaberta.ui.theme.CamaraAbertaTheme
import com.claudiogalvaodev.camaraaberta.utils.getTime
import com.claudiogalvaodev.camaraaberta.utils.toLocalDateTime
import com.claudiogalvaodev.camaraaberta.utils.toReadableFullDate
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreen(
    navigateToEventDetails: (Int) -> Unit,
    navigateToPropositionDetails: (Int) -> Unit
) {
    val viewModel: EventsViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val currentDate by viewModel.currentDate.collectAsState(initial = LocalDate.now())

    val baseScreenState = rememberBaseScreenComponentState(
        key = currentDate,
        isTopButtonVisible = currentDate != LocalDate.now()
    )

    var openDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    LaunchedEffect(key1 = currentDate) {
        datePickerState.selectedDateMillis = currentDate.toEpochDay() * 86400000
    }

    BaseScreen(
        state = baseScreenState,
        contentState = state,
        header = {
            Header(
                currentDate = currentDate,
                onDateClicked = { openDatePicker = true },
                onPreviousDateClicked = { viewModel.selectPreviousDate() },
                onNextDateClicked = { viewModel.selectNextDate() }
            )
        },
        topButton = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { viewModel.resetCurrentDate() }
                ) {
                    Text("Voltar para hoje")
                }
            }
        },
        dialog = {
            if (openDatePicker) {
                DatePickerDialog(
                    state = datePickerState,
                    onConfirm = { selectedDate ->
                        viewModel.selectDate(selectedDate)
                        openDatePicker = false
                    },
                    onDismiss = { openDatePicker = false }
                )
            }
        }
    ) { data ->
        EventsScreen(
            uiModel = data,
            onEventClicked = { navigateToEventDetails(it) },
            onPropositionClicked = { navigateToPropositionDetails(it) }
        )
    }
}

@Composable
fun EventsScreen(
    uiModel: EventsUiModel,
    onEventClicked: (Int) -> Unit,
    onPropositionClicked: (id: Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val parentLazyListState = rememberLazyListState()
    val isBackToTopVisible by remember {
        derivedStateOf { parentLazyListState.firstVisibleItemIndex > 0 }
    }
    val isScrolling by remember {
        derivedStateOf { parentLazyListState.isScrollInProgress }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEEEEE))
    ) {
        LazyColumn(
            state = parentLazyListState,
            contentPadding = PaddingValues(bottom = 64.dp, top = 12.dp)
        ) {
            if (uiModel.eventsInProgress.isNotEmpty()) {
                item {
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

                            HighlightOnGoingEvents(eventsInProgress = uiModel.eventsInProgress) { eventId ->
                                onEventClicked(eventId)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(vertical = 8.dp))
                }
            }

            item {
                ProjetosDeLeiDoDia(
                    propositions = uiModel.projetosDeLeiDoDia,
                    onPropositionClicked = onPropositionClicked
                )
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
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
                parentLazyListState.animateScrollToItem(0)
            }
        }
    }
}

@Composable
fun Header(
    currentDate: LocalDate,
    onDateClicked: () -> Unit,
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
                contentDescription = Icons.Default.ArrowCircleLeft.name,
                tint = Color.White
            )
        }
        TextButton(onClick = onDateClicked) {
            Text(
                text = currentDate.toReadableFullDate().uppercase(),
                fontSize = 18.sp,
                modifier = Modifier.padding(16.dp),
                color = Color.White
            )
        }
        IconButton(onClick = onNextDateClicked) {
            Icon(
                imageVector = Icons.Default.ArrowCircleRight,
                contentDescription = Icons.Default.ArrowCircleRight.name,
                tint = Color.White
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HighlightOnGoingEvents(
    eventsInProgress: List<Event>,
    onEventClicked: (Int) -> Unit
) {
    val highlightLazyListState = rememberLazyListState()
    val highlightSnapBehavior = rememberSnapFlingBehavior(lazyListState = highlightLazyListState)

    LazyRow(
        state = highlightLazyListState,
        flingBehavior = highlightSnapBehavior,
        horizontalArrangement = Arrangement
            .spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        items(
            items = eventsInProgress,
            key = { event -> event.id }
        ) { event ->
            event.videoId?.let { videoId ->
                HighlightCard(
                    title = event.title,
                    type = event.descricaoTipo,
                    videoId = videoId,
                    fullWidth = eventsInProgress.size == 1,
                    onClick = { onEventClicked(event.id) }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProjetosDeLeiDoDia(
    propositions: RequestState<List<ProposicaoResumida>>,
    onPropositionClicked: (id: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(vertical = 21.dp)
    ) {
        Text(
            text = "Projetos de lei do dia",
            modifier = Modifier.padding(start = 12.dp, bottom = 8.dp),
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold
        )
        when (propositions) {
            is RequestState.Loading -> {
                // Loading
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(48.dp),
                        color = Color(0xFF22A87E),
                        trackColor = Color(0xFFE0E0E0)
                    )
                }
            }
            is RequestState.Success -> {
                val lazyListState = rememberLazyListState()

                val snapBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)

                // Success
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(propositions.data.size.toString())
                            }
                            append(" projetos de lei")
                        },
                        color = Color.Gray
                    )
                    LazyRow (
                        state = lazyListState,
                        flingBehavior = snapBehavior,
                        horizontalArrangement = Arrangement
                            .spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp)
                    ) {
                        items(
                            items = propositions.data,
                            key = { proposicao -> proposicao.id }
                        ) { proposicao ->
                            PropositionItem(
                                title = proposicao.getIdentificador(),
                                description = proposicao.ementa
                            ) {
                                onPropositionClicked(proposicao.id)
                            }
                        }
                    }
                }
            }
            is RequestState.Empty -> {
                // Empty
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    text = "Nenhum projeto de lei para o dia selecionado",
                    textAlign = TextAlign.Center
                )
            }
            is RequestState.Error -> {
                // Error
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    text = "Ocorreu um erro inesperado. Tente novamente mais tarde.",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    state: DatePickerState,
    onConfirm: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    DatePickerDialog(
        onDismissRequest = { /*TODO*/ },
        confirmButton = {
            TextButton(
                onClick = {
                    state.selectedDateMillis?.let {
                        val selectedDate = LocalDate.ofEpochDay(it / 86400000)
                        onConfirm(selectedDate)
                    }
                }
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancelar")
            }
        }
    ) {
        DatePicker(state = state)
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
                eventsInProgress = getEvents().take(3),
                projetosDeLeiDoDia = RequestState.Empty
            ),
            onEventClicked = {},
            onPropositionClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    CamaraAbertaTheme {
        Header(
            currentDate = LocalDate.now(),
            onDateClicked = {},
            onPreviousDateClicked = {},
            onNextDateClicked = {}
        )
    }
}