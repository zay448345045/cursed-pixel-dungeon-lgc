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

package com.smujamesb.cursedpixeldungeon.plants;

import com.smujamesb.cursedpixeldungeon.Dungeon;
import com.smujamesb.cursedpixeldungeon.actors.Char;
import com.smujamesb.cursedpixeldungeon.actors.buffs.Buff;
import com.smujamesb.cursedpixeldungeon.actors.buffs.Poison;
import com.smujamesb.cursedpixeldungeon.actors.buffs.ToxicImbue;
import com.smujamesb.cursedpixeldungeon.actors.hero.Hero;
import com.smujamesb.cursedpixeldungeon.actors.hero.HeroSubClass;
import com.smujamesb.cursedpixeldungeon.effects.CellEmitter;
import com.smujamesb.cursedpixeldungeon.effects.particles.PoisonParticle;
import com.smujamesb.cursedpixeldungeon.sprites.ItemSpriteSheet;

public class Sorrowmoss extends Plant {

	{
		image = 6;
	}
	
	@Override
	public void activate( Char ch ) {
		if (ch instanceof Hero && ((Hero) ch).subClass == HeroSubClass.WARDEN){
			Buff.affect(ch, ToxicImbue.class).set(15f);
		}
		
		if (ch != null) {
			Buff.affect( ch, Poison.class ).set( 5 + Math.round(2*Dungeon.depth / 3f) );
		}
		
		if (Dungeon.level.heroFOV[pos]) {
			CellEmitter.center( pos ).burst( PoisonParticle.SPLASH, 3 );
		}
	}
	
	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_SORROWMOSS;

			plantClass = Sorrowmoss.class;
		}
	}
}
