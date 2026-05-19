public class Minion extends Entity {
    private int team;
    private int damage;
    private int health;
    public Minion(int team) {
        super();
        this.SetObject(4);
        this.team = team;
        this.SetName("Team " + this.team + "'s minion" );
    }
    public int GetHealth(){
        return this.health;
    }
    public int GetDamage(){
        return this.damage;
    }
<<<<<<< HEAD
    public boolean Ability2(ActionContext context) {
        return false;
    }
    public boolean Ability3(ActionContext context) {
        return false;
    }
    public String[] getAbilityMenu() {
        return new String[]{
            "1. Do nothing", 
            "2. Do nothing", 
            "3. Do nothing"
        };
    }
    public boolean Buff() {
        this.SetCurrHealth(this.getCurrHealth() + 1);
        return true;
    }
    public boolean executeAbility(int choice, ActionContext context) {
        if (choice == 1 && CheckAbility1Possible(gs)) {
            System.out.println(this.getFullName() + " Does nothing!");
            // Apply damage/effects here
            return true;
        } else if (choice == 2 && CheckAbility2Possible(gs)) {
            System.out.println(this.getFullName() + " Does nothing!");
            // Apply damage/effects here
            return true;
        } else if (choice == 3 && CheckAbility3Possible(gs)) {
            System.out.println(this.getFullName() + " Does nothing!");
            // Apply damage/effects here
            return true;
        }
        System.out.println("Ability failed or unavailable.");
        return false;
=======
    public void Buff() {
        this.health += 1;
        this.damage += 1;
>>>>>>> 29cc78584bb120f90bde73b1cc1f92c89d7a02d1
    }
}
//tag (for github)