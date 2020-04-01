package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee;

import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;

import java.util.ArrayList;

public class Chainsaw extends MeleeWeapon {
	{
		image = ItemSpriteSheet.CHAINSAW_HAND;

		tier = 1;

		damageMultiplier = 0.33f;
		DLY = 0.2f;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero)) {
			actions = new ArrayList<>();
		} else {
			actions.remove(AC_EQUIP);
		}
		return actions;
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		Buff.affect(defender, Bleeding.class).set(damage*3);
		return super.proc(attacker, defender, damage);
	}
}
