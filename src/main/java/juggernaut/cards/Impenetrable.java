package juggernaut.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import juggernaut.JuggernautMod;
import juggernaut.actions.common.ModifyMagicNumberAction;
import juggernaut.patches.AbstractCardEnum;
import juggernaut.patches.OverflowCard;
import juggernaut.powers.ImpenetrablePower;

public class Impenetrable extends OverflowCard {
    public static final String ID = "Impenetrable";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int PLATE_AMT = 2;
    private static final int UPGRADED_PLATE = 4;
    private static final int OVERFLOW_AMT = 3;
    private static final int POWER_AMT = 1;
    private static final int POWER_AMT_UP = 2;

    public Impenetrable() {
        super(ID, NAME, JuggernautMod.IMPENETRABLE, COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                AbstractCardEnum.COPPER, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = OVERFLOW_AMT;
        this.isOverflow = true;
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        int powerAmt;
        if (this.upgraded) {
            powerAmt = POWER_AMT_UP;
        } else {
            powerAmt = POWER_AMT;
        }
        if (this.magicNumber > 0) {
            AbstractDungeon.actionManager.addToBottom(new ModifyMagicNumberAction(this, -1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ImpenetrablePower(AbstractDungeon.player, powerAmt), powerAmt));
            if (this.magicNumber == 1) {
                this.isOverflow = false;
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, PLATE_AMT), PLATE_AMT));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Impenetrable();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
        }
    }
}