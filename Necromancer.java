/****************************************************
 * Necromancer
 *
 * The necromancer class that stores the abilities and their configurations to be used in the game.
 * First applies new modifiers to the base characters stats.
 * Next runs this.ScaleStats(), running pre-made formulae to set the characters proper stats.
 * Stats are converted from stat points to true stats.
 * (E.G. 10 hlt points -> 90 health.)
 * 
 * Ability 1        :Summons a mindless minion
 * Ability 2        :Buff the minion, make them do more damage.
 * Ability 3        :Sacrafice a minion for a powerful melee attack.
 * 
 * Author: Leo & Lucas
 * Date: 20/05/26
 * **************************************************/
public class Necromancer extends Character {
    public Necromancer(Character character, int team) {
        super();
        this.SetName("Necromancer");
        this.SetFullName(character.GetFullName());
        this.SetTeam(team);
        this.SetStatMods(SPDPOS, -1);
        ;
        this.SetStatMods(INTLPOS, 3);
        this.SetStatMods(ATKPOS, 1);
        this.SetStatMods(MGCPOS, 2);
        this.SetStatMods(HLTPOS, 1);
        this.SetStatMods(SPPPOS, 2);
        this.ApplyStats(character);
        this.ScaleStats();
    }

    public String GetName() {
        return "Necromancer";
    }

    public boolean CheckAbility1Possible(GameSystem gs) {
        if (CheckSurroundingsContain(gs, NONE, 1) && GetCurrMagic() > 4) {
            return true;
        }
        return false;
    }

    public boolean CheckAbility2Possible(GameSystem gs) {
        if (CheckSurroundingsContain(gs, MINION, 1)) {
            return true;
        }
        return false;
    }

    public boolean CheckAbility3Possible(GameSystem gs) {
        if (CheckSurroundingsContain(gs, MINION, 2) && CheckSurroundingsContain(gs, CHARACTER, 2)) {
            return true;
        }
        return false;
    }

    public boolean Ability1(ActionContext context) {
        if (!CheckConditions(5)) {
            return false;
        }

        int targetX = context.GetPosX();
        int targetY = context.GetPosY();
        Block[][] grid = context.GetGrid();

        // Safely make sure coordinates fall inside bounds and target is an empty tile space
        if (grid == null || targetY < 0 || targetY >= grid.length || targetX < 0 || targetX >= grid[0].length) {
            return false;
        }

        Block targetBlock = grid[targetY][targetX];
        if (targetBlock == null || targetBlock.GetEntity() == null
                || targetBlock.GetEntity().GetObject() != Entity.NONE) {
            return false;
        }

        // Calculate manual spatial distance check since there is no target entity object to pass to CheckRange
        int[] myPos = this.GetPosition();
        int distX = Math.abs(myPos[0] - targetY); // array layout expects Y at position 0
        int distY = Math.abs(myPos[1] - targetX); // array layout expects X at position 1

        if (Math.max(distX, distY) <= 1) {
            // Place minion entity inside the existing block structure instead of replacing the block instance
            Minion newMinion = new Minion(this.GetTeam());
            targetBlock.SetEntity(newMinion);

            // Remove magic points
            this.SetCurrMagic(this.GetCurrMagic() - 5);
            return true;
        }
        return false;
    }

    public boolean Ability2(ActionContext context) {
        if (context == null || context.GetTargetEntity() == null) {
            return false;
        }
        if (!CheckConditions(2, 1, context.GetTargetEntity())) {
            return false;
        }

        if (context.GetTargetEntity().GetObject() == Entity.MINION) {
            Minion target = context.GetTargetEntity().minion;
            if (target != null) {
                target.Buff();
                this.SetCurrMagic(this.GetCurrMagic() - 2);
                return true;
            }
        }
        return false;
    }

    public boolean Ability3(ActionContext context) {
        if (context == null || context.GetGrid() == null) {
            return false;
        }

        int targetX = context.GetPosX();
        int targetY = context.GetPosY();
        Block[][] grid = context.GetGrid();

        if (targetY < 0 || targetY >= grid.length || targetX < 0 || targetX >= grid[0].length) {
            return false;
        }

        Block targetBlock = grid[targetY][targetX];
        if (targetBlock == null || targetBlock.GetEntity() == null) {
            return false;
        }

        if (!CheckConditions(4, 2, targetBlock.GetEntity())) {
            return false;
        }

        Entity targetEntity = targetBlock.GetEntity();
        if (targetEntity.GetObject() == Entity.CHARACTER && targetEntity.GetTeam() != this.GetTeam()) {
            Character enemy = (Character) targetEntity;
            enemy.SetCurrHealth(enemy.GetCurrHealth() - 20);
            this.SetCurrMagic(this.GetCurrMagic() - 4);
            return true;
        }
        return false;
    }
}