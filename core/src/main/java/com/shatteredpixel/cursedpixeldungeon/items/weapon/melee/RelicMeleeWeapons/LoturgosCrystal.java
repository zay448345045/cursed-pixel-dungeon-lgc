package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons;

import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments.Barrier;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments.RelicEnchantment;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;

public class LoturgosCrystal extends RelicMeleeWeapon {
    {
        image = ItemSpriteSheet.LOTURGOS_CRYSTAL;
        chargeToAdd = 1f;
        usesTargeting = true;
    }

    @Override
    public RelicEnchantment enchantment() {
        return new Barrier();
    }
}
