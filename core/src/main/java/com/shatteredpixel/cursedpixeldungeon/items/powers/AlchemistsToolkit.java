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

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.LockedFloor;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.items.Recipe;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.scenes.AlchemyScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;
import com.shatteredpixel.cursedpixeldungeon.windows.WndBag;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;

import java.util.ArrayList;

public class AlchemistsToolkit extends Power {

	{
		image = ItemSpriteSheet.ARTIFACT_TOOLKIT;
		defaultAction = AC_BREW;
	}


	int charge = 0;
	float partialCharge = 0;
	int chargeCap = 100;

	int exp = 0;

	public static final String AC_BREW = "BREW";
	
	protected WndBag.Mode mode = WndBag.Mode.POTION;


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
				
				AlchemyScene.setProvider(hero.buff(Energy.class));
				Game.switchScene(AlchemyScene.class);
			}
			
		}
	}

	protected Energy passiveBuff() {
		return new Energy();
	}

	public void charge(Hero target) {
		if (charge < chargeCap){
			partialCharge += 0.5f;
			if (partialCharge >= 1){
				partialCharge--;
				charge++;
				updateQuickslot();
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
	
	public class Energy extends Buff implements AlchemyScene.AlchemyProvider {

		public int itemLevel() {
			return level();
		}

		public boolean isCursed() {
			return cursed;
		}

		public void gainCharge(float levelPortion) {

			if (cursed) return;

			if (charge < chargeCap) {

				//generates 2 energy every hero level, +0.1 energy per toolkit level
				//to a max of 12 energy per hero level
				//This means that energy absorbed into the kit is recovered in 6.67 hero levels (as 33% of input energy is kept)
				//exp towards toolkit levels is included here
				float effectiveLevel = GameMath.gate(0, level() + exp/10f, 10);
				partialCharge += (2 + (1f * effectiveLevel)) * levelPortion;

				//charge is in increments of 1/10 max hunger value.
				while (partialCharge >= 1) {
					charge++;
					partialCharge -= 1;

					if (charge == chargeCap){
						GLog.p( Messages.get(com.shatteredpixel.cursedpixeldungeon.items.artifacts.AlchemistsToolkit.class, "full") );
						partialCharge = 0;
					}

					updateQuickslot();
				}
			} else
				partialCharge = 0;
		}
		
		@Override
		public int getEnergy() {
			return charge;
		}
		
		@Override
		public void spendEnergy(int reduction) {
			charge = Math.max(0, charge - reduction);
		}
	}

	@Override
	public boolean doPickUp(Hero hero) {
		if (super.doPickUp(hero)){
			Buff.affect(hero, Energy.class);
			return true;
		}
		return false;
	}

	@Override
	protected void onDetach() {
		Energy spawner = Dungeon.hero.buff(Energy.class);
		if (spawner != null){
			spawner.detach();
		}
	}

}
