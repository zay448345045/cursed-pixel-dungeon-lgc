package com.shatteredpixel.cursedpixeldungeon.items.powers;

import com.shatteredpixel.cursedpixeldungeon.items.stones.StoneOfFlock;
import com.shatteredpixel.cursedpixeldungeon.mechanics.Ballistica;

public class Blocker extends TargetedPower {
    @Override
    public void onZap(Ballistica shot) {
        int cell = shot.collisionPos;
        new StoneOfFlock().activate(cell);
    }
}
