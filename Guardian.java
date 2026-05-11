public class Guardian extends Character {
    public Guardian() {
        this.spdMod -= 2;
        this.intlMod = 4;
        this.atkMod = 0;
        this.sprMod = 1;
        this.hltMod = 5;
        this.sppMod = 0;
        this.spd += this.spdMod ;
        this.intl += this.intlMod;
        this.atk += this.atkMod;
        this.spr += this.sprMod;
        this.hlt += this.hltMod;
        this.spp += this.sppMod;
    }

    public boolean Special(ActionContext context) {
        if ( context.getGrid()[context.getPosX()][context.getPosY()].getEntity().getObject() == 0) {
            context.getGrid()[context.getPosX()][context.getPosY()] = new Block(new Obstacle());
            return true;
        }
        else {
            return false;
        }
    }
    // had to change from void to boolean because I simplified the methods earlier
    public boolean Ability1(ActionContext context) {
        this.hlt++;
        this.spd--;
        ApplyStats();
        return true;
    }

    public boolean Ability2(ActionContext context) {
        if (1.1 * this.currHealth <= health ) {
            this.currHealth *= 1.1;
            return true;
        }
        else {
            return false;
        }
    }
}