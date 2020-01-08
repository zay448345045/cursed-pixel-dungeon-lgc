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

package com.shatteredpixel.cursedpixeldungeon.items.powers;

import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.scenes.AlchemyScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;
import com.shatteredpixel.cursedpixeldungeon.windows.WndBag;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class Alchemy extends Power {

	{
		image = ItemSpriteSheet.ARTIFACT_TOOLKIT;
		defaultAction = AC_BREW;
	}




	int exp = 0;

	public static final String AC_BREW = "BREW";
	
	protected WndBag.Mode mode = WndBag.Mode.POTION;

	Energy energy = new Energy();

	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add(AC_BREW);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action ) {

		super.execute(hero, action);

		if (action.equals(AC_BREW)){
			 if (cursed)                                                GLog.w( Messages.get(this, "cursed") );
			else if (hero.visibleEnemies() > hero.mindVisionEnemies.size()) GLog.i( Messages.get(this, "enemy_near") );
			else {
				
				AlchemyScene.setProvider(energy);
				Game.switchScene(AlchemyScene.class);
			}
			
		}
	}

	@Override
	public String desc() {
		String result = Messages.get(this, "desc");
		if (cursed)             result += "\n\n" + Messages.get(this, "desc_cursed");
		else                    result += "\n\n" + Messages.get(this, "desc_hint");
		
		return result;
	}

	
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
	}
	
	@Override
	public void restoreFromBundle(Bundle bundle) {
		energy.setToolkit(this);
		super.restoreFromBundle(bundle);
	}

	@Override
	public String status() {

		//if the artifact isn't IDed, or is cursed, don't display anything
		if (!isIdentified() || cursed){
			return null;
		}
		//display as percent
		if (chargeCap == 100)
			return Messages.format( "%d%%", charge );

		//display as #/#
		if (chargeCap > 0)
			return Messages.format( "%d/%d", charge, chargeCap );

		//if there's no cap -
		//- but there is charge anyway, display that charge
		if (charge != 0)
			return Messages.format( "%d", charge );

		//otherwise, if there's no charge, return null.
		return null;
	}

	@Override
	public void onHeroGainExp(float levelPercent, Hero hero) {
		this.charge += 1;
		this.charge = Math.min(this.charge,this.chargeCap);
		energy.setToolkit(this);
		updateQuickslot();
	}

	public class Energy extends PowerBuff implements AlchemyScene.AlchemyProvider {
		Alchemy Toolkit = null;

		public void setToolkit(Alchemy toolkit) {
			Toolkit = toolkit;
		}

		@Override
		public int getEnergy() {
			if (Toolkit == null) {
				return 0;
			} else {
				return charge;
			}
		}

		@Override
		public void spendEnergy(int reduction) {
			if (Toolkit != null) {
				charge = Math.max(0, charge - reduction);
			}

		}
	}
}
