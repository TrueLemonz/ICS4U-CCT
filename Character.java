public class Character extends Entity {

    // These stats will hold their respective calculated values
    public double speed, intelligence, attack, spirit, health, spellpower;
    // These stats are simple integers which will later be scaled to actual values
    public int spd, intl, atk, spr, hlt, spp;
    // These are the modifiers that each character has, specific to itself.
    public int spdMod, intlMod, atkMod, sprMod, hltMod, sppMod;
    public double currHealth;
    public double currMagic;
    public int team;
    public boolean isAlive = true;
    private boolean isStunned;
    private int turns = 1; // Just for minions really
    public final static int SPDPOS = 0;
    public final static int INTLPOS = 1;
    public final static int ATKPOS = 2;
    public final static int SPRPOS = 3;
    public final static int HLTPOS = 4;
    public final static int SPPPOS = 5;
    public final static int SPEEDPOS = 0;
    public final static int INTELLIGENCEPOS = 1;
    public final static int ATTACKPOS = 2;
    public final static int SPIRITPOS = 3;
    public final static int MAXHEALTHPOS = 4;
    public final static int SPELLPOWERPOS = 5;
    private boolean isMinion = false; // for checking if the character is a minion or not, used in necromancer's abilities
    private boolean IsDivineSheielded;
    
    public Character() {
        super();
        this.SetObject(1);
    }

    public Character(int spd, int intl, int atk, int spr, int hlt, int spp, boolean isStunned) {
        super();
        this.SetObject(1);
        this.spd = spd;
        this.intl = intl;
        this.atk = atk;
        this.spr = spr;
        this.hlt = hlt;
        this.spp = spp;
        this.isStunned = isStunned;
    }

    public Character GenerateCharacter() {
        int nameInt = (int) (Math.random() * 15);
        String[] names = {"Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Henry", "Ivy", "Jack", "Kate", "Liam", "Mia", "Noah", "Olivia"};
        this.SetFullName(names[nameInt]);

        int points = 20;
        for (int i = 0; i < points; i++) {
            int stat = (int) (Math.random() * 6);
            if (stat == 0){
                this.spd++;
            }
            else if (stat == 1 && this.intl < 8){
                this.intl++;
            }
            else if (stat == 2 && this.atk < 8){
                this.atk++;
            }
            else if (stat == 3 && this.spr < 8){
                this.spr++;
            }
            else if (stat == 4 && this.hlt < 8){
                this.hlt++;
            }
            else if (stat == 5 && this.spp < 8){
                this.spp++;
            }
            else {
                i--;
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
    public double[] GetCalculatedStats() {
        double[] stats = {this.speed, this.intelligence, this.attack, this.spirit, this.health, this.spellpower};
        return stats;
    }
    public void SetCalculatedStats( int pos, double amt ) {
        this.GetCalculatedStats()[pos] = amt;
    }
    public boolean GetIsDivineShielded() {
        return this.IsDivineSheielded;
    }
    public void SetIsDivineSheielded(boolean isDivineSheielded) {
        this.IsDivineSheielded = isDivineSheielded;
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
    public double GetMagic() {
        return this.currMagic;
    }
    public double GetMaxHealth() {
        return this.health;
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
    public boolean GetIsAlive() {
        return this.isAlive;
    }
    public int GetSpd() {
        return this.spd;
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
    public boolean Special(ActionContext context) {
        return false;
    }
    private String SpecialHint;
    public String GetSpecialHint() {
        return this.SpecialHint;
    }
    public void SetSpecialHint(String hint) {
        this.SpecialHint = hint;
    }

    public boolean Ability1(ActionContext context) {
        return false;
    }
    private String Ability1Hint;
    public String GetAbility1Hint() {
        return this.Ability1Hint;
    }
    public void SetAbility1Hint(String hint) {
        this.Ability1Hint = hint;
    }
    public boolean CheckConditions ( int magic, int range, Entity target) {
        if ( !this.isAlive || this.isStunned || this.currMagic - magic < 0 || !CheckRange(range, target)) {
            return false;
        }
        else return true;
    }
    public boolean CheckConditions ( int magic) {
        if ( !this.isAlive || this.isStunned || this.currMagic - magic < 0 ) {
            return false;
        }
        else return true;
    }
    /*Returns character with the most speed
    * Will put it to chance if speeds are equal
    * c1, c2 and c3 refer to character 1, etc. respectively */
    public Character getMaxSpeed(Character[] PlayerTeam) { 
        //  c1 has highest speed
        if ( PlayerTeam[0].GetSpd() > PlayerTeam[1].GetSpd() && PlayerTeam[0].GetSpd() > PlayerTeam[2].GetSpd()) {
            return PlayerTeam[0];
        }
        // c2 has highest speed
        else if ( PlayerTeam[1].GetSpd() > PlayerTeam[0].GetSpd() && PlayerTeam[1].GetSpd() > PlayerTeam[2].GetSpd() ) {
            return PlayerTeam[1];
        } 
        // c3 has highest speed
        else if ( PlayerTeam[2].GetSpd() > PlayerTeam[1].GetSpd() && PlayerTeam[2].GetSpd() > PlayerTeam[0].GetSpd() ) {
            return PlayerTeam[2];
        }
        // c1 has a higher speed than c2 but is equal to c3, 50% change to return either c1 or c3
        else if ( PlayerTeam[0].GetSpd() > PlayerTeam[1].GetSpd() && PlayerTeam[0].GetSpd() == PlayerTeam[2].GetSpd() ) {
            int randNum = (int) (Math.random() * 2);
            if ( randNum == 1) {
                return PlayerTeam[0];
            }
            else {
                return PlayerTeam[2];
            }
        }
        // If c1 has a higher speed than c3 but is equal to c2, 50% change to return either c1 or c2
        else if ( PlayerTeam[0].GetSpd() > PlayerTeam[2].GetSpd() && PlayerTeam[0].GetSpd() == PlayerTeam[1].GetSpd() ) {
            int randNum = (int) (Math.random() * 2);
            if ( randNum == 1) {
                return PlayerTeam[0];
            }
            else {
                return PlayerTeam[1];
            }

        }
        // If c2 has a higher speed than c1 but is equal to c3, 50% change to return either c2 or c3
        else if ( PlayerTeam[1].GetSpd() > PlayerTeam[0].GetSpd() && PlayerTeam[0].GetSpd() == PlayerTeam[2].GetSpd() ) {
            int randNum = (int) (Math.random() * 2);
            if ( randNum == 1) {
                return PlayerTeam[1];
            }
            else {
                return PlayerTeam[2];
            }

        }
        // If c2 has a higher speed than c3 but is equal to c1, 50% change to return either c2 or c1 
        else if ( PlayerTeam[1].GetSpd() > PlayerTeam[2].GetSpd() && PlayerTeam[1].GetSpd() == PlayerTeam[0].GetSpd() ) {
            int randNum = (int) (Math.random() * 2);
            if ( randNum == 1) {
                return PlayerTeam[0];
            }
            else {
                return PlayerTeam[1];
            }
        }
        // If c3 has a higher speed than c1 but is equal to c2, 50% change to return either c3 or c2
        else if ( PlayerTeam[2].GetSpd() > PlayerTeam[0].GetSpd() && PlayerTeam[2].GetSpd() == PlayerTeam[1].GetSpd() ) {
            int randNum = (int) (Math.random() * 2);
            if ( randNum == 1) {
                return PlayerTeam[1];
            }
            else {
                return PlayerTeam[2];
            }
        }
        // If c3 has a higher speed than c2 but is equal to c1, 50% change to return either c3 or c1
        else if ( PlayerTeam[2].GetSpd() > PlayerTeam[1].GetSpd() && PlayerTeam[2].GetSpd() == PlayerTeam[0].GetSpd() ) {
            int randNum = (int) (Math.random() * 2);
            if ( randNum == 1) {
                return PlayerTeam[0];
            }
            else {
                return PlayerTeam[2];
            }
        }
        // they all have the same speed
        else  {
            int randNum = (int) (Math.random() * 3);
            for ( int i = 1; i <= 3; i++) {
                if ( randNum == i) {
                    return PlayerTeam[i-1];
                }
            }
        }
        // Dummy - code won't reach here
        return PlayerTeam[0];
    }
    //TODO write getMedianSpeed
    //public Character getMedianSpeed(Character maxSpeedCharacter) {
          

    public boolean Ability2(ActionContext context) {
        return false;
    }
    private String Ability2Hint;
    public String GetAbility2Hint() {
        return this.Ability2Hint;
    }
    public void SetAbility2Hint(String hint) {
        this.Ability2Hint = hint;
    }
}