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

package com.shatteredpixel.cursedpixeldungeon.items.scrolls;

import com.shatteredpixel.cursedpixeldungeon.Assets;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.effects.Flare;
import com.shatteredpixel.cursedpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.cursedpixeldungeon.items.EquipableItem;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.items.armor.Armor;
import com.shatteredpixel.cursedpixeldungeon.items.wands.Wand;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;
import com.shatteredpixel.cursedpixeldungeon.windows.WndBag;
import com.watabou.noosa.audio.Sample;

public class ScrollOfRemoveCurse extends InventoryScroll {

	{
		initials = 7;
		mode = WndBag.Mode.UNCURSABLE;
		mp_cost = 5;
	}
	
	@Override
	public void empoweredRead() {
		for (Item item : curUser.belongings){
			if (item.cursed){
				item.cursedKnown = true;
			}
		}
		Sample.INSTANCE.play( Assets.SND_READ );
		Invisibility.dispel();
		doRead();
	}
	
	@Override
	protected void onItemSelected(Item item) {
		new Flare( 6, 32 ).show( curUser.sprite, 2f ) ;

		boolean procced = uncurse( curUser, item );

		Weakness.detach( curUser, Weakness.class );

		if (procced) {
			GLog.p( Messages.get(this, "cleansed") );
		} else {
			GLog.i( Messages.get(this, "not_cleansed") );
		}
	}

	public static boolean uncurse( Hero hero, Item... items ) {
		
		boolean procced = false;
		for (Item item : items) {
			if (item != null) {
				item.cursedKnown = true;
				if (item.cursed) {
					procced = true;
					item.cursed = false;
				}
			}
			if (item instanceof Weapon){
				Weapon w = (Weapon) item;
				if (w.hasCurseEnchant()){
					w.enchant(null);
					procced = true;
				}
			}
			if (item instanceof Armor){
				Armor a = (Armor) item;
				if (a.hasCurseGlyph()){
					a.inscribe(null);
					procced = true;
				}
			}
			if (item instanceof Wand){
				((Wand) item).updateLevel();
			}
		}
		
		if (procced) {
			hero.sprite.emitter().start( ShadowParticle.UP, 0.05f, 10 );
			hero.updateHT( false ); //for ring of might
			updateQuickslot();
		}
		
		return procced;
	}
	
	public static boolean uncursable( Item item ){
		if ((item instanceof EquipableItem || item instanceof Wand) && (!item.isIdentified() || item.cursed)){
			return true;
		} else if (item instanceof Weapon){
			return ((Weapon)item).hasCurseEnchant();
		} else if (item instanceof Armor){
			return ((Armor)item).hasCurseGlyph();
		} else {
			return false;
		}
	}
	
	@Override
	public int price() {
		return isKnown() ? 30 * quantity : super.price();
	}
}
