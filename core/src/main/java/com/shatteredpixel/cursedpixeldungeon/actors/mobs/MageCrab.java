package com.shatteredpixel.cursedpixeldungeon.actors.mobs;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.cursedpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.cursedpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.cursedpixeldungeon.sprites.MageCrabSprite;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class MageCrab extends Mob implements Callback {
    private static final float TIME_TO_ZAP	= 1f;
    {
        spriteClass = MageCrabSprite.class;

        HP = HT = 210;
        defenseSkill = 27;
        viewDistance = Light.DISTANCE;

        EXP = 14;
        maxLvl = 26;

        loot = new PotionOfHealing();
        lootChance = 0.2f;

        properties.add(Property.DEMONIC);
    }

    public static class MageCrabIce{}

    protected boolean doAttack( Char enemy ) {

        if (Dungeon.level.adjacent( pos, enemy.pos )) {

            return super.doAttack( enemy );

        } else {

            boolean visible = fieldOfView[pos] || fieldOfView[enemy.pos];
            if (visible) {
                sprite.zap( enemy.pos );
            } else {
                zap();
            }

            return !visible;
        }
    }

    private void zap() {
        spend( TIME_TO_ZAP );

        if (hit( this, enemy, true )) {
            Buff.affect( enemy, Chill.class, 3f );


            int dmg = Random.Int( 10, 30 );
            enemy.damage( dmg, new MageCrabIce() );

            if (!enemy.isAlive() && enemy == Dungeon.hero) {
                Dungeon.fail( getClass() );
                GLog.n( Messages.get(this, "bolt_kill") );
            }
        } else {
            enemy.sprite.showStatus( CharSprite.NEUTRAL,  enemy.defenseVerb() );
        }
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 60, 120);
    }

    @Override
    public int attackSkill( Char target ) {
        return 70;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 16);
    }

    @Override
    protected boolean canAttack( Char enemy ) {
        Ballistica attack = new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE);
        return !Dungeon.level.adjacent( pos, enemy.pos ) && attack.collisionPos == enemy.pos;
    }

    @Override
    public int attackProc( Char enemy, int damage ) {
        damage = super.attackProc( enemy, damage );
        if (Random.Int( 2 ) == 0) {
            Buff.prolong( enemy, Cripple.class, Cripple.DURATION );
        }

        return damage;
    }

    @Override
    protected boolean getCloser( int target ) {
        if (state == HUNTING) {
            return enemySeen && getFurther( target );
        } else {
            return super.getCloser( target );
        }
    }

    @Override
    protected Item createLoot() {
        //(9-count) / 9 chance of getting healing, otherwise mystery meat
        if (Random.Float() < ((9f - Dungeon.LimitedDrops.SCORPIO_HP.count) / 9f)) {
            Dungeon.LimitedDrops.SCORPIO_HP.count++;
            return (Item)loot;
        } else {
            return new MysteryMeat();
        }
    }

    @Override
    public void call() {
        next();
    }

    public void onZapComplete() {
        zap();
        next();
    }
}
