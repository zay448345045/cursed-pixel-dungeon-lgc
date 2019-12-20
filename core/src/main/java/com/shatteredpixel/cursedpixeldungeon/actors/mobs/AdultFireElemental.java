package com.shatteredpixel.cursedpixeldungeon.actors.mobs;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.cursedpixeldungeon.actors.blobs.Inferno;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Frost;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.watabou.utils.Random;

public class AdultFireElemental extends Elemental {
    {

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
