package com.claudiogalvaodev.camaraaberta.ui.components.timeline.defaults

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.claudiogalvaodev.camaraaberta.ui.components.timeline.models.CircleParameters
import com.claudiogalvaodev.camaraaberta.ui.components.timeline.models.StrokeParameters

object CircleParametersDefaults {

    private val defaultCircleRadius = 12.dp

    fun circleParameters(
        radius: Dp = defaultCircleRadius,
        backgroundColor: Color = Color.Cyan,
        stroke: StrokeParameters? = null,
        @DrawableRes
        icon: Int? = null
    ) = CircleParameters(
        radius,
        backgroundColor,
        stroke,
        icon
    )
}