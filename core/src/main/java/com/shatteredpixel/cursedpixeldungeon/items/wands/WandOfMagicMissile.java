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

package com.shatteredpixel.cursedpixeldungeon.items.wands;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.cursedpixeldungeon.effects.SpellSprite;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.Weapon.Enchantment;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.cursedpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;

public class WandOfMagicMissile extends DamageWand {
	public Enchantment Enchantment = null;
	{
		image = ItemSpriteSheet.WAND_MAGIC_MISSILE;

	}

	public ItemSprite.Glowing glowing() {
		if (this.Enchantment != null) {
			return this.Enchantment.glowing();
		} else {
			return null;
		}

	}

	@Override
	public String name() {
		return Enchantment != null && (cursedKnown || !Enchantment.curse()) ? Enchantment.name( super.name() ) : super.name();
	}

	public WandOfMagicMissile enchant( Enchantment ench ) {
		if (ench == null || !ench.curse()) curseInfusionBonus = false;
		Enchantment = ench;
		updateQuickslot();
		return this;
	}

	public int min(int lvl){
		return 2+2*lvl;
	}

	public int max(int lvl){
		return 8+4*lvl;
	}
	
	@Override
	protected void onZap( Ballistica bolt ) {
				
		Char ch = Actor.findChar( bolt.collisionPos );
		int damage = damageRoll();
		if (ch != null) {
			damage = proc(this, Dungeon.hero, ch, damage);//Proc enchantment
			processSoulMark(ch, chargesPerCast());
			ch.damage(damage, this);

			ch.sprite.burst(0xFFFFFFFF, level() / 2 + 2);

		} else {
			Dungeon.level.press(bolt.collisionPos, null, true);
		}
	}

	@Override
	public void onHit(MagesStaff staff, Char attacker, Char defender, int damage) {
		Buff.prolong( attacker, Recharging.class, 1 + staff.level()/2f);
		SpellSprite.show(attacker, SpellSprite.CHARGE);

	}

	
	protected int initialCharges() {
		return 3;
	}

	public int proc(WandOfMagicMissile Wand, Char attacker, Char defender, int damage) {
		if (this.Enchantment != null) {
			return this.Enchantment.proc(Wand,attacker,defender,damage);
		} else {
			return damage;
		}
	}
}
