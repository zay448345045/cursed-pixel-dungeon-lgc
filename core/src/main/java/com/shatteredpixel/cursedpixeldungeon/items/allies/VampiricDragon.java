package com.shatteredpixel.cursedpixeldungeon.items.allies;

import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.effects.Pushing;
import com.shatteredpixel.cursedpixeldungeon.effects.Speck;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.enchantments.Grim;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.cursedpixeldungeon.sprites.VampiricDragonSprite;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Random;

public class VampiricDragon extends DragonCrystal {
    public Dragon dragon = new Dragon();
    {
        image = ItemSpriteSheet.LIGHTGREENDRAGONCRYSTAL;
    }

    public static class Dragon extends DragonCrystal.Dragon {
        {
            spriteClass = VampiricDragonSprite.class;
            immunities.add(Grim.class);//immune to Grim
            PassiveRegen = false;
        }

        public int attackProc(Char enemy, int damage ) {
            //chance to heal scales from 25%-75% based on missing HP
            float missingPercent = (HT - HP) / (float) HT;
            float healChance = 0.25f + .50f * missingPercent;

            if (Random.Float() < healChance) {
                int healAmt = Math.round(damage * 0.70f);//Heals 70% of dmg on proc
                healAmt = Math.min(healAmt, HT - HP);

                if (healAmt > 0 && isAlive()) {

                    HP += healAmt;
                    sprite.emitter().start(Speck.factory(Speck.HEALING), 0.4f, 1);
                    sprite.showStatus(CharSprite.POSITIVE, Integer.toString(healAmt));

                }
            }
            return  super.attackProc(enemy, damage);

        }

        @Override
        public int damageRoll() {
            return (int)(super.damageRoll()*0.75);//25% reduced damage (35% was too much imo)
        }
    }

}

