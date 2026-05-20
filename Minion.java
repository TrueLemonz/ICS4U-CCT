/****************************************************
 * Minion
 * 
 * Minion summoned by the Necromancer.
 * Not a character because it does not have abilities.
 * 
 * Author: Leo & Lucas
 * Date: 20/05/26
 * **************************************************/
public class Minion extends Entity {
    private int damage;
    private int health;
    public Minion(int team) {
        super();
        this.SetObject(4);
        this.SetTeam(team);
        this.SetName("Team " + this.GetTeam() + "'s minion" );
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
//Does this work