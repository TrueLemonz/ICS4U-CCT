public class Entity {
    private String name;
    private boolean isFood;
    private boolean isObstacle;
    private boolean isCharacter;

    public Entity(String name, boolean isFood, boolean isObstacle, boolean isCharacter) {
        this.name = name;
        this.isFood = isFood;
        this.isObstacle = isObstacle;
        this.isCharacter = isCharacter;
    }

    public Entity() {
        this.name = null;
        this.isFood = false;
        this.isObstacle = false;
        this.isCharacter = false;
    }

    /* 
     * Renders an entity non-existant by setting all its variables to false or null.
     */
    public void destroy() {
        this.isFood = false;
        this.isObstacle = false;
        this.isCharacter = false;
        this.name = null;
    }

    /*
     * Returns a number that represents the type of object:
     * 0 = empty (destroyed or no object)
     * 1 = character
     * 2 = obstacle
     * 3 = food
     */
    public int getObject() {
        if (isFood) {
            return 3;
        } 
        else if (isObstacle) {
            return 2;
        } 
        else if (isCharacter) {
            return 1;
        } 
        else {
            return 0;
        }
    }

    /* 
     * Returns the name of the entity.
     */
    public String getName() {
        return name;
    }
}
