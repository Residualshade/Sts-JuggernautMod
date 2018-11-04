package juggernaut.characters;

import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import juggernaut.JuggernautMod;
import juggernaut.actions.unique.HeavyBodyAction;
import juggernaut.cards.Defend_Purple;
import juggernaut.cards.OnGuard;
import juggernaut.cards.Overpower;
import juggernaut.cards.Strike_Purple;
import juggernaut.patches.AbstractCardEnum;
import juggernaut.patches.TheJuggernautEnum;
import juggernaut.powers.PlateBalancePower;
import juggernaut.relics.HeavyBody;

import java.util.ArrayList;

public class TheJuggernaut extends CustomPlayer {
	public static final int ENERGY_PER_TURN = 2;
    public static final String NAME = "The Juggernaut";

    private static final String CHARACTER_PATH = JuggernautMod.IMG_PATH + "char/juggernaut/";
    private static final String ORB_PATH = CHARACTER_PATH + "orb/";
    private static final String JUGGERNAUT_LAYER1_PATH = ORB_PATH + "juggernaut_layer1.png";
	private static final String JUGGERNAUT_LAYER2_PATH = ORB_PATH + "juggernaut_layer2.png";
	private static final String JUGGERNAUT_LAYER3_PATH = ORB_PATH + "juggernaut_layer3.png";
	private static final String JUGGERNAUT_LAYER4_PATH = ORB_PATH + "juggernaut_layer4.png";
	private static final String JUGGERNAUT_LAYER5_PATH = ORB_PATH + "juggernaut_layer5.png";
	private static final String JUGGERNAUT_LAYER6_PATH = ORB_PATH + "juggernaut_layer6.png";
	private static final String JUGGERNAUT_LAYER1D_PATH = ORB_PATH + "juggernaut_layer1d.png";
	private static final String JUGGERNAUT_LAYER2D_PATH = ORB_PATH + "juggernaut_layer2d.png";
	private static final String JUGGERNAUT_LAYER3D_PATH = ORB_PATH + "juggernaut_layer3d.png";
	private static final String JUGGERNAUT_LAYER4D_PATH = ORB_PATH + "juggernaut_layer4d.png";
	private static final String JUGGERNAUT_LAYER5D_PATH = ORB_PATH + "juggernaut_layer5d.png";
	private static final String JUGGERNAUT_VFX_PATH = ORB_PATH + "vfx_juggernaut.png";
	private static final String JUGGERNAUT2_PATH = CHARACTER_PATH + "juggernaut2.g3dj";

    public static int turnTracker = 0;
	public static final float[] orbRotations = {
			0.0F,
			0.0F,
			0.0F,
			0.0F,
			0.0F,
			0.0F,
			0.0F,
			0.0F,
			0.0F,
			0.0F,
	};

	public static final String[] orbTextures = {
			JUGGERNAUT_LAYER1_PATH,
			JUGGERNAUT_LAYER2_PATH,
			JUGGERNAUT_LAYER3_PATH,
			JUGGERNAUT_LAYER4_PATH,
			JUGGERNAUT_LAYER5_PATH,
			JUGGERNAUT_LAYER6_PATH,
			JUGGERNAUT_LAYER1D_PATH,
			JUGGERNAUT_LAYER2D_PATH,
			JUGGERNAUT_LAYER3D_PATH,
			JUGGERNAUT_LAYER4D_PATH,
			JUGGERNAUT_LAYER5D_PATH,
	};
	
	public TheJuggernaut(String name, PlayerClass setClass) {
		super(name, setClass, orbTextures, JUGGERNAUT_VFX_PATH, JUGGERNAUT2_PATH, "sls_md_juggernaut|idle");
		
		initializeClass(null, JuggernautMod.JUGGERNAUT_SHOULDER_2,
				JuggernautMod.JUGGERNAUT_SHOULDER_1,
				JuggernautMod.JUGGERNAUT_CORPSE,
				getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));
	}

	@Override
	public void applyEndOfTurnTriggers() {
		for (AbstractPower p : this.powers) {
			p.atEndOfTurn(true);
		}
		turnTracker++;
	}

	@Override
	public void onVictory() {
		if (!this.isDying) {
			for (AbstractRelic r : this.relics) {
				r.onVictory();
			}
		}
		turnTracker = 0;
		this.damagedThisCombat = 0;
	}

	@Override
	public void applyStartOfTurnPostDrawPowers() {
		for (AbstractPower p : this.powers) {
			p.atStartOfTurnPostDraw();
		}
		if (AbstractDungeon.player.hasRelic(HeavyBody.ID))
		AbstractDungeon.actionManager.addToBottom(new HeavyBodyAction(AbstractDungeon.player, 1));
		if (AbstractDungeon.player.hasPower(PlateBalancePower.POWER_ID) != true){
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlateBalancePower()));
		}
	}

	public void applyStartOfCombatLogic() {
		for (AbstractRelic r : this.relics) {
			if (r == null) continue;
			r.atBattleStart();
		}
		if (AbstractDungeon.player.hasPower(PlateBalancePower.POWER_ID) != true){
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlateBalancePower()));
		}
	}

	@Override
	public ArrayList<String> getStartingDeck() {
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add(Strike_Purple.ID);
		retVal.add(Strike_Purple.ID);
		retVal.add(Strike_Purple.ID);
		retVal.add(Strike_Purple.ID);
		retVal.add(Defend_Purple.ID);
		retVal.add(Defend_Purple.ID);
		retVal.add(Defend_Purple.ID);
		retVal.add(Defend_Purple.ID);
		retVal.add(OnGuard.ID);
		retVal.add(Overpower.ID);
		return retVal;
	}

	@Override
	public ArrayList<String> getStartingRelics() {
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add(HeavyBody.ID);
		UnlockTracker.markRelicAsSeen(HeavyBody.ID);
		return retVal;
	}

	@Override
	public CharSelectInfo getLoadout() {
		return new CharSelectInfo(NAME, "A fearless behemoth. He has weathered countless blows, and dealt countless more.",
				90, 90, 0, 99, 5,
			this, getStartingRelics(), getStartingDeck(), false);
	}

    @Override
    public String getTitle(PlayerClass playerClass) {
        return NAME;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return AbstractCardEnum.COPPER;
    }

    @Override
    public Color getCardRenderColor() {
        return JuggernautMod.COPPER;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Overpower();
    }

    @Override
    public Color getCardTrailColor() {
        return JuggernautMod.COPPER;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_HEAVY", MathUtils.random(-0.2f, 0.2f));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, true);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_HEAVY";
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAME;
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheJuggernaut(NAME, TheJuggernautEnum.THE_JUGGERNAUT);
    }

    @Override
    public String getSpireHeartText() {
        return "NL You ready your Weapon...";
    }

    @Override
    public Color getSlashAttackColor() {
        return JuggernautMod.COPPER;
    }

	@Override
	public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
		return new AbstractGameAction.AttackEffect[]{
				AbstractGameAction.AttackEffect.SLASH_DIAGONAL
				, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL
				, AbstractGameAction.AttackEffect.SLASH_VERTICAL
				, AbstractGameAction.AttackEffect.SLASH_HEAVY
		};
	}

    //TODO: Character Specific Dialog
    @Override
    public String getVampireText() {
        return "Navigating an unlit street, you come across several hooded figures in the midst of some dark ritual. As you approach, they turn to you in eerie unison. The tallest among them bares fanged teeth and extends a long, pale hand towards you. NL ~\"Join~ ~us,~ ~oh Mighty Warrior,~ ~and~ ~feel~ ~the~ ~warmth~ ~of~ ~the~ ~Spire.\"~";
    }
}
