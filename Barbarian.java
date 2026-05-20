/****************************************************
 * Barbarian
 *
 * The barbarian class that stores the abilities and their configurations to be used in the game.
 * First applies new modifiers to the base characters stats.
 * Next runs this.ScaleStats(), running pre-made formulae to set the characters proper stats.
 * Stats are converted from stat points to true stats.
 * (E.G. 10 hlt points -> 90 health.)
 * 
 * Ability 1        :Flip an enemy onto the opposite side of yourself.
 * Ability 2        :Kickpunch an enemy for massive damage.
 * Ability 3        :Infect yourself with lupus and deal extra damage.
 * 
 * Author: Leo & Lucas
 * Date: 20/05/26
 * **************************************************/
public class Barbarian extends Character {

    /* Constructs a barbarian character instance.
     * Initializes base stats and applies class-based modifications (SetStatMods) for the
     * barbarian class.
     *
     * @param character         - The base Character object used to initialize the new
     * barbarian.
     * 
     * @param team              - The team ID assigned to this barbarian.
     */
    public Barbarian(Character character, int team) {
        super();
        this.SetName("Barbarian");
        this.SetFullName(character.GetFullName());
        this.SetTeam(team);
        this.SetStatMods(SPDPOS, 2);
        this.SetStatMods(INTLPOS,  -1);
        this.SetStatMods(ATKPOS, 8);
        this.SetStatMods(MGCPOS, 1);
        this.SetStatMods(HLTPOS, -2);
        this.SetStatMods(SPPPOS, 0);
        this.ApplyStats(character);
        this.ScaleStats();
    }

    /* Checks if ability 1 should be displayed and/or possible to perform.
     * Is calculated differently for each ability.
     * This checks: if there is a character within range.
     *              if the character has sufficient magic amount.
     *              if the character found within range is not in the same team.
     * 
     * @param gs                - The Game System that contains the grid and all of the entities.
     *
     * @return                  - Returns true or false depending on whether or not the ability may or may not be performed.
     */
    public boolean CheckAbility1Possible(GameSystem gs) {
        if (gs == null || gs.GameBoard == null) {
            return false;
        }
        for (int i = 0; i < gs.GameBoard.length; i++) {
            for (int j = 0; j < gs.GameBoard[i].length; j++) {
                Character target = gs.GameBoard[i][j].GetEntity().GetCharacter();
                if (target != null && target.GetObject() == Entity.CHARACTER && CheckConditions(2, GetAbility1Range(), target) && target.GetTeam() != this.GetTeam()) {
                    return true;
                }
            }
        }
        return false;
    }

    /* Checks if ability 2 should be displayed and/or possible to perform.
     * Is calculated differently for each ability.
     * This checks: if there is a character within range.
     *              if the character has sufficient magic amount.
     *              if the character found within range is not in the same team.
     * 
     * @param gs                - The Game System that contains the grid and all of the entities.
     *
     * @return                  - Returns true or false depending on whether or not the ability may or may not be performed.
     */
    public boolean CheckAbility2Possible(GameSystem gs) {
        if (gs == null || gs.GameBoard == null) {
            return false;
        }
        for (int i = 0; i < gs.GameBoard.length; i++) {
            for (int j = 0; j < gs.GameBoard[i].length; j++) {
                Character target = gs.GameBoard[i][j].GetEntity().GetCharacter();
                if (target != null && target.GetObject() == Entity.CHARACTER && CheckConditions(2, GetAbility2Range(), target) && target.GetTeam() != this.GetTeam()) {
                    return true;
                }
            }
        }
        return false;
    }

    /* Checks if ability 3 should be displayed and/or possible to perform.
     * Is calculated differently for each ability.
     * This checks: if the if the character has sufficient magic amount.
     * 
     * @param gs                - The Game System that contains the grid and all of the entities.
     *
     * @return                  - Returns true or false depending on whether or not the ability may or may not be performed.
     */
    public boolean CheckAbility3Possible(GameSystem gs) {
        if (gs == null || gs.GameBoard == null) {
            return false;
        }
        if (CheckConditions(2)) {
            return true;
        }else {
            return false;
        }
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

        // find where the enemy will land and make sure its not occupied/off the map
        int flipY = myY + (myY - tarY);
        int flipX = myX + (myX - tarX);
        if (flipY < 0 || flipY >= grid.length || flipX < 0 || flipX >= grid[0].length) {
            return false;
        }
        if (grid[flipY][flipX].GetEntity() != null && grid[flipY][flipX].GetEntity().GetObject() != Entity.NONE) {
            return false;
        }
        grid[flipY][flipX].SetEntity(target);
        target.GetPosition()[0] = flipY;
        target.GetPosition()[1] = flipX;
        // Place new empty entity where it was
        grid[tarY][tarX].SetEntity(new Entity("", false, false, false, false));
        this.SetCurrMagic(this.GetCurrMagic() - 2);
        return true;
    }

    // Strong attack, meant to hit multiple times so that block/parry is calculated
    // for each hit
    // and its unlikely for the whole thing to be blocked
    public boolean Ability2(ActionContext context) {
        if (!CheckConditions(1, 1, context.GetTarget())) {
            return false;
        }
        applyPassive();
        this.ScaleStats();
        context.GetTarget().SetCurrHealth(context.GetTarget().GetCurrHealth() - this.GetCalculatedStats()[ATTACKPOS] * 4);
        this.SetCurrMagic(this.GetCurrMagic() - 1);
        this.ScaleStats();
        return true;
    }

    public boolean Ability3() {
        applyPassive();
        if (this.GetCurrHealth() > 0.2 * this.GetCalculatedStats()[HLTPOS]) {
            this.SetCurrHealth(this.GetCurrHealth() - 0.2 * this.GetCalculatedStats()[HLTPOS]);
            this.SetCalculatedStats(ATTACKPOS, this.GetCalculatedStats()[ATTACKPOS] * 1.15);
            this.SetCurrMagic(this.GetCurrMagic() - 2);
            this.ScaleStats();
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
        this.GetCalculatedStats()[ATTACKPOS] *= damageMultiplier;
    }
}
//Does this work