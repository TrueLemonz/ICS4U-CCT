public class Paladin extends Character {

    public Paladin(Character character, int team) {
        super();
        ApplyStats();
        ScaleStats();
        this.SetName("Paladin");
        this.SetFullName(character.GetFullName());
        this.SetTeam(team);
        this.spdMod = 2;
        this.intlMod = -1;
        this.atkMod = 8;
        this.mgcMod = 1;
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
        this.mgc = character.mgc + this.mgcMod;
        if ( this.mgc + this.mgcMod < 0 ) {
            this.mgc = 1;
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
    public boolean CheckAbility1Possible(GameSystem gs) { return false; }
    public boolean CheckAbility2Possible(GameSystem gs) { return false; }
    public boolean CheckAbility3Possible(GameSystem gs) { return false; }
    public int GetAbility1Range() {
        return 1;
    }
    public int GetAbility2Range() {
        return 2;
    }
    public int GetAbility3Range() {
        return 2;
    }
    public String GetName() {
        return "Paladin";
    }
    public boolean Ability1(ActionContext context) {
        if ( !CheckConditions(2,1, context.GetTarget()) || context.GetTarget().GetTeam() != this.team ) {
            return false;
        }
        Character ally = context.GetTarget(); 
        ally.SetIsDivineSheielded(true);
        return true;
    }

    public boolean Ability2 ( ActionContext context ) {
        Character ally = context.GetTarget();
        if ( !CheckConditions(2, 2, ally ) || context.GetTarget().GetTeam() != this.team ) {
            return false;
        }   
        if ( ally.GetCurrHealth() + 15 <= ally.GetMaxHealth() ) {
            ally.SetCurrHealth(ally.GetCurrHealth() + 10);
            if ( ally.GetIsDivineShielded() ) {
                ally.SetCurrHealth(ally.GetCurrHealth() + 5);
            }
            return true;
        }
        return false;
    }

    public boolean Ability3 ( ActionContext context ) {
        if ( !CheckConditions(2, 1, context.GetTarget()) ) {
            return false;
        }
        Character target = context.GetTarget();
        if ( target.GetCurrHealth() - 5 >= 0 ) {
            if ( target.GetIsDivineShielded() ) {
                target.SetCurrHealth(target.GetCurrHealth() - 2.5);
            } 
            else {
                target.SetCurrHealth(target.GetCurrHealth() - 5);
            }
            return true;
        }
        return false;
    }

   
    
}
