public abstract class Character extends Entity {

    // These stats will hold their respective calculated values
    public double speed, intelligence, attack, spirit, health, spellpower;
    // These stats are simple integers which will later be scaled to actual values
    public int spd, intl, atk, spr, hlt, spp;
    // These are the modifiers that each character has, specific to itself.
    public int spdMod, intlMod, atkMod, sprMod, hltMod, sppMod;
    public double currHealth;
    public double currMagic;
    public int team;
    private boolean isStunned;
    private int turns = 1; // Just for minions really
    
    public Character() {}

    public Character(int spd, int intl, int atk, int spr, int hlt, int spp, boolean isStunned) {
        super();
        this.spd = spd;
        this.intl = intl;
        this.atk = atk;
        this.spr = spr;
        this.hlt = hlt;
        this.spp = spp;
        this.isStunned = isStunned;
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
    public double[] GetCalculatedStats() {
        double[] stats = {this.speed, this.intelligence, this.attack, this.spirit, this.health, this.spellpower};
        return stats;
    }
    public double GetCurrHealth() {
        return this.currHealth;
    }
    public double GetCurrMagic() {
        return this.currMagic;
    }
    public int[] GetRawStats() {
        int[] stats = {this.spd, this.intl, this.atk, this.spr, this.hlt, this.spp};
        return stats;
    }
    public int GetTeam() {
        return this.team;
    }
    public void SetHlt(int hlt) {
        this.hlt = hlt;
    }
    public void SetCurrHealth(double currHealth) {
        this.currHealth = currHealth;
    }
    public void SetCurrMagic(double currMagic) {
        this.currMagic = currMagic;
    }
    public void SetAtk(int atk) {
        this.atk = atk;
    }
    public void SetSpd(int spd) {
        this.spd = spd;
    }
    public void SetIntl(int intl) {
        this.intl = intl;
    }
    public void SetSpr(int spr) {
        this.spr = spr;
    }
    public void SetSPP(int spp) {
        this.spp = spp;
    }
    public void SetIsStunned(boolean isStunned) {
        this.isStunned = isStunned;
    }
    public boolean GetIsStunned() {
        return this.isStunned;
    }
    public void AddTurn() { // Two ridiculous methods just for minions (could be used for stunning or buffs i don't know)
        this.turns++;
    }
    public int GetTurns() {
        return this.turns;
    }
    public boolean CheckRange(int range, Entity target) {
        if (this.isStunned) {
            return false;
        }
        int[] targetPos = target.GetPosition();
        int[] myPos = this.GetPosition();

        int x = Math.abs(targetPos[0] - myPos[0]);
        int y = Math.abs(targetPos[1] - myPos[1]);

        return Math.max(x, y) <= range;
    }
    public abstract boolean Special(ActionContext context);
    private String SpecialHint;
    public String GetSpecialHint() {
        return this.SpecialHint;
    }
    public void SetSpecialHint(String hint) {
        this.SpecialHint = hint;
    }

    public abstract boolean Ability1(ActionContext context);
    private String Ability1Hint;
    public String GetAbility1Hint() {
        return this.Ability1Hint;
    }
    public void SetAbility1Hint(String hint) {
        this.Ability1Hint = hint;
    }

    public abstract boolean Ability2(ActionContext context);
    private String Ability2Hint;
    public String GetAbility2Hint() {
        return this.Ability2Hint;
    }
    public void SetAbility2Hint(String hint) {
        this.Ability2Hint = hint;
    }
}