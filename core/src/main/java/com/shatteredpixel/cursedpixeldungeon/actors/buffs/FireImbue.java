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

package com.shatteredpixel.cursedpixeldungeon.actors.buffs;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.effects.particles.FlameParticle;
import com.shatteredpixel.cursedpixeldungeon.levels.Terrain;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.shatteredpixel.cursedpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class FireImbue extends Buff {
	
	{
		type = buffType.POSITIVE;
		announced = true;
	}

	public static final float DURATION	= 50f;

	protected float left;

	private static final String LEFT	= "left";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( LEFT, left );

	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		left = bundle.getFloat( LEFT );
	}

	public void set( float duration ) {
		this.left = duration;
	};

	@Override
	public boolean act() {
		if (Dungeon.level.map[target.pos] == Terrain.GRASS) {
			Dungeon.level.set(target.pos, Terrain.EMBERS);
			GameScene.updateMap(target.pos);
		}

		spend(TICK);
		left -= TICK;
		if (left <= 0){
			detach();
		} else if (left < 5){
			BuffIndicator.refreshHero();
		}

		return true;
	}

	public void proc(Char enemy){
		if (Random.Int(2) == 0)
			Buff.affect( enemy, Burning.class ).reignite( enemy );

		enemy.sprite.emitter().burst( FlameParticle.FACTORY, 2 );
	}

	@Override
	public int icon() {
		return BuffIndicator.FIRE;
	}
	
	@Override
	public void tintIcon(Image icon) {
		FlavourBuff.greyIcon(icon, 5f, left);
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", dispTurns(left));
	}

	{
		immunities.add( Burning.class );
	}
}
