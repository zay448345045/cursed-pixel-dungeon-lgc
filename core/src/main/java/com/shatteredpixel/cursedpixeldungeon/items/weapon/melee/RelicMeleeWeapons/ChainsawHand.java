package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons;

import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments.Bloodlust;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments.RelicEnchantment;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;

import java.util.ArrayList;

public class ChainsawHand extends RelicMeleeWeapon {
    {
        image = ItemSpriteSheet.CHAINSAW_HAND;
        ACC = 2f;
        damageMultiplier = 0.5f;
        cursed = true;
    }

    private final String AC_DEACTIVATE = "deactivate";

    private boolean turnedOn = false;

    public boolean isTurnedOn() {
        return turnedOn;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        if (turnedOn) {
            actions.remove(AC_ACTIVATE);
            actions.add(AC_DEACTIVATE);
        }
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if (action.equals(AC_DEACTIVATE)) {
            specialAction(hero);
        }
    }

    @Override
    public RelicEnchantment enchantment() {
        return new Bloodlust();
    }

    @Override
    public Weapon enchant(Enchantment ench) {
        cursed = true;//Since it has a curse enchantment, cleansing it will always remove it by enchant(null). This code, along with the regular RelicMeleeWeapon code, ensures that it stays cursed just like the Sprouted version.
        return super.enchant(ench);
    }

    @Override
    public void specialAction(Hero hero) {
        turnedOn = !turnedOn;
    }
}
