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
                        Row(Modifier.fillMaxWidth().height(36.dp)) {
                            Spacer(Modifier.width(16.dp))
                            Text("Select AnimationSpec:")
                            Spacer(Modifier.width(16.dp))
                            DropdownDemo(Modifier.fillMaxSize().padding(0.dp, 0.dp, 16.dp),
                                enumValues<AnimationSpecEnum>().toList(), selectedAnimationSpec)
                        }
                        OverallPlotter(
                            modifier = Modifier.weight(1f).fillMaxWidth(),
                            selectedAnimationSpec.value.animationSpec)
                    }
                }
            }
        }
    }
}

@Composable
fun <T>DropdownDemo(modifier: Modifier, items: List<T>, selected: MutableState<T>) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = modifier.wrapContentSize(Alignment.TopStart)) {
        Text(selected.value.toString(),
            modifier = Modifier.fillMaxWidth().clickable(
                onClick = { expanded = true }).background(
            Color.Gray))
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth().background(
                Color.Red)
        ) {
            items.forEachIndexed { index, select ->
                DropdownMenuItem(onClick = {
                    selected.value = items[index]
                    expanded = false
                }) {
                    Text(text = select.toString())
                }
            }
        }
    }
}

enum class AnimationSpecEnum(
    val descriptor: String,
    val animationSpec: AnimationSpec<Float>,
    val lowerBound: Float,
    val upperBound: Float,
    val duration: Int
    ) {
    SPRING("Spring",
        spring<Float>(0.125f, 5f),
        -0.2f, 1.8f, 20000),
    SPRING_2("Spring 2",
        spring<Float>(0.25f, 5f),
        -0.2f, 1.8f, 20000),
    KEYFRAME_1("Keyframe 1",
             keyframes {
                durationMillis = 5000
                0.0f at 0 with LinearOutSlowInEasing // for 0-15 ms
                -0.4f at 1500 with FastOutLinearInEasing // for 15-75 ms
                -0.8f at 3000 // ms
                0.95f at 4000 // ms
            },
        -0.2f, 1.8f, 20000);

    override fun toString(): String {
        return descriptor
    }

    companion object {
        fun getEnum(value: String): AnimationSpecEnum {
            return values().first { it.descriptor == value }
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