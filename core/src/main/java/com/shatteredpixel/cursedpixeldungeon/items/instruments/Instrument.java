package com.shatteredpixel.cursedpixeldungeon.items.instruments;

import com.shatteredpixel.cursedpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.cursedpixeldungeon.ui.RedButton;
import com.shatteredpixel.cursedpixeldungeon.ui.RenderedTextMultiline;
import com.shatteredpixel.cursedpixeldungeon.ui.Window;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;
import com.shatteredpixel.cursedpixeldungeon.windows.IconTitle;
import com.watabou.noosa.Group;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Instrument extends Item {
	{
		image = ItemSpriteSheet.HARP;

		defaultAction = AC_PLAY;
	}

	protected int maxNotes;

	private static final String AC_PLAY = "play";
	private static final String AC_SONGS = "songs";

	//Max song length
	private static final int LIMIT		= 8;

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_SONGS);
		actions.add(AC_PLAY);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		if (action.equals(AC_PLAY)) {
			ShatteredPixelDungeon.scene().addToFront(new WndPlay(this));
		}
		if (action.equals(AC_SONGS)) {
			ShatteredPixelDungeon.scene().addToFront(new WndSongList(this));
		}
	}

	private static class WndSongList extends Window {
		private static final int WIDTH		= 120;
		private static final int HEIGHT		= 50;
		private static final int FONT_SIZE		= 10;
		private static final float GAP		= 2;
		WndSongList(Instrument instrument) {
			IconTitle titlebar = new IconTitle();
			titlebar.icon(new ItemSprite(instrument.image(), null));
			titlebar.label(Messages.titleCase(instrument.name()));
			titlebar.setRect(0, 0, WIDTH, 0);
			add( titlebar );
			int bottom = (int) titlebar.bottom();
			for (Song song : Song.values()) {
				if (song.unlocked()) {
					/*RenderedTextMultiline message = getMessage(song);
					message.maxWidth(WIDTH);
					message.setPos(0, bottom + GAP);
					add(message);*/
					RenderedTextMultiline text = PixelScene.renderMultiline("_" + song.getName() + "_", FONT_SIZE);
					text.setPos(0, bottom);
					add(text);
					add(Song.createSheet(0, bottom + FONT_SIZE, WIDTH, HEIGHT, song.melody().length, song.melody()));
					bottom += HEIGHT + GAP + FONT_SIZE;
				}
			}
			resize(WIDTH, bottom);
		}

	}

	private static class WndPlay extends Window {
		private static final int WIDTH		= 120;
		private static final int HEIGHT		= 50;
		private static final int BTN_SIZE = 20;

		public static ArrayList<Integer> curSong = new ArrayList<>();

		Song.Sheet sheet;

		WndPlay(Instrument instrument) {
			IconTitle titlebar = new IconTitle();
			titlebar.icon(new ItemSprite(instrument.image(), null));
			titlebar.label(Messages.titleCase(instrument.name()));
			titlebar.setRect(0, 0, WIDTH, 0);
			add( titlebar );

			//message = PixelScene.renderMultiline(Song.createSheet(curSong.toArray(new Integer[0])), 10 );
			//message.maxWidth(WIDTH);
			//message.setPos(0, titlebar.bottom() + 2);
			sheet = Song.createSheet(0, (int) (titlebar.bottom() + 2), WIDTH, HEIGHT, LIMIT, curSong.toArray(new Integer[0]));
			add( sheet );

			int y = (int) titlebar.bottom() + HEIGHT + BTN_SIZE;
			for (int i = 0; i < instrument.maxNotes; i++) {
				int x = (int) ((WIDTH) * (i/(float)instrument.maxNotes));
				Key key = new Key(x, y, i+1);
				add(key);
			}
			resize(WIDTH, (int) (titlebar.bottom() + HEIGHT + BTN_SIZE*2));
		}

		@Override
		public synchronized void update() {
			if (curSong.size() > LIMIT) {
				curSong = new ArrayList<>();
				sheet.update(null);
			} else {
				sheet.update(curSong.toArray(new Integer[0]));
			}
			//message.text(Song.createSheet(curSong.toArray(new Integer[0])));
			//message.update();
			for (Song s : Song.values()) {
				if (Arrays.equals(curSong.toArray(new Integer[0]), s.melody()) && s.unlocked()) {
					GLog.p("Played " + s.getName() + ".");
					s.onPlay();
					hide();
				}
			}
		}

		@Override
		public void hide() {
			super.hide();
			curSong = new ArrayList<>();
		}

		private class Key extends RedButton {
			private int number;
			public Key(int x, int y, int number) {
				super(Song.getNote(number));
				this.number = number;
				setSize(BTN_SIZE, BTN_SIZE);

				setPos(x, y);
			}

			@Override
			protected void onClick() {
				curSong.add(number);
				WndPlay.this.update();
			}
		}
	}
}
