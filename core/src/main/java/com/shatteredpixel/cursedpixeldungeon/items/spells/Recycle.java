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

import com.shatteredpixel.cursedpixeldungeon.Challenges;
import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.cursedpixeldungeon.items.Generator;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.items.potions.Potion;
import com.shatteredpixel.cursedpixeldungeon.items.potions.brews.Brew;
import com.shatteredpixel.cursedpixeldungeon.items.potions.elixirs.Elixir;
import com.shatteredpixel.cursedpixeldungeon.items.potions.exotic.ExoticPotion;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.exotic.ExoticScroll;
import com.shatteredpixel.cursedpixeldungeon.items.stones.Runestone;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.plants.Plant;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;
import com.shatteredpixel.cursedpixeldungeon.windows.WndBag;

public class Recycle extends InventorySpell {
	
	{
		image = ItemSpriteSheet.RECYCLE;
		mode = WndBag.Mode.RECYCLABLE;
	}
	
	@Override
	protected void onItemSelected(Item item) {
		Item result;
		do {
			if (item instanceof Potion) {
				result = Generator.random(Generator.Category.POTION);
				if (item instanceof ExoticPotion){
					try {
						result = ExoticPotion.regToExo.get(result.getClass()).newInstance();
					} catch ( Exception e ){
						ShatteredPixelDungeon.reportException(e);
						result = item;
					}
				}
			} else if (item instanceof Scroll) {
				result = Generator.random(Generator.Category.SCROLL);
				if (item instanceof ExoticScroll){
					try {
						result = ExoticScroll.regToExo.get(result.getClass()).newInstance();
					} catch ( Exception e ){
						ShatteredPixelDungeon.reportException(e);
						result = item;
					}
				}
			} else if (item instanceof Plant.Seed) {
				result = Generator.random(Generator.Category.SEED);
			} else {
				result = Generator.random(Generator.Category.STONE);
			}
		} while (result.getClass() == item.getClass() || Challenges.isItemBlocked(result));
		
		item.detach(curUser.belongings.backpack);
		GLog.p(Messages.get(this, "recycled", result.name()));
		if (!result.collect()){
			Dungeon.level.drop(result, curUser.pos).sprite.drop();
		}
		//TODO visuals
	}
	
	public static boolean isRecyclable(Item item){
		return (item instanceof Potion && !(item instanceof Elixir || item instanceof Brew)) ||
				item instanceof Scroll ||
				item instanceof Plant.Seed ||
				item instanceof Runestone;
	}
	
	@Override
	public int price() {
		//prices of ingredients, divided by output quantity
		return Math.round(quantity * ((50 + 40) / 8f));
	}
	
	public static class Recipe extends com.shatteredpixel.cursedpixeldungeon.items.Recipe.SimpleRecipe {
		
		{
			inputs =  new Class[]{ScrollOfTransmutation.class, ArcaneCatalyst.class};
			inQuantity = new int[]{1, 1};
			
			cost = 6;
			
			output = Recycle.class;
			outQuantity = 8;
		}
		
	}
}
