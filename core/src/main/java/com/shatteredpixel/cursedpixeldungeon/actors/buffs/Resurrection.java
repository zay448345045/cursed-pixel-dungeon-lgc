package com.shatteredpixel.cursedpixeldungeon.actors.buffs;

import com.shatteredpixel.cursedpixeldungeon.messages.Messages;

public class Resurrection extends Buff {
    {
        announced = true;
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }
}
