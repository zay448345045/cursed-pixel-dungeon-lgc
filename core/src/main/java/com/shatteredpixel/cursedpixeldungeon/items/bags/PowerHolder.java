package com.shatteredpixel.cursedpixeldungeon.items.bags;

import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.items.powers.Power;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;

public class PowerHolder extends Bag {
    {
        image = ItemSpriteSheet.SPELLBOOK;

        size = 5;

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
    public boolean grab( Item item ) {
        return item instanceof Power;
    }

    @Override
    public int price() {
        return 30;
    }

}
