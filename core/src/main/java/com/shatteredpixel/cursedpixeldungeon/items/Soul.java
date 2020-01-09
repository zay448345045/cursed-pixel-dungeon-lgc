package com.shatteredpixel.cursedpixeldungeon.items;

import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.cursedpixeldungeon.effects.Speck;
import com.shatteredpixel.cursedpixeldungeon.items.rings.RingOfLuck;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class Soul extends Item {
    {
        image = ItemSpriteSheet.SOUL;
        stackable = true;
        unique = true;
        cursed = false;
    }
    private Mob mob = null;

    private static final String AC_CONSUME = "consume";
    private static final String AC_SACRIFICE = "sacrifice";
    private static final String AC_SUMMON = "summon";
    private static final String MOB = "mob";

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_CONSUME);
        actions.add(AC_SACRIFICE);
        actions.add(AC_SUMMON);
        return actions;
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        bundle.put(MOB, mob);
        super.storeInBundle(bundle);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        mob = (Mob) bundle.get(MOB);
        super.restoreFromBundle(bundle);
    }

    private void ACConsume(Hero hero) {
        detach(hero.belongings.backpack);
        hero.spendAndNext(1f);
        if (hero.HP < hero.HT) {
            hero.HP = Math.min( hero.HP + hero.HT/10, hero.HT );
            hero.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
        }
        Buff.affect(hero, Weakness.class, Weakness.DURATION);
    }
    private void ACSacrifice(Hero hero) {
        hero.spendAndNext(1f);
        hero.damage(hero.HT/20, this);
        Buff.prolong(hero, Adrenaline.class, 1 + RingOfLuck.randomInt((int)Adrenaline.DURATION-1, (int)Adrenaline.DURATION));
    }
    private void ACSummon(Hero hero) {
        if (mob != null) {

        }
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        switch (action) {
            case AC_CONSUME:
                ACConsume(hero);
                break;
            case AC_SACRIFICE:
                ACSacrifice(hero);
                break;
            case AC_SUMMON:
                ACSummon(hero);
                break;
        }
    }
}
