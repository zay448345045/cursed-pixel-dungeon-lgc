package com.shatteredpixel.shatteredpixeldungeon.items.allies;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.NPC;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DragonSprite;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndQuest;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public abstract class DragonMob extends NPC {
    int SpawnerLevel = 0;
    boolean Spawned = true;

    {
        spriteClass = DragonSprite.class;
        this.alignment = Char.Alignment.ALLY;
        defenseSkill = Dungeon.depth;
        state = WANDERING;

        HP = HT = 8 + 4 * SpawnerLevel;
    }

    @Override
    public int attackSkill( Char target ) {
        return 10 + SpawnerLevel;
    }

    public void setLevel (int level) {
        SpawnerLevel = level;
    }

    public boolean Spawned() {
        return Spawned;
    }

    @Override
    public void storeInBundle (Bundle bundle) {
        super.storeInBundle(bundle);
    }

    @Override
    public void restoreFromBundle (Bundle bundle) {
        super.restoreFromBundle(bundle);
    }

    @Override
    public void die( Object cause ) {
        super.die(cause);
        Spawned = false;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(1 + SpawnerLevel, 8 + SpawnerLevel * 3);
    }//base of 1-8 (Worn Shortsword), scales by 1-3 (Sword)

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0 + SpawnerLevel, 2 + SpawnerLevel * 3);
    }//base of 0-2 (Cloth Armour), scales by 1-3 (Mail Armour)

    @Override
    public boolean interact() {
        int curPos = pos;

        moveSprite(pos, Dungeon.hero.pos);
        move(Dungeon.hero.pos);

        Dungeon.hero.sprite.move(Dungeon.hero.pos, curPos);
        Dungeon.hero.move(curPos);

        Dungeon.hero.spend(1 / Dungeon.hero.speed());
        Dungeon.hero.busy();
        return true;

    }

    @Override
    protected boolean getCloser(int target) {
        if (alignment == Char.Alignment.ALLY && enemy == null && buff(Corruption.class) == null){
            target = Dungeon.hero.pos;
        } else if (enemy != null) {
            target = enemy.pos;
        } else if ((state == WANDERING))
            this.target = target = Dungeon.hero.pos;
        return super.getCloser( target );
    }

}
