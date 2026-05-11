public class Entity {
    private String name;
    private boolean isFood;
    private boolean isObstacle;
    private boolean isCharacter;
    int[] position = new int[2];

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

    public void setObject(int objectType) {
        if (objectType == 3) {
            this.isFood = true;
            this.isObstacle = false;
            this.isCharacter = false;
        } 
        else if (objectType == 2) {
            this.isFood = false;
            this.isObstacle = true;
            this.isCharacter = false;
        } 
        else if (objectType == 1) {
            this.isFood = false;
            this.isObstacle = false;
            this.isCharacter = true;
        } 
        else {
            this.isFood = false;
            this.isObstacle = false;
            this.isCharacter = false;
        }
    }

    public int[] GetPosition() {
        return this.position;
    }

    /* 
     * Returns the name of the entity.
     */
    public String getName() {
        return name;
    }

    /*
     * Changes name of entity.
     */
    public void setName(String name) {
        this.name = name;
    }
<<<<<<< Updated upstream
    public boolean CheckRange(int range, Character target) {
        int[] targetPos = target.GetPosition();
        int[] myPos = this.GetPosition();

        int x = Math.abs(targetPos[0] - myPos[0]);
        int y = Math.abs(targetPos[1] - myPos[1]);

        return Math.max(x, y) <= range;
    }
=======
    //
>>>>>>> Stashed changes
}
