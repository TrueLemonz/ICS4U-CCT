public class Barbarian extends Character {
    public Barbarian() {
        super();
        this.spdMod = 2;
        this.intlMod = -1;
        this.atkMod = 8;
        this.sprMod = 1;
        this.hltMod = -2;
        this.sppMod = 0;
    }
    public Barbarian(int spd, int intl, int atk, int spr, int hlt, int spp) {
        super();
        this.spdMod = 2;
        this.intlMod = -1;
        this.atkMod = 8;
        this.sprMod = 1;
        this.hltMod = -2;
        this.sppMod = 0;
        this.spd += this.spdMod ;
        this.intl += this.intlMod;
        this.atk += this.atkMod;
        this.spr += this.sprMod;
        this.hlt += this.hltMod;
        this.spp += this.sppMod;
    }
    // Picks up character, throws them behind
    public boolean Special(Character target, Block[][] grid) {
        if (CheckRange(1, target)) {
            int[] targetPos = target.GetPosition();
            int[] myPos = this.GetPosition();
        
            // Performs special vertically
            // Checks that the barbarian is not at the top or bottom of the grid, or he'll throw the target out of the grid
            if (this.GetPosition()[1] == 0 || this.GetPosition()[1] == grid.length - 1) {
            return false;
            }
            else {
                // target is above barbarian
                if (targetPos[0] == myPos[0] && targetPos[1] == myPos[1] + 1) {
                grid[myPos[0]][myPos[1] - 2].SetEntity(target);
                grid[myPos[0]][myPos[1]].SetEntity(null);
                target.position[0] = myPos[0];
                target.position[1] = myPos[1] - 2;
                return true;
                }
                // target is below barbarian
                else if (targetPos[0] == myPos[0] && targetPos[1] == myPos[1] - 1) {
                grid[myPos[0]][myPos[1] + 2].SetEntity(target);
                grid[myPos[0]][myPos[1]].SetEntity(null);
                target.position[0] = myPos[0];
                target.position[1] = myPos[1] + 2;
                return true;
                }
            }
            // Performs special horizontally  
            // Checks that the barbarian is not at the extreme left or right of the grid or he'll throw the target out of the grid
            if ( this.GetPosition()[0] == 0 || this.GetPosition()[0] == grid[0].length - 1) {
                return false;
            }
            else {
                // target is to the left of barbarian
                if (targetPos[0] == myPos[0] - 1 && targetPos[1] == myPos[1]) {
                    grid[myPos[0] + 2][myPos[1]].SetEntity(target);
                    grid[myPos[0]][myPos[1]].SetEntity(null);
                    target.position[0] = myPos[0] + 2;
                    target.position[1] = myPos[1];
                    return true;
                }
                // target is to the right of barbarian
                else if (targetPos[0] == myPos[0] + 1 && targetPos[1] == myPos[1]) {
                    grid[myPos[0] - 2][myPos[1]].SetEntity(target);
                    grid[myPos[0]][myPos[1]].SetEntity(null);
                    target.position[0] = myPos[0] - 2;
                    target.position[1] = myPos[1];
                    return true;
                }
            }
            // Performs special diagonally
            // Checks both vertical and horizontal conditions
            if ( this.GetPosition()[0] == 0 || this.GetPosition()[0] == grid[0].length - 1 
                 || this.GetPosition()[1] == 0 || this.GetPosition()[1] == grid.length - 1 ) {
                    return false;
                 }
                 else {
                    if (targetPos[0] == myPos[0] - 1 && targetPos[1] == myPos[1] - 1) {
                        grid[targetPos[0] + 2][targetPos[1] + 2].SetEntity(target);
                        grid[targetPos[0]][targetPos[1]].SetEntity(null);
                        target.position[0] = targetPos[0] + 2;
                        target.position[1] = targetPos[1] + 2;
                        return true;
                    }
                    else if (targetPos[0] == myPos[0] - 1 && targetPos[1] == myPos[1] + 1) {
                        grid[targetPos[0] + 2][targetPos[1] - 2].SetEntity(target);
                        grid[targetPos[0]][targetPos[1]].SetEntity(null);
                        target.position[0] = targetPos[0] + 2;
                        target.position[1] = targetPos[1] - 2;
                        return true;
                    }
                    else if (targetPos[0] == myPos[0] + 1 && targetPos[1] == myPos[1] - 1) {
                        grid[targetPos[0] - 2][targetPos[1] + 2].SetEntity(target);
                        grid[targetPos[0]][targetPos[1]].SetEntity(null);
                        target.position[0] = targetPos[0] - 2;
                        target.position[1] = targetPos[1] + 2;
                        return true;
                    }
                    else if (targetPos[0] == myPos[0] + 1 && targetPos[1] == myPos[1] + 1) {
                        grid[targetPos[0] - 2][targetPos[1] - 2].SetEntity(target);
                        grid[targetPos[0]][targetPos[1]].SetEntity(null);
                        target.position[0] = targetPos[0] - 2;
                        target.position[1] = targetPos[1] - 2;
                        return true;
                    }
                 }
        }
        this.intl -= 4;
        ApplyStats();
        return false;
    }
    public boolean Ability1 (Character target, Block[][] grid) {
        if ( CheckRange(1, target) ) {
            target.SetHealth(target.GetHealth() - this.attack * 4);
            return true;
        }
        return false;
    }
}