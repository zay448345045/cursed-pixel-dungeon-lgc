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

package com.shatteredpixel.cursedpixeldungeon.windows;

import com.shatteredpixel.cursedpixeldungeon.Assets;
import com.shatteredpixel.cursedpixeldungeon.Badges;
import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.GamesInProgress;
import com.shatteredpixel.cursedpixeldungeon.CPDSettings;
import com.shatteredpixel.cursedpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.cursedpixeldungeon.journal.Journal;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.shatteredpixel.cursedpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.cursedpixeldungeon.scenes.IntroScene;
import com.shatteredpixel.cursedpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.cursedpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.cursedpixeldungeon.ui.CheckBox;
import com.shatteredpixel.cursedpixeldungeon.ui.IconButton;
import com.shatteredpixel.cursedpixeldungeon.ui.Icons;
import com.shatteredpixel.cursedpixeldungeon.ui.RedButton;
import com.shatteredpixel.cursedpixeldungeon.ui.Window;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.ui.Button;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.DeviceCompat;

public class WndStartGame extends Window {
	
	private static final int WIDTH    = 120;
	private static final int HEIGHT   = 140;

	/*public WndStartGame(final int slot) {
		new WndStartGame(slot, false);
	}*/

	public WndStartGame(final int slot, final boolean testing){
		
		Badges.loadGlobal();
		Journal.loadGlobal();

		if (!testing) {
			CPDSettings.testing(false);
		}

		RenderedText title = PixelScene.renderText(Messages.get(this, "title"), 12 );
		title.hardlight(Window.TITLE_COLOR);
		title.x = (WIDTH - title.width())/2f;
		title.y = 2;
		add(title);

		float heroBtnSpacing = (WIDTH - 5*HeroBtn.WIDTH)/5f;
		
		float curX = heroBtnSpacing;
		for (HeroClass cl : HeroClass.values()){
			HeroBtn button = new HeroBtn(cl);
			button.setRect(curX, title.baseLine() + 4, HeroBtn.WIDTH, HeroBtn.HEIGHT);
			curX += HeroBtn.WIDTH + heroBtnSpacing;
			add(button);
		}
		
		ColorBlock separator = new ColorBlock(1, 1, 0xFF222222);
		separator.size(WIDTH, 1);
		separator.x = 0;
		separator.y = title.baseLine() + 6 + HeroBtn.HEIGHT;
		add(separator);
		
		HeroPane ava = new HeroPane();
		ava.setRect(20, separator.y + 2, WIDTH-30, 80);
		add(ava);
		
		RedButton start = new RedButton(Messages.get(this, "start")){
			@Override
			protected void onClick() {
				if (GamesInProgress.selectedClass == null) return;
				
				super.onClick();
				
				GamesInProgress.curSlot = slot;
				Dungeon.hero = null;
				ActionIndicator.action = null;
				InterlevelScene.mode = InterlevelScene.Mode.DESCEND_GAMEINIT;
				InterlevelScene.testing = testing;
				if (CPDSettings.intro()) {
					CPDSettings.intro( false );
					Game.switchScene( IntroScene.class );
				} else {
					Game.switchScene( InterlevelScene.class );
				}
			}
			
			@Override
			public void update() {
				if( !visible && GamesInProgress.selectedClass != null){
					visible = true;
				}
				super.update();
			}
		};
		start.visible = false;
		start.setRect(0, HEIGHT - 20, WIDTH, 20);
		add(start);
		
		if (DeviceCompat.isDebug() || Badges.isUnlocked(Badges.Badge.VICTORY)){
			IconButton challengeButton = new IconButton(
					Icons.get( CPDSettings.challenges() > 0 ? Icons.CHALLENGE_ON :Icons.CHALLENGE_OFF)){
				@Override
				protected void onClick() {
					ShatteredPixelDungeon.scene().add(new WndChallenges(CPDSettings.challenges(), true) {
						public void onBackPressed() {
							super.onBackPressed();
							icon( Icons.get( CPDSettings.challenges() > 0 ?
									Icons.CHALLENGE_ON :Icons.CHALLENGE_OFF ) );
						}
					} );
				}
				
				@Override
				public void update() {
					if( !visible && GamesInProgress.selectedClass != null){
						visible = true;
					}
					super.update();
				}
			};
			challengeButton.setRect(WIDTH - 20, HEIGHT - 20, 20, 20);
			challengeButton.visible = false;
			add(challengeButton);
			
		} else {
			Dungeon.challenges = 0;
			CPDSettings.challenges(0);
		}
		int bonus = 0;

		if (testing) {
			bonus = 20;
			final CheckBox BoxTesting = new CheckBox("TEST MODE"){
				@Override
				protected void onClick() {
					super.onClick();
					CPDSettings.testing(checked());
					GameScene.layoutTags();
				}
			};
			BoxTesting.setRect(0, HEIGHT , WIDTH, 20);
			BoxTesting.checked(CPDSettings.testing());
			add(BoxTesting);
		}
		
		resize(WIDTH, HEIGHT + bonus);
		
	}
	
