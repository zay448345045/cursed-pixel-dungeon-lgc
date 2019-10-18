package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
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
    float charge = 0;
    int maxCharge = 40;

    public static final String AC_CURSE = "CURSE";
    public static final String AC_SUMMON = "SUMMON";

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions( hero );
        actions.add(AC_CURSE);
        actions.add(AC_SUMMON);
        return actions;
    }

    @Override
    public void onHeroGainExp(float levelPercent, Hero hero) {
        if (this.isEquipped(Dungeon.hero)) {
            super.onHeroGainExp(levelPercent, hero);
            charge += (levelPercent / 2);
        }
    }

    @Override
    public String desc() {
        return super.desc() + "\n\n" + Messages.get(this, "charge_desc", (int)charge, maxCharge);
    }
}
