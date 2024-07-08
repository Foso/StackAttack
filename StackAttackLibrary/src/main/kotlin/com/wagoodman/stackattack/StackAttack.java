package com.wagoodman.stackattack;


public class StackAttack extends StackAttackBase
{

	// LIBRARY VAR HOOKS
	public final Boolean isPaid = true;
	public final int mFreeVersionMaxPoints = -1;

	@Override
	public Boolean getIsPaid() { return isPaid; }

	@Override
	public int getFreeVersionMaxPoints() { return mFreeVersionMaxPoints; }
}