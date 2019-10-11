package com.shatteredpixel.cursedpixeldungeon.items;

import com.shatteredpixel.cursedpixeldungeon.Assets;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;

public class AmuletSectorWater extends Item {
    {
        image = ItemSpriteSheet.AMULET_WATER;
        unique = true;
        bones = false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }
}
