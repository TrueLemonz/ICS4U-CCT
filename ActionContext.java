public class ActionContext {
    private Character target;
    private Block[][] grid;
    private int posx, posy;

    public ActionContext() {}
    public ActionContext(Character target, Block[][] grid) {
        this.target = target;
        this.grid = grid;
        this.posx = 0;
        this.posy = 0;
    }

    public ActionContext(int x, int y, Character target, Block[][] grid) {
        this.target = target;
        this.grid = grid;
        this.posx = x;
        this.posy = y;
    }
    public ActionContext( int x, int y, Block[][] grid) {
        this.grid = grid;
        this.posx = x;
        this.posy = y;
    }

    public ActionContext(Character target) {
        this.target = target;
        this.grid = null;
        this.posx = 0;
        this.posy = 0;
    }

    public Character GetTarget() {
        return this.target;
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
//tag (for github)