/****************************************************
 * Character
 * 
 * The character class directly branches off the entity class.
 * The connections to entity allow it to be seamlessly
 * displayed and stored on the game system's grid.
 * Stores a characters stats, raw and calculated.
 * 
 * Author: Leo & Lucas
 * Date: 20/05/26
 **************************************************/
public class Character extends Entity {

    // These stats hold their respective calculated values
    private double speed, intelligence, attack, magic, health, spellpower;
    // These stats are simple integers which will later be scaled to actual values
    private int spd, intl, atk, mgc, hlt, spp;
    // These are the modifiers that each character has, specific to itself.
    private int spdMod, intlMod, atkMod, mgcMod, hltMod, sppMod;
    private double currHealth;
    private double currMagic;
    private boolean isAlive = true;
    private boolean isStunned;

    // Flag so ScaleStats only initialises currHealth/currMagic once.
    // After that it preserves the in-fight values.
    private boolean statsInitialised = false;

    public final static int SPDPOS = 0;
    public final static int INTLPOS = 1;
    public final static int ATKPOS = 2;
    public final static int MGCPOS = 3;
    public final static int HLTPOS = 4;
    public final static int SPPPOS = 5;

    public Character() {
        super();
        this.character = this;
        this.SetObject(1);
    }

    public Character(int spd, int intl, int atk, int mgc, int hlt, int spp, boolean isStunned) {
        super();
        this.character = this;
        this.SetObject(1);
        this.spd = spd;
        this.intl = intl;
        this.atk = atk;
        this.mgc = mgc;
        this.hlt = hlt;
        this.spp = spp;
        this.isStunned = isStunned;
        // currHealth and currMagic are set properly by ScaleStats(); do not set them here.
    }

    /*
     * Creates a basic character (to be later modified) with 20 total initial stats
     * and a randomly selected name.
     * The character's 20 stat points will be randomly distributed as evenly as
     * possible.
     * The character will not recieve more than 8 points in one stat.
     * Once a character has been converted into a class, any stats of 0 will be
     * boosted to 1.
     */
    public Character GenerateCharacter() {
        String[] names = { "Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Henry", "Ivy", "Jack", "Kate",
                "Liam", "Mia", "Noah", "Olivia", "Leo", "Lucas", "Sophia", "William", "Amelia", "James", "Balthazar",
                "Cassandra", "Dorian", "Evangeline", "Felix", "Genevieve", "Hector", "Isabella", "Julian", "Katarina",
                "Lysander", "Mariana", "Nathaniel", "Ophelia", "Percival", "Quinn", "Raphael", "Seraphina", "Theodore",
                "Ulysses", "Valentina", "Xavier", "Yvonne", "Zachary", "Mikhail", "Azazel", "Bealzebub", "Lucifer",
                "Abaddon", "Leviathan", "Asmodeus", "Mammon", "Belphegor", "Samael", "Astaroth", "Baphomet",
                "Mephistopheles", "Lilith", "Nyx", "Erebus", "Thanatos", "Hypnos", "Jan", "Jonathan" };
        int nameInt = (int) (Math.random() * names.length);
        this.SetFullName(names[nameInt]);

        int points = 20;
        for (int i = 0; i < points; i++) {
            int stat = (int) (Math.random() * 6);
            if (stat == 0 && this.spd < 8) {
                this.spd++;
            } else if (stat == 1 && this.intl < 8) {
                this.intl++;
            } else if (stat == 2 && this.atk < 8) {
                this.atk++;
            } else if (stat == 3 && this.mgc < 8) {
                this.mgc++;
            } else if (stat == 4 && this.hlt < 8) {
                this.hlt++;
            } else if (stat == 5 && this.spp < 8) {
                this.spp++;
            } else {
                i--;
            }
        }
        return this;
    }

    /*
     * Scales the integer values (spd, intl, etc) to actual values (speed,
     * intelligence, etc.)
     */
    public void ScaleStats() {
        this.speed      = this.spd;
        this.intelligence = this.intl * 2.5;
        this.attack     = this.atk * 9.5;
        this.magic      = this.mgc * 2;
        this.health     = this.hlt * 9;
        this.spellpower = this.spp;

        if (!statsInitialised) {
            this.currHealth = this.health;
            this.currMagic  = this.magic;
            this.statsInitialised = true;
        }
    }

