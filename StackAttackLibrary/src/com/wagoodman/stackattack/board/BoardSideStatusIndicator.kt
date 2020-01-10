package com.wagoodman.stackattack.board

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLUtils
import com.wagoodman.stackattack.*
import javax.microedition.khronos.opengles.GL10
import javax.microedition.khronos.opengles.GL11
import javax.microedition.khronos.opengles.GL11Ext

class BoardSideStatusIndicator internal constructor(context: Context, left: Boolean) : GLMenuItem() {
    private val mContext: Context
    private val game: MainActivity
    var mColor: ColorBroker
    private val isLeft: Boolean
    private var mLastEventRow = 0
    private var mCurrentRow = 0f
    var IMMINENT = Color.RED
    var WARNING = Color.YELLOW
    var NEUTRAL = Color.GREEN
    private var isTransforming = false
    private var mStartTime: Long = 0
    private val mDuration = 500
    private var mStartPoint = 0f
    private var mEndPoint = 0f
    private var texID = 0
    private val level = 0
    private val UVarray = IntArray(4)
    private var cropHeight = 0
    private var scaledWidth = 0
    private var scaledHeight = 0
    private var scaledCropHeight = 0
    private val scaledCropHeightOffset = 0
    private var totalPixHeight = 200f
    fun loadImage(gl: GL10) { // select a bitmap based on dpi
        val bitmap = BitmapFactory.decodeResource(mContext.resources, R.drawable.white)
        setDimensions(8, 8, game.world.mScreenHeight.toInt())
        // fix for the extra notification bar space (when taking away the bar the usable space does not increase as it should... so add it manually.
//Rect rectgle= new Rect();
//Window window= game.getWindow();
//window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
//int statusBarHeight = rectgle.top;
// get crop & scale dim
/*
		scaledWidth = (int) game.getWorld().mScreenWidth/CA_Game.COLCOUNT;
		double scaleRatio =  scaledWidth / (double) mWidth  ;
		scaledHeight = (int) ( scaleRatio * mHeight );
		scaledCropHeightOffset = (int) (game.getWorld().mScreenHeight - scaledHeight) + statusBarHeight;
		scaledCropHeight = (int) (scaledHeight - Math.abs(scaledCropHeightOffset));
		cropHeight = (int) (scaledCropHeight/scaleRatio);
		*/
// TEMP TEMP TEMP
/*
		scaledWidth = mWidth;
		scaledHeight = mHeight;
		cropHeight = mHeight;
		scaledCropHeight = mHeight;
		*/scaledWidth = (game.world.mScreenWidth / MainActivity.COLCOUNT.toFloat() / 7f).toInt()
        scaledHeight = mHeight
        cropHeight = mHeight
        scaledCropHeight = 0 //(int)(game.getWorld().mScreenHeight*0.75);
        totalPixHeight = (game.world.mScreenHeight - game.world.mScreenHeight * DropSectionState.FOLDED.height / 2).toFloat()
        // set position based off of scaled info
        if (isLeft) setPos(0, 0, true) else setPos((game.world.mScreenWidth - scaledWidth).toInt(), 0, true)
        /*
		String extensions = gl.glGetString(GL10.GL_EXTENSIONS);
		boolean drawTexture = extensions.contains("draw_texture");
		game.text = "OpenGL Support - ver.:" + gl.glGetString(GL10.GL_VERSION) + "renderer:" + gl.glGetString(GL10.GL_RENDERER) + " : " + (drawTexture ? "good to go!" : "forget it!!");
		game.textviewHandler.post(game.updateTextView);
		*/
/*
		game.text =  "Picture       : " + mWidth + " x " + mHeight + "\n";
		game.text += "Screen        : " + game.getWorld().mScreenWidth + " x " + game.getWorld().mScreenHeight + "\n";
		game.text += "Scaled        : " + scaledWidth + " x " + scaledHeight + "\n";
		game.text += "ScaledCropOff : " + scaledCropHeightOffset + "\n";
		game.text += "Scaled Crop Ht: " + scaledCropHeight + "\n";
		game.text += "Crop Ht       : " + cropHeight + "\n";
		game.textviewHandler.post(game.updateTextView);
		*/
/*
		cropHeightOffset = (int) (game.getWorld().mScreenHeight - mHeight) + statusBarHeight;
		cropHeight = mHeight - Math.abs(cropHeightOffset);
		setPos((int) (game.getWorld().mScreenWidth/2 - mWidth/2), 0, true);
		*/
/*
		game.text  = "Height         : " + mHeight + "\n";
		game.text += "Crop Height    : " + cropHeight + "\n";
		game.text += "Crop Height Off: " + cropHeightOffset + "\n";
		game.textviewHandler.post( game.updateTextView );
		*/
//gl.glEnable(GL10.GL_BLEND);
//gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        val texid = IntArray(1)
        gl.glGenTextures(texid.size, texid, 0)
        texID = texid[0]
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texID)
        // Set texture parameters
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR.toFloat())
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR.toFloat())
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE.toFloat())
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE.toFloat())
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0)
        //gl.glDisable(GL10.GL_BLEND);
        bitmap.recycle()
    }

    @Synchronized
    fun updateRow(newRow: Int) {
        if (newRow != mLastEventRow) { // set transform
            mLastEventRow = newRow
            start(mLastEventRow.toFloat())
            var nextColor = Color.NONE
            // set color
            nextColor = if (mLastEventRow >= MainActivity.ROWCOUNT * 0.7) { // Imminent
                IMMINENT
            } else if (mLastEventRow >= MainActivity.ROWCOUNT * 0.5 && mLastEventRow < MainActivity.ROWCOUNT * 0.7) { // warning
                WARNING
            } else { // neutral
                NEUTRAL
            }
            // change color
            if (mColor.mCurrentColor != nextColor) {
                mColor.enqueue(
                        ColorState(arrayOf(MotionEquation.LINEAR, MotionEquation.LINEAR, MotionEquation.LINEAR, MotionEquation.LINEAR),
                                mColor.mCurrentColor,
                                nextColor, intArrayOf(mDuration, mDuration, mDuration, mDuration), intArrayOf(0, 0, 0, 0)))
            }
        }
    }

    private fun start(endRow: Float) { // stop any previous transforms
        stop()
        mStartPoint = mCurrentRow //where i am now
        mEndPoint = endRow // trigger point
        mStartTime = System.currentTimeMillis()
        isTransforming = true
    }

    private fun stop() {
        isTransforming = false
    }

    override fun update(now: Long, primaryThread: Boolean?, secondaryThread: Boolean?) {
        if (primaryThread!!) { // Process color
            if (!mColor.isEmpty) mColor.processColorElements(now)
            if (isTransforming) {
                mCurrentRow = MotionEquation.applyFinite(
                        TransformType.TRANSLATE,
                        MotionEquation.LOGISTIC,
                        Math.max(0, Math.min(now - mStartTime, mDuration.toLong())),
                        mDuration.toDouble(),
                        mStartPoint.toDouble(),
                        mEndPoint
                                .toDouble()).toFloat()
                if (Math.max(0, Math.min(now - mStartTime, mDuration.toLong())) == mDuration.toLong()) {
                    stop()
                    // prevent settling in the wrong place
                    mCurrentRow = mEndPoint
                }
            }
            scaledCropHeight = (totalPixHeight * (mCurrentRow / (MainActivity.ROWCOUNT - 1))).toInt()
        } else if (secondaryThread!!) { //queueing
        }
    }

    fun draw(gl: GL10) { // rely on z-ordering
        gl.glDisable(GL10.GL_DEPTH_TEST)
        // GL11ETX wont work with lighting on some devices!
// Disable lighting
        gl.glDisable(GL10.GL_LIGHT0)
        gl.glDisable(GL10.GL_LIGHTING)
        gl.glEnable(GL10.GL_BLEND)
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA)
        gl.glColor4f(
                mColor.mCurrentColorAmbient[0],
                mColor.mCurrentColorAmbient[1],
                mColor.mCurrentColorAmbient[2],
                mColor.mCurrentColorAmbient[3]
        )
        // Set up GL for rendering the text
        gl.glEnable(GL10.GL_TEXTURE_2D)
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texID)
        // Update the crop rect
        UVarray[0] = 0
        UVarray[1] = cropHeight
        UVarray[2] = mWidth
        UVarray[3] = -cropHeight
        // bottom half
