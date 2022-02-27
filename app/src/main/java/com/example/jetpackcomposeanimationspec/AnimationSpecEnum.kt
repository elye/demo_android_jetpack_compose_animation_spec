package com.example.jetpackcomposeanimationspec

import android.animation.TimeInterpolator
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.Interpolator
import androidx.compose.animation.core.*
import com.example.jetpackcomposeanimationspec.math.noise1D
import kotlin.math.sin

private val HesitateEasing = CubicBezierEasing(0f, 1f, 1f, 0f)

enum class AnimationSpecEnum(
    val descriptor: String,
    val animationSpec: AnimationSpec<Float>,
    val lowerBound: Float,
    val upperBound: Float,
    val duration: Int
) {
    TWEEN(
        "Tween 1000 0 LinearEasing",
        FloatTweenSpec(1000, 0, LinearEasing),
        0f, 1f, 1000
    ),
    TWEEN_2(
        "Tween 500 250 LinearEasing",
        FloatTweenSpec(500, 250, LinearEasing),
        0f, 1f, 1000
    ),
    TWEEN_3(
        "Tween 1000 0 LinearOutSlowInEasing",
        FloatTweenSpec(1000, 0, LinearOutSlowInEasing),
        0f, 1f, 1000
    ),
    TWEEN_4(
        "Tween 1000 0 FastOutSlowInEasing",
        FloatTweenSpec(1000, 0, FastOutSlowInEasing),
        0f, 1f, 1000
    ),
    TWEEN_5(
        "Tween 1000 0 FastOutLinearInEasing",
        FloatTweenSpec(1000, 0, FastOutLinearInEasing),
        0f, 1f, 1000
    ),
    TWEEN_6(
        "Tween 1000 0 HesitateEasing",
        FloatTweenSpec(1000, 0, HesitateEasing),
        0f, 1f, 1000
    ),
    TWEEN_7(
        "Tween AnticipateOvershootInterpolator",
        FloatTweenSpec(1000, 0,  AnticipateOvershootInterpolator().toEasing()),
        -0.2f, 1.2f, 1000
    ),
    TWEEN_8(
        "Tween BouncingInterpolator",
        FloatTweenSpec(2000, 0,  BounceInterpolator().toEasing()),
        0f, 1f, 2000
    ),
    TWEEN_9(
        "Tween 50 CircularSpring",
        FloatTweenSpec(7000, 0, CircularSpringInterpolatorEasing()),
        -1f, 2f, 7000
    ),
    TWEEN_10(
        "Tween 200 PerlinNoise",
        FloatTweenSpec(2000, 0, PerlinNoiseInterpolator(200.0).toEasing()),
        0f, 1.2f, 2000
    ),
    SPRING(
        "Spring 0.25 20",
        FloatSpringSpec(0.25f, 20f),
        0f, 2f, 5000
    ),
    SPRING_2(
        "Spring 0.25 40",
        FloatSpringSpec(0.25f, 40f),
        0f, 2f, 5000
    ),
    SPRING_3(
        "Spring 0.5 40",
        FloatSpringSpec(0.5f, 40f),
        0f, 2f, 5000
    ),
    SPRING_4(
        "Spring 1 40",
        FloatSpringSpec(1f, 40f),
        0f, 2f, 5000
    ),
    SPRING_5(
        "Spring 0.1 100",
        FloatSpringSpec(0.1f, 100f),
        0f, 2f, 5000
    ),
    SPRING_6(
        "Spring 0.01 3000",
        FloatSpringSpec(0.01f, 3000f),
        0f, 2f, 10000),
    REPEATABLE_1("Repeat 2 Tween Restart",
        repeatable(2,
            tween<Float>(500, 0, FastOutSlowInEasing),
            RepeatMode.Restart),
        0f, 1f, 1000),
    REPEATABLE_2("Repeat 3 Tween Reverse",
        repeatable(3,
            tween<Float>(500, 0, FastOutSlowInEasing),
            RepeatMode.Reverse),
        0f, 1f, 1500),
    KEYFRAME_1(
        "Keyframe Without Easing",
        keyframes {
            durationMillis = 5000
            0.0f at 0
            -0.4f at 1000
            0.8f at 2000
            -0.6f at 3000
            0.7f at 4000
        },
        -1.2f, 1.2f, 5000
    ),
    KEYFRAME_2(
        "Keyframe With FastOutSlowInEasing",
        keyframes {
            durationMillis = 5000
            0.0f at 0 with FastOutSlowInEasing
            -0.4f at 1000 with FastOutSlowInEasing
            0.8f at 2000 with FastOutSlowInEasing
            -0.6f at 3000 with FastOutSlowInEasing
            0.7f at 4000 with FastOutSlowInEasing
        },
        -1.2f, 1.2f, 5000
    ),
    KEYFRAME_3(
        "Keyframe With HesitateEasing",
        keyframes {
            durationMillis = 5000
            0.0f at 0 with HesitateEasing
            -0.4f at 1000 with HesitateEasing
            0.8f at 2000 with HesitateEasing
            -0.6f at 3000 with HesitateEasing
            0.7f at 4000 with HesitateEasing
        },
        -1.2f, 1.2f, 5000
    ),
    SNAP_1("Snap 500",
        snap(500), 0f, 1f, 1000);

    override fun toString(): String {
        return "$descriptor ($lowerBound, $upperBound, ${duration}ms)"
    }

    companion object {
        fun getEnum(value: String): AnimationSpecEnum {
            return values().first { it.descriptor == value }
        }
    }
}

internal fun CircularSpringInterpolatorEasing(tension: Float = 50f): Easing =
    CircularSpringInterpolator(tension).toEasing()

fun TimeInterpolator.toEasing() = Easing { x -> getInterpolation(x) }

class CircularSpringInterpolator(private val tension: Float = 50f) : Interpolator {
    override fun getInterpolation(input: Float): Float {
        return (sin(tension * input) * sin(Math.PI * input) + input).toFloat()
    }
}

class PerlinNoiseInterpolator(
    private val seed: Double,
    private val cycle: Int = 2,
    private val length: Int = 2,
    private val noiseWeight: Int = 2
) : Interpolator {
    override fun getInterpolation(input: Float): Float {
        val noiseStrength = (if (input < 0.5) input else (1f - input)) * noiseWeight
        return (noise1D((seed + input.toDouble()) * cycle)).toFloat() * length * noiseStrength + input
    }
}

