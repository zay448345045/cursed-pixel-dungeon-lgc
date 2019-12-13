package com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicEnchantments;

import com.cursedpixel.curseddungeon.R;
import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.cursedpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.HeroAction;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.cursedpixeldungeon.effects.particles.FlameParticle;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.enchantments.Blazing;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.enchantments.Grim;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RelicMeleeWeapons.RelicMeleeWeapon;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Inferno extends RelicEnchantment {

    private static ItemSprite.Glowing ORANGE = new ItemSprite.Glowing( 0xFF4400 );

    @Override
    public int relicProc(RelicMeleeWeapon weapon, Char attacker, Char defender, int damage ) {
        // lvl 0 - 33%
        // lvl 1 - 50%
        // lvl 2 - 60%
        int bonusdamage = weapon.damageRoll(attacker)/5;
        for (int n : PathFinder.NEIGHBOURS9) {
            int pos = defender.pos + n;
            Char enemy = Actor.findChar(pos);
            if (enemy != null & (Random.Int(2) == 0 | enemy == defender)) {//Guaranteed to burn the target, may burn adjacent mobs as well.
                if (defender.buff(Burning.class) != null) {
                    Buff.affect(defender, Burning.class).reignite(defender, 8f);
                    defender.damage(bonusdamage, this);
                } else {
                    Buff.affect(defender, Burning.class).reignite(defender, 8f);
                }
            } else {
                if (Random.Int(2) == 0 & pos != attacker.pos) {
                    GameScene.add(Blob.seed(pos, 3, Fire.class));
                }
            }
        }
        for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
            if (attacker.fieldOfView[mob.pos] & Random.Int(4) == 0) {
                GameScene.add(Blob.seed(mob.pos, 3, Fire.class));
            }
        }


        defender.sprite.emitter().burst(FlameParticle.FACTORY, (weapon.level()+3)*3);
        return damage;

    }

    @Override
    public void activate(RelicMeleeWeapon weapon, Char owner) {
        super.activate(weapon,owner);
        for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
            int damage = mob.HT/2;
            if (mob.properties().contains(Char.Property.BOSS)) {
                damage /=4;
            }
            mob.damage(damage, Fire.class);
            GameScene.add(Blob.seed(mob.pos, 3, Fire.class));
        }
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return ORANGE;
    }
}
