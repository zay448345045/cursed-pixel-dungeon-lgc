package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee;

import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;

public class InscribedKnife extends MeleeWeapon {

    {
        image = ItemSpriteSheet.INSCRIBED_KINFE;

        tier = 1;

        bones = false;
    }
    int charge = 0;
    int maxCharge = 40;

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
