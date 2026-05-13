public class Crusader extends Character {

    public Crusader(Character character, int team) {
        super();
        this.team = team;
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
    public String getName() {
        return "Crusader";
    }
    public boolean Special(ActionContext context) {
        if ( !CheckConditions(2,1, context.GetTarget()) || context.GetTarget().getTeam() != this.team ) {
            return false;
        }
        Character ally = context.GetTarget(); 
        ally.SetIsDivineSheielded(true);
        return true;
    }

    public boolean Ability1 ( ActionContext context ) {
        Character ally = context.GetTarget();
        if ( !CheckConditions(2, 2, ally ) || context.GetTarget().getTeam() != this.team ) {
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

    public boolean Ability2 ( ActionContext context ) {
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