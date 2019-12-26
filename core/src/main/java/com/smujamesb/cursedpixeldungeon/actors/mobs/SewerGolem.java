/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.smujamesb.cursedpixeldungeon.actors.mobs;

import com.smujamesb.cursedpixeldungeon.Badges;
import com.smujamesb.cursedpixeldungeon.Dungeon;
import com.smujamesb.cursedpixeldungeon.actors.Char;
import com.smujamesb.cursedpixeldungeon.actors.blobs.Blob;
import com.smujamesb.cursedpixeldungeon.actors.blobs.SpawnerGas;
import com.smujamesb.cursedpixeldungeon.actors.buffs.Buff;
import com.smujamesb.cursedpixeldungeon.actors.buffs.Cripple;
import com.smujamesb.cursedpixeldungeon.actors.buffs.LockedFloor;
import com.smujamesb.cursedpixeldungeon.effects.CellEmitter;
import com.smujamesb.cursedpixeldungeon.effects.Speck;
import com.smujamesb.cursedpixeldungeon.effects.particles.SparkParticle;
import com.smujamesb.cursedpixeldungeon.items.AmuletSectorWater;
import com.smujamesb.cursedpixeldungeon.items.Generator;
import com.smujamesb.cursedpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.smujamesb.cursedpixeldungeon.items.wands.Wand;
import com.smujamesb.cursedpixeldungeon.items.wands.WandOfPrismaticLight;
import com.smujamesb.cursedpixeldungeon.items.weapon.enchantments.Grim;
import com.smujamesb.cursedpixeldungeon.mechanics.Ballistica;
import com.smujamesb.cursedpixeldungeon.messages.Messages;
import com.smujamesb.cursedpixeldungeon.scenes.GameScene;
import com.smujamesb.cursedpixeldungeon.sprites.CharSprite;
import com.smujamesb.cursedpixeldungeon.sprites.SewerGolemSprite;
import com.smujamesb.cursedpixeldungeon.ui.BossHealthBar;
import com.smujamesb.cursedpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class SewerGolem extends Mob implements Callback  {
    private static final float TIME_TO_ZAP	= 2f;
    {
        spriteClass = SewerGolemSprite.class;

        HP = HT = 2000;
        defenseSkill = 30;

        EXP = 22;

        loot = Generator.random(Generator.Category.WEP_T6);
        lootChance = 1f;

        baseSpeed = 1.5f;//Faster than the hero
        properties.add(Property.BOSS);
        properties.add(Property.DEMONIC);
        immunities.add(Grim.class);
    }

    public static class LightningBolt{}

    @Override
    protected boolean doAttack( Char enemy ) {

        if (Dungeon.level.distance( pos, enemy.pos ) <= 1) {

            return super.doAttack( enemy );

        } else {

            boolean visible = fieldOfView[pos] || fieldOfView[enemy.pos];
            if (visible) {
                sprite.zap( enemy.pos );
            }

            spend( TIME_TO_ZAP );

            if (hit( this, enemy, true )) {
                int dmg = Random.NormalIntRange(20, 60);
                if (Dungeon.level.water[enemy.pos] && !enemy.flying) {
                    dmg *= 2f;
                }
                enemy.damage( dmg, new LightningBolt() );

                enemy.sprite.centerEmitter().burst( SparkParticle.FACTORY, 3 );
                enemy.sprite.flash();

                if (enemy == Dungeon.hero) {

                    Camera.main.shake( 2, 0.3f );

                    if (!enemy.isAlive()) {
                        Dungeon.fail( getClass() );
                        GLog.n( Messages.get(this, "zap_kill") );
                    }
                }
            } else {
                enemy.sprite.showStatus( CharSprite.NEUTRAL,  enemy.defenseVerb() );
            }

            return !visible;
        }
    }

    @Override
    public void notice() {
        super.notice();
        BossHealthBar.assignBoss(this);
        yell( Messages.get(this, "notice") );
    }

    @Override
    public void damage( int dmg, Object src ) {
        this.notice();
        if (Random.Int(2) == 1 & dmg > HT/100f) {
            GameScene.add(Blob.seed(Dungeon.hero.pos, 300, SpawnerGas.class));
            int newPos = -1;
            for (int i = 0; i < 20; i++) {
                newPos = Dungeon.level.randomRespawnCell();
                if (newPos != -1) {
                    break;
                }
            }
            if (newPos != -1) {
                CellEmitter.get(pos).start(Speck.factory(Speck.LIGHT), 0.2f, 3);
                pos = newPos;
                sprite.place(pos);
                sprite.visible = Dungeon.hero.fieldOfView[pos];
            }
        }
        if (src instanceof Wand & !(src instanceof WandOfPrismaticLight)) {
            dmg = Random.Int(dmg);
        }
        boolean bleeding = (HP*2 <= HT);
        super.damage(dmg, src);
        if ((HP*2 <= HT) && !bleeding){
            BossHealthBar.bleed(true);
        }
        LockedFloor lock = Dungeon.hero.buff(LockedFloor.class);
        if (lock != null) lock.addTime(dmg*2);
    }
    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 40, 85);
    }

    @Override
    public int attackSkill( Char target ) {
        return 36;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 16);
    }

    @Override
    protected boolean canAttack( Char enemy ) {
        Ballistica attack = new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE);
        return !Dungeon.level.adjacent( pos, enemy.pos ) && attack.collisionPos == enemy.pos;
    }

    @Override
    public int attackProc( Char enemy, int damage ) {
        damage = super.attackProc( enemy, damage );
        sprite.zap( enemy.pos );
        enemy.sprite.centerEmitter().burst( SparkParticle.FACTORY, 3 );
        enemy.sprite.flash();
        if (Random.Int( 2 ) == 0) {
            Buff.prolong( enemy, Cripple.class, Cripple.DURATION );
        }

        return damage;
    }

    @Override
    public void die( Object cause ) {

        super.die( cause );

        Dungeon.level.unseal();

        GameScene.bossSlain();
        //60% chance of 2 blobs, 30% chance of 3, 10% chance for 4. Average of 2.5
        int blobs = Random.chances(new float[]{0, 0, 6, 3, 1});
        for (int i = 0; i < blobs; i++){
            int ofs;
            do {
                ofs = PathFinder.NEIGHBOURS8[Random.Int(8)];
            } while (!Dungeon.level.passable[pos + ofs]);
            Dungeon.level.drop( new ScrollOfUpgrade(), pos + ofs ).sprite.drop( pos );
        }

        Dungeon.level.drop(new AmuletSectorWater(), pos).sprite.drop(pos);

        Badges.validateBossSlain();

        yell( Messages.get(this, "defeated") );
    }



    @Override
    protected boolean getCloser( int target ) {
        if (state == HUNTING) {
            return enemySeen && getFurther( target );
        } else {
            return super.getCloser( target );
        }
    }


    @Override
    public void call() {
        next();
    }
}
