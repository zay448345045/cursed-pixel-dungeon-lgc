package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments;

import com.shatteredpixel.cursedpixeldungeon.CPDSettings;
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
    private int cost = 5;
    @Override
    public int relicProc(RelicMeleeWeapon weapon, Char attacker, Char defender, int damage) {


        boolean procced = false;

        int level = Math.max(0, weapon.level());
        int distance = 5 + level;
        try {

            for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {

                if (Dungeon.level.distance(attacker.pos, mob.pos) < distance && mob.isAlive() && mob.isEnemy()) {
                    // int dmg = 20;
                    attacker.sprite.parent.addToFront(new Lightning(attacker.pos, mob.pos, null));
                    if (!CPDSettings.particles()) {
                        mob.sprite.centerEmitter().burst(SparkParticle.FACTORY, 10 + 5 * level);
                    }
                    mob.sprite.flash();

                    if (mob.isAlive() & mob != defender) {
                        procced = true;
                        int splitDamage = damage/6;
                        if (Dungeon.level.water[mob.pos] && !mob.flying) {
                            splitDamage *= 3;
                        }
                        splitDamage = mob.defenseProc( attacker, splitDamage );
                        mob.damage( splitDamage, weapon );
                        mob.aggro( attacker );
                    }

                    Camera.main.shake(2, 0.3f);
                }
            }
        } catch (Exception e) {
            return damage;
        }

        if (weapon.charge>=cost & procced){
            weapon.charge-=cost;
        } else {
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
