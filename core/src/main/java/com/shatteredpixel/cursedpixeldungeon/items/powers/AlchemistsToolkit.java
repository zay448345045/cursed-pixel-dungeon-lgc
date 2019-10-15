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
import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.LockedFloor;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.items.Recipe;
import com.shatteredpixel.cursedpixeldungeon.items.allies.DragonCrystal;
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
	@Override
	protected PowerBuff passiveBuff() {
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
	
	public static class Energy extends PowerBuff implements AlchemyScene.AlchemyProvider {

		@Override
		public boolean act() {
			if (Dungeon.hero != null) {
				AlchemistsToolkit Toolkit = Dungeon.hero.belongings.getItem(AlchemistsToolkit.class);
				if (Toolkit != null) {
					spend(TICK);

					LockedFloor lock = target.buff(LockedFloor.class);
					if (Toolkit.charge < Toolkit.chargeCap && !Toolkit.cursed && (lock == null || lock.regenOn())) {
						Toolkit.partialCharge += 0.1;

						if (Toolkit.partialCharge >= 1) {
							Toolkit.partialCharge--;
							Toolkit.charge++;

							if (Toolkit.charge == Toolkit.chargeCap) {
								Toolkit.partialCharge = 0;
							}
						}
					}

					updateQuickslot();
				}
			}

			return true;
		}
		
		@Override
		public int getEnergy() {
			if (Dungeon.hero != null) {
				AlchemistsToolkit Toolkit = Dungeon.hero.belongings.getItem(AlchemistsToolkit.class);
				if (Toolkit != null) {
					return Toolkit.charge;

				} else {
					return 0;
				}
			} else {
				return 0;
			}
		}

		@Override
		public void spendEnergy(int reduction) {
			if (Dungeon.hero != null) {
				AlchemistsToolkit Toolkit = Dungeon.hero.belongings.getItem(AlchemistsToolkit.class);
				if (Toolkit != null) {
					Toolkit.charge = Math.max(0, Toolkit.charge - reduction);

				}
			}
		}
	}

}
