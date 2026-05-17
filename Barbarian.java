public class Barbarian extends Character {
    // Had to change this, Math.log only takes one variable. Must be log 10.
    private double missingHPBonus = 1.1 * Math.log (this.GetCurrHealth() / this.GetCalculatedStats()[this.HLTPOS] );

    public Barbarian(Character character, int team) {
        super();
        this.SetName("Barbarian");
        this.SetFullName(character.GetFullName());
        this.SetTeam(team);
        this.spdMod = 2;
        this.intlMod = -1;
        this.atkMod = 8;
        this.sprMod = 1;
        this.hltMod = -2;
        this.sppMod = 0;
        this.spd = character.spd + this.spdMod ;
        if ( this.spd + this.spdMod < 0 ) {
            this.spd = 1;
        }
        this.intl = character.intl + this.intlMod;
        if ( this.intl + this.intlMod < 0 ) {
            this.intl = 1;
        }
        this.atk = character.atk + this.atkMod;
        if ( this.atk + this.atkMod < 0 ) {
            this.atk = 1;
        }
        this.spr = character.spr + this.sprMod;
        if ( this.spr + this.sprMod < 0 ) {
            this.spr = 1;
        }
        this.hlt = character.hlt + this.hltMod;
        if ( this.hlt + this.hltMod < 0 ) {
            this.hlt = 1;
        }
        this.spp = character.spp + this.sppMod;
        if ( this.spp + this.sppMod < 0 ) {
            this.spp = 1;
        }
        ScaleStats();
    }
    public int GetAbility1Range() {
        return 1;
    }
    public int GetAbility2Range() {
        return 1;
    }
    public int GetAbility3Range() {
        return 9;
    }
    public String getName() {
        return "Barbarian";
    }
    // Picks up character, throws them behind
    public boolean Ability1(ActionContext context) {
        if ( !CheckConditions(2, 1, context.GetTarget()) ) {
            return false;
        }
        applyPassive();
            int[] targetPos = context.GetTarget().GetPosition();
            int[] myPos = this.GetPosition();

            // Performs Ability1 vertically
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
            // Performs Ability1 horizontally  
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
            // Performs Ability1 diagonally
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
            this.SetCurrMagic ( this.GetCurrMagic() - 2);
            return false;
        }
        //System.out.println( Ability1Hint());
    // Strong attack, meant to hit multiple times so that block/parry is calculated for each hit
    // and its unlikely for the whole thing to be blocked
    public boolean Ability2 (ActionContext context) {
        if ( !CheckConditions(1, 1, context.GetTarget()) ){
            return false;
        }
        applyPassive();
            ScaleStats();
            if ( context.GetTarget().GetIsDivineShielded()) {
                context.GetTarget().SetCurrHealth(context.GetTarget().GetCalculatedStats()[this.MAXHEALTHPOS] - this.attack * 2);
            }
            else context.GetTarget().SetCurrHealth(context.GetTarget().GetCurrHealth() - this.attack * 4);
            this.SetCurrMagic( this.GetCurrMagic() - 1);
            ScaleStats();
            return true;
        }

    public boolean Ability3 () {
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