package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee;

import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;

import java.util.ArrayList;

public class InscribedKnife extends MeleeWeapon {

    {
        image = ItemSpriteSheet.INSCRIBED_KINFE;

        tier = 1;

        bones = false;
    }
    int charge = 0;
    int maxCharge = 40;

    public static final String AC_CURSE = "CURSE";
    public static final String AC_SUMMON = "SUMMON";

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions( hero );
        actions.add(AC_CURSE);
        return actions;
    }

    @Override
    public void onHeroGainExp(float levelPercent, Hero hero) {
        super.onHeroGainExp(levelPercent, hero);
        charge += (int) (levelPercent/2);
    }

    @Override
    public String desc() {
        return super.desc() + "\n\n" + Messages.get(this, "charge_desc", charge, maxCharge);
    }
}
