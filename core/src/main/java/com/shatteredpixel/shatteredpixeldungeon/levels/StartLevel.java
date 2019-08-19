package com.shatteredpixel.shatteredpixeldungeon.levels;

import java.util.ArrayList;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Bones;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Shopkeeper;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Wandmaker_2;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.watabou.noosa.Group;

public class StartLevel extends Level {

    {
        color1 = 0x6a723d;
        color2 = 0x88924c;
    }

    //keep track of that need to be removed as the level is changed. We dump 'em back into the level at the end.
    private ArrayList<Item> storedItems = new ArrayList<>();

    @Override
    public String tilesTex() {
        return Assets.TILES_SURFACE;
    }

    @Override
    public String waterTex() {
        return Assets.WATER_SEWERS;
    }

    @Override
    protected boolean build() {

        setSize(32, 32);

        map = MAP_START.clone();

        buildFlagMaps();
        cleanWalls();

        entrance = 25+23*32;
        exit = 17+13*32;


        placeNpc();
        return true;
    }

    private void placeNpc() {
        Mob shopkeeper = new Wandmaker_2();
        shopkeeper.pos = 10 + 11 * 32;
        mobs.add( shopkeeper );
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
            drop( item, exit - 1 ).type = Heap.Type.REMAINS;
        }
    }

    @Override
    public String tileName( int tile ) {
        switch (tile) {
            case Terrain.WATER:
                return Messages.get(SewerLevel.class, "water_name");
            default:
                return super.tileName( tile );
        }
    }

    @Override
    public String tileDesc(int tile) {
        switch (tile) {
            case Terrain.EMPTY_DECO:
                return Messages.get(SewerLevel.class, "empty_deco_desc");
            case Terrain.BOOKSHELF:
                return Messages.get(SewerLevel.class, "bookshelf_desc");
            default:
                return super.tileDesc( tile );
        }
    }

    @Override
    public Group addVisuals() {
        super.addVisuals();
        SewerLevel.addSewerVisuals(this, visuals);
        return visuals;
    }

    private static final int W = Terrain.WALL;
    private static final int D = Terrain.DOOR;
    private static final int L = Terrain.LOCKED_DOOR;
    private static final int e = Terrain.EMPTY;
    private static final int A = Terrain.WATER;
    private static final int m = Terrain.EMPTY_SP;
    private static final int g = Terrain.GRASS;

    private static final int S = Terrain.STATUE;

    private static final int E = Terrain.ENTRANCE;
    private static final int X = Terrain.EXIT;

    private static final int M = Terrain.WALL_DECO;
    private static final int P = Terrain.PEDESTAL;

    //TODO if I ever need to store more static maps I should externalize them instead of hard-coding
    //Especially as I means I won't be limited to legal identifiers
    private static final int[] MAP_START =
            {       W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
                    W, m, m, m, W, e, W, m, m, m, m, m, m, W, e, e, e, e, W, m, m, m, m, m, m, m, D, e, e, e, W, W,
                    W, m, m, m, W, e, W, m, m, m, m, m, m, W, e, e, e, e, W, m, m, m, m, m, m, m, W, e, e, e, W, W,
                    W, W, D, W, W, e, W, W, W, W, W, D, W, W, e, e, e, e, W, m, m, m, m, m, m, m, W, e, e, e, W, W,
                    W, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, D, m, m, m, m, m, m, m, W, e, W, W, W, W,
                    W, W, e, e, W, W, W, W, W, W, W, W, W, W, e, e, e, e, W, m, m, m, m, m, m, m, W, e, e, e, e, W,
                    W, W, e, e, W, m, m, m, m, W, m, m, m, W, e, e, e, e, W, m, m, m, W, W, W, W, W, W, W, W, W, W,
                    W, W, e, e, W, m, m, m, m, W, m, m, m, W, e, e, e, e, W, m, m, m, W, W, W, W, W, W, W, W, W, W,
                    W, W, e, e, W, m, m, m, m, W, m, m, m, W, e, e, e, e, W, m, m, m, W, m, m, m, m, m, m, m, m, W,
                    W, W, e, e, W, m, m, m, m, W, W, W, D, W, e, e, e, e, W, W, W, W, W, m, m, m, m, m, m, m, m, W,
                    W, W, e, e, W, m, m, m, m, W, e, e, e, e, e, e, e, e, e, e, e, e, W, m, m, m, m, m, m, m, m, W,
                    W, W, e, e, W, m, m, m, m, W, e, e, e, e, e, S, S, e, e, e, e, e, W, m, m, m, m, m, m, m, m, W,
                    W, W, e, e, D, m, m, m, m, D, e, e, e, e, e, e, e, e, e, e, e, e, D, m, m, m, m, m, m, m, m, W,
                    W, W, e, e, W, W, W, W, W, W, e, e, e, e, e, e, e, X, e, e, e, e, W, W, D, W, W, W, W, D, W, W,
                    W, e, e, e, e, e, e, e, e, e, e, e, e, e, m, m, m, m, e, e, e, e, e, e, e, e, e, e, e, e, e, W,
                    W, e, e, e, e, e, e, e, e, e, e, S, e, e, m, A, A, m, e, e, S, e, e, e, e, e, e, e, e, e, e, W,
                    W, e, e, e, e, e, e, e, e, e, e, S, e, e, m, A, A, m, e, e, S, e, e, e, e, e, e, e, e, e, e, W,
                    W, e, e, e, e, e, e, e, e, e, e, e, e, e, m, m, m, m, e, e, e, e, e, e, e, e, e, e, e, e, e, W,
                    W, W, W, D, W, W, W, W, W, W, e, e, e, e, e, e, e, e, e, e, e, e, W, W, W, W, W, W, W, W, W, W,
                    W, e, e, e, W, m, m, m, m, W, e, e, e, e, e, e, e, e, e, e, e, e, W, m, m, m, m, m, m, m, m, W,
                    W, e, e, e, W, m, m, m, m, W, e, e, e, e, e, S, S, e, e, e, e, e, D, m, m, m, m, m, m, m, m, W,
                    W, e, e, e, W, m, m, m, m, W, e, e, e, e, e, e, e, e, e, e, e, e, W, m, m, m, m, m, m, m, m, W,
                    W, W, D, W, W, m, m, m, m, W, W, W, D, W, e, e, e, e, W, D, W, W, W, W, W, W, W, m, m, m, m, W,
                    W, m, m, m, W, m, m, m, m, m, m, m, m, W, e, e, e, e, W, m, m, m, m, W, m, E, W, m, m, m, m, W,
                    W, m, m, m, W, m, m, m, m, m, m, m, m, W, e, e, e, e, W, m, m, m, m, W, m, m, W, m, m, m, m, W,
                    W, m, m, m, W, m, m, m, m, m, m, m, m, W, e, e, e, e, W, m, m, m, m, D, m, m, W, W, W, D, W, W,
                    W, m, m, m, W, m, m, m, m, m, m, m, m, W, e, e, e, e, W, m, m, m, m, W, m, m, W, g, g, g, g, W,
                    W, W, W, W, W, m, m, m, m, m, m, m, m, W, e, e, e, e, W, m, m, m, m, W, m, m, W, e, e, e, e, W,
                    W, m, m, m, W, W, W, W, W, W, W, W, W, W, e, e, e, e, W, W, W, W, W, W, W, W, W, g, g, g, g, W,
                    W, m, m, m, D, e, e, e, W, W, W, W, W, W, e, e, e, e, W, W, W, W, W, W, W, W, W, e, e, e, e, W,
                    W, m, m, m, W, e, e, e, e, e, e, e, e, e, e, e, e, e, W, W, W, W, W, W, W, W, W, g, g, g, g, W,
                    W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W};

}