     /*
     * To be called when a character lands on a food entity.
     * Grants the character + 15 hp (if possible) and then kills the food.
     * 
     * @param food - the food entity to be destroyed.
     */
    public boolean EatFood(Food food) {
        if (food == null) {
            return false;
        }
        if (this.currHealth + 15 <= this.health) {
            food.Destroy();
            this.currHealth += 15;
            return true;
        }
        // Still eat the food but cap at max health
        food.Destroy();
        this.currHealth = this.health;
        return true;
    }

    
    /*
     * Used ONLY for initializing a character's position. Will not work in any other
     * circumstance.
     * The map uses y, x instead of the (for humans) logical x, y.
     * 
     * @param coordinates - An array of size 2 that contains the characters x and y
     * coordinate.
     * 
     * @param grid - The Game System's grid of blocks.
     * 
     * @return - True if the move was successful, False otherwise.
     */
    public boolean SetPosition(int[] coordinates, Block[][] grid) {
        int x = coordinates[0];
        int y = coordinates[1];
        if (y >= 0 && y < grid.length && x >= 0 && x < grid[0].length && grid[y][x] != null) {
            grid[y][x].SetEntity(this);
            this.position[0] = y;
            this.position[1] = x;
            return true;
        }
        return false;
    }

    /*
     * Getter function for the rendered stats of a character. (not the raw points)
     * 
     * @return - A uniformly organized array of pre-calculated stats.
     */
    public double[] GetCalculatedStats() {
        // Returns as a copy
        double[] stats = { this.speed, this.intelligence, this.attack, this.magic, this.health, this.spellpower };
        return stats;
    }

    /*
     * Setter function to manually override the calculated stats of a character.
     * 
     * @param pos - The position (positions are hard-coded) of the selected stat.
     * 
     * @param amt - What to change the selected stat to.
     */
    public void SetCalculatedStats(int pos, double amt) {
        if (pos == SPDPOS) {
            this.speed = amt;
        } else if (pos == INTLPOS) {
            this.intelligence = amt;
        } else if (pos == ATKPOS) {
            this.attack = amt;
        } else if (pos == MGCPOS) {
            this.magic = amt;
        } else if (pos == HLTPOS) {
            this.health = amt;
        } else if (pos == SPPPOS) {
            this.spellpower = amt;
        }
    }

    /*
     * Getter function for the raw points of a character. (not the rendered stats)
     * 
     * @return - A uniformly organized array of raw stats.
     */
    public int[] GetRawStats() {
        int[] stats = { this.spd, this.intl, this.atk, this.mgc, this.hlt, this.spp };
        return stats;
    }

     /*
     * Setter function to manually override the raw stats of a character.
     * 
     * @param pos - The position (positions are hard-coded) of the selected stat.
     * 
     * @param amt - What to change the selected stat to.
     */
    public void SetRawStats(int pos, double amt) {
        int val = (int) amt;
        if (pos == SPDPOS) {
            this.spd = val;
        } else if (pos == INTLPOS) {
            this.intl = val;
        } else if (pos == ATKPOS) {
            this.atk = val;
        } else if (pos == MGCPOS) {
            this.mgc = val;
        } else if (pos == HLTPOS) {
            this.hlt = val;
        } else if (pos == SPPPOS) {
            this.spp = val;
        }
    }

     /*
     * Getter function for the stat mods of a character. (to make sure everything is
     * perfectly encapsulated)
     * 
     * @return - A uniformly organized array of stat modifiers.
     */
    public int[] GetStatMods() {
        int[] stats = { this.spdMod, this.intlMod, this.atkMod, this.mgcMod, this.hltMod, this.sppMod };
        return stats;
    }

