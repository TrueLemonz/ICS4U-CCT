public class Block {
    private Entity entity;

    public Block() {
        this.entity = null;
    }
    public Block( Entity entity) {
        this.entity = entity;
    }
    public Entity GetEntity() {
        return this.entity;
    }
    public void SetEntity(Entity entity) {
        this.entity = entity;
    }
}


