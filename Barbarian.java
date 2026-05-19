public class Barbarian extends Character {

    public Barbarian(Character character, int team) {
        super();
        ApplyStats();
        ScaleStats();
        this.SetName("Barbarian");
        this.SetFullName(character.GetFullName());
        this.SetTeam(team);
        this.spdMod = 2;
        this.intlMod = -1;
        this.atkMod = 8;
        this.mgcMod = 1;
        this.hltMod = -2;
        this.sppMod = 0;
        this.spd = character.spd + this.spdMod;
        if (this.spd + this.spdMod < 0) {
            this.spd = 1;
        }
        this.intl = character.intl + this.intlMod;
        if (this.intl + this.intlMod < 0) {
            this.intl = 1;
        }
        this.atk = character.atk + this.atkMod;
        if (this.atk + this.atkMod < 0) {
            this.atk = 1;
        }
        this.mgc = character.mgc + this.mgcMod;
        if (this.mgc + this.mgcMod < 0) {
            this.mgc = 1;
        }
        this.hlt = character.hlt + this.hltMod;
        if (this.hlt + this.hltMod < 0) {
            this.hlt = 1;
        }
        this.spp = character.spp + this.sppMod;
        if (this.spp + this.sppMod < 0) {
            this.spp = 1;
        }
        ScaleStats();
    }

    public boolean CheckAbility1Possible(GameSystem gs) {
        for (int i = 0; i < gs.gameBoard.length; i++) {
            for (int j = 0; j < gs.gameBoard[i].length; j++) {
                Character target = gs.gameBoard[i][j].getEntity().GetCharacter();
                if (target != null && target.GetObject() == Entity.CHARACTER
                        && CheckConditions(2, GetAbility1Range(), target) && target.GetTeam() != this.team) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean CheckAbility2Possible(GameSystem gs) {
        for (int i = 0; i < gs.gameBoard.length; i++) {
            for (int j = 0; j < gs.gameBoard[i].length; j++) {
                Character target = gs.gameBoard[i][j].getEntity().GetCharacter();
                if (target != null && target.GetObject() == Entity.CHARACTER
                        && CheckConditions(2, GetAbility2Range(), target) && target.GetTeam() != this.team) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean CheckAbility3Possible(GameSystem gs) {
        return Ability3();
    }

    public int GetAbility1Range() {
        return 1;
    }

    public int GetAbility2Range() {
        return 1;
    }

    public String GetName() {
        return "Barbarian";
    }

    // Picks up character, throws them behind
    public boolean Ability1(ActionContext context) {
        Character target = context.GetTarget();
        Block[][] grid = context.GetGrid();

        if (target == null || grid == null || !CheckConditions(2, 1, target)) {
            return false;
        }

        applyPassive();

        int[] myPos = this.GetPosition();
        int[] tarPos = target.GetPosition();

        int myY = myPos[0];
        int myX = myPos[1];
        int tarY = tarPos[0];
        int tarX = tarPos[1];

        // find where the enemy will land
        int flipY = myY + (myY - tarY);
        int flipX = myX + (myX - tarX);

        // check if it's in the map
        if (flipY < 0 || flipY >= grid.length || flipX < 0 || flipX >= grid[0].length) {
            return false;
        }

        // check if theres already an object there
        if (grid[flipY][flipX].getEntity().GetObject() != Entity.NONE) {
            return false;
        }

        // Place target character into landing zone
        grid[flipY][flipX].SetEntity(target);
        target.GetPosition()[0] = flipY;
        target.GetPosition()[1] = flipX;

        // Clear original target space with an empty entity 
        grid[tarY][tarX] = new Block(new Entity("", false, false, false, false));

        this.SetCurrMagic(this.GetCurrMagic() - 2);
        return true;
    }

    // System.out.println( Ability1Hint());
    // Strong attack, meant to hit multiple times so that block/parry is calculated
    // for each hit
    // and its unlikely for the whole thing to be blocked
    public boolean Ability2(ActionContext context) {
        if (!CheckConditions(1, 1, context.GetTarget())) {
            return false;
        }
        applyPassive();
        ScaleStats();
        if (context.GetTarget().GetIsDivineShielded()) {
            context.GetTarget()
                    .SetCurrHealth(context.GetTarget().GetCalculatedStats()[Character.MAXHEALTHPOS] - this.attack * 2);
        } else
            context.GetTarget().SetCurrHealth(context.GetTarget().GetCurrHealth() - this.attack * 4);
        this.SetCurrMagic(this.GetCurrMagic() - 1);
        ScaleStats();
        return true;
    }

    public boolean Ability3() {
        applyPassive();
        if (this.GetCurrHealth() > 0.2 * this.GetCalculatedStats()[HLTPOS]) {
            this.SetCurrHealth(this.GetCurrHealth() - 0.2 * this.GetCalculatedStats()[HLTPOS]);
            this.SetCalculatedStats(ATTACKPOS, this.GetCalculatedStats()[ATTACKPOS] * 1.15);
            this.SetCurrMagic(this.GetCurrMagic() - 2);
            ScaleStats();
            return true;
        }
        return false;
    }

    private void applyPassive() {
        // had to scrap the log stuff, it was always 0 for some reason
        double maxHP = this.GetCalculatedStats()[Character.HLTPOS];
        double missingHPRatio = (maxHP - this.GetCurrHealth()) / maxHP;
        
        // At 100% health, multiplier is 1.0. At 20% health, multiplier increases is roughly 1.4x
        double damageMultiplier = 1.0 + (0.5 * missingHPRatio);
        this.attack *= damageMultiplier;
    }
}
