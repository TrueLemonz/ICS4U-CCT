public class ActionContext {
    private Character target;
    private Block[][] grid;

    public ActionContext(Character target, Block[][] grid) {
        this.target = target;
        this.grid = grid;
    }

    public Character getTarget() {
        return this.target;
    }

    public Block[][] getGrid() {
        return this.grid;
    }
}