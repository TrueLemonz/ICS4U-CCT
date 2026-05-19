public class Paladin extends Character {

    public Paladin(Character character, int team) {
        super();
        ApplyStats();
        ScaleStats();
        this.SetName("Paladin");
        this.SetFullName(character.getFullName());
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
    public boolean CheckAbility1Possible(GameSystem gs) { return false; }
    public boolean CheckAbility2Possible(GameSystem gs) { return false; }
    public boolean CheckAbility3Possible(GameSystem gs) { return false; }
    public int getAbility1Range() {
        return 1;
    }
    public int getAbility2Range() {
        return 2;
    }
    public int getAbility3Range() {
        return 2;
    }
    public String getName() {
        return "Paladin";
    }
    public boolean Ability1(ActionContext context) {
        if ( !CheckConditions(2,1, context.getTarget()) || context.getTarget().getTeam() != this.team ) {
            return false;
        }
        Character ally = context.getTarget(); 
        ally.SetIsDivineSheielded(true);
        return true;
    }

    public boolean Ability2 ( ActionContext context ) {
        Character ally = context.getTarget();
        if ( !CheckConditions(2, 2, ally ) || context.getTarget().getTeam() != this.team ) {
            return false;
        }   
        if ( ally.getCurrHealth() + 15 <= ally.getMaxHealth() ) {
            ally.SetCurrHealth(ally.getCurrHealth() + 10);
            if ( ally.getIsDivineShielded() ) {
                ally.SetCurrHealth(ally.getCurrHealth() + 5);
            }
            return true;
        }
        return false;
    }

    public boolean Ability3 ( ActionContext context ) {
        if ( !CheckConditions(2, 1, context.getTarget()) ) {
            return false;
        }
        Character target = context.getTarget();
        if ( target.getCurrHealth() - 5 >= 0 ) {
            if ( target.getIsDivineShielded() ) {
                target.SetCurrHealth(target.getCurrHealth() - 2.5);
            } 
            else {
                target.SetCurrHealth(target.getCurrHealth() - 5);
            }
            return true;
        }
        return false;
    }

   
    
}
