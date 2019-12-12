package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments;

import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicMeleeWeapon;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;

import javax.microedition.khronos.opengles.GL;

public abstract class RelicEnchantment extends Weapon.Enchantment {
    @Override
    public int proc(Item weapon, Char attacker, Char defender, int damage) {
        if (weapon instanceof RelicMeleeWeapon) {
            return relicProc((RelicMeleeWeapon)weapon,attacker,defender,damage);
        } else {
            GLog.w("Inconceivable!");
            return 0;
        }
    }

    public abstract int relicProc(RelicMeleeWeapon weapon, Char attacker, Char defender, int damage);

    @Override
    public ItemSprite.Glowing glowing() {
        return null;
    }
}
