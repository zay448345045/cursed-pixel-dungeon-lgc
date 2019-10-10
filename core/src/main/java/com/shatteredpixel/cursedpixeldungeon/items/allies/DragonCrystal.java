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

package com.shatteredpixel.cursedpixeldungeon.items.allies;

import com.shatteredpixel.cursedpixeldungeon.Assets;
import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.blobs.CorrosiveGas;
import com.shatteredpixel.cursedpixeldungeon.actors.blobs.ToxicGas;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.LockedFloor;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.Wraith;
import com.shatteredpixel.cursedpixeldungeon.actors.mobs.npcs.NPC;
import com.shatteredpixel.cursedpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.cursedpixeldungeon.effects.Speck;
import com.shatteredpixel.cursedpixeldungeon.effects.particles.ShaftParticle;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.items.KindofMisc;
import com.shatteredpixel.cursedpixeldungeon.items.armor.Armor;
import com.shatteredpixel.cursedpixeldungeon.items.armor.glyphs.AntiMagic;
import com.shatteredpixel.cursedpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.ScrollOfRetribution;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.exotic.ScrollOfPsionicBlast;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.cursedpixeldungeon.levels.Level;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.shatteredpixel.cursedpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.cursedpixeldungeon.sprites.PoisonDragonSprite;
import com.shatteredpixel.cursedpixeldungeon.ui.RenderedTextMultiline;
import com.shatteredpixel.cursedpixeldungeon.ui.Window;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;
import com.shatteredpixel.cursedpixeldungeon.windows.IconTitle;
import com.shatteredpixel.cursedpixeldungeon.windows.WndBag;
import com.shatteredpixel.cursedpixeldungeon.windows.WndBlacksmith;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class DragonCrystal extends KindofMisc {

	{
		image = ItemSpriteSheet.ADORNEDDRAGONCRYSTAL;



		defaultAction = AC_SUMMON;
	}
	int levelCap = 10;

	int charge = 100;
	int chargeCap = 100;
	float partialCharge = 0;

	protected Buff passiveBuff;
	protected Buff activeBuff;

	private boolean talkedTo = false;
	private boolean firstSummon = false;
	
	private Dragon ghost = null;
	private int DragonID = 0;
	
	private MeleeWeapon weapon = null;
	private Armor armor = null;

	public int droppedPetals = 0;

	public static final String AC_SUMMON = "SUMMON";

	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		if (isEquipped( hero ) && charge == chargeCap && !cursed) {
			actions.add(AC_SUMMON);
		}
		
		return actions;
	}

	@Override
	public void execute( Hero hero, String action ) {

		super.execute(hero, action);

		if (action.equals(AC_SUMMON)) {
			if (ghost != null)         GLog.i( Messages.get(this, "spawned") );
			else if (!isEquipped( hero ))   GLog.i( Messages.get(Artifact.class, "need_to_equip") );
			else if (charge != chargeCap)   GLog.i( Messages.get(this, "no_charge") );
			else if (cursed)                GLog.i( Messages.get(this, "cursed") );
			else {
				ArrayList<Integer> spawnPoints = new ArrayList<Integer>();
				for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
					int p = hero.pos + PathFinder.NEIGHBOURS8[i];
					if (Actor.findChar(p) == null && (Dungeon.level.passable[p] || Dungeon.level.avoid[p])) {
						spawnPoints.add(p);
					}
				}

				if (spawnPoints.size() > 0) {
					ghost = new Dragon( this );
					DragonID = ghost.id();
					ghost.pos = Random.element(spawnPoints);

					GameScene.add(ghost, 1f);
					CellEmitter.get(ghost.pos).start( ShaftParticle.FACTORY, 0.3f, 4 );
					CellEmitter.get(ghost.pos).start( Speck.factory(Speck.LIGHT), 0.2f, 3 );

					hero.spend(1f);
					hero.busy();
					hero.sprite.operate(hero.pos);

					if (!firstSummon) {
						ghost.yell( Messages.get(Dragon.class, "hello", Dungeon.hero.givenName()) );
						Sample.INSTANCE.play( Assets.SND_GHOST );
						firstSummon = true;
					} else
						ghost.saySpawned();
					
					charge = 0;
					updateQuickslot();

				} else
					GLog.i( Messages.get(this, "no_space") );
			}

		}
	}
	
	public int ghostStrength(){
		return 13 + level()/2;
	}

	@Override
	public String desc() {
		
		String desc = super.desc();

		if (isEquipped( Dungeon.hero )){
			if (!cursed){

				if (level() < levelCap)
					desc+= "\n\n" + Messages.get(this, "desc_hint");

			} else
				desc += "\n\n" + Messages.get(this, "desc_cursed");
		}

		return desc;
	}

	@Override
	public boolean doEquip( final Hero hero ) {

		if (super.doEquip(hero)) {

			identify();
			return true;

		} else {

			return false;

		}

	}

	public void activate( Char ch ) {
		passiveBuff = passiveBuff();
		passiveBuff.attachTo(ch);
	}

	@Override
	public boolean doUnequip( Hero hero, boolean collect, boolean single ) {
		if (super.doUnequip( hero, collect, single )) {

			passiveBuff.detach();
			passiveBuff = null;

			if (activeBuff != null){
				activeBuff.detach();
				activeBuff = null;
			}

			return true;

		} else {

			return false;

		}
	}

	public class CrystalBuff extends Buff {

		public int itemLevel() {
			return level();
		}

		public boolean isCursed() {
			return cursed;
		}

	}

	protected CrystalBuff passiveBuff() {
		return new recharge();
	}

	public class recharge extends CrystalBuff {
		@Override
		public boolean act() {
			LockedFloor lock = target.buff(LockedFloor.class);
			if (charge < chargeCap && !cursed && (lock == null || lock.regenOn())) {
				partialCharge += 0.1;

				if (partialCharge >= 1) {
					partialCharge --;
					charge ++;

					if (charge == chargeCap){
						partialCharge = 0;
					}
				}
			}

			updateQuickslot();

			spend( TICK );

			return true;
		}
	}
	
	@Override
	public Item upgrade() {
		if (ghost != null){
			ghost.updateCrystal();
		}

		return super.upgrade();
	}

	@Override
	public String status() {

		//if the artifact isn't IDed, or is cursed, don't display anything
		if (!isIdentified() || cursed){
			return null;
		} else {
			return Messages.format("%d%%", charge);
		}
	}

	private static final String TALKEDTO =      "talkedto";
	private static final String FIRSTSUMMON =   "firstsummon";
	private static final String GHOSTID =       "DragonID";
	private static final String PETALS =        "petals";
	
	private static final String WEAPON =        "weapon";
	private static final String ARMOR =         "armor";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle(bundle);

		bundle.put( TALKEDTO, talkedTo );
		bundle.put( FIRSTSUMMON, firstSummon );
		bundle.put( GHOSTID, DragonID);
		bundle.put( PETALS, droppedPetals );
		
		if (weapon != null) bundle.put( WEAPON, weapon );
		if (armor != null)  bundle.put( ARMOR, armor );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle(bundle);

		talkedTo = bundle.getBoolean( TALKEDTO );
		firstSummon = bundle.getBoolean( FIRSTSUMMON );
		DragonID = bundle.getInt( GHOSTID );
		droppedPetals = bundle.getInt( PETALS );
		
		if (bundle.contains(WEAPON)) weapon = (MeleeWeapon)bundle.get( WEAPON );
		if (bundle.contains(ARMOR))  armor = (Armor)bundle.get( ARMOR );
	}
	
	// *** static methods for transferring a ghost hero between floors ***
	
	private static Dragon heldGhost;
	
	public static void holdGhostHero( Level level ){
		for (Mob mob : level.mobs.toArray( new Mob[0] )) {
			if (mob instanceof Dragon) {
				level.mobs.remove( mob );
				heldGhost = (Dragon) mob;
				break;
			}
		}
	}
	
	public static void restoreGhostHero( Level level, int pos ){
		if (heldGhost != null){
			level.mobs.add( heldGhost );
			
			int ghostPos;
			do {
				ghostPos = pos + PathFinder.NEIGHBOURS8[Random.Int(8)];
			} while (Dungeon.level.solid[ghostPos] || level.findMob(ghostPos) != null);
			
			heldGhost.pos = ghostPos;
			heldGhost = null;
		}
	}
	
	public static void clearHeldGhostHero(){
		heldGhost = null;
	}

	public class roseRecharge extends CrystalBuff {

		@Override
		public boolean act() {
			
			spend( TICK );
			
			if (ghost == null && DragonID != 0){
				Actor a = Actor.findById(DragonID);
				if (a != null){
					ghost = (Dragon)a;
				} else {
					DragonID = 0;
				}
			}
			
			//Crystal does not charge while ghost hero is alive
			if (ghost != null){
				return true;
			}
			
			LockedFloor lock = target.buff(LockedFloor.class);
			if (charge < chargeCap && !cursed && (lock == null || lock.regenOn())) {
				partialCharge += 1/5f; //500 turns to a full charge
				if (partialCharge > 1){
					charge++;
					partialCharge--;
					if (charge == chargeCap){
						partialCharge = 0f;
						GLog.p( Messages.get(DragonCrystal.class, "charged") );
					}
				}
			} else if (cursed && Random.Int(100) == 0) {

				ArrayList<Integer> spawnPoints = new ArrayList<Integer>();

				for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
					int p = target.pos + PathFinder.NEIGHBOURS8[i];
					if (Actor.findChar(p) == null && (Dungeon.level.passable[p] || Dungeon.level.avoid[p])) {
						spawnPoints.add(p);
					}
				}

				if (spawnPoints.size() > 0) {
					Wraith.spawnAt(Random.element(spawnPoints));
					Sample.INSTANCE.play(Assets.SND_CURSED);
				}

			}

			updateQuickslot();

			return true;
		}
	}

	public static class Petal extends Item {

		{
			stackable = true;
			image = ItemSpriteSheet.PETAL;
		}

		@Override
		public boolean doPickUp( Hero hero ) {
			DragonCrystal rose = hero.belongings.getItem( DragonCrystal.class );

			if (rose == null){
				GLog.w( Messages.get(this, "no_rose") );
				return false;
			} if ( rose.level() >= rose.levelCap ){
				GLog.i( Messages.get(this, "no_room") );
				hero.spendAndNext(TIME_TO_PICK_UP);
				return true;
			} else {

				rose.upgrade();
				if (rose.level() == rose.levelCap) {
					GLog.p( Messages.get(this, "maxlevel") );
				} else
					GLog.i( Messages.get(this, "levelup") );

				Sample.INSTANCE.play( Assets.SND_DEWDROP );
				hero.spendAndNext(TIME_TO_PICK_UP);
				return true;

			}
		}

	}

	public static class Dragon extends NPC {

		{
			spriteClass = PoisonDragonSprite.class;

			flying = true;

			alignment = Alignment.ALLY;
			
			WANDERING = new Wandering();
			
			state = HUNTING;
			
			//before other mobs
			actPriority = MOB_PRIO + 1;
			
			properties.add(Property.UNDEAD);
		}
		
		private DragonCrystal Crystal = null;
		
		public Dragon(){
			super();
		}

		public Dragon(DragonCrystal rose){
			super();
			this.Crystal = rose;
			updateCrystal();
			HP = HT;
		}
		
		private void updateCrystal(){
			if (Crystal == null) {
				Crystal = Dungeon.hero.belongings.getItem(DragonCrystal.class);
			}
			
			defenseSkill = (Dungeon.hero.lvl+4)*2;
			if (Crystal == null) return;
			HT = 30 + 8* Crystal.level();
		}

		public void saySpawned(){
		}

		public void sayAnhk(){
		}

		public void sayDefeated(){
		}

		public void sayHeroKilled(){
		}

		public void sayBossBeaten(){
		}

		@Override
		protected boolean act() {
			updateCrystal();
			if (Crystal == null || !Crystal.isEquipped(Dungeon.hero)){
				damage(1, this);
			}
			
			if (!isAlive())
				return true;
			if (!Dungeon.hero.isAlive()){
				sayHeroKilled();
				sprite.die();
				destroy();
				return true;
			}
			return super.act();
		}
		
		@Override
		protected Char chooseEnemy() {
			Char enemy = super.chooseEnemy();
			
			//will never attack something far from the player
			if (enemy != null && Dungeon.level.mobs.contains(enemy)
					&& Dungeon.level.distance(enemy.pos, Dungeon.hero.pos) <= 8){
				return enemy;
			} else {
				return null;
			}
		}

		@Override
		public int attackSkill(Char target) {
			
			//same accuracy as the hero.
			int acc = Dungeon.hero.lvl + 9;
			
			if (Crystal != null && Crystal.weapon != null){
				acc *= Crystal.weapon.accuracyFactor(this);
			}
			
			return acc;
		}
		
		@Override
		protected float attackDelay() {
			float delay = super.attackDelay();
			if (Crystal != null && Crystal.weapon != null){
				delay *= Crystal.weapon.speedFactor(this);
			}
			return delay;
		}
		
		@Override
		protected boolean canAttack(Char enemy) {
			return super.canAttack(enemy) || (Crystal != null && Crystal.weapon != null && Crystal.weapon.canReach(this, enemy.pos));
		}
		
		@Override
		public int damageRoll() {
			int dmg = 0;
			if (Crystal != null && Crystal.weapon != null){
				dmg += Crystal.weapon.damageRoll(this);
			} else {
				dmg += Random.NormalIntRange(0, 5);
			}
			
			return dmg;
		}
		
		@Override
		public int attackProc(Char enemy, int damage) {
			damage = super.attackProc(enemy, damage);
			if (Crystal != null && Crystal.weapon != null) {
				damage = Crystal.weapon.proc( this, enemy, damage );
			}
			return damage;
		}
		
		@Override
		public int defenseProc(Char enemy, int damage) {
			if (Crystal != null && Crystal.armor != null) {
				return Crystal.armor.proc( enemy, this, damage );
			} else {
				return super.defenseProc(enemy, damage);
			}
		}
		@Override
		public int defenseSkill(Char enemy) {
			return 2*(Crystal.level()+2);
		}//2 Evasion, scaling at 2/level
		
		@Override
		public int drRoll() {
			int block = 0;
			if (Crystal != null && Crystal.armor != null){
				block += Random.NormalIntRange( Crystal.armor.DRMin(), Crystal.armor.DRMax());
			}
			if (Crystal != null && Crystal.weapon != null){
				block += Random.NormalIntRange( 0, Crystal.weapon.defenseFactor( this ));
			}
			return block;
		}

		@Override
		public boolean interact() {
			updateCrystal();
			if (Dungeon.level.passable[pos] || Dungeon.hero.flying) {
				int curPos = pos;

				moveSprite( pos, Dungeon.hero.pos );
				move( Dungeon.hero.pos );

				Dungeon.hero.sprite.move( Dungeon.hero.pos, curPos );
				Dungeon.hero.move( curPos );

				Dungeon.hero.spend( 1 / Dungeon.hero.speed() );
				Dungeon.hero.busy();
				return true;
			} else {
				return false;
			}
		}

		@Override
		public void die(Object cause) {
			sayDefeated();
			super.die(cause);
		}

		@Override
		public void destroy() {
			updateCrystal();
			if (Crystal != null) {
				Crystal.ghost = null;
				Crystal.DragonID = -1;
			}
			super.destroy();
		}
		
		{
			immunities.add( ToxicGas.class );
			immunities.add( CorrosiveGas.class );
			immunities.add( Burning.class );
			immunities.add( ScrollOfRetribution.class );
			immunities.add( ScrollOfPsionicBlast.class );
			immunities.add( Corruption.class );
		}
		
		private class Wandering extends Mob.Wandering {
			
			@Override
			public boolean act( boolean enemyInFOV, boolean justAlerted ) {
				if ( enemyInFOV ) {
					
					enemySeen = true;
					
					notice();
					alerted = true;
					state = HUNTING;
					target = enemy.pos;
					
				} else {
					
					enemySeen = false;
					
					int oldPos = pos;
					//always move towards the hero when wandering
					if (getCloser( target = Dungeon.hero.pos )) {
						//moves 2 tiles at a time when returning to the hero from a distance
						if (!Dungeon.level.adjacent(Dungeon.hero.pos, pos)){
							getCloser( target = Dungeon.hero.pos );
						}
						spend( 1 / speed() );
						return moveSprite( oldPos, pos );
					} else {
						spend( TICK );
					}
					
				}
				return true;
			}
			
		}

	}
}
