package com.shatteredpixel.cursedpixeldungeon.items.allies;

import com.shatteredpixel.cursedpixeldungeon.Assets;
import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.LockedFloor;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.cursedpixeldungeon.effects.Pushing;
import com.shatteredpixel.cursedpixeldungeon.effects.Splash;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.items.KindofMisc;
import com.shatteredpixel.cursedpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class DragonItem extends KindofMisc {
    protected Buff passiveBuff;
    protected Buff activeBuff;
    public static final String AC_SUMMON	= "SHATTER";
    protected int charge = 100;
    //the build towards next charge, usually rolls over at 1.
    //better to keep charge as an int and use a separate float than casting.
    protected float partialCharge = 0;
    //the maximum charge, varies per artifact, not all artifacts use this.
    protected int chargeCap = 100;

    @Override
    public boolean isUpgradable() {
        if ( Dungeon.hero.heroClass == HeroClass.PRIESTESS ) {//Only Priestess gets +20 Allies
            UpgradeLimit = 20;
        }
        return super.isUpgradable();
    }

    {
        image = ItemSpriteSheet.DRAGON_CRYSTAL;

        defaultAction = AC_SUMMON;
        usesTargeting = false;

        stackable = false;



    }

    @Override
    public Item upgrade() {
        this.dragon.SpawnerLevel = level();
        this.dragon.updateStats(this.dragon.SpawnerLevel);
        return super.upgrade();
    }

    @Override
    public String status() {
        return Messages.format( "%d%%", charge );
    }

    public void charge(Hero target) {
        if (charge < chargeCap){
            partialCharge += 0.1f;
            if (partialCharge >= 1){
                partialCharge--;
                charge++;
                updateQuickslot();
            }
        }
    }

    @Override
    public boolean doEquip( final Hero hero ) {

        if (super.doEquip(hero)) {

            identify();
            return true;

        } else {

            return false;

        }

    }

    public void activate( Char ch ) {
        passiveBuff = passiveBuff();
        passiveBuff.attachTo(ch);
    }

    @Override
    public boolean doUnequip( Hero hero, boolean collect, boolean single ) {
        if (super.doUnequip( hero, collect, single )) {

            passiveBuff.detach();
            passiveBuff = null;

            if (activeBuff != null){
                activeBuff.detach();
                activeBuff = null;
            }

            return true;

        } else {

            return false;

        }
    }

    public class CrystalBuff extends Buff {

        public int itemLevel() {
            return level();
        }

        public boolean isCursed() {
            return cursed;
        }

    }

    protected CrystalBuff passiveBuff() {
        return new recharge();
    }

    public class recharge extends CrystalBuff {
        @Override
        public boolean act() {
            LockedFloor lock = target.buff(LockedFloor.class);
            if (charge < chargeCap && !cursed && (lock == null || lock.regenOn())) {
                partialCharge += 0.1;

                if (partialCharge >= 1) {
                    partialCharge --;
                    charge ++;

                    if (charge == chargeCap){
                        partialCharge = 0;
                    }
                }
            }

            updateQuickslot();

            spend( TICK );

            return true;
        }
    }

    public Dragon dragon = new Dragon();

    boolean Spawned = false;
    public static String  SPAWNED = "spawned";
    public static String  CHARGE = "charge";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);
        bundle.put( SPAWNED, Spawned );
        bundle.put(CHARGE, charge);
        dragon.storeInBundle(bundle);

    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        Spawned = bundle.getBoolean( SPAWNED );
        dragon.restoreFromBundle(bundle);
        charge = bundle.getInt(CHARGE);

    }

    @Override
    public ArrayList<String> actions( Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_SUMMON );
        return actions;
    }

    @Override
    public void execute( final Hero hero, String action ) {

        super.execute( hero, action );
        boolean spawn = true;
        if (action.equals( AC_SUMMON )) {
            if (charge < chargeCap) {
                spawn = false;
            }
            if (!isEquipped(hero)) GLog.i( Messages.get(Artifact.class, "need_to_equip") );
            else if (!spawn)  GLog.i( Messages.get(this, "already_spawned") );
            else {
                shatter(hero, hero.pos);
                this.Spawned = true;
                hero.next();
            }
        }
    }


    public Item shatter( Char owner, int pos ) {

        if (Dungeon.level.heroFOV[pos]) {
            Sample.INSTANCE.play( Assets.SND_SHATTER );
            Splash.at( pos, 0xffd500, 5 );
        }

        int newPos = pos;
        if (Actor.findChar( pos ) != null) {
            ArrayList<Integer> candidates = new ArrayList<Integer>();
            boolean[] passable = Dungeon.level.passable;

            for (int n : PathFinder.NEIGHBOURS4) {
                int c = pos + n;
                if (passable[c] && Actor.findChar( c ) == null) {
                    candidates.add( c );
                }
            }

            newPos = candidates.size() > 0 ? Random.element( candidates ) : -1;
        }

        if (newPos != -1) {

            SpawnDragon(newPos, pos);
            this.charge = 0;
            updateQuickslot();
            Sample.INSTANCE.play( Assets.SND_BEE );
            return this;
        } else {
            return this;
        }
    }
    private static class Dragon extends DragonMob {


    }



    public void SpawnDragon(int newPos, int pos) {
        dragon = new Dragon();
        dragon.SpawnerLevel = level();
        dragon.HP = dragon.HT = 8 + 4 * dragon.SpawnerLevel;
        dragon.alignment = Char.Alignment.ALLY;
        dragon.pos = newPos;
        dragon.setLevel(level());

        GameScene.add( dragon );
        Actor.addDelayed( new Pushing( dragon, pos, newPos ), -1f );

        dragon.sprite.alpha( 0 );
        dragon.sprite.parent.add( new AlphaTweener( dragon.sprite, 1, 0.15f ) );
        dragon.notice();
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public int price() {
        return 30 * quantity;
    }

    //The bee's broken 'home', all this item does is let its bee know where it is, and who owns it (if anyone).
    /*public static class ShatteredPot extends Item {

        {
            image = ItemSpriteSheet.SHATTPOT;
            stackable = true;
        }

        @Override
        public boolean doPickUp(Hero hero) {
            if ( super.doPickUp(hero) ){
                pickupPot( hero );
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void doDrop(Hero hero) {
            super.doDrop(hero);
            dropPot(hero, hero.pos);
        }

        @Override
        protected void onThrow(int cell) {
            super.onThrow(cell);
            dropPot(curUser, cell);
        }

        public void pickupPot(Char holder){
            for (Bee bee : findBees(holder.pos)){
                updateBee(bee, -1, holder);
            }
        }

        public void dropPot( Char holder, int dropPos ){
            for (Bee bee : findBees(holder)){
                updateBee(bee, dropPos, null);
            }
        }

        private void updateBee( Bee bee, int cell, Char holder ){
            if (bee != null && bee.alignment == Char.Alignment.ENEMY)
                bee.setPotInfo( cell, holder );
        }

        //returns up to quantity bees which match the current pot Pos
        private ArrayList<Bee> findBees( int potPos ){
            ArrayList<Bee> bees = new ArrayList<>();
            for (Char c : Actor.chars()){
                if (c instanceof Bee && ((Bee) c).potPos() == potPos){
                    bees.add((Bee) c);
                    if (bees.size() >= quantity) {
                        break;
                    }
                }
            }

            return bees;
        }

        //returns up to quantity bees which match the current pot holder
        private ArrayList<Bee> findBees( Char potHolder ){
            ArrayList<Bee> bees = new ArrayList<>();
            for (Char c : Actor.chars()){
                if (c instanceof Bee && ((Bee) c).potHolderID() == potHolder.id()){
                    bees.add((Bee) c);
                    if (bees.size() >= quantity) {
                        break;
                    }
                }
            }

            return bees;
        }

        @Override
        public boolean isUpgradable() {
            return false;
        }

        @Override
        public boolean isIdentified() {
            return true;
        }

        @Override
        public int price() {
            return 5 * quantity;
        }
    }*/
}

