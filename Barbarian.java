public class Barbarian extends Character {
    // Had to change this, Math.log only takes one variable. Must be log 10.
    private double missingHPBonus = 1.1 * Math.log (this.currHealth / this.health );
    public Barbarian(int team) {
        super();
        this.team = team;
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
    public boolean Special(ActionContext context) {
        if ( this.GetIsStunned() ) {
            return false;
        }
        if (CheckRange(1, context.getTarget())) {
            ScaleStats();
            Character target = context.getTarget();
            Block[][] grid = context.getGrid();
            int targetX = target.GetPosition()[0];
            int targetY = target.GetPosition()[1];
            int myX = this.GetPosition()[0];
            int myY = this.GetPosition()[1];

            // Performs special vertically
            // Checks that the barbarian is not at the top or bottom of the grid, or he'll throw the target out of the grid
            if (myY == 0 || myY == grid.length - 1) {
                return false;
            }
            else {
                // target is above barbarian
                if (targetX == myX && targetY == myY + 1) {
                    grid[myX][myY - 2].SetEntity(target);
                    grid[myX][myY].SetEntity(null);
                    target.position[0] = myX;
                    target.position[1] = myY - 2;
                    return true;
                }
                // target is below barbarian
                else if (targetX == myX && targetY == myY - 1) {
                    grid[myX][myY + 2].SetEntity(target);
                    grid[myX][myY].SetEntity(null);
                    target.position[0] = myX;
                    target.position[1] = myY + 2;
                    return true;
                }
            }
            // Performs special horizontally  
            // Checks that the barbarian is not at the extreme left or right of the grid or he'll throw the target out of the grid
            if (myX == 0 || myX == grid[0].length - 1) {
                return false;
            }
            else {
                // target is to the left of barbarian
                if (targetX == myX - 1 && targetY == myY) {
                    grid[myX + 2][myY].SetEntity(target);
                    grid[myX][myY].SetEntity(null);
                    target.position[0] = myX + 2;
                    target.position[1] = myY;
                    return true;
                }
                // target is to the right of barbarian
                else if (targetX == myX + 1 && targetY == myY) {
                    grid[myX - 2][myY].SetEntity(target);
                    grid[myX][myY].SetEntity(null);
                    target.position[0] = myX - 2;
                    target.position[1] = myY;
                    return true;
                }
            }
            // Performs special diagonally
            // Checks both vertical and horizontal conditions
            if (myX == 0 || myX == grid[0].length - 1 || myY == 0 || myY == grid.length - 1) {
                return false;
            }
            else {
                if (targetX == myX - 1 && targetY == myY - 1) {
                    grid[targetX + 2][targetY + 2].SetEntity(target);
                    grid[targetX][targetY].SetEntity(null);
                    target.position[0] = targetX + 2;
                    target.position[1] = targetY + 2;
                    return true;
                }
                else if (targetX == myX - 1 && targetY == myY + 1) {
                    grid[targetX + 2][targetY - 2].SetEntity(target);
                    grid[targetX][targetY].SetEntity(null);
                    target.position[0] = targetX + 2;
                    target.position[1] = targetY - 2;
                    return true;
                }
                else if (targetX == myX + 1 && targetY == myY - 1) {
                    grid[targetX - 2][targetY + 2].SetEntity(target);
                    grid[targetX][targetY].SetEntity(null);
                    target.position[0] = targetX - 2;
                    target.position[1] = targetY + 2;
                    return true;
                }
                else if (targetX == myX + 1 && targetY == myY + 1) {
                    grid[targetX - 2][targetY - 2].SetEntity(target);
                    grid[targetX][targetY].SetEntity(null);
                    target.position[0] = targetX - 2;
                    target.position[1] = targetY - 2;
                    return true;
                }
            }
        }
        this.intl -= 4;
        ScaleStats();
        return false;
    }
    // Strong attack, meant to hit multiple times so that block/parry is calculated for each hit
    // and its unlikely for the whole thing to be blocked
    public boolean Ability1 (ActionContext context) {
        if ( this.GetIsStunned() ) {
            return false;
        }
        Character target = context.getTarget();
        if ( CheckRange(1, target ) ) {
            ScaleStats();
            target.SetCurrHealth(target.GetCurrHealth() - this.attack * 4);
            this.intl -= 2;
            ScaleStats();

            return true;
        }
        return false;
    }
    public boolean Ability2 (ActionContext context)  {
        if ( this.GetIsStunned() ) {
            return false;
        }
        if ( this.currHealth > 0.2 * this.health ) {
            SetCurrHealth(this.currHealth - 0.2 * this.health);
            this.attack *= 1.15;
            this.intl -= 2;
            ScaleStats();
            return true;
        }
        return false;
    }
    private void ApplyPassive() {
        // apply using same formula in stat calculation
    }
}