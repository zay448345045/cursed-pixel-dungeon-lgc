package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments;

import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.HeroAction;
import com.shatteredpixel.cursedpixeldungeon.effects.particles.FlameParticle;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.enchantments.Blazing;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Random;

public class Inferno extends Blazing {

    @Override
    public int proc(Item weapon, Char attacker, Char defender, int damage ) {
        // lvl 0 - 33%
        // lvl 1 - 50%
        // lvl 2 - 60%
        int bonusdamage = Random.Int(1,3);
        if (weapon instanceof MeleeWeapon) {
            Weapon wep = (MeleeWeapon) weapon;
            bonusdamage = wep.damageRoll(attacker)/4;
        }
        if (defender.buff(Burning.class) != null) {
            Buff.affect(defender, Burning.class).reignite(defender, 8f);
            defender.damage(bonusdamage, this);
        } else {
            Buff.affect(defender, Burning.class).reignite(defender, 8f);
        }

        defender.sprite.emitter().burst(FlameParticle.FACTORY, weapon.level()*5);


        return damage;

    }
}
