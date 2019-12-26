package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Doom;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.cursedpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.LorsionsGreataxe;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicMeleeWeapon;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.cursedpixeldungeon.ui.QuickSlotButton;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

public class Damning extends RelicEnchantment {
    private static ItemSprite.Glowing BLACK = new ItemSprite.Glowing( 0x000000 );
    @Override
    public ItemSprite.Glowing glowing() {
        return BLACK;
    }
    @Override
    public int relicProc(RelicMeleeWeapon weapon, Char attacker, Char defender, int damage) {
        if (Random.Int(2) == 0) {
            Buff.affect(defender, Doom.class);
        } else {
            Buff.prolong(defender, Weakness.class, Weakness.DURATION);
        }
        return damage;
    }

    @Override
    public void activate(RelicMeleeWeapon weapon, Char owner) {
        GameScene.selectCell(crush);
    }
    private CellSelector.Listener crush = new  CellSelector.Listener() {

        @Override
        public void onSelect(Integer target) {
            Char enemy;
            KindOfWeapon Axe = Dungeon.hero.belongings.weapon;
            if (target != null && Axe instanceof LorsionsGreataxe) {
                int cell = target;
                LorsionsGreataxe Greataxe = (LorsionsGreataxe) Axe;
                if (Actor.findChar(target) != null)
                    QuickSlotButton.target(Actor.findChar(target));
                else
                    QuickSlotButton.target(Actor.findChar(cell));
                enemy = Actor.findChar(cell);
                if (enemy != null) {
                    if (Dungeon.level.trueDistance(Dungeon.hero.pos,enemy.pos) <= Greataxe.RCH + 1) {
                        Greataxe.prepare();
                        Dungeon.hero.attack(enemy);
                        Dungeon.hero.sprite.attack(enemy.pos);
                        Dungeon.hero.spendAndNext(Greataxe.speedFactor(Dungeon.hero));//This is enforced here so that augments make a difference
                        Damning.super.activate(Greataxe,Dungeon.hero);
                    } else {
                        GLog.n( Messages.get(Damning.class, "short_reach") );
                    }
                } else {
                    GLog.w( Messages.get(Damning.class, "no_enemy") );
                }
            }
        }

        @Override
        public String prompt() {
            return Messages.get(Damning.class, "prompt");
        }
    };
}
