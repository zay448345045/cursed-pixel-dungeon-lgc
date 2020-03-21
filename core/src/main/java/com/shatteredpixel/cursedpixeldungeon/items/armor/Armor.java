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

package com.shatteredpixel.cursedpixeldungeon.items.armor;

import com.shatteredpixel.cursedpixeldungeon.Badges;
import com.shatteredpixel.cursedpixeldungeon.Constants;
import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.cursedpixeldungeon.actors.Actor;
import com.shatteredpixel.cursedpixeldungeon.actors.Char;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.MagicImmune;
import com.shatteredpixel.cursedpixeldungeon.actors.buffs.Momentum;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.cursedpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.cursedpixeldungeon.effects.Speck;
import com.shatteredpixel.cursedpixeldungeon.items.BrokenSeal;
import com.shatteredpixel.cursedpixeldungeon.items.EquipableItem;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.items.armor.curses.AntiEntropy;
import com.shatteredpixel.cursedpixeldungeon.items.armor.curses.Bulk;
import com.shatteredpixel.cursedpixeldungeon.items.armor.curses.Corrosion;
import com.shatteredpixel.cursedpixeldungeon.items.armor.curses.Displacement;
import com.shatteredpixel.cursedpixeldungeon.items.armor.curses.Metabolism;
import com.shatteredpixel.cursedpixeldungeon.items.armor.curses.Multiplicity;
import com.shatteredpixel.cursedpixeldungeon.items.armor.curses.Overgrowth;
import com.shatteredpixel.cursedpixeldungeon.items.armor.curses.Stench;
import com.shatteredpixel.cursedpixeldungeon.items.armor.glyphs.Affection;
import com.shatteredpixel.cursedpixeldungeon.items.armor.glyphs.Brimstone;
import com.shatteredpixel.cursedpixeldungeon.items.armor.glyphs.Camouflage;
import com.shatteredpixel.cursedpixeldungeon.items.armor.glyphs.Entanglement;
import com.shatteredpixel.cursedpixeldungeon.items.armor.glyphs.Flow;
import com.shatteredpixel.cursedpixeldungeon.items.armor.glyphs.Obfuscation;
import com.shatteredpixel.cursedpixeldungeon.items.armor.glyphs.Potential;
import com.shatteredpixel.cursedpixeldungeon.items.armor.glyphs.Repulsion;
import com.shatteredpixel.cursedpixeldungeon.items.armor.glyphs.Stone;
import com.shatteredpixel.cursedpixeldungeon.items.armor.glyphs.Swiftness;
import com.shatteredpixel.cursedpixeldungeon.items.armor.glyphs.Thorns;
import com.shatteredpixel.cursedpixeldungeon.items.armor.glyphs.Viscosity;
import com.shatteredpixel.cursedpixeldungeon.items.rings.RingOfLuck;
import com.shatteredpixel.cursedpixeldungeon.levels.Terrain;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.sprites.HeroSprite;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.cursedpixeldungeon.utils.GLog;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;

public class Armor extends EquipableItem {

	protected static final String AC_DETACH       = "DETACH";
	public enum Augment {
		EVASION (1.5f , -1f),
		DEFENSE (-1.5f, 1f),
		NONE	(0f   ,  0f);
		
		private float evasionFactor;
		private float defenceFactor;
		
		Augment(float eva, float df){
			evasionFactor = eva;
			defenceFactor = df;
		}
		
		public int evasionFactor(int level){
			return Math.round((2 + level) * evasionFactor);
		}
		
		public int defenseFactor(int level){
			return Math.round((2 + level) * defenceFactor);
		}
	}
	
	public Augment augment = Augment.NONE;
	
	public Glyph glyph;
	public boolean curseInfusionBonus = false;

	private int storedUpgrades = 0;

	@Override
	public int upgradeLimit() {
		int limit = super.upgradeLimit();
		if (curseInfusionBonus) {
			limit += Constants.curseInfusionBonusAmt;
		}
		return limit;
	}

	private BrokenSeal seal;
	
	public int tier;
	
	private static final int USES_TO_ID = 10;
	private int usesLeftToID = USES_TO_ID;
	private float availableUsesToID = USES_TO_ID/2f;
	
	public Armor( int tier ) {
		this.tier = tier;
	}
	
