package com.wagoodman.stackattack.full;


import android.content.SharedPreferences;
import android.os.Handler;

import com.wagoodman.stackattack.StackAttackBase;


public class StackAttack extends StackAttackBase
{

	// LIBRARY VAR HOOKS
	public final Boolean isPaid = true;
	public final int mFreeVersionMaxPoints = -1;

	@Override
	public Boolean getIsPaid() { return isPaid; }

	@Override
	public int getFreeVersionMaxPoints() { return mFreeVersionMaxPoints; }


	Handler mHandler;

	SharedPreferences prefs;

	private static final byte[] SALT = new byte[] {42,84,72,-67,-89,-87,-89,-13,37,47,-92,-13,37,39,30,-55,32,32,-37,92,-43,30,-93,33,-32,-37,-99,-16,19,87,42};

	private void displayResult(final String result) {
		mHandler.post(new Runnable() {
			public void run() {
				setProgressBarIndeterminateVisibility(false);
			}
		});
	}
	
	
	
}