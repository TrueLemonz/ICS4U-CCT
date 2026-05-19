public class Healer extends Character {
    public Healer(Character character, int team) {
        super();
        ApplyStats();
        ScaleStats();
        this.SetName("Healer");
        this.SetFullName(character.getFullName());
        this.SetTeam(team);
        this.spdMod = 0;
        this.intlMod = 3;
        this.atkMod = -1;
        this.sprMod = 3;
        this.hltMod = 2;
        this.sppMod = 1;
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
        return 3;
    }
    public int getAbility3Range() {
        return 2;
    }

    public String getName() {
        return "Healer";
    }
    /*Reads every entity on the grid and checks the range (1)
    * If entity is a character, it checks their team
    * If belonging to the same team as the healer, heals them for 5
    * If opposite team, damages them by 5
    */
    public boolean Ability1 (ActionContext context) {
        if ( !CheckConditions(4)) {
            return false;
        }
        Block[][] grid = context.getGrid();
        Character target = context.getTarget();
        for ( int i = 0; i < 8; i++ ) {
            for (int j = 0; j < 8; j++ ) {
                if ( grid[i][j].getEntity() instanceof Character) {             
                    if ( target != null && target instanceof Character ) {
                        if ( CheckRange(1, target) && target.team == this.team)  {
                            if ( target.getCurrHealth() + 5 < target.getCalculatedStats()[Character.HLTPOS] )
                                target.SetCurrHealth(target.getCurrHealth() + 5);
                                this.SetCurrMagic( this.getCurrMagic() - 4);
                                return true;
                        }
                        else if ( CheckRange(1, target) && target.team != this.team) {
                            target.SetCurrHealth(target.getCurrHealth() - 5);
                            this.SetCurrMagic( this.getCurrMagic() - 4);
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }
    // Gives teammate +4 intl and +2 spr
    public boolean Ability2 (ActionContext context) {
        if ( !CheckConditions ( 3, 3 ,context.getTarget() ) ) {
            return false;
        }
        Character target = context.getTarget(); 
        if ( target != null && target instanceof Character &&  CheckRange(4, target) &&target.team == this.team) {
            target.SetIntl( target.getRawStats()[Character.INTLPOS] + 4);
            target.SetSpr( target.getRawStats()[Character.SPRPOS] + 2);
            // target.CalculateStats();
            this.SetCurrMagic( this.getCurrMagic() - 3 );
            return true;
        }
        return false;
    }
    // Basic attack, has a 50% chance to stun the target
    public boolean Ability3 (ActionContext context) {
        if ( !CheckConditions ( 2, 2, context.getTarget() ) ) {
            return false;
        }
        Character target = context.getTarget();
        if ( target != null && target instanceof Character && CheckRange(2, target) && target.team != this.team) {
            if ( target.getIsDivineShielded()) {
                target.SetCurrHealth(target.getCurrHealth() - 1);
            }
            else target.SetCurrHealth(target.getCurrHealth() - 2);
            double rand = Math.random();
            if ( rand < 0.5 ) {
                target.SetIsStunned(true);
            }
            this.SetCurrMagic( this.getCurrMagic() - 2);
            return true;
        }
        return false;
    }
    
}
//tag (for github)