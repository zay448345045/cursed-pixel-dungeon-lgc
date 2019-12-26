package com.shatteredpixel.cursedpixeldungeon.items.allies;

import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.cursedpixeldungeon.plants.Earthroot;
import com.shatteredpixel.cursedpixeldungeon.sprites.EarthenDragonSprite;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;

public class EarthenDragon extends DragonCrystal {
    public Dragon dragon = new Dragon();
    {
        image = ItemSpriteSheet.BROWNDRAGONCRYSTAL;
    }

    @Override
    public DragonCrystal.Dragon GetDragonTypeToSpawn() {
        return new EarthenDragon.Dragon();
    }

    public static class Dragon extends DragonCrystal.Dragon {
        {
            spriteClass = EarthenDragonSprite.class;
            immunities.add(Paralysis.class);//immune to paralysis
        }

        public int attackProc(Char enemy, int damage ) {
            damage = super.attackProc( enemy, damage );
            Buff.affect( enemy, Roots.class, 3);

            return damage;

        }

        @Override
        public Class CrystalType() {
            return EarthenDragon.class;
        }

        @Override
        public int defenseProc( Char enemy, int damage ) {
            Buff.affect( this, Roots.class, 3);
            Buff.affect( this, Earthroot.Armor.class ).level( 5 + 4 * this.Crystal.level() );
            return super.defenseProc(enemy, damage);
        }

        @Override
        public int defenseSkill(Char enemy) {
            return super.defenseSkill(enemy)/2;
        }
    }

}