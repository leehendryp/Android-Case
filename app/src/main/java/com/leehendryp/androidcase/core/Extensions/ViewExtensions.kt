package com.leehendryp.androidcase.core.Extensions

import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE

fun View.visible() {
    visibility = VISIBLE
}

fun View.invisible() {
    visibility = INVISIBLE
}

fun View.gone() {
    visibility = GONE
}

fun View.transparent(duration: Long = 300) {
    animate()
        .alpha(0.33f)
        .setDuration(duration)
        .start()
}

fun View.mat(duration: Long = 300) {
    animate()
        .alpha(1f)
        .setDuration(duration)
        .start()
}

fun View.vanish(duration: Long = 300) {
    animate()
        .alpha(0f)
        .setDuration(duration)
        .withEndAction { gone() }
        .start()
}

fun View.fadeOut(duration: Long = 300) {
    animate()
        .alpha(0f)
        .setDuration(duration)
        .withEndAction { invisible() }
        .start()
}

fun View.fadeIn(duration: Long = 300) {
    animate()
        .alpha(1f)
        .setDuration(duration)
        .withStartAction { visible() }
        .start()
}