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

package com.smujamesb.cursedpixeldungeon.actors.blobs;

import com.smujamesb.cursedpixeldungeon.Assets;
import com.smujamesb.cursedpixeldungeon.Dungeon;
import com.smujamesb.cursedpixeldungeon.actors.buffs.Hunger;
import com.smujamesb.cursedpixeldungeon.actors.hero.Hero;
import com.smujamesb.cursedpixeldungeon.effects.BlobEmitter;
import com.smujamesb.cursedpixeldungeon.effects.CellEmitter;
import com.smujamesb.cursedpixeldungeon.effects.Speck;
import com.smujamesb.cursedpixeldungeon.effects.particles.ShaftParticle;
import com.smujamesb.cursedpixeldungeon.items.DewVial;
import com.smujamesb.cursedpixeldungeon.items.Item;
import com.smujamesb.cursedpixeldungeon.items.potions.PotionOfHealing;
import com.smujamesb.cursedpixeldungeon.journal.Notes.Landmark;
import com.smujamesb.cursedpixeldungeon.messages.Messages;
import com.smujamesb.cursedpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class WaterOfHealth extends WellWater {
	
	@Override
	protected boolean affectHero( Hero hero ) {
		
		if (!hero.isAlive()) return false;
		
		Sample.INSTANCE.play( Assets.SND_DRINK );

		hero.HP = hero.HT;
		hero.sprite.emitter().start( Speck.factory( Speck.HEALING ), 0.4f, 4 );

		PotionOfHealing.cure( hero );
		hero.belongings.uncurseEquipped();
		((Hunger)hero.buff( Hunger.class )).satisfy( Hunger.STARVING );
		
		CellEmitter.get( hero.pos ).start( ShaftParticle.FACTORY, 0.2f, 3 );

		Dungeon.hero.interrupt();
	
		GLog.p( Messages.get(this, "procced") );
		
		return true;
	}
	
	@Override
	protected Item affectItem( Item item, int pos ) {
		if (item instanceof DewVial && !((DewVial)item).isFull()) {
			((DewVial)item).fill();
			return item;
		}
		
		return null;
	}
	
	@Override
	protected Landmark record() {
		return Landmark.WELL_OF_HEALTH;
	}
	
	@Override
	public void use( BlobEmitter emitter ) {
		super.use( emitter );
		emitter.start( Speck.factory( Speck.HEALING ), 0.5f, 0 );
	}
	
	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}
