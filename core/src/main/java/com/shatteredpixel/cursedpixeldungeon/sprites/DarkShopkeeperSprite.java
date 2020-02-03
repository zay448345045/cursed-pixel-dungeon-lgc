package com.shatteredpixel.cursedpixeldungeon.sprites;

import com.shatteredpixel.cursedpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class DarkShopkeeperSprite extends MobSprite {
    public DarkShopkeeperSprite() {
        super();

        texture( Assets.DARK_MAGE );
        TextureFilm film = new TextureFilm( texture, 14, 14 );

        idle = new Animation( 10, true );
        idle.frames( film, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 );

        die = new Animation( 20, false );
        die.frames( film, 0 );

        run = idle.clone();

        attack = idle.clone();

        idle();
    }
}
