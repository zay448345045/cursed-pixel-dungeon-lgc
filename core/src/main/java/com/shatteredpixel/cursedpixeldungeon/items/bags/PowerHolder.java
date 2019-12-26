package com.shatteredpixel.cursedpixeldungeon.items.bags;

import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.items.powers.Power;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;

public class PowerHolder extends Bag {
    {
        image = ItemSpriteSheet.POWERHOLDER;

        size = 35;
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
