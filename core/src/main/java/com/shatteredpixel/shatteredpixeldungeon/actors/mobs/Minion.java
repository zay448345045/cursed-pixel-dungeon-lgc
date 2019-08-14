package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfPrismaticLight;
import com.watabou.utils.Random;

public class Minion extends Wraith {
    {
        defenseSkill = 0;
        HP = HT = 60;
        immunities.add(Wand.class);
        immunities.remove(WandOfPrismaticLight.class);//immune to wands except prismatic light
        properties.add(Property.DEMONIC);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 20, 45 );
    }

}