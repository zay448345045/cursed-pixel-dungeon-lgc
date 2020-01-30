package com.shatteredpixel.cursedpixeldungeon.items.powers;

import com.shatteredpixel.cursedpixeldungeon.Assets;
import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.MagicImmune;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.cursedpixeldungeon.items.wands.Wand;
import com.shatteredpixel.cursedpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.shatteredpixel.cursedpixeldungeon.ui.QuickSlotButton;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

import java.util.ArrayList;

public abstract class ActivatedPower extends Power {

    {
        defaultAction = AC_CAST;
    }

    public static final String AC_CAST = "CAST";
    private static final float TIME_TO_ZAP = 1.0f;

    public int mp_cost = -1;

    protected int collisionProperties = Ballistica.STOP_TARGET | Ballistica.STOP_TERRAIN;

    ActivatedPower() {
        usesTargeting = usesTargeting();
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_CAST);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if (action.equals(AC_CAST)) {
            activatePower(hero);
        }
    }

    public void activatePower(Hero hero) {
        if (usesTargeting()) {
            GameScene.selectCell(zapper);
        } else {
            onUse();
            affectCell(hero.pos);
        }
    }

    public abstract boolean usesTargeting();

    protected static CellSelector.Listener zapper = new  CellSelector.Listener() {

        @Override
        public void onSelect( Integer target ) {

            if (target != null) {

                //FIXME this safety check shouldn't be necessary
                //it would be better to eliminate the curItem static variable.
                final ActivatedPower curPower;
                if (curItem instanceof ActivatedPower) {
                    curPower = (ActivatedPower) ActivatedPower.curItem;
                } else {
                    return;
                }

                final Ballistica shot = new Ballistica(curUser.pos, target, curPower.collisionProperties);
                int cell = shot.collisionPos;

                if (target == curUser.pos || cell == curUser.pos) {
                    GLog.i(Messages.get(Wand.class, "self_target"));
                    return;
                } else if (curUser.buff(MagicImmune.class) != null) {
                    GLog.w(Messages.get(Wand.class, "no_magic"));
                    return;
                }

                curUser.sprite.zap(cell);

                //attempts to target the cell aimed at if something is there, otherwise targets the collision pos.
                if (Actor.findChar(target) != null) {
                    QuickSlotButton.target(Actor.findChar(target));
                } else {
                    QuickSlotButton.target(Actor.findChar(cell));
                }

                curPower.fx(shot, new Callback() {
                    public void call() {
                        curPower.onZap(shot);
                        curPower.onUse();
                    }
                });

            }
        }

        @Override
        public String prompt() {
            return Messages.get(Wand.class, "prompt");
        }
    };

    public void onUse() {
        if (mp_cost > 0) {
            curUser.loseMP(mp_cost, this);
        }
        curUser.spendAndNext( TIME_TO_ZAP );
    }

    public abstract void affectCell(int pos);

    public void onZap(Ballistica shot) {
        affectCell(shot.collisionPos);
    }

    public void fx(Ballistica shot, Callback callback) {
        MagicMissile.boltFromChar( curUser.sprite.parent,
                MagicMissile.MAGIC_MISSILE,
                curUser.sprite,
                shot.collisionPos,
                callback);
        Sample.INSTANCE.play( Assets.SND_ZAP );
    }
}
