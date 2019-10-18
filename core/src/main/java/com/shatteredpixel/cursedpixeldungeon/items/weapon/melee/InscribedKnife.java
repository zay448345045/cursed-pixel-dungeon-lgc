package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Belongings;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.cursedpixeldungeon.effects.particles.EnergyParticle;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.items.allies.DragonCrystal;
import com.shatteredpixel.cursedpixeldungeon.items.artifacts.Artifact;
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
    public int UpgradeLimit() {
        return 20;
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (attacker instanceof Hero && ((Hero)attacker).subClass == HeroSubClass.MEDIC) {
            ((Hero)attacker).sprite.centerEmitter().burst( EnergyParticle.FACTORY, 15 );
            Belongings b = ((Hero) attacker).belongings;
            if (b.misc1 instanceof DragonCrystal) {
                ((DragonCrystal)b.misc1).Charge(1);
            }
            if (b.misc2 instanceof DragonCrystal) {
                ((DragonCrystal)b.misc2).Charge(1);
            }
            if (b.misc3 instanceof DragonCrystal) {
                ((DragonCrystal)b.misc3).Charge(1);
            }
            if (b.misc4 instanceof DragonCrystal) {
                ((DragonCrystal)b.misc4).Charge(1);
            }
        }
        return super.proc(attacker, defender, damage);
    }

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
