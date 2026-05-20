/****************************************************
 * Guardian
 *
 * The guardian class that stores the abilities and their configurations to be used in the game.
 * First applies new modifiers to the base characters stats.
 * Next runs this.ScaleStats(), running pre-made formulae to set the characters proper stats.
 * Stats are converted from stat points to true stats.
 * (E.G. 10 hlt points -> 90 health.)
 * 
 * Ability 1        :Create an obstacle in an adjacent slot.
 * Ability 2        :Give yourself a massive amount of maximum hp + small heal.
 * Ability 3        :Slam adjacent enemy, 50% stun chance.
 * 
 * Author: Leo & Lucas
 * Date: 20/05/26
 * **************************************************/
public class Guardian extends Character {
    public Guardian(Character character, int team) {
        super();

        this.SetName("Guardian");
        this.SetFullName(character.GetFullName());
        this.SetTeam(team);
        this.SetStatMods(SPDPOS, 2);
        this.SetStatMods(INTLPOS, 4);
        this.SetStatMods(ATKPOS, 0);
        this.SetStatMods(MGCPOS, 1);
        this.SetStatMods(HLTPOS, 5);
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
        if (CheckConditions(4)) {
            return true;
        }
        else {
            return false;
        }
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
        if (CheckConditions(3)) {
            return true;
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
                        if (CheckConditions(2, 2, target) && target.GetTeam() != this.GetTeam()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public String GetName() {
        return "Guardian";
    }

    public boolean Ability1(ActionContext context) {
        if (!CheckConditions(2)) {
            return false;
        }

        int targetX = context.GetPosX();
        int targetY = context.GetPosY();
        Block[][] grid = context.GetGrid();

        if (grid == null || targetY < 0 || targetY >= grid.length || targetX < 0 || targetX >= grid[0].length) {
            return false;
        }

        Block targetBlock = grid[targetY][targetX];
        if (targetBlock == null || targetBlock.GetEntity() == null || targetBlock.GetEntity().GetObject() != Entity.NONE) {
            return false;
        }

        // check manually if target block is within range of 2 tiles
        int[] myPos = this.GetPosition();
        int distX = Math.abs(myPos[0] - targetY);
        int distY = Math.abs(myPos[1] - targetX);

        if (Math.max(distX, distY) <= 2) {
            targetBlock.SetEntity(new Obstacle());
            this.SetCurrMagic(this.GetCurrMagic() - 2);
            return true;
        }
        return false;
    }

    // had to change from void to boolean because I simplified the methods earlier
    public boolean Ability2(ActionContext context) {
        if (!CheckConditions(1)) {
            return false;
        }
        this.SetRawStats(HLTPOS, (this.GetRawStats()[HLTPOS] + 1));
        this.SetRawStats(INTLPOS, (this.GetRawStats()[INTLPOS] + 1));
        this.ScaleStats();
        this.SetCurrMagic(this.GetCurrMagic() - 1);
        return true;
    }

    public boolean Ability3(ActionContext context) {
        if (!CheckConditions(2, 2, context.GetTarget())) {
            return false;
        }

        Character target = context.GetTarget();
        if (target.GetTeam() != this.GetTeam()) {
            target.SetCurrHealth(Math.max(0, target.GetCurrHealth() - 2));
            double rand = Math.random();
            if (rand < 0.5) {
                target.SetIsStunned(true);
            }
            this.SetCurrMagic(this.GetCurrMagic() - 2);
            return true;
        }
        return false;
    }

}
//Does this work