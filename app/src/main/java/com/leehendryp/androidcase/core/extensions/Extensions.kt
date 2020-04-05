package com.leehendryp.androidcase.core.extensions

import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.SeekBar
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

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

fun SeekBar.doOnProgressChanged(block: () -> Unit) {
    setOnSeekBarChangeListener(
        object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                block()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        }
    )
}

fun AutocompleteSupportFragment.setOnPlaceSelectedListener(
    onPlaceSelected: (Place) -> Unit,
    onError: (Status) -> Unit
) {
    setOnPlaceSelectedListener(
        object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) = onPlaceSelected(place)
            override fun onError(status: Status) = onError(status)
        }
    )
}