package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments;

import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicMeleeWeapon;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.cursedpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Barrier extends RelicEnchantment {

    private static final ItemSprite.Glowing BLUE = new ItemSprite.Glowing( 0x0000FF );

    @Override
    public ItemSprite.Glowing glowing() {
        return BLUE;
    }

    @Override
    public int relicProc(RelicMeleeWeapon weapon, Char attacker, Char defender, int damage) {//Practically identical to Blocking, but it's function is different.

        int level = Math.max( 0, weapon.level() );

        Buff.prolong(attacker, BarrierBuff.class, 2 + level/2).setBlocking(level + 1);

        return damage;
    }

    public static class BarrierBuff extends FlavourBuff {

        private int blocking = 0;

        public void setBlocking( int blocking ){
            this.blocking = blocking;
        }

        public int blockingRoll(){
            return Random.NormalIntRange(0, blocking);
        }

        @Override
        public int icon() {
            return BuffIndicator.ARMOR;
        }

        @Override
        public void tintIcon(Image icon) {
            icon.tint(0, 1f, 1.5f, 0.5f);
        }

        @Override
        public String toString() {
            return Messages.get(this, "name");
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc", blocking, dispTurns());
        }

        private static final String BLOCKING = "blocking";

        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put(BLOCKING, blocking);
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            blocking = bundle.getInt(BLOCKING);
        }

    }
}
