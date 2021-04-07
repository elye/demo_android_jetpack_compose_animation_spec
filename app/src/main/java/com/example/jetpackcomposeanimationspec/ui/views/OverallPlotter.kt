package com.example.jetpackcomposeanimationspec.ui.views

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun OverallPlotter(modifier: Modifier = Modifier, animationSpec: AnimationSpec<Float>) {

    val animatableFloat = remember { Animatable(0f) }
    val trackAnimatableFloat = remember { Animatable(0f) }

    LaunchedEffect(animatableFloat) {
        animatableFloat.animateTo(
            1f,
            animationSpec = tween(durationMillis = 20000, easing = LinearEasing)
        )
    }

    val upperBound = 2f
    val lowerBound = -1f

    val lowerBoundCeil = ceil(lowerBound).toInt()
    val upperBoundFloor = floor(upperBound).toInt()

    LaunchedEffect(trackAnimatableFloat) {
        trackAnimatableFloat.animateTo(
            1f,
            animationSpec = animationSpec
        )
    }

    Row(modifier.padding(16.dp)) {
        val penColor = MaterialTheme.colors.onBackground
        val boxPadding = 16.dp
        Box(
            modifier = Modifier
                .padding(0.dp, boxPadding, 0.dp, boxPadding)
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
                .padding(0.dp, boxPadding, 0.dp, boxPadding)
                .width(30.dp)
                .fillMaxHeight()
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
        Box(
            Modifier
                .padding(0.dp, boxPadding, 0.dp, boxPadding)
                .weight(4f)
                .fillMaxHeight()
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
            PlotterView(
                Modifier.fillMaxSize(),
                xPoint = animatableFloat.value,
                yPoint = trackAnimatableFloat.value,
                upperBound = upperBound,
                lowerBound = lowerBound
            )
        }
    }
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
fun PlotterView(
    modifier: Modifier = Modifier,
    xPoint: Float, yPoint: Float,
    upperBound: Float, lowerBound: Float
) {
    val path by remember { mutableStateOf(Path()) }
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
