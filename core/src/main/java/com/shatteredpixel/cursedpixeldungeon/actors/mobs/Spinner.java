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

import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.cursedpixeldungeon.actors.blobs.Web;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.cursedpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.SpinnerSprite;
import com.watabou.utils.Random;

public class Spinner extends Mob {

	{
		spriteClass = SpinnerSprite.class;

		HP = HT = 100;
		defenseSkill = 14;

		EXP = 9;
		maxLvl = 17;

		loot = new MysteryMeat();
		lootChance = 0.125f;

		FLEEING = new Fleeing();
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(15, 60);
	}

	@Override
	public int attackSkill(Char target) {
		return 20;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 10);
	}

	@Override
	protected boolean act() {
		boolean result = super.act();

		if (state == FLEEING && buff( Terror.class ) == null &&
				enemy != null && enemySeen && enemy.buff( Poison.class ) == null) {
				state = HUNTING;
		}
		return result;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		damage = super.attackProc( enemy, damage );
		if (Random.Int(2) == 0) {
			Buff.affect(enemy, Poison.class).set(Random.Int(7, 9) );
			state = FLEEING;
		}

		return damage;
	}

	@Override
	public void move(int step) {
		int curWeb = Blob.volumeAt(pos, Web.class);
		if (state == FLEEING && curWeb < 5) {
			GameScene.add(Blob.seed(pos, Random.Int(5, 7) - curWeb, Web.class));
		}
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
