package com.example.jetpackcomposeanimationspec.ui.views

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeanimationspec.AnimationSpecEnum
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun OverallPlotter(modifier: Modifier = Modifier, animationSpecEnum: AnimationSpecEnum) {

    val animatableFloat = remember(animationSpecEnum) { Animatable(0f) }
    val trackAnimatableFloat = remember(animationSpecEnum) { Animatable(0f) }

    LaunchedEffect(animatableFloat) {
        animatableFloat.animateTo(
            1f,
            animationSpec = tween(durationMillis = animationSpecEnum.duration, easing = LinearEasing)
        )
    }

    val upperBound = animationSpecEnum.upperBound
    val lowerBound = animationSpecEnum.lowerBound

    val lowerBoundCeil = ceil(lowerBound).toInt()
    val upperBoundFloor = floor(upperBound).toInt()

    LaunchedEffect(trackAnimatableFloat) {
        trackAnimatableFloat.animateTo(
            1f,
            animationSpec = animationSpecEnum.animationSpec
        )
    }

    Row(modifier.padding(16.dp)) {
        val penColor = MaterialTheme.colors.onBackground
        val boxPadding = 24.dp
        Box(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, boxPadding)
                .width(30.dp)
                .fillMaxHeight()
        ) {
            BallAnimator(
                Modifier.fillMaxSize(),
                yPoint = trackAnimatableFloat.value,
                upperBound = upperBound,
                lowerBound = lowerBound
            )
        }
        Box(
            Modifier
                .padding(0.dp, 0.dp, 0.dp, boxPadding)
                .width(30.dp)
                .fillMaxHeight()
        ) {
            Yaxis(lowerBoundCeil, upperBoundFloor, lowerBound, upperBound)
        }
        Column(
            Modifier
                .padding(0.dp, 0.dp, 0.dp, 0.dp)
                .weight(4f)
                .fillMaxHeight()) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                PlotterView(
                    penColor,
                    lowerBoundCeil,
                    upperBoundFloor,
                    lowerBound,
                    upperBound,
                    animatableFloat,
                    trackAnimatableFloat,
                    animationSpecEnum
                )
            }
            Xaxis(boxPadding, animationSpecEnum)
        }
    }
}

@Composable
private fun Yaxis(
    lowerBoundCeil: Int,
    upperBoundFloor: Int,
    lowerBound: Float,
    upperBound: Float
) {
    Canvas(
        Modifier
            .fillMaxSize()
    ) {
        val textSize = 16.sp.toPx()
        val textPaint = Paint().asFrameworkPaint().apply {
            this.textSize = textSize
        }
        drawIntoCanvas { canvas ->
            (lowerBoundCeil..upperBoundFloor).forEach {
                canvas.nativeCanvas.drawText(
                    it.toString(),
                    15.dp.toPx(),
                    size.height + textSize / 2
                            - ((size.height))
                            * (it - lowerBound) / (upperBound - lowerBound),
                    textPaint
                )
            }
        }
    }
}

@Composable
private fun Xaxis(
    boxPadding: Dp,
    animationSpecEnum: AnimationSpecEnum
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(boxPadding)) {
        Text("${animationSpecEnum.duration} ms", Modifier.fillMaxWidth(), textAlign = TextAlign.End)
    }
}

@Composable
private fun PlotterView(
    penColor: Color,
    lowerBoundCeil: Int,
    upperBoundFloor: Int,
    lowerBound: Float,
    upperBound: Float,
    animatableFloat: Animatable<Float, AnimationVector1D>,
    trackAnimatableFloat: Animatable<Float, AnimationVector1D>,
    animationSpecEnum: AnimationSpecEnum
) {
    Canvas(Modifier.fillMaxSize()) {
        drawRect(penColor, size = size, style = Stroke(1.dp.toPx()))
        (lowerBoundCeil..upperBoundFloor).forEach {
            val yAxis = size.height -
                    ((size.height)) * (it - lowerBound) / (upperBound - lowerBound)
            drawLine(
                penColor,
                Offset(0f, yAxis),
                Offset(size.width, yAxis),
                1.dp.toPx()
            )
        }
    }
    RawPlotterView(
        Modifier.fillMaxSize(),
        xPoint = animatableFloat.value,
        yPoint = trackAnimatableFloat.value,
        upperBound = upperBound,
        lowerBound = lowerBound,
        animationSpecEnum = animationSpecEnum
    )
}

@Composable
fun BallAnimator(
    modifier: Modifier = Modifier,
    yPoint: Float,
    upperBound: Float, lowerBound: Float
) {
    val penColor = Color.Red

    Canvas(modifier) {
        val yAxis = size.height * (1 - (yPoint - lowerBound) / (upperBound - lowerBound))
        val radius = 8.dp.toPx()
        drawCircle(penColor, radius, Offset(15.dp.toPx(), yAxis))
    }
}

@Composable
fun RawPlotterView(
    modifier: Modifier = Modifier,
    xPoint: Float, yPoint: Float,
    upperBound: Float, lowerBound: Float,
    animationSpecEnum: AnimationSpecEnum
) {
    val path by remember(animationSpecEnum) { mutableStateOf(Path()) }
    val penColor = Color.Red

    Canvas(modifier) {
        val yAxis = size.height * (1 - (yPoint - lowerBound) / (upperBound - lowerBound))
        if (path.isEmpty) {
            path.moveTo(0f, yAxis)
        }
        path.lineTo(size.width * xPoint, yAxis)
        drawPath(
            path,
            color = penColor,
            alpha = 1f,
            style = Stroke(2.dp.toPx())
        )
    }
}
