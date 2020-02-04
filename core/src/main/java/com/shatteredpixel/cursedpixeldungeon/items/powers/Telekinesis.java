package com.shatteredpixel.cursedpixeldungeon.items.powers;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.npcs.Sheep;
import com.shatteredpixel.cursedpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.cursedpixeldungeon.items.Heap;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.cursedpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;
import com.watabou.utils.Callback;

public class Telekinesis extends ActivatedPower {
    {
        image = ItemSpriteSheet.TELEKINESIS;
        mp_cost = 4;
    }

    @Override
    public boolean usesTargeting() {
        return true;
    }

    @Override
    public void affectCell(int pos) {}

    @Override
    public void onZap(Ballistica shot) {
        int level = curUser.lvl;

        int cell = shot.collisionPos;

        int dist = shot.dist;
        if (shot.dist > level/5 + 4) {
            dist = level + 4;
        } else if (Actor.findChar(cell) != null) {
            dist--;
        }
        shot = new Ballistica(shot.sourcePos, shot.path.get(dist), Ballistica.PROJECTILE);

        for (Integer affectedCell : shot.path) {
            Char ch = Actor.findChar(affectedCell);
            if (ch != null && ch != curUser) {
                WandOfBlastWave.throwChar(ch, shot, 3 );
            }
            Dungeon.level.press(affectedCell, null, true);
            Heap heap = Dungeon.level.heaps.get(affectedCell);
            if (heap != null && heap.type == Heap.Type.HEAP) {
                for (Item item : heap.items) {
                    if (!item.collect()) {
                        Dungeon.level.drop(item, heap.pos).sprite.drop();
                    }
                }
                heap.pickUp();
            }
        }
    }

    @Override
    public void fx(Ballistica shot, Callback callback) {
        MagicMissile.boltFromChar(
                curUser.sprite.parent,
                MagicMissile.SPIRAL,
                curUser.sprite,
                shot.collisionPos,
                callback);
    }
}
