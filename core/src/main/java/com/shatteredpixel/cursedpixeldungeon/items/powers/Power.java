package com.shatteredpixel.cursedpixeldungeon.items.powers;

import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.items.Item;

import java.util.ArrayList;

public abstract class Power extends Item {
    {
        cursed = false;
    }

    @Override
    public boolean isIdentified() {//Always identified
        return true;
    }

    public ArrayList<String> actions(Hero hero ) {//Can't drop or throw a power
        ArrayList<String> actions = super.actions(hero);
        actions.remove( AC_DROP );
        actions.remove( AC_THROW );
        return actions;
    }
}
