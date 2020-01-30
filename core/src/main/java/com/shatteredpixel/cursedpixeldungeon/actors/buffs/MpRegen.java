package com.shatteredpixel.cursedpixeldungeon.actors.buffs;

import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;

public class MpRegen extends Buff {

    {
        actPriority = HERO_PRIO - 1;
    }

    private static final float REGENERATION_DELAY = 20;

    @Override
    public boolean act() {
        if (target.isAlive()) {
            Hero hero = ((Hero)target);
            if (hero.MP < hero.MAX_MP && !hero.isStarving()) {
                LockedFloor lock = hero.buff(LockedFloor.class);
                if ((lock == null || lock.regenOn()) && hero.buff(MagicImmune.class) == null) {
                    hero.MP += 1;
                }
            }
            spend( REGENERATION_DELAY );
        } else {
            diactivate();
        }
        return true;
    }
}
