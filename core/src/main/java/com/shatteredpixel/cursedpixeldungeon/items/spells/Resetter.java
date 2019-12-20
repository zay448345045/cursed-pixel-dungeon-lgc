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

package com.shatteredpixel.cursedpixeldungeon.items.spells;

import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.exotic.ScrollOfConfusion;
import com.shatteredpixel.cursedpixeldungeon.levels.traps.DistortionTrap;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;

public class Resetter extends Spell {
	
	{
		image = ItemSpriteSheet.AQUA_BLAST;
	}

	
	@Override
	public int price() {
		return Math.round(quantity * 40);
	}

	@Override
	protected void onCast(Hero hero) {
		new DistortionTrap().activate();
	}

	public static class Recipe extends com.shatteredpixel.cursedpixeldungeon.items.Recipe.SimpleRecipe {
		
		{
			inputs =  new Class[]{ScrollOfConfusion.class, ArcaneCatalyst.class};
			inQuantity = new int[]{1, 1};
			
			cost = 8;
			
			output = Resetter.class;
			outQuantity = 2;
		}
		
	}
}
