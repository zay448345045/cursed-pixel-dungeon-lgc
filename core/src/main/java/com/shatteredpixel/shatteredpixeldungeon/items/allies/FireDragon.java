package com.shatteredpixel.shatteredpixeldungeon.items.allies;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Bee;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Shaman;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Statue;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Warlock;
import com.shatteredpixel.shatteredpixeldungeon.effects.Fireball;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SparkParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.KindofMisc;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Firebomb;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Grim;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DragonSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.sprites.RatSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ShamanSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.StatueSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.WarlockSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class FireDragon extends KindofMisc {

    public static final String AC_SUMMON	= "SHATTER";

    {
        image = ItemSpriteSheet.HONEYPOT;

        defaultAction = AC_THROW;
        usesTargeting = true;

        stackable = true;
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

        if (action.equals( AC_SUMMON )) {

            hero.sprite.zap( hero.pos );

            shatter( hero, hero.pos );

            hero.next();

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
            Dragon bee = new Dragon();
            bee.HP = bee.HT;
            bee.alignment = Char.Alignment.ALLY;
            bee.pos = newPos;

            GameScene.add( bee );
            Actor.addDelayed( new Pushing( bee, pos, newPos ), -1f );

            bee.sprite.alpha( 0 );
            bee.sprite.parent.add( new AlphaTweener( bee.sprite, 1, 0.15f ) );

            Sample.INSTANCE.play( Assets.SND_BEE );
            return this;
        } else {
            return this;
        }
    }
    public class Dragon extends Mob  {


            {
                spriteClass = DragonSprite.class;

                HP = HT = 8 + 4*level();
                defenseSkill = 2;
            }

            @Override
            public int damageRoll() {
                return Random.NormalIntRange( 1 + level(), 4 + level()*2 );
            }

            @Override
            public int attackSkill( Char target ) {
                return 8;
            }

            @Override
            public int drRoll() {
                return Random.NormalIntRange(0, 1);
            }


    }




    @Override
    public boolean isUpgradable() {
        return super.isUpgradable();
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

