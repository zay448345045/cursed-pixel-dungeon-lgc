package com.shatteredpixel.cursedpixeldungeon.items.instruments;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
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
import com.watabou.noosa.audio.Sample;

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
	private static final int LIMIT		= 16;

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_SONGS);
		actions.add(AC_PLAY);
		return actions;
	}

	public abstract String noteSFX(int note);

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
		private static final int FONT_SIZE	= 10;
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

		Instrument instrument;

		WndPlay(Instrument instrument) {
			this.instrument = instrument;
			IconTitle titlebar = new IconTitle();
			titlebar.icon(new ItemSprite(instrument.image(), null));
			titlebar.label(Messages.titleCase(instrument.name()));
			titlebar.setRect(0, 0, WIDTH, 0);
			add( titlebar );

			sheet = Song.createSheet(0, (int) (titlebar.bottom() + 2), WIDTH, HEIGHT, LIMIT, curSong.toArray(new Integer[0]));
			add( sheet );

			int y = (int) titlebar.bottom() + HEIGHT + BTN_SIZE;
			for (int i = 0; i < instrument.maxNotes; i++) {
				int note = i + 1;
				int x = (int) ((WIDTH) * (i/(float)instrument.maxNotes));
				Key key = new Key(x, y, note, instrument);
				String soundFile = instrument.noteSFX(note);
				Sample.INSTANCE.load(soundFile);
				add(key);
			}
			y += BTN_SIZE;
			RedButton btnClear = new RedButton("Clear") {
				@Override
				protected void onClick() {
					curSong = new ArrayList<>();
					sheet.update(null);
				}
			};
			btnClear.setRect(0, y, WIDTH, BTN_SIZE);
			add(btnClear);
			y+= BTN_SIZE;
			resize(WIDTH, y);
		}

		@Override
		public synchronized void update() {
			if (curSong.size() > LIMIT) {
				curSong = new ArrayList<>();
				sheet.update(null);
			} else {
				sheet.update(curSong.toArray(new Integer[0]));
			}
			for (Song s : Song.values()) {
				if (Arrays.equals(curSong.toArray(new Integer[0]), s.melody()) && s.unlocked()) {
					GLog.p("Played " + s.getName() + ".");
					Dungeon.hero.spend(1f);
					Dungeon.hero.busy();
					s.play(instrument, Dungeon.hero);
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
			private String soundFile;

			public Key(int x, int y, int number, Instrument instrument) {
				super(Song.getNote(number));
				this.number = number;
				setSize(BTN_SIZE, BTN_SIZE);
				soundFile = instrument.noteSFX(number);
				setPos(x, y);
			}

			@Override
			protected void onClick() {
				curSong.add(number);
				WndPlay.this.update();
				Sample.INSTANCE.play(soundFile);
			}
		}
	}
}
