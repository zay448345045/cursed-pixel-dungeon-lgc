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

package com.shatteredpixel.cursedpixeldungeon.actors.mobs;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.cursedpixeldungeon.effects.Pushing;
import com.shatteredpixel.cursedpixeldungeon.effects.Speck;
import com.shatteredpixel.cursedpixeldungeon.levels.Terrain;
import com.shatteredpixel.cursedpixeldungeon.levels.features.Door;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.NewbornElementalSprite;
import com.shatteredpixel.cursedpixeldungeon.sprites.WaterElementalSprite;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class WaterElemental extends Mob {

    {
        spriteClass = WaterElementalSprite.class;

        HT = 400;
        HP = HT;

        defenseSkill = 25;

        EXP = 15;
        maxLvl = 30;
        properties.add(Property.ACIDIC);
        resistances.add(Fire.class);

    }
    private static final float SPLIT_DELAY	= 1f;

    int generation	= 0;

    private static final String GENERATION	= "generation";

    @Override
    public void add(Buff buff) {
            super.add(buff);

    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 36, 70 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 35;
    }

    @Override
    public boolean act() {

        if (Dungeon.level.water[pos] && HP < HT) {
            sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );

            HP+= 10;
        }

        return super.act();
    }

    @Override
    public int defenseProc(Char enemy, int damage ) {

        if (HP >= damage + 2) {
            ArrayList<Integer> candidates = new ArrayList<>();
            boolean[] solid = Dungeon.level.solid;

            int[] neighbours = {pos + 1, pos - 1, pos + Dungeon.level.width(), pos - Dungeon.level.width()};
            for (int n : neighbours) {
                if (!solid[n] && Actor.findChar( n ) == null) {
                    candidates.add( n );
                }
            }

            if (candidates.size() > 0) {

                WaterElemental clone = split();
                clone.HP = (HP - damage) / 2;
                clone.pos = Random.element( candidates );
                clone.state = clone.HUNTING;

                if (Dungeon.level.map[clone.pos] == Terrain.DOOR) {
                    Door.enter( clone.pos );
                }

                GameScene.add( clone, SPLIT_DELAY );
                Actor.addDelayed( new Pushing( clone, pos, clone.pos ), -1 );

                HP -= clone.HP;
            }
        }

        return super.defenseProc(enemy, damage);
    }

    private WaterElemental split() {
        WaterElemental clone = new WaterElemental();
        clone.generation = generation + 1;
        clone.EXP = 0;
        if (buff( Burning.class ) != null) {
            Buff.affect( clone, Burning.class ).reignite( clone );
        }
        if (buff( Poison.class ) != null) {
            Buff.affect( clone, Poison.class ).set(2);
        }
        if (buff(Corruption.class ) != null) {
            Buff.affect( clone, Corruption.class);
        }
        return clone;
    }
    public class ElementalIce {}//Used so Ring of Elements only affects extra damage.
    @Override
    public void die(Object cause) {
        super.die(cause);
    }
    @Override
    public int attackProc( Char enemy, int damage ) {
        damage = super.attackProc( enemy, damage );
        if (Random.Int( 2 ) == 0) {
            Buff.affect( enemy, Chill.class, 4f );
        } else {
            float duration = 0;
            Chill chill = Dungeon.hero.buff(Chill.class);
            if (chill != null) {
                duration = chill.speedFactor();
            }
            if (duration > 0) {
                enemy.damage((int)(damageRoll()*duration*0.5f),ElementalIce.class);
            }


        }

        return damage;
    }
}

