public class Barbarian extends Character {
    // Had to change this, Math.log only takes one variable. Must be log 10.
    private double missingHPBonus = 1.1 * Math.log (this.GetCurrHealth() / this.GetCalculatedStats()[this.HLTPOS] );

    public Barbarian(Character character) {
        super();
        this.spdMod = 2;
        this.intlMod = -1;
        this.atkMod = 8;
        this.sprMod = 1;
        this.hltMod = -2;
        this.sppMod = 0;
        this.spd = character.spd + this.spdMod;
        this.intl = character.intl + this.intlMod;
        this.atk = character.atk + this.atkMod;
        this.spr = character.spr + this.sprMod;
        this.hlt = character.hlt + this.hltMod;
        this.spp = character.spp + this.sppMod;

    }
    // Picks up character, throws them behind
    public boolean Special(ActionContext context) {
        if ( !CheckConditions(2) ) {
            return false;
        }
        applyPassive();
        if (CheckRange(1, context.GetTarget())) {
            int[] targetPos = context.GetTarget().GetPosition();
            int[] myPos = this.GetPosition();

            // Performs Special vertically
            // Checks that the barbarian is not at the top or bottom of the grid, or he'll throw the target out of the grid
            if (this.GetPosition()[1] == 0 || this.GetPosition()[1] == context.GetGrid().length - 1) {
            return false;
            }
            else {
                // target is above barbarian
                if (targetPos[0] == myPos[0] && targetPos[1] == myPos[1] + 1) {
                context.GetGrid()[myPos[0]][myPos[1] - 2].SetEntity(context.GetTarget());
                context.GetGrid()[myPos[0]][myPos[1]].SetEntity(null);
                context.GetTarget().position[0] = myPos[0];
                context.GetTarget().position[1] = myPos[1] - 2;
                return true;
                }
                // target is below barbarian
                else if (targetPos[0] == myPos[0] && targetPos[1] == myPos[1] - 1) {
                context.GetGrid()[myPos[0]][myPos[1] + 2].SetEntity(context.GetTarget());
                context.GetGrid()[myPos[0]][myPos[1]].SetEntity(null);
                context.GetTarget().position[0] = myPos[0];
                context.GetTarget().position[1] = myPos[1] + 2;
                return true;
                }
            }
            // Performs Special horizontally  
            // Checks that the barbarian is not at the extreme left or right of the grid or he'll throw the target out of the grid
            if ( this.GetPosition()[0] == 0 || this.GetPosition()[0] == context.GetGrid()[0].length - 1) {
                return false;
            }
            else {
                // target is to the left of barbarian
                if (targetPos[0] == myPos[0] - 1 && targetPos[1] == myPos[1]) {
                    context.GetGrid()[myPos[0] + 2][myPos[1]].SetEntity(context.GetTarget());
                    context.GetGrid()[myPos[0]][myPos[1]].SetEntity(null);
                    context.GetTarget().position[0] = myPos[0] + 2;
                    context.GetTarget().position[1] = myPos[1];
                    return true;
                }
                // target is to the right of barbarian
                else if (targetPos[0] == myPos[0] + 1 && targetPos[1] == myPos[1]) {
                    context.GetGrid()[myPos[0] - 2][myPos[1]].SetEntity(context.GetTarget());
                    context.GetGrid()[myPos[0]][myPos[1]].SetEntity(null);
                    context.GetTarget().position[0] = myPos[0] - 2;
                    context.GetTarget().position[1] = myPos[1];
                    return true;
                }
            }
            // Performs Special diagonally
            // Checks both vertical and horizontal conditions
            if ( this.GetPosition()[0] == 0 || this.GetPosition()[0] == context.GetGrid()[0].length - 1 
                 || this.GetPosition()[1] == 0 || this.GetPosition()[1] == context.GetGrid().length - 1 ) {
                    return false;
                 }
                 else {
                    if (targetPos[0] == myPos[0] - 1 && targetPos[1] == myPos[1] - 1) {
                        context.GetGrid()[targetPos[0] + 2][targetPos[1] + 2].SetEntity(context.GetTarget());
                        context.GetGrid()[targetPos[0]][targetPos[1]].SetEntity(null);
                        context.GetTarget().position[0] = targetPos[0] + 2;
                        context.GetTarget().position[1] = targetPos[1] + 2;
                        return true;
                    }
                    else if (targetPos[0] == myPos[0] - 1 && targetPos[1] == myPos[1] + 1) {
                        context.GetGrid()[targetPos[0] + 2][targetPos[1] - 2].SetEntity(context.GetTarget());
                        context.GetGrid()[targetPos[0]][targetPos[1]].SetEntity(null);
                        context.GetTarget().position[0] = targetPos[0] + 2;
                        context.GetTarget().position[1] = targetPos[1] - 2;
                        return true;
                    }
                    else if (targetPos[0] == myPos[0] + 1 && targetPos[1] == myPos[1] - 1) {
                        context.GetGrid()[targetPos[0] - 2][targetPos[1] + 2].SetEntity(context.GetTarget());
                        context.GetGrid()[targetPos[0]][targetPos[1]].SetEntity(null);
                        context.GetTarget().position[0] = targetPos[0] - 2;
                        context.GetTarget().position[1] = targetPos[1] + 2;
                        return true;
                    }
                    else if (targetPos[0] == myPos[0] + 1 && targetPos[1] == myPos[1] + 1) {
                        context.GetGrid()[targetPos[0] - 2][targetPos[1] - 2].SetEntity(context.GetTarget());
                        context.GetGrid()[targetPos[0]][targetPos[1]].SetEntity(null);
                        context.GetTarget().position[0] = targetPos[0] - 2;
                        context.GetTarget().position[1] = targetPos[1] - 2;
                        return true;
                    }
                 }
        }
        this.SetCurrMagic ( this.GetCurrMagic() - 2);
        //System.out.println( SpecialHint());
        return false;
    }
    // Strong attack, meant to hit multiple times so that block/parry is calculated for each hit
    // and its unlikely for the whole thing to be blocked
    public boolean Ability1 (ActionContext context) {
        if ( !CheckConditions(1) ){
            return false;
        }
        applyPassive();
        if ( CheckRange(1, context.GetTarget()) ) {
            ScaleStats();
            context.GetTarget().SetCurrHealth(context.GetTarget().GetCurrHealth() - this.attack * 4);
            this.SetCurrMagic( this.GetCurrMagic() - 1);
            ScaleStats();
            return true;
        }
        return false;
    }

    public boolean Ability2 (ActionContext context) {
        if ( !CheckConditions( 1) ) {
            return false;
        }
        applyPassive();
        if ( this.GetCurrHealth() > 0.2 * this.GetCalculatedStats()[HLTPOS] ) {
            this.SetCurrHealth(this.GetCurrHealth() - 0.2 * this.GetCalculatedStats()[HLTPOS]);
            this.SetCalculatedStats(ATTACKPOS, this.GetCalculatedStats()[ATTACKPOS] * 1.15);
            this.SetCurrMagic( this.GetCurrMagic() - 2);
            ScaleStats();
            return true;
        }
        return false;
    }
    private void applyPassive() {
        this.attack *= missingHPBonus;
    }
}