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

package com.shatteredpixel.cursedpixeldungeon.items;

import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.cursedpixeldungeon.items.allies.DragonCrystal;
import com.shatteredpixel.cursedpixeldungeon.items.allies.EarthenDragon;
import com.shatteredpixel.cursedpixeldungeon.items.allies.FireDragon;
import com.shatteredpixel.cursedpixeldungeon.items.allies.PoisonDragon;
import com.shatteredpixel.cursedpixeldungeon.items.allies.VampiricDragon;
import com.shatteredpixel.cursedpixeldungeon.items.allies.WaterDragon;
import com.shatteredpixel.cursedpixeldungeon.items.armor.Armor;
import com.shatteredpixel.cursedpixeldungeon.items.armor.ClothArmor;
import com.shatteredpixel.cursedpixeldungeon.items.armor.LeatherArmor;
import com.shatteredpixel.cursedpixeldungeon.items.armor.MailArmor;
import com.shatteredpixel.cursedpixeldungeon.items.armor.PlateArmor;
import com.shatteredpixel.cursedpixeldungeon.items.armor.ScaleArmor;
import com.shatteredpixel.cursedpixeldungeon.items.artifacts.AlchemistsToolkit;
import com.shatteredpixel.cursedpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.cursedpixeldungeon.items.artifacts.CapeOfThorns;
import com.shatteredpixel.cursedpixeldungeon.items.artifacts.ChaliceOfBlood;
import com.shatteredpixel.cursedpixeldungeon.items.artifacts.CloakOfShadows;
import com.shatteredpixel.cursedpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.cursedpixeldungeon.items.artifacts.EtherealChains;
import com.shatteredpixel.cursedpixeldungeon.items.artifacts.HornOfPlenty;
import com.shatteredpixel.cursedpixeldungeon.items.artifacts.LloydsBeacon;
import com.shatteredpixel.cursedpixeldungeon.items.artifacts.MasterThievesArmband;
import com.shatteredpixel.cursedpixeldungeon.items.artifacts.SandalsOfNature;
import com.shatteredpixel.cursedpixeldungeon.items.artifacts.TalismanOfForesight;
import com.shatteredpixel.cursedpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.shatteredpixel.cursedpixeldungeon.items.artifacts.UnstableSpellbook;
import com.shatteredpixel.cursedpixeldungeon.items.bags.Bag;
import com.shatteredpixel.cursedpixeldungeon.items.food.Food;
import com.shatteredpixel.cursedpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.cursedpixeldungeon.items.food.Pasty;
import com.shatteredpixel.cursedpixeldungeon.items.potions.Potion;
import com.shatteredpixel.cursedpixeldungeon.items.potions.PotionOfExperience;
import com.shatteredpixel.cursedpixeldungeon.items.potions.PotionOfFrost;
import com.shatteredpixel.cursedpixeldungeon.items.potions.PotionOfHaste;
import com.shatteredpixel.cursedpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.cursedpixeldungeon.items.potions.PotionOfInvisibility;
import com.shatteredpixel.cursedpixeldungeon.items.potions.PotionOfLevitation;
import com.shatteredpixel.cursedpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.cursedpixeldungeon.items.potions.PotionOfMindVision;
import com.shatteredpixel.cursedpixeldungeon.items.potions.PotionOfParalyticGas;
import com.shatteredpixel.cursedpixeldungeon.items.potions.PotionOfPurity;
import com.shatteredpixel.cursedpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.cursedpixeldungeon.items.potions.PotionOfToxicGas;
import com.shatteredpixel.cursedpixeldungeon.items.potions.brews.BlizzardBrew;
import com.shatteredpixel.cursedpixeldungeon.items.potions.brews.CausticBrew;
import com.shatteredpixel.cursedpixeldungeon.items.potions.brews.InfernalBrew;
import com.shatteredpixel.cursedpixeldungeon.items.potions.brews.ShockingBrew;
import com.shatteredpixel.cursedpixeldungeon.items.potions.elixirs.ElixirOfAquaticRejuvenation;
import com.shatteredpixel.cursedpixeldungeon.items.potions.elixirs.ElixirOfArcaneArmor;
import com.shatteredpixel.cursedpixeldungeon.items.potions.elixirs.ElixirOfDragonsBlood;
import com.shatteredpixel.cursedpixeldungeon.items.potions.elixirs.ElixirOfHoneyedHealing;
import com.shatteredpixel.cursedpixeldungeon.items.potions.elixirs.ElixirOfIcyTouch;
import com.shatteredpixel.cursedpixeldungeon.items.potions.elixirs.ElixirOfMight;
import com.shatteredpixel.cursedpixeldungeon.items.potions.elixirs.ElixirOfToxicEssence;
import com.shatteredpixel.cursedpixeldungeon.items.potions.exotic.PotionOfAdrenalineSurge;
import com.shatteredpixel.cursedpixeldungeon.items.potions.exotic.PotionOfCleansing;
import com.shatteredpixel.cursedpixeldungeon.items.potions.exotic.PotionOfCorrosiveGas;
import com.shatteredpixel.cursedpixeldungeon.items.potions.exotic.PotionOfDragonsBreath;
import com.shatteredpixel.cursedpixeldungeon.items.potions.exotic.PotionOfEarthenArmor;
import com.shatteredpixel.cursedpixeldungeon.items.potions.exotic.PotionOfHolyFuror;
import com.shatteredpixel.cursedpixeldungeon.items.potions.exotic.PotionOfMagicalSight;
import com.shatteredpixel.cursedpixeldungeon.items.potions.exotic.PotionOfShielding;
import com.shatteredpixel.cursedpixeldungeon.items.potions.exotic.PotionOfShroudingFog;
import com.shatteredpixel.cursedpixeldungeon.items.potions.exotic.PotionOfSnapFreeze;
import com.shatteredpixel.cursedpixeldungeon.items.potions.exotic.PotionOfStamina;
import com.shatteredpixel.cursedpixeldungeon.items.potions.exotic.PotionOfStormClouds;
import com.shatteredpixel.cursedpixeldungeon.items.rings.Ring;
import com.shatteredpixel.cursedpixeldungeon.items.rings.RingOfAccuracy;
import com.shatteredpixel.cursedpixeldungeon.items.rings.RingOfElements;
import com.shatteredpixel.cursedpixeldungeon.items.rings.RingOfEnergy;
import com.shatteredpixel.cursedpixeldungeon.items.rings.RingOfEvasion;
import com.shatteredpixel.cursedpixeldungeon.items.rings.RingOfForce;
import com.shatteredpixel.cursedpixeldungeon.items.rings.RingOfFuror;
import com.shatteredpixel.cursedpixeldungeon.items.rings.RingOfHaste;
import com.shatteredpixel.cursedpixeldungeon.items.rings.RingOfMight;
import com.shatteredpixel.cursedpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.cursedpixeldungeon.items.rings.RingOfTenacity;
import com.shatteredpixel.cursedpixeldungeon.items.rings.RingOfWealth;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.ScrollOfLullaby;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.ScrollOfRage;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.ScrollOfRetribution;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.ScrollOfTerror;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.exotic.ScrollOfAffection;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.exotic.ScrollOfAntiMagic;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.exotic.ScrollOfConfusion;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.exotic.ScrollOfDivination;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.exotic.ScrollOfEnchantment;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.exotic.ScrollOfForesight;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.exotic.ScrollOfMysticalEnergy;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.exotic.ScrollOfPassage;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.exotic.ScrollOfPetrification;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.exotic.ScrollOfPolymorph;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.exotic.ScrollOfPrismaticImage;
import com.shatteredpixel.cursedpixeldungeon.items.scrolls.exotic.ScrollOfPsionicBlast;
import com.shatteredpixel.cursedpixeldungeon.items.spells.Alchemize;
import com.shatteredpixel.cursedpixeldungeon.items.spells.AquaBlast;
import com.shatteredpixel.cursedpixeldungeon.items.spells.CurseInfusion;
import com.shatteredpixel.cursedpixeldungeon.items.spells.FeatherFall;
import com.shatteredpixel.cursedpixeldungeon.items.spells.MagicalInfusion;
import com.shatteredpixel.cursedpixeldungeon.items.spells.MagicalPorter;
import com.shatteredpixel.cursedpixeldungeon.items.spells.PhaseShift;
import com.shatteredpixel.cursedpixeldungeon.items.spells.ReclaimTrap;
import com.shatteredpixel.cursedpixeldungeon.items.spells.Recycle;
import com.shatteredpixel.cursedpixeldungeon.items.spells.Spell;
import com.shatteredpixel.cursedpixeldungeon.items.spells.WildEnergy;
import com.shatteredpixel.cursedpixeldungeon.items.stones.Runestone;
import com.shatteredpixel.cursedpixeldungeon.items.stones.StoneOfAffection;
import com.shatteredpixel.cursedpixeldungeon.items.stones.StoneOfAggression;
import com.shatteredpixel.cursedpixeldungeon.items.stones.StoneOfAugmentation;
import com.shatteredpixel.cursedpixeldungeon.items.stones.StoneOfBlast;
import com.shatteredpixel.cursedpixeldungeon.items.stones.StoneOfBlink;
import com.shatteredpixel.cursedpixeldungeon.items.stones.StoneOfClairvoyance;
import com.shatteredpixel.cursedpixeldungeon.items.stones.StoneOfDeepenedSleep;
import com.shatteredpixel.cursedpixeldungeon.items.stones.StoneOfDisarming;
import com.shatteredpixel.cursedpixeldungeon.items.stones.StoneOfEnchantment;
import com.shatteredpixel.cursedpixeldungeon.items.stones.StoneOfFlock;
import com.shatteredpixel.cursedpixeldungeon.items.stones.StoneOfIntuition;
import com.shatteredpixel.cursedpixeldungeon.items.stones.StoneOfShock;
import com.shatteredpixel.cursedpixeldungeon.items.wands.Wand;
import com.shatteredpixel.cursedpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.cursedpixeldungeon.items.wands.WandOfCorrosion;
import com.shatteredpixel.cursedpixeldungeon.items.wands.WandOfCorruption;
import com.shatteredpixel.cursedpixeldungeon.items.wands.WandOfDisintegration;
import com.shatteredpixel.cursedpixeldungeon.items.wands.WandOfFireblast;
import com.shatteredpixel.cursedpixeldungeon.items.wands.WandOfFrost;
import com.shatteredpixel.cursedpixeldungeon.items.wands.WandOfLightning;
import com.shatteredpixel.cursedpixeldungeon.items.wands.WandOfLivingEarth;
import com.shatteredpixel.cursedpixeldungeon.items.wands.WandOfMagicMissile;
import com.shatteredpixel.cursedpixeldungeon.items.wands.WandOfPrismaticLight;
import com.shatteredpixel.cursedpixeldungeon.items.wands.WandOfRegrowth;
import com.shatteredpixel.cursedpixeldungeon.items.wands.WandOfTransfusion;
import com.shatteredpixel.cursedpixeldungeon.items.wands.WandOfWarding;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.AssassinsBlade;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.BattleAxe;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.Crossbow;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.Dagger;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.Dirk;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.Flail;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.Gauntlet;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.Glaive;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.Gloves;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.Greataxe;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.Greatshield;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.Greatsword;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.HandAxe;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.Longsword;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.Mace;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.Quarterstaff;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RoundShield;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.RunicBlade;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.Sai;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.Scimitar;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.Shortsword;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.Spear;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.Sword;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.WarHammer;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.Whip;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.melee.WornShortsword;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.missiles.Bolas;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.missiles.FishingSpear;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.missiles.ForceCube;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.missiles.HeavyBoomerang;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.missiles.Javelin;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.missiles.Kunai;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.missiles.Shuriken;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.missiles.ThrowingClub;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.missiles.ThrowingHammer;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.missiles.ThrowingKnife;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.missiles.ThrowingSpear;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.missiles.ThrowingStone;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.missiles.Tomahawk;
import com.shatteredpixel.cursedpixeldungeon.items.weapon.missiles.Trident;
import com.shatteredpixel.cursedpixeldungeon.plants.Blindweed;
import com.shatteredpixel.cursedpixeldungeon.plants.Dreamfoil;
import com.shatteredpixel.cursedpixeldungeon.plants.Earthroot;
import com.shatteredpixel.cursedpixeldungeon.plants.Fadeleaf;
import com.shatteredpixel.cursedpixeldungeon.plants.Firebloom;
import com.shatteredpixel.cursedpixeldungeon.plants.Icecap;
import com.shatteredpixel.cursedpixeldungeon.plants.Plant;
import com.shatteredpixel.cursedpixeldungeon.plants.Rotberry;
import com.shatteredpixel.cursedpixeldungeon.plants.Sorrowmoss;
import com.shatteredpixel.cursedpixeldungeon.plants.Starflower;
import com.shatteredpixel.cursedpixeldungeon.plants.Stormvine;
import com.shatteredpixel.cursedpixeldungeon.plants.Sungrass;
import com.shatteredpixel.cursedpixeldungeon.plants.Swiftthistle;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Generator {

	public enum Category {
		WEAPON	( 6,    MeleeWeapon.class),
		WEP_T1	( 0,    MeleeWeapon.class),
		WEP_T2	( 0,    MeleeWeapon.class),
		WEP_T3	( 0,    MeleeWeapon.class),
		WEP_T4	( 0,    MeleeWeapon.class),
		WEP_T5	( 0,    MeleeWeapon.class),
		
		ARMOR	( 4,    Armor.class ),
		
		MISSILE ( 3,    MissileWeapon.class ),
		MIS_T1  ( 0,    MissileWeapon.class ),
		MIS_T2  ( 0,    MissileWeapon.class ),
		MIS_T3  ( 0,    MissileWeapon.class ),
		MIS_T4  ( 0,    MissileWeapon.class ),
		MIS_T5  ( 0,    MissileWeapon.class ),
		ALLIES  ( 1,     DragonCrystal.class),
		
		WAND	( 3,    Wand.class ),
		RING	( 1,    Ring.class ),
		ARTIFACT( 1,    Artifact.class),
		
		FOOD	( 0,    Food.class ),
		
		POTION	( 20,   Potion.class ),
		POTION_EXOTIC	( 1,   Potion.class ),
		ELXIR	( 1,   Potion.class ),
		SEED	( 0,    Plant.Seed.class ), //dropped by grass
		
		SCROLL	( 20,   Scroll.class ),
		SPELL ( 0,   Spell.class ),
		SCROLL_EXOTIC (1,   Scroll.class),
		STONE   ( 2,    Runestone.class),
		
		GOLD	( 18,   Gold.class );
		
		public Class<?>[] classes;
		public float[] probs;
		
		public float prob;
		public Class<? extends Item> superClass;
		
		private Category( float prob, Class<? extends Item> superClass ) {
			this.prob = prob;
			this.superClass = superClass;
		}
		
		public static int order( Item item ) {
			for (int i=0; i < values().length; i++) {
				if (values()[i].superClass.isInstance( item )) {
					return i;
				}
			}
			
			return item instanceof Bag ? Integer.MAX_VALUE : Integer.MAX_VALUE - 1;
		}
		
		private static final float[] INITIAL_ARTIFACT_PROBS = new float[]{ 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1};
		
		static {
			GOLD.classes = new Class<?>[]{
					Gold.class };
			GOLD.probs = new float[]{ 1 };

			ALLIES.classes = new Class<?>[]{
					FireDragon.class,
					PoisonDragon.class,
					WaterDragon.class,
					VampiricDragon.class,
					EarthenDragon.class
			};

			ALLIES.probs = new float[]{1,1,1,1,1};//All Allies are equally likely

			POTION.classes = new Class<?>[]{
					PotionOfStrength.class, //2 drop every chapter, see Dungeon.posNeeded()
					PotionOfHealing.class,
					PotionOfMindVision.class,
					PotionOfFrost.class,
					PotionOfLiquidFlame.class,
					PotionOfToxicGas.class,
					PotionOfHaste.class,
					PotionOfInvisibility.class,
					PotionOfLevitation.class,
					PotionOfParalyticGas.class,
					PotionOfPurity.class,
					PotionOfExperience.class};
			POTION.probs = new float[]{ 0, 6, 4, 3, 3, 3, 2, 2, 2, 2, 2, 1 };

			POTION_EXOTIC.classes = new Class<?>[]{
					PotionOfAdrenalineSurge.class, //2 drop every chapter, see Dungeon.posNeeded()
					PotionOfShielding.class,
					PotionOfMagicalSight.class,
					PotionOfSnapFreeze.class,
					PotionOfDragonsBreath.class,
					PotionOfCorrosiveGas.class,
					PotionOfStamina.class,
					PotionOfShroudingFog.class,
					PotionOfStormClouds.class,
					PotionOfEarthenArmor.class,
					PotionOfCleansing.class,
					PotionOfHolyFuror.class};
			POTION_EXOTIC.probs = new float[]{ 0, 6, 4, 3, 3, 3, 2, 2, 2, 2, 2, 1 };

			ELXIR.classes = new Class<?>[] {
					BlizzardBrew.class,
					CausticBrew.class,
					InfernalBrew.class,
					ShockingBrew.class,
					ElixirOfAquaticRejuvenation.class,
					ElixirOfArcaneArmor.class,
					ElixirOfDragonsBlood.class,
					ElixirOfHoneyedHealing.class,
					ElixirOfIcyTouch.class,
					ElixirOfMight.class,
					ElixirOfToxicEssence.class
			};

			ELXIR.probs = new float[] {1,1,1,1,1,1,1,1,1,1,1};

			SEED.classes = new Class<?>[]{
					Rotberry.Seed.class, //quest item
					Blindweed.Seed.class,
					Dreamfoil.Seed.class,
					Earthroot.Seed.class,
					Fadeleaf.Seed.class,
					Firebloom.Seed.class,
					Icecap.Seed.class,
					Sorrowmoss.Seed.class,
					Stormvine.Seed.class,
					Sungrass.Seed.class,
					Swiftthistle.Seed.class,
					Starflower.Seed.class};
			SEED.probs = new float[]{ 0, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 1 };



			SPELL.classes = new Class<?>[]{
					CurseInfusion.class,
					MagicalInfusion.class,
					MagicalPorter.class,
					PhaseShift.class,
					WildEnergy.class,
					AquaBlast.class,
					FeatherFall.class,
					ReclaimTrap.class,
					Alchemize.class,
					Recycle.class
			};
			SPELL.probs = new float[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

			SCROLL.classes = new Class<?>[]{
					ScrollOfUpgrade.class, //3 drop every chapter, see Dungeon.souNeeded()
					ScrollOfIdentify.class,
					ScrollOfRemoveCurse.class,
					ScrollOfMirrorImage.class,
					ScrollOfRecharging.class,
					ScrollOfTeleportation.class,
					ScrollOfLullaby.class,
					ScrollOfMagicMapping.class,
					ScrollOfRage.class,
					ScrollOfRetribution.class,
					ScrollOfTerror.class,
					ScrollOfTransmutation.class
			};

			SCROLL.probs = new float[]{ 1, 6, 4, 3, 3, 3, 2, 2, 2, 2, 2, 1};

			SCROLL_EXOTIC.classes = new Class<?>[]{
					ScrollOfEnchantment.class,
					ScrollOfDivination.class,
					ScrollOfAntiMagic.class,
					ScrollOfPrismaticImage.class,
					ScrollOfMysticalEnergy.class,
					ScrollOfPassage.class,
					ScrollOfAffection.class,
					ScrollOfForesight.class,
					ScrollOfConfusion.class,
					ScrollOfPsionicBlast.class,
					ScrollOfPetrification.class,
					ScrollOfPolymorph.class
			};
			SCROLL_EXOTIC.probs = SCROLL.probs.clone();
			
			STONE.classes = new Class<?>[]{
					StoneOfEnchantment.class,   //1 is guaranteed to drop on floors 6-19
					StoneOfAugmentation.class,  //1 is sold in each shop
					StoneOfIntuition.class,     //1 additional stone is also dropped on floors 1-3
					StoneOfAggression.class,
					StoneOfAffection.class,
					StoneOfBlast.class,
					StoneOfBlink.class,
					StoneOfClairvoyance.class,
					StoneOfDeepenedSleep.class,
					StoneOfDisarming.class,
					StoneOfFlock.class,
					StoneOfShock.class
			};
			STONE.probs = new float[]{ 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
			
			//TODO: add last ones when implemented
			WAND.classes = new Class<?>[]{
					WandOfMagicMissile.class,
					WandOfLightning.class,
					WandOfDisintegration.class,
					WandOfFireblast.class,
					WandOfCorrosion.class,
					WandOfBlastWave.class,
					WandOfLivingEarth.class,
					WandOfFrost.class,
					WandOfPrismaticLight.class,
					WandOfWarding.class,
					WandOfTransfusion.class,
					WandOfCorruption.class,
					WandOfRegrowth.class };
			WAND.probs = new float[]{ 5, 4, 4, 4, 4, 3, 3, 3, 3,3, 3, 3, 3 };
			
			//see generator.randomWeapon
			WEAPON.classes = new Class<?>[]{};
			WEAPON.probs = new float[]{};
			
			WEP_T1.classes = new Class<?>[]{
					WornShortsword.class,
					Gloves.class,
					Dagger.class,
					MagesStaff.class
			};
			WEP_T1.probs = new float[]{ 1, 1, 1, 0 };
			
			WEP_T2.classes = new Class<?>[]{
					Shortsword.class,
					HandAxe.class,
					Spear.class,
					Quarterstaff.class,
					RunicBlade.class,
					Dirk.class

			};
			WEP_T2.probs = new float[]{ 6, 5, 5, 4, 3, 4 };
			
			WEP_T3.classes = new Class<?>[]{
					Sword.class,
					Mace.class,
					Scimitar.class,
					RoundShield.class,
					Sai.class,
					Whip.class
			};
			WEP_T3.probs = new float[]{ 6, 5, 5, 4, 4, 4 };
			
			WEP_T4.classes = new Class<?>[]{
					Longsword.class,
					BattleAxe.class,
					Flail.class,
					AssassinsBlade.class,
					Crossbow.class
			};
			WEP_T4.probs = new float[]{ 6, 5, 5, 4, 4 };
			
			WEP_T5.classes = new Class<?>[]{
					Greatsword.class,
					WarHammer.class,
					Glaive.class,
					Greataxe.class,
					Greatshield.class,
					Gauntlet.class
			};
			WEP_T5.probs = new float[]{ 6, 5, 5, 4, 4, 4 };
			
			//see Generator.randomArmor
			ARMOR.classes = new Class<?>[]{
					ClothArmor.class,
					LeatherArmor.class,
					MailArmor.class,
					ScaleArmor.class,
					PlateArmor.class };
			ARMOR.probs = new float[]{ 0, 0, 0, 0, 0 };
			
			//see Generator.randomMissile
			MISSILE.classes = new Class<?>[]{};
			MISSILE.probs = new float[]{};
			
			MIS_T1.classes = new Class<?>[]{
					ThrowingStone.class,
					ThrowingKnife.class
			};
			MIS_T1.probs = new float[]{ 6, 5 };
			
			MIS_T2.classes = new Class<?>[]{
					FishingSpear.class,
					ThrowingClub.class,
					Shuriken.class
			};
			MIS_T2.probs = new float[]{ 6, 5, 4 };
			
			MIS_T3.classes = new Class<?>[]{
					ThrowingSpear.class,
					Kunai.class,
					Bolas.class
			};
			MIS_T3.probs = new float[]{ 6, 5, 4 };
			
			MIS_T4.classes = new Class<?>[]{
					Javelin.class,
					Tomahawk.class,
					HeavyBoomerang.class
			};
			MIS_T4.probs = new float[]{ 6, 5, 4 };
			
			MIS_T5.classes = new Class<?>[]{
					Trident.class,
					ThrowingHammer.class,
					ForceCube.class
			};
			MIS_T5.probs = new float[]{ 6, 5, 4 };
			
			FOOD.classes = new Class<?>[]{
					Food.class,
					Pasty.class,
					MysteryMeat.class };
			FOOD.probs = new float[]{ 4, 1, 0 };
			
			RING.classes = new Class<?>[]{
					RingOfAccuracy.class,
					RingOfEvasion.class,
					RingOfElements.class,
					RingOfForce.class,
					RingOfFuror.class,
					RingOfHaste.class,
					RingOfEnergy.class,
					RingOfMight.class,
					RingOfSharpshooting.class,
					RingOfTenacity.class,
					RingOfWealth.class};
			RING.probs = new float[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 };//Ring of Wealth can no longer generate naturally. Ring of Tenacity is temporarily removed.
			
			ARTIFACT.classes = new Class<?>[]{
					CapeOfThorns.class,
					ChaliceOfBlood.class,
					CloakOfShadows.class,
					HornOfPlenty.class,
					MasterThievesArmband.class,
					SandalsOfNature.class,
					TalismanOfForesight.class,
					TimekeepersHourglass.class,
					UnstableSpellbook.class,
					AlchemistsToolkit.class,
					DriedRose.class,
					LloydsBeacon.class,
					EtherealChains.class
			};
			ARTIFACT.probs = INITIAL_ARTIFACT_PROBS.clone();
		}
	}

	private static final float[][] floorSetTierProbs = new float[][] {
			{0, 70, 20,  8,  2},
			{0, 25, 50, 20,  5},
			{0, 10, 40, 40, 10},
			{0,  5, 20, 50, 25},
			{0,  2,  8, 20, 70}
	};
	
	private static HashMap<Category,Float> categoryProbs = new LinkedHashMap<>();
	
	public static void reset() {
		for (Category cat : Category.values()) {
			categoryProbs.put( cat, cat.prob );
		}
	}
	
	public static Item random() {
		Category cat = Random.chances( categoryProbs );
		if (cat == null){
			reset();
			cat = Random.chances( categoryProbs );
		}
		categoryProbs.put( cat, categoryProbs.get( cat ) - 1);
		return random( cat );
	}
	
	public static Item random( Category cat ) {
		try {
			
			switch (cat) {
			case ARMOR:
				return randomArmor();
			case WEAPON:
				return randomWeapon();
			case MISSILE:
				return randomMissile();
			case ARTIFACT:
				Item item = randomArtifact();
				//if we're out of artifacts, return a ring instead.
				return item != null ? item : random(Category.RING);
			default:
				return ((Item)cat.classes[Random.chances( cat.probs )].newInstance()).random();
			}
			
		} catch (Exception e) {

			ShatteredPixelDungeon.reportException(e);
			return null;
			
		}
	}
	
	public static Item random( Class<? extends Item> cl ) {
		try {
			
			return ((Item)cl.newInstance()).random();
			
		} catch (Exception e) {

			ShatteredPixelDungeon.reportException(e);
			return null;
			
		}
	}

	public static Armor randomArmor(){
		return randomArmor(Dungeon.depth / 5);
	}
	
	public static Armor randomArmor(int floorSet) {

		floorSet = (int)GameMath.gate(0, floorSet, floorSetTierProbs.length-1);

		try {
			Armor a = (Armor)Category.ARMOR.classes[Random.chances(floorSetTierProbs[floorSet])].newInstance();
			a.random();
			return a;
		} catch (Exception e) {
			ShatteredPixelDungeon.reportException(e);
			return null;
		}
	}

	public static final Category[] wepTiers = new Category[]{
			Category.WEP_T1,
			Category.WEP_T2,
			Category.WEP_T3,
			Category.WEP_T4,
			Category.WEP_T5,
			Category.WEP_T5
	};

	public static MeleeWeapon randomWeapon(){
		return randomWeapon(Dungeon.depth / 5);
	}
	
	public static MeleeWeapon randomWeapon(int floorSet) {

		floorSet = (int)GameMath.gate(0, floorSet, floorSetTierProbs.length-1);

		try {
			Category c = wepTiers[Random.chances(floorSetTierProbs[floorSet])];
			MeleeWeapon w = (MeleeWeapon)c.classes[Random.chances(c.probs)].newInstance();
			w.random();
			return w;
		} catch (Exception e) {
			ShatteredPixelDungeon.reportException(e);
			return null;
		}
	}
	
	public static final Category[] misTiers = new Category[]{
			Category.MIS_T1,
			Category.MIS_T2,
			Category.MIS_T3,
			Category.MIS_T4,
			Category.MIS_T5
	};
	
	public static MissileWeapon randomMissile(){
		return randomMissile(Dungeon.depth / 5);
	}
	
	public static MissileWeapon randomMissile(int floorSet) {
		
		floorSet = (int)GameMath.gate(0, floorSet, floorSetTierProbs.length-1);
		
		try {
			Category c = misTiers[Random.chances(floorSetTierProbs[floorSet])];
			MissileWeapon w = (MissileWeapon)c.classes[Random.chances(c.probs)].newInstance();
			w.random();
			return w;
		} catch (Exception e) {
			ShatteredPixelDungeon.reportException(e);
			return null;
		}
	}

	//enforces uniqueness of artifacts throughout a run.
	public static Artifact randomArtifact() {

		try {
			Category cat = Category.ARTIFACT;
			int i = Random.chances( cat.probs );

			//if no artifacts are left, return null
			if (i == -1){
				return null;
			}
			
			Class<?extends Artifact> art = (Class<? extends Artifact>) cat.classes[i];

			if (removeArtifact(art)) {
				Artifact artifact = art.newInstance();
				
				artifact.random();
				
				return artifact;
			} else {
				return null;
			}

		} catch (Exception e) {
			ShatteredPixelDungeon.reportException(e);
			return null;
		}
	}

	public static boolean removeArtifact(Class<?extends Artifact> artifact) {
		if (spawnedArtifacts.contains(artifact))
			return false;

		Category cat = Category.ARTIFACT;
		for (int i = 0; i < cat.classes.length; i++)
			if (cat.classes[i].equals(artifact)) {
				if (cat.probs[i] == 1){
					cat.probs[i] = 0;
					spawnedArtifacts.add(artifact);
					return true;
				} else
					return false;
			}

		return false;
	}

	//resets artifact probabilities, for new dungeons
	public static void initArtifacts() {
		Category.ARTIFACT.probs = Category.INITIAL_ARTIFACT_PROBS.clone();
		spawnedArtifacts = new ArrayList<>();
	}

	private static ArrayList<Class<?extends Artifact>> spawnedArtifacts = new ArrayList<>();
	
	private static final String GENERAL_PROBS = "general_probs";
	private static final String SPAWNED_ARTIFACTS = "spawned_artifacts";
	
	public static void storeInBundle(Bundle bundle) {
		Float[] genProbs = categoryProbs.values().toArray(new Float[0]);
		float[] storeProbs = new float[genProbs.length];
		for (int i = 0; i < storeProbs.length; i++){
			storeProbs[i] = genProbs[i];
		}
		bundle.put( GENERAL_PROBS, storeProbs);
		
		bundle.put( SPAWNED_ARTIFACTS, spawnedArtifacts.toArray(new Class[0]));
	}

	public static void restoreFromBundle(Bundle bundle) {
		if (bundle.contains(GENERAL_PROBS)){
			float[] probs = bundle.getFloatArray(GENERAL_PROBS);
			for (int i = 0; i < probs.length; i++){
				categoryProbs.put(Category.values()[i], probs[i]);
			}
		} else {
			reset();
		}
		
		initArtifacts();
		
		for ( Class<?extends Artifact> artifact : bundle.getClassArray(SPAWNED_ARTIFACTS) ){
			removeArtifact(artifact);
		}
		
	}
}
