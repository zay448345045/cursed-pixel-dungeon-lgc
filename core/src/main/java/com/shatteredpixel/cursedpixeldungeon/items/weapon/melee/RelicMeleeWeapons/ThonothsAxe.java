package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons;

import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments.RelicEnchantment;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments.Vicious;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;

public class ThonothsAxe extends RelicMeleeWeapon {
    {
        image = ItemSpriteSheet.THONOTHS_AXE;
        ACC = 1.5f;
        damageMultiplier = 0.74f;
    }

    @Override
    public RelicEnchantment enchantment() {
        return new Vicious();
    }
}
