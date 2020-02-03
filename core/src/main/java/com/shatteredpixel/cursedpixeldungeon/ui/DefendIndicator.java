package com.shatteredpixel.cursedpixeldungeon.ui;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.cursedpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.Image;

public class DefendIndicator extends Tag {

    private Image icon;

    public DefendIndicator() {
        super(0xCDD5C0);

        setSize(24, 24);

        visible = false;

    }

    @Override
    protected void createChildren() {
        super.createChildren();
    }

    @Override
    protected synchronized void layout() {
        super.layout();

        if (icon != null) {
            icon.x = x + (width - icon.width()) / 2;
            icon.y = y + (height - icon.height()) / 2;
            PixelScene.align(icon);
        }
    }

    private synchronized void updateImage() {

        if (icon != null) {
            icon.killAndErase();
            icon = null;
        }

        try {

            icon = new ItemSprite(ItemSpriteSheet.ROUND_SHIELD);

            add(icon);

            icon.x = x + (width - icon.width()) / 2;
            icon.y = y + (height - icon.height()) / 2;
            PixelScene.align(icon);

        } catch (Exception e) {
            ShatteredPixelDungeon.reportException(e);
        }
    }

    @Override
    public synchronized void update() {
        if (!Dungeon.hero.isAlive())
            visible = false;
        else if (Dungeon.hero.visibleEnemies() > 0) {
            if (!visible) {
                visible = true;
                updateImage();
                flash();
            }
        } else {
            if (visible) {
                visible = false;
                updateImage();
            }
        }

        super.update();
    }

    @Override
    protected void onClick() {
        Dungeon.hero.defend();
    }
}