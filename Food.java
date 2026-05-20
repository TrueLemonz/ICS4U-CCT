/****************************************************
 * Food
 * 
 * Another very simple class.
 * Can be "Eaten" by moving a character onto it.
 * Heals from 5-15 health.
 * 
 * Author: Leo & Lucas
 * Date: 20/05/26
 * **************************************************/
public class Food extends Entity {
    public Food() {
        super();
        this.SetObject(3);
        this.SetName("Food");
    }
}
//Does this work