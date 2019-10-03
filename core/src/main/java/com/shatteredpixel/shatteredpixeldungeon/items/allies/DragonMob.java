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
    int regenDelay = 0;
    boolean PassiveRegen = true;
    {
        spriteClass = DragonSprite.class;
        this.alignment = Char.Alignment.ALLY;
        defenseSkill = Dungeon.depth;
        state = WANDERING;

        HP = HT = HPcalc(SpawnerLevel);
        baseSpeed = 1f;
    }

    @Override
    public int attackSkill( Char target ) {
        return 10 + SpawnerLevel;
    }

    public void updateStats(int lvl) {
        this.alignment = Char.Alignment.ALLY;
        defenseSkill = Dungeon.depth;
        state = WANDERING;

        HP = HT = HPcalc(lvl);
        baseSpeed = 1f;
    }

    public final String LEVEL = "level";

    public void setLevel (int level) {
        SpawnerLevel = level;
    }

    public boolean Spawned() {
        return Spawned;
    }

    public int HPcalc(int level) {
        return  20 + 10 * level;
    }

    @Override
    public void storeInBundle (Bundle bundle) {
        bundle.put(LEVEL, SpawnerLevel);
        super.storeInBundle(bundle);
    }

    @Override
    public void restoreFromBundle (Bundle bundle) {
        SpawnerLevel = bundle.getInt(LEVEL);
        super.restoreFromBundle(bundle);
    }

    @Override
    public void die( Object cause ) {
        super.die(cause);
        Spawned = false;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(1 + SpawnerLevel, 8 + SpawnerLevel * 4);
    }//base of 1-8 (Worn Shortsword), scales by 1-3 (Sword)

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0 + SpawnerLevel, 2 + SpawnerLevel * 4);
    }//base of 0-2 (Cloth Armour), scales by 1-3 (Mail Armour)

    @Override
    public boolean interact() {
        int curPos = pos;
        this.state = WANDERING;//Wake the Dragon up
        moveSprite(pos, Dungeon.hero.pos);
        move(Dungeon.hero.pos);

        Dungeon.hero.sprite.move(Dungeon.hero.pos, curPos);
        Dungeon.hero.move(curPos);

        Dungeon.hero.spend(1 / Dungeon.hero.speed());
        Dungeon.hero.busy();
        return true;

    }

    @Override
    public int defenseSkill(Char enemy) {
        return 2*(SpawnerLevel+2);
    }

    @Override
    public boolean act() {
        if (PassiveRegen) {
            regenDelay += 1;
            if (regenDelay > 9) {
                regenDelay = 0;
                HP += Math.min(HT / 10, this.missingHP());
            }
        }
        return super.act();
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
