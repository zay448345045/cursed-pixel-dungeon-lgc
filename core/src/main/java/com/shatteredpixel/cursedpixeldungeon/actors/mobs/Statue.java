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
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.items.Generator;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.Weapon.Enchantment;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.enchantments.Grim;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.cursedpixeldungeon.journal.Notes;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.sprites.StatueSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Statue extends Mob {
	
	{
		spriteClass = StatueSprite.class;

		EXP = 0;
		state = PASSIVE;
		
		properties.add(Property.INORGANIC);
	}
	
	protected Weapon weapon;
	
	public Statue() {
		super();
		
		do {
			weapon = (MeleeWeapon) Generator.randomWeapon();
		} while (weapon == null | weapon.cursed);
		weapon.upgrade(Random.NormalIntRange( Dungeon.scaleWithDepth()/4, Dungeon.scaleWithDepth()/2 ) + 1);
		
		weapon.enchant( Enchantment.random() );
		
		HP = HT = 30 + Dungeon.scaleWithDepth() * 10;
		defenseSkill = 4 + Dungeon.scaleWithDepth();
	}
	
	private static final String WEAPON	= "weapon";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( WEAPON, weapon );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		weapon = (Weapon)bundle.get( WEAPON );
	}
	
	@Override
	protected boolean act() {
		if (Dungeon.level.heroFOV[pos]) {
			Notes.add( Notes.Landmark.STATUE );
		}
		return super.act();
	}
	
	@Override
	public int damageRoll() {
		return weapon.damageRoll(this);
	}
	
	@Override
	public int attackSkill( Char target ) {
		return (int)((9 + Dungeon.depth) * weapon.accuracyFactor(this));
	}
	
	@Override
	protected float attackDelay() {
		return super.attackDelay()*weapon.speedFactor( this );
	}

	@Override
	protected boolean canAttack(Char enemy) {
		return super.canAttack(enemy) || weapon.canReach(this, enemy.pos);
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, Dungeon.depth + weapon.defenseFactor(this));
	}
	
	@Override
	public void damage( int dmg, Object src ) {

		if (state == PASSIVE) {
			state = HUNTING;
		}
		
		super.damage( dmg, src );
	}
	
	@Override
	public int attackProc( Char enemy, int damage ) {
		damage = super.attackProc( enemy, damage );
		return weapon.proc( this, enemy, damage );
	}
	
	@Override
	public void beckon( int cell ) {
		// Do nothing
	}
	
	@Override
	public void die( Object cause ) {
		weapon.identify();
		Dungeon.level.drop( weapon, pos ).sprite.drop();
		super.die( cause );
	}
	
	@Override
	public void destroy() {
		Notes.remove( Notes.Landmark.STATUE );
		super.destroy();
	}
	
	@Override
	public boolean reset() {
		state = PASSIVE;
		return true;
	}

	@Override
	public String description() {
		return Messages.get(this, "desc", weapon.name());
	}
	
	{
		resistances.add(Grim.class);
	}
	
}
