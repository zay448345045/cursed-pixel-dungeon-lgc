package com.shatteredpixel.cursedpixeldungeon.sprites;

import com.shatteredpixel.cursedpixeldungeon.Assets;
import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.effects.Speck;
import com.watabou.noosa.TextureFilm;

import static com.shatteredpixel.cursedpixeldungeon.Assets.FOSSILSKELETON;


public class FossilSkeletonSprite extends MobSprite {

    public FossilSkeletonSprite() {
        super();

        texture( FOSSILSKELETON );

        TextureFilm frames = new TextureFilm( texture, 12, 15 );

        idle = new Animation( 12, true );
        idle.frames( frames, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3 );

        run = new Animation( 15, true );
        run.frames( frames, 4, 5, 6, 7, 8, 9 );

        attack = new Animation( 15, false );
        attack.frames( frames, 14, 15, 16 );

        die = new Animation( 12, false );
        die.frames( frames, 10, 11, 12, 13 );

        play( idle );
    }

    @Override
    public void die() {
        super.die();
        if (Dungeon.level.heroFOV[ch.pos]) {
            emitter().burst( Speck.factory( Speck.BONE ), 6 );
        }
    }

    @Override
    public int blood() {
        return 0xFFcccccc;
    }
}

