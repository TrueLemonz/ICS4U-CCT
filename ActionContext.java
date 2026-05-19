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

    public ActionContext(Entity entity) {
        this.targetEntity = target;
        this.target = null;
        this.grid = null;
        this.posx = 0;
        this.posy = 0;
    }

    public Character GetTarget() {
        return this.target;
    }
    public Entity GetTargetEntity() {
        return this.targetEntity;
    }

    public Block[][] GetGrid() {
        return this.grid;
    }

    public int getPosX() {
        return posx;
    }

    public int getPosY() {
        return posy;
    }
}

