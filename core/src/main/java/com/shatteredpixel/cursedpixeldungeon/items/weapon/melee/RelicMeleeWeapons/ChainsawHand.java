package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons;

import com.shatteredpixel.cursedpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments.Bloodlust;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments.RelicEnchantment;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;

public class ChainsawHand extends RelicMeleeWeapon {
    {
        image = ItemSpriteSheet.CHAINSAW_HAND;
        ACC = 2f;
        damageMultiplier = 0.5f;
        cursed = true;
    }

    @Override
    public RelicEnchantment enchantment() {
        return new Bloodlust();
    }

    @Override
    public Weapon enchant(Enchantment ench) {
        cursed = true;//Since it has a curse enchantment, cleansing it will always remove it by enchant(null). This code, along with the regular RelicMeleeWeapon code, ensures that it stays cursed just like the Sprouted version.
        return super.enchant(ench);
    }
}
