package juggernaut;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import juggernaut.cards.*;
import juggernaut.characters.TheJuggernaut;
import juggernaut.patches.AbstractCardEnum;
import juggernaut.patches.TheJuggernautEnum;
import juggernaut.relics.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;

@SpireInitializer
public class JuggernautMod implements PostInitializeSubscriber,
	EditCardsSubscriber, EditRelicsSubscriber, EditCharactersSubscriber,
	EditStringsSubscriber, SetUnlocksSubscriber, OnCardUseSubscriber,
	EditKeywordsSubscriber, OnPowersModifiedSubscriber, PostExhaustSubscriber,
	PostBattleSubscriber, PostDungeonInitializeSubscriber, PostDrawSubscriber {
	public static final Logger logger = LogManager.getLogger(JuggernautMod.class.getName());
	
    private static final String MODNAME = "JuggernautMod";
    private static final String AUTHOR = "Paltorz, and the FruityMod team";
    private static final String DESCRIPTION = "v0.4.3\n Adds The Juggernaut as a playable third character";
    
    public static final Color COPPER = CardHelper.getColor(186.0f, 108.0f, 31.0f);
    public static final String FRUITY_MOD_ASSETS_FOLDER = "img";

    public static final String RESROUCE_PATH = "juggernaut/";
    public static final String IMG_PATH = RESROUCE_PATH + "img/";
    public static final String LOCALIZATION_PATH = RESROUCE_PATH + "localization/";

    public static final String CARD_STRINGS = LOCALIZATION_PATH + "JuggernautMod-CardStrings.json";
    public static final String RELIC_STRINGS = LOCALIZATION_PATH + "JuggernautMod-RelicStrings.json";

    // card backgrounds
    private static final String ATTACK_COPPER = IMG_PATH + "512/bg_attack_brown.png";
    private static final String SKILL_COPPER = IMG_PATH + "512/bg_attack_brown.png";
    private static final String POWER_COPPER = IMG_PATH + "512/bg_attack_brown.png";
    private static final String ENERGY_ORB_COPPER = IMG_PATH + "512/card_brown_orb.png";
    
    private static final String ATTACK_COPPER_PORTRAIT = IMG_PATH + "1024/bg_attack_brown.png";
    private static final String SKILL_COPPER_PORTRAIT = IMG_PATH + "1024/bg_attack_brown.png";
    private static final String POWER_COPPER_PORTRAIT = IMG_PATH + "1024/bg_attack_brown.png";
    private static final String ENERGY_ORB_COPPER_PORTRAIT = IMG_PATH + "1024/card_brown_orb.png";

    private static final String ENERGY_ORB_IN_DESCRIPTION = IMG_PATH + "energy/energyOrbInDescription.png";
    
    // card images
    public static final String STRIKE_PURPLE = IMG_PATH + "cards/strike_purple.png";
    public static final String DEFEND_PURPLE = IMG_PATH + "cards/defend_purple.png";

    public static final String EARTHQUAKE = IMG_PATH + "cards/earth_quake.png";
    public static final String ON_GUARD = IMG_PATH + "cards/on_guard.png";
    public static final String FURY = IMG_PATH + "cards/fury.png";
    public static final String STRUGGLE = IMG_PATH + "cards/struggle.png";
    public static final String SKULL_BASH = IMG_PATH + "cards/skull_bash.png";
    public static final String HAMMER_ARM = IMG_PATH + "cards/hammer_arm.png";
    public static final String PULVERIZE = IMG_PATH + "cards/pulverize.png";
    public static final String SHOULDER_BLOW = IMG_PATH + "cards/shoulder_blow.png";
    public static final String FLYING_PRESS = IMG_PATH + "cards/flying_press.png";
    public static final String FRENZY = IMG_PATH + "cards/frenzy.png";
    public static final String RELENTLESS_BLOWS = IMG_PATH + "cards/relentless_blows.png";
    public static final String LUNGE = IMG_PATH + "cards/lunge.png";
    public static final String CHALLENGING_ROAR = IMG_PATH + "cards/challenging_roar.png";
    public static final String SIMPLE_MINDED = IMG_PATH + "cards/simple_minded.png";
    public static final String HYSTERIA = IMG_PATH + "cards/hysteria.png";
    public static final String SMOTHER = IMG_PATH + "cards/smother.png";
    public static final String CASCADING_STEEL = IMG_PATH + "cards/cascading_steel.png";
    public static final String FEINT = IMG_PATH + "cards/feint.png";
    public static final String BULLDOZE = IMG_PATH + "cards/bulldoze.png";
    public static final String PURSUIT = IMG_PATH + "cards/pursuit.png";
    public static final String ENDURE = IMG_PATH + "cards/endure.png";
    public static final String GALVANIZE = IMG_PATH + "cards/galvanize.png";
    public static final String ACCELERATE = IMG_PATH + "cards/accelerate.png";
    public static final String BULWARK = IMG_PATH + "cards/bulwark.png";
    public static final String FOCUSED_POWER = IMG_PATH + "cards/focused_power.png";
    public static final String UNSHAKABLE = IMG_PATH + "cards/unshakable.png";
    public static final String CONVERT_FLESH = IMG_PATH + "cards/convert_flesh.png";
    public static final String VENGEANCE = IMG_PATH + "cards/vengeance.png";
    public static final String OVERPOWER = IMG_PATH + "cards/overpower.png";
    public static final String HEAVY_CRASH = IMG_PATH + "cards/heavy_crash.png";
    public static final String STEEL_FORCE = IMG_PATH + "cards/steel_force.png";
    public static final String LASH_OUT = IMG_PATH + "cards/lash_out.png";
    public static final String UNSTOPPABLE_FORCE = IMG_PATH + "cards/unstoppable_force.png";
    public static final String HEAVY_ASSAULT = IMG_PATH + "cards/heavy_assault.png";
    public static final String SLOW_AND_STEADY = IMG_PATH + "cards/slow_and_steady.png";
    public static final String BREAKTHROUGH = IMG_PATH + "cards/breakthrough.png";
    public static final String HEAVY_ARMOR = IMG_PATH + "cards/heavy_armor.png";
    public static final String GRAPPLE = IMG_PATH + "cards/grapple.png";
    public static final String INHUMAN_RECOVERY = IMG_PATH + "cards/inhuman_recovery.png";
    public static final String HUNKER_DOWN = IMG_PATH + "cards/hunker_down.png";
    public static final String TAUNT = IMG_PATH + "cards/taunt.png";
    public static final String DESTRUCTIVE_FINISH = IMG_PATH + "cards/destructive_finish.png";
    public static final String THUNDER_STRUCK = IMG_PATH + "cards/thunder_struck.png";
    public static final String LIVING_ARMOR = IMG_PATH + "cards/living_armor.png";
    public static final String REVERSAL = IMG_PATH + "cards/reflection_ward.png";
    public static final String MERCURIAL = IMG_PATH + "cards/mercurial.png";
    public static final String BRUTE_FORCE = IMG_PATH + "cards/brute_force.png";
    public static final String INERTIA = IMG_PATH + "cards/inertia.png";
    public static final String COMBAT_TRAINING = IMG_PATH + "cards/combat_training.png";
    public static final String RELEASE_RESTRAINT = IMG_PATH + "cards/release_restraint.png";
    public static final String SHATTER = IMG_PATH + "cards/shatter.png";
    public static final String NATURAL_ENDURANCE = IMG_PATH + "cards/natural_endurance.png";
    public static final String BOLSTER = IMG_PATH + "cards/bolster.png";
    public static final String CANNIBALIZE = IMG_PATH + "cards/cannibalize.png";
    public static final String BIDE = IMG_PATH + "cards/bide.png";
    public static final String GIGA_IMPACT = IMG_PATH + "cards/giga_impact.png";
    public static final String FEAST = IMG_PATH + "cards/feast.png";
    public static final String APEX_PREDATOR = IMG_PATH + "cards/apex_predator.png";
    public static final String OVEREXERT = IMG_PATH + "cards/overexert.png";
    public static final String COLOSSUS = IMG_PATH + "cards/colossus.png";
    public static final String SPIKED_ARMOR = IMG_PATH + "cards/spiked_armor.png";
    public static final String INDOMITABLE_WILL = IMG_PATH + "cards/indomitable_will.png";
    public static final String MANGLE = IMG_PATH + "cards/mangle.png";
    public static final String CALL_TO_ARMS = IMG_PATH + "cards/call_to_arms.png";
    public static final String PERFECTED_BLOW = IMG_PATH + "cards/perfected_blow.png";
    public static final String OVERFLOWING_ARMOR = IMG_PATH + "cards/overflowing_armor.png";
    public static final String ATLAS = IMG_PATH + "cards/atlas.png";
    public static final String IMPENETRABLE = IMG_PATH + "cards/impenetrable.png";
    public static final String CHARGE = IMG_PATH + "cards/charge.png";
    public static final String BATTLE_SHOUT = IMG_PATH + "cards/battle_shout.png";
    public static final String BLITZ = IMG_PATH + "cards/blitz.png";
    public static final String IMPROVISATION = IMG_PATH + "cards/improvisation.png";
    public static final String SLUGFEST = IMG_PATH + "cards/slugfest.png";
    public static final String HEAVIER_BODY = IMG_PATH + "cards/heavier_body.png";
    public static final String PARRY = IMG_PATH + "cards/struggle.png";

    // power images

    public static final String BRUTE_FORCE_POWER = IMG_PATH + "powers/brute_force.png";
    public static final String INERTIA_POWER = IMG_PATH + "powers/inertia.png";
    public static final String COMBAT_TRAINING_POWER = IMG_PATH + "powers/combat_training.png";
    public static final String RELEASE_RESTRAINT_POWER = IMG_PATH + "powers/release_restraint.png";
    public static final String SHATTER_POWER = IMG_PATH + "powers/shatter.png";
    public static final String NATURAL_ENDURANCE_POWER = IMG_PATH + "powers/natural_endurance.png";
    public static final String BIDE_POWER = IMG_PATH + "powers/bide.png";
    public static final String GIGA_IMPACT_POWER = IMG_PATH + "powers/giga_impact.png";
    public static final String COLOSSUS_POWER = IMG_PATH + "powers/colossus.png";
    public static final String SPIKED_ARMOR_POWER = IMG_PATH + "powers/spiked_armor.png";
    public static final String INDOMITABLE_WILL_POWER = IMG_PATH + "powers/indomitable_will.png";
    public static final String MANGLE_POWER = IMG_PATH + "powers/mangle.png";
    public static final String OVERFLOWING_BLOCK_POWER = IMG_PATH + "powers/overflowing_block.png";
    public static final String OVERFLOWING_PLATE_POWER = IMG_PATH + "powers/overflowing_plate.png";
    public static final String ATLAS_POWER = IMG_PATH + "powers/atlas.png";
    public static final String IMPENETRABLE_POWER = IMG_PATH + "powers/impenetrable.png";
    public static final String HEAVIER_BODY_POWER = IMG_PATH + "powers/heavier_body.png";
    public static final String FOOLHARDY_POWER = IMG_PATH + "powers/foolhardy.png";

    // relic images

    public static final String HEAVY_BODY_RELIC = IMG_PATH + "relics/mechanicalCore.png";
    public static final String SNECKO_HEART_RELIC = IMG_PATH + "relics/sneckoHeart.png";
    public static final String RIGID_ARMOR_RELIC = IMG_PATH + "relics/rigidArmor.png";
    public static final String BRITTLE_ROCK_RELIC = IMG_PATH + "relics/brittleRock.png";
    public static final String KINETIC_ROCK_RELIC = IMG_PATH + "relics/kineticRock.png";

    
    // juggernaut assets
    private static final String JUGGERNAUT_BUTTON = IMG_PATH + "charSelect/juggernautButton.png";
    private static final String JUGGERNAUT_PORTRAIT = IMG_PATH + "charSelect/JuggernautPortraitBG.jpg";
    public static final String JUGGERNAUT_SHOULDER_1 = IMG_PATH + "char/juggernaut/shoulder.png";
    public static final String JUGGERNAUT_SHOULDER_2 = IMG_PATH + "char/juggernaut/shoulder2.png";
    public static final String JUGGERNAUT_CORPSE = IMG_PATH + "char/juggernaut/corpse.png";
    public static final String JUGGERNAUT_SKELETON_ATLAS = IMG_PATH + "char/juggernaut/skeleton.atlas";
    public static final String JUGGERNAUT_SKELETON_JSON = IMG_PATH + "char/juggernaut/skeleton.json";
    
    // badge
    public static final String BADGE_IMG = IMG_PATH + "JRelicBadge.png";
    
    // texture loaders
    public static Texture getFoolhardyPowerTexture() {
        return new Texture(FOOLHARDY_POWER);
    }

    public static Texture getHeavierBodyPowerTexture() {
        return new Texture(HEAVIER_BODY_POWER);
    }

    public static Texture getImpenetrablePowerTexture() {
        return new Texture(IMPENETRABLE_POWER);
    }

    public static Texture getAtlasPowerTexture() {
        return new Texture(ATLAS_POWER);
    }

    public static Texture getOverflowingBlockPowerTexture() {
        return new Texture(OVERFLOWING_BLOCK_POWER);
    }

    public static Texture getOverflowingPlatePowerTexture() {
        return new Texture(OVERFLOWING_PLATE_POWER);
    }

    public static Texture getManglePowerTexture() {
        return new Texture(MANGLE_POWER);
    }

    public static Texture getIndomitableWillPowerTexture() {
        return new Texture(INDOMITABLE_WILL_POWER);
    }

    public static Texture getSpikedArmorPowerTexture() { return new Texture(SPIKED_ARMOR_POWER); }

    public static Texture getColossusPowerTexture() {
        return new Texture(COLOSSUS_POWER);
    }

    public static Texture getGigaImpactPowerTexture() {
        return new Texture(GIGA_IMPACT_POWER);
    }

    public static Texture getBidePowerTexture() {
        return new Texture(BIDE_POWER);
    }

    public static Texture getNaturalEndurancePowerTexture() { return new Texture(NATURAL_ENDURANCE_POWER); }

    public static Texture getShatterPowerTexture() { return new Texture(SHATTER_POWER); }

    public static Texture getReleaseRestraintPowerTexture() {
        return new Texture(RELEASE_RESTRAINT_POWER);
    }

    public static Texture getCombatTrainingPowerTexture() {
        return new Texture(COMBAT_TRAINING_POWER);
    }

    public static Texture getInertiaPowerTexture() {
        return new Texture(INERTIA_POWER);
    }

    public static Texture getBruteForcePowerTexture() {
        return new Texture(BRUTE_FORCE_POWER);
    }

    public static Texture getHeavyBodyTexture() { return new Texture(HEAVY_BODY_RELIC); }

    public static Texture getSneckoHeartTexture() { return new Texture(SNECKO_HEART_RELIC); }

    public static Texture getRigidArmorTexture() { return new Texture(RIGID_ARMOR_RELIC); }

    public static Texture getBrittleRockTexture() { return new Texture(BRITTLE_ROCK_RELIC); }

    public static Texture getKineticRockTexture() { return new Texture(KINETIC_ROCK_RELIC); }

    /**
     * Makes a full path for a resource path
     * @param resource the resource, must *NOT* have a leading "/"
     * @return the full path
     */
//    public static final String String resource {
//    	return FRUITY_MOD_ASSETS_FOLDER + "/" + resource;
//    }
    
    public JuggernautMod() {
        BaseMod.subscribe(this);
        
        /*
         * Note that for now when installing JuggernautMod, in the `mods/` folder another folder named
         * the value of FRUITY_MOD_ASSETS_FOLDER must be created into which all the contents of the
         * `images/` folder must be relocated
         */
        logger.info("creating the color " + AbstractCardEnum.COPPER.toString());
        BaseMod.addColor(
                AbstractCardEnum.COPPER
                , COPPER
                , ATTACK_COPPER
                , SKILL_COPPER
                , POWER_COPPER
                , ENERGY_ORB_COPPER
                , ATTACK_COPPER_PORTRAIT
                , SKILL_COPPER_PORTRAIT
                , POWER_COPPER_PORTRAIT
                , ENERGY_ORB_COPPER_PORTRAIT
                , ENERGY_ORB_IN_DESCRIPTION
        );
    }

    public static void initialize() {
    	logger.info("========================= FRUITYMOD INIT =========================");
		
		@SuppressWarnings("unused")
        JuggernautMod Jug = new JuggernautMod();
		
		logger.info("================================================================");
    }

    @Override
    public void receivePostInitialize() {
        // Mod badge
        Texture badgeTexture = new Texture(BADGE_IMG);
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, null);
        
        Settings.isDailyRun = false;
        Settings.isTrial = false;
        Settings.isDemo = false;
    }

	@Override
	public void receiveEditCharacters() {
		logger.info("begin editting characters");
		
		logger.info("add " + TheJuggernautEnum.THE_JUGGERNAUT);
		BaseMod.addCharacter(new TheJuggernaut(TheJuggernaut.NAME,TheJuggernautEnum.THE_JUGGERNAUT),
				JUGGERNAUT_BUTTON, JUGGERNAUT_PORTRAIT,
				TheJuggernautEnum.THE_JUGGERNAUT);
		
		logger.info("done editting characters");
	}

	
	@Override
	public void receiveEditRelics() {
		logger.info("begin editting relics");
        
        // Add relics
		BaseMod.addRelicToCustomPool(new HeavyBody(), AbstractCardEnum.COPPER);
        BaseMod.addRelicToCustomPool(new SneckoHeart(), AbstractCardEnum.COPPER);
        BaseMod.addRelicToCustomPool(new RigidArmor(), AbstractCardEnum.COPPER);
        BaseMod.addRelicToCustomPool(new BrittleRock(), AbstractCardEnum.COPPER);
        BaseMod.addRelicToCustomPool(new KineticRock(), AbstractCardEnum.COPPER);
        
        logger.info("done editting relics");
	}

	@Override
	public void receiveEditCards() {
		logger.info("begin editting cards");
		
		logger.info("add cards for " + TheJuggernautEnum.THE_JUGGERNAUT);
		
		BaseMod.addCard(new Strike_Purple());
		BaseMod.addCard(new Defend_Purple());

        BaseMod.addCard(new Parry());
        BaseMod.addCard(new HeavierBody());
        BaseMod.addCard(new Slugfest());
        BaseMod.addCard(new Improvisation());
        BaseMod.addCard(new Blitz());
        BaseMod.addCard(new BattleShout());
        BaseMod.addCard(new Charge());
        BaseMod.addCard(new Impenetrable());
        BaseMod.addCard(new Atlas());
        BaseMod.addCard(new OverflowingArmor());
        BaseMod.addCard(new PerfectedBlow());
        BaseMod.addCard(new CallToArms());
        BaseMod.addCard(new Mangle());
        BaseMod.addCard(new IndomitableWill());
        BaseMod.addCard(new SpikedArmor());
        BaseMod.addCard(new Colossus());
        BaseMod.addCard(new Overexert());
        BaseMod.addCard(new ApexPredator());
        BaseMod.addCard(new Feast());
        BaseMod.addCard(new GigaImpact());
        BaseMod.addCard(new Bide());
        BaseMod.addCard(new Cannibalize());
        BaseMod.addCard(new NaturalEndurance());
        BaseMod.addCard(new Shatter());
        BaseMod.addCard(new ReleaseRestraint());
        BaseMod.addCard(new CombatTraining());
        BaseMod.addCard(new Inertia());
        BaseMod.addCard(new BruteForce());
        BaseMod.addCard(new Mercurial());
        //BaseMod.addCard(new Reversal());
        BaseMod.addCard(new LivingArmor());
        BaseMod.addCard(new ThunderStruck());
        BaseMod.addCard(new DestructiveFinish());
        BaseMod.addCard(new Taunt());
        BaseMod.addCard(new HunkerDown());
        BaseMod.addCard(new InhumanRecovery());
        BaseMod.addCard(new Grapple());
        BaseMod.addCard(new HeavyArmor());
        BaseMod.addCard(new Bolster());
        BaseMod.addCard(new Breakthrough());
        BaseMod.addCard(new SlowAndSteady());
        BaseMod.addCard(new HeavyAssault());
        BaseMod.addCard(new UnstoppableForce());
        BaseMod.addCard(new LashOut());
        BaseMod.addCard(new SteelForce());
        BaseMod.addCard(new HeavyCrash());
        BaseMod.addCard(new Overpower());
        BaseMod.addCard(new Vengeance());
        BaseMod.addCard(new ConvertFlesh());
        BaseMod.addCard(new Unshakable());
        BaseMod.addCard(new FocusedPower());
        BaseMod.addCard(new Bulwark());
        BaseMod.addCard(new Accelerate());
        BaseMod.addCard(new Galvanize());
        BaseMod.addCard(new Endure());
        BaseMod.addCard(new Pursuit());
        BaseMod.addCard(new Bulldoze());
        BaseMod.addCard(new Feint());
        BaseMod.addCard(new CascadingSteel());
        BaseMod.addCard(new Smother());
        BaseMod.addCard(new Hysteria());
        BaseMod.addCard(new ChallengingRoar());
        BaseMod.addCard(new SimpleMinded());
        BaseMod.addCard(new Lunge());
        BaseMod.addCard(new RelentlessBlows());
        BaseMod.addCard(new FlyingPress());
        BaseMod.addCard(new Frenzy());
        BaseMod.addCard(new ShoulderBlow());
        BaseMod.addCard(new Pulverize());
        BaseMod.addCard(new HammerArm());
        BaseMod.addCard(new SkullBash());
        //BaseMod.addCard(new Struggle());
        BaseMod.addCard(new Fury());
        BaseMod.addCard(new OnGuard());
        BaseMod.addCard(new Earthquake());
		
		// make sure everything is always unlocked
		UnlockTracker.unlockCard(Strike_Purple.ID);
		UnlockTracker.unlockCard(Defend_Purple.ID);
		
		/*UnlockTracker.unlockCard("Starburst");
		UnlockTracker.unlockCard("Irradiate");
		UnlockTracker.unlockCard("AstralHaze");
		UnlockTracker.unlockCard("Brainstorm");
		UnlockTracker.unlockCard("DarkMatter");
		UnlockTracker.unlockCard("ArcaneArmor");
		UnlockTracker.unlockCard("Entropy");
		UnlockTracker.unlockCard("EssenceDart");		
		UnlockTracker.unlockCard("Flicker");
		UnlockTracker.unlockCard("PlasmaWave");		
		UnlockTracker.unlockCard("PulseBarrier");
		UnlockTracker.unlockCard("Nebula");
		UnlockTracker.unlockCard("EtherBlast");
		UnlockTracker.unlockCard("Flare");		
		UnlockTracker.unlockCard("NullStorm");
		UnlockTracker.unlockCard("VoidRay");
		UnlockTracker.unlockCard("DisruptionField");
		UnlockTracker.unlockCard("UnstableOrb");
		UnlockTracker.unlockCard("Hypothesis");
		UnlockTracker.unlockCard("Comet");
		UnlockTracker.unlockCard("ForceRipple");
		UnlockTracker.unlockCard("PhaseCoil");
		UnlockTracker.unlockCard("Overload");
		UnlockTracker.unlockCard("Syzygy");
		UnlockTracker.unlockCard("SiphonPower");
		UnlockTracker.unlockCard("Shimmer");
		UnlockTracker.unlockCard("ThoughtRaze");
		UnlockTracker.unlockCard("Retrograde");
		UnlockTracker.unlockCard("Singularity");
		UnlockTracker.unlockCard("Umbra");
		UnlockTracker.unlockCard("Genesis");
		UnlockTracker.unlockCard("PrismaticSphere");
		UnlockTracker.unlockCard("Flux");
		UnlockTracker.unlockCard("Channel");
		UnlockTracker.unlockCard("Implosion");
		UnlockTracker.unlockCard("ChaosForm");
		UnlockTracker.unlockCard("Vacuum");
		UnlockTracker.unlockCard("DimensionDoor");
		UnlockTracker.unlockCard("Wormhole");
		UnlockTracker.unlockCard("Eureka");
		UnlockTracker.unlockCard("Eclipse");
		UnlockTracker.unlockCard("Echo");
		UnlockTracker.unlockCard("EventHorizon");
		UnlockTracker.unlockCard("Zenith");
		UnlockTracker.unlockCard("ReflectionWard");
		UnlockTracker.unlockCard("Creativity");
		UnlockTracker.unlockCard("Transference");
		UnlockTracker.unlockCard("Surge");
		UnlockTracker.unlockCard("StrokeOfGenius");
		UnlockTracker.unlockCard("SiphonSpeed");
		UnlockTracker.unlockCard("Convergence");
		UnlockTracker.unlockCard("GravityWell");
		UnlockTracker.unlockCard("Coalescence");
		UnlockTracker.unlockCard("PeriaptOfCelerity");
		UnlockTracker.unlockCard("PeriaptOfPotency");
		UnlockTracker.unlockCard("MeteorShower");
		UnlockTracker.unlockCard("PowerOverwhelming");
		UnlockTracker.unlockCard("MindOverMatter");
		UnlockTracker.unlockCard("Disperse");
		UnlockTracker.unlockCard("Magnetize");
		UnlockTracker.unlockCard("Illuminate");
		UnlockTracker.unlockCard("Flow");
		UnlockTracker.unlockCard("Equinox");
		UnlockTracker.unlockCard("Corona");
		UnlockTracker.unlockCard("Archives");
		UnlockTracker.unlockCard("MagicMissile");
		UnlockTracker.unlockCard("Enigma");
		UnlockTracker.unlockCard("Feedback");
		UnlockTracker.unlockCard("Brilliance");
		UnlockTracker.unlockCard("Anomaly");
		UnlockTracker.unlockCard("Nova");
		UnlockTracker.unlockCard("Vortex");
		UnlockTracker.unlockCard("Nexus");
		*/
		
		logger.info("done editting cards");
	}

	@Override
	public void receiveEditStrings() {
		logger.info("begin editting strings");
		
        // RelicStrings
        String relicStrings = Gdx.files.internal(RELIC_STRINGS).readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
        // CardStrings
        String cardStrings = Gdx.files.internal(CARD_STRINGS).readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
		
		logger.info("done editting strings");
	}

	@Override
	public void receiveSetUnlocks() {
//		UnlockTracker.addCard("Flicker");
//		UnlockTracker.addCard("Transference");
//		UnlockTracker.addCard("ForceRipple");
//		// juggernaut unlock 1
//		BaseMod.addUnlockBundle(new CustomUnlockBundle(
//				"Flicker", "Transference", "ForceRipple"
//				), TheJuggernautEnum.THE_JUGGERNAUT, 1);
//		
//		// juggernaut unlock 2
//		BaseMod.addUnlockBundle(new CustomUnlockBundle(
//				"Channel", "Shimmer", "ThoughtRaze"
//				), TheJuggernautEnum.THE_JUGGERNAUT, 2);
//		UnlockTracker.addCard("Channel");
//		UnlockTracker.addCard("Shimmer");
//		UnlockTracker.addCard("ThoughtRaze");
//		
//		// juggernaut unlock 3 (Vacuum tmp in place of Feedback)
//		BaseMod.addUnlockBundle(new CustomUnlockBundle(
//				"Convergence", "Hypothesis", "Nexus"
//				), TheJuggernautEnum.THE_JUGGERNAUT, 3);
//		UnlockTracker.addCard("Convergence");
//		UnlockTracker.addCard("Hypothesis");
//		UnlockTracker.addCard("Nexus");
	}
	

	@Override
	public void receiveEditKeywords() {
        logger.info("setting up custom keywords");
        BaseMod.addKeyword(new String[] {"plated-armor", "Plated-Armor"}, "Gain Block equal to your Plated-Armor at the end of your turn. Taking damage from an attack reduces Plated-Armor by 1.");
        BaseMod.addKeyword(new String[] {"overflow", "Overflow"}, "When a card with Overflow is in your hand at the end of the turn, activate an effect. This effect has a limited amount of uses.");
        BaseMod.addKeyword(new String[] {"draw-reduction", "Draw-Reduction"}, "Draw 1 less card at the beginning of your turn.");
        BaseMod.addKeyword(new String[] {"confusion", "Confusion"}, "The costs of your cards are randomized when they are drawn.");
        BaseMod.addKeyword(new String[] {"slow", "Slow"}, "Creatures with Slow take 10% more damage for each card played this turn.");
        BaseMod.addKeyword(new String[] {"encumbered", "Encumbered"}, "Only happens while you have less than 0 Dexterity.");
        BaseMod.addKeyword(new String[] {"instinct", "Instinct"}, "Has an improved effect on the first turn of combat.");
	}
	
	//
	// Enigma hooks and functionality 	
	//
	
	// used by JuggernautMod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CanUsedDazed
	public static boolean hasRelicCustom(String relicID, AbstractCard card) {
		System.out.println("I was checked!");
		// if it's checking for relicID.equals("Medical Kit") then we know we're in the block where
		// we are saying if we can use a status card so also check if we have enigma and the card is Dazed
		if (relicID.equals("Medical Kit") && AbstractDungeon.player.hasPower("EnigmaPower") && card.cardID.equals("Dazed")) {
			return true;
		} else {
			// otherwise leave normal behavior intact
			return AbstractDungeon.player.hasRelic(relicID);
		}
	}


    public static boolean hasRelicCustomI(String relicID) {
        System.out.println("I was checked!");
        // if it's checking for relicID.equals("Runic Pyramid") then we know we're in the block where
        // we are saying if we can use a status card so also check if we have enigma and the card is Dazed
        if (AbstractDungeon.player.hasPower("Inertia") ) {
            return true;
        } else {
            // otherwise leave normal behavior intact
            return AbstractDungeon.player.hasRelic(relicID);
        }
    }


    // used by fruitmod.patches.com.megacrit.cardcrawl.cards.status.Dazed.UseDazed
	public static void maybeUseDazed(Dazed dazed) {
		System.out.println("maybe use dazed");
		if (!AbstractDungeon.player.hasPower("EnigmaPower")) {
			System.out.println("do use dazed");
			AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.utility.UseCardAction(dazed));
		} else {
			System.out.println("don't use dazed");
		}
	}
	
	@Override
	public void receiveCardUsed(AbstractCard c) {
		AbstractPlayer p = AbstractDungeon.player;
		if (p.hasPower("EnigmaPower") && c.cardID.equals("Dazed")) {
			AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, c.block));
			AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, 
					c.multiDamage,
					DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE, true));
			c.exhaustOnUseOnce = false;
		}
	}

	//
	// Relic code
	// (yes we're doing the exact same things the devs did where relic code
	// isn't in the actual relics - oh well)
	//
	
	private boolean moreThanXStacks(AbstractPlayer player, String powerID, int stacksWanted) {
		if (player != null && player.hasPower(powerID) && player.getPower(powerID).amount >= stacksWanted) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void receivePostBattle(AbstractRoom arg0) {
	}

	@Override
	public void receivePostDungeonInitialize() {
	}

	@Override
	public void receivePostDraw(AbstractCard c) {
	}

    @Override
    public void receivePowersModified() {
    }

    @Override
    public void receivePostExhaust(AbstractCard card) {
    }
}
