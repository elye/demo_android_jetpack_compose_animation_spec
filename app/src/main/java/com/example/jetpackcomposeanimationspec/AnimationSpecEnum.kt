package com.example.jetpackcomposeanimationspec

import androidx.compose.animation.core.*

enum class AnimationSpecEnum(
    val descriptor: String,
    val animationSpec: AnimationSpec<Float>,
    val lowerBound: Float,
    val upperBound: Float,
    val duration: Int
) {
    SPRING("Spring 0.125 0.5",
        spring<Float>(0.125f, 5f),
        -0.2f, 1.8f, 20000),
    SPRING_2("Spring 0.25 0.5",
        spring<Float>(0.25f, 5f),
        -0.2f, 1.8f, 20000),
    KEYFRAME_1("Keyframe Variant",
        keyframes {
            durationMillis = 5000
            0.0f at 0 with LinearOutSlowInEasing // for 0-15 ms
            -0.4f at 1500 with FastOutLinearInEasing // for 15-75 ms
            -0.8f at 3000 // ms
            0.95f at 4000 // ms
        },
        -1.2f, 1.2f, 5000);

    override fun toString(): String {
        return "$descriptor ($lowerBound, $upperBound, ${duration}ms)"
    }

    companion object {
        fun getEnum(value: String): AnimationSpecEnum {
            return values().first { it.descriptor == value }
        }
    }
}
