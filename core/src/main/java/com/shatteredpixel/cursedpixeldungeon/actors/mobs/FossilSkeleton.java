package com.shatteredpixel.cursedpixeldungeon.actors.mobs;

import com.shatteredpixel.cursedpixeldungeon.Assets;
import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.items.Generator;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.cursedpixeldungeon.levels.features.Chasm;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.sprites.FossilSkeletonSprite;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class FossilSkeleton extends Mob {
    {
        spriteClass = FossilSkeletonSprite.class;

        HP = HT = 200;
        defenseSkill = 20;

        EXP = 20;
        maxLvl = 30;

        loot = Generator.Category.WEAPON;
        lootChance = 0.5f;

        properties.add(Property.UNDEAD);
        properties.add(Property.INORGANIC);
        resistances.add(MeleeWeapon.class);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(50, 100);
    }

    @Override
    public void die(Object cause) {

        super.die(cause);

        if (cause == Chasm.class) return;

        boolean heroKilled = false;
        for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
            Char ch = findChar(pos + PathFinder.NEIGHBOURS8[i]);
            if (ch != null && ch.isAlive()) {
                int damage = (int) (damageRoll()*1.5f);
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
    public int attackSkill(Char target) {
        return 40;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(10, 25);
    }

    /*public static class FossilSkeletonSprite extends SkeletonSprite {
        public FossilSkeletonSprite(){
            super();
            tint(0, 0, 0, 0.2f);
        }

        @Override
        public void resetColor() {
            super.resetColor();
            tint(0, 0, 0, 0.2f);
        }
    }*/

}

