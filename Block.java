/****************************************************
 * Block
 * 
 * This contains just an entity.
 * The Game System's grid is made up of a 2D array of Blocks.
 * 
 * Author: Leo & Lucas
 * Date: 20/05/26
 **************************************************/
public class Block {
    private Entity entity;

    public Block() {
        this.entity = null;
    }

    /* Create a block with an entity inside already.
     *
     * @param entity        - The entity to store in the block.
     */
    public Block( Entity entity) {
        this.entity = entity;
    }

    /* Getter function for the entity inside of the block.
     *
     * @return              - Returns the entity stored inside of the block.
     */
    public Entity GetEntity() {
        return this.entity;
    }

    /* Setter function for the entity inside of the block.
     *
     * @param               - The entity to place inside of the block.
     */
    public void SetEntity(Entity entity) {
        this.entity = entity;
    }
}
