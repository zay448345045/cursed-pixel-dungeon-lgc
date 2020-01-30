package com.shatteredpixel.cursedpixeldungeon.items.powers;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.cursedpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.cursedpixeldungeon.effects.Speck;
import com.shatteredpixel.cursedpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.Camera;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;

public class HeroicLeap extends ActivatedPower {

    {
        image = ItemSpriteSheet.HEROICLEAP;
    }

    private static int LEAP_TIME	= 1;
    private static int SHOCK_TIME	= 3;

    @Override
    public boolean usesTargeting() {
        return true;
    }

    @Override
    public void affectCell(int pos) {
        final int dest = pos;
        curUser.busy();
        curUser.sprite.jump(curUser.pos, pos, new Callback() {
            @Override
            public void call() {
                curUser.move(dest);
                Dungeon.level.press(dest, curUser, true);
                Dungeon.observe();
                GameScene.updateFog();

                for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
                    Char mob = Actor.findChar(curUser.pos + PathFinder.NEIGHBOURS8[i]);
                    if (mob != null && mob != curUser) {
                        Buff.prolong(mob, Paralysis.class, SHOCK_TIME);
                    }
                }

                CellEmitter.center(dest).burst(Speck.factory(Speck.DUST), 10);
                Camera.main.shake(2, 0.5f);

                curUser.spendAndNext(LEAP_TIME);
            }
        });
    }

    @Override
    public void fx(Ballistica shot, Callback callback) {
        callback.call();//No zapping animation, animation is handled in affectCell.
    }
}
