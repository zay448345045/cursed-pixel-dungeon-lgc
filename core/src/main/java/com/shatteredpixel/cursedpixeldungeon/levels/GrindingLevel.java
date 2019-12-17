package com.shatteredpixel.cursedpixeldungeon.levels;

import com.shatteredpixel.cursedpixeldungeon.Assets;
import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Warlock;
import com.shatteredpixel.cursedpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.items.powers.LuckyBadge;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.cursedpixeldungeon.levels.traps.WornDartTrap;
import com.shatteredpixel.cursedpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.cursedpixeldungeon.sprites.StatueSprite;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class GrindingLevel extends SewerLevel {

    @Override
    public float respawnTime() {
        return super.respawnTime()/5f;
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
        int lootAmt = 1;

        int getScaleFactor() {
            return Math.min(30,Math.max(0,Dungeon.hero.lvl-2));
        }

        public Guardian() {
            super();
            HP = HT = 10 + 7 * getScaleFactor();
            aggro(Dungeon.hero);
        }

        @Override
        public void die(Object cause) {
            for (int i = 0; i < lootAmt; i++) {
                Item luckybadgedrop = LuckyBadge.tryForBonusDrop(Dungeon.hero, 1);
                if (luckybadgedrop != null) {
                    Dungeon.level.drop(luckybadgedrop, pos).sprite.drop();
                }
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

        private boolean canParalyze = true;
        private final String CAN_PARALYZE = "can_paralyze";
        {
            spriteClass = GreenGuardianSprite.class;
            evaFactor = 0.5f;
            accuFactor = 2f;
            damageFactor = 0.7f;
            DRFactor = 2f;
        }

        @Override
        public int attackProc(Char enemy, int damage) {
            if (canParalyze & Random.Int(2) == 0) {
                Buff.affect(enemy, Paralysis.class, Paralysis.DURATION/2f);
                canParalyze = false;
            }
            enemy.damage(Math.max(10,enemy.HP)/10,this);
            return super.attackProc(enemy, damage);
        }

        @Override
        public void storeInBundle(Bundle bundle) {
            bundle.put( CAN_PARALYZE, canParalyze );
            super.storeInBundle(bundle);
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            canParalyze = bundle.getBoolean( CAN_PARALYZE );
            super.restoreFromBundle(bundle);
        }
    }
    public static class RedGuardian extends Guardian {
        {
            spriteClass = RedGuardianSprite.class;
            baseSpeed = 2f;
            accuFactor = 2f;
            damageFactor = 0.75f;
            DRFactor = 0f;
            evaFactor = 1.5f;
            HP = HT = (int) (super.HT*0.67f);
        }

        @Override
        protected float attackDelay() {
            return 0.5f;
        }

        @Override
        public int attackProc(Char enemy, int damage) {
            if (Random.Int(3) == 0) {
                Buff.affect(enemy, Burning.class).reignite(enemy);
            }
            return super.attackProc(enemy, damage);
        }
    }

    public static class BlueGuardian extends Guardian {
        {
            spriteClass = BlueGuardianSprite.class;
            baseSpeed = 0.5f;
            evaFactor = 0.5f;
            damageFactor = 2f;
            DRFactor = 2f;
            lootAmt = 2;//Tankier, so provides more reward.
            HP = HT = (int)(super.HT*2f);
        }
    }

    public static class YellowGuardian extends Guardian {
        private int delay = 0;
        private static final int BLINK_DELAY = 5;
        {
            spriteClass = YellowGuardianSprite.class;
            HP = HT = super.HT/2;
            damageFactor = 2.5f;
            DRFactor = 0f;
        }

        @Override
        protected float attackDelay() {
            return 1.5f;
        }

        private void blink(int target ) {

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

    public static class OrangeGuardian extends RedGuardian implements Callback {
        {
            spriteClass = OrangeGuardianSprite.class;
            baseSpeed = 1f;
        }

        static class FireBolt {}

        public void onZapComplete() {
            zap();
            next();
        }

        private void zap() {
            spend( attackDelay()*2 );

            if (hit( this, enemy, true )) {
                if (enemy == Dungeon.hero && Random.Int( 2 ) == 0) {
                    Buff.affect( enemy, Burning.class ).reignite(enemy);
                }

                int dmg = damageRoll()/3;
                enemy.damage( dmg, new FireBolt() );

                if (!enemy.isAlive() && enemy == Dungeon.hero) {
                    Dungeon.fail( getClass() );
                    GLog.n( Messages.get(this, "bolt_kill") );
                }
            } else {
                enemy.sprite.showStatus( CharSprite.NEUTRAL,  enemy.defenseVerb() );
            }
        }

        @Override
        public void call() {
            next();
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

    public class OrangeGuardianSprite extends StatueSprite {

        public OrangeGuardianSprite() {
            super();
            zap = attack.clone();
            tint(1, 0.5f, 0, 0.2f);
        }

        @Override
        public void resetColor() {
            super.resetColor();
            tint(1, 0.5f, 0, 0.2f);
        }
        @Override
        public void zap( int cell ) {
            super.zap( cell );

            turnTo( ch.pos , cell );
            play( zap );

            MagicMissile.boltFromChar( parent,
                    MagicMissile.FIRE_CONE,
                    this,
                    cell,
                    new Callback() {
                        @Override
                        public void call() {
                            ((OrangeGuardian)ch).onZapComplete();
                        }
                    } );
            Sample.INSTANCE.play( Assets.SND_ZAP );
        }

        @Override
        public void onComplete( Animation anim ) {
            if (anim == zap) {
                idle();
            }
            super.onComplete( anim );
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
