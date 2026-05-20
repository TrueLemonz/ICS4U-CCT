public class Crusader extends Character {

    /*
     * Constructs a crusader character instance.
     * Initializes base stats and applies class-based modifications (___mod) for the
     * crusader class.
     *
     * @param character     - The base Character object used to initialize the new
     *                        crusader.
     * @param team          - The team ID assigned to this crusader.
     */
    public Crusader(Character character, int team) {
        super();
        ApplyStats();
        ScaleStats();
        this.SetName("Crusader");
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
    public boolean CheckAbility1Possible(GameSystem gs) { 
        for ( int i = 0 ; i < gs.gameBoard.length; i++ ) {
            for ( int j = 0; j < gs.gameBoard[i].length; j ++ ) {
                Character target = gs.gameBoard[i][j].getEntity().GetCharacter();
                if ( target != null && target.GetObject() == Entity.CHARACTER && CheckConditions(2, GetAbility1Range(), target) && target.GetTeam() != this.team ) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean CheckAbility2Possible(GameSystem gs) { 
        for ( int i = 0 ; i < gs.gameBoard.length; i++ ) {
            for ( int j = 0; j < gs.gameBoard[i].length; j ++ ) {
                Character target = gs.gameBoard[i][j].getEntity().GetCharacter();
                if ( target != null &&  target.GetObject() == Entity.CHARACTER && CheckConditions(2, GetAbility2Range(), target) && target.GetTeam() == this.team ) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean CheckAbility3Possible(GameSystem gs ) {
        for ( int i = 0 ; i < gs.gameBoard.length; i++ ) {
            for ( int j = 0; j < gs.gameBoard[i].length; j ++ ) {
                Character target = gs.gameBoard[i][j].getEntity().GetCharacter();
                if ( target != null &&  target.GetObject() == Entity.CHARACTER &&  CheckConditions(2, GetAbility3Range(), target) && target.GetTeam() != this.team ) {
                    return true;
                }
            }
        }
        return false;
    }
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
        return "Crusader";
    }
    public boolean Ability1(ActionContext context) {
        if ( !CheckConditions(2,1, context.GetTarget()) || context.GetTarget().GetTeam() == this.team ) {
            return false;
        }
        Character target = context.GetTarget(); 
        if ( target != null ) {
            target.SetCurrHealth ( target.GetCurrHealth() - 15);
            SetIntl ( GetRawStats()[Character.INTLPOS] + 1);
            SetAtk ( GetRawStats()[Character.ATKPOS] + 1);
            ScaleStats();
            return true;
        }
        return false;
    }
    public boolean Ability2 ( ActionContext context ) {
        Character ally = context.GetTarget();
        if ( ally != null && !CheckConditions(2, 2, ally ) || context.GetTarget().GetTeam() != this.team ) {
            return false;
        }   
        if ( ally != null && ally.GetCurrHealth() + 15 <= ally.GetCalculatedStats()[Character.MAXHEALTHPOS]) {
            ally.SetCurrHealth(ally.GetCurrHealth() + 10);
            ally.SetSPP ( ally.GetRawStats()[SPPPOS] + 1);
            return true;
        }
        else {
            double healCap = ally.GetCalculatedStats()[Character.MAXHEALTHPOS] - ally.GetCurrHealth();
            ally.SetCurrHealth( ally.GetCurrHealth() + healCap );
            return true;
        }
    }

    public boolean Ability3 ( ActionContext context ) {
        if ( !CheckConditions(2, 1, context.GetTarget()) ) {
            return false;
        }
        Character target = context.GetTarget();
        if ( target != null ) {
            target.SetCurrHealth(target.GetCurrHealth() - 5);
            return true;
        }
        return false;
    }
}
