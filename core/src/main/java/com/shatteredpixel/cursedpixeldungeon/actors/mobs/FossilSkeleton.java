package com.shatteredpixel.cursedpixeldungeon.actors.mobs;

import com.shatteredpixel.cursedpixeldungeon.Assets;
import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.cursedpixeldungeon.effects.Chains;
import com.shatteredpixel.cursedpixeldungeon.effects.Pushing;
import com.shatteredpixel.cursedpixeldungeon.items.Generator;
import com.shatteredpixel.cursedpixeldungeon.items.Gold;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.levels.features.Chasm;
import com.shatteredpixel.cursedpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.SkeletonSprite;
import com.shatteredpixel.cursedpixeldungeon.utils.BArray;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class FossilSkeleton extends Mob {
    public boolean rocksUsed = false;
    {
        spriteClass = SkeletonSprite.class;

        HP = HT = 200;
        defenseSkill = 20;

        EXP = 20;
        maxLvl = 30;

        loot = Generator.Category.WEAPON;
        lootChance = 0.125f;

        properties.add(Property.UNDEAD);
        properties.add(Property.INORGANIC);
        //HUNTING = new Hunting();
    }

    private boolean useRocks(int target) {
        if (rocksUsed || enemy.properties().contains(Property.IMMOVABLE))
            return false;

        Ballistica LOS = new Ballistica(pos, target, Ballistica.PROJECTILE);

        if (LOS.collisionPos != enemy.pos
                || LOS.path.size() < 2
                || Dungeon.level.pit[LOS.path.get(1)])
            return false;
        else {
            if (enemy != null && enemy.isAlive()){
                int damage = Random.NormalIntRange(5+Dungeon.depth, 10+Dungeon.depth*2);
                damage -= enemy.drRoll();
                enemy.damage( Math.max(damage, 0) , this);

                Buff.prolong( enemy, Paralysis.class, Paralysis.DURATION );

                if (!enemy.isAlive() && enemy == Dungeon.hero){
                    Dungeon.fail( getClass() );
                    GLog.n( Messages.get(this, "ondeath") );
                }

            }
        }
        return true;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(50, 110);
    }

    @Override
    public void die(Object cause) {

        super.die(cause);

        if (cause == Chasm.class) return;

        boolean heroKilled = false;
        for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
            Char ch = findChar(pos + PathFinder.NEIGHBOURS8[i]);
            if (ch != null && ch.isAlive()) {
                int damage = Random.NormalIntRange(60, 120);
                damage = Math.max(0, damage - (ch.drRoll() + ch.drRoll()));
                ch.damage(damage, this);
                if (ch == Dungeon.hero && !ch.isAlive()) {
                    heroKilled = true;
                }
            }
        }

        if (Dungeon.level.heroFOV[pos]) {
            Sample.INSTANCE.play(Assets.SND_BONES);
        }

        if (heroKilled) {
            Dungeon.fail(getClass());
            GLog.n(Messages.get(this, "explo_kill"));
        }
    }

    @Override
    protected Item createLoot() {
        Item result = new Gold().random();
        result.quantity(Math.round(result.quantity() * Random.NormalFloat(0.33f, 1f)));
        return result;
    }

    @Override
    public int attackSkill(Char target) {
        return 40;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 5);
    }

    private class Hunting extends Mob.Hunting{
        @Override
        public boolean act( boolean enemyInFOV, boolean justAlerted ) {
            enemySeen = enemyInFOV;

            if (!rocksUsed
                    && enemyInFOV
                    && !isCharmedBy( enemy )
                    && !canAttack( enemy )
                    && Dungeon.level.distance( pos, enemy.pos ) < 5
                    && Random.Int(3) == 0

                    && useRocks(enemy.pos)){
                return false;
            } else {
                return super.act( enemyInFOV, justAlerted );
            }

        }
    }

}

