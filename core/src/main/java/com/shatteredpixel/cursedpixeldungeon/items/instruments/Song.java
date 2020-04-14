package com.shatteredpixel.cursedpixeldungeon.items.instruments;

import com.shatteredpixel.cursedpixeldungeon.Assets;
import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Drowsy;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Wraith;
import com.shatteredpixel.cursedpixeldungeon.effects.Flare;
import com.shatteredpixel.cursedpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.enchantments.Grim;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.shatteredpixel.cursedpixeldungeon.ui.RenderedTextMultiline;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;
import com.watabou.noosa.Group;
import com.watabou.noosa.Image;
import com.watabou.noosa.RenderedText;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

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
				if (mob.properties().contains(Char.Property.UNDEAD) && !(mob.properties().contains(Char.Property.MINIBOSS) || mob.properties().contains(Char.Property.BOSS))) {
					if (mob instanceof Wraith) {
						mob.die(null);
					} else {
						mob.damage(mob.HT/2, new Grim());
					}
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

	public boolean unlocked() {
		return true;
	}

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

	@android.support.annotation.NonNull
	@Contract("_, _, _, _, _, _ -> new")
	public static Sheet createSheet(int x, int y, int width, int height, int songLength, Integer[] melody) {
		return new Sheet(x, y, width, height, songLength, melody);
	}

	public static class Sheet extends Group {

		private int x;
		private int y;
		private int width;
		private int height;
		private int songLength;
		private Integer[] melody;

		public Sheet(int x, int y, int width, int height, int songLength, Integer[] melody) {
			update(x, y, width, height, songLength, melody);
		}

		private void update(int x, int y, int width, int height, int songLength, Integer[] melody) {
			clear();
			Image bg = new Image(Assets.NOTES);
			bg.x = x;
			bg.y = y;
			bg.frame(0, 0, width, height);
			add(bg);
			if (melody != null) {
				for (int i = 0; i < melody.length; i++) {
					int note = melody[i];
					RenderedText text = GameScene.renderText(getNote(note), 7);
					text.x = x + (width / songLength) * i;
					text.y = y + (height / 5) * (note-1);
					add(text);
				}
			}
			this.x = x;
			this.y = y;
			this.height = height;
			this.width = width;
			this.melody = melody;
			this.songLength = songLength;
		}

		@Override
		public synchronized void update() {
			update(x, y, width, height, songLength, melody);
		}

		public void update(Integer[] melody) {
			update(x, y, width, height, songLength, melody);
		}
	}
}
