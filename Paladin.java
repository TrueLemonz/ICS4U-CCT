/****************************************************
 * Paladin
 *
 * The paladin class that stores the abilities and their configurations to be used in the game.
 * First applies new modifiers to the base characters stats.
 * Next runs this.ScaleStats(), running pre-made formulae to set the characters proper stats.
 * Stats are converted from stat points to true stats.
 * (E.G. 10 hlt points -> 90 health.)
 * 
 * Ability 1 : Verdict          - melee attack that permanently boosts the Paladin's intelligence.
 * Ability 2 : Heal Self        - restores a portion of the Paladin's own health.
 * Ability 3 : Strike           - strong close-range melee attack.
 * 
 * Author: Leo & Lucas
 * Date: 20/05/26
 * **************************************************/
public class Paladin extends Character {

    /*
     * Constructs a Paladin from a base Character.
     *
     * @param character         - The base Character object used to initialise the Paladin.
     * @param team              - The team ID assigned to this Paladin.
     */
    public Paladin(Character character, int team) {
        super();
        this.SetName("Paladin");
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
                if (gs.GameBoard[i][j] != null && gs.GameBoard[i][j].GetEntity() != null) {
                    Entity entity = gs.GameBoard[i][j].GetEntity();
                    if (entity.GetObject() == Entity.CHARACTER) {
                        Character target = entity.GetCharacter();
                        if (target != null && CheckConditions(2, GetAbility1Range(), target)
                                && target.GetTeam() != this.GetTeam()) {
                            return true;
                        }
                    }
                }
            }
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
        return CheckConditions(4) && this.GetCurrHealth() < this.GetMaxHealth();
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
     * @return                  - The range of Ability 1.
     */
    public int GetAbility1Range() {
        return 1;
    }

    /* Returns the range of Ability 2. 
     * @return                  - The range of Ability 2.
     */
    public int GetAbility2Range() {
        return 2;
    }
    /* Returns the range of Ability 3. 
     * @return                  - The range of Ability 3.
     */
    public int GetAbility3Range() {
        return 1;
    }

    /*
     * Returns the name of the Paladin.
     *
     * @return                  - The name of the Paladin.
     */
    public String GetName() {
        return "Paladin";
    }

    /* 
     * Ability 1 - Holy Strike: deals damage to an enemy within range.
     * Costs 2 magic.
     *
     * @param context           - ActionContext containing the target enemy and the grid.
     * @return                  - True if damage succeeded.
     */
    public boolean Ability1(ActionContext context) {
        if (context == null) {
            return false;
        }
        Character enemy = context.GetTarget();
        if (enemy == null) {
            return false;
        }
        if (!CheckConditions(2, GetAbility1Range(), enemy)) {
            return false;
        }
        if (enemy.GetTeam() == this.GetTeam()) {
            return false;
        }

        enemy.SetCurrHealth(enemy.GetCurrHealth() - 10);

        this.SetRawStats(INTLPOS, this.GetRawStats()[INTLPOS] + 1);
        this.ScaleStats();

        this.SetCurrMagic(this.GetCurrMagic() - 2);
        return true;
    }

    /* 
     * Ability 2 - Heal: restores health to the Paladin.
     * Costs 4 magic.
     *
     * @param context           - ActionContext (unused; self-targeted ability).
     * @return                  - True if healing succeeded.
     */
    public boolean Ability2(ActionContext context) {
        if (!CheckConditions(4)) {
            return false;
        }
        if (this.GetCurrHealth() >= this.GetMaxHealth()) {
            return false;
        }

        this.SetCurrHealth(Math.min(this.GetMaxHealth(), this.GetCurrHealth() + 20));
        this.SetCurrMagic(this.GetCurrMagic() - 4);
        return true;
    }

    /* 
     * Ability 3 - Divine Strike: deals damage to an enemy within range.
     * Costs 2 magic.
     *
     * @param context           - ActionContext containing the target enemy and the grid.
     * @return                  - True if damage succeeded.
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