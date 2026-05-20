/****************************************************
 * Healer
 *
 * The healer class that stores the abilities and their configurations to be used in the game.
 * First applies new modifiers to the base characters stats.
 * Next runs this.ScaleStats(), running pre-made formulae to set the characters proper stats.
 * Stats are converted from stat points to true stats.
 * (E.G. 10 hlt points -> 90 health.)
 * 
 * Ability 1        :Buff nearby allies, and damage nearby enemies.
 * Ability 2        :Heal self.
 * Ability 3        :Smite a character, with a 50% chance to paralyze.
 * 
 * Author: Leo & Lucas
 * Date: 20/05/26
 * **************************************************/
public class Healer extends Character {
    public Healer(Character character, int team) {
        super();
        this.SetName("Healer");
        this.SetFullName(character.GetFullName());
        this.SetTeam(team);
        this.SetStatMods(SPDPOS, 0);
        this.SetStatMods(INTLPOS, 3);
        this.SetStatMods(ATKPOS, -1);
        this.SetStatMods(MGCPOS, 3);
        this.SetStatMods(HLTPOS, 2);
        this.SetStatMods(SPPPOS, 1);
        this.ApplyStats(character);
        this.ScaleStats();
    }

    public boolean CheckAbility1Possible(GameSystem gs) {
        if (this.GetCurrMagic() >= 4) {
            return true;
        }
        return false;
    }

    public boolean CheckAbility2Possible(GameSystem gs) {
        if (this.GetCurrMagic() >= 3 && this.CheckSurroundingsContain(gs, Character.CHARACTER, 3)) {
            return true;
        }
        return false;
    }

    public boolean CheckAbility3Possible(GameSystem gs) {
        if (this.GetCurrMagic() >= 2 && this.CheckSurroundingsContain(gs, Character.CHARACTER, 2)) {
            return true;
        }
        return false;
    }

    public int GetAbility1Range() {
        return 1;
    }

    public int GetAbility2Range() {
        return 3;
    }

    public int GetAbility3Range() {
        return 2;
    }

    public String GetName() {
        return "Healer";
    }

    /*
     * Reads every entity on the grid and checks the range (1)
     * If entity is a character, it checks their team
     * If belonging to the same team as the healer, heals them for 5
     * If opposite team, damages them by 5
     */
    public boolean Ability1(ActionContext context) {
        if (!CheckConditions(4)) {
            return false;
        }

        Block[][] grid = context.GetGrid();
        if (grid == null) {
            return false;
        }

        boolean targetsAffected = false;
        double currentMagicPool = this.GetCurrMagic();

        // Iterate across the entire board grid layout
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null && grid[i][j].GetEntity() != null) {
                    Entity currentEntity = grid[i][j].GetEntity();

                    if (currentEntity.GetObject() == Entity.CHARACTER) {
                        Character target = (Character) currentEntity;

                        if (CheckRange(1, target)) {
                            if (target.GetTeam() == this.GetTeam()) {
                                // Heal Allies
                                double maxHealth = target.GetMaxHealth();
                                if (target.GetCurrHealth() < maxHealth) {
                                    target.SetCurrHealth(Math.min(maxHealth, target.GetCurrHealth() + 5));
                                    targetsAffected = true;
                                }
                            } else {
                                // Damage Enemies
                                target.SetCurrHealth(Math.max(0, target.GetCurrHealth() - 5));
                                targetsAffected = true;
                            }
                        }
                    }
                }
            }
        }

        if (targetsAffected) {
            this.SetCurrMagic(currentMagicPool - 4);
            return true;
        }
        return false;
    }

    // Gives teammate +4 intl and +2 mgc
    public boolean Ability2(ActionContext context) {
        if (!CheckConditions(3, 3, context.GetTarget())) {
            return false;
        }

        Character target = context.GetTarget();
        if (target.GetTeam() == this.GetTeam()) {
            target.SetRawStats(Character.INTLPOS, (target.GetRawStats()[Character.INTLPOS] + 4));
            target.SetRawStats(Character.MGCPOS, (target.GetRawStats()[Character.MGCPOS] + 2));
            target.ScaleStats();
            this.SetCurrMagic(this.GetCurrMagic() - 3);
            return true;
        }
        return false;
    }

    // Basic attack, has a 50% chance to stun the target
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
