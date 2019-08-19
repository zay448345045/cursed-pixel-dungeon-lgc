package com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Random;

public class Swift extends Weapon.Enchantment {
    private static ItemSprite.Glowing YELLOW_1 = new ItemSprite.Glowing( 0xFFFE00 );

    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        //no proc effect, see Hero.attackDelay
        return damage;
    }

    public static boolean activate(KindOfWeapon weapon) {
        return Random.Int(3) == 1;
    }

    public static int drRoll( int level ){
        return Random.NormalIntRange(2+level, 4 + (level*2));
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return YELLOW_1;
    }
}
