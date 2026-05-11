public abstract class Character extends Entity {

    // These stats will hold their respective calculated values
    public double speed, intelligence, attack, spirit, health, spellpower;
    // These stats are simple integers which will later be scaled to actual values
    public int spd, intl, atk, spr, hlt, spp;
    // These are the modifiers that each character has, specific to itself.
    public int spdMod, intlMod, atkMod, sprMod, hltMod, sppMod;
    private double currHealth;
    private double currMagic; // for tracking magic amount in-game
    private boolean isMinion = false; // for checking if the character is a minion or not, used in necromancer's abilities
    private int turns = 1; 
    
    public Character() {}

    public Character(int spd, int intl, int atk, int spr, int hlt, int spp) {
        super();
        this.spd = spd;
        this.intl = intl;
        this.atk = atk;
        this.spr = spr;
        this.hlt = hlt;
        this.spp = spp;
    }

    public Character GenerateCharacter() {
        int points = 20;
        for (int i = 0; i < points; i++) {
            int stat = (int) (Math.random() * 6);
            if (stat == 0){
                this.spd++;
            }
            if (stat == 1){
                this.intl++;
            }
            if (stat == 2){
                this.atk++;
            }
            if (stat == 3){
                this.spr++;
            }
            if (stat == 4){
                this.hlt++;
            }
            if (stat == 5){
                this.spp++;
            }
        }
        return this;
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
        // Done
        this.speed = this.spd;
        this.intelligence = this.intl * 2.5;
        this.attack = this.atk * 9.5;
        this.spirit = this.spr * 0.5;
        this.health = this.hlt * 10;
        this.spellpower = this.spp; //calculated in attack  
    }
     
    public boolean EatFood(Food food) {
        if (this.currHealth + 15 <= this.health) {
            this.currHealth += 15;
            return true;
        }
        return false;
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
    public double GetMagic() {
        return this.currMagic;
    }
    public void SetMagic(double magic) {
        this.currMagic = magic;
    }
    public boolean IsMinion() {
        return this.isMinion;
    }
    public void SetMinion(boolean isMinion) { //RIDICULOUS function
        this.isMinion = isMinion;
    }
    private int GetTurns() {
        return this.turns;
    }
    public void AddTurn() { //ANOTHER RIDICULOUS FUNCTION
        this.turns++;
    }
    public boolean CheckRange(int range, Entity target) {
        int[] targetPos = target.GetPosition();
        int[] myPos = this.GetPosition();

        int x = Math.abs(targetPos[0] - myPos[0]);
        int y = Math.abs(targetPos[1] - myPos[1]);

        return Math.max(x, y) <= range;
    }
    public abstract boolean Special(ActionContext context);
    public abstract boolean Ability1(ActionContext context);
    public abstract boolean Ability2(ActionContext context);
}