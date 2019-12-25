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

package com.shatteredpixel.cursedpixeldungeon.items.rings;

import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.blobs.Electricity;
import com.shatteredpixel.cursedpixeldungeon.actors.blobs.ToxicGas;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Corrosion;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Frost;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Ooze;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Eye;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.MageCrab;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Shaman;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Warlock;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.WaterElemental;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Yog;
import com.shatteredpixel.cursedpixeldungeon.levels.traps.DisintegrationTrap;
import com.shatteredpixel.cursedpixeldungeon.levels.traps.GrimTrap;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;

import java.text.DecimalFormat;
import java.util.HashSet;

public class RingOfElements extends Ring {
	
	public String statsInfo() {
		if (isIdentified()){
			return Messages.get(this, "stats", new DecimalFormat("#.##").format(100f * (0.03125 * soloBonus())));
		} else {
			return Messages.get(this, "typical_stats", new DecimalFormat("#.##").format(03f));
		}
	}
	
	@Override
	protected RingBuff buff( ) {
		return new Resistance();
	}
	
	public static final HashSet<Class> RESISTS = new HashSet<>();
	static {
		RESISTS.add( Burning.class );
		RESISTS.add( Charm.class );
		RESISTS.add( Chill.class );
		RESISTS.add( Frost.class );
		RESISTS.add( Ooze.class );
		RESISTS.add( Paralysis.class );
		RESISTS.add( Poison.class );
		RESISTS.add( Corrosion.class );
		RESISTS.add( Weakness.class );
		
		RESISTS.add( DisintegrationTrap.class );
		RESISTS.add( GrimTrap.class );
		
		RESISTS.add( ToxicGas.class );
		RESISTS.add( Electricity.class );
		
		RESISTS.add( Shaman.LightningBolt.class );
		RESISTS.add( Warlock.DarkBolt.class );
		RESISTS.add( Eye.DeathGaze.class );
		RESISTS.add( Yog.BurningFist.DarkBolt.class );
		RESISTS.add( WaterElemental.ElementalIce.class );
		RESISTS.add( MageCrab.MageCrabIce.class );
	}
	
	public static float resist( Char target, Class effect ){
		if (getBonus(target, Resistance.class) == 0) return 1f;
		
		for (Class c : RESISTS){
			if (c.isAssignableFrom(effect)){
				float bonus = (float) Math.min(16, getBonus(target, Resistance.class));
				return (float) (1 - 0.03125 * bonus);
			}
		}
		
		return 1f;
	}
	
	public class Resistance extends RingBuff {
	
	}
}
