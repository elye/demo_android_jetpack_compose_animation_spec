package com.example.jetpackcomposeanimationspec

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeanimationspec.ui.theme.JetpackComposeAnimationSpecTheme
import com.example.jetpackcomposeanimationspec.ui.views.OverallPlotter
import kotlin.math.ceil
import kotlin.math.floor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeAnimationSpecTheme {

                val animationSpec = spring<Float>(0.125f, 5f)
//            animationSpec = keyframes {
//                durationMillis = 5000
//                0.0f at 0 with LinearOutSlowInEasing // for 0-15 ms
//                -0.4f at 1500 with FastOutLinearInEasing // for 15-75 ms
//                -0.8f at 3000 // ms
//                0.95f at 4000 // ms
//            }
                Surface(color = MaterialTheme.colors.background) {
                    OverallPlotter(modifier = Modifier.fillMaxSize(), animationSpec)
                }
            }
        }
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