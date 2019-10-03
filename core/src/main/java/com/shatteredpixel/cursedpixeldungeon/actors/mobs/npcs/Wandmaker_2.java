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

package com.shatteredpixel.cursedpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.shatteredpixel.cursedpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.cursedpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.WandmakerSprite;
import com.shatteredpixel.cursedpixeldungeon.ui.RedButton;
import com.shatteredpixel.cursedpixeldungeon.ui.RenderedTextMultiline;
import com.shatteredpixel.cursedpixeldungeon.ui.Window;
import com.shatteredpixel.cursedpixeldungeon.windows.IconTitle;
import com.shatteredpixel.cursedpixeldungeon.windows.WndBag;
import com.watabou.noosa.Game;

public class Wandmaker_2 extends NPC {

    {
        spriteClass = WandmakerSprite.class;

        properties.add(Property.IMMOVABLE);
    }
    @Override
    protected boolean act() {
        throwItem();
        return super.act();
    }

    @Override
    public boolean interact() {
        GameScene.show( new WndWandmaker_2( this ) );
        return false;
    }

    @Override
    public int defenseSkill( Char enemy ) {
        return 1000;
    }

    @Override
    public void damage( int dmg, Object src ) {
    }

    @Override
    public void add( Buff buff ) {
    }

    @Override
    public boolean reset() {
        return true;
    }

    public class WndWandmaker_2 extends Window {

        private static final int WIDTH = 120;
        private static final int BTN_HEIGHT = 20;
        private static final float GAP = 2;

        public WndWandmaker_2(final Wandmaker_2 wandmaker_2) {

            super();

            IconTitle titlebar = new IconTitle();
            titlebar.icon(wandmaker_2.sprite());
            titlebar.label(Messages.titleCase(wandmaker_2.name));
            titlebar.setRect(0, 0, WIDTH, 0);
            add(titlebar);

            String msg = "";
            msg = Messages.get(wandmaker_2, "chat",Dungeon.hero.givenName());

            RenderedTextMultiline message = PixelScene.renderMultiline(msg, 6);
            message.maxWidth(WIDTH);
            message.setPos(0, titlebar.bottom() + GAP);
            add(message);

            RedButton btnWand1 = new RedButton(Messages.get(wandmaker_2, "water_challenge")) {
                @Override
                protected void onClick() {
                    InterlevelScene.mode = InterlevelScene.Mode.WATERCHALLENGE;
                    Game.switchScene(InterlevelScene.class);
                }
            };
            btnWand1.setRect(0, message.top() + message.height() + GAP, WIDTH, BTN_HEIGHT);
            add(btnWand1);

            RedButton btnWand2 = new RedButton(Messages.get(wandmaker_2, "earth_challenge")) {
                @Override
                protected void onClick() {
                    InterlevelScene.mode = InterlevelScene.Mode.EARTHCHALLENGE;
                    Game.switchScene(InterlevelScene.class);
                }
            };
            btnWand2.setRect(0, message.top() + message.height() + GAP + BTN_HEIGHT, WIDTH, BTN_HEIGHT);

            add(btnWand2);

            resize(WIDTH, (int) btnWand2.bottom());
        }
    }

    protected static WndBag.Listener itemSelector = new WndBag.Listener() {
        @Override
        public void onSelect( Item item ) {

            if (item != null) {

                ScrollOfRemoveCurse rc = new ScrollOfRemoveCurse();

            }
        }
    };
}