	private static class HeroBtn extends Button {
		
		private HeroClass cl;
		
		private Image hero;
		
		private static final int WIDTH = 20;
		private static final int HEIGHT = 16;
		
		HeroBtn ( HeroClass cl ){
			super();
			
			this.cl = cl;
			
			if (cl == HeroClass.WARRIOR){
				hero = new Image(Assets.WARRIOR, 0, 90, 12, 15);
			} else if (cl == HeroClass.MAGE){
				hero = new Image(Assets.MAGE, 0, 90, 12, 15);
			} else if (cl == HeroClass.ROGUE){
				hero = new Image(Assets.ROGUE, 0, 90, 12, 15);
			} else if (cl == HeroClass.HUNTRESS){
				hero = new Image(Assets.HUNTRESS, 0, 90, 12, 15);
			} else if (cl == HeroClass.PRIESTESS) {
				hero = new Image(Assets.PRIESTESS, 0, 90, 12, 15);
			}
			add(hero);
		}
		
		@Override
		protected void layout() {
			super.layout();
			if (hero != null){
				hero.x = x + (width - hero.width()) / 2f;
				hero.y = y + (height - hero.height()) / 2f;
				PixelScene.align(hero);
			}
		}
		
		@Override
		public void update() {
			super.update();
			if (cl != GamesInProgress.selectedClass){
				if (!cl.isUnlocked()){
					hero.brightness(0.3f);
				} else {
					hero.brightness(0.6f);
				}
			} else {
				hero.brightness(1f);
			}
		}
		
		@Override
		protected void onClick() {
			super.onClick();
			
			if( !cl.isUnlocked() ){
				ShatteredPixelDungeon.scene().add(
						new WndMessage(cl.unlockMsg()));
			} else {
				GamesInProgress.selectedClass = cl;
			}
		}
	}
	
	private class HeroPane extends Component {
		
		private HeroClass cl;
		
		private Image avatar;
		
		private IconButton heroItem;
		private IconButton heroLoadout;
		private IconButton heroMisc;
		private IconButton heroSubclass;
		
		private RenderedText name;
		
		private static final int BTN_SIZE = 20;
		
