package com.shatteredpixel.cursedpixeldungeon.items.powers;

import com.shatteredpixel.cursedpixeldungeon.Assets;
import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.cursedpixeldungeon.items.Gold;
import com.shatteredpixel.cursedpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.cursedpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;

public class Greed extends ActivatedPower {
    {
        mp_cost = 10;
        image = ItemSpriteSheet.GREED;
    }

    @Override
    public boolean usesTargeting() {
        return true;
    }

    @Override
    public void affectCell(int pos) {
        Char ch = Actor.findChar(pos);
        if (ch == null) {
            return;
        }
        Buff.affect(ch, GreedBuff.class).set(10);
    }

    public void fx(Ballistica shot, Callback callback) {
        MagicMissile.boltFromChar( curUser.sprite.parent,
                MagicMissile.COIN,
                curUser.sprite,
                shot.collisionPos,
                callback);
        Sample.INSTANCE.play( Assets.SND_GOLD );
    }


    public static class GreedBuff extends Buff {
        protected float left;

        private static final String LEFT	= "left";

        {
            type = buffType.NEGATIVE;
        }

        @Override
        public void storeInBundle( Bundle bundle ) {
            super.storeInBundle( bundle );
            bundle.put( LEFT, left );

        }

        @Override
        public void restoreFromBundle( Bundle bundle ) {
            super.restoreFromBundle( bundle );
            left = bundle.getFloat( LEFT );
        }

        @Override
        public String toString() {
            return Messages.get(this, "name");
        }

        @Override
        public int icon() {
            return BuffIndicator.RAGE;
        }

        public void set( float duration ) {
            this.left = Math.max(duration, left);
        }

        public void extend( float duration ) {
            this.left += duration;
        }

        @Override
        public boolean act() {
            Dungeon.level.drop( new Gold().random(), target.pos ).sprite.drop( target.pos );
            if ((left -= TICK) <= 0) {
                detach();
            }
            spend( TICK );
            return true;
        }
    }
}