	private static final String USES_LEFT_TO_ID = "uses_left_to_id";
	private static final String AVAILABLE_USES  = "available_uses";
	private static final String GLYPH			= "glyph";
	private static final String CURSE_INFUSION_BONUS = "curse_infusion_bonus";
	private static final String SEAL            = "seal";
	private static final String AUGMENT			= "augment";
	private static final String STORED_UPGRADES = "stored_upgrades";

	public void storeUpgrades(int number) {
		storedUpgrades = number;
	}

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( USES_LEFT_TO_ID, usesLeftToID );
		bundle.put( AVAILABLE_USES, availableUsesToID );
		bundle.put( GLYPH, glyph );
		bundle.put( CURSE_INFUSION_BONUS, curseInfusionBonus );
		bundle.put( SEAL, seal);
		bundle.put( AUGMENT, augment);
		bundle.put( STORED_UPGRADES, storedUpgrades );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle(bundle);
		usesLeftToID = bundle.getInt( USES_LEFT_TO_ID );
		availableUsesToID = bundle.getInt( AVAILABLE_USES );
		inscribe((Glyph) bundle.get(GLYPH));
		curseInfusionBonus = bundle.getBoolean( CURSE_INFUSION_BONUS );
		seal = (BrokenSeal)bundle.get(SEAL);
		storedUpgrades = bundle.getInt(STORED_UPGRADES);
		
		//pre-0.7.2 saves
		if (bundle.contains( "unfamiliarity" )){
			usesLeftToID = bundle.getInt( "unfamiliarity" );
			availableUsesToID = USES_TO_ID/2f;
		}
		
