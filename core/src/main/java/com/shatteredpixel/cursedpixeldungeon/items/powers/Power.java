package com.shatteredpixel.cursedpixeldungeon.items.powers;

import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;

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



    private static final String CHARGE = "charge";
    private static final String PARTIALCHARGE = "partialcharge";
    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);
        bundle.put( CHARGE , charge );
        bundle.put( PARTIALCHARGE , partialCharge );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        if (chargeCap > 0)  charge = Math.min( chargeCap, bundle.getInt( CHARGE ));
        else                charge = bundle.getInt( CHARGE );
        partialCharge = bundle.getFloat( PARTIALCHARGE );
    }

    protected PowerBuff passiveBuff(){
        return null;
    }

    /*@Override
    protected void onDetach() {
        if (curUser.buff(passiveBuff().getClass()) != null) {
            curUser.buff(passiveBuff().getClass()).detach();
            passiveBuff = null;
        }

        if (activeBuff != null) {
            activeBuff.detach();
            activeBuff = null;
        }
    }*/

    abstract static class PowerBuff extends Buff {

    }

}
