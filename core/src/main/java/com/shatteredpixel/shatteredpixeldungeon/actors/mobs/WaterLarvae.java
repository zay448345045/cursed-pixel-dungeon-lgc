package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.sprites.LarvaSprite;
import com.watabou.utils.Random;

public class WaterLarvae extends Mob {

    {
        spriteClass = LarvaSprite.class;

        HP = HT = 20;
        defenseSkill = 20;

        EXP = 0;
        maxLvl = -2;

        state = HUNTING;

        properties.add(Property.DEMONIC);
    }

    @Override
    public int attackSkill( Char target ) {
        return 30;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 22, 55 );
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 8);
    }

}
}
}
