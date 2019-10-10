package com.shatteredpixel.cursedpixeldungeon.items.allies;

import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.cursedpixeldungeon.effects.Pushing;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.cursedpixeldungeon.sprites.PoisonDragonSprite;
import com.watabou.noosa.tweeners.AlphaTweener;

public class PoisonDragon extends DragonCrystal {
    public Dragon dragon = new Dragon();
    {
        image  = ItemSpriteSheet.ADORNEDDRAGONCRYSTAL;
    }

    @Override
    public DragonCrystal.Dragon GetDragonTypeToSpawn() {
        return new Dragon();
    }

    public static class Dragon extends DragonCrystal.Dragon {
        {
            spriteClass = PoisonDragonSprite.class;
            properties.add(Property.ACIDIC);
            immunities.add(Poison.class);//immune to poison
        }

        public int attackProc(Char enemy, int damage ) {
            damage = super.attackProc( enemy, damage );
            Buff.affect( enemy, Poison.class ).set( this.HP / 5 );

            return damage;

        }

        @Override
        public Class CrystalType() {
            return PoisonDragon.class;
        }

        @Override
        public int defenseProc( Char enemy, int damage ) {
            enemy.damage(Math.round(damage/2),this);//damages enemies who attack
            return super.defenseProc(enemy, damage);
        }

    }

}
