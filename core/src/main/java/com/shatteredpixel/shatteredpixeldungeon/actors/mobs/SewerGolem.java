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

package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Web;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SpinnerSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.StatueSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

public class SewerGolem extends Mob {

    {
        spriteClass = StatueSprite.class;

        HP = HT = 1000;
        defenseSkill = 45;

        EXP = 22;

        loot = Generator.randomWeapon();
        lootChance = 0.125f;

        FLEEING = new Fleeing();
        baseSpeed = 2f;
        properties.add(Property.BOSS);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(10, 40);
    }

    @Override
    public int attackSkill(Char target) {
        return 20;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 6);
    }

    @Override
    protected boolean act() {
        boolean result = super.act();

        if (state == FLEEING && buff( Terror.class ) == null &&
                enemy != null && enemySeen && enemy.buff( Blindness.class ) == null) {
            state = HUNTING;
        }
        return result;
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        damage = super.attackProc( enemy, damage );
        if (Random.Int(2) == 0) {
            Buff.affect(enemy, Blindness.class,9);
            state = FLEEING;
        }

        return damage;
    }

    protected boolean doAttack( Char enemy ) {

        if (Dungeon.level.adjacent( pos, enemy.pos )) {

            return super.doAttack( enemy );

        } else {

            boolean visible = fieldOfView[pos] || fieldOfView[enemy.pos];
            if (visible) {
                sprite.zap( enemy.pos );
            } else {
                zap();
            }

            return !visible;
        }
    }

    private void zap() {
        spend( 1 );

        if (hit( this, enemy, true )) {

            int dmg = Random.Int( 20, 26 );
            enemy.damage( dmg, new Shaman.LightningBolt() );

            if (!enemy.isAlive() && enemy == Dungeon.hero) {
                Dungeon.fail( getClass() );
                GLog.n( Messages.get(this, "bolt_kill") );
            }
        } else {
            enemy.sprite.showStatus( CharSprite.NEUTRAL,  enemy.defenseVerb() );
        }
    }

    @Override
    public void move(int step) {
        super.move(step);
    }

    {
        resistances.add(Poison.class);
    }

    {
        immunities.add(Web.class);
    }

    private class Fleeing extends Mob.Fleeing {
        @Override
        protected void nowhereToRun() {
            if (buff(Terror.class) == null) {
                state = HUNTING;
            } else {
                super.nowhereToRun();
            }
        }
    }
}
