package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments;

import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.ChainsawHand;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicMeleeWeapon;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;
import com.watabou.utils.Callback;

import javax.microedition.khronos.opengles.GL;

public class Bloodlust extends RelicEnchantment {

    private static ItemSprite.Glowing BLACK = new ItemSprite.Glowing( 0x000000 );

    @Override
    public boolean curse() {
        return true;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return BLACK;
    }

    @Override
    public int relicProc(RelicMeleeWeapon weapon, Char attacker, final Char defender, final int damage) {
        if (attacker instanceof Hero & defender.isAlive()) {
            if (weapon.charge > 0 & ((ChainsawHand)weapon).isTurnedOn()) {
                weapon.use(2);
                final Hero hero = (Hero) attacker;
                hero.sprite.attack(defender.pos, new Callback() {
                    @Override
                    public void call() {
                        hero.actuallyAttack(defender);
                        hero.next();
                    }
                });
            } else {
                GLog.n(Messages.get(Bloodlust.class,"no_charge"));
            }
        } else {
            attacker.next();
            return damage;//TODO: allow proccing from Statues and Ghost
        }

        return damage;
    }
}
