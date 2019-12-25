package com.shatteredpixel.cursedpixeldungeon.items.weapon.enchantments;

import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.cursedpixeldungeon.plants.Swiftthistle;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Random;

public class Swift extends Weapon.Enchantment {
    private static ItemSprite.Glowing YELLOW = new ItemSprite.Glowing( 0xFFEE00 );

    @Override
    public int proc(Item weapon, Char attacker, Char defender, int damage) {
        if (Random.Int(4) == 0) {
            Buff.affect(attacker, Swiftthistle.TimeBubble.class).reset();
        }
        return damage;
    }

    /*public static boolean activate(KindOfWeapon weapon) {
        return Random.Int(3) == 1;
    }*/

    @Override
    public ItemSprite.Glowing glowing() {
        return YELLOW;
    }
}
