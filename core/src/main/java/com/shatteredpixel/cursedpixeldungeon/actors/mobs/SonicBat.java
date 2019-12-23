package com.shatteredpixel.cursedpixeldungeon.actors.mobs;

import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.watabou.utils.Random;

public class SonicBat extends Bat {
    {
        EXP = 25;
        maxLvl = 30;
        HP = HT = 200;
        defenseSkill = 30;
        baseSpeed = 3f;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(50,100);
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0,10);
    }

    @Override
    public int attackSkill(Char target) {
        return 40;
    }
}