/*
		UVarray[0] = 0;
		UVarray[1] =  mHeight;
		UVarray[2] = mWidth;
		UVarray[3] = -(int)(mHeight*0.5);
		*/
/*
		// top half
		UVarray[0] = 0;
		UVarray[1] =  (int)(mHeight*0.5);
		UVarray[2] = mWidth;
		UVarray[3] = -(int)(mHeight*0.5);
		*/
// no bluring texture on scale
//((GL11) gl).glTexParameteri(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
// Set crop area
        (gl as GL11).glTexParameteriv(GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES, UVarray, 0)
        // Draw texture (scaled)
        (gl as GL11Ext).glDrawTexfOES(
                xPos.toFloat(),  // x
                yPos.toFloat(),  // y
                World.GLEX11HUD_Z_ORDER,
                scaledWidth.toFloat(),
                scaledCropHeight.toFloat())
        /*
		gl.glScalef(
				3,
				3,
				3
						);

		game.getWorld().mBlock.draw(gl);
		*/gl.glDisable(GL10.GL_TEXTURE_2D)
        gl.glDisable(GL10.GL_BLEND)
        // Enable Lighting!
        gl.glEnable(GL10.GL_LIGHT0)
        gl.glEnable(GL10.GL_LIGHTING)
        // re enable
        gl.glDisable(GL10.GL_DEPTH_TEST)
        //game.text = mCurrentRow + " of " + (CA_Game.ROWCOUNT-1) + "\n";
//game.text += (mCurrentRow/(CA_Game.ROWCOUNT-1)) + "\n";
//game.textviewHandler.post( game.updateTextView );
    }

    override fun triggerIntro(delay: Int) { // TODO Auto-generated method stub
    }

    override fun triggerOutro(delay: Int) { // TODO Auto-generated method stub
    }

    override fun intro() { // TODO Auto-generated method stub
    }

    override fun intro(dur: Int?) { // TODO Auto-generated method stub
    }

    override fun outro() { // TODO Auto-generated method stub
    }

    override fun outro(dur: Int?) { // TODO Auto-generated method stub
    }

    override fun setFontDimensions() { // TODO Auto-generated method stub
    }

    override fun setLabelDimensions() { // TODO Auto-generated method stub
    }

    // TODO Auto-generated method stub
    override val outroDuration: Int
        get() =// TODO Auto-generated method stub
            0

    override fun interact(x: Int, y: Int, pixYOffset: Int): Boolean? { // TODO Auto-generated method stub
        return null
    }

    override fun draw(gl: GL10?, pixYOffset: Float) { // TODO Auto-generated method stub
    }

    init { // get the game object from context
        game = context as MainActivity
        mContext = context
        mColor = ColorBroker(NEUTRAL)
        isLeft = left
    }
}