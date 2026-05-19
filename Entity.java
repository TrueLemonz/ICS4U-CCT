public class Entity {
    private String name;
    private String fullName;
    private boolean isFood;
    private boolean isObstacle;
    private boolean isCharacter;
    public Food food;
    public Obstacle obstacle;
    public Character character;
    private int team = 0;
    int[] position = new int[2];
    // Final variables for the getObject() method
    public final static int FOOD = 3;
    public final static int OBSTACLE = 2;
    public final static int CHARACTER = 1;
    public Entity(String name, boolean isFood, boolean isObstacle, boolean isCharacter) {
        this.name = name;
        this.isFood = isFood;
        this.isObstacle = isObstacle;
        this.isCharacter = isCharacter;
    }

    public Entity() {
        this.team = 0;
        this.name = null;
        this.isFood = false;
        this.isObstacle = false;
        this.isCharacter = false;
    }

    /* 
     * Renders an entity non-existant by setting all its variables to false or null.
     */
    public void Destroy() {
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
            return FOOD;
        } 
        else if (isObstacle) {
            return OBSTACLE;
        } 
        else if (isCharacter) {
            return CHARACTER;
        } 
        else {
            return 0;
        }
    }
    public Character getCharacter() {
        return this.character;
    }

    public void SetObject(int objectType) {
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

    public int[] getPosition() {
        return this.position;
    }

    /* 
     * Returns the name of the entity.
     */
    public String getName() {
        return name;
    }

    public String getFullName() {
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
    public int getTeam() {
        return this.team;
    }
    public void SetTeam(int team) {
        this.team = team;
    }
    public boolean CheckRange(int range, Character target) {
        int[] targetPos = target.getPosition();
        int[] myPos = this.getPosition();

        int x = Math.abs(targetPos[0] - myPos[0]);
        int y = Math.abs(targetPos[1] - myPos[1]);

        return Math.max(x, y) <= range;
    }

}
