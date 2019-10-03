package com.shatteredpixel.cursedpixeldungeon.actors.mobs;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.items.wands.Wand;
import com.shatteredpixel.cursedpixeldungeon.items.wands.WandOfPrismaticLight;
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