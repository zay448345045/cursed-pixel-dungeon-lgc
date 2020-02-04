package com.shatteredpixel.cursedpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.cursedpixeldungeon.Statistics;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.shatteredpixel.cursedpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.Hau_tulSprite;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.cursedpixeldungeon.ui.RedButton;
import com.shatteredpixel.cursedpixeldungeon.ui.RenderedTextMultiline;
import com.shatteredpixel.cursedpixeldungeon.ui.Window;
import com.shatteredpixel.cursedpixeldungeon.windows.IconTitle;
import com.shatteredpixel.cursedpixeldungeon.windows.WndMessage;

public class Hau_tul extends NPC {
    /*
    NPC on floor 0

    Increases magic skill of hero at the cost of max HP and Gold.
     */

    {
        spriteClass = Hau_tulSprite.class;

        properties.add(Property.IMMOVABLE);
    }

    @Override
    public boolean interact() {
        if (Dungeon.hero != null) {
            GameScene.show(new WndHau_tul(Dungeon.hero));
            return true;
        }
        return false;
    }

    public static class WndHau_tul extends Window {

        private static final int WIDTH = 125;
        private static final int BTN_HEIGHT = 20;
        private static final float GAP = 2;

        public WndHau_tul(final Hero hero) {
            super();
            IconTitle titlebar = new IconTitle();
            titlebar.icon(new ItemSprite(ItemSpriteSheet.LORSIONSGREATAXE+1));
            titlebar.label(Messages.titleCase(Messages.get(Hau_tul.class, "name")));
            titlebar.setRect(0, 0, WIDTH, 0);
            add(titlebar);

            String msg = Messages.get(Hau_tul.class, "chat");

            RenderedTextMultiline message = PixelScene.renderMultiline(msg, 6);
            message.maxWidth(WIDTH);
            message.setPos(0, titlebar.bottom() + GAP);
            add(message);

            final int cost = (int) (Math.pow(2, Statistics.rituals)) * 100;

            RedButton btnRitual = new RedButton(Messages.get(Hau_tul.class, "increase_magic", cost)) {
                @Override
                protected void onClick() {
                    hero.increaseMagicSkill(1);
                    Statistics.rituals++;
                    Dungeon.gold -= cost;
                    hero.updateHT(false);
                    hero.sprite.emitter().burst( ShadowParticle.CURSE, 25 );
                    hide();
                }
            };
            btnRitual.enable(Dungeon.gold >= cost && hero.HT > 15+hero.lvl*5);
            btnRitual.setRect(0, message.top() + message.height() + GAP, WIDTH, BTN_HEIGHT);
            add(btnRitual);

            RedButton btnGormel = new RedButton(Messages.get(Hau_tul.class, "ritual_of_gormel")) {
                @Override
                protected void onClick() {
                    ShatteredPixelDungeon.scene().add(new WndMessage(Messages.get(Hau_tul.class, "ritual_of_gormel_info")));
                }
            };
            btnGormel.setRect(0, btnRitual.bottom(), WIDTH, BTN_HEIGHT);
            add(btnGormel);

            RedButton btnHell = new RedButton(Messages.get(Hau_tul.class, "hell")) {
                @Override
                protected void onClick() {
                    ShatteredPixelDungeon.scene().add(new WndMessage(Messages.get(Hau_tul.class, "hell_info")));
                }
            };
            btnHell.setRect(0, btnGormel.bottom(), WIDTH, BTN_HEIGHT);
            add(btnHell);
            resize(WIDTH, (int) btnHell.bottom());
        }
    }
}
