/****************************************************
 * Healer
 *
 * The healer class that stores the abilities and their configurations to be used in the game.
 * First applies new modifiers to the base characters stats.
 * Next runs this.ScaleStats(), running pre-made formulae to set the characters proper stats.
 * Stats are converted from stat points to true stats.
 * (E.G. 10 hlt points -> 90 health.)
 * 
 * Ability 1 : Prayer — buff nearby allies and damage nearby enemies.
 * Ability 2 : Praise — permanently buff an ally's intelligence and spirit.
 * Ability 3 : Strike — weak attack with a 50% chance to paralyse.
 * 
 * Author: Leo & Lucas
 * Date: 20/05/26
 * **************************************************/
public class Healer extends Character {

    /*
     * Constructs a Healer from a base Character.
     *
     * @param character - The base Character object used to initialise the Healer.
     * @param team      - The team ID assigned to this Healer.
     */
    public Healer(Character character, int team) {
        super();
        this.SetName("Healer");
        this.SetFullName(character.GetFullName());
        this.SetTeam(team);
        this.SetStatMods(SPDPOS,  0);
        this.SetStatMods(INTLPOS, 3);
        this.SetStatMods(ATKPOS, -1);
        this.SetStatMods(MGCPOS,  3);
        this.SetStatMods(HLTPOS,  2);
        this.SetStatMods(SPPPOS,  1);
        this.ApplyStats(character);
        this.ScaleStats();
    }

    /* Checks if ability 1 should be displayed and/or possible to perform.
     * Is calculated differently for each ability.
     * This checks: if the character has sufficient magic.
     * 
     * @param gs                - The Game System that contains the grid and all of the entities.
     *
     * @return                  - Returns true or false depending on whether or not the ability may or may not be performed.
     */
    public boolean CheckAbility1Possible(GameSystem gs) {
        if (gs == null || gs.GameBoard == null) {
            return false;
        }
        return CheckConditions(4);
    }

    /* Checks if ability 2 should be displayed and/or possible to perform.
     * Is calculated differently for each ability.
     * This checks: if there is a character within range.
     *              if the character has sufficient magic amount.
     * 
     * @param gs                - The Game System that contains the grid and all of the entities.
     *
     * @return                  - Returns true or false depending on whether or not the ability may or may not be performed.
     */
    public boolean CheckAbility2Possible(GameSystem gs) {
        if (gs == null || gs.GameBoard == null) {
            return false;
        }
        if (!CheckConditions(4)) {
            return false;
        }
        for (int i = 0; i < gs.GameBoard.length; i++) {
            for (int j = 0; j < gs.GameBoard[i].length; j++) {
                if (gs.GameBoard[i][j] != null && gs.GameBoard[i][j].GetEntity() != null) {
                    Entity entity = gs.GameBoard[i][j].GetEntity();
                    if (entity.GetObject() == Entity.CHARACTER) {
                        Character target = entity.GetCharacter();
                        if (target != null && CheckRange(GetAbility2Range(), target)
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
        if (!CheckConditions(2)) {
            return false;
        }
        for (int i = 0; i < gs.GameBoard.length; i++) {
            for (int j = 0; j < gs.GameBoard[i].length; j++) {
                if (gs.GameBoard[i][j] != null && gs.GameBoard[i][j].GetEntity() != null) {
                    Entity entity = gs.GameBoard[i][j].GetEntity();
                    if (entity.GetObject() == Entity.CHARACTER) {
                        Character target = entity.GetCharacter();
                        if (target != null && CheckRange(GetAbility3Range(), target)
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
        return 3;
    }

    /* Returns the range of Ability 3. 
     * @return - The range of Ability 3.
     */
    public int GetAbility3Range() {
        return 2;
    }

    /*
     * Returns the name of the Healer.
     *
     * @return - The name of the Healer.
     */
    public String GetName() {
        return "Healer";
    }

    /*
     * Ability 1 - Prayer: radiates holy energy across all tiles within range 1.
     * Heals allied characters by 5 (up to their maximum) and damages enemies by 5.
     * Costs 4 magic.
     *
     * @param context - ActionContext containing the grid.
     * @return        - True if at least one character was affected.
     */
    public boolean Ability1(ActionContext context) {
        if (context == null) {
            return false;
        }
        if (!CheckConditions(4)) {
            return false;
        }

        Block[][] grid = context.GetGrid();
        if (grid == null) {
            return false;
        }

        boolean targetsAffected = false;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null && grid[i][j].GetEntity() != null) {
                    Entity currentEntity = grid[i][j].GetEntity();
                    if (currentEntity.GetObject() == Entity.CHARACTER) {
                        Character target = currentEntity.GetCharacter();
                        if (target != null && CheckRange(GetAbility1Range(), target)) {
                            if (target.GetTeam() == this.GetTeam()) {
                                // Heal ally up to their maximum
                                if (target.GetCurrHealth() < target.GetMaxHealth()) {
                                    target.SetCurrHealth(
                                            Math.min(target.GetMaxHealth(), target.GetCurrHealth() + 5));
                                    targetsAffected = true;
                                }
                            } else {
                                // Damage enemy
                                target.SetCurrHealth(target.GetCurrHealth() - 5);
                                targetsAffected = true;
                            }
                        }
                    }
                }
            }
        }

        if (targetsAffected) {
            this.SetCurrMagic(this.GetCurrMagic() - 4);
            return true;
        }
        return false;
    }

    /*
     * Ability 2 - Praise: grants an ally a permanent boost to intelligence and
     * spirit, then re-scales their stats. Costs 4 magic.
     *
     * @param context - ActionContext containing the ally target.
     * @return        - True if the praise was applied.
     */
    public boolean Ability2(ActionContext context) {
        if (context == null) {
            return false;
        }
        Character target = context.GetTarget();
        if (target == null) {
            return false;
        }
        if (!CheckConditions(4, GetAbility2Range(), target)) {
            return false;
        }
        if (target.GetTeam() != this.GetTeam()) {
            return false;
        }

        target.SetRawStats(INTLPOS, target.GetRawStats()[INTLPOS] + 4);
        target.SetRawStats(MGCPOS,  target.GetRawStats()[MGCPOS]  + 2);
        target.ScaleStats();

        this.SetCurrMagic(this.GetCurrMagic() - 4);
        return true;
    }

    /*
     * Ability 3 - Strike: smites an enemy with a bolt of energy, dealing 2 damage
     * and having a 50% chance to stun the target. Costs 2 magic.
     *
     * @param context - ActionContext containing the enemy target.
     * @return        - True if the strike landed.
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

        target.SetCurrHealth(target.GetCurrHealth() - 2);

        if (Math.random() < 0.5) {
            target.SetIsStunned(true);
        }

        this.SetCurrMagic(this.GetCurrMagic() - 2);
        return true;
    }
}