package com.shatteredpixel.cursedpixeldungeon.items.bags;

import com.shatteredpixel.cursedpixeldungeon.CPDSettings;
import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.DeferredDeath;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.items.powers.Power;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;

public class PowerHolder extends Bag {
    {
        image = ItemSpriteSheet.SPELLBOOK;

        size = 35;

        unique = true;

        bones = false;
    }

    private static final String SIZE = "size";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put( SIZE, size );
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        size = bundle.getInt( SIZE );
    }

    @Override
    public boolean collect() {
        updateSize(Dungeon.hero);
        return super.collect();
    }

    public void updateSize(Hero hero) {
        if (CPDSettings.testing()) {
            size = 35;
        } else {
            size = hero.magicSkill;
        }
    }

    @Override
    public boolean grab( Item item ) {
        return item instanceof Power;
    }

}
