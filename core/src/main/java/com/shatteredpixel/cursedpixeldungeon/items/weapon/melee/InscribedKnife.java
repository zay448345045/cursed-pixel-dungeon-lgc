package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Doom;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Belongings;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Wraith;
import com.shatteredpixel.cursedpixeldungeon.effects.particles.EnergyParticle;
import com.shatteredpixel.cursedpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.items.allies.DragonCrystal;
import com.shatteredpixel.cursedpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.cursedpixeldungeon.items.wands.Wand;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.cursedpixeldungeon.ui.QuickSlotButton;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;

import java.util.ArrayList;

public class InscribedKnife extends MeleeWeapon {

    {
        image = ItemSpriteSheet.INSCRIBED_KINFE;

        tier = 1;

        bones = false;

        defaultAction = AC_CURSE;

        usesTargeting = true;
    }
    float charge = 0;
    int maxCharge = 40;

    public static final String AC_CURSE = "CURSE";
    public static final String AC_SUMMON = "SUMMON";
    public static final int CURSE_AMT = 30;
    public static final int SUMMON_AMT = 40;

    @Override
    public int UpgradeLimit() {
       if (Dungeon.hero.subClass == HeroSubClass.MEDIC) {
           return 25;
       } else {
           return 20;
       }
    }

    public void Charge(float amount) {
        charge += amount;
        charge = Math.min(charge,maxCharge);
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
            Charge(1f + 0.1f*level());//Charge gained scales slowly with level
        }
    }

    @Override
    public String desc() {
        return super.desc() + "\n\n" + Messages.get(this, "charge_desc", (int)charge, maxCharge);
    }

    protected static CellSelector.Listener curse = new  CellSelector.Listener() {

        @Override
        public void onSelect(Integer target) {
            Char enemy;
            if (target != null) {
                int cell = target;
                if (Actor.findChar(target) != null)
                    QuickSlotButton.target(Actor.findChar(target));
                else
                    QuickSlotButton.target(Actor.findChar(cell));
                enemy = Actor.findChar(cell);
                if (enemy != null) {
                    Buff.affect(enemy, Doom.class);
                    enemy.sprite.emitter().burst(ShadowParticle.CURSE, 6);
                    GLog.i( Messages.get(InscribedKnife.class, "curse_message") );
                } else {
                    GLog.w( Messages.get(InscribedKnife.class, "curse_fail") );
                }
            }
        }

        @Override
        public String prompt() {
            return Messages.get(this, "prompt_curse");
        }
    };

    protected static CellSelector.Listener summon = new  CellSelector.Listener() {

        @Override
        public void onSelect(Integer target) {
            if (target != null) {
                int cell = target;
                if (Actor.findChar(target) != null)
                    QuickSlotButton.target(Actor.findChar(target));
                else
                    QuickSlotButton.target(Actor.findChar(cell));
                if (Wraith.spawnAt(cell) != null) {
                    GLog.i( Messages.get(InscribedKnife.class, "summon_message") );
                } else {
                    GLog.w( Messages.get(InscribedKnife.class, "summon_fail") );
                }
            }
        }

        @Override
        public String prompt() {
            return Messages.get(this, "prompt_summon");
        }
    };

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if (action.equals(AC_CURSE)) {
            if (charge >= CURSE_AMT) {
                GameScene.selectCell(curse);
                charge -= CURSE_AMT;
            } else {
                GLog.i( Messages.get(this, "no_charge") );
            }
        } else if (action.equals(AC_SUMMON)) {
            if (charge >= SUMMON_AMT) {
                GameScene.selectCell(summon);
                charge -= SUMMON_AMT;
            } else {
                GLog.i( Messages.get(this, "no_charge") );
            }
        }
    }
}
