package com.shatteredpixel.cursedpixeldungeon.items.powers;

import com.shatteredpixel.cursedpixeldungeon.Assets;
import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.cursedpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.cursedpixeldungeon.effects.Speck;
import com.shatteredpixel.cursedpixeldungeon.items.armor.RogueArmor;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class SmokeBomb extends ActivatedPower {

    {
        image = ItemSpriteSheet.SMOKEBOMB;
        mp_cost = 4;
    }

    @Override
    public boolean usesTargeting() {
        return false;//Does actually use targeting, but it's unique enough to have to be hardcoded here.
    }

    @Override
    public void affectCell(int pos) {}

    @Override
    public void activatePower(Hero hero) {
        GameScene.selectCell( teleporter );
    }

    private static CellSelector.Listener teleporter = new  CellSelector.Listener() {

        @Override
        public void onSelect( Integer target ) {
            if (target != null) {

                PathFinder.buildDistanceMap(curUser.pos, Dungeon.level.passable, 8);

                if ( PathFinder.distance[target] == Integer.MAX_VALUE ||
                        !Dungeon.level.heroFOV[target] ||
                        !(Dungeon.level.passable[target] || Dungeon.level.avoid[target]) ||
                        Actor.findChar( target ) != null) {

                    GLog.w( Messages.get(SmokeBomb.class, "fov") );
                    return;
                }

                for (Mob mob : Dungeon.level.mobs.toArray(new Mob[Dungeon.level.mobs.size()])) {
                    if (Dungeon.level.heroFOV[mob.pos]) {
                        Buff.prolong( mob, Blindness.class, 2 );
                        if (mob.state == mob.HUNTING) mob.state = mob.WANDERING;
                        mob.sprite.emitter().burst( Speck.factory( Speck.LIGHT ), 4 );
                    }
                }

                ScrollOfTeleportation.appear( curUser, target );
                CellEmitter.get( target ).burst( Speck.factory( Speck.WOOL ), 10 );
                Sample.INSTANCE.play( Assets.SND_PUFF );
                Dungeon.level.press( target, curUser );
                Dungeon.observe();
                GameScene.updateFog();
            }
        }

        @Override
        public String prompt() {
            return Messages.get(RogueArmor.class, "prompt");
        }
    };
}
