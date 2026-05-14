public class Healer extends Character {
    public Healer(Character character, int team) {
        super();
        this.SetName("Healer");
        this.SetFullName(character.GetFullName());
        this.SetTeam(team);
        this.spdMod = 0;
        this.intlMod = 3;
        this.atkMod = -1;
        this.sprMod = 3;
        this.hltMod = 2;
        this.sppMod = 1;
        this.spd = character.spd + this.spdMod;
        this.intl = character.intl + this.intlMod;
        this.atk = character.atk + this.atkMod;
        this.spr = character.spr + this.sprMod;
        this.hlt = character.hlt + this.hltMod;
        this.spp = character.spp + this.sppMod;
    }

    public String getName() {
        return "Healer";
    }
    /*Reads every entity on the grid and checks the range (1)
    * If entity is a character, it checks their team
    * If belonging to the same team as the healer, heals them for 5
    * If opposite team, damages them by 5
    */
    public boolean Special (ActionContext context) {
        if ( !CheckConditions(4)) {
            return false;
        }
        Block[][] grid = context.GetGrid();
        Character target = context.GetTarget();
        for ( int i = 0; i < 8; i++ ) {
            for (int j = 0; j < 8; j++ ) {
                if ( grid[i][j].getEntity() instanceof Character) {             
                    if ( target != null && target instanceof Character ) {
                        if ( CheckRange(1, target) && target.team == this.team)  {
                            if ( target.GetCurrHealth() + 5 < target.GetCalculatedStats()[target.HLTPOS] )
                                target.SetCurrHealth(target.GetCurrHealth() + 5);
                                this.SetCurrMagic( this.GetCurrMagic() - 4);
                                return true;
                        }
                        else if ( CheckRange(1, target) && target.team != this.team) {
                            target.SetCurrHealth(target.GetCurrHealth() - 5);
                            this.SetCurrMagic( this.GetCurrMagic() - 4);
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
    public boolean Ability1 (ActionContext context) {
        if ( !CheckConditions ( 3, 3 ,context.GetTarget() ) ) {
            return false;
        }
        Character target = context.GetTarget(); 
        if ( target != null && target instanceof Character &&  CheckRange(4, target) &&target.team == this.team) {
            target.SetIntl( target.GetRawStats()[target.INTLPOS] + 4);
            target.SetSpr( target.GetRawStats()[target.SPRPOS] + 2);
            // target.CalculateStats();
            this.SetCurrMagic( this.GetCurrMagic() - 3 );
            return true;
        }
        return false;
    }
    // Basic attack, has a 50% chance to stun the target
    public boolean Ability2 (ActionContext context) {
        if ( !CheckConditions ( 2, 2, context.GetTarget() ) ) {
            return false;
        }
        Character target = context.GetTarget();
        if ( target != null && target instanceof Character && CheckRange(2, target) && target.team != this.team) {
            if ( target.GetIsDivineShielded()) {
                target.SetCurrHealth(target.GetCurrHealth() - 1);
            }
            else target.SetCurrHealth(target.GetCurrHealth() - 2);
            double rand = Math.random();
            if ( rand < 0.5 ) {
                target.SetIsStunned(true);
            }
            this.SetCurrMagic( this.GetCurrMagic() - 2);
            return true;
        }
        return false;
    }
}
