package com.shatteredpixel.cursedpixeldungeon.actors.mobs;

import com.shatteredpixel.cursedpixeldungeon.Assets;
import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.cursedpixeldungeon.actors.blobs.GooWarn;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Ooze;
import com.shatteredpixel.cursedpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.cursedpixeldungeon.effects.Speck;
import com.shatteredpixel.cursedpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.enchantments.Grim;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.cursedpixeldungeon.sprites.GooSprite;
import com.shatteredpixel.cursedpixeldungeon.sprites.WaterChallengeGooSprite;
import com.shatteredpixel.cursedpixeldungeon.utils.BArray;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class NonBossGoo extends Mob {

    {
        HP = HT = 300;
        EXP = 10;
        defenseSkill = 30;
        spriteClass = WaterChallengeGooSprite.class;

        properties.add(Property.DEMONIC);
        properties.add(Property.ACIDIC);
        resistances.add(Grim.class);
        baseSpeed = 2f;
        int cooldown = 0;
    }

    private int pumpedUp = 0;

    @Override
    public int damageRoll() {
        int min = 20;
        int max = (HP*2 <= HT) ? 120 : 100;
        if (pumpedUp > 0) {
            pumpedUp = 0;
            PathFinder.buildDistanceMap( pos, BArray.not( Dungeon.level.solid, null ), 2 );
            for (int i = 0; i < PathFinder.distance.length; i++) {
                if (PathFinder.distance[i] < Integer.MAX_VALUE)
                    CellEmitter.get(i).burst(ElmoParticle.FACTORY, 10);
            }
            Sample.INSTANCE.play( Assets.SND_BURNING );
            int Damage = Random.NormalIntRange( min, max*2 );//Goo can deal heavier damage but is unlikely to one shot the player as damage is rolled up to 3 times
            if (Damage > Dungeon.hero.HP) {
                Damage = Random.NormalIntRange( min, max*2 );
                if (Damage > Dungeon.hero.HP) {
                    Damage = Random.NormalIntRange( min, max*2 );
                }
            }
            return Damage;
        } else {
            return Random.NormalIntRange( min, max );
        }
    }
    @Override
    public void damage(int dmg, Object src) {
        if (pumpedUp > 0) dmg /= 2;
        super.damage(dmg, src);
    }
    @Override
    public int attackSkill( Char target ) {
        int attack = 45;
        if (HP*2 <= HT) attack = 15;
        if (pumpedUp > 0) attack *= 2;
        return attack;
    }

    @Override
    public int defenseSkill(Char enemy) {
        return 20;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(5, 10);
    }

    @Override
    public boolean act() {

        if (Dungeon.level.water[pos] && HP < HT) {
            sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );

            HP+= 3;
        }

        return super.act();
    }

    @Override
    protected boolean canAttack( Char enemy ) {
        return (pumpedUp > 0) ? distance( enemy ) <= 2 : super.canAttack(enemy);
    }

    @Override
    public int attackProc( Char enemy, int damage ) {
        damage = super.attackProc( enemy, damage );
        if (Random.Int( 3 ) == 0) {
            Buff.affect( enemy, Ooze.class ).set( 20f );
            enemy.sprite.burst( 0x000000, 5 );
        }

        if (pumpedUp > 0) {
            Camera.main.shake( 3, 0.2f );
        }

        return damage;
    }

    @Override
    protected boolean doAttack( Char enemy ) {
        if (pumpedUp == 1) {
            ((GooSprite)sprite).pumpUp();
            PathFinder.buildDistanceMap( pos, BArray.not( Dungeon.level.solid, null ), 2 );
            for (int i = 0; i < PathFinder.distance.length; i++) {
                if (PathFinder.distance[i] < Integer.MAX_VALUE)
                    GameScene.add(Blob.seed(i, 2, GooWarn.class));
            }
            pumpedUp++;

            spend( attackDelay() );

            return true;
        } else if (pumpedUp >= 2 || Random.Int( (HP*2 <= HT) ? 2 : 3     ) > 0) {

            boolean visible = Dungeon.level.heroFOV[pos];

            if (visible) {
                if (pumpedUp >= 2) {
                    ((GooSprite) sprite).pumpAttack();
                }
                else
                    sprite.attack( enemy.pos );
            } else {
                attack( enemy );
            }

            spend( attackDelay() );

            return !visible;

        } else {

            pumpedUp++;

            ((GooSprite)sprite).pumpUp();

            for (int i=0; i < PathFinder.NEIGHBOURS9.length; i++) {
                int j = pos + PathFinder.NEIGHBOURS9[i];
                if (!Dungeon.level.solid[j]) {
                    GameScene.add(Blob.seed(j, 2, GooWarn.class));
                }
            }

            if (Dungeon.level.heroFOV[pos]) {
                sprite.showStatus( CharSprite.NEGATIVE, Messages.get(this, "!!!") );
                GLog.n( Messages.get(this, "pumpup") );
            }

            spend( attackDelay() );

            return true;
        }
    }

    @Override
    public boolean attack( Char enemy ) {
        boolean result = super.attack( enemy );
        pumpedUp = 0;
        return result;
    }

    @Override
    protected boolean getCloser( int target ) {
        pumpedUp = 0;
        return super.getCloser( target );
    }

    @Override
    public void move( int step ) {
        super.move( step );
    }



    @Override
    public void die( Object cause ) {

        super.die( cause );
    }


    private final String PUMPEDUP = "pumpedup";

    @Override
    public void storeInBundle( Bundle bundle ) {

        super.storeInBundle( bundle );

        bundle.put( PUMPEDUP , pumpedUp );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {

        super.restoreFromBundle( bundle );

        pumpedUp = bundle.getInt( PUMPEDUP );

    }

}