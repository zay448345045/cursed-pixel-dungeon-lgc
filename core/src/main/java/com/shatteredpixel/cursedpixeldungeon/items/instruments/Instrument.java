package com.shatteredpixel.cursedpixeldungeon.items.instruments;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Drowsy;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.cursedpixeldungeon.effects.Flare;
import com.shatteredpixel.cursedpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.enchantments.Grim;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.cursedpixeldungeon.ui.RedButton;
import com.shatteredpixel.cursedpixeldungeon.ui.RenderedTextMultiline;
import com.shatteredpixel.cursedpixeldungeon.ui.Window;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;
import com.shatteredpixel.cursedpixeldungeon.windows.IconTitle;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Instrument extends Item {
	{
		//image = ItemSpriteSheet.SANDBAG;

		defaultAction = AC_PLAY;
	}

	protected int maxNotes = 5;

	private static final String AC_PLAY = "play";
	private static final String AC_SONGS = "songs";

	//Max song length
	private static final int LIMIT		= 10;

	public enum Song {
		SONG_OF_DELIVERANCE {
			@Override
			public Integer[] melody() {
				return new Integer[] {1, 2, 1, 3};
			}

			@Override
			public String getName() {
				return "Song of Deliverance";
			}

			@Override
			public void onPlay() {
				boolean procced = false;
				for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
					if (mob.properties().contains(Char.Property.UNDEAD)) {
						mob.damage(mob.HT, new Grim());
						procced = true;
						mob.sprite.emitter().start( ShadowParticle.UP, 0.05f, 10 );
						new Flare( 6, 32 ).show( mob.sprite, 2f ) ;
					}
				}
				if (procced) {
					GLog.p("The spirits of the dead thank you silently as they are released into the afterlife...");
				}
			}
		},
		SONG_OF_PEACE {
			@Override
			public Integer[] melody() {
				return new Integer[] {1, 3, 4, 1, 2};
			}

			@Override
			public String getName() {
				return "Song of Peace";
			}

			@Override
			public void onPlay() {
				Buff.affect(Dungeon.hero, Drowsy.class);
				GLog.p("You drift into a magical sleep...");
			}
		};

		public abstract Integer[] melody();

		public abstract String getName();

		public abstract void onPlay();

		@Nullable
		@Contract(pure = true)
		public static String getNote(Integer note) {
			switch (note) {
				case 1:
					return "A";
				case 2:
					return "B";
				case 3:
					return "C";
				case 4:
					return "D";
				case 5:
					return "E";
			}
			return null;
		}

		public static String createSheet(Integer[] melody) {
			StringBuilder string = new StringBuilder();
			for (int i = 0; i < melody.length; i++) {
				int note = melody[i];
				string.append(getNote(note));
				if (i != melody.length-1) {
					string.append(" - ");
				}
			}
			return string.toString();
		}
	}

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
		private static final float GAP		= 2;
		WndSongList(Instrument instrument) {
			IconTitle titlebar = new IconTitle();
			titlebar.icon(new ItemSprite(instrument.image(), null));
			titlebar.label(Messages.titleCase(instrument.name()));
			titlebar.setRect(0, 0, WIDTH, 0);
			add( titlebar );

			RenderedTextMultiline message = getMessage(Song.SONG_OF_DELIVERANCE);
			message.maxWidth(WIDTH);
			message.setPos(0, titlebar.bottom() + GAP);
			add( message );

			RenderedTextMultiline message2 = getMessage(Song.SONG_OF_PEACE);
			message2.maxWidth(WIDTH);
			message2.setPos(0, message.bottom() + GAP);
			add( message2 );

			resize(WIDTH, (int) message.bottom());
		}

		private RenderedTextMultiline getMessage(Song song) {
			return PixelScene.renderMultiline(song.name() + ":" + "\n" + Song.createSheet(song.melody()), 10 );
		}

	}

	private static class WndPlay extends Window {
		private static final int WIDTH		= 120;
		private static final int BTN_SIZE = 20;

		public static ArrayList<Integer> curSong = new ArrayList<>();

		RenderedTextMultiline message;

		WndPlay(Instrument instrument) {
			IconTitle titlebar = new IconTitle();
			titlebar.icon(new ItemSprite(instrument.image(), null));
			titlebar.label(Messages.titleCase(instrument.name()));
			titlebar.setRect(0, 0, WIDTH, 0);
			add( titlebar );

			message = PixelScene.renderMultiline(Song.createSheet(curSong.toArray(new Integer[0])), 10 );
			message.maxWidth(WIDTH);
			message.setPos(0, titlebar.bottom() + 2);
			add( message );

			int y = (int) message.bottom() + BTN_SIZE;
			for (int i = 0; i < instrument.maxNotes; i++) {
				int x = (int) ((WIDTH) * (i/(float)instrument.maxNotes));
				Key key = new Key(x, y, i+1);
				add(key);
			}
			resize(WIDTH, (int) (message.bottom() + BTN_SIZE*2));
		}

		@Override
		public synchronized void update() {
			if (curSong.size() > LIMIT) {
				curSong = new ArrayList<>();
			}
			message.text(Song.createSheet(curSong.toArray(new Integer[0])));
			message.update();
			for (Song s : Song.values()) {
				if (Arrays.equals(curSong.toArray(new Integer[0]), s.melody())) {
					GLog.p("Played " + s.getName() + ".");
					s.onPlay();
					hide();
				}
			}
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
