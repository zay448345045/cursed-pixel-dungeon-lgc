package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments;

import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicMeleeWeapon;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;

public class Bloodlust extends RelicEnchantment {

    private static ItemSprite.Glowing BLACK = new ItemSprite.Glowing( 0x000000 );

    @Override
    public boolean curse() {
        return true;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return BLACK;
    }

    @Override
    public int relicProc(RelicMeleeWeapon weapon, Char attacker, Char defender, int damage) {
        return damage;//Effect is in hero.actuallyAttack(). This does mean it doesn't work for Statues, but I doubt Statues will ever use Tier 6 weapons.
    }
}