     /*
     * Setter function to manually override the raw stats of a character.
     * 
     * @param pos - The position (positions are hard-coded) of the selected stat.
     * 
     * @param amt - What to change the selected stat to.
     */
    public void SetStatMods(int pos, int amt) {
        if (pos == SPDPOS) {
            this.spdMod = amt;
        } else if (pos == INTLPOS) {
            this.intlMod = amt;
        } else if (pos == ATKPOS) {
            this.atkMod = amt;
        } else if (pos == MGCPOS) {
            this.mgcMod = amt;
        } else if (pos == HLTPOS) {
            this.hltMod = amt;
        } else if (pos == SPPPOS) {
            this.sppMod = amt;
        }
    }

    /* Simple setter to simultaneously integrate all stat modifiers provided by a class base. 
     * Enforces a minimum stat floor value of 1.
     * 
     * @param character          - The base character providing raw template stats.
     */
    public void ApplyStats(Character character) {
        int[] baseStats    = character.GetRawStats();
        int[] currentMods  = this.GetStatMods();

        int newSpd = baseStats[SPDPOS] + currentMods[SPDPOS];
        if (newSpd < 1) { newSpd = 1; }
        this.SetRawStats(SPDPOS, newSpd);

        int newIntl = baseStats[INTLPOS] + currentMods[INTLPOS];
        if (newIntl < 1) { newIntl = 1; }
        this.SetRawStats(INTLPOS, newIntl);

        int newAtk = baseStats[ATKPOS] + currentMods[ATKPOS];
        if (newAtk < 1) { newAtk = 1; }
        this.SetRawStats(ATKPOS, newAtk);

        int newMgc = baseStats[MGCPOS] + currentMods[MGCPOS];
        if (newMgc < 1) { newMgc = 1; }
        this.SetRawStats(MGCPOS, newMgc);

        int newHlt = baseStats[HLTPOS] + currentMods[HLTPOS];
        if (newHlt < 1) { newHlt = 1; }
        this.SetRawStats(HLTPOS, newHlt);

        int newSpp = baseStats[SPPPOS] + currentMods[SPPPOS];
        if (newSpp < 1) { newSpp = 1; }
        this.SetRawStats(SPPPOS, newSpp);
    }

    /*
     * Getter function for the current in-game health of a character. (not the
     * health stat)
     * This is not the health stat because the health stat remains as the maximum
     * (for regeneration or seeing damage comparisons)
     * 
     * @return - The current health of the character.
     */
    public double GetCurrHealth() {
        return this.currHealth;
    }

    /*
     * Sets current in-game health stat.
     *
     * @param currHealth - The new health amount
     */
    public void SetCurrHealth(double currHealth) {
        if (currHealth < 0) {
            this.currHealth = 0;
        } else if (currHealth > this.health) {
            this.currHealth = this.health;
        } else {
            this.currHealth = currHealth;
        }
        this.isAlive = this.currHealth > 0;
    }

    /*
     * Getter function for the current in-game magic amount of a character. (not the
     * magic stat)
     * This is not the magic stat because the magic stat remains as the maximum (for
     * regeneration or usage comparisons)
     * 
     * @return - The current magic amount of the character.
     */
    public double GetCurrMagic() {
        return this.currMagic;
    }

    /*
     * Sets current in-agame magic stat.
     *
     * @param currMagic - The new magic amount
     */
    public void SetCurrMagic(double currMagic) {
        if (currMagic < 0) {
            this.currMagic = 0;
        } else if (currMagic > this.magic) {
            this.currMagic = this.magic;
        } else {
            this.currMagic = currMagic;
        }
    }

    /*
     * Sets the characters stun state.
     *
     * @param isStunned - True to stun the character, False to remove it.
     */
    public void SetIsStunned(boolean isStunned) {
        this.isStunned = isStunned;
    }
    /*
     * Gets the characters stun state.
     *
     * @return - True if stunned, False otherwise.
     */
    public boolean GetIsStunned() {
        return this.isStunned;
    }

    /*
     * Gets the maximum total health pool of the character.
     *
     * @return - The calculated maximum health value.
     */
    public double GetMaxHealth() {
        return this.health;
    }

    /*
     * Gets the maximum total magic pool of the character.
     *
     * @return - The calculated maximum magic value.
     */
    public double GetMaxMagic() {
        return this.magic;
    }

