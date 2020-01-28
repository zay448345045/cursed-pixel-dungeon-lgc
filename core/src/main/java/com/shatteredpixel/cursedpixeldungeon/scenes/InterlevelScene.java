/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.cursedpixeldungeon.scenes;

import com.shatteredpixel.cursedpixeldungeon.Assets;
import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.GamesInProgress;
import com.shatteredpixel.cursedpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.cursedpixeldungeon.Statistics;
import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.LuckyBadgeBuff;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.cursedpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.cursedpixeldungeon.items.powers.LuckyBadge;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.enchantments.Lucky;
import com.shatteredpixel.cursedpixeldungeon.levels.Level;
import com.shatteredpixel.cursedpixeldungeon.levels.features.Chasm;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.ui.GameLog;
import com.shatteredpixel.cursedpixeldungeon.windows.WndError;
import com.shatteredpixel.cursedpixeldungeon.windows.WndStory;
import com.watabou.gltextures.TextureCache;
import com.watabou.glwrap.Blending;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.NoosaScript;
import com.watabou.noosa.NoosaScriptNoLighting;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.SkinnedBlock;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.DeviceCompat;

import java.io.FileNotFoundException;
import java.io.IOException;

public class InterlevelScene extends PixelScene {
	
	//slow fade on entering a new region
	private static final float SLOW_FADE = 1f; //.33 in, 1.33 steady, .33 out, 2 seconds total
	//norm fade when loading, falling, returning, or descending to a new floor
	private static final float NORM_FADE = 0.67f; //.33 in, .67 steady, .33 out, 1.33 seconds total
	//fast fade when ascending, or descending to a floor you've been on
	private static final float FAST_FADE = 0.50f; //.33 in, .33 steady, .33 out, 1 second total
	public static final String FALL_NAME = "FALL_NAME";
	public static final String DESCEND_NAME = "DESCEND_NAME";
	public static final String ASCEND_NAME = "ASCEND_NAME";
	public static final String RESET_NAME = "RESET_NAME";
	public static final String RESURRECT_NAME = "RESURRECT_NAME";
	public static final String RETURNTO_NAME = "returnto_name";
	
	private static float fadeTime;

	public enum Mode {
		DESCEND, ASCEND, CONTINUE, RESURRECT, RETURN, FALL, RESET, NONE, START, WATERCHALLENGE, EARTHCHALLENGE, DESCEND_GAMEINIT, DESCEND_FIX, FIRECHALLENGE, AIRCHALLENGE, GRIND, HOME, RETURNTO
	}
	public static Mode mode;
	
	public static int returnDepth;
	public static int returnPos;
	
	public static boolean noStory = false;

	public static boolean testing = false;

	public static boolean fallIntoPit;
	
	private enum Phase {
		FADE_IN, STATIC, FADE_OUT
	}
	private Phase phase;
	private float timeLeft;
	
	private RenderedText message;
	
	private static Thread thread;
	private static Exception error = null;
	private float waitingTime;


