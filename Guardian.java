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
 * Ability 2        :Give yourself a massive amount of maximum hp.
 * Ability 3        :Heal yourself.
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

    public boolean CheckAbility1Possible(GameSystem gs) {
        if (this.GetCurrMagic() - 2 >= 0 && this.CheckSurroundingsContain(gs, Entity.OBSTACLE, 8)) {
            return true;
        }
        return false;
    }

    public boolean CheckAbility2Possible(GameSystem gs) {
        if (this.GetCurrMagic() - 1 >= 0) {
            return true;
        }
        return false;
    }

    public boolean CheckAbility3Possible(GameSystem gs) {
        if (this.GetCurrMagic() - 1 >= 0) {
            return true;
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
        if (!CheckConditions(1)) {
            return false;
        }
        if (1.1 * this.GetCurrHealth() <= this.GetMaxHealth()) {
            this.SetCurrHealth(this.GetCurrHealth() * 1.1);
            this.SetCurrMagic(this.GetCurrMagic() - 1);
            return true;
        } else {
            return false;
        }
    }

}
