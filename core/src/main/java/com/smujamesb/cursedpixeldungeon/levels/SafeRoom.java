package com.smujamesb.cursedpixeldungeon.levels;

import com.smujamesb.cursedpixeldungeon.Assets;
import com.smujamesb.cursedpixeldungeon.Bones;
import com.smujamesb.cursedpixeldungeon.actors.Actor;
import com.smujamesb.cursedpixeldungeon.actors.mobs.Mob;
import com.smujamesb.cursedpixeldungeon.items.Heap;
import com.smujamesb.cursedpixeldungeon.items.Item;
import com.smujamesb.cursedpixeldungeon.messages.Messages;
import com.watabou.noosa.Group;

public class SafeRoom extends Level {

    {
        color1 = 0x6a723d;
        color2 = 0x88924c;
    }

    @Override
    public String tilesTex() {
        return Assets.TILES_PRISON;
    }

    @Override
    public String waterTex() {
        return Assets.WATER_PRISON;
    }

    @Override
    protected boolean build() {

        setSize(48, 48);

        map = Layouts.SAFE_ROOM_DEFAULT.clone();

        buildFlagMaps();
        cleanWalls();

        entrance = 23 + width * 15;
        exit = 0;

        return true;
    }

    @Override
    public Mob createMob() {
        return null;
    }

    @Override
    protected void createMobs() {
    }

    public Actor respawner() {
        return null;
    }

    @Override
    protected void createItems() {
        Item item = Bones.get();
        if (item != null) {
            drop(item, exit - 1).type = Heap.Type.REMAINS;
        }
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