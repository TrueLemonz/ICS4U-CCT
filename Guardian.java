public class Guardian extends Character {
    public Guardian(Character character, int team) {
        super();
        ApplyStats();
        ScaleStats();
        this.SetName("Guardian");
        this.SetFullName(character.GetFullName());
        this.SetTeam(team);
        this.spdMod -= 2;
        this.intlMod = 4;
        this.atkMod = 0;
        this.mgcMod = 1;
        this.hltMod = 5;
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
    public boolean CheckAbility1Possible(GameSystem gs) {
         if ( this.GetCurrMagic() - 2 >= 0  && this.CheckSurroundingsContain ( gs, Entity.OBSTACLE, 8) ) {
            return true;
         }
         return false;
    }
    public boolean CheckAbility2Possible(GameSystem gs) {
        if ( this.GetCurrMagic() - 1 >= 0 ) { return true; }
        return false;
     }
    public boolean CheckAbility3Possible(GameSystem gs) {
        if ( this.GetCurrMagic() - 1 >= 0 )  { return true; }
         return false; 
         }
    public String GetName() {
        return "Guardian";
    }


    public boolean Ability1(ActionContext context) {
        if ( !CheckConditions(2)) { 
            return false;
        }
        if ( context.GetTarget() != null && context.GetGrid()[context.GetPosY()][context.GetPosX()].GetEntity().GetObject() == Entity.NONE) {
            context.GetGrid()[context.GetPosY()][context.GetPosX()] = new Block(new Obstacle());
            this.SetCurrMagic( this.GetCurrMagic() - 2);
            return true;
        }
        else {
            return false;
        }
    }
    // had to change from void to boolean because I simplified the methods earlier
    public boolean Ability2() {
        if ( !CheckConditions(1)) {
            return false;
        }
        this.SetHlt ( this.GetRawStats()[HLTPOS] + 1);
        this.SetIntl ( this.GetRawStats()[INTLPOS] + 1);
        this.SetCurrMagic ( this.GetCurrMagic() - 1);
        return true;
    }

    public boolean Ability3() {
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
