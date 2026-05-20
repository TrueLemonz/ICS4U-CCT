/****************************************************
 * Paladin
 *
 * The paladin class that stores the abilities and their configurations to be used in the game.
 * First applies new modifiers to the base characters stats.
 * Next runs this.ScaleStats(), running pre-made formulae to set the characters proper stats.
 * Stats are converted from stat points to true stats.
 * (E.G. 10 hlt points -> 90 health.)
 * 
 * Ability 1        :Attack that buffs your intelligence.
 * Ability 2        :Heal self.
 * Ability 3        :Close range strong melee attack.
 * 
 * Author: Leo & Lucas
 * Date: 20/05/26
 * **************************************************/
public class Paladin extends Character {

    /*
     * Initalizes a Paladin from a base character.
     * 
     * @param character - The character that contains the base stats to be modified.
     * 
     * @param team - The team to place the character in. Used to tell who takes damage from who.
     */
    public Paladin(Character character, int team) {
        super();
        this.SetName("Paladin");
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

        // Loop through the map to see if an enemy is in range for the attack
        for (int i = 0; i < gs.GameBoard.length; i++) {
            for (int j = 0; j < gs.GameBoard[i].length; j++) {
                if (gs.GameBoard[i][j] != null && gs.GameBoard[i][j].GetEntity() != null) {
                    Entity entity = gs.GameBoard[i][j].GetEntity();
                    if (entity.GetObject() == Entity.CHARACTER) {
                        Character target = (Character) entity;
                        // requires cost 2, range 1, and must be an enemy unit
                        if (CheckConditions(2, GetAbility1Range(), target) && target.GetTeam() != this.GetTeam()) {
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

        // Loop through map to locate a friendly teammate to heal
        for (int i = 0; i < gs.GameBoard.length; i++) {
            for (int j = 0; j < gs.GameBoard[i].length; j++) {
                if (gs.GameBoard[i][j] != null && gs.GameBoard[i][j].GetEntity() != null) {
                    Entity entity = gs.GameBoard[i][j].GetEntity();
                    if (entity.GetObject() == Entity.CHARACTER) {
                        Character target = (Character) entity;
                        // requires cost 2, range 2, and must be on your team
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

        // Loop through map to see if an enemy is close enough for a strike
        for (int i = 0; i < gs.GameBoard.length; i++) {
            for (int j = 0; j < gs.GameBoard[i].length; j++) {
                if (gs.GameBoard[i][j] != null && gs.GameBoard[i][j].GetEntity() != null) {
                    Entity entity = gs.GameBoard[i][j].GetEntity();
                    if (entity.GetObject() == Entity.CHARACTER) {
                        Character target = (Character) entity;
                        // matching the hardcoded range 1 parameter from your Ability3 code block
                        if (CheckConditions(2, 1, target) && target.GetTeam() != this.GetTeam()) {
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
        return "Paladin";
    }
    

    public boolean Ability1(ActionContext context) {
        // if target is null, or conditions fail, or target is an ally (same team), fail the attack
        if (context.GetTarget() == null || !CheckConditions(2, 1, context.GetTarget()) || context.GetTarget().GetTeam() == this.GetTeam()) {
            return false;
        }
        Character enemy = context.GetTarget();
        if (enemy != null) {
            enemy.SetCurrHealth(enemy.GetCurrHealth() - 10);
            this.SetRawStats(INTLPOS, (this.GetRawStats()[Character.INTLPOS] + 1));
            this.ScaleStats();
            return true;
        }
        return false;
    }

    public boolean Ability2(ActionContext context) {
        if (CheckConditions(4)) {
            return true;
        }else {
            return false;
        }
    }

    public boolean Ability3(ActionContext context) {
        if (context.GetTarget() == null || !CheckConditions(2, 1, context.GetTarget())) {
            return false;
        }
        Character target = context.GetTarget();
        if (target != null) {
            target.SetCurrHealth(target.GetCurrHealth() - 5);
            return true;
        }
        return false;
    }
}
//Does this work