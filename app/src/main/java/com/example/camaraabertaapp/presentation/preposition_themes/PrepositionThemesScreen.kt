package com.example.camaraabertaapp.presentation.preposition_themes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.camaraabertaapp.data.references.model.ThemeModel

@Composable
fun PrepositionThemesScreen(
    state: PrepositionThemesState,
    onSelectTheme: (id: String) -> Unit,
    onProceed: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
    ) {
        if (state.error.isNotBlank()) {
            Text(text = state.error)
        }
        if (state.isLoading) {
            Text(text = "Carregando...")
        }
        if (state.isLoading.not() && state.error.isBlank()) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                TextButton(
                    onClick = onProceed,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(
                        text = "Pular",
                        textAlign = TextAlign.End
                    )
                }
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = "Selecione pelo menos 3 temas que você tem interesse:"
                )
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 128.dp),
                    contentPadding = PaddingValues(bottom = 128.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.themes) { theme ->
                        ThemeItem(theme = theme) {
                            onSelectTheme(theme.id)
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = Brush.verticalGradient(
                    colorStops = arrayOf(0.0f to Color.Transparent, 0.5f to Color.White)
                ))
                .align(Alignment.BottomCenter)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 12.dp),
                shape = RoundedCornerShape(8.dp),
                onClick = onProceed,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = Color.LightGray
                ),
                enabled = state.isButtonEnabled
            ) {
                Text(text = "Próximo")
            }
        }
    }
}

// TODO Move to the components package
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeItem(
    theme: ThemeModel,
    onClick: () -> Unit
) {
    val currentBackgroundColor = if (theme.isSelected) {
        MaterialTheme.colorScheme.primary
    } else Color.LightGray

    ElevatedCard(
        onClick = onClick,
        modifier = Modifier
            .fillMaxSize()
            .heightIn(min = 128.dp),
        colors = CardDefaults.cardColors(containerColor = currentBackgroundColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(24.dp),
                text = theme.name,
                textAlign = TextAlign.Start,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrepositionThemesScreenPreview() {
    PrepositionThemesScreen(
        PrepositionThemesState(
            themes = listOf(
                ThemeModel(id = "001", "Administracao publica"),
                ThemeModel(id = "002", "Meio ambiente"),
                ThemeModel(id = "003", "Educacao"),
                ThemeModel(id = "004", "Saude"),
                ThemeModel(id = "005", "Direitos humanos"),
            )
        ),
        onSelectTheme = {},
        onProceed = {}
    )
}

@Preview
@Composable
fun ThemeItemPreview() {
    ThemeItem(theme = ThemeModel(id = "001", name = "Administração pública"), onClick = {})
}

