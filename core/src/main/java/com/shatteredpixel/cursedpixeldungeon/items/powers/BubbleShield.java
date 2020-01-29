package com.shatteredpixel.cursedpixeldungeon.items.powers;

import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.cursedpixeldungeon.ui.BuffIndicator;

public class BubbleShield extends ActivatedPower {
    {
         mp_cost = 5;
         image = ItemSpriteSheet.BUBBLESHIELD;
    }

    @Override
    public boolean usesTargeting() {
        return false;
    }

    @Override
    public void affectCell(int pos) {
        Char ch = Actor.findChar(pos);
        if (ch != null) {
            Buff.affect(ch, BubbleShieldBuff.class);
        }
    }

    public static class BubbleShieldBuff extends Buff {

        {
            type = buffType.POSITIVE;
        }

        @Override
        public String toString() {
            return Messages.get(this, "name");
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc");
        }

        @Override
        public void fx(boolean on) {
            if (on) target.sprite.add(CharSprite.State.BUBBLED);
            else target.sprite.remove(CharSprite.State.BUBBLED);
        }

        @Override
        public int icon() {
            return BuffIndicator.RAGE;
        }
    }
}
