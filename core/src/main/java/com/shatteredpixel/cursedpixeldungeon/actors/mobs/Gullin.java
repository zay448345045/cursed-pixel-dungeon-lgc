package com.shatteredpixel.cursedpixeldungeon.actors.mobs;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.items.Generator;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicMeleeWeapon;
import com.watabou.utils.Random;

public class Gullin extends Kupua {
    {
        loot = Generator.random(Generator.Category.WEP_T6);
        lootChance = 0.5f;
        HP = HT = 300;
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        Buff.affect(enemy, Bleeding.class).set(Dungeon.scaleWithDepth()*2);//Heavy bleeding
        return super.attackProc(enemy, damage);
    }

    @Override
    public void damage(int dmg, Object src) {
        if (src instanceof Hero) {
            KindOfWeapon wep = ((Hero)src).belongings.weapon;
            if (wep instanceof MeleeWeapon & !(wep instanceof RelicMeleeWeapon)) {
                dmg = Random.Int(dmg);
            }
        } else if (src instanceof Item & !(src instanceof RelicMeleeWeapon)) {
            dmg = Random.Int(dmg);
        }
        super.damage(dmg, src);
    }
}
