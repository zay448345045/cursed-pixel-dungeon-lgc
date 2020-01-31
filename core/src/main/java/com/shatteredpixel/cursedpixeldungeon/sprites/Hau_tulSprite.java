package com.shatteredpixel.cursedpixeldungeon.sprites;

import com.shatteredpixel.cursedpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class Hau_tulSprite extends MobSprite {

    public Hau_tulSprite() {

        texture( Assets.HUMAN );

        TextureFilm frames = new TextureFilm( texture, 14, 15 );

        idle = new Animation( 4, true );
        idle.frames( frames, 18, 18, 18, 19 );

        die = idle.clone();

        play( idle );
    }
}
