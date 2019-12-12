package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee;

import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.LockedFloor;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;

public class RelicMeleeWeapon extends MeleeWeapon {

    protected Buff passiveBuff;
    protected Buff activeBuff;
    protected int charge = 0;
    protected float partialCharge = 0;
    protected int chargeCap = 0;
    protected int cooldown = 0;

    public void activate( Char ch ) {
        passiveBuff = passiveBuff();
        if (passiveBuff != null) {
            passiveBuff.attachTo(ch);
        }
    }

    @Override
    public boolean doUnequip(Hero hero, boolean collect, boolean single) {
        passiveBuff.detach();
        return super.doUnequip(hero, collect, single);
    }

    protected RelicMeleeWeaponBuff passiveBuff() {
        return null;
    }

    @Override
    public String status() {
        if (!isIdentified()){
            return null;
        }
        //display as percent
        return Messages.format( "%d%%", charge );
    }

    public class RelicMeleeWeaponBuff extends Buff {

        public int itemLevel() {
            return level();
        }

        public boolean isCursed() {
            return cursed;
        }

        @Override
        public boolean act() {
            LockedFloor lock = target.buff(LockedFloor.class);
            if (charge < chargeCap && !cursed && (lock == null || lock.regenOn())) {
                partialCharge += 1/5f; //500 turns to a full charge
                if (partialCharge > 1){
                    charge++;
                    partialCharge--;
                    if (charge == chargeCap){
                        partialCharge = 0f;
                        GLog.p( Messages.get(RelicMeleeWeapon.class, "charged") );
                    }
                }
            }
            spend( TICK );
            return true;
        }
    }
}
