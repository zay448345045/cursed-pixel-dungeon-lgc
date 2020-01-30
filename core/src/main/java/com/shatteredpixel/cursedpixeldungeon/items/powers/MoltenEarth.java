package com.shatteredpixel.cursedpixeldungeon.items.powers;

import com.shatteredpixel.cursedpixeldungeon.Assets;
import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.cursedpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class MoltenEarth extends ActivatedPower {

    {
        image = ItemSpriteSheet.MOLTENEARTH;
        mp_cost = 3;
    }

    @Override
    public boolean usesTargeting() {
        return false;
    }

    @Override
    public void affectCell(int pos) {
        for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
            if (Dungeon.level.heroFOV[mob.pos]) {
                Buff.affect( mob, Burning.class ).reignite( mob );
                Buff.prolong( mob, Roots.class, 3 );
            }
        }

        curUser.sprite.centerEmitter().start( ElmoParticle.FACTORY, 0.15f, 4 );
        Sample.INSTANCE.play( Assets.SND_READ );
    }
}
