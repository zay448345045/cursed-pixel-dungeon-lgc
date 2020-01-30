package com.shatteredpixel.cursedpixeldungeon.items.powers;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.missiles.Shuriken;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.cursedpixeldungeon.sprites.MissileSprite;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;
import com.watabou.utils.Callback;

import java.util.HashMap;

public class SpectralBlades extends ActivatedPower {

    {
        image = ItemSpriteSheet.SPECTRALBLADES;
        mp_cost = 8;
    }

    @Override
    public boolean usesTargeting() {
        return false;
    }

    private HashMap<Callback, Mob> targets = new HashMap<Callback, Mob>();

    @Override
    public void affectCell(int pos) {
        Item proto = new Shuriken();

        for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
            if (Dungeon.level.distance(curUser.pos, mob.pos) <= 12) {

                Callback callback = new Callback() {
                    @Override
                    public void call() {
                        curUser.attack( targets.get( this ) );
                        targets.remove( this );
                    }
                };

                ((MissileSprite)curUser.sprite.parent.recycle( MissileSprite.class )).
                        reset( curUser.pos, mob.pos, proto, callback );

                targets.put( callback, mob );
            }
        }

        if (targets.size() == 0) {
            GLog.w( Messages.get(this, "no_enemies") );
            return;
        }

        curUser.sprite.zap( curUser.pos );
        curUser.busy();
    }
}
