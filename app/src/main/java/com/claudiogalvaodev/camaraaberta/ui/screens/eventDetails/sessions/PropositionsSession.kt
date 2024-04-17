package com.claudiogalvaodev.camaraaberta.ui.screens.eventDetails.sessions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.claudiogalvaodev.camaraaberta.data.model.pauta.Pauta
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PropositionsSession(
    tabs: List<PropositionTab>,
    onPropositionClicked: (Int) -> Unit
) {
    if (tabs.isEmpty()) return

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val couroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)

    Column {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, tabItem ->
                Tab(
                    text = {
                        Text(text = tabItem.title)
                    },
                    selected = selectedTabIndex == index,
                    onClick = {
                        couroutineScope.launch {
                            if (selectedTabIndex != index) return@launch
                            if (lazyListState.firstVisibleItemIndex == 0) return@launch
                            lazyListState.animateScrollToItem(0)
                        }
                        selectedTabIndex = index
                    }
                )
            }
        }

        LazyRow(
            state = lazyListState,
            flingBehavior = snapBehavior,
            modifier = Modifier
                .background(Color.White)
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement
                .spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 12.dp)
        ) {
            items(
                items = tabs[selectedTabIndex].pauta,
                key = { proposition -> proposition.id }
            ) { proposition ->
                PropositionItem(
                    title = proposition.titulo,
                    topic = proposition.topico,
                    description = proposition.getEmenta(),
                    onClick = {
                        onPropositionClicked(proposition.getProposicaoId())
                    }
                )
            }
        }
    }
}

@Composable
private fun PropositionItem(
    title: String,
    topic: String,
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
            Text(
                text = topic,
                color = Color(0xFF22A87E),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
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

sealed class PropositionTab(
    val index: Int,
    val title: String,
    var pauta: List<Pauta>
) {
    data object ToBeAnalyzed: PropositionTab(0, "Pauta da reunião", emptyList())
    data object Analyzed : PropositionTab(0, "Propostas analisadas", emptyList())
    data object UnAnalyzed : PropositionTab(1, "Propostas não analisadas", emptyList())

    companion object {
        fun getTabs(pauta: List<Pauta>, isAfterNow: Boolean): List<PropositionTab> {
            return getTabsFutureEvent(pauta)
        }
        private fun getTabsFutureEvent(pauta: List<Pauta>): List<PropositionTab> {
            val toBeAnalyzed = ToBeAnalyzed
            toBeAnalyzed.pauta = pauta
            return listOf(toBeAnalyzed)
        }
        private fun getTabsPastEvent(
            pautaAnalyzed: List<Pauta>,
            pautaUnAnalyzed: List<Pauta>
        ): List<PropositionTab> {
            val analyzed = Analyzed
            analyzed.pauta = pautaAnalyzed

            val unAnalyzed = UnAnalyzed
            unAnalyzed.pauta = pautaUnAnalyzed

            return listOf(analyzed, unAnalyzed)
        }
    }
}

val pautaMock = listOf(
    Pauta.getMock(
        topico = "Tramitação Ordinária",
        titulo = "PL 1425/2020",
        ementa = "Altera a Lei nº 9.503, de 23 de setembro de 1997, que institui o Código de Trânsito Brasileiro, para dispor sobre curso de recuperação de dependentes para obtenção de Carteira Nacional de Habilitação."
    ),
    Pauta.getMock(
        topico = "Requerimentos",
        titulo = "REQ 9/2024 CVT",
        ementa = "Requer realização de audiência pública na cidade de Joinville - SC para discutir a situação do transporte público coletivo no município"
    ),
    Pauta.getMock(
        topico = "Requerimentos",
        titulo = "REQ 10/2024 CVT",
        ementa = "Dispõe sobre a relação de trabalho intermediado por empresas operadoras de aplicativos de transporte remunerado privado individual de passageiros em veículos automotores de quatro rodas e estabelece mecanismos de inclusão previdenciária e outros direitos para melhoria das condições de trabalho."
    )
)

@Composable
@Preview
private fun PropositionsSessionPreview() {
    PropositionsSession(
        tabs = PropositionTab.getTabs(pautaMock, isAfterNow = false),
        onPropositionClicked = {}
    )
}

@Composable
@Preview
private fun PropositionItemPreview() {
    PropositionItem(
        title = pautaMock[0].titulo,
        topic = pautaMock[0].topico,
        description = pautaMock[2].getEmenta(),
        onClick = {}
    )
}