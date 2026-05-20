/****************************************************
 * Entity
 * 
 * The base level everything in-game extends from.
 * Characters, food, and obstacles all are instances of entity.
 * 
 * Author: Leo & Lucas
 * Date: 20/05/26
 * **************************************************/
public class Entity {
    private String name;
    private String fullName;
    private boolean isFood;
    private boolean isObstacle;
    private boolean isCharacter;
    private boolean isMinion;
    public Food food;
    public Minion minion;
    public Obstacle obstacle;
    public Character character;
    private int team = 0;
    int[] position = new int[2];
    // Final variables for the GetObject() method
    public final static int MINION = 4;
    public final static int FOOD = 3;
    public final static int OBSTACLE = 2;
    public final static int CHARACTER = 1;
    public final static int NONE = 0;
    
    public Entity(String name, boolean isFood, boolean isObstacle, boolean isCharacter, boolean isMinion) {
        this.name = name;
        this.isFood = isFood;
        this.isObstacle = isObstacle;
        this.isCharacter = isCharacter;
        this.isMinion = isMinion;
    }

    public Entity() {
        this.SetTeam(0);
        this.name = null;
        this.isFood = false;
        this.isObstacle = false;
        this.isCharacter = false;
        this.isMinion = false;
    }

    /* 
     * Renders an entity non-existant by setting all its variables to false or null.
     */
    public void Destroy() {
        this.isFood = false;
        this.isObstacle = false;
        this.isCharacter = false;
        this.name = null;
        this.isMinion = false;
    }

    /*
     * Returns a number that represents the type of object:
     * 0 = empty (destroyed or no object)
     * 1 = character
     * 2 = obstacle
     * 3 = food
     */
    public int GetObject() {
        if (isFood) {
            return FOOD;
        } 
        else if (isObstacle) {
            return OBSTACLE;
        } 
        else if (isCharacter) {
            return CHARACTER;
        } 
        else if (isMinion) {
            return MINION;
        } 
        else {
            return NONE;
        }
    }
    public Character GetCharacter() {
        return this.character;
    }

    public void SetObject(int objectType) {
        if (objectType == 3) {
            this.isFood = true;
            this.isObstacle = false;
            this.isCharacter = false;
            this.isMinion = false;
        } 
        else if (objectType == 2) {
            this.isFood = false;
            this.isObstacle = true;
            this.isCharacter = false;
            this.isMinion = false;
        } 
        else if (objectType == 1) {
            this.isFood = false;
            this.isObstacle = false;
            this.isCharacter = true;
            this.isMinion = false;
        } 
        else if (objectType == 4) {
            this.isFood = false;
            this.isObstacle = false;
            this.isCharacter = false;
            this.isMinion = true;
        } 
        else {
            this.isFood = false;
            this.isObstacle = false;
            this.isCharacter = false;
            this.isMinion = false;
        }
    }

    public int[] GetPosition() {
        return this.position;
    }

    /* 
     * Returns the name of the entity.
     */
    public String GetName() {
        return name;
    }

    public String GetFullName() {
        return fullName;
    }
    public void SetFullName(String fullName) {
        this.fullName = fullName;
    }

    /*
     * Changes name of entity.
     */
    public void SetName(String name) {
        this.name = name;
    }
    public int GetTeam() {
        return this.team;
    }
    public void SetTeam(int team) {
        this.team = team;
    }
    public boolean CheckRange(int range, Character target) {
        int[] targetPos = target.GetPosition();
        int[] myPos = this.GetPosition();

        int x = Math.abs(targetPos[0] - myPos[0]);
        int y = Math.abs(targetPos[1] - myPos[1]);

        return Math.max(x, y) <= range;
    }

}
