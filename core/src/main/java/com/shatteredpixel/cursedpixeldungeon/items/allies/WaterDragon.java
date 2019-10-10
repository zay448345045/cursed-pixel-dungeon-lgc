package com.shatteredpixel.cursedpixeldungeon.items.allies;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.cursedpixeldungeon.effects.Pushing;
import com.shatteredpixel.cursedpixeldungeon.effects.Speck;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.cursedpixeldungeon.sprites.WaterDragonSprite;
import com.watabou.noosa.tweeners.AlphaTweener;

public class WaterDragon extends DragonCrystal {

    public Dragon dragon = new Dragon();
    {
        image = ItemSpriteSheet.LIGHTBLUEDRAGONCRYSTAL;
    }

    public static class Dragon extends DragonCrystal.Dragon {
        {
            spriteClass = WaterDragonSprite.class;
            immunities.add(Chill.class);//immune to chill
            baseSpeed = 1.5f;
            PassiveRegen = false;
        }

        @Override
        public boolean act() {

            if (Dungeon.level.water[pos] && HP < HT) {
                sprite.emitter().burst( Speck.factory( Speck.STEAM ), 1 );

                HP+= Math.min(HT/5,missingHP());
            }

            return super.act();
        }

        @Override
        public int HPCalc() {
            return 24 + 8 * Crystal.level();
        }

        @Override
        public int defenseSkill(Char enemy) {
            return (Crystal.level()+2);
        }
    }
}
