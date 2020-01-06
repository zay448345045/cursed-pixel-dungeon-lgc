package com.shatteredpixel.cursedpixeldungeon.actors.buffs;

import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.ui.BuffIndicator;

import java.text.DecimalFormat;

public class Bloodlust extends FlavourBuff {
    {
        type = buffType.POSITIVE;
        announced = true;
    }

    @Override
    public int icon() {
        return BuffIndicator.RAGE;
    }

    public float bonusDamage(){
        return 1f + Math.max(0.25f, 1 - cooldown()*0.05f);
    }

    public void attack() {
        spend(-3f);
        if (cooldown() <= 0){
            detach();
        }
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", dispTurns(), new DecimalFormat("#.##").format((bonusDamage()-1f)*100f));
    }
}
