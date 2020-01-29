package com.shatteredpixel.cursedpixeldungeon.items.powers;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.npcs.Sheep;
import com.shatteredpixel.cursedpixeldungeon.items.Heap;
import com.shatteredpixel.cursedpixeldungeon.levels.Level;
import com.shatteredpixel.cursedpixeldungeon.levels.Terrain;
import com.shatteredpixel.cursedpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;

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
        } else if (Actor.findChar(cell) != null && shot.dist > 1) {
            dist--;
        }
        shot = new Ballistica(shot.sourcePos, shot.path.get(dist), Ballistica.PROJECTILE);

        for (Integer affectedCell : shot.path) {
            Char ch = Actor.findChar(affectedCell);
            if (ch != null && ch != curUser) {
                Buff.affect(ch, Vertigo.class, Vertigo.DURATION/2f);
            }
            Dungeon.level.press(cell, new Sheep());
            Heap heap = Dungeon.level.heaps.get(cell);
            if (heap != null) {
                heap.pickUp();
            }
        }
    }
}
