package com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Random;

public class Swift extends Weapon.Enchantment {
    private static ItemSprite.Glowing YELLOW = new ItemSprite.Glowing( 0xFFFF00 );

    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        //no proc effect, see Hero.damage
        return damage;
    }

    public static int drRoll( int level ){
        return Random.NormalIntRange(2+level, 4 + (level*2));
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return YELLOW;
    }
}
