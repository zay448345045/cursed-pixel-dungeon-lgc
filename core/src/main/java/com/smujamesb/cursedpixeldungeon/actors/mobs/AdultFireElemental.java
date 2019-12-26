package com.smujamesb.cursedpixeldungeon.actors.mobs;

import com.smujamesb.cursedpixeldungeon.actors.Char;
import com.smujamesb.cursedpixeldungeon.actors.blobs.Blob;
import com.smujamesb.cursedpixeldungeon.actors.blobs.Inferno;
import com.smujamesb.cursedpixeldungeon.scenes.GameScene;
import com.smujamesb.cursedpixeldungeon.sprites.AdultElementalSprite;
import com.watabou.utils.Random;

public class AdultFireElemental extends Elemental {
    {
        spriteClass = AdultElementalSprite.class;
        HP = HT = 250;
        defenseSkill = 30;

        EXP = 25;
        maxLvl = 30;
    }

    @Override
    public boolean act() {

        GameScene.add(Blob.seed(pos, 20, Inferno.class));

        return super.act();
    }
    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 70, 110 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 35;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 25);
    }
}