	@Override
	public void create() {
		super.create();
		
		String loadingAsset;
		int loadingDepth;
		final float scrollSpeed;
		fadeTime = NORM_FADE;
		switch (mode){
			case DESCEND_GAMEINIT:
				loadingDepth = 1;
				fadeTime = SLOW_FADE;
				scrollSpeed = 5;
				break;

			default:
				loadingDepth = Dungeon.depth;
				scrollSpeed = 0;
				break;
			case CONTINUE:
				loadingDepth = GamesInProgress.check(GamesInProgress.curSlot).depth;
				scrollSpeed = 5;
				break;
			case DESCEND:
				if (Dungeon.hero == null){
					loadingDepth = 1;
					fadeTime = SLOW_FADE;
				} else {
					loadingDepth = Dungeon.depth+1;
					if (!(Statistics.deepestFloor < loadingDepth)) {
						fadeTime = FAST_FADE;
					} else if (loadingDepth == 6 || loadingDepth == 11
							|| loadingDepth == 16 || loadingDepth == 22 || loadingDepth == 31) {
						fadeTime = SLOW_FADE;
					}
				}
				scrollSpeed = 5;
				break;
			case FALL:
				loadingDepth = Dungeon.depth+1;
				scrollSpeed = 50;
				break;
			case ASCEND:
				fadeTime = FAST_FADE;
				loadingDepth = Dungeon.depth-1;
				scrollSpeed = -5;
				break;
			case RETURN:
				loadingDepth = returnDepth;
				scrollSpeed = returnDepth > Dungeon.depth ? 15 : -15;
				break;
		}
		if (loadingDepth <= 5)          loadingAsset = Assets.LOADING_SEWERS;
		else if (loadingDepth <= 10)    loadingAsset = Assets.LOADING_PRISON;
		else if (loadingDepth <= 15)    loadingAsset = Assets.LOADING_CAVES;
		else if (loadingDepth <= 21)    loadingAsset = Assets.LOADING_CITY;
		else if (loadingDepth <= 25)    loadingAsset = Assets.LOADING_HALLS;
		else                            loadingAsset = Assets.SHADOW;
		
		//speed up transition when debugging
		if (DeviceCompat.isDebug()){
			fadeTime /= 2;
		}
		
		SkinnedBlock bg = new SkinnedBlock(Camera.main.width, Camera.main.height, loadingAsset ){
			@Override
			protected NoosaScript script() {
				return NoosaScriptNoLighting.get();
			}
			
			@Override
			public void draw() {
				Blending.disable();
				super.draw();
				Blending.enable();
			}
			
			@Override
			public void update() {
				super.update();
				offset(0, Game.elapsed * scrollSpeed);
			}
		};
		bg.scale(4, 4);
		add(bg);
		
		Image im = new Image(TextureCache.createGradient(0xAA000000, 0xBB000000, 0xCC000000, 0xDD000000, 0xFF000000)){
			@Override
			public void update() {
				super.update();
				if (phase == Phase.FADE_IN)         aa = Math.max( 0, (timeLeft - (fadeTime - 0.333f)));
				else if (phase == Phase.FADE_OUT)   aa = Math.max( 0, (0.333f - timeLeft));
				else                                aa = 0;
			}
		};
		im.angle = 90;
		im.x = Camera.main.width;
		im.scale.x = Camera.main.height/5f;
		im.scale.y = Camera.main.width;
		add(im);

		String text = Messages.get(Mode.class, mode.name());
		
		message = PixelScene.renderText( text, 9 );
		message.x = (Camera.main.width - message.width()) / 2;
		message.y = (Camera.main.height - message.height()) / 2;
		align(message);
		add( message );
		
		phase = Phase.FADE_IN;
		timeLeft = fadeTime;
		
		if (thread == null) {
			thread = new Thread() {
				@Override
				public void run() {
					
					try {

						if (Dungeon.hero != null){
							Dungeon.hero.spendToWhole();
						}
						Actor.fixTime();

						switch (mode) {//Second argument decides a few things. DESCEND_NAME, places the player at the entrance, while ASCEND_NAME places them at the exit. FALL_NAME gives them the fall buff and causes them to fall into a random room
							case DESCEND:
								goToDepth(Dungeon.depth + 1, DESCEND_NAME);
								break;
							case DESCEND_GAMEINIT:
								init();
								break;
							case ASCEND:
								goToDepth(Dungeon.depth - 1, ASCEND_NAME);
								break;
							case CONTINUE:
								restore();
								break;
							case RESURRECT:
								goToDepth(Dungeon.depth, RESURRECT_NAME);
								break;
							case RETURN:
								goToDepth(returnDepth, DESCEND_NAME);
								break;
							case RETURNTO:
								goToDepth(returnDepth, RETURNTO_NAME);
								break;
							case FALL:
								goToDepth(Dungeon.depth + 1, FALL_NAME);
								break;
							case RESET:
								createNewLevel(Dungeon.depth);
								break;
							case START:
								goToDepth(0, ASCEND_NAME);
								break;
							case WATERCHALLENGE:
								goToDepth(31, DESCEND_NAME);
								break;
							case EARTHCHALLENGE:
								goToDepth(36, DESCEND_NAME);
								break;
							case FIRECHALLENGE:
								goToDepth(41, DESCEND_NAME);
								break;
							case AIRCHALLENGE:
								goToDepth(46, DESCEND_NAME);
								break;
							case GRIND:
								goToDepth( LuckyBadge.GrindDepth, DESCEND_NAME );
								break;
							case HOME:
								goToDepth( LuckyBadge.HomeDepth, DESCEND_NAME );
						}
						
						if ((Dungeon.depth % 5) == 0) {
							Sample.INSTANCE.load(Assets.SND_BOSS);
						}
						
					} catch (Exception e) {
						
						error = e;
						
					}
					
					if (phase == Phase.STATIC && error == null) {
						phase = Phase.FADE_OUT;
						timeLeft = fadeTime;
					}
				}
			};
			thread.start();
		}
		waitingTime = 0f;
	}
	
	@Override
	public void update() {
		super.update();

		waitingTime += Game.elapsed;
		
		float p = timeLeft / fadeTime;
		
		switch (phase) {
		
		case FADE_IN:
			message.alpha( 1 - p );
			if ((timeLeft -= Game.elapsed) <= 0) {
				if (!thread.isAlive() && error == null) {
					phase = Phase.FADE_OUT;
					timeLeft = fadeTime;
				} else {
					phase = Phase.STATIC;
				}
			}
			break;
			
		case FADE_OUT:
			message.alpha( p );
			
			if ((timeLeft -= Game.elapsed) <= 0) {
				Game.switchScene( GameScene.class );
				thread = null;
				error = null;
			}
			break;
			
		case STATIC:
			if (error != null) {
				String errorMsg = Messages.get(this, "file_not_found");
				if (error instanceof FileNotFoundException) {
					try {//Attempt to set if error when descending
						createNewLevel(Dungeon.depth);
						error = null;
					} catch (Exception e) {
						error = e;
					}

				}
				else if (error instanceof IOException)          errorMsg = Messages.get(this, "io_error");
				else if (error.getMessage() != null &&
						error.getMessage().equals("old save")) errorMsg = Messages.get(this, "io_error");

				else errorMsg = Messages.get(this, "io_error");


				errorMsg += "\n\n" + error;
				if (error != null) {//In case resetting the floor fixed it
					thread = null;
					add(new WndError(errorMsg) {
						public void onBackPressed() {
							super.onBackPressed();
							Game.switchScene(StartScene.class);
						}
					});
				} else {
					Statistics.deepestFloor = 0;
					phase = Phase.FADE_OUT;
				}


				error = null;
				throw new RuntimeException("fatal error occured while moving between floors. " +
						"Seed:" + Dungeon.seed + " depth:" + Dungeon.depth, error);
			} else if (thread != null && (int)waitingTime == 10){
				waitingTime = 11f;
				String s = "";
				for (StackTraceElement t : thread.getStackTrace()){
					s += "\n";
					s += t.toString();
				}
				ShatteredPixelDungeon.reportException(
						new RuntimeException("waited more than 10 seconds on levelgen. " +
								"Seed:" + Dungeon.seed + " depth:" + Dungeon.depth + " trace:" +
								s)
				);
			}
			break;
		}
	}

