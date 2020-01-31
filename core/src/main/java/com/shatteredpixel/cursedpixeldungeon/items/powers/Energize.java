package com.shatteredpixel.cursedpixeldungeon.items.powers;

import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;

public class Energize extends ActivatedPower {

    {
        image = ItemSpriteSheet.ENERGIZE;
        mp_cost = 5;
    }

    @Override
    public boolean usesTargeting() {
        return false;
    }

    @Override
    public void affectCell(int pos) {
        Char ch = Actor.findChar(pos);
        if (ch != null) {
            Buff.affect(ch, Recharging.class, ScrollOfRecharging.BUFF_DURATION/4f);
        }
    }
}
