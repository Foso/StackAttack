package com.wagoodman.stackattack

import javax.microedition.khronos.opengles.GL10

interface PxBase {
    fun loadImage(gl: GL10?)
    //public abstract Boolean isWithinClickableArea(float xPix, float yPix, int pixYOffset);
    fun pickup(x: Float, y: Float, pixYOffset: Int, digitCount: Int, force: Boolean?): Boolean?

    fun pickup(x: Float, y: Float, pixYOffset: Int, digitCount: Int): Boolean?
    fun interact(x: Float, y: Float, pixYOffset: Int, digitCount: Int): Boolean?
    fun drop(digitCount: Int)
    fun drop(digitCount: Int, toGridResolution: Boolean?)
    fun fadeIn(eq: MotionEquation?, dur: Int?, delay: Int?, full: Boolean?)
    fun fadeOut(eq: MotionEquation?, dur: Int?, delay: Int?, full: Boolean?)
    fun moveTo(pXend: Float?, pYend: Float?, eq: MotionEquation?, dur: Int?, delay: Int?)
    fun update(now: Long, primaryThread: Boolean?, secondaryThread: Boolean?)
    fun draw(gl: GL10?, pixYOffset: Float)
}