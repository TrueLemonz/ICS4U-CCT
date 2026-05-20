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
 * Ability 1 :Buff your own .
 * Ability 2 :Give yourself a massive amount of maximum hp.
 * Ability 3 :Heal yourself.
 * 
 * Author: Leo & Lucas
 * Date: 20/05/26
 **************************************************/
public class Crusader extends Character {

    /*
     * Constructs a crusader character instance.
     * Initializes base stats and applies class-based modifications (___mod) for the
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

    public boolean CheckAbility1Possible(GameSystem gs) {
        if (gs == null || gs.GameBoard == null)
            return false;

        for (int i = 0; i < gs.GameBoard.length; i++) {
            for (int j = 0; j < gs.GameBoard[i].length; j++) {
                if (gs.GameBoard[i][j] != null && gs.GameBoard[i][j].GetEntity() != null) {
                    Entity entity = gs.GameBoard[i][j].GetEntity();
                    if (entity.GetObject() == Entity.CHARACTER) {
                        Character target = (Character) entity;
                        if (CheckConditions(2, GetAbility1Range(), target) && target.GetTeam() != this.GetTeam()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean CheckAbility2Possible(GameSystem gs) {
        if (gs == null || gs.GameBoard == null)
            return false;

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

    public boolean CheckAbility3Possible(GameSystem gs) {
        if (gs == null || gs.GameBoard == null)
            return false;

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