public class Paladin extends Character {

    public Paladin(Character character, int team) {
        super();
        this.SetName("Paladin");
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
    public boolean CheckAbility1Possible() { return false; }
    public boolean CheckAbility2Possible() { return false; }
    public boolean CheckAbility3Possible() { return false; }
    public int GetAbility1Range() {
        return 1;
    }
    public int GetAbility2Range() {
        return 2;
    }
    public int GetAbility3Range() {
        return 2;
    }
    public String getName() {
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

    public String[] getAbilityMenu() {
        return new String[]{
            "1. Divine Shield", 
            "2. Healing Light", 
            "3. Smite"
        };
    }
    public boolean executeAbility(int choice, ActionContext context) {
        if (choice == 1 && CheckAbility1Possible()) {
            System.out.println(this.GetFullName() + " casts Divine Shield!");
            // Apply damage/effects here
            return true;
        } else if (choice == 2 && CheckAbility2Possible()) {
            System.out.println(this.GetFullName() + " casts Healing Light!");
            // Apply damage/effects here
            return true;
        } else if (choice == 3 && CheckAbility3Possible()) {
            System.out.println(this.GetFullName() + " casts Smite on " + context.GetTarget().GetFullName() + "!");
            // Apply damage/effects here
            return true;
        }
        System.out.println("Ability failed or unavailable.");
        return false;
    }
}
//tag (for github)