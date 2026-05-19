/* A class that functions as a variable packet of information.
 * Can pass anything we need it to between methods. */
public class ActionContext {
    private Character target;
    private Entity targetEntity;
    private Block[][] grid;
    private int posx, posy;

    public ActionContext() {}
    public ActionContext(Character target, Block[][] grid) {
        this.targetEntity = null;
        this.target = target;
        this.grid = grid;
        this.posx = 0;
        this.posy = 0;
    }

    public ActionContext(int x, int y, Character target, Block[][] grid) {
        this.targetEntity = null;
        this.target = target;
        this.grid = grid;
        this.posx = x;
        this.posy = y;
    }
    public ActionContext( int x, int y, Block[][] grid) {
        this.targetEntity = null;
        this.grid = grid;
        this.posx = x;
        this.posy = y;
    }

    public ActionContext(Character target) {
        this.targetEntity = null;
        this.target = target;
        this.grid = null;
        this.posx = 0;
        this.posy = 0;
    }

    public ActionContext(Entity target) {
        this.targetEntity = target;
        this.target = null;
        this.grid = null;
        this.posx = 0;
        this.posy = 0;
    }

    /* Returns the target (Character) stored in the Context */
    public Character GetTarget() {
        return this.target;
    }

    /* Returns the target (Entity) stored in the Context */
    public Entity GetTargetEntity() {
        return this.targetEntity;
    }

    /* Returns the game's grid stored in the Context */
    public Block[][] GetGrid() {
        return this.grid;
    }

    /* Returns the target (Character) stored in the Context */
    public int GetPosX() {
        return posx;
    }

    public int GetPosY() {
        return posy;
    }
}

