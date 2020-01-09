package com.shatteredpixel.cursedpixeldungeon.actors.mobs;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.items.wands.Wand;

public class Minion extends Wraith {
    {
        resistances.add(Wand.class);
        properties.add(Property.DEMONIC);
        alignment = Alignment.ENEMY;
    }

    public Minion() {
        super();
        adjustStats(30);
        defenseSkill = 0;
        HP = HT = 80;
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        Buff.prolong( enemy, Blindness.class, 5 );
        return super.attackProc(enemy, damage);
    }

    @Override
    public void convert() {//Necromancer can't tame them
    }
}