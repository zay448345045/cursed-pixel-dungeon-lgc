package com.shatteredpixel.cursedpixeldungeon.items.powers;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.items.Item;

import java.util.ArrayList;

public abstract class Power extends Item {
    {
        cursed = false;
        cursedKnown = true;

        unique = true;
    }
    int charge = 0;
    float partialCharge = 0;
    int chargeCap = 100;

    PowerBuff passiveBuff = null;

    PowerBuff activeBuff = null;

    @Override
    public ArrayList<String> actions(Hero hero) {
        return new ArrayList<>();
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public boolean doPickUp(Hero hero) {
        passiveBuff = passiveBuff();
        passiveBuff.attachTo(hero);
        return super.doPickUp(hero);
    }

    protected PowerBuff passiveBuff() {
        return null;
    }

    @Override
    protected void onDetach() {

        passiveBuff.detach();
        passiveBuff = null;

        if (activeBuff != null) {
            activeBuff.detach();
            activeBuff = null;
        }


    }

    public abstract static class PowerBuff extends Buff {

    }

}
