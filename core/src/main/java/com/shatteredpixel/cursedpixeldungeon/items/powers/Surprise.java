package com.shatteredpixel.cursedpixeldungeon.items.powers;

import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.cursedpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.cursedpixeldungeon.effects.Wound;
import com.shatteredpixel.cursedpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class Surprise extends ActivatedPower {

    {
        image = ItemSpriteSheet.SURPRISE;

        mp_cost = 5;
    }

    @Override
    public boolean usesTargeting() {
        return true;
    }

    @Override
    public void affectCell(int pos) {
        Char ch = Actor.findChar(pos);
        int damage;
        if (ch != null) {
            damage = Random.NormalIntRange(curUser.magicSkill, curUser.magicSkill*3);
            if (ch instanceof Mob && ((Mob)ch).surprisedBy(curUser)) {
                damage *= 3;
                Wound.hit(ch);
            }
            ch.damage(damage, this);
        }
    }

    @Override
    public void fx(Ballistica shot, Callback callback) {
        MagicMissile.boltFromChar( curUser.sprite.parent,
                MagicMissile.SLICE,
                curUser.sprite,
                shot.collisionPos,
                callback);
    }
}