	private static void createNewLevel(int depthToAccess) {
		Level level;
		Dungeon.depth = depthToAccess;
		level = Dungeon.createNewLevelWithDepth(Dungeon.depth);
		Dungeon.switchLevel( level, level.entrance );
	}

	private void init() throws IOException {
		Dungeon.init(testing);
		GameLog.wipe();
		createNewLevel(0);
	}

	private static void goToDepth(int depthToAccess, final String typeOfDescend) throws IOException {
		if (typeOfDescend.equals(FALL_NAME)) {
			Buff.affect( Dungeon.hero, Chasm.Falling.class );
		}
		if (Dungeon.hero == null) {
			Mob.clearHeldAllies();
			Dungeon.init();
			if (noStory) {
				Dungeon.chapters.add( WndStory.ID_SEWERS );
				noStory = false;
			}
			GameLog.wipe();
		} else {
			Mob.holdAllies( Dungeon.level );
			Dungeon.saveAll();
		}
		Dungeon.depth = depthToAccess;
		if (typeOfDescend.equals(RESURRECT_NAME)) {
			if (Dungeon.level.locked) {
				Dungeon.hero.resurrect( Dungeon.depth );
			} else {
				Dungeon.hero.resurrect( -1 );
				Dungeon.resetLevel();
				return;
			}
		}
		if (Dungeon.hero == null) {
			Dungeon.depth = 1;
		}
		Level level;
		try {

			level = Dungeon.loadLevel(GamesInProgress.curSlot);

		} catch (Exception e) {

			level = Dungeon.createNewLevelWithDepth(Dungeon.depth);
			if (Dungeon.hero != null) {
				LuckyBadge badge = Dungeon.hero.belongings.getItem(LuckyBadge.class);
				if (typeOfDescend.equals(DESCEND_NAME) & badge != null && badge.type == LuckyBadge.SPEED & Dungeon.depth != LuckyBadge.HomeDepth & !Dungeon.bossLevel() & Dungeon.depth != 21) {
					Buff.affect(Dungeon.hero, LuckyBadgeBuff.class, 99f);
				}
			}
		}

		switch (typeOfDescend) {
			default:
				Dungeon.switchLevel(level, level.entrance);
				break;
			case FALL_NAME:
				Dungeon.switchLevel(level, level.fallCell(fallIntoPit));
				break;
			case ASCEND_NAME:
				Dungeon.switchLevel(level, level.exit);
				break;
			case RETURNTO_NAME:
				Dungeon.switchLevel(level, returnPos);
				break;
		}

	}
	
	private void restore() throws IOException {
		
		DriedRose.clearHeldGhostHero();

		GameLog.wipe();

		Dungeon.loadGame( GamesInProgress.curSlot );
		if (Dungeon.depth == -1) {
			Dungeon.depth = Statistics.deepestFloor;
			Dungeon.switchLevel( Dungeon.loadLevel( GamesInProgress.curSlot ), -1 );
		} else {
			Level level;
			try {
				level = Dungeon.loadLevel( GamesInProgress.curSlot );

			} catch(Exception e) {

				level = Dungeon.createNewLevelWithDepth(Dungeon.depth);
			}
			Dungeon.switchLevel( level, Dungeon.hero.pos );
		}
	}
	
	/*private void resurrect() throws IOException {
		
		if (Dungeon.level.locked) {
			Dungeon.hero.resurrect( Dungeon.depth );
			Dungeon.depth--;
			Level level = Dungeon.newLevel();
			Dungeon.switchLevel( level, level.entrance );
		} else {
			Dungeon.hero.resurrect( -1 );
			Dungeon.resetLevel();
		}
	}*/

	/*private void set() throws IOException {

		DriedRose.holdGhostHero( Dungeon.level );

		SpecialRoom.resetPitRoom(Dungeon.depth+1);

		Dungeon.depth--;
		Level level = Dungeon.newLevel();
		Dungeon.switchLevel( level, level.entrance );
	}*/
	
	@Override
	protected void onBackPressed() {
		//Do nothing
	}
}
