package com.shatteredpixel.cursedpixeldungeon.items.allies;

import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Frost;
import com.shatteredpixel.cursedpixeldungeon.sprites.DragonSprite;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;

public class FireDragon extends DragonCrystal {
    public Dragon dragon = new Dragon();
    {
        image = ItemSpriteSheet.REDDRAGONCRYSTAL;
    }

    @Override
    public DragonCrystal.Dragon GetDragonTypeToSpawn() {
        return new FireDragon.Dragon();
    }

    public static class Dragon extends DragonCrystal.Dragon {
        {
            properties.add(Property.FIERY);
            immunities.add(Fire.class);//immune to fire because it's... well... fiery?
            spriteClass = DragonSprite.class;
        }

        @Override
        public Class CrystalType() {
            return FireDragon.class;
        }

        public int attackProc(Char enemy, int damage ) {
            damage = super.attackProc( enemy, damage );
            Buff.affect( enemy, Burning.class ).reignite( enemy );

            return damage;

        }

        @Override
        public void add(Buff buff) {
            if (buff instanceof Frost || buff instanceof Chill) {
                damage(Math.round(HP/5),this);//Damages for 20% of max HP when chilled/frozen
            } else {
                super.add(buff);
            }
        }

        @Override
        public int damageRoll() {
            return (int) (super.damageRoll()*1.3);
        }//base of 1-8 (Worn Shortsword), scales by 1-5 (Longsword). This is higher than the base value as Fire dragons should do extra damage (due to setting random fires)


    }
}
