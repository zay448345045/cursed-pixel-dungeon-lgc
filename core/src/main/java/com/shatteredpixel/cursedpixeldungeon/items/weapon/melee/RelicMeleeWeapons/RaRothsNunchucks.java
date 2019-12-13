package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons;

import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments.Etheral;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments.RelicEnchantment;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;

public class RaRothsNunchucks extends RelicMeleeWeapon {
    {
        image = ItemSpriteSheet.RA_ROTHS_NUNCHUCKS;
        ACC = 0.7f;
        RCH = 3;
        DLY = 0.5f;
        damageMultiplier = 0.5f;
    }

    @Override
    public RelicEnchantment enchantment() {
        return new Etheral();
    }
}
