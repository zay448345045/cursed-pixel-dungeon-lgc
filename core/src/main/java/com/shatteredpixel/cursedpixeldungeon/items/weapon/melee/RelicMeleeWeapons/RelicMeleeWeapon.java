package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons;

import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.LockedFloor;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments.RelicEnchantment;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class RelicMeleeWeapon extends MeleeWeapon {
    {
        tier = 6;
        defaultAction = AC_ACTIVATE;
    }

    public static final String AC_ACTIVATE = "activate";

    protected Buff passiveBuff;
    protected int charge = 100;
    protected float partialCharge = 0;
    protected int chargeCap = 100;
    protected int cooldown = 0;

    public RelicMeleeWeapon() {
        super();
        enchant(enchantment());
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions =  super.actions(hero);
        actions.add(AC_ACTIVATE);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);
        if (action.equals(AC_ACTIVATE)) {
            ((RelicEnchantment)enchantment).activate(this,hero);
        }
    }

    public void use() {
        charge = 0;
    }

    @Override
    public int max(int lvl) {
        return (int) (super.max(lvl)*0.5f);
    }

    public void activate(Char ch ) {
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
        return new RelicMeleeWeaponBuff();
    }

    public RelicEnchantment enchantment() {//Ensures that it can only hold it's special enchantment
        return null;
    }

    @Override
    public Weapon enchant(Enchantment ench) {
        return super.enchant(enchantment());
    }

    @Override
    public String status() {
        if (!isIdentified()){
            return null;
        }
        //display as percent
        return Messages.format( "%d%%", charge );
    }
    private static final String CHARGE = "charge";
    private static final String PARTIALCHARGE = "partialcharge";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);
        bundle.put( CHARGE , charge );
        bundle.put( PARTIALCHARGE , partialCharge );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        charge = Math.min( chargeCap, bundle.getInt( CHARGE ));
        charge = bundle.getInt( CHARGE );
        partialCharge = bundle.getFloat( PARTIALCHARGE );
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