    /*
     * Evaluates if the character is alive based on their current health.
     *
     * @return - True if current health is greater than 0, False otherwise.
     */
    public boolean GetIsAlive() {
        return this.currHealth > 0;
    }

    /*
     * Evaluates if a specified target entity falls within the characters
     * performance range using Chebyshev distance.
     * Will immediately fail if the character is currently stunned.
     *
     * @param range - The maximum range of tiles away the target can be.
     * 
     * @param target - The target entity to examine distance against.
     * 
     * @return - True if the target is in range and character isn't stunned, False
     * otherwise.
     */
    public boolean CheckRange(int range, Entity target) {
        if (this.isStunned) {
            return false;
        }
        int[] targetPos = target.GetPosition();
        int[] myPos     = this.GetPosition();

        int dy = Math.abs(targetPos[0] - myPos[0]);
        int dx = Math.abs(targetPos[1] - myPos[1]);

        return Math.max(dy, dx) <= range;
    }

    /*
     * Triggers the characters first action capability using contextual details.
     *
     * @param context - The current context tracking the targets and environment of
     * the action.
     * 
     * @return - True if the action executed successfully, False otherwise.
     */
    public boolean Ability1(ActionContext context) {
        return false;
    }

    /*
     * Triggers the characters second action capability using contextual details.
     *
     * @param context - The current context tracking the targets and environment of
     * the action.
     * 
     * @return - True if the action executed successfully, False otherwise.
     */
    public boolean Ability2(ActionContext context) {
        return false;
    }

    /*
     * Checks if casting a self-targeted or non-targeted action is viable by
     * evaluating life state, stuns, and resource costs.
     *
     * @param magic - The absolute resource amount required to deploy the action.
     * 
     * @return - True if all target-free action parameters are fully satisfied,
     * False otherwise.
     */
    public boolean CheckConditions(int magic, int range, Entity target) {
        if (target == null) {
            return false;
        }
        if (!this.isAlive || this.isStunned || this.currMagic - magic < 0 || !CheckRange(range, target)) {
            return false;
        }
        return true;
    }

    /*
     * Checks if casting a ranged ability on a target is viable by evaluating life
     * state, stuns, cost requirements, and positioning.
     *
     * @param magic - The absolute resource amount required to deploy the action.
     * 
     * @param range - The operational threshold boundary.
     * 
     * @param target - The destination target entity.
     * 
     * @return - True if all action deployment requirements are fully satisfied,
     * False otherwise.
     */
    public boolean CheckConditions(int magic) {
        if (!this.isAlive || this.isStunned || this.currMagic - magic < 0) {
            return false;
        }
        return true;
    }

    /*
     * Returns character with the most speed
     * Will put it to chance if speeds are equal
     * c1, c2 and c3 refer to character 1, etc. respectively
     * 
     * @param PlayerTeam - The array of characters containing the team to evaluate.
     * 
     * @return - The index of the character with the maximum speed.
     */
    public int GetMaxSpeedIndex(Character[] PlayerTeam) {
        int s0 = PlayerTeam[0].GetRawStats()[SPDPOS];
        int s1 = PlayerTeam[1].GetRawStats()[SPDPOS];
        int s2 = PlayerTeam[2].GetRawStats()[SPDPOS];

        // All three equal
        if (s0 == s1 && s1 == s2) {
            return (int) (Math.random() * 3);
        }
        // c0 strictly fastest
        if (s0 > s1 && s0 > s2) {
            return 0;
        }
        // c1 strictly fastest
        if (s1 > s0 && s1 > s2) {
            return 1;
        }
        // c2 strictly fastest
        if (s2 > s0 && s2 > s1) {
            return 2;
        }
        // c0 == c1 > c2
        if (s0 == s1 && s0 > s2) {
            return (int) (Math.random() * 2);  // 0 or 1
        }
        // c0 == c2 > c1
        if (s0 == s2 && s0 > s1) {
            int r = (int) (Math.random() * 2);
            return (r == 0) ? 0 : 2;
        }
        // c1 == c2 > c0
        int r = (int) (Math.random() * 2);
        return (r == 0) ? 1 : 2;
    }

