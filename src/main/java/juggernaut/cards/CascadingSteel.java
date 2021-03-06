package juggernaut.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import juggernaut.JuggernautMod;
import juggernaut.actions.common.ModifyMagicNumberAction;
import juggernaut.actions.unique.ArcanosphereAction;
import juggernaut.patches.AbstractCardEnum;
import juggernaut.patches.OverflowCard;

public class CascadingSteel extends OverflowCard{
    public static final String ID = "Cascading Steel";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int ATTACK_DMG = 16;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int ARTIFACT_AMT = 1;
    private static final int OVERFLOW_AMT = 1;

    public CascadingSteel() {
        super(ID, NAME, JuggernautMod.CASCADING_STEEL, COST, DESCRIPTION,
                AbstractCard.CardType.ATTACK, AbstractCardEnum.COPPER,
                AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY);
        this.isMultiDamage = true;
        this.damage=this.baseDamage = ATTACK_DMG;
        this.magicNumber = this.baseMagicNumber = OVERFLOW_AMT;
        this.isOverflow = true;
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        if (this.magicNumber > 0) {
            AbstractDungeon.actionManager.addToBottom(new ModifyMagicNumberAction(this, -1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ArtifactPower(AbstractDungeon.player, ARTIFACT_AMT), ARTIFACT_AMT));
            if (this.magicNumber == 1) {
                this.isOverflow = false;
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    @Override
    public AbstractCard makeCopy() {
        return new CascadingSteel();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}
