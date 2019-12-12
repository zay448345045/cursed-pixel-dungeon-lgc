package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons;

import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments.Inferno;

public class MaracarsBlades extends RelicMeleeWeapon {
    {
        damageMultiplier = 0.64f;
    }

    @Override
    public Enchantment enchantment() {
        return new Inferno();
    }
}
