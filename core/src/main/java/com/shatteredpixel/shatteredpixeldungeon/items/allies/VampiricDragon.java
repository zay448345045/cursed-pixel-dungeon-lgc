package com.shatteredpixel.shatteredpixeldungeon.items.allies;

import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Grim;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.PoisonDragonSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.VampiricDragonSprite;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Random;

public class VampiricDragon extends DragonItem {
    public Dragon dragon = new Dragon();
    private static ItemSprite.Glowing GREEN = new ItemSprite.Glowing( 0x00CC07 );

    public static class Dragon extends DragonMob {
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

                //heals for 25% of damage dealt
                int healAmt = Math.round(damage * 0.50f);
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
            return (int)(super.damageRoll()*0.65);//35% reduced damage
        }
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
        return GREEN;
    }

}

