package com.wagoodman.stackattack

import com.wagoodman.stackattack.World.BLOCKTYPES
import com.wagoodman.stackattack.block.BlockValue
import javax.microedition.khronos.opengles.GL10

internal abstract class GLShape : Identity() {
    // Specific Delegate Members 
	@JvmField
	var mAnimation: AnimationBroker? = null
    @JvmField
	var mColor: ColorBroker? = null
    //public GLRect			mShape;	// not here! high memory cost
// ...Tangibility
	@JvmField
	var isInteractable = true // User cannot manipulate it
    @JvmField
	var isFrozen = false // User and settle cannot manipulate it
    @JvmField
	var isVisible = true // not visible until first animation: false
    @JvmField
	var isSeeThru = false
    // used to trigger deletion / settling
	@JvmField
	var isDead = false
    @JvmField
	var isMatching = false
    // Block dep items
    @JvmField
	protected var mBlockType: BLOCKTYPES? = null
    @JvmField
	var mBlockValue = BlockValue.NORMAL
    //abstract public void update(long now);
    abstract fun update(now: Long, primaryThread: Boolean?, secondaryThread: Boolean?)

    abstract fun draw(gl: GL10?, offset: Array<FloatArray?>?)
    abstract fun draw(gl: GL10?)
}