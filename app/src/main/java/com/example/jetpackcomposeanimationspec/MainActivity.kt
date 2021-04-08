package com.example.jetpackcomposeanimationspec

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposeanimationspec.ui.theme.JetpackComposeAnimationSpecTheme
import com.example.jetpackcomposeanimationspec.ui.views.OverallPlotter
import com.example.jetpackcomposeanimationspec.ui.views.OverallSelector

class MainActivity : ComponentActivity() {

    private val selectedAnimationSpec = mutableStateOf(AnimationSpecEnum.SPRING)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeAnimationSpecTheme {
                val animationSpec = spring<Float>(0.125f, 5f)

                Surface(color = MaterialTheme.colors.background) {
                    Column {
                        Spacer(Modifier.height(16.dp))
                        OverallSelector(selectedAnimationSpec)
                        OverallPlotter(
                            modifier = Modifier.weight(1f).fillMaxWidth(),
                            selectedAnimationSpec.value)
                    }
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