/****************************************************
 * Crusader
 *
 * The crusader class that stores the abilities and their configurations to be
 * used in the game.
 * First applies new modifiers to the base characters stats.
 * Next runs this.ScaleStats(), running pre-made formulae to set the characters
 * proper stats.
 * Stats are converted from stat points to true stats.
 * (E.G. 10 hlt points -> 90 health.)
 * 
 * Ability 1 : Buff yourself — raise intelligence and attack.
 * Ability 2 : Bless an ally — restore health and boost spell power.
 * Ability 3 : Shield Bash an enemy for moderate damage.
 * 
 * Author: Leo & Lucas
 * Date: 20/05/26
 **************************************************/
public class Crusader extends Character {

    /*
     * Constructs a crusader character instance.
     * Initializes base stats and applies class-based modifications (SetStatMods) for the
     * crusader class.
     *
     * @param character - The base Character object used to initialize the new
     * crusader.
     * 
     * @param team - The team ID assigned to this crusader.
     */
    public Crusader(Character character, int team) {
        super();
        this.SetName("Crusader");
        this.SetFullName(character.GetFullName());
        this.SetTeam(team);
        this.SetStatMods(SPDPOS,  2);
        this.SetStatMods(INTLPOS, -1);
        this.SetStatMods(ATKPOS,  8);
        this.SetStatMods(MGCPOS,  1);
        this.SetStatMods(HLTPOS, -2);
        this.SetStatMods(SPPPOS,  0);
        this.ApplyStats(character);
        this.ScaleStats();
    }

    /* Checks if ability 1 should be displayed and/or possible to perform.
     * Is calculated differently for each ability.
     * This checks: if the character has sufficient magic amount.
     * 
     * @param gs                - The Game System that contains the grid and all of the entities.
     *
     * @return                  - Returns true or false depending on whether or not the ability may or may not be performed.
     */
    public boolean CheckAbility1Possible(GameSystem gs) {
        if (gs == null || gs.GameBoard == null) {
            return false;
        }
        return CheckConditions(3);
    }

    /* Checks if ability 1 should be displayed and/or possible to perform.
     * Is calculated differently for each ability.
     * This checks: if the character has sufficient magic amount.
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
                if (gs.GameBoard[i][j] != null && gs.GameBoard[i][j].GetEntity() != null) {
                    Entity entity = gs.GameBoard[i][j].GetEntity();
                    if (entity.GetObject() == Entity.CHARACTER) {
                        Character target = entity.GetCharacter();
                        if (target != null && CheckConditions(2, GetAbility2Range(), target)
                                && target.GetTeam() == this.GetTeam()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /* Checks if ability 3 should be displayed and/or possible to perform.
     * Is calculated differently for each ability.
     * This checks: if there is a character within range.
     *              if the character has sufficient magic amount.
     *              if the character found within range is not in the same team.
     * 
     * @param gs                - The Game System that contains the grid and all of the entities.
     *
     * @return                  - Returns true or false depending on whether or not the ability may or may not be performed.
     */
    public boolean CheckAbility3Possible(GameSystem gs) {
        if (gs == null || gs.GameBoard == null) {
            return false;
        }
        for (int i = 0; i < gs.GameBoard.length; i++) {
            for (int j = 0; j < gs.GameBoard[i].length; j++) {
                if (gs.GameBoard[i][j] != null && gs.GameBoard[i][j].GetEntity() != null) {
                    Entity entity = gs.GameBoard[i][j].GetEntity();
                    if (entity.GetObject() == Entity.CHARACTER) {
                        Character target = entity.GetCharacter();
                        if (target != null && CheckConditions(2, GetAbility3Range(), target)
                                && target.GetTeam() != this.GetTeam()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /* Returns the range of Ability 1. 
     * @return - The range of Ability 1.
     */
    public int GetAbility1Range() {
        return 1;
    }

    /* Returns the range of Ability 2. 
     * @return - The range of Ability 2.
     */
    public int GetAbility2Range() {
        return 2;
    }

    /* Returns the range of Ability 3. 
     * @return - The range of Ability 3.
     */
    public int GetAbility3Range() {
        return 2;
    }

    /*
     * Returns the name of the Crusader.
     *
     * @return - The name of the Crusader.
     */
    public String GetName() {
        return "Crusader";
    }

    /*
     * Ability 1 - Holy Divinity: the Crusader permanently buffs his intl and atk
     *
     * @param context - ActionContext for overloading 
     * @return        - True if the buff was applied successfully, else false
     * 
     */
    public boolean Ability1(ActionContext context) {
        if (!CheckConditions(3)) {
            return false;
        }

        this.SetRawStats(INTLPOS, this.GetRawStats()[INTLPOS] + 1);
        this.SetRawStats(ATKPOS,  this.GetRawStats()[ATKPOS]  + 1);
        this.ScaleStats();

        this.SetCurrMagic(this.GetCurrMagic() - 3);
        return true;
    }

    /*
     * Ability 2 - Holy Light: blesses an allied character, restoring up to 15
     * health and permanently increasing their spell power.
     *
     * @param context - ActionContext containing the ally target and the grid.
     * @return        - True if the blessing was applied successfully.
     */
    public boolean Ability2(ActionContext context) {
        if (context == null) {
            return false;
        }
        Character ally = context.GetTarget();
        if (ally == null) {
            return false;
        }
        if (!CheckConditions(2, GetAbility2Range(), ally)) {
            return false;
        }
        if (ally.GetTeam() != this.GetTeam()) {
            return false;
        }

        double healed = Math.min(ally.GetMaxHealth(), ally.GetCurrHealth() + 15);
        ally.SetCurrHealth(healed);

        ally.SetRawStats(SPPPOS, ally.GetRawStats()[SPPPOS] + 1);
        ally.ScaleStats();

        this.SetCurrMagic(this.GetCurrMagic() - 2);
        return true;
    }

    /*
     * Ability 3 - Shield Bash: slams an enemy with the Crusader's shield for
     * moderate damage.
     *
     * @param context - ActionContext containing the enemy target and the grid.
     * @return        - True if the bash landed.
     */
    public boolean Ability3(ActionContext context) {
        if (context == null) {
            return false;
        }
        Character target = context.GetTarget();
        if (target == null) {
            return false;
        }
        if (!CheckConditions(2, GetAbility3Range(), target)) {
            return false;
        }
        if (target.GetTeam() == this.GetTeam()) {
            return false;
        }

        target.SetCurrHealth(target.GetCurrHealth() - 15);
        this.SetCurrMagic(this.GetCurrMagic() - 2);
        return true;
    }
}