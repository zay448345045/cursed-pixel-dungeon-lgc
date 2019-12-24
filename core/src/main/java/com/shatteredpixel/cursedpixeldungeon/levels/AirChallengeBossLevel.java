package com.shatteredpixel.cursedpixeldungeon.levels;

import com.shatteredpixel.cursedpixeldungeon.Assets;
import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Shinobi;
import com.shatteredpixel.cursedpixeldungeon.items.keys.IronKey;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.watabou.noosa.Group;

public class AirChallengeBossLevel extends Level {
    {
        color1 = 0x6a723d;
        color2 = 0x88924c;
    }

    @Override
    public String waterTex() {
        return Assets.WATER_PRISON;
    }

    @Override
    public String tilesTex() {
        return Assets.TILES_PRISON;
    }

    @Override
    protected boolean build() {

        setSize(48, 48);

        map = Layouts.AIR_CHALLENGE_BOSS_START.clone();

        buildFlagMaps();
        cleanWalls();

        entrance = 23 + width * 6;
        exit = 0;

        return true;
    }

    @Override
    protected void createMobs() {
        Shinobi guard = (Shinobi) createMob();
        guard.pos = 23 + width * 15;
        mobs.add(guard);
    }

    @Override
    protected void createItems() {
        int cell = 23 + width * 15;
        drop(new IronKey(Dungeon.depth), cell);
    }

    @Override
    public Mob createMob() {
        return new Shinobi();
    }

    @Override
    public String tileName(int tile) {
        if (tile == Terrain.WATER) {
            return Messages.get(SewerLevel.class, "water_name");
        }
        return super.tileName(tile);
    }

    @Override
    public String tileDesc(int tile) {
        switch (tile) {
            case Terrain.EMPTY_DECO:
                return Messages.get(SewerLevel.class, "empty_deco_desc");
            case Terrain.BOOKSHELF:
                return Messages.get(SewerLevel.class, "bookshelf_desc");
            default:
                return super.tileDesc(tile);
        }
    }

    @Override
    public Group addVisuals() {
        super.addVisuals();
        SewerLevel.addSewerVisuals(this, visuals);
        return visuals;
    }
}
