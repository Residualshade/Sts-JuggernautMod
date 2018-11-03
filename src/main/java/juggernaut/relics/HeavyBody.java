package juggernaut.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import juggernaut.JuggernautMod;
import juggernaut.actions.unique.HeavyBodyAction;

public class HeavyBody extends CustomRelic{
    public static final String ID = "Heavy Body";
    private static final int CARDS_TO_CHOOSE = 1;

    public HeavyBody() {
        super(ID, JuggernautMod.getHeavyBodyTexture(),
                RelicTier.STARTER, LandingSound.HEAVY);
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(
                new HeavyBodyAction(AbstractDungeon.player,
                        CARDS_TO_CHOOSE));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new HeavyBody();
    }
}
