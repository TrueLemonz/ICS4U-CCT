/****************************************************
 * Obstacle
 * 
 * Very simple class needed purely to occupy space.
 * 
 * Author: Leo & Lucas
 * Date: 20/05/26
 * **************************************************/
public class Obstacle extends Entity {
    public Obstacle() {
        super();
        this.SetTeam(0);
        this.SetObject(2);
        this.SetName("Obstacle");
    }
}