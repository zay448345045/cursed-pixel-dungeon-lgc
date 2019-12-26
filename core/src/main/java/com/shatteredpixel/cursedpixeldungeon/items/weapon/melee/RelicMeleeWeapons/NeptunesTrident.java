package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons;

import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments.RelicEnchantment;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments.Voltage;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;

public class NeptunesTrident extends RelicMeleeWeapon {
    {
        image = ItemSpriteSheet.NEPTUNES_TRIDENT;
        DLY = 1.5f;
        ACC = 1.1f;
        damageMultiplier = 1.6f;
        RCH = 2;
        chargeToAdd = 0.5f;//200 turns to charge.
    }

    @Override
    public RelicEnchantment enchantment() {
        return new Voltage();
    }
}
