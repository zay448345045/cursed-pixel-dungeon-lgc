package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfPrismaticLight;
import com.watabou.utils.Random;

public class Minion extends Wraith {
    {
        defenseSkill = 0;
        HP = HT = 80;
        resistances.add(Wand.class);
        resistances.remove(WandOfPrismaticLight.class);//immune to wands except prismatic light
        properties.add(Property.DEMONIC);
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        Buff.prolong( enemy, Blindness.class, 5 );
        return super.attackProc(enemy, damage);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 20, 60 );
    }

}