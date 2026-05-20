/* 
 * The paladin class that stores the abilities and their configurations to be used in the game.
 * First applies new modifiers to the base characters stats.
 * Next runs this.ScaleStats(), running pre-made formulae to set the characters proper stats.
 * Stats are converted from stat points to true stats.
 * (E.G. 10 hlt points -> 90 health.)
 */
public class Paladin extends Character {

    /*
     * Initalizes a Paladin from a base character.
     * 
     * @param character - The character that contains the base stats to be modified.
     * 
     * @param team - The team to place the character in. Used to tell who takes
     * damage.
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

    public boolean CheckAbility1Possible(GameSystem gs) {
        return false;
    }

    public boolean CheckAbility2Possible(GameSystem gs) {
        return false;
    }

    public boolean CheckAbility3Possible(GameSystem gs) {
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
        // if target is null, or conditions fail, or target is an enemy (different team), fail the heal
        if (context.GetTarget() == null || !CheckConditions(2, 2, context.GetTarget()) || context.GetTarget().GetTeam() != this.GetTeam()) {
            return false;
        }
        Character ally = context.GetTarget();
        if (ally.GetCurrHealth() + 10 <= ally.GetMaxHealth()) {
            ally.SetCurrHealth(ally.GetCurrHealth() + 10);
            return true;
        }
        return false;
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