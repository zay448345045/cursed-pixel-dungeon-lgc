package com.shatteredpixel.cursedpixeldungeon.items;

import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;

import java.util.ArrayList;

public class Soul extends Item {
    {
        image = ItemSpriteSheet.SOUL;
    }
    private static final String AC_CONSUME = "consume";
    private static final String AC_SACRIFICE = "sacrifice";
    private static final String AC_SUMMON = "summon";

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_CONSUME);
        actions.add(AC_SACRIFICE);
        actions.add(AC_SUMMON);
        return actions;
    }


}
