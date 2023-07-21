package com.example.camaraabertaapp.presentation.preposition_themes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.camaraabertaapp.datasource.references.model.ThemeModel

@Composable
fun PrepositionThemesScreen(
    viewModel: PrepositionThemesViewModel = hiltViewModel()
) {
     val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()) {
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
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
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
                    contentPadding = PaddingValues(bottom = 32.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.themes) { theme ->
                        ThemeItem(theme = theme)
                    }
                }
            }
        }
    }
}

// TODO Move to the components package
@Composable
fun ThemeItem(
    theme: ThemeModel
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxSize()
            .heightIn(min = 128.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
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

@Preview
@Composable
fun PrepositionThemesScreenPreview() {
    PrepositionThemesScreen()
}

@Preview
@Composable
fun ThemeItemPreview() {
    ThemeItem(theme = ThemeModel(id = "001", name = "Administração pública"))
}

