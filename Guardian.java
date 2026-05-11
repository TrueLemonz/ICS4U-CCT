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

    public boolean Special(Block[][] grid, Obstacle obstacle, int x, int y) {
        if ( grid[x][y].getEntity().getObject() == 0) {
            grid[x][y] = new Block(obstacle);
            return true;
        }
        else {
            return false;
        }
    }

    public void Ability1() {
        this.hlt++;
        this.spd--;
        ApplyStats();
    }

    public boolean Ability2() {
        if (1.1 * this.currHealth <= health ) {
            this.currHealth *= 1.1;
            return true;
        }
        else {
            return false;
        }
    }
}