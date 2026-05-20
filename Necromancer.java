public class Necromancer extends Character {
    public Necromancer(Character character, int team) {
        super();
        ApplyStats();
        ScaleStats();
        this.SetName("Necromancer");
        this.SetFullName(character.GetFullName());
        this.SetTeam(team);
        this.spdMod = -1;
        this.intlMod = 3;
        this.atkMod = 1;
        this.mgcMod = 2;
        this.hltMod = 1;
        this.sppMod = 2;
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
    public String GetName() {
        return "Necromancer";
    }
    public boolean CheckAbility1Possible(GameSystem gs) { 
        if (CheckSurroundingsContain(gs, 0, 1) && GetCurrMagic() > 4) {
            return true;
        }
        return false;
    }
    public boolean CheckAbility2Possible(GameSystem gs) { 
        if (CheckSurroundingsContain(gs, 4, 1)) {
            return true;
        }
        return false; 
    }
    public boolean CheckAbility3Possible(GameSystem gs) { return false; } 
    public boolean Ability1(ActionContext context) {
        if ( context.GetTarget() != null && context.GetGrid()[context.GetPosY()][context.GetPosX()].getEntity().GetObject() == Entity.NONE 
            && CheckRange(1, this)) {
            context.GetGrid()[context.GetPosY()][context.GetPosX()] = new Block(new Minion(this.team));
            return true;
        }
        else {
            return false;
        }
    }
    public boolean Ability2(ActionContext context) {
        if ( context != null && context.GetTargetEntity() != null && context.GetTargetEntity().GetObject() == Entity.MINION) {
            Minion target = context.GetTargetEntity().minion;
            target.Buff();
            return true;
        }
        else {
            return false;
        }
    }
    public boolean Ability3(ActionContext context) {
        if ( context != null && context.GetGrid()[context.GetPosY()][context.GetPosX()].getEntity().GetObject() == Entity.MINION) {
            return true;
        }
        else {
            return false;
        }
    }
   
    
}
