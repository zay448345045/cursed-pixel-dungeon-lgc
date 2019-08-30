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

package com.shatteredpixel.shatteredpixeldungeon.ui.changelist;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.items.Ankh;
import com.shatteredpixel.shatteredpixeldungeon.items.DewVial;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Embers;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfMagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.ChangesScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.AlbinoSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SwarmSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.Image;

import java.util.ArrayList;

public class Cruel_Changes {
	
	public static void addAllChanges( ArrayList<ChangeInfo> changeInfos ){
		add_v0_1_0_Changes(changeInfos);
	}

	public static void add_v0_1_0_Changes(ArrayList<ChangeInfo> changeInfos){
		ChangeInfo changes = new ChangeInfo( "0.1.0 - alpha", true, "");
		changes.hardlight(Window.TITLE_COLOR);
		changeInfos.add(changes);
		
		changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"),false,null);
		changes.hardlight( Window.TITLE_COLOR );
		changeInfos.add(changes);

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARTIFACT_SANDALS, null), "Tall Grass",
				"Tall grass can now drop Stones."));

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.RING_AGATE, null), "Misc Slots",
				"Misc slots have been reworked so that Wands must be equipped. To compensate, the player now has 4 slots."));

		changes = new ChangeInfo("buffs", false, null);
		changes.hardlight(Window.TITLE_COLOR);
		changeInfos.add(changes);

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARMOR_PLATE, null), "Curses Rebalanced",
				"Most curses have been changed to be more viable:\n" +
						"_-_ Metabolism now heals 5% of max HP instead of 4 dmg\n" +
						"_-_ Anti Entropy now freezes the enemy for longer. It's also more viable due to the 'Water' Dew Vial function\n" +
						"_-_ Bulk reduces all incoming damage by 25%\n" +
						"_-_ Corrosion now activates more often, with a 50% chance\n" +
						"_-_ Displacement now gives 5 turns of invisibility\n" +
						"_-_ Multiplicity has not been changed yet\n" +
						"_-_ Overgrowth has not been changed yet\n" +
						"_-_ Stench now gives 5 turns of gas immunity. This is not enough to take no damage from the gas, but it's enough to buy you some escape time."));

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.LONGSWORD, null), "Curses Rebalanced",
				"Most curses have been changed to be more viable:\n" +
						"_-_ Annoying curse now amoks enemies and procs more often\n" +
						"_-_ Displacing curse now puts enemies it teleports into a magical sleep\n" +
						"_-_ Fragile curse now inflicts paralysis depending on the amount it has degraded\n" +
						"_-_ Friendly curse now charms the enemy for longer than the player\n" +
						"_-_ Polarized curse now does 0 dmg or 2.2x dmg\n" +
						"_-_ Sacrificial curse now increases dmg depending on HP taken (imo still probably not viable, needs a bigger rework)\n" +
						"_-_ Wayward curse hs not been changed yet"));



		changes.addButton(new ChangeButton(Icons.get(Icons.WARNING), "Upgrade Limits",
				"All items in the game have been given upgrade limits:\n" +
						"_-_Most equipment is capped at +15.\n" +
						"_-_Wands can be upgraded to +20 by the Mage. Additionally, Battlemage can upgrade his staff to +25, but other wands are still capped at +20.\n" +
						"_-_Warrior, in turn, can upgrade armour higher. While most armour is capped at the regular level, if the Warrior has his Broken Seal attatched, the limit will be temporarily increased to +20. Warrior also can upgrade his Broken Seal to +5.\n" +
						"_-_Rogue can upgrade weapons more. They are capped at +20 for him and +15 for others\n" +
						"_-_Huntress can simply upgrade thrown weapons to +20 instead of +15. I may have to buff this in future, possibly to extend this bonus to the Ring of Sharpshooting as well."));

		changes.addButton( new ChangeButton(new Image(Assets.KING, 1, 0, 14, 16), "Bosses changed",
				"All bosses now drop 2-4 Scrolls of Upgrade, with an average of 2.5\n\n" +
						"All bosses have had their HP buffed to compensate for higher upgrade levels; Goo has 150 instead of 100, Tengu has 240 instead of 120, DM300 has 400 instead of 200, Dwarf King has 500 instead of 300 and Yog has 1500 instead of 300.\n\n" +
						"More extensive reworks are planned, especially for Dwarf King and Tengu."));

		changes.addButton( new ChangeButton( new Image(Assets.WARRIOR, 0, 90, 12, 15), "Starting Equipment",
				"Due to surviving the sewers being heavily dependant on RNG, all starting equipment has been buffed:\n\n" +
						"Huntress starts with +1 gloves and +1 Spirit Bow.\n\n" +
						"Mage starts with a +1 Staff of Disintegration\n\n" +
						"Warrior starts with an equipped Ring of Might and +1 Leather Armour, as well as his trusty Shortsword with a free upgrade\n\n" +
						"Rogue simply starts with his Dagger upgraded twice\n\n\n"+
						"Additionally, the player's inventory now has 24 slots, up from 20, and the Seed Pouch stores food as well"));

		changes.addButton( new ChangeButton(new WandOfMagicMissile(),
				"A few wand balance changes have been made:\n" +
						"_-_ Magic Missile now has doubled scaling, upgrading by +2/+5 instead of +1/+2\n" +
						"_-_ Wand of Frost scaling buffed\n" +
						"_-_ Wand of Blast Wave now inflicts Vertigo on targets\n" +
						"_-_ Wand of Living Earth has been implemented directly from 0.7.4 with existing sprites. HP of guardian increased scaling.\n" +
						"_-_ Transfusion self-shielding reduced, now charming scales with upgrades\n" +
						"_-_ Most wands have had scaling buffed by +1 max damage to be able to compete with weapons. For example, Lightning is equal to tier-6 scaling."));

		changes.addButton( new ChangeButton(new DewVial(),
				"The Dew Vial now has the 'Water' functionality, which allows the player to turn several surrounding tiles into water. It will consume up to 5 dew drops and will be more powerful the more Dew the player has."));

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.RING_EMERALD, null), "Ring Of Wealth",
				"A few changes have been made to the Ring of Wealth:\n" +
						"_-_ It no longer spawns naturally\n" +
						"_-_ A +10 ROW spawns in the first shop for about 800 Gold. Better save up!\n" +
						"_-_ It can be used to farm for Scrolls of Upgrade\n" +
						"_-_ Drop rates at higher levels significantly increased"));

		changes.addButton(new ChangeButton(Icons.get(Icons.SKULL), "Enemies Buffed",
				"Enemies have been buffed at all levels. The amount of extra HP that they have been given scales with depth. This is mainly due to more Scrolls Of Upgrade being availible and the player having higher HP.\n\n" +
						"_-_Sewers enemies are unchanged, Prision enemies have 50% more HP, Caves enemies 75% more and enemies in the Dwarven City and Demon Halls have double HP\n"+
						"_-_A new Demon Halls enemy has been added: the Goo. It is a version of the first boss, edited to be challenging. Better watch out!\n"+
						"_-_Evil Eye's Deathgaze can also now destroy all terrain!"));
	}
	
	
}
