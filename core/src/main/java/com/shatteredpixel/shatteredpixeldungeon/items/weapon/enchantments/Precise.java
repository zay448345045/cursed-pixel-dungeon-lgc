/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class Precise extends Weapon.Enchantment {
    {
        testing = false;
    }

    private static ItemSprite.Glowing WHITE = new ItemSprite.Glowing( 0xFFFFFF );
    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        testing = true;
        float evasion = defender.defenseSkill(attacker);
        float accuracy = attacker.attackSkill(defender);
        testing = false;

        float hitChance;
        if (evasion >= accuracy){
            hitChance = (accuracy/evasion)/2f;
        } else {
            hitChance = 1f - (evasion/accuracy)/2f;
        }

        //75% of dodge chance is applied as damage reduction
        hitChance = (1f + 3f*hitChance)/4f;

        damage = (int)Math.ceil(damage * hitChance);

        return damage;
    }


    @Override
    public Glowing glowing() {
        return WHITE;
    }
}