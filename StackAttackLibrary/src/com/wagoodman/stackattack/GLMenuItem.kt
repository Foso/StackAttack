package com.wagoodman.stackattack

import javax.microedition.khronos.opengles.GL10

abstract class GLMenuItem {
    // Position functionality
// in pixels
	@JvmField
	var mWidth = 100
    @JvmField
	var mHeight = 35
    @JvmField
	var mLeftPos = 0
    @JvmField
	var mRightPos = 0
    @JvmField
	var mTopPos = 0
    @JvmField
	var mBottomPos = 0
    @JvmField
	var xPos = 0
    @JvmField
	var yPos = 0
    @JvmField
	protected var mScreenHeight = 0
    protected var mLeftJust = true
    // Clickable
    fun setPos(xSide: Int, bottom: Int, leftJust: Boolean) {
        xPos = xSide // left or right
        yPos = bottom
        mLeftJust = leftJust
    }

    fun setDimensions(width: Int, height: Int, screenheight: Int) {
        mWidth = width
        mHeight = height
        mScreenHeight = screenheight
        // set comparisons
        if (mLeftJust) { // left just
            mLeftPos = xPos
            mRightPos = xPos + width
        } else { // right just
            mLeftPos = xPos - width
            mRightPos = xPos
        }
        mTopPos = yPos + height
        mBottomPos = yPos
    }

    fun isWithinClickableArea(xPix: Int, yPix: Int, pixYOffset: Int): Boolean {
        return if (xPix >= mLeftPos && xPix <= mRightPos && mScreenHeight - yPix <= mTopPos + (mScreenHeight - pixYOffset) && mScreenHeight - yPix >= mBottomPos + (mScreenHeight - pixYOffset)) true else false
    }

    abstract fun triggerIntro(delay: Int)
    abstract fun triggerOutro(delay: Int)
    abstract fun intro()
    abstract fun intro(dur: Int?)
    abstract fun outro()
    abstract fun outro(dur: Int?)
    abstract fun setFontDimensions()
    abstract fun setLabelDimensions()
    //abstract void hide();
    abstract val outroDuration: Int

    abstract fun interact(x: Int, y: Int, pixYOffset: Int): Boolean?
    abstract fun update(now: Long, primaryThread: Boolean?, secondaryThread: Boolean?)
    abstract fun draw(gl: GL10?, pixYOffset: Float)
}