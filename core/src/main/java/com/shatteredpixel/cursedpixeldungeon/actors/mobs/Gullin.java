package com.shatteredpixel.cursedpixeldungeon.actors.mobs;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.items.Generator;

public class Gullin extends Kupua {
    {
        loot = Generator.random(Generator.Category.WEP_T6);
        lootChance = 0.5f;
        HP = HT = 300;
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        Buff.affect(enemy, Bleeding.class).set(Dungeon.depth*2);//Heavy bleeding
        return super.attackProc(enemy, damage);
    }
}