		augment = bundle.getEnum(AUGMENT, Augment.class);
	}

	@Override
	public void reset() {
		super.reset();
		usesLeftToID = USES_TO_ID;
		availableUsesToID = USES_TO_ID/2f;
		//armor can be kept in bones between runs, the seal cannot.
		seal = null;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (seal != null) actions.add(AC_DETACH);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_DETACH) && seal != null){
			BrokenSeal.WarriorShield sealBuff = hero.buff(BrokenSeal.WarriorShield.class);
			if (sealBuff != null) sealBuff.setArmor(null);

			if (seal.level() > 0){
				degrade(seal.level());
			}
			upgrade(storedUpgrades);
			if (level() > upgradeLimit()) {
				level(upgradeLimit());
			}
			GLog.i( Messages.get(Armor.class, "detach_seal") );
			hero.sprite.operate(hero.pos);
			if (!seal.collect()){
				Dungeon.level.drop(seal, hero.pos);
			}
			seal = null;
		}
	}

	@Override
	public boolean doEquip( Hero hero ) {
		
		detach(hero.belongings.backpack);

		if (hero.belongings.armor == null || hero.belongings.armor.doUnequip( hero, true, false )) {
			
			hero.belongings.armor = this;
			
			cursedKnown = true;
			if (cursed) {
				equipCursed( hero );
				GLog.n( Messages.get(Armor.class, "equip_cursed") );
			}
			
			((HeroSprite)hero.sprite).updateArmor();
			activate(hero);

			hero.spendAndNext( time2equip( hero ) );
			return true;
			
		} else {
			
			collect( hero.belongings.backpack );
			return false;
			
		}
	}

	@Override
	public void activate(Char ch) {
		if (seal != null) Buff.affect(ch, BrokenSeal.WarriorShield.class).setArmor(this);
	}

	public void affixSeal(BrokenSeal seal){
		this.seal = seal;
		if (seal.level() > 0){
			//doesn't trigger upgrading logic such as affecting curses/glyphs
			level(level()+seal.level());
			Badges.validateItemLevelAquired(this);
		}
		if (isEquipped(Dungeon.hero)){
			Buff.affect(Dungeon.hero, BrokenSeal.WarriorShield.class).setArmor(this);
		}
	}

	public BrokenSeal checkSeal(){
		return seal;
	}

	@Override
	protected float time2equip( Hero hero ) {
		return 2 / hero.speed();
	}

	@Override
	public boolean doUnequip( Hero hero, boolean collect, boolean single ) {
		if (super.doUnequip( hero, collect, single )) {

			hero.belongings.armor = null;
			((HeroSprite)hero.sprite).updateArmor();

			BrokenSeal.WarriorShield sealBuff = hero.buff(BrokenSeal.WarriorShield.class);
			if (sealBuff != null) sealBuff.setArmor(null);

			return true;

		} else {

			return false;

		}
	}
	
	@Override
	public boolean isEquipped( Hero hero ) {
		return hero.belongings.armor == this;
	}

	public final int DRMax(){
		return DRMax(level());
	}

	public int DRMax(int lvl){
		int max = tier * (2 + lvl) + augment.defenseFactor(lvl);
		if (lvl > max){
			return ((lvl - max)+1)/2;
		} else {
			return max;
		}
	}

	public int DRRoll( Char owner ) {
		return RingOfLuck.randomNormalIntRange( DRMin(), DRMax(), DRMax() );//Finds closest to max
	}

	public final int DRMin(){
		return DRMin(level());
	}

	public int DRMin(int lvl){
		int max = (int) ((tier/2f) * (2 + lvl) + augment.defenseFactor(lvl));
		if (lvl >= max){
			return (lvl - max);
		} else {
			return max;
		}
	}
	
	public float evasionFactor( Char owner, float evasion ){
		
		if (hasGlyph(Stone.class, owner) && !((Stone)glyph).testingEvasion()){
			return 0;
		}
		
		if (owner instanceof Hero){
			Hero hero = (Hero) owner;
			int aEnc = STRReq() - ((Hero) owner).STR();
			if (aEnc > 0) evasion /= Math.pow(1.5, aEnc);
			
			Momentum momentum = owner.buff(Momentum.class);
			if (momentum != null){
				evasion += momentum.evasionBonus();
			}
			if (hero.subClass == HeroSubClass.BRAWLER) {
				evasion += Math.max(0, -aEnc)*1.5f;
			}
		}
		
		return evasion + augment.evasionFactor(level());
	}
	
	public float speedFactor( Char owner, float speed ){
		
		if (owner instanceof Hero) {
			int aEnc = STRReq() - ((Hero) owner).STR();
			if (aEnc > 0) speed /= Math.pow(1.2, aEnc);
		}
		
		if (hasGlyph(Swiftness.class, owner)) {
			boolean enemyNear = false;
			for (Char ch : Actor.chars()){
				if (Dungeon.level.adjacent(ch.pos, owner.pos) && owner.alignment != ch.alignment){
					enemyNear = true;
					break;
				}
			}
			if (!enemyNear) speed *= (1.2f + 0.04f * level());
		} else if (hasGlyph(Flow.class, owner) && Dungeon.level.water[owner.pos]){
			speed *= (1.5f + 0.1f * level());
		}
		
		if (hasGlyph(Bulk.class, owner) &&
				(Dungeon.level.map[owner.pos] == Terrain.DOOR
						|| Dungeon.level.map[owner.pos] == Terrain.OPEN_DOOR )) {
			speed /= 3f;
		}
		
		return speed;
		
	}
	
	public float stealthFactor( Char owner, float stealth ){
		
		if (hasGlyph(Obfuscation.class, owner)){
			stealth += 1 + level()/3f;
		}
		
		return stealth;
	}
	
	@Override
	public int level() {
		return super.level() + (curseInfusionBonus ? Constants.curseInfusionBonusAmt : 0);
	}
	
	@Override
	public Item upgrade() {
		return upgrade( false );
	}
	
	public Item upgrade( boolean inscribe ) {

		if (inscribe && (glyph == null || glyph.curse())){
			inscribe( Glyph.random() );
		}

		if (seal != null && seal.level() <= 4)
			seal.upgrade();

		return super.upgrade();
	}
	
	public int proc( Char attacker, Char defender, int damage ) {

		if (glyph != null && defender.buff(MagicImmune.class) == null) {
			damage = glyph.proc( this, attacker, defender, damage );
		}
		if (!levelKnown && defender == Dungeon.hero && availableUsesToID >= 1) {
			availableUsesToID--;
			usesLeftToID--;
			if (usesLeftToID <= 0) {
				identify();
				GLog.p( Messages.get(Armor.class, "identify") );
				Badges.validateItemLevelAquired( this );
			}
		}
		
		return damage;
	}
	
	@Override
	public void onHeroGainExp(float levelPercent, Hero hero) {
		if (!levelKnown && isEquipped(hero) && availableUsesToID <= USES_TO_ID/2f) {
			//gains enough uses to ID over 0.5 levels
			availableUsesToID = Math.min(USES_TO_ID/2f, availableUsesToID + levelPercent * USES_TO_ID);
		}
	}
	
	@Override
	public String name() {
		return glyph != null && (cursedKnown || !glyph.curse()) ? glyph.name( super.name() ) : super.name();
	}
	
	@Override
	public String info() {
		String info = desc();
		
		if (levelKnown) {
			info += "\n\n" + Messages.get(Armor.class, "curr_absorb", DRMin(), DRMax(), STRReq());
			
			if (STRReq() > Dungeon.hero.STR()) {
				info += " " + Messages.get(Armor.class, "too_heavy");
			}
		} else {
			info += "\n\n" + Messages.get(Armor.class, "avg_absorb", DRMin(0), DRMax(0), STRReq(0));

			if (STRReq(0) > Dungeon.hero.STR()) {
				info += " " + Messages.get(Armor.class, "probably_too_heavy");
			}
		}

		switch (augment) {
			case EVASION:
				info += "\n\n" + Messages.get(Armor.class, "evasion");
				break;
			case DEFENSE:
				info += "\n\n" + Messages.get(Armor.class, "defense");
				break;
			case NONE:
		}
		
		if (glyph != null  && (cursedKnown || !glyph.curse())) {
			info += "\n\n" +  Messages.get(Armor.class, "inscribed", glyph.name());
			info += " " + glyph.desc();
		}
		
		if (cursed && isEquipped( Dungeon.hero )) {
			info += "\n\n" + Messages.get(Armor.class, "cursed_worn");
		} else if (cursedKnown && cursed) {
			info += "\n\n" + Messages.get(Armor.class, "cursed");
		} else if (seal != null) {
			info += "\n\n" + Messages.get(Armor.class, "seal_attached");
		} else if (!isIdentified() && cursedKnown){
			info += "\n\n" + Messages.get(Armor.class, "not_cursed");
		}
		//info += "\n\n" + Messages.get(Item.class, "durability", new DecimalFormat("#.##").format(durability()));
		return info;
	}

	@Override
	public Emitter emitter() {
		if (seal == null) return super.emitter();
		Emitter emitter = new Emitter();
		emitter.pos(ItemSpriteSheet.film.width(image)/2f + 2f, ItemSpriteSheet.film.height(image)/3f);
		emitter.fillTarget = false;
		emitter.pour(Speck.factory( Speck.RED_LIGHT ), 0.6f);
		return emitter;
	}

	@Override
	public Item random() {
		//+0: 75% (3/4)
		//+1: 20% (4/20)
		//+2: 5%  (1/20)
		int n = 0;
		if (RingOfLuck.randomInt(4, 0) == 0) {
			n++;
			if (RingOfLuck.randomInt(5, 0) == 0) {
				n++;
			}
		}
		level(n);
		
		//30% chance to be cursed, 70% at night
		//15% chance to be inscribed, 30% at night
		float effectRoll = RingOfLuck.randomFloat(1f);
		if (effectRoll < 0.3f | (Dungeon.checkNight() & RingOfLuck.randomFloat(1f) < 0.7f)) {
			inscribe(Glyph.randomCurse());
			upgrade();
			cursed = true;
		} else if (effectRoll >= 0.85f | (Dungeon.checkNight())){
			inscribe();
		}

		return this;
	}

	public int STRReq(){
		return STRReq(level());
	}

	public int STRReq(int lvl){
		lvl = Math.max(0, lvl);
		//strength req decreases at +1,+3,+6,+10,etc.
		return ((tier + 1) * 5) - (int)(Math.sqrt(8 * lvl + 1) - 1)/2;
	}
	
	@Override
	public int price() {
		if (seal != null) return 0;

		int price = 20 * tier;
		if (hasGoodGlyph()) {
			price *= 1.5;
		}
		if (cursedKnown && (cursed || hasCurseGlyph())) {
			price /= 2;
		}
		if (levelKnown && level() > 0) {
			price *= (level() + 1);
		}
		if (price < 1) {
			price = 1;
		}
		return price;
	}

	public Armor inscribe( Glyph glyph ) {
		if (glyph == null || !glyph.curse()) curseInfusionBonus = false;
		this.glyph = glyph;
		updateQuickslot();
		return this;
	}

	public Armor inscribe() {

		Class<? extends Glyph> oldGlyphClass = glyph != null ? glyph.getClass() : null;
		Glyph gl = Glyph.random( oldGlyphClass );

		return inscribe( gl );
	}

	public boolean hasGlyph(Class<?extends Glyph> type, Char owner) {
		return glyph != null && glyph.getClass() == type && owner.buff(MagicImmune.class) == null;
	}

	//these are not used to process specific glyph effects, so magic immune doesn't affect them
	public boolean hasGoodGlyph(){
		return glyph != null && !glyph.curse();
	}

	public boolean hasCurseGlyph(){
		return glyph != null && glyph.curse();
	}
	
	@Override
	public ItemSprite.Glowing glowing() {
		return glyph != null && (cursedKnown || !glyph.curse()) ? glyph.glowing() : null;
	}
	
	public static abstract class Glyph implements Bundlable {
		
		private static final Class<?>[] common = new Class<?>[]{
				Obfuscation.class, Swiftness.class, Viscosity.class, Potential.class };
		
		private static final Class<?>[] uncommon = new Class<?>[]{
				Brimstone.class, Stone.class, Entanglement.class,
				Repulsion.class, Camouflage.class, Flow.class };
		
		private static final Class<?>[] rare = new Class<?>[]{
				Affection.class, /*AntiMagic.class,*/ Thorns.class };
		
		private static final float[] typeChances = new float[]{
				50, //12.5% each
				40, //6.67% each
				10  //3.33% each
		};

		private static final Class<?>[] curses = new Class<?>[]{
				AntiEntropy.class, Corrosion.class, Displacement.class, Metabolism.class,
				Multiplicity.class, Stench.class, Overgrowth.class, Bulk.class
		};
		
		public abstract int proc( Armor armor, Char attacker, Char defender, int damage );
		
		public String name() {
			if (!curse())
				return name( Messages.get(this, "glyph") );
			else
				return name( Messages.get(Item.class, "curse"));
		}
		
		public String name( String armorName ) {
			return Messages.get(this, "name", armorName);
		}

		public String desc() {
			return Messages.get(this, "desc");
		}

		public boolean curse() {
			return false;
		}
		
		@Override
		public void restoreFromBundle( Bundle bundle ) {
		}

		@Override
		public void storeInBundle( Bundle bundle ) {
		}
		
		public abstract ItemSprite.Glowing glowing();

		@SuppressWarnings("unchecked")
		public static Glyph random( Class<? extends Glyph> ... toIgnore ) {
			switch(RingOfLuck.randomChances(typeChances, 2)){
				case 0: default:
					return randomCommon( toIgnore );
				case 1:
					return randomUncommon( toIgnore );
				case 2:
					return randomRare( toIgnore );
			}
		}
		
		@SuppressWarnings("unchecked")
		public static Glyph randomCommon( Class<? extends Glyph> ... toIgnore ){
			try {
				ArrayList<Class<?>> glyphs = new ArrayList<>(Arrays.asList(common));
				glyphs.removeAll(Arrays.asList(toIgnore));
				if (glyphs.isEmpty()) {
					return random();
				} else {
					return (Glyph) Random.element(glyphs).newInstance();
				}
			} catch (Exception e) {
				ShatteredPixelDungeon.reportException(e);
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		public static Glyph randomUncommon( Class<? extends Glyph> ... toIgnore ){
			try {
				ArrayList<Class<?>> glyphs = new ArrayList<>(Arrays.asList(uncommon));
				glyphs.removeAll(Arrays.asList(toIgnore));
				if (glyphs.isEmpty()) {
					return random();
				} else {
					return (Glyph) Random.element(glyphs).newInstance();
				}
			} catch (Exception e) {
				ShatteredPixelDungeon.reportException(e);
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		public static Glyph randomRare( Class<? extends Glyph> ... toIgnore ){
			try {
				ArrayList<Class<?>> glyphs = new ArrayList<>(Arrays.asList(rare));
				glyphs.removeAll(Arrays.asList(toIgnore));
				if (glyphs.isEmpty()) {
					return random();
				} else {
					return (Glyph) Random.element(glyphs).newInstance();
				}
			} catch (Exception e) {
				ShatteredPixelDungeon.reportException(e);
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		public static Glyph randomCurse( Class<? extends Glyph> ... toIgnore ){
			try {
				ArrayList<Class<?>> glyphs = new ArrayList<>(Arrays.asList(curses));
				glyphs.removeAll(Arrays.asList(toIgnore));
				if (glyphs.isEmpty()) {
					return random();
				} else {
					return (Glyph) Random.element(glyphs).newInstance();
				}
			} catch (Exception e) {
				ShatteredPixelDungeon.reportException(e);
				return null;
			}
		}
		
	}
}
