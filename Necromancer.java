/****************************************************
 * Necromancer
 *
 * The necromancer class that stores the abilities and their configurations to be used in the game.
 * First applies new modifiers to the base characters stats.
 * Next runs this.ScaleStats(), running pre-made formulae to set the characters proper stats.
 * Stats are converted from stat points to true stats.
 * (E.G. 10 hlt points -> 90 health.)
 * 
 * Ability 1 : Summon a mindless minion on an adjacent empty tile.
 * Ability 2 : Buff an adjacent minion, increasing its damage and health.
 * Ability 3 : Sacrifice an adjacent minion to deal damage to a nearby enemy.
 * 
 * Author: Leo & Lucas
 * Date: 20/05/26
 * **************************************************/
public class Necromancer extends Character {

    /*
     * Constructs a Necromancer from a base Character.
     *
     * @param character - The base Character object used to initialise the Necromancer.
     * @param team      - The team ID assigned to this Necromancer.
     */
    public Necromancer(Character character, int team) {
        super();
        this.SetName("Necromancer");
        this.SetFullName(character.GetFullName());
        this.SetTeam(team);
        this.SetStatMods(SPDPOS,  -1);
        this.SetStatMods(INTLPOS,  3);
        this.SetStatMods(ATKPOS,   1);
        this.SetStatMods(MGCPOS,   2);
        this.SetStatMods(HLTPOS,   1);
        this.SetStatMods(SPPPOS,   2);
        this.ApplyStats(character);
        this.ScaleStats();
    }

    /*
     * Returns the name of the Necromancer.
     *
     * @return - The name of the Necromancer.
     */
    public String GetName() {
        return "Necromancer";
    }

    /* Checks if ability 1 should be displayed and/or possible to perform.
     
    Is calculated differently for each ability.
    This checks: if there is an empty space within range.
    if the character has sufficient magic amount.
    @param gs                - The Game System that contains the grid and all of the entities.*
    @return                  - Returns true or false depending on whether or not the ability may or may not be performed.*/
    public boolean CheckAbility1Possible(GameSystem gs) {
        if (gs == null || gs.GameBoard == null) {
            return false;
        }
        return CheckConditions(5) && CheckSurroundingsContain(gs, NONE, 1);
    }

    /* Checks if ability 2 should be displayed and/or possible to perform.
     
    Is calculated differently for each ability.
    This checks: if there is a minion within range.
    if the character has sufficient magic amount.
    @param gs                - The Game System that contains the grid and all of the entities.*
    @return                  - Returns true or false depending on whether or not the ability may or may not be performed.*/
    public boolean CheckAbility2Possible(GameSystem gs) {
        if (gs == null || gs.GameBoard == null) {
            return false;
        }
        return CheckConditions(2) && CheckSurroundingsContain(gs, MINION, 1);
    }

    /* Checks if ability 3 should be displayed and/or possible to perform.
     
    Is calculated differently for each ability.
    This checks: if there is a minion within range.
    if there is a character within range.
    if the character has sufficient magic amount.
    if the character found within range is not in the same team.
    @param gs                - The Game System that contains the grid and all of the entities.*
    @return                  - Returns true or false depending on whether or not the ability may or may not be performed.*/
    public boolean CheckAbility3Possible(GameSystem gs) {
        if (gs == null || gs.GameBoard == null) {
            return false;
        }
        return CheckSurroundingsContain(gs, MINION, 2)
                && CheckSurroundingsContain(gs, CHARACTER, 2);
    }

    /*
     * Ability 1 - Summon Minion: places a new minion on the chosen adjacent empty
     * tile. Costs 5 magic.
     *
     * @param context - ActionContext containing the target grid coordinates.
     * @return        - True if the minion was summoned successfully.
     */
    public boolean Ability1(ActionContext context) {
        if (context == null) {
            return false;
        }
        if (!CheckConditions(5)) {
            return false;
        }

        int targetX    = context.GetPosX();
        int targetY    = context.GetPosY();
        Block[][] grid = context.GetGrid();

        if (grid == null || targetY < 0 || targetY >= grid.length
                || targetX < 0 || targetX >= grid[0].length) {
            return false;
        }

        Block targetBlock = grid[targetY][targetX];
        if (targetBlock == null || targetBlock.GetEntity() == null
                || targetBlock.GetEntity().GetObject() != Entity.NONE) {
            return false;
        }

        // Chebyshev range check — position[0]=Y, position[1]=X
        int[] myPos  = this.GetPosition();
        int distRow  = Math.abs(myPos[0] - targetY);
        int distCol  = Math.abs(myPos[1] - targetX);

        if (Math.max(distRow, distCol) > 1) {
            return false;
        }

        Minion newMinion = new Minion(this.GetTeam());
        newMinion.GetPosition()[0] = targetY;
        newMinion.GetPosition()[1] = targetX;
        targetBlock.SetEntity(newMinion);

        this.SetCurrMagic(this.GetCurrMagic() - 5);
        return true;
    }

    /*
     * Ability 2 - Buff Minion: permanently buffs an adjacent allied minion, increasing its 
     * damage and health by 10 each. This is done by calling the Minion's Buff() method
     * Costs 2 magic.
     *
     * @param context - ActionContext.
     * @return        - True if buff succeeded.
     */
    public boolean Ability2(ActionContext context) {
        if (context == null || context.GetTargetEntity() == null) {
            return false;
        }
        Entity targetEntity = context.GetTargetEntity();
        if (!CheckConditions(2, 1, targetEntity)) {
            return false;
        }
        if (targetEntity.GetObject() != Entity.MINION) {
            return false;
        }

        Minion target = targetEntity.GetMinion();
        if (target == null) {
            return false;
        }

        target.Buff();
        this.SetCurrMagic(this.GetCurrMagic() - 2);
        return true;
    }

    /* 
     * Ability 3 - Sacrifice: sacrifices an adjacent allied minion to deal damage to an enemy.
     * Costs 4 magic.
     *
     * @param context - ActionContext containing the target enemy and the grid.
     * @return        - True if sacrifice and damage succeeded.
     */
    public boolean Ability3(ActionContext context) {
        if (context == null || context.GetGrid() == null) {
            return false;
        }

        Character enemy = context.GetTarget();
        Block[][] grid  = context.GetGrid();

        if (enemy == null) {
            return false;
        }
        if (!CheckConditions(4, 2, enemy)) {
            return false;
        }
        if (enemy.GetTeam() == this.GetTeam()) {
            return false;
        }

        // Find the first adjacent allied minion to sacrifice
        int[] myPos    = this.GetPosition();
        int myRow      = myPos[0];
        int myCol      = myPos[1];
        Block minionBlock = null;

        for (int row = myRow - 2; row <= myRow + 2; row++) {
            for (int col = myCol - 2; col <= myCol + 2; col++) {
                if (row == myRow && col == myCol) {
                    // skip own tile
                } else if (row >= 0 && row < grid.length && col >= 0 && col < grid[0].length) {
                    Block b = grid[row][col];
                    if (b != null && b.GetEntity() != null
                            && b.GetEntity().GetObject() == Entity.MINION
                            && b.GetEntity().GetTeam() == this.GetTeam()) {
                        minionBlock = b;
                    }
                }
            }
        }

        if (minionBlock == null) {
            return false;
        }

        // Sacrifice the minion — clear its tile
        minionBlock.SetEntity(new Entity("", 0));

        // Deal the damage
        enemy.SetCurrHealth(enemy.GetCurrHealth() - 20);
        this.SetCurrMagic(this.GetCurrMagic() - 4);
        return true;
    }
}