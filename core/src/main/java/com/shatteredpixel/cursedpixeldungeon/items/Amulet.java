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

package com.shatteredpixel.cursedpixeldungeon.items;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.scenes.AmuletScene;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.shatteredpixel.cursedpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.cursedpixeldungeon.windows.WndBag;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

import java.io.IOException;
import java.util.ArrayList;

public class Amulet extends Item {
	
	private static final String AC_END = "END";
	private static final String AC_EMBED = "embed";
	public boolean WATER_IMBEDDED = false;
	public final String WATERSECTOR = "WATERSECTOR";
	
	{
		image = ItemSpriteSheet.AMULET;
		
		unique = true;
	}
	
	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_END );
		actions.add( AC_EMBED );
		return actions;
	}

	@Override
	public ItemSprite getSprite() {
		return new AmuletSprite(WATER_IMBEDDED, false, false, false);
	}

	public void imbed(String ImbedType) {
		if (ImbedType.equals(WATERSECTOR)) {
			WATER_IMBEDDED = true;
		}
	}
	public final String WATER_IMBED = "water_imbedded";
	@Override
	public void storeInBundle(Bundle bundle) {
		bundle.put(WATER_IMBED, WATER_IMBEDDED);
		super.storeInBundle(bundle);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		WATER_IMBEDDED = bundle.getBoolean(WATER_IMBED);
		super.restoreFromBundle(bundle);
	}

	@Override
	public void execute( Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals(AC_END)) {
			InterlevelScene.mode = InterlevelScene.Mode.START;
			Game.switchScene(InterlevelScene.class);
		} else if (action.equals(AC_EMBED)) {
			curItem = this;
			GameScene.selectItem(amuletSelector, WndBag.Mode.IMBED, Messages.get(this, "prompt"));
		}
	}

	protected static WndBag.Listener amuletSelector = new WndBag.Listener() {
		@Override
		public void onSelect( Item item ) {
			if (item instanceof AmuletSectorWater) {
				item.detach(Dungeon.hero.belongings.backpack);
				((Amulet)curItem).imbed(((Amulet)curItem).WATERSECTOR);
			}
		}
	};
	
	private void showAmuletScene( boolean showText ) {
		try {
			Dungeon.saveAll();
			AmuletScene.noText = !showText;
			Game.switchScene( AmuletScene.class );
		} catch (IOException e) {
			ShatteredPixelDungeon.reportException(e);
		}
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}

	public static class AmuletSprite extends ItemSprite {

		private Image fullAmulet() {
			return new ItemSprite(ItemSpriteSheet.AMULET);
		}
		private Image emptyAmulet() {
			return new ItemSprite(ItemSpriteSheet.AMULET_EMPTY);
		}

		private Image topLeftSprite;
		private Image bottomLeftSprite;
		private Image topRightSprite;
		private Image bottomRightSprite;

		AmuletSprite(boolean topLeft, boolean bottomLeft, boolean topRight, boolean bottomRight) {
			if (topLeft) {
				topLeftSprite = fullAmulet();
			} else {
				topLeftSprite = emptyAmulet();
			}
			topLeftSprite.frame(0, 0, 8, 8);

			if (bottomLeft) {
				bottomLeftSprite = fullAmulet();
			} else {
				bottomLeftSprite = emptyAmulet();
			}
			bottomLeftSprite.frame(0, 8, 8, 8);

			if (topRight) {
				topRightSprite = fullAmulet();
			} else {
				topRightSprite = emptyAmulet();
			}
			topRightSprite.frame(8, 0, 8, 8);

			if (bottomRight) {
				bottomRightSprite = fullAmulet();
			} else {
				bottomRightSprite = emptyAmulet();
			}
			bottomRightSprite.frame(8, 8, 8, 8);
		}

		@Override
		public synchronized void update() {
			super.update();
			topLeftSprite.x = 0;
			topLeftSprite.y = 16;
			topLeftSprite.visible = true;

			bottomLeftSprite.x = 0;
			bottomLeftSprite.y = 8;
			bottomLeftSprite.visible = true;

			topRightSprite.x = 8;
			topRightSprite.y = 16;
			topRightSprite.visible = true;

			bottomRightSprite.x = 8;
			bottomRightSprite.y = 8;
			bottomRightSprite.visible = true;
		}

		@Override
		public void link() {}
	}

}
