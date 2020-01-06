package com.shatteredpixel.cursedpixeldungeon.items.potions;

import com.shatteredpixel.cursedpixeldungeon.Badges;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;

import java.util.ArrayList;

public class MegaStrengthPotion extends Item {
    {
        name = "Mega Potion of Experience";

        bones = false;

        image = ItemSpriteSheet.POTION_AZURE;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    public static final String AC_DRINK = "DRINK";
    public void apply(Hero hero) {

        hero.STR += 10;
        hero.sprite.showStatus( CharSprite.POSITIVE, "+10 STR" );
        GLog.p( "You feel a rush of strength in your body..." );

        Badges.validateStrengthAttained();
    }

    @Override
    public String desc() {
        return "Drink this for cool stuff like much experience. It is good.";
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_DRINK);
        return actions;
    }

    @Override
    public void execute(final Hero hero, String action) {
        if (action.equals(AC_DRINK)) {
            apply(hero);
        } else {
            super.execute(hero,action);
        }
    }
}