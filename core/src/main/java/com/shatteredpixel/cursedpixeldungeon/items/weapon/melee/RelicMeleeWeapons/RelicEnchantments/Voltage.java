package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.cursedpixeldungeon.actors.blobs.StormCloud;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.cursedpixeldungeon.effects.Lightning;
import com.shatteredpixel.cursedpixeldungeon.effects.particles.SparkParticle;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicMeleeWeapon;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.watabou.noosa.Camera;

public class Voltage extends RelicEnchantment {
    private static ItemSprite.Glowing WHITE = new ItemSprite.Glowing( 0xFFFFFF, 0.5f );
    private int cost = 4;
    @Override
    public int relicProc(RelicMeleeWeapon weapon, Char attacker, Char defender, int damage) {
        if (weapon.charge>=cost){
            weapon.charge-=cost;
        } else {
            return damage;
        }

        int level = Math.max(0, weapon.level());
        int distance = 5 + level;
        try {

            for (Mob mob : Dungeon.level.mobs) {

                if (Dungeon.level.distance(attacker.pos, mob.pos) < distance && mob.isAlive() && mob.alignment == Char.Alignment.ENEMY) {
                    // int dmg = 20;
                    attacker.sprite.parent.addToFront(new Lightning(attacker.pos, mob.pos, null));

                    mob.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
                    mob.sprite.flash();

                    if (mob.isAlive() & mob != defender) {
                        int splitDamage = damage/4;
                        if (Dungeon.level.water[mob.pos] && !mob.flying) {
                            splitDamage *= 2;
                        }
                        mob.damage( splitDamage, weapon );
                        mob.aggro( attacker );
                    }

                    Camera.main.shake(2, 0.3f);
                }
            }
        } catch (Exception e) {
            return damage;
        }

        return damage;
    }

    @Override
    public void activate(RelicMeleeWeapon weapon, Char owner) {
        super.activate(weapon, owner);
        GameScene.add(Blob.seed(owner.pos, 500, StormCloud.class));
        for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
            GameScene.add(Blob.seed(mob.pos, 100, StormCloud.class));
        }
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return WHITE;
    }
}
