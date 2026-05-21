/****************************************************
 * Barbarian
 *
 * The barbarian class that stores the abilities and their configurations to be used in the game.
 * First applies new modifiers to the base characters stats.
 * Next runs this.ScaleStats(), running pre-made formulae to set the characters proper stats.
 * Stats are converted from stat points to true stats.
 * (E.G. 10 hlt points -> 90 health.)
 * 
 * Ability 1 : Flip an enemy onto the opposite side of yourself.
 * Ability 2 : Kickpunch an enemy for massive damage.
 * Ability 3 : Infect yourself with lupus and deal extra damage.
 * 
 * Author: Leo & Lucas
 * Date: 20/05/26
 * **************************************************/
public class Barbarian extends Character {

    /*
     * Constructs a Barbarian from a base Character.
     *
     * @param character - The base Character object used to initialise the Barbarian.
     * @param team      - The team ID assigned to this Barbarian.
     */
    public Barbarian(Character character, int team) {
        super();
        this.SetName("Barbarian");
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

    /*
     * Checks if Ability 1 (Flip) is possible.
     * Requires: enemy character within range 1 and sufficient magic.
     *
     * @param gs - The Game System.
     * @return   - True if the ability can be performed.
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

    /*
     * Checks if Ability 2 (Kickpunch) is possible.
     * Requires: enemy character within range 1 and sufficient magic.
     *
     * @param gs - The Game System.
     * @return   - True if the ability can be performed.
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
                        if (target != null && CheckConditions(1, GetAbility2Range(), target)
                                && target.GetTeam() != this.GetTeam()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /*
     * Checks if Ability 3 (Lupus) is possible.
     * Requires: sufficient magic and enough health to survive the self-damage.
     *
     * @param gs - The Game System.
     * @return   - True if the ability can be performed.
     */
    public boolean CheckAbility3Possible(GameSystem gs) {
        if (gs == null || gs.GameBoard == null) {
            return false;
        }
        return CheckConditions(2);
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

    /*
     * Ability 1 - Flip: picks up an enemy and throws them to the tile directly
     * opposite the Barbarian's position. Fails if the landing tile is occupied
     * or out of bounds.
     *
     * @param context - ActionContext containing the target Character and the grid.
     * @return        - True if the flip succeeded.
     */
    public boolean Ability1(ActionContext context) {
        if (context == null) {
            return false;
        }
        Character target = context.GetTarget();
        Block[][] grid   = context.GetGrid();

        if (target == null || grid == null) {
            return false;
        }
        if (!CheckConditions(2, GetAbility1Range(), target)) {
            return false;
        }
        if (target.GetTeam() == this.GetTeam()) {
            return false;
        }

        applyPassive();

        int myY  = this.GetPosition()[0];
        int myX  = this.GetPosition()[1];
        int tarY = target.GetPosition()[0];
        int tarX = target.GetPosition()[1];

        // Land position is the mirror of the target through the Barbarian
        int flipY = myY + (myY - tarY);
        int flipX = myX + (myX - tarX);

        if (flipY < 0 || flipY >= grid.length || flipX < 0 || flipX >= grid[0].length) {
            return false;
        }
        if (grid[flipY][flipX].GetEntity() != null
                && grid[flipY][flipX].GetEntity().GetObject() != Entity.NONE) {
            return false;
        }

        // Move target to the flip position
        grid[flipY][flipX].SetEntity(target);
        target.GetPosition()[0] = flipY;
        target.GetPosition()[1] = flipX;

        // Clear the tile the target was on
        grid[tarY][tarX].SetEntity(new Entity("", 0));

        this.SetCurrMagic(this.GetCurrMagic() - 2);
        return true;
    }

    /*
     * Ability 2 - Kickpunch: a ferocious multi-hit attack. Each hit is independent
     * so blocks and parries can be considered per hit, making a full block unlikely.
     *
     * @param context - ActionContext containing the target Character and the grid.
     * @return        - True if the attack landed.
     */
    public boolean Ability2(ActionContext context) {
        if (context == null) {
            return false;
        }
        Character target = context.GetTarget();
        if (target == null) {
            return false;
        }
        if (!CheckConditions(1, GetAbility2Range(), target)) {
            return false;
        }
        if (target.GetTeam() == this.GetTeam()) {
            return false;
        }

        applyPassive();

        // Damage is applied using the current (passive-boosted) attack stat
        double damage = this.GetCalculatedStats()[ATKPOS] * 4;
        target.SetCurrHealth(target.GetCurrHealth() - damage);

        this.SetCurrMagic(this.GetCurrMagic() - 1);
        return true;
    }

    /*
     * Ability 3 - Lupus: the Barbarian infects himself, sacrificing 20% of his max
     * health in exchange for a permanent 15% attack increase. Fails if the
     * self-damage would kill him.
     *
     * @param context - ActionContext (unused but required for consistent interface).
     * @return        - True if Lupus was successfully cast.
     */
    public boolean Ability3(ActionContext context) {
        if (!CheckConditions(2)) {
            return false;
        }

        applyPassive();

        double sacrifice = 0.2 * this.GetMaxHealth();
        if (this.GetCurrHealth() <= sacrifice) {
            // Not enough health — would kill the Barbarian
            return false;
        }

        this.SetCurrHealth(this.GetCurrHealth() - sacrifice);
        this.SetCalculatedStats(ATKPOS, this.GetCalculatedStats()[ATKPOS] * 1.15);
        this.SetCurrMagic(this.GetCurrMagic() - 2);
        return true;
    }

    /*
     * Passive - Rage: the Barbarian deals more damage the lower his health is.
     * At full health the multiplier is 1.0×; at 0% health it peaks near 1.5×.
     * Applied once at the start of each ability so the bonus is consistent.
     */
    private void applyPassive() {
        double maxHP         = this.GetMaxHealth();
        double missingRatio  = (maxHP - this.GetCurrHealth()) / maxHP;
        double multiplier    = 1.0 + (0.5 * missingRatio);
        this.SetCalculatedStats(ATKPOS, this.GetCalculatedStats()[ATKPOS] * multiplier);
    }
}