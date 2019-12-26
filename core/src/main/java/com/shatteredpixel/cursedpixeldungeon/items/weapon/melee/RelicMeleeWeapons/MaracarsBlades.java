package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons;

import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments.Inferno;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments.RelicEnchantment;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;

public class MaracarsBlades extends RelicMeleeWeapon {
    {
        damageMultiplier = 0.64f;
        ACC = 0.8f;
        DLY = 0.5f;
        image = ItemSpriteSheet.MARACARS_BLADES;
    }

    @Override
    public RelicEnchantment enchantment() {
        return new Inferno();
    }
}
