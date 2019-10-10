package com.shatteredpixel.cursedpixeldungeon.items.allies;

import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.cursedpixeldungeon.effects.Pushing;
import com.shatteredpixel.cursedpixeldungeon.plants.Earthroot;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.EarthenDragonSprite;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.tweeners.AlphaTweener;

public class EarthenDragon extends DragonCrystal {
    public Dragon dragon = new Dragon();
    {
        image = ItemSpriteSheet.BROWNDRAGONCRYSTAL;
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