		@Override
		protected void createChildren() {
			super.createChildren();
			
			avatar = new Image(Assets.AVATARS);
			avatar.scale.set(2f);
			add(avatar);
			
			heroItem = new IconButton(){
				@Override
				protected void onClick() {
					if (cl == null) return;
					ShatteredPixelDungeon.scene().add(new WndMessage(Messages.get(cl, cl.name() + "_desc_item")));
				}
			};
			heroItem.setSize(BTN_SIZE, BTN_SIZE);
			add(heroItem);
			
			heroLoadout = new IconButton(){
				@Override
				protected void onClick() {
					if (cl == null) return;
					ShatteredPixelDungeon.scene().add(new WndMessage(Messages.get(cl, cl.name() + "_desc_loadout")));
				}
			};
			heroLoadout.setSize(BTN_SIZE, BTN_SIZE);
			add(heroLoadout);
			
			heroMisc = new IconButton(){
				@Override
				protected void onClick() {
					if (cl == null) return;
					ShatteredPixelDungeon.scene().add(new WndMessage(Messages.get(cl, cl.name() + "_desc_misc")));
				}
			};
			heroMisc.setSize(BTN_SIZE, BTN_SIZE);
			add(heroMisc);
			
			heroSubclass = new IconButton(new ItemSprite(ItemSpriteSheet.MASTERY, null)){
				@Override
				protected void onClick() {
					if (cl == null) return;
					String msg = Messages.get(cl, cl.name() + "_desc_subclasses");
					for (HeroSubClass sub : cl.subClasses()){
						msg += "\n\n" + sub.desc();
					}
					ShatteredPixelDungeon.scene().add(new WndMessage(msg));
				}
			};
			heroSubclass.setSize(BTN_SIZE, BTN_SIZE);
			add(heroSubclass);
			
			name = PixelScene.renderText(12);
			add(name);
			
			visible = false;
		}
		
		@Override
		protected void layout() {
			super.layout();
			
			avatar.x = x;
			avatar.y = y + (height - avatar.height() - name.baseLine() - 2)/2f;
			PixelScene.align(avatar);
			
			name.x = x + (avatar.width() - name.width())/2f;
			name.y = avatar.y + avatar.height() + 2;
			PixelScene.align(name);
			
			heroItem.setPos(x + width - BTN_SIZE, y);
			heroLoadout.setPos(x + width - BTN_SIZE, heroItem.bottom());
			heroMisc.setPos(x + width - BTN_SIZE, heroLoadout.bottom());
			heroSubclass.setPos(x + width - BTN_SIZE, heroMisc.bottom());
		}
		
		@Override
		public synchronized void update() {
			super.update();
			if (GamesInProgress.selectedClass != cl){
				cl = GamesInProgress.selectedClass;
				if (cl != null) {
					avatar.frame(cl.ordinal() * 24, 0, 24, 32);
					
					name.text(Messages.capitalize(cl.title()));
					
					switch(cl){
						case WARRIOR:
							heroItem.icon(new ItemSprite(ItemSpriteSheet.SEAL, null));
							heroLoadout.icon(new ItemSprite(ItemSpriteSheet.WORN_SHORTSWORD, null));
							heroMisc.icon(new ItemSprite(ItemSpriteSheet.RATION, null));
							break;
						case MAGE:
							heroItem.icon(new ItemSprite(ItemSpriteSheet.MAGES_STAFF, null));
							heroLoadout.icon(new ItemSprite(ItemSpriteSheet.HOLDER, null));
							heroMisc.icon(new ItemSprite(ItemSpriteSheet.WAND_MAGIC_MISSILE, null));
							break;
						case ROGUE:
							heroItem.icon(new ItemSprite(ItemSpriteSheet.ARTIFACT_CLOAK, null));
							heroLoadout.icon(new ItemSprite(ItemSpriteSheet.DAGGER, null));
							heroMisc.icon(Icons.get(Icons.DEPTH));
							break;
						case HUNTRESS:
							heroItem.icon(new ItemSprite(ItemSpriteSheet.SPIRIT_BOW, null));
							heroLoadout.icon(new ItemSprite(ItemSpriteSheet.GLOVES, null));
							heroMisc.icon(new Image(Assets.TILES_SEWERS, 112, 96, 16, 16 ));
							break;
						case PRIESTESS:
							heroItem.icon(new ItemSprite(ItemSpriteSheet.ADORNEDDRAGONCRYSTAL, null));
							heroLoadout.icon(new ItemSprite(ItemSpriteSheet.INSCRIBED_KINFE, null));
							heroMisc.icon(new Image(Assets.RAT,0,0,16,15));
							break;
					}
					
					layout();
					
					visible = true;
				} else {
					visible = false;
				}
			}
		}
	}

}
