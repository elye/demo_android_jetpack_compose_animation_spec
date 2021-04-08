package com.example.jetpackcomposeanimationspec

import androidx.compose.animation.core.*

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
        tween(1000, 0, LinearEasing),
        0f, 1f, 1000
    ),
    TWEEN_2(
        "Tween 500 250 LinearEasing",
        tween(500, 250, LinearEasing),
        0f, 1f, 1000
    ),
    TWEEN_3(
        "Tween 1000 0 LinearOutSlowInEasing",
        tween(1000, 0, LinearOutSlowInEasing),
        0f, 1f, 1000
    ),
    TWEEN_4(
        "Tween 1000 0 FastOutSlowInEasing",
        tween(1000, 0, FastOutSlowInEasing),
        0f, 1f, 1000
    ),
    TWEEN_5(
        "Tween 1000 0 FastOutLinearInEasing",
        tween(1000, 0, FastOutLinearInEasing),
        0f, 1f, 1000
    ),
    TWEEN_6(
        "Tween 1000 0 HesitateEasing",
        tween(1000, 0, HesitateEasing),
        0f, 1f, 1000
    ),
    SPRING(
        "Spring 0.25 20",
        spring<Float>(0.25f, 20f),
        0f, 2f, 5000
    ),
    SPRING_2(
        "Spring 0.25 40",
        spring<Float>(0.25f, 40f),
        0f, 2f, 5000
    ),
    SPRING_3(
        "Spring 0.5 40",
        spring<Float>(0.5f, 40f),
        0f, 2f, 5000
    ),
    SPRING_4(
        "Spring 1 40",
        spring<Float>(1f, 40f),
        0f, 2f, 5000
    ),
    SPRING_5(
        "Spring 0.1 100",
        spring<Float>(0.1f, 100f),
        0f, 2f, 5000
    ),
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
    SNAP_1("Snap 500", snap(500), 0f, 1f, 1000);


    override fun toString(): String {
        return "$descriptor ($lowerBound, $upperBound, ${duration}ms)"
    }

    companion object {
        fun getEnum(value: String): AnimationSpecEnum {
            return values().first { it.descriptor == value }
        }
    }
}
