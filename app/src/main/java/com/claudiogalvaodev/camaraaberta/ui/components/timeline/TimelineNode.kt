package com.claudiogalvaodev.camaraaberta.ui.components.timeline

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.claudiogalvaodev.camaraaberta.ui.components.AgendaCard
import com.claudiogalvaodev.camaraaberta.ui.components.timeline.defaults.CircleParametersDefaults
import com.claudiogalvaodev.camaraaberta.ui.components.timeline.defaults.LineParametersDefaults
import com.claudiogalvaodev.camaraaberta.ui.components.timeline.models.StrokeParameters
import com.claudiogalvaodev.camaraaberta.ui.theme.CamaraAbertaTheme

@Composable
fun TimelineNode(
    modifier: Modifier,
    isActive: Boolean,
    contentStartOffset: Dp = 12.dp,
    spacer: Dp = 12.dp,
    isLast: Boolean,
    content: @Composable BoxScope.(modifier: Modifier) -> Unit
) {
    val lineParameters = remember {
        LineParametersDefaults.linearGradient(
            startColor = Color(0xFF22A87E).copy(alpha = if (isActive) 1f else 0.3f),
            endColor = Color(0xFF22A87E).copy(alpha = if (isActive) 1f else 0.3f)
        )
    }
    val circleParameters = remember {
        CircleParametersDefaults.circleParameters(
            radius = 8.dp,
            backgroundColor = Color(0xFF22A87E).copy(alpha = if (isActive) 1f else 0.3f),
            stroke = StrokeParameters(
                color = Color(0xFF22A87E).copy(alpha = if (isActive) 1f else 0.3f),
                width = 2.dp
            )
        )
    }
    val iconPainter = circleParameters.icon?.let { painterResource(id = it) }
    Box(
        modifier = modifier
            .wrapContentSize()
            .drawBehind {
                val circleRadiusInPx = circleParameters.radius.toPx()

                if (isLast.not()) {
                    drawLine(
                        brush = lineParameters.brush,
                        start = Offset(x = circleRadiusInPx, y = circleRadiusInPx * 2),
                        end = Offset(x = circleRadiusInPx, y = this.size.height),
                        strokeWidth = lineParameters.strokeWidth.toPx()
                    )
                }

                drawCircle(
                    circleParameters.backgroundColor,
                    circleRadiusInPx,
                    center = Offset(x = circleRadiusInPx, y = circleRadiusInPx)
                )

                iconPainter?.let { painter ->
                    this.withTransform(
                        transformBlock = {
                            translate(
                                left = circleRadiusInPx - painter.intrinsicSize.width / 2f,
                                top = circleRadiusInPx - painter.intrinsicSize.height / 2f
                            )
                        },
                        drawBlock = {
                            this.drawIntoCanvas {
                                with(painter) {
                                    draw(intrinsicSize)
                                }
                            }
                        })
                }
            }
    ) {
        content(
            Modifier
                .defaultMinSize(minHeight = circleParameters.radius * 2)
                .padding(
                    start = circleParameters.radius * 2 + contentStartOffset,
                    bottom = if (isLast) 0.dp else spacer
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TimelinePreview() {
    CamaraAbertaTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TimelineNode(
                modifier = Modifier,
                isLast = false,
                isActive = false
            ) { modifier ->
                AgendaCard(modifier = modifier,
                    timeStart = "9:00",
                    timeEnd = "10:00",
                    title = "Teste",
                    type = "Teste",
                    local = "Anexo II, Plenário 06",
                    onClick = {}
                )
            }

            TimelineNode(
                modifier = Modifier,
                isLast = false,
                isActive = true
            ) { modifier ->
                AgendaCard(modifier = modifier,
                    timeStart = "9:00",
                    timeEnd = "10:00",
                    title = "Teste",
                    type = "Teste",
                    local = "Anexo II, Plenário 06",
                    onClick = {}
                )
            }

            TimelineNode(
                modifier = Modifier,
                isLast = false,
                isActive = true
            ) { modifier ->
                AgendaCard(modifier = modifier,
                    timeStart = "9:00",
                    timeEnd = "10:00",
                    title = "Teste",
                    type = "Teste",
                    local = "Anexo II, Plenário 06",
                    onClick = {}
                )
            }

            TimelineNode(
                modifier = Modifier,
                isLast = true,
                isActive = true
            ) { modifier ->
                AgendaCard(modifier = modifier,
                    timeStart = "9:00",
                    timeEnd = "10:00",
                    title = "Teste",
                    type = "Teste",
                    local = "Anexo II, Plenário 06",
                    onClick = {}
                )
            }
        }
    }
}