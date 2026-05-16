public class Guardian extends Character {
    public Guardian(Character character, int team) {
        super();
        this.SetName("Guardian");
        this.SetFullName(character.GetFullName());
        this.SetTeam(team);
        this.spdMod -= 2;
        this.intlMod = 4;
        this.atkMod = 0;
        this.sprMod = 1;
        this.hltMod = 5;
        this.sppMod = 0;
        this.spd = character.spd + this.spdMod ;
        if ( this.spd + this.spdMod < 0 ) {
            this.spd = 0;
        }
        this.intl = character.intl + this.intlMod;
        if ( this.intl + this.intlMod < 0 ) {
            this.intl = 0;
        }
        this.atk = character.atk + this.atkMod;
        if ( this.atk + this.atkMod < 0 ) {
            this.atk = 0;
        }
        this.spr = character.spr + this.sprMod;
        if ( this.spr + this.sprMod < 0 ) {
            this.spr = 0;
        }
        this.hlt = character.hlt + this.hltMod;
        if ( this.hlt + this.hltMod < 0 ) {
            this.hlt = 0;
        }
        this.spp = character.spp + this.sppMod;
        if ( this.spp + this.sppMod < 0 ) {
            this.spp = 0;
        }
    }
    public int GetSpecialRange() {
        return 9;
    }
    public int GetAbility1Range() {
        return 9;
    }
    public int GetAbility2Range() {
        return 9;
    }
    public String getName() {
        return "Guardian";
    }


    public boolean Special(ActionContext context) {
        if ( !CheckConditions(2)) { 
            return false;
        }
        if ( context.GetGrid()[context.getPosX()][context.getPosY()].getEntity().GetObject() == 0) {
            context.GetGrid()[context.getPosX()][context.getPosY()] = new Block(new Obstacle());
            this.SetCurrMagic( this.GetCurrMagic() - 2);
            return true;
        }
        else {
            return false;
        }
    }
    // had to change from void to boolean because I simplified the methods earlier
    public boolean Ability1(ActionContext context) {
        if ( !CheckConditions(1)) {
            return false;
        }
        this.SetHlt ( this.GetRawStats()[HLTPOS] + 1);
        this.SetIntl ( this.GetRawStats()[INTLPOS] + 1);
        this.SetCurrMagic ( this.GetCurrMagic() - 1);
        return true;
    }

    public boolean Ability2(ActionContext context) {
         if ( !CheckConditions(1)) {
            return false;
        }
        if (1.1 * this.GetCurrHealth() <= this.health ) {
            this.SetCurrHealth(this.GetCurrHealth() * 1.1); // Sorry if this broke it, tried to make everything encapsulated
            this.SetCurrMagic( this.GetCurrMagic() - 1);
            return true;
        }
        else {
            return false;
        }
    }
}