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

package com.shatteredpixel.cursedpixeldungeon.actors.hero;

import com.shatteredpixel.cursedpixeldungeon.Assets;
import com.shatteredpixel.cursedpixeldungeon.Badges;
import com.shatteredpixel.cursedpixeldungeon.Challenges;
import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.items.BrokenSeal;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.items.TomeOfMastery;
import com.shatteredpixel.cursedpixeldungeon.items.allies.PoisonDragon;
import com.shatteredpixel.cursedpixeldungeon.items.armor.ClothArmor;
import com.shatteredpixel.cursedpixeldungeon.items.armor.LeatherArmor;
import com.shatteredpixel.cursedpixeldungeon.items.artifacts.CloakOfShadows;
import com.shatteredpixel.cursedpixeldungeon.items.bags.FoodHolder;
import com.shatteredpixel.cursedpixeldungeon.items.bags.MagicalHolster;
import com.shatteredpixel.cursedpixeldungeon.items.bags.PotionBandolier;
import com.shatteredpixel.cursedpixeldungeon.items.bags.ScrollHolder;
import com.shatteredpixel.cursedpixeldungeon.items.bags.VelvetPouch;
import com.shatteredpixel.cursedpixeldungeon.items.food.Food;
import com.shatteredpixel.cursedpixeldungeon.items.food.SmallRation;
import com.shatteredpixel.cursedpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.cursedpixeldungeon.items.potions.PotionOfInvisibility;
import com.shatteredpixel.cursedpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.cursedpixeldungeon.items.potions.PotionOfMindVision;
import com.shatteredpixel.cursedpixeldungeon.items.potions.PotionOfPurity;
import com.shatteredpixel.cursedpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.ScrollOfLullaby;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.ScrollOfRage;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.cursedpixeldungeon.items.wands.WandOfDisintegration;
import com.shatteredpixel.cursedpixeldungeon.items.wands.WandOfMagicMissile;
import com.shatteredpixel.cursedpixeldungeon.items.wands.WandOfWarding;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.Dagger;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.Gloves;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.WornShortsword;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.missiles.ThrowingKnife;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.missiles.ThrowingStone;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.watabou.utils.Bundle;
import com.watabou.utils.DeviceCompat;

public enum HeroClass {

	WARRIOR( "warrior", HeroSubClass.BERSERKER, HeroSubClass.GLADIATOR ),
	MAGE( "mage", HeroSubClass.BATTLEMAGE, HeroSubClass.WARLOCK ),
	ROGUE( "rogue", HeroSubClass.ASSASSIN, HeroSubClass.FREERUNNER ),
	HUNTRESS( "huntress", HeroSubClass.SNIPER, HeroSubClass.WARDEN ),
	PRIESTESS("priestess", HeroSubClass.NECROMACER, HeroSubClass.MEDIC);


	private String title;
	private HeroSubClass[] subClasses;

	HeroClass( String title, HeroSubClass...subClasses ) {
		this.title = title;
		this.subClasses = subClasses;
	}

	public void initHero( Hero hero ) {

		hero.heroClass = this;

		initCommon( hero );

		switch (this) {
			case WARRIOR:
				initWarrior( hero );
				break;

			case MAGE:
				initMage( hero );
				break;

			case ROGUE:
				initRogue( hero );
				break;

			case HUNTRESS:
				initHuntress( hero );
				break;
			case PRIESTESS:
				initPriestess(hero);
				break;
		}
		
	}

	private static void initCommon( Hero hero ) {
		Item i = new ClothArmor().identify();
		if (!Challenges.isItemBlocked(i)) hero.belongings.armor = (ClothArmor)i;

		i = new Food();
		if (!Challenges.isItemBlocked(i)) i.collect();

		if (Dungeon.isChallenged(Challenges.NO_FOOD)){
			new SmallRation().collect();
		}
		new FoodHolder().collect();
		Dungeon.LimitedDrops.FOOD_HOLDER.drop();
		new ScrollOfIdentify().identify();

		new PotionBandolier().collect();
		Dungeon.LimitedDrops.POTION_BANDOLIER.drop();

		new ScrollHolder().collect();
		Dungeon.LimitedDrops.SCROLL_HOLDER.drop();

		new VelvetPouch().collect();
		Dungeon.LimitedDrops.VELVET_POUCH.drop();

		new MagicalHolster().collect();
		Dungeon.LimitedDrops.MAGICAL_HOLSTER.drop();

		if (!Dungeon.isChallenged(Challenges.NO_HEALING)) {
			new PotionOfHealing().quantity(3).collect();
			new PotionOfPurity().quantity(1).collect();
		} else {
			new PotionOfPurity().quantity(3).collect();
		}

		if (Badges.isUnlocked(Badges.Badge.BOSS_SLAIN_4)) {
			new TomeOfMastery().collect();
		}
	}

	public Badges.Badge masteryBadge() {
		switch (this) {
			case WARRIOR:
				return Badges.Badge.MASTERY_WARRIOR;
			case MAGE:
				return Badges.Badge.MASTERY_MAGE;
			case ROGUE:
				return Badges.Badge.MASTERY_ROGUE;
			case HUNTRESS:
				return Badges.Badge.MASTERY_HUNTRESS;
			case PRIESTESS:
				return Badges.Badge.MASTERY_HUNTRESS;
		}
		return null;
	}

	private static void initPriestess( Hero hero) {
		(hero.belongings.weapon = new WornShortsword()).identify().upgrade();
		(hero.belongings.armor = new ClothArmor()).identify();
		(hero.belongings.misc1 = new PoisonDragon()).identify().upgrade();
		hero.belongings.misc1.activate(hero);
		hero.HP = hero.HT = 20;
		Dungeon.quickslot.setSlot(0,hero.belongings.misc1);
		new PotionOfHealing().identify();
		new ScrollOfMirrorImage().identify();
	}

	private static void initWarrior( Hero hero ) {
		(hero.belongings.weapon = new WornShortsword()/*.enchant(new Precise())*/).identify().upgrade();
		(hero.belongings.armor = new LeatherArmor()).identify().upgrade();
		hero.HP = hero.HT = 24;
		ThrowingStone stones = new ThrowingStone();
		stones.quantity(3).collect();
		Dungeon.quickslot.setSlot(0, stones);
		if (hero.belongings.armor != null){
			hero.belongings.armor.affixSeal(new BrokenSeal());
		}
		

		
		new PotionOfStrength().identify();
		new ScrollOfRage().identify();
	}

	private static void initMage( Hero hero ) {
		MagesStaff staff;
		//new PoisonDragon().identify().collect();
		//new EarthenDragon().identify().collect();
		//new ScrollOfUpgrade().quantity(5).collect();
		//new WandOfLightning().identify().upgrade(2).collect();
		staff = new MagesStaff(new WandOfMagicMissile());
		/*new WandOfMagicMissile().enchant(Weapon.Enchantment.random()).identify().upgrade(5).collect();
		new WandOfMagicMissile().identify().collect();
		new ScrollOfEnchantment().quantity(5).identify().collect();
		new StoneOfEnchantment().quantity(5).identify().collect();*/
		staff.upgrade();
		(hero.belongings.weapon = staff).identify();
		//(hero.belongings.misc1 = new RingOfWealth()).upgrade(20).identify();
		hero.belongings.weapon.activate(hero);

		Dungeon.quickslot.setSlot(0, staff);


		
		new ScrollOfUpgrade().identify();
		new PotionOfLiquidFlame().identify();
	}

	private static void initRogue( Hero hero ) {
		(hero.belongings.weapon = new Dagger()).identify().upgrade(2);

		CloakOfShadows cloak = new CloakOfShadows();
		(hero.belongings.misc1 = cloak).identify();
		hero.belongings.misc1.activate( hero );

		ThrowingKnife knives = new ThrowingKnife();
		knives.quantity(3).collect();

		Dungeon.quickslot.setSlot(0, cloak);
		Dungeon.quickslot.setSlot(1, knives);


		
		new ScrollOfMagicMapping().identify();
		new PotionOfInvisibility().identify();
	}

	private static void initHuntress( Hero hero ) {

		(hero.belongings.weapon = new Gloves()).identify().upgrade();
		SpiritBow bow = new SpiritBow();
		bow.identify().upgrade().collect();
		hero.HT = hero.HP = 16;

		Dungeon.quickslot.setSlot(0, bow);


		
		new PotionOfMindVision().identify();
		new ScrollOfLullaby().identify();
	}
	
	public String title() {
		return Messages.get(HeroClass.class, title);
	}
	
	public HeroSubClass[] subClasses() {
		return subClasses;
	}
	
	public String spritesheet() {
		switch (this) {
			case WARRIOR: default:
				return Assets.WARRIOR;
			case MAGE:
				return Assets.MAGE;
			case ROGUE:
				return Assets.ROGUE;
			case HUNTRESS:
				return Assets.HUNTRESS;
			case PRIESTESS:
				return Assets.PRIESTESS;
		}
	}
	
	public String[] perks() {
		switch (this) {
			case WARRIOR: default:
				return new String[]{
						Messages.get(HeroClass.class, "warrior_perk1"),
						Messages.get(HeroClass.class, "warrior_perk2"),
						Messages.get(HeroClass.class, "warrior_perk3"),
						Messages.get(HeroClass.class, "warrior_perk4"),
						Messages.get(HeroClass.class, "warrior_perk5"),
				};
			case MAGE:
				return new String[]{
						Messages.get(HeroClass.class, "mage_perk1"),
						Messages.get(HeroClass.class, "mage_perk2"),
						Messages.get(HeroClass.class, "mage_perk3"),
						Messages.get(HeroClass.class, "mage_perk4"),
						Messages.get(HeroClass.class, "mage_perk5"),
				};
			case ROGUE:
				return new String[]{
						Messages.get(HeroClass.class, "rogue_perk1"),
						Messages.get(HeroClass.class, "rogue_perk2"),
						Messages.get(HeroClass.class, "rogue_perk3"),
						Messages.get(HeroClass.class, "rogue_perk4"),
						Messages.get(HeroClass.class, "rogue_perk5"),
				};
			case HUNTRESS:
				return new String[]{
						Messages.get(HeroClass.class, "huntress_perk1"),
						Messages.get(HeroClass.class, "huntress_perk2"),
						Messages.get(HeroClass.class, "huntress_perk3"),
						Messages.get(HeroClass.class, "huntress_perk4"),
						Messages.get(HeroClass.class, "huntress_perk5"),
				};
			case PRIESTESS:
				return new String[]{
						Messages.get(HeroClass.class, "priestess_perk1"),
						Messages.get(HeroClass.class, "priestess_perk2"),
						Messages.get(HeroClass.class, "priestess_perk3"),
						Messages.get(HeroClass.class, "priestess_perk4"),
						Messages.get(HeroClass.class, "priestess_perk5"),
				};
		}
	}
	
	public boolean isUnlocked(){
		//always unlock on debug builds
		if (DeviceCompat.isDebug()) return true;
		
		switch (this){
			case WARRIOR: default:
				return true;
			case MAGE:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_MAGE);
			case ROGUE:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_ROGUE);
			case HUNTRESS:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_HUNTRESS);
		}
	}
	
	public String unlockMsg() {
		switch (this){
			case WARRIOR: default:
				return "";
			case MAGE:
				return Messages.get(HeroClass.class, "mage_unlock");
			case ROGUE:
				return Messages.get(HeroClass.class, "rogue_unlock");
			case HUNTRESS:
				return Messages.get(HeroClass.class, "huntress_unlock");
		}
	}

	private static final String CLASS	= "class";
	
	public void storeInBundle( Bundle bundle ) {
		bundle.put( CLASS, toString() );
	}
	
	public static HeroClass restoreInBundle( Bundle bundle ) {
		String value = bundle.getString( CLASS );
		return value.length() > 0 ? valueOf( value ) : ROGUE;
	}
}
