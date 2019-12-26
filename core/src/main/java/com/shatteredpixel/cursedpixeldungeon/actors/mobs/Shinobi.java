package com.shatteredpixel.cursedpixeldungeon.actors.mobs;

import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.cursedpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.cursedpixeldungeon.sprites.ShinobiSprite;
import com.watabou.utils.Random;

public class Shinobi extends Mob {
    {
        spriteClass = ShinobiSprite.class;
        HP = HT = 180;
        defenseSkill = 30;
        EXP = 20;
        maxLvl = 30;
    }

    @Override
    protected float attackDelay() {
        return 0.5f;
    }

    @Override
    public int attackSkill(Char target) {
        return 50;
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        if(Random.Int(2)==0){
            Buff.affect(enemy, Slow.class, Slow.DURATION);
        } else if (Random.Int(3) == 0) {
            Buff.affect(enemy, Burning.class).reignite(enemy);
        }
        return super.attackProc(enemy, damage);
    }

    @Override
    public int damageRoll() {
        return Random.IntRange(30,80);
    }

    @Override
    public int drRoll() {
        return Random.Int(20);
    }

    @Override
    protected boolean canAttack( Char enemy ) {
        return new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE).collisionPos == enemy.pos;
    }
}
