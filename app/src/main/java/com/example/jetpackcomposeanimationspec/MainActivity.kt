package com.example.jetpackcomposeanimationspec

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeanimationspec.ui.theme.JetpackComposeAnimationSpecTheme
import kotlin.math.ceil
import kotlin.math.floor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeAnimationSpecTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    OverallPlotter(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun OverallPlotter(modifier: Modifier = Modifier) {

    val animatableFloat = remember { Animatable(0f) }
    val trackAnimatableFloat = remember { Animatable(0f) }

    LaunchedEffect(animatableFloat) {
        animatableFloat.animateTo(
            1f,
            animationSpec = tween(durationMillis = 20000, easing = LinearEasing)
        )
    }

    val upperBound = 1.8f
    val lowerBound = -1.5f

    val lowerBoundCeil = ceil(lowerBound).toInt()
    val upperBoundFloor = floor(upperBound).toInt()

    LaunchedEffect(trackAnimatableFloat) {
        trackAnimatableFloat.animateTo(
            1f,
            animationSpec = spring(0.125f, 5f)
//            animationSpec = keyframes {
//                durationMillis = 5000
//                0.0f at 0 with LinearOutSlowInEasing // for 0-15 ms
//                0.4f at 1500 with FastOutLinearInEasing // for 15-75 ms
//                0.8f at 3000 // ms
//                0.95f at 4000 // ms
//            }
        )
    }

    Canvas(modifier = Modifier.size(16.dp)) {

    }
    Row (modifier.padding(16.dp)) {
        val penColor = MaterialTheme.colors.onBackground
        val boxPadding = 16.dp
        Canvas(
            Modifier
                .width(30.dp)
                .fillMaxHeight()) {
            val textSize = 16.sp.toPx()
            val textPaint = Paint().asFrameworkPaint().apply {
                this.textSize = textSize
            }

            drawIntoCanvas { canvas ->
                (lowerBoundCeil..upperBoundFloor).forEach {
                    canvas.nativeCanvas.drawText(
                        it.toString(),
                        15.dp.toPx(),
                        size.height - boxPadding.toPx() + textSize/2
                                - ((size.height - boxPadding.toPx() * 2))
                                * (it - lowerBound) / (upperBound - lowerBound),
                        textPaint
                    )
                }
            }
        }
        Box(
            Modifier
                .padding(0.dp, boxPadding, 0.dp, boxPadding)
                .weight(4f)
                .fillMaxHeight()) {
            Canvas(Modifier.fillMaxSize()) {
                drawRect(penColor, size = size, style = Stroke(1.dp.toPx()))
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
fun PlotterView(modifier: Modifier = Modifier,
                xPoint: Float, yPoint: Float,
                upperBound: Float, lowerBound: Float) {
    val path by remember { mutableStateOf(Path()) }
    val penColor = MaterialTheme.colors.onBackground

    Canvas(modifier) {
        if (path.isEmpty) {
            path.moveTo(0f, size.height)
        }
        path.lineTo(
            size.width * xPoint,
            size.height * (1 - (yPoint - lowerBound) / (upperBound - lowerBound))
        )
        drawPath(
            path,
            color = penColor,
            alpha = 1f,
            style = Stroke(2.dp.toPx())
        )
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackComposeAnimationSpecTheme {
        Greeting("Android")
    }
}