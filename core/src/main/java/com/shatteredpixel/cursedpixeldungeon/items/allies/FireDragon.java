package com.shatteredpixel.cursedpixeldungeon.items.allies;

import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Frost;
import com.shatteredpixel.cursedpixeldungeon.effects.Pushing;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Random;

public class FireDragon extends DragonItem {
    public Dragon dragon = new Dragon();
    private static ItemSprite.Glowing ORANGE = new ItemSprite.Glowing( 0xFF4400 );
    public static class Dragon extends DragonMob {
        {
            properties.add(Property.FIERY);
            immunities.add(Fire.class);//immune to fire because it's... well... fiery?
        }

        public int attackProc(Char enemy, int damage ) {
            damage = super.attackProc( enemy, damage );
            Buff.affect( enemy, Burning.class ).reignite( enemy );

            return damage;

        }

        @Override
        public void add(Buff buff) {
            if (buff instanceof Frost || buff instanceof Chill) {
                damage(Math.round(HP/5),this);//Damages for 10% of max HP when chilled/frozen
            } else {
                super.add(buff);
            }
        }

        @Override
        public int damageRoll() {
            return Random.NormalIntRange(1 + SpawnerLevel, 8 + SpawnerLevel * 5);
        }//base of 1-8 (Worn Shortsword), scales by 1-4 (Longsword). This is higher than the base value as Fire dragons should do extra damage (due to setting random fires)


    }

    @Override
    public void SpawnDragon(int newPos, int pos) {
        dragon = new Dragon();
        dragon.SpawnerLevel = level();
        dragon.HP = dragon.HT = dragon.HPcalc(dragon.SpawnerLevel);
        dragon.alignment = Char.Alignment.ALLY;
        dragon.pos = newPos;
        dragon.setLevel(level());

        GameScene.add( dragon );
        Actor.addDelayed( new Pushing( dragon, pos, newPos ), -1f );

        dragon.sprite.alpha( 0 );
        dragon.sprite.parent.add( new AlphaTweener( dragon.sprite, 1, 0.15f ) );
        dragon.notice();
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return ORANGE;
    }
}
