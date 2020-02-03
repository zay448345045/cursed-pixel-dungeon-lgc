package com.shatteredpixel.cursedpixeldungeon.actors.buffs;

import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.cursedpixeldungeon.ui.BuffIndicator;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class Defense extends Buff {

    public int defensivePower = 0;

    private static String DEFENSIVE_POWER = "defensive_power";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(DEFENSIVE_POWER, defensivePower);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        defensivePower = bundle.getInt(DEFENSIVE_POWER);
    }

    public Defense setDefensivePower(Hero hero) {
        KindOfWeapon wep = hero.belongings.weapon;
        if (wep != null) {
            defensivePower = wep.defenseFactor(hero)*2;
        }
        if (wep instanceof MeleeWeapon) {
            defensivePower += hero.attackType.defenseBoost((MeleeWeapon) wep);
        }
        defensivePower += hero.damageRoll()/2;
        return this;
    }

    public boolean proc(Char defender, final Char attacker, int damage) {
        hit();
        if (Random.Int(defensivePower) >= Random.Int(damage)) {
            defender.attack(attacker, false, 0.5f);
            defender.sprite.attack(attacker.pos, new Callback() {
                @Override
                public void call() {
                    attacker.sprite.showStatus(CharSprite.POSITIVE, Messages.get(Char.class, "free_attack"));
                }
            });
            defender.sprite.showStatus(CharSprite.NEUTRAL, Messages.get(Char.class, "blocked"));
            return true;
        }
        return false;
    }

    public void hit() {
        defensivePower *= 0.67;
    }

    @Override
    public boolean act() {
        detach();
        return true;
    }

    @Override
    public int icon() {
        return BuffIndicator.ARMOR;
    }
}
