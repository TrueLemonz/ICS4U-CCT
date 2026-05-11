public class Barbarian extends Character {
    // Had to change this, Math.log only takes one variable. Must be log 10.
    private double missingHPBonus = 1.1 * Math.log (this.currHealth / this.health );
    public Barbarian() {
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
<<<<<<< Updated upstream
    public boolean Special(ActionContext context) {
        if (CheckRange(1, context.getTarget())) {
            int[] targetPos = context.getTarget().GetPosition();
=======
    public boolean Special(Character target, Block[][] grid) {
        if (CheckRange(1, target)) {
            ScaleStats();
            int[] targetPos = target.GetPosition();
>>>>>>> Stashed changes
            int[] myPos = this.GetPosition();

            // Performs special vertically
            // Checks that the barbarian is not at the top or bottom of the grid, or he'll throw the target out of the grid
            if (this.GetPosition()[1] == 0 || this.GetPosition()[1] == context.getGrid().length - 1) {
            return false;
            }
            else {
                // target is above barbarian
                if (targetPos[0] == myPos[0] && targetPos[1] == myPos[1] + 1) {
                context.getGrid()[myPos[0]][myPos[1] - 2].SetEntity(context.getTarget());
                context.getGrid()[myPos[0]][myPos[1]].SetEntity(null);
                context.getTarget().position[0] = myPos[0];
                context.getTarget().position[1] = myPos[1] - 2;
                return true;
                }
                // target is below barbarian
                else if (targetPos[0] == myPos[0] && targetPos[1] == myPos[1] - 1) {
                context.getGrid()[myPos[0]][myPos[1] + 2].SetEntity(context.getTarget());
                context.getGrid()[myPos[0]][myPos[1]].SetEntity(null);
                context.getTarget().position[0] = myPos[0];
                context.getTarget().position[1] = myPos[1] + 2;
                return true;
                }
            }
            // Performs special horizontally  
            // Checks that the barbarian is not at the extreme left or right of the grid or he'll throw the target out of the grid
            if ( this.GetPosition()[0] == 0 || this.GetPosition()[0] == context.getGrid()[0].length - 1) {
                return false;
            }
            else {
                // target is to the left of barbarian
                if (targetPos[0] == myPos[0] - 1 && targetPos[1] == myPos[1]) {
                    context.getGrid()[myPos[0] + 2][myPos[1]].SetEntity(context.getTarget());
                    context.getGrid()[myPos[0]][myPos[1]].SetEntity(null);
                    context.getTarget().position[0] = myPos[0] + 2;
                    context.getTarget().position[1] = myPos[1];
                    return true;
                }
                // target is to the right of barbarian
                else if (targetPos[0] == myPos[0] + 1 && targetPos[1] == myPos[1]) {
                    context.getGrid()[myPos[0] - 2][myPos[1]].SetEntity(context.getTarget());
                    context.getGrid()[myPos[0]][myPos[1]].SetEntity(null);
                    context.getTarget().position[0] = myPos[0] - 2;
                    context.getTarget().position[1] = myPos[1];
                    return true;
                }
            }
            // Performs special diagonally
            // Checks both vertical and horizontal conditions
            if ( this.GetPosition()[0] == 0 || this.GetPosition()[0] == context.getGrid()[0].length - 1 
                 || this.GetPosition()[1] == 0 || this.GetPosition()[1] == context.getGrid().length - 1 ) {
                    return false;
                 }
                 else {
                    if (targetPos[0] == myPos[0] - 1 && targetPos[1] == myPos[1] - 1) {
                        context.getGrid()[targetPos[0] + 2][targetPos[1] + 2].SetEntity(context.getTarget());
                        context.getGrid()[targetPos[0]][targetPos[1]].SetEntity(null);
                        context.getTarget().position[0] = targetPos[0] + 2;
                        context.getTarget().position[1] = targetPos[1] + 2;
                        return true;
                    }
                    else if (targetPos[0] == myPos[0] - 1 && targetPos[1] == myPos[1] + 1) {
                        context.getGrid()[targetPos[0] + 2][targetPos[1] - 2].SetEntity(context.getTarget());
                        context.getGrid()[targetPos[0]][targetPos[1]].SetEntity(null);
                        context.getTarget().position[0] = targetPos[0] + 2;
                        context.getTarget().position[1] = targetPos[1] - 2;
                        return true;
                    }
                    else if (targetPos[0] == myPos[0] + 1 && targetPos[1] == myPos[1] - 1) {
                        context.getGrid()[targetPos[0] - 2][targetPos[1] + 2].SetEntity(context.getTarget());
                        context.getGrid()[targetPos[0]][targetPos[1]].SetEntity(null);
                        context.getTarget().position[0] = targetPos[0] - 2;
                        context.getTarget().position[1] = targetPos[1] + 2;
                        return true;
                    }
                    else if (targetPos[0] == myPos[0] + 1 && targetPos[1] == myPos[1] + 1) {
                        context.getGrid()[targetPos[0] - 2][targetPos[1] - 2].SetEntity(context.getTarget());
                        context.getGrid()[targetPos[0]][targetPos[1]].SetEntity(null);
                        context.getTarget().position[0] = targetPos[0] - 2;
                        context.getTarget().position[1] = targetPos[1] - 2;
                        return true;
                    }
                 }
        }
        this.intl -= 4;
        ScaleStats();
        return false;
    }
<<<<<<< Updated upstream
    public boolean Ability1 (ActionContext context) {
        if ( CheckRange(1, context.getTarget()) ) {
            context.getTarget().SetHealth(context.getTarget().GetHealth() - this.attack * 4);
=======
    // Dummy method for overloading
    public boolean Special(Character target) {
        return false;
    }
    // Dummy method for overloading
    public void Special() {
    }
    // Strong attack, meant to hit multiple times so that block/parry is calculated for each hit
    // and its unlikely for the whole thing to be blocked
    public boolean Ability1 (Character target, Block[][] grid) {
        if ( CheckRange(1, target) ) {
            ScaleStats();
            target.SetHealth(target.GetHealth() - this.attack * 4);
            this.intl -= 2;
            ScaleStats();
>>>>>>> Stashed changes
            return true;
        }
        return false;
    }
<<<<<<< Updated upstream
    //TODO actually set this
    public boolean Ability2 (ActionContext context) {
        if (true) {
=======
    // Dummy method for overloading
    public boolean Ability1 (Character target) {
        return false;
    }
    // Dummy method for overloading
    public void Ability1() {
    }

    public boolean Ability2 () {
        if ( this.currHealth > 0.2 * this.health ) {
            SetHealth(this.currHealth - 0.2 * this.health);
            this.attack *= 1.15;
            this.intl -= 2;
            ScaleStats();
>>>>>>> Stashed changes
            return true;
        }
        return false;
    }
<<<<<<< Updated upstream
    private void ApplyPassive() {
        // apply using same formula in stat calculation
=======
    // Dummy method for overloading
    public boolean Ability2 (Character target, Block[][] grid) {
        return false;
    }
    public boolean Ability2 (Character target) {
        return false;
>>>>>>> Stashed changes
    }
}