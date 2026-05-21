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
    private int type;
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

    public Entity(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public Entity() {
        this.SetTeam(0);
        this.name = null;
        this.type = NONE;
    }

    /* 
     * Renders an entity non-existant by setting all its variables to false or null.
     */
    public void Destroy() {
        this.type = NONE;
        this.name = null;
    }

    /*
     * Returns the Character reference if this entity is a character, null otherwise.
     *
     * @return - The Character stored in this entity, or null.
     */
    public Character GetCharacter() {
        return this.character;
    }

    /*
     * Returns the Minion reference if this entity is a minion, null otherwise.
     *
     * @return - The Minion stored in this entity, or null.
     */
    public Minion GetMinion() {
        return this.minion;
    }

    /*
     * Sets the type of the entity (used solely for initialization):
     * 0 = empty (destroyed or no object)
     * 1 = character
     * 2 = obstacle
     * 3 = food
     * 4 = minion
     * 
     * @param type              - What type of entity to classify itself as.
     */
    public void SetObject(int type) {
        this.type = type;
    }

    /*
     * Returns a number that represents the type of object:
     * 0 = empty (destroyed or no object)
     * 1 = character
     * 2 = obstacle
     * 3 = food
     * 4 = minion
     * 
     * @return type             - The type of entity in storage.
     */
    public int GetObject() {
        return type;
    }

    /* Gets the position of the entity.
     *
     * @return position         - The position of the entity in the gameboard coordinates. (y, x)
     */
    public int[] GetPosition() {
        return this.position;
    }

    /* Changes the name (title) of the entity.
     *
     * @param name              - What to change the name (title) to.
     */
    public void SetName(String name) {
        this.name = name;
    }

    /* 
     * Returns the name (title) of the entity.
     *
     * @return                  - Returns the name (title) of the entity.
     */
    public String GetName() {
        return name;
    }

    /* Sets the entity's full name. Only applicable to characters but needed for compatibility with minions.
     *
     * @param fullName          - What to change the full name to.
     */
    public void SetFullName(String fullName) {
        this.fullName = fullName;
    }

    /* Gets the entity's full name. Only applicable to characters but needed for compatibility with minions.
     *
     * @return fullName         - The full name of the entity.
     */
    public String GetFullName() {
        return fullName;
    }

    /* Sets the team of the entity. Most applicable to characters but needed for minions.
     *
     * @param team              - The team to change to.
     */
    public void SetTeam(int team) {
        this.team = team;
    }

    /* Gets the team of the entity. Most applicable to characters but needed for minions.
     *
     * @return                  - Returns the team of the entity.
     */
    public int GetTeam() {
        return this.team;
    }

    /* Checks a set range and returns true if a set character is found within said range.
     *
     * @param range             - The range to search.
     * @param target            - The character to look for.
     * 
     * @return                  - Returns true if found, false otherwise.
     */
    public boolean CheckRange(int range, Character target) {
        int[] targetPos = target.GetPosition();
        int[] myPos = this.GetPosition();

        int dy = Math.abs(targetPos[0] - myPos[0]);
        int dx = Math.abs(targetPos[1] - myPos[1]);

        return Math.max(dy, dx) <= range;
    }
}