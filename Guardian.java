/****************************************************
 * Guardian
 *
 * The guardian class that stores the abilities and their configurations to be used in the game.
 * First applies new modifiers to the base characters stats.
 * Next runs this.ScaleStats(), running pre-made formulae to set the characters proper stats.
 * Stats are converted from stat points to true stats.
 * (E.G. 10 hlt points -> 90 health.)
 * 
 * Ability 1 : Create an obstacle on an adjacent empty tile.
 * Ability 2 : Fortify — permanently gain max health and intelligence.
 * Ability 3 : Slam an adjacent enemy; 50% stun chance.
 * 
 * Author: Leo & Lucas
 * Date: 20/05/26
 * **************************************************/
public class Guardian extends Character {

    /*
     * Constructs a Guardian from a base Character.
     *
     * @param character - The base Character object used to initialise the Guardian.
     * @param team      - The team ID assigned to this Guardian.
     */
    public Guardian(Character character, int team) {
        super();
        this.SetName("Guardian");
        this.SetFullName(character.GetFullName());
        this.SetTeam(team);
        this.SetStatMods(SPDPOS,  2);
        this.SetStatMods(INTLPOS, 4);
        this.SetStatMods(ATKPOS,  0);
        this.SetStatMods(MGCPOS,  1);
        this.SetStatMods(HLTPOS,  5);
        this.SetStatMods(SPPPOS,  0);
        this.ApplyStats(character);
        this.ScaleStats();
    }

    /*
     * Checks if Ability 1 (Obstruct) is possible.
     * Requires sufficient magic (cost 2).
     *
     * @param gs - The Game System.
     * @return   - True if the ability can be performed.
     */
    public boolean CheckAbility1Possible(GameSystem gs) {
        if (gs == null || gs.GameBoard == null) {
            return false;
        }
        return CheckConditions(2) && CheckSurroundingsContain(gs, Entity.NONE, 2);
    }

    /*
     * Checks if Ability 2 (Fortify) is possible.
     * Requires sufficient magic (cost 1).
     *
     * @param gs - The Game System.
     * @return   - True if the ability can be performed.
     */
    public boolean CheckAbility2Possible(GameSystem gs) {
        if (gs == null || gs.GameBoard == null) {
            return false;
        }
        return CheckConditions(1);
    }

    /*
     * Checks if Ability 3 (Slam) is possible.
     * Requires an enemy character within range 2 and sufficient magic (cost 2).
     *
     * @param gs - The Game System.
     * @return   - True if the ability can be performed.
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
                        if (target != null && CheckConditions(2, 2, target)
                                && target.GetTeam() != this.GetTeam()) {
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

    /*
     * Ability 1 - Obstruct: places an obstacle on a chosen empty tile within
     * range 2. Costs 2 magic.
     *
     * @param context - ActionContext containing the target grid coordinates.
     * @return        - True if the obstacle was placed successfully.
     */
    public boolean Ability1(ActionContext context) {
        if (context == null) {
            return false;
        }
        if (!CheckConditions(2)) {
            return false;
        }

        int targetX  = context.GetPosX();
        int targetY  = context.GetPosY();
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

        // Chebyshev distance check — position[0]=Y, position[1]=X
        int[] myPos = this.GetPosition();
        int distRow = Math.abs(myPos[0] - targetY);
        int distCol = Math.abs(myPos[1] - targetX);

        if (Math.max(distRow, distCol) <= 2) {
            targetBlock.SetEntity(new Obstacle());
            this.SetCurrMagic(this.GetCurrMagic() - 2);
            return true;
        }
        return false;
    }

    /*
     * Ability 2 - Fortify: the Guardian permanently increases their health and
     * intelligence raw stats, then re-scales to update maximums.
     * Costs 1 magic.
     *
     * @param context - ActionContext (unused; self-targeted ability).
     * @return        - True if fortification succeeded.
     */
    public boolean Ability2(ActionContext context) {
        if (!CheckConditions(1)) {
            return false;
        }
        this.SetRawStats(HLTPOS,  this.GetRawStats()[HLTPOS]  + 1);
        this.SetRawStats(INTLPOS, this.GetRawStats()[INTLPOS] + 1);
        this.ScaleStats();
        this.SetCurrMagic(this.GetCurrMagic() - 1);
        return true;
    }

    /*
     * Ability 3 - Slam: a powerful strike against an adjacent enemy.
     * Has a 50% chance to stun the target. Costs 2 magic.
     *
     * @param context - ActionContext containing the enemy target.
     * @return        - True if the slam landed.
     */
    public boolean Ability3(ActionContext context) {
        if (context == null) {
            return false;
        }
        Character target = context.GetTarget();
        if (target == null) {
            return false;
        }
        if (!CheckConditions(2, 2, target)) {
            return false;
        }
        if (target.GetTeam() == this.GetTeam()) {
            return false;
        }

        target.SetCurrHealth(Math.max(0, target.GetCurrHealth() - 2));

        if (Math.random() < 0.5) {
            target.SetIsStunned(true);
        }

        this.SetCurrMagic(this.GetCurrMagic() - 2);
        return true;
    }
}