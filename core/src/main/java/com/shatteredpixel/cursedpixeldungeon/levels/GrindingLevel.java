package com.shatteredpixel.cursedpixeldungeon.levels;

import com.shatteredpixel.cursedpixeldungeon.Assets;
import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.items.powers.LuckyBadge;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.cursedpixeldungeon.levels.traps.WornDartTrap;
import com.shatteredpixel.cursedpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.cursedpixeldungeon.sprites.StatueSprite;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class GrindingLevel extends SewerLevel {
    static {
        TIME_TO_RESPAWN = 25;//Double the spawn rate
    }

    @Override
    protected Class<?>[] trapClasses() {
        return new Class<?>[]{ WornDartTrap.class };
    }

    @Override
    protected float[] trapChances() {
        return new float[]{1};
    }

    @Override
    public String tilesTex() {
        return Assets.TILES_BOSS;
    }

    @Override
    public String waterTex() {
        return Assets.WATER_SEWERS;
    }

    @Override
    protected void createItems() {}//Does nothing. Prevents Ghost from spawning, also makes sense that all loot is supposed to come from killing Guardians.

    public static class Guardian extends Mob {

        {
            spriteClass = StatueSprite.class;

            EXP = 0;
            state = WANDERING;
        }

        public float damageFactor = 1f;
        float accuFactor = 1f;
        float evaFactor = 1f;
        float DRFactor = 1f;

        int getScaleFactor() {
            return Dungeon.hero.lvl-1;
        }

        public Guardian() {
            super();
            HP = HT = 15 + 5 * getScaleFactor();
            aggro(Dungeon.hero);
        }

        @Override
        public void die(Object cause) {
            Item luckybadgedrop = LuckyBadge.tryForBonusDrop(Dungeon.hero, 1);
            if (luckybadgedrop != null) {
                Dungeon.level.drop(luckybadgedrop, pos).sprite.drop();
            }
            super.die(cause);
        }

        @Override
        public int damageRoll() {
            return (int) (Random.IntRange(1 + getScaleFactor(), 3 + getScaleFactor()*2) * damageFactor);
        }

        @Override
        public int drRoll() {
            return (int) (Random.Int(getScaleFactor() + 1) * DRFactor);
        }

        @Override
        public int attackSkill(Char target) {
            int scaleFactor;
            if (enemy instanceof Hero) {
                scaleFactor = getScaleFactor();
            } else {
                return (int) (enemy.attackSkill(this) * accuFactor);
            }
            return (int) ((10 + scaleFactor)*accuFactor);
        }

        @Override
        public int defenseSkill(Char enemy) {
            int scaleFactor;
            if (enemy instanceof Hero) {
                scaleFactor = getScaleFactor();
            } else {
                return (int) (enemy.defenseSkill(this) * evaFactor);
            }
            return (int) ((4 + scaleFactor)*evaFactor);
        }
    }

    public static class GreenGuardian extends Guardian {
        {
            spriteClass = GreenGuardianSprite.class;
            evaFactor = 0.5f;
            accuFactor = 2f;
            damageFactor = 0.7f;
            DRFactor = 2f;
        }
    }
    public static class RedGuardian extends Guardian {
        {
            spriteClass = RedGuardianSprite.class;
            baseSpeed = 2f;
            accuFactor = 2f;
            damageFactor = 0.75f;
            DRFactor = 0f;
        }

        @Override
        protected float attackDelay() {
            return 0.5f;
        }
    }

    public static class BlueGuardian extends Guardian {
        {
            spriteClass = BlueGuardianSprite.class;
            baseSpeed = 0.5f;
            damageFactor = 2f;
            DRFactor = 2f;
            HP = HT = (int)(super.HT*1.5f);
        }
    }

    public static class YellowGuardian extends Guardian {
        private int delay = 0;
        private static final int BLINK_DELAY	= 5;
        {
            spriteClass = YellowGuardianSprite.class;
            HP = HT = super.HT/2;
            damageFactor = 1.25f;
            DRFactor = 0f;
        }

        @Override
        protected float attackDelay() {
            return 0.67f;
        }

        private void blink( int target ) {

            Ballistica route = new Ballistica( pos, target, Ballistica.PROJECTILE);
            int cell = route.collisionPos;

            //can't occupy the same cell as another char, so move back one.
            if (Actor.findChar( cell ) != null && cell != this.pos)
                cell = route.path.get(route.dist-1);

            if (Dungeon.level.avoid[ cell ]){
                ArrayList<Integer> candidates = new ArrayList<>();
                for (int n : PathFinder.NEIGHBOURS8) {
                    cell = route.collisionPos + n;
                    if (Dungeon.level.passable[cell] && Actor.findChar( cell ) == null) {
                        candidates.add( cell );
                    }
                }
                if (candidates.size() > 0) {
                    cell = Random.element(candidates);
                } else {
                    delay = BLINK_DELAY;
                    return;
                }
            }

            ScrollOfTeleportation.appear( this, cell );

            delay = BLINK_DELAY;
        }

        @Override
        protected boolean getCloser( int target ) {
            if (fieldOfView[target] && Dungeon.level.distance( pos, target ) > 2 && delay <= 0) {

                blink( target );
                spend( -1 / speed() );
                return true;

            } else {

                delay--;
                return super.getCloser( target );

            }
        }
    }

    public static class GreenGuardianSprite extends StatueSprite {

        public GreenGuardianSprite(){
            super();
            tint(0, 1, 0, 0.2f);
        }

        @Override
        public void resetColor() {
            super.resetColor();
            tint(0, 1, 0, 0.2f);
        }
    }

    public static class RedGuardianSprite extends StatueSprite {

        public RedGuardianSprite(){
            super();
            tint(1, 0, 0, 0.2f);
        }

        @Override
        public void resetColor() {
            super.resetColor();
            tint(1, 0, 0, 0.2f);
        }
    }

    public static class BlueGuardianSprite extends StatueSprite {

        public BlueGuardianSprite(){
            super();
            tint(0, 0, 1, 0.2f);
        }

        @Override
        public void resetColor() {
            super.resetColor();
            tint(0, 0, 1, 0.2f);
        }
    }

    public static class YellowGuardianSprite extends StatueSprite {

        public YellowGuardianSprite(){
            super();
            tint(1, 1, 0, 0.2f);
        }

        @Override
        public void resetColor() {
            super.resetColor();
            tint(1, 1, 0, 0.2f);
        }
    }
}
