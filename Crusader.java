public class Crusader extends Character {

    public Crusader(Character character, int team) {
        super();
        ApplyStats();
        ScaleStats();
        this.SetName("Crusader");
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
    public boolean CheckAbility1Possible(GameSystem gs) { 
        for ( int i = 0 ; i < gs.gameBoard.length; i++ ) {
            for ( int j = 0; j < gs.gameBoard[i].length; j ++ ) {
                Character target = gs.gameBoard[i][j].getEntity().getCharacter();
                if ( target != null && target.getObject() == Entity.CHARACTER && CheckConditions(2, getAbility1Range(), target) && target.getTeam() != this.team ) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean CheckAbility2Possible(GameSystem gs) { 
        for ( int i = 0 ; i < gs.gameBoard.length; i++ ) {
            for ( int j = 0; j < gs.gameBoard[i].length; j ++ ) {
                Character target = gs.gameBoard[i][j].getEntity().getCharacter();
                if ( target != null &&  target.getObject() == Entity.CHARACTER && CheckConditions(2, getAbility2Range(), target) && target.getTeam() == this.team ) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean CheckAbility3Possible(GameSystem gs ) {
        for ( int i = 0 ; i < gs.gameBoard.length; i++ ) {
            for ( int j = 0; j < gs.gameBoard[i].length; j ++ ) {
                Character target = gs.gameBoard[i][j].getEntity().getCharacter();
                if ( target != null &&  target.getObject() == Entity.CHARACTER &&  CheckConditions(2, getAbility3Range(), target) && target.getTeam() != this.team ) {
                    return true;
                }
            }
        }
        return false;
    }
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
        return "Crusader";
    }
    public boolean Ability1(ActionContext context) {
        if ( !CheckConditions(2,1, context.getTarget()) || context.getTarget().getTeam() == this.team ) {
            return false;
        }
        Character target = context.getTarget(); 
        target.SetCurrHealth ( target.getCalculatedStats()[Character.MAXHEALTHPOS] - 15);
        SetIntl ( getRawStats()[Character.INTLPOS] + 1);
        SetAtk ( getRawStats()[Character.ATKPOS] + 1);
        return true;
    }
    public boolean Ability2 ( ActionContext context ) {
        Character ally = context.getTarget();
        if ( !CheckConditions(2, 2, ally ) || context.getTarget().getTeam() != this.team ) {
            return false;
        }   
        if ( ally.getCurrHealth() + 15 <= ally.getCalculatedStats()[Character.MAXHEALTHPOS]) {
            ally.SetCurrHealth(ally.getCurrHealth() + 10);
            ally.SetSPP ( ally.getRawStats()[SPPPOS] + 1);
            return true;
        }
        else {
            double healCap = ally.getCalculatedStats()[Character.MAXHEALTHPOS] - ally.getCurrHealth();
            ally.SetCurrHealth( ally.getCurrHealth() + healCap );
            return true;
        }
    }

    public boolean Ability3 ( ActionContext context ) {
        if ( !CheckConditions(2, 1, context.getTarget()) ) {
            return false;
        }
        Character target = context.getTarget();
        target.SetCurrHealth(target.getCurrHealth() - 5);
        return true;
    }
}
