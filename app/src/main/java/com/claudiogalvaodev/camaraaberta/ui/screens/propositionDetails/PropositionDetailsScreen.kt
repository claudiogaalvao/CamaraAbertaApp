package com.claudiogalvaodev.camaraaberta.ui.screens.propositionDetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.claudiogalvaodev.camaraaberta.R
import com.claudiogalvaodev.camaraaberta.data.enums.BrazilStates
import com.claudiogalvaodev.camaraaberta.data.model.deputados.Deputado
import com.claudiogalvaodev.camaraaberta.data.model.proposition.Proposition
import com.claudiogalvaodev.camaraaberta.ui.components.ColoredLabel
import com.claudiogalvaodev.camaraaberta.ui.theme.CamaraAbertaTheme
import com.claudiogalvaodev.camaraaberta.utils.getThumbnailUrl
import com.claudiogalvaodev.camaraaberta.utils.toLocalDateTime
import com.claudiogalvaodev.camaraaberta.utils.toReadableFullDate
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.time.LocalDate

@Composable
fun PropositionDetailsScreen(
    propositionId: Int
) {
    val viewModel: PropositionDetailsViewModel = koinViewModel {
        parametersOf(propositionId)
    }
    val proposition by viewModel.proposition.collectAsState()
    val authors by viewModel.authors.collectAsState()
    val ultimoRelator by viewModel.ultimoRelator.collectAsState()

    CamaraAbertaTheme {
        proposition?.let { prop ->
            PropositionDetailsScreen(
                proposition = prop,
                authors = authors,
                ultimoRelator = ultimoRelator
            )
        }
    }
}

@Composable
private fun PropositionDetailsScreen(
    proposition: Proposition,
    authors: List<Deputado>,
    ultimoRelator: Deputado?
) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF22A87E))
                    .padding(top = 36.dp, bottom = 12.dp, start = 12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = proposition.getTitle(),
                        fontSize = 28.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Última apreciação: ${proposition.statusProposicao.dataHora.toLocalDateTime().toLocalDate().toReadableFullDate()}",
                        color = Color.DarkGray,
                        fontSize = 16.sp
                    )
                    ColoredLabel(
                        text = proposition.statusProposicao.getStatusDescription(),
                        backgroundColor = Color.DarkGray
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(top = 12.dp, bottom = 64.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    InfoMessage(message = proposition.statusProposicao.despacho)
                }
                item {
                    Session(title = "Autores") {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            authors.forEach { author ->
                                DeputadoItem(
                                    urlPhoto = author.ultimoStatus.urlFoto,
                                    name = author.nomeCivil,
                                    siglaPartido = author.ultimoStatus.siglaPartido,
                                    siglaUf = author.ultimoStatus.siglaUf,
                                    situacao = author.ultimoStatus.getSituacaoAtual()
                                )
                            }
                        }
                    }
                }
                item {
                    ultimoRelator?.let { relator ->
                        Session(title = "Último Relator") {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                DeputadoItem(
                                    urlPhoto = relator.ultimoStatus.urlFoto,
                                    name = relator.nomeCivil,
                                    siglaPartido = relator.ultimoStatus.siglaPartido,
                                    siglaUf = relator.ultimoStatus.siglaUf,
                                    situacao = relator.ultimoStatus.getSituacaoAtual()
                                )
                            }
                        }
                    }
                }
                item {
                    Session(title = "Ementa") {
                        Text(
                            text = proposition.ementa
                        )
                    }
                }
            }
//            Button(
//                onClick = { /*TODO*/ },
//                shape = RoundedCornerShape(8.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.White
//                ),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 12.dp)
//                    .align(Alignment.BottomCenter)
//            ) {
//                Text(text = "Seguir".uppercase(), color = Color(0xFF22A87E))
//            }
        }
    }
}

@Composable
private fun DeputadoItem(
    urlPhoto: String,
    name: String,
    siglaPartido: String?,
    siglaUf: String,
    situacao: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            model = urlPhoto,
            error = if (LocalInspectionMode.current) null else painterResource(id = R.drawable.ic_person),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = ColorPainter(Color.Gray)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = "Deputado(a) $name (${siglaPartido ?: "Sem partido"} - $siglaUf)",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = situacao,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
private fun Session(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 20.dp, horizontal = 12.dp)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        content()
    }
}

@Composable
private fun InfoMessage(
    message: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = Color(color = 0xFF0000FF).copy(alpha = 0.2f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = Icons.Default.Warning.name,
            tint = Color(0xFF0000FF)
        )
        Text(
            text = message,
            color = Color.Black,
            fontSize = 14.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PropositionDetailsScreenPreview() {
    PropositionDetailsScreen(
        proposition = Proposition.getMock(
            siglaTipo = "PL",
            numero = 7817,
            ano = 2017,
            ementa = "Dispõe sobre a obrigatoriedade de criação de mecanismos de levantamento e divulgação da demanda por vagas em creches nos Municípios e no Distrito Federal.\\r\\n\\r\\nNOVA EMENTA: Dispõe sobre a obrigatoriedade de criação de mecanismos de levantamento e de divulgação da demanda por vagas no atendimento à educação infantil de crianças de 0 (zero) a 3 (três) anos de idade.",
            despacho = "Retirado de pauta de ofício, em virtude da ausência do relator."
        ),
        authors = listOf(Deputado.getMock("Deputado(a) Herculano Passos", BrazilStates.MG)),
        ultimoRelator = Deputado.getMock("Deputado(a) Herculano Passos", BrazilStates.MG)
    )
}