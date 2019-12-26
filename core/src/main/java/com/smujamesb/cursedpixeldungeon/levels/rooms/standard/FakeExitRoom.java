package com.smujamesb.cursedpixeldungeon.levels.rooms.standard;

import com.smujamesb.cursedpixeldungeon.levels.Level;
import com.smujamesb.cursedpixeldungeon.levels.Terrain;
import com.smujamesb.cursedpixeldungeon.levels.painters.Painter;
import com.smujamesb.cursedpixeldungeon.levels.rooms.Room;

public class FakeExitRoom extends ExitRoom {
    @Override
    public void paint(Level level) {

        Painter.fill( level, this, Terrain.WALL );
        Painter.fill( level, this, 1, Terrain.EMPTY );

        for (Room.Door door : connected.values()) {
            door.set( Room.Door.Type.REGULAR );
        }

        level.exit = level.pointToCell(random( 2 ));
        Painter.set( level, level.exit, Terrain.PEDESTAL );
    }
}
