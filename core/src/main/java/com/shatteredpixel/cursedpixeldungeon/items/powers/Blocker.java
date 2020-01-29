package com.shatteredpixel.cursedpixeldungeon.items.powers;

import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.items.stones.StoneOfFlock;
import com.shatteredpixel.cursedpixeldungeon.mechanics.Ballistica;

public class Blocker extends ActivatedPower {
    {
        mp_cost = 4;
    }

    @Override
    public boolean usesTargeting() {
        return true;
    }

    @Override
    public void affectCell(int pos) {
        new StoneOfFlock().activate(pos);
    }
}
