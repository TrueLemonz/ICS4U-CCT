public abstract class Character extends Entity {

    // These stats will hold their respective calculated values
    public double speed, intelligence, attack, spirit, health, spellpower;
    // These stats are simple integers which will later be scaled to actual values
    public double spd, intl, atk, spr, hlt, spp;
    // These are the modifiers that each character has, specific to itself.
    public int spdMod, intlMod, atkMod, sprMod, hltMod, sppMod;
    int[] position = new int[2];
    public double currHealth;
    
    public Character() {}

    public Character(int spd, int intl, int atk, int spr, int hlt, int spp) {
        this.spd = spd;
        this.intl = intl;
        this.atk = atk;
        this.spr = spr;
        this.hlt = hlt;
        this.spp = spp;
    }
    public void ApplyStats() {
        this.spd += this.spdMod ;
        this.intl += this.intlMod;
        this.atk += this.atkMod;
        this.spr += this.sprMod;
        this.hlt += this.hltMod;
        this.spp += this.sppMod;    
    }
    public void ScaleStats() {
        // Scales the integer values (spd, intl, etc) to actual values (speed, intelligence, etc), formulae to be determined   
        this.speed = this.spd + 1;
        this.intelligence = this.intl + 1;
        this.attack = this.atk + 1;
        this.spirit = this.spr + 1;
        this.health = this.hlt + 1;
        this.spellpower = this.spp + 1;  
    }
     
    public boolean EatFood(Food food) {
        if (this.currHealth + 15 <= this.health) {
            this.currHealth += 15;
            return true;
        }
        return false;
    }
    // Getter method for position of character on the 2D array
    // First block is the x-coordinate, second is y
    public int[] GetPosition() {
        return this.position;
    }
    public boolean SetPosition(int[] coordinates, Block[][] grid) {
        int x = coordinates[0];
        int y = coordinates[1];
        if (grid[x][y] != null) {
            grid[x][y].SetEntity(this);
            this.position[0] = x;
            this.position[1] = y;
            return true;
        }
        else {
            return false;
        }
    }
    public double GetHealth() {
        return this.currHealth;
    }
    public void SetHealth(double health) {
        this.currHealth = health;
    }
    public boolean CheckRange(int range, Entity target) {
        int[] targetPos = target.GetPosition();
        int[] myPos = this.GetPosition();

        int x = Math.abs(targetPos[0] - myPos[0]);
        int y = Math.abs(targetPos[1] - myPos[1]);

        return Math.max(x, y) <= range;
    }
    public boolean CheckRange()
    public abstract boolean Special(Character target, Block[][] grid);
    public abstract boolean Special(Character target);
    public abstract void Special();
    public abstract boolean Ability1(Character target, Block[][] grid);
    public abstract boolean Ability1(Character target);
    public abstract void Ability1();
    public abstract boolean Ability2(Character target, Block[][] grid);
    public abstract boolean Ability2(Character target);
    public abstract boolean Ability2();

}