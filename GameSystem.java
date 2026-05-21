/****************************************************
 * GameSystem
 * 
 * The central backend class that contains vital game information.
 * Holds the GameBoard (which in turn holds all of the entities in-game).
 * Also holds each Player and their teams.
 * 
 * Author: Leo & Lucas
 * Date: 20/05/26
 * **************************************************/
public class GameSystem {
    public Block[][] GameBoard = new Block[GAMEHEIGHT][GAMEWIDTH];
    public static int GAMEHEIGHT = 8;
    public static int GAMEWIDTH  = 8;
    private int currTeam;
    public Player player1 = new Player();
    public Player player2 = new Player();

    public GameSystem() {
        RefreshGameBoard();
    }

    /*
     * Regenerates 1 health and 1 magic for every character on the given team,
     * capped at their respective maximums.
     *
     * @param team - The array of characters to regenerate.
     */
    public void RegenerateCharacters(Character[] team) {
        for (int i = 0; i < team.length; i++) {
            if (team[i] == null) {
                // skip empty slots
            } else {
                if (team[i].GetCurrMagic() < team[i].GetMaxMagic()) {
                    team[i].SetCurrMagic(team[i].GetCurrMagic() + 1);
                }
                if (team[i].GetCurrHealth() < team[i].GetMaxHealth()) {
                    team[i].SetCurrHealth(team[i].GetCurrHealth() + 1);
                }
            }
        }
    }

    /*
     * Attempts to move a character to a block on the grid.
     * If the destination contains food, the character eats it and moves there.
     *
     * @param character - The character to move.
     * @param posX      - Target column (X coordinate).
     * @param posY      - Target row (Y coordinate).
     * @return          - True if the move was successful, False otherwise.
     */
    public boolean Move(Character character, int posX, int posY) {
        if (character == null) {
            return false;
        }

        int nullY = character.GetPosition()[0]; // current row (Y)
        int nullX = character.GetPosition()[1]; // current col (X)

        // Bounds check
        if (posX < 0 || posY < 0 || posY >= GAMEHEIGHT || posX >= GAMEWIDTH) {
            return false;
        }

        
        if (Math.abs(posX - nullX) > 2 || Math.abs(posY - nullY) > 2) { // 2 is how many steps any character can take. Not 1 because the game would play too slow.
            return false;
        }

        if (!character.CheckConditions(0)) {
            return false;
        }

        Block destination = this.GameBoard[posY][posX];
        if (destination == null) {
            return false;
        }

        Entity destEntity = destination.GetEntity();
        if (destEntity == null) {
            return false;
        }

        int destType = destEntity.GetObject();

        if (destType == Entity.NONE) {
            // Normal move to empty tile
            destination.SetEntity(character);
            character.GetPosition()[0] = posY;
            character.GetPosition()[1] = posX;
            this.GameBoard[nullY][nullX] = new Block(new Entity("", 0));
            return true;
        }

        if (destType == Entity.FOOD) {
            // Eat the food and move onto the tile
            character.EatFood(destEntity.GetFood());
            destination.SetEntity(character);
            character.GetPosition()[0] = posY;
            character.GetPosition()[1] = posX;
            this.GameBoard[nullY][nullX] = new Block(new Entity("", 0));
            return true;
        }

        // Block is obstacle, character or minion so returns false.
        return false;
    }

    /*
     * Resets every block on the GameBoard to an empty Block.
     */
    public void RefreshGameBoard() {
        for (int i = 0; i < GAMEHEIGHT; i++) {
            for (int j = 0; j < GAMEWIDTH; j++) {
                this.GameBoard[i][j] = new Block(new Entity("", 0));
            }
        }
    }

    /*
     * Randomly populates the board with a number of obstacles and food
     *
     * @param obstacleCount - How many obstacle entities to place.
     * @param foodCount     - How many food entities to place.
     */
    public void PopulateGameBoard(int obstacleCount, int foodCount) {
        for (int i = 0; i < obstacleCount; i++) {
            RandBlock(new Obstacle());
        }
        for (int i = 0; i < foodCount; i++) {
            RandBlock(new Food());
        }
    }

    /*
     * Places an entity on a randomly chosen empty cell of the board.
     * Does nothing if no empty cells remain.
     *
     * @param entity - The entity to place.
     */
    public void RandBlock(Entity entity) {
        // Count empty cells first
        int emptyCount = 0;
        for (int i = 0; i < this.GameBoard.length; i++) {
            for (int j = 0; j < this.GameBoard[i].length; j++) {
                Block block = this.GameBoard[i][j];
                if (block != null && block.GetEntity() != null
                        && block.GetEntity().GetObject() == Entity.NONE) {
                    emptyCount++;
                }
            }
        }

        if (emptyCount == 0) {
            return;
        }

        // Collect their positions
        int[][] emptyPositions = new int[emptyCount][2];
        int index = 0;
        for (int i = 0; i < this.GameBoard.length; i++) {
            for (int j = 0; j < this.GameBoard[i].length; j++) {
                Block block = this.GameBoard[i][j];
                if (block != null && block.GetEntity() != null
                        && block.GetEntity().GetObject() == Entity.NONE) {
                    emptyPositions[index][0] = i;
                    emptyPositions[index][1] = j;
                    index++;
                }
            }
        }

        // Pick one at random and place the entity
        int randomIndex = (int) (Math.random() * emptyCount);
        int targetRow   = emptyPositions[randomIndex][0];
        int targetCol   = emptyPositions[randomIndex][1];
        entity.GetPosition()[0] = targetRow;
        entity.GetPosition()[1] = targetCol;
        this.GameBoard[targetRow][targetCol] = new Block(entity);
    }

    /*
     * Sets the current active team.
     *
     * @param team - 1 for player one, 2 for player two.
     */
    public void SetCurrTeam(int team) {
        if (team == 1) {
            currTeam = 1;
        } else {
            currTeam = 2;
        }
    }

    /*
     * Returns the current active team.
     *
     * @return - 1 or 2.
     */
    public int GetCurrTeam() {
        return currTeam;
    }

    /*
     * Switches the active team between 1 and 2.
     */
    public void SwitchPlayer() {
        if (currTeam == 1) {
            currTeam = 2;
        } else {
            currTeam = 1;
        }
    }

    /*
     * Determines which team has won by scanning all characters on the board.
     *
     * @return - 1 if team 1 has won, 2 if team 2 has won, 0 if nobody has won yet
     */
    public int GetWinningTeam() {
        boolean team1Alive = false;
        boolean team2Alive = false;

        for (int i = 0; i < this.GameBoard.length; i++) {
            for (int j = 0; j < this.GameBoard[i].length; j++) {
                if (this.GameBoard[i][j] != null && this.GameBoard[i][j].GetEntity() != null
                        && this.GameBoard[i][j].GetEntity().GetObject() == Entity.CHARACTER) {
                    Character c = this.GameBoard[i][j].GetEntity().GetCharacter();
                    if (c != null) {
                        if (c.GetTeam() == 1 && c.GetCurrHealth() > 0) {
                            team1Alive = true;
                        } else if (c.GetTeam() == 2 && c.GetCurrHealth() > 0) {
                            team2Alive = true;
                        }
                    }
                }
            }
        }

        if (!team1Alive && team2Alive) {
            return 2;
        }
        if (!team2Alive && team1Alive) {
            return 1;
        }
        return 0;
    }

    /*
     * Returns true if someone won
     *
     * @return - True if either team has been eliminated.
     */
    public boolean CheckWin() {
        return GetWinningTeam() != 0;
    }
}