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

package com.shatteredpixel.cursedpixeldungeon.items.weapon.curses;

import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.enchantments.Grim;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

public class Sacrificial extends Weapon.Enchantment {

	private static ItemSprite.Glowing BLACK = new ItemSprite.Glowing( 0x000000 );

	@Override
	public int proc(Item weapon, Char attacker, Char defender, int damage ) {

		/*if (Random.Int(12) == 0){
			int duration = Math.max(1, attacker.HP/6);
			Buff.affect(attacker, Bleeding.class).set(duration);
			damage = Math.round((duration*2/attacker.HT)*damage);//Increases damage based on HP taken
		}
		*/
		if (Random.Int(5) == 0) {
			int procDMG = defender.HP;
			if (Math.round(procDMG/2f) >= attacker.HP) {//Use Math.round rather than integer division so that odd damage doesn't have a small chance of killing the player
				procDMG = (attacker.HP - 1)*2;
			}
			if (procDMG > 0) {
				GLog.n(Messages.get(this,"proc"));
				attacker.damage(procDMG / 2, this);
				defender.damage(procDMG, new Grim());
			}
		}
		return damage;
	}

	@Override
	public boolean curse() {
		return true;
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return BLACK;
	}

}
