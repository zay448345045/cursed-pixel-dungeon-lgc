package com.shatteredpixel.cursedpixeldungeon.levels;

import com.shatteredpixel.cursedpixeldungeon.Assets;
import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Statue;
import com.shatteredpixel.cursedpixeldungeon.levels.traps.GuardianTrap;
import com.shatteredpixel.cursedpixeldungeon.sprites.StatueSprite;
import com.watabou.utils.Random;

public class GrindingLevel extends SewerLevel {

    @Override
    public String tilesTex() {
        return Assets.TILES_BOSS;
    }

    @Override
    public String waterTex() {
        return Assets.WATER_SEWERS;
    }

    public static class Guardian extends Mob {

        {
            spriteClass = StatueSprite.class;

            EXP = 0;
            state = WANDERING;
        }

        public int getScaleFactor() {
            return Dungeon.hero.lvl;
        }

        public Guardian() {
            super();
            HP = HT = 15 + 5 * getScaleFactor();
            aggro(Dungeon.hero);
        }

        @Override
        public int damageRoll() {
            return Random.IntRange(2 + getScaleFactor(), 5 + getScaleFactor()*3);
        }

        @Override
        public int drRoll() {
            return Random.Int(getScaleFactor() + 1);
        }

        @Override
        public int attackSkill(Char target) {
            int scaleFactor;
            if (enemy instanceof Hero) {
                scaleFactor = getScaleFactor();
            } else {
                return enemy.defenseSkill(this);
            }
            return 10 + scaleFactor;
        }

        @Override
        public int defenseSkill(Char enemy) {
            int scaleFactor;
            if (enemy instanceof Hero) {
                scaleFactor = getScaleFactor();
            } else {
                return enemy.defenseSkill(this);
            }
            return 4 + scaleFactor;
        }
    }

    public static class GreenGuardianSprite extends StatueSprite {

        public GreenGuardianSprite(){
            super();
            tint(0, 1, 0, 0.2f);
        }

        @Override
        public void resetColor() {
            super.resetColor();
            tint(0, 1, 0, 0.2f);
        }
    }
}