    /*
     * Determines the median speed index among the remaining player characters after
     * the maximum speed index has been isolated.
     *
     * @param maxSpeedIndex - The already verified index containing the highest
     * speed statistic.
     * 
     * @param PlayerTeam - The array of characters containing the team to evaluate.
     * 
     * @return - The index matching the intermediate median speed score.
     */
    public int GetMedianSpeedIndex(int maxSpeedIndex, Character[] PlayerTeam) {
        if (maxSpeedIndex == 0) {
            if (PlayerTeam[1].GetRawStats()[SPDPOS] >= PlayerTeam[2].GetRawStats()[SPDPOS]) {
                return (PlayerTeam[1].GetRawStats()[SPDPOS] > PlayerTeam[2].GetRawStats()[SPDPOS])
                        ? 1
                        : ((int) (Math.random() * 2) == 0 ? 1 : 2);
            }
            return 2;
        } else if (maxSpeedIndex == 1) {
            if (PlayerTeam[0].GetRawStats()[SPDPOS] >= PlayerTeam[2].GetRawStats()[SPDPOS]) {
                return (PlayerTeam[0].GetRawStats()[SPDPOS] > PlayerTeam[2].GetRawStats()[SPDPOS])
                        ? 0
                        : ((int) (Math.random() * 2) == 0 ? 0 : 2);
            }
            return 2;
        } else {
            if (PlayerTeam[0].GetRawStats()[SPDPOS] >= PlayerTeam[1].GetRawStats()[SPDPOS]) {
                return (PlayerTeam[0].GetRawStats()[SPDPOS] > PlayerTeam[1].GetRawStats()[SPDPOS])
                        ? 0
                        : ((int) (Math.random() * 2) == 0 ? 0 : 1);
            }
            return 1;
        }
    }

    /*
     * Checks if there is a valid target for an ability within the specified range.
     *
     * @param gs - The current game system context containing the grid and entities.
     * 
     * @param type - The type of entity to look for (character, minion, etc.)
     * 
     * @param range - The maximum distance the target can be from the character.
     * 
     * @return - True if at least one valid target is found, False otherwise.
     */
    public boolean CheckSurroundingsContain(GameSystem gs, int type, int range) {
        int[] pos    = this.GetPosition();
        int myRow    = pos[0];  // Y / row
        int myCol    = pos[1];  // X / col

        int boardRows = gs.GameBoard.length;
        int boardCols = gs.GameBoard[0].length;

        for (int row = myRow - range; row <= myRow + range; row++) {
            for (int col = myCol - range; col <= myCol + range; col++) {
                if (row == myRow && col == myCol) {
                    // skip own cell
                } else if (row >= 0 && row < boardRows && col >= 0 && col < boardCols) {
                    if (gs.GameBoard[row][col] != null
                            && gs.GameBoard[row][col].GetEntity() != null
                            && gs.GameBoard[row][col].GetEntity().GetObject() == type) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*
     * Check if ability one is valid inside the current global game system context.
     *
     * @param gs - The primary game engine status tracker.
     * 
     * @return - True if action conditions pass validation tests, False otherwise.
     */
    public boolean CheckAbility1Possible(GameSystem gs) {
        return false;
    }

    /*
     * Check if ability two is valid inside the current game context.
     *
     * @param gs - The primary game engine status tracker.
     * @return   - True if action conditions pass validation, False otherwise.
     */
    public boolean CheckAbility2Possible(GameSystem gs) {
        return false;
    }

    /*
     * Check if ability three is valid inside the current global game system
     * context.
     *
     * @param gs - The primary game engine status tracker.
     * 
     * @return - True if action conditions pass validation tests, False otherwise.
     */
    public boolean CheckAbility3Possible(GameSystem gs) {
        return false;
    }

    /*
     * Triggers the characters third action capability using contextual details.
     *
     * @param context - The current context tracking the targets and environment of
     * the action.
     * 
     * @return - True if the action executed successfully, False otherwise.
     */
    public boolean Ability3(ActionContext context) {
        return false;
    }
}