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
    public int getHealth(){
        return this.health;
    }
    public int getDamage(){
        return this.damage;
    }
    public void Buff() {
        this.health += 1;
        this.damage += 1;
    }
}
//tag (for github)