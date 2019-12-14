package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.MagicalSleep;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.npcs.Shopkeeper;
import com.shatteredpixel.cursedpixeldungeon.effects.Speck;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicMeleeWeapon;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Random;

public class Ethereal extends RelicEnchantment {
    private static ItemSprite.Glowing SILVER = new ItemSprite.Glowing( 0x909396 );

    @Override
    public int relicProc(RelicMeleeWeapon weapon, Char attacker, Char defender, int damage) {
        defender.sprite.emitter().start( Speck.factory( Speck.DISCOVER ), 0.05f, 5 * weapon.level() + 1 );
        if (Random.Int( 100-weapon.level()) < 30) {
            Buff.prolong(defender, Blindness.class, 1 + Random.Int(weapon.level()/2+3));
            Buff.prolong(defender, Cripple.class, Cripple.DURATION);
        }
        if (Random.Int(Dungeon.level.distance(attacker.pos, defender.pos)*2 - (weapon.level()/2) + 10) <= 2) {
            Buff.affect(defender, MagicalSleep.class);
        }
        return damage;
    }

    @Override
    public void activate(RelicMeleeWeapon weapon, Char owner) {
        super.activate(weapon, owner);
        Buff.affect(owner, MagicalSleep.class);
        for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
            if (!(mob instanceof Shopkeeper)) {//We don't want him to run away!
                Buff.affect(mob, MagicalSleep.class);
            }
        }
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return SILVER;
    }
}
