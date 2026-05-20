public class Healer extends Character {
    public Healer(Character character, int team) {
        super();
        ApplyStats();
        ScaleStats();
        this.SetName("Healer");
        this.SetFullName(character.GetFullName());
        this.SetTeam(team);
        this.spdMod = 0;
        this.intlMod = 3;
        this.atkMod = -1;
        this.mgcMod = 3;
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
        return 3;
    }
    public int GetAbility3Range() {
        return 2;
    }

    public String GetName() {
        return "Healer";
    }
    /*Reads every entity on the grid and checks the range (1)
    * If entity is a character, it checks their team
    * If belonging to the same team as the healer, heals them for 5
    * If opposite team, damages them by 5
    */
    public boolean Ability1(ActionContext context) {
        // check if healer is alive and has enough magic
        if (!CheckConditions(4)) {
            return false;
        }

        Block[][] grid = context.GetGrid();
        if (grid == null) {
            return false;
        }

        boolean targetsAffected = false;
        double currentMagicPool = this.GetCurrMagic();

        // Iterate across the entire 8x8 game board grid
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null && grid[i][j].getEntity() != null) {
                    Entity currentEntity = grid[i][j].getEntity();

                    // check if the entity is a character before we check if it is an ally or an enemy
                    if (currentEntity != null && currentEntity.GetObject() == Entity.CHARACTER) {
                        Character target = (Character) currentEntity;

                        // check if the character is within an adjacent range of 1 from the caster
                        if (CheckRange(1, target)) {
                            if (target.GetTeam() == this.team) {
                                // Heal Allies
                                double maxHealth = target.GetCalculatedStats()[Character.HLTPOS];
                                if (target.GetCurrHealth() < maxHealth) {
                                    target.SetCurrHealth(Math.min(maxHealth, target.GetCurrHealth() + 5));
                                    targetsAffected = true;
                                }
                            } else {
                                // Damage Enemies
                                target.SetCurrHealth(Math.max(0, target.GetCurrHealth() - 5));
                                targetsAffected = true;
                            }
                        }
                    }
                }
            }
        }

        if (targetsAffected) {
            this.SetCurrMagic(currentMagicPool - 4);
            return true;
        }
        return false;
    }
    // Gives teammate +4 intl and +2 mgc
    public boolean Ability2 (ActionContext context) {
        if ( !CheckConditions ( 3, 3 ,context.GetTarget() ) ) {
            return false;
        }
        Character target = context.GetTarget(); 
        if ( target != null && target instanceof Character &&  CheckRange(4, target) &&target.team == this.team) {
            target.SetIntl( target.GetRawStats()[Character.INTLPOS] + 4);
            target.SetMgc( target.GetRawStats()[Character.MGCPOS] + 2);
            target.ScaleStats();
            this.SetCurrMagic( this.GetCurrMagic() - 3 );
            return true;
        }
        return false;
    }
    // Basic attack, has a 50% chance to stun the target
    public boolean Ability3 (ActionContext context) {
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
//tag (for github)