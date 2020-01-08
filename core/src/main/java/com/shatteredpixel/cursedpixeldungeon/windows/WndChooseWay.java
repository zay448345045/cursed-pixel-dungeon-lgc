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

import com.shatteredpixel.cursedpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.cursedpixeldungeon.items.TomeOfMastery;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.shatteredpixel.cursedpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.cursedpixeldungeon.ui.RedButton;
import com.shatteredpixel.cursedpixeldungeon.ui.RenderedTextMultiline;
import com.shatteredpixel.cursedpixeldungeon.ui.Window;
import com.watabou.noosa.Game;

import java.io.IOException;
import java.util.ArrayList;

public class WndChooseWay extends Window {
	
	private static final int WIDTH		= 120;
	private static final int BTN_HEIGHT	= 18;
	private static final float GAP		= 2;
	
	public WndChooseWay(final TomeOfMastery tome, final HeroClass cl) {
		
		super();
		final ArrayList<HeroSubClass> subClasses = HeroClass.avalibleSubClasses(cl);
		ArrayList<RedButton> subClassButtons = new ArrayList<>();
		if (subClasses.size() < 1) {
			return;
		}
		IconTitle titlebar = new IconTitle();
		titlebar.icon( new ItemSprite( tome.image(), null ) );
		titlebar.label( tome.name() );
		titlebar.setRect( 0, 0, WIDTH, 0 );
		add( titlebar );

		RenderedTextMultiline hl = PixelScene.renderMultiline( 6 );
		hl.text(  Messages.get(this, "message"), WIDTH );
		hl.setPos( titlebar.left(), titlebar.bottom() + GAP );
		add( hl );
		int extra = 0;
		for (HeroSubClass subClass : subClasses) {
			final HeroSubClass SubClass = subClass;
			RedButton btnWay = new RedButton( SubClass.title().toUpperCase() ) {
				@Override
				protected void onClick() {
					hide();
					tome.choose( SubClass );
				}
			};
			RedButton btnDesc = new RedButton(Messages.get(this, "info")) {
				@Override
				protected void onClick() {
					Game.scene().addToFront(new WndTitledMessage( new ItemSprite( tome.image(), null ), SubClass.title().toUpperCase(), SubClass.desc()));
				}
			};
			subClassButtons.add(btnWay);

			btnWay.setRect( 0, hl.bottom() + GAP + extra, (WIDTH - GAP)*0.67f, BTN_HEIGHT );
			add( btnWay );
			btnDesc.setRect( (WIDTH - GAP)*0.67f, hl.bottom() + GAP + extra, (WIDTH - GAP)*0.33f, BTN_HEIGHT );
			add( btnDesc );
			extra += BTN_HEIGHT;
		}

		/*RedButton btnWay1 = new RedButton( way1.title().toUpperCase() ) {
			@Override
			protected void onClick() {
				hide();
				tome.choose( way1 );
			}
		};
		btnWay1.setRect( 0, hl.bottom() + GAP, (WIDTH - GAP) / 2, BTN_HEIGHT );
		add( btnWay1 );
		
		RedButton btnWay2 = new RedButton( way2.title().toUpperCase() ) {
			@Override
			protected void onClick() {
				hide();
				tome.choose( way2 );
			}
		};
		btnWay2.setRect( btnWay1.right() + GAP, btnWay1.top(), btnWay1.width(), BTN_HEIGHT );
		add( btnWay2 );*/

		RedButton btnCancel = new RedButton( Messages.get(this, "cancel") ) {
			@Override
			protected void onClick() {
				hide();
			}
		};
		btnCancel.setRect( 0, subClassButtons.get(subClassButtons.size()-1).bottom() + GAP, WIDTH, BTN_HEIGHT );
		add( btnCancel );
		
		resize( WIDTH, (int)btnCancel.bottom() );
	}
}
