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
 * Ability 1 :Buff yourself.
 * Ability 2 :Buff an ally.
 * Ability 3 :Bash an enemy for high damage.
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
        this.SetStatMods(SPDPOS, 2);
        this.SetStatMods(INTLPOS, -1);
        this.SetStatMods(ATKPOS, 8);
        this.SetStatMods(MGCPOS, 1);
        this.SetStatMods(HLTPOS, -2);
        this.SetStatMods(SPPPOS, 0);
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

        if (CheckConditions(3)) {
            return true;
        }
        return false;
    }

    /* Checks if ability 2 should be displayed and/or possible to perform.
     * Is calculated differently for each ability.
     * This checks: if there is a character within range.
     *              if the character has sufficient magic amount.
     *              if the character found within range is in the same team.
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
                        Character target = (Character) entity;
                        if (CheckConditions(2, GetAbility2Range(), target) && target.GetTeam() == this.GetTeam()) {
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
                        Character target = (Character) entity;
                        if (CheckConditions(2, GetAbility3Range(), target) && target.GetTeam() != this.GetTeam()) {
                            return true;
                        }
                    }
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
        if (context.GetTarget() == null || !CheckConditions(2, 1, context.GetTarget()) || context.GetTarget().GetTeam() == this.GetTeam()) {
            return false;
        }

        Character target = context.GetTarget();
        target.SetCurrHealth(target.GetCurrHealth() - 15);

        // update stats safely using class references
        this.SetRawStats(Character.INTLPOS, (this.GetRawStats()[Character.INTLPOS] + 1));
        this.SetRawStats(Character.ATKPOS, (this.GetRawStats()[Character.ATKPOS] + 1));
        this.ScaleStats();

        this.SetCurrMagic(this.GetCurrMagic() - 2);
        return true;
    }

    public boolean Ability2(ActionContext context) {
        if (context.GetTarget() == null || !CheckConditions(2, 2, context.GetTarget()) || context.GetTarget().GetTeam() != this.GetTeam()) {
            return false;
        }

        Character ally = context.GetTarget();
        double maxHealth = ally.GetMaxHealth();

        if (ally.GetCurrHealth() + 15 <= maxHealth) {
            ally.SetCurrHealth(ally.GetCurrHealth() + 15);
        } else {
            ally.SetCurrHealth(maxHealth);
        }

        ally.SetRawStats(Character.SPPPOS, (ally.GetRawStats()[Character.SPPPOS] + 1));
        this.SetCurrMagic(this.GetCurrMagic() - 2);
        return true;
    }

    public boolean Ability3(ActionContext context) {
        // updated range parameter to use GetAbility3Range() instead of hardcoded 1
        if (context.GetTarget() == null || !CheckConditions(2, GetAbility3Range(), context.GetTarget()) || context.GetTarget().GetTeam() == this.GetTeam()) {
            return false;
        }

        Character target = context.GetTarget();
        target.SetCurrHealth(target.GetCurrHealth() - 5);
        this.SetCurrMagic(this.GetCurrMagic() - 2);
        return true;
    }
}