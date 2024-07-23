package com.flux.launcher.util.helper

import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import com.flux.launcher.util.constant.Constants
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.util.Timer
import kotlin.concurrent.schedule
import kotlin.math.abs

open class OnSwipeTouchListener : SimpleOnGestureListener() {

    private var longPressOn = false
    private var doubleTapOn = false
    private val swipeThreshold: Int = 100
    private val swipeVelocityThreshold: Int = 100

    override fun onDown(e: MotionEvent): Boolean {
        return true
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        if (doubleTapOn) {
            doubleTapOn = false
            onTripleClick()
        }
        return super.onSingleTapUp(e)
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        doubleTapOn = true
        Timer().schedule(Constants.TRIPLE_TAP_DELAY_MS.toLong()) {
            if (doubleTapOn) {
                doubleTapOn = false
                onDoubleClick()
            }
        }
        return super.onDoubleTap(e)
    }

    override fun onLongPress(e: MotionEvent) {
        longPressOn = true
        Timer().schedule(Constants.LONG_PRESS_DELAY_MS.toLong()) {
            if (longPressOn) onLongClick()
        }
        super.onLongPress(e)
    }

    override fun onFling(
        event1: MotionEvent?,
        event2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        val diffY = event2.y - event1!!.y
        val diffX = event2.x - event1.x
        if (abs(diffX) > abs(diffY)) {
            if (abs(diffX) > swipeThreshold && abs(velocityX) > swipeVelocityThreshold) {
                if (diffX > 0) onSwipeRight() else onSwipeLeft()
            }
        } else {
            if (abs(diffY) > swipeThreshold && abs(velocityY) > swipeVelocityThreshold) {
                if (diffY < 0) onSwipeUp() else onSwipeDown()
            }
        }
        return false
    }

    open fun onSwipeRight() {}
    open fun onSwipeLeft() {}
    open fun onSwipeUp() {}
    open fun onSwipeDown() {}
    open fun onLongClick() {}
    open fun onDoubleClick() {}
    open fun onTripleClick() {}

}
