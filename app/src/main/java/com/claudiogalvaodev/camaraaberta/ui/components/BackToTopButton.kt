package com.claudiogalvaodev.camaraaberta.ui.components

import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BoxScope.BackToTopButton(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    isScrolling: Boolean,
    onClick: () -> Unit
) {
    androidx.compose.animation.AnimatedVisibility(
        modifier = modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 32.dp),
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { it / 2 }
        ),
        exit = slideOutVertically(
            targetOffsetY = { it / 2 }
        )
    ) {
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            contentAlignment = Alignment.BottomCenter
        ){
            IconButton(
                modifier = Modifier
                    .background(Color.Black.copy(alpha = if (isScrolling) 0.3f else 1f), CircleShape)
                    .padding(4.dp),
                onClick = onClick
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowUpward,
                    contentDescription = Icons.Default.KeyboardArrowLeft.name,
                    tint = Color.White
                )
            }
        }
    }
}