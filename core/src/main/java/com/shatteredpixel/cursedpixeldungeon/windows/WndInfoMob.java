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

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.CPDSettings;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Resurrection;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.cursedpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.cursedpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.cursedpixeldungeon.ui.HealthBar;
import com.shatteredpixel.cursedpixeldungeon.ui.RedButton;
import com.shatteredpixel.cursedpixeldungeon.ui.RenderedTextMultiline;
import com.shatteredpixel.cursedpixeldungeon.ui.Window;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.ui.Component;

public class WndInfoMob extends Window {
	protected static final int WIDTH_P    = 120;
	protected static final int WIDTH_L    = 200;
	protected static final int GAP	= 2;
	private static final int BTN_HEIGHT = 15;
	public WndInfoMob( final Mob mob ) {
		int width = CPDSettings.landscape() ? WIDTH_L : WIDTH_P;
		Component titlebar = new MobTitle( mob );
		titlebar.setRect( 0, 0, width, 0 );
		add(titlebar);
		String msg = mob.description();

		RenderedTextMultiline message = PixelScene.renderMultiline(msg, 6);
		message.maxWidth(width);
		message.setPos(0, titlebar.bottom() + GAP);
		add(message);
		int bottom = (int) message.bottom();
		if (mob.alignment == Char.Alignment.ALLY && Dungeon.hero.heroClass == HeroClass.PRIESTESS || mob instanceof DriedRose.GhostHero) {//Only Priestess can see these buttons, but the Sad Ghost always has them.
			RedButton btnWander = new RedButton(Messages.get(mob, "wander")) {
				@Override
				protected void onClick() {
					mob.wanderRandom();
					onBackPressed();
				}
			};
			btnWander.setRect(0, message.top() + message.height() + GAP, width, BTN_HEIGHT);
			add(btnWander);

			RedButton btnFollow = new RedButton(Messages.get(mob, "follow")) {
				@Override
				protected void onClick() {
					mob.followHero();
					onBackPressed();
				}
			};
			btnFollow.setRect(0, message.top() + message.height() + GAP + BTN_HEIGHT, width, BTN_HEIGHT);
			add(btnFollow);
			bottom = (int) btnFollow.bottom();
		}
		if (mob.alignment == Char.Alignment.ALLY && Dungeon.hero.subClass == HeroSubClass.NECROMANCER) {
			String title = Messages.get(mob, "enable_resurrection");
			final Buff resurrection = mob.buff(Resurrection.class);
			final boolean canResurrect =  resurrection != null;
			if (canResurrect) {
				title = Messages.get(mob, "disable_resurrection");
			}
			RedButton btnResurrect = new RedButton(title) {
				@Override
				protected void onClick() {
					if (!canResurrect) {
						Buff.affect(mob, Resurrection.class);
					} else {
						resurrection.detach();
					}
					onBackPressed();
				}
			};
			btnResurrect.setRect(0, bottom, width, BTN_HEIGHT);
			add(btnResurrect);
			bottom = (int) btnResurrect.bottom();
		}
		resize(width, bottom);
		//super( new MobTitle( mob ), mob.description()  );
		
	}
	
	private static class MobTitle extends Component {

		private static final int GAP	= 2;
		
		private CharSprite image;
		private RenderedText name;
		private HealthBar health;
		private BuffIndicator buffs;
		
		public MobTitle( Mob mob ) {
			
			name = PixelScene.renderText( Messages.titleCase( mob.name ), 9 );
			name.hardlight( TITLE_COLOR );
			add( name );
			
			image = mob.sprite();
			add( image );

			health = new HealthBar();
			health.level(mob);
			add( health );

			buffs = new BuffIndicator( mob );
			add( buffs );
		}
		
		@Override
		protected void layout() {
			
			image.x = 0;
			image.y = Math.max( 0, name.height() + health.height() - image.height );

			name.x = image.width + GAP;
			name.y = Math.max( 0, image.height - health.height() - name.height());

			float w = width - image.width - GAP;

			health.setRect(image.width + GAP, name.y + name.height(), w, health.height());

			buffs.setPos(
				name.x + name.width() + GAP-1,
				name.y + name.baseLine() - BuffIndicator.SIZE-2 );

			height = health.bottom();
		}
	}
}
