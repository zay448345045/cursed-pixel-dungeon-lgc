package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.cursedpixeldungeon.effects.Lightning;
import com.shatteredpixel.cursedpixeldungeon.effects.particles.SparkParticle;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicMeleeWeapon;
import com.shatteredpixel.cursedpixeldungeon.levels.Level;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.watabou.noosa.Camera;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Voltage extends RelicEnchantment {
    private static ItemSprite.Glowing WHITE = new ItemSprite.Glowing( 0xFFFFFF, 0.5f );
    private int cost = 10;
    @Override
    public int relicProc(RelicMeleeWeapon weapon, Char attacker, Char defender, int damage) {
        if (weapon.charge>=cost){
            weapon.charge-=cost;
        } else {
            return damage;
        }

        int level = Math.max(0, weapon.level());
        int distance = 5 + level;

        for (Mob mob : Dungeon.level.mobs) {

            if (Dungeon.level.distance(attacker.pos, mob.pos) < distance && mob.isAlive()){
                // int dmg = 20;
                attacker.sprite.parent.addToFront( new Lightning( attacker.pos, mob.pos, null ) );

                mob.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
                mob.sprite.flash();

                if (Dungeon.level.water[mob.pos] && !mob.flying) {
                    damage *= 2;
                }

                if (mob.isAlive() & mob != defender){
                    mob.damage(damage/4, weapon);
                }

                Camera.main.shake(2, 0.3f);
            }
        }


        return damage;
    }
    @Override
    public ItemSprite.Glowing glowing() {
        return WHITE;
    }
}
