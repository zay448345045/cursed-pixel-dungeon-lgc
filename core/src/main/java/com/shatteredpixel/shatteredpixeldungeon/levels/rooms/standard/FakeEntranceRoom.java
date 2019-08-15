package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard;

import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.Room;

public class FakeEntranceRoom extends EntranceRoom{
    @Override
    public void paint(Level level) {
        Painter.fill(level, this, Terrain.WALL);
        Painter.fill(level, this, 1, Terrain.EMPTY);

        for (
                Room.Door door : connected.values()) {
            door.set(Room.Door.Type.REGULAR);
        }

        level.exit = level.pointToCell(random(2));
        Painter.set(level, level.entrance, Terrain.PEDESTAL);
    }
}
