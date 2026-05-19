public class GameSystem {
    public Block[][] gameBoard = new Block[GAMEHEIGHT][GAMEWIDTH];
    public static int GAMEHEIGHT = 8;
    public static int GAMEWIDTH = 8;
    private int currTeam;
    Player player1 = new Player();
    Player player2 = new Player();

    public GameSystem() {
        refreshGameBoard();
    }

    public boolean Move(Character character, int posX, int posY) {
        int nullY = character.getPosition()[0]; // [0] = y
        int nullX = character.getPosition()[1]; // [1] = x

        if (posX < 0 || posY < 0 || posY >= GAMEHEIGHT || posX >= GAMEWIDTH || Math.abs(posX - nullX) > 1
                || Math.abs(posY - nullY) > 1) {
            return false;
        }

        if (this.gameBoard[posY][posX] != null && character.CheckConditions(0)
                && this.gameBoard[posY][posX].getEntity().getObject() == 0) {
            this.gameBoard[posY][posX].SetEntity(character);

            character.getPosition()[0] = posY; // [0] = y
            character.getPosition()[1] = posX; // [1] = x

            this.gameBoard[nullY][nullX] = new Block(new Entity("", false, false, false));

            return true;
        }
        return false;
    }

    public void refreshGameBoard() {
        for (int i = 0; i < GAMEHEIGHT; i++) {
            for (int j = 0; j < GAMEWIDTH; j++) {
                this.gameBoard[i][j] = new Block(new Entity(
                        "",
                        false,
                        false,
                        false));
            }
        }
    }

    public void PopulateGameBoard(int obstacleCount, int foodCount) {
        for (int i = 0; i < obstacleCount; i++) {
            randBlock(new Obstacle());
        }
        for (int i = 0; i < foodCount; i++) {
            randBlock(new Food());
        }
    }

    public void randBlock(Entity entity) {
        int emptyCount = 0;
        for (int i = 0; i < this.gameBoard.length; i++) {
            for (int j = 0; j < this.gameBoard[i].length; j++) {
                Block block = this.gameBoard[i][j];
                if (block == null || block.getEntity() == null) {
                    continue;
                }
                if (block.getEntity().getObject() == 0) {
                    emptyCount++; // count number of empty blocks in the grid
                }
            }
        }
        if (emptyCount == 0) { // cancel if grid is full
            return;
        }
        int[][] emptyPositions = new int[emptyCount][2];
        int index = 0;

        for (int i = 0; i < this.gameBoard.length; i++) {
            for (int j = 0; j < this.gameBoard[i].length; j++) {
                Block block = this.gameBoard[i][j];
                if (block == null || block.getEntity() == null) {
                    continue;
                }
                if (block.getEntity().getObject() == 0) {
                    emptyPositions[index][0] = i; // stores empty row coordinate
                    emptyPositions[index][1] = j; // stores empty column coordinate
                    index++;
                }
            }
        }
        // randomly pick one of the empty coordinates
        int randomIndex = (int) (Math.random() * emptyCount);
        int targetRow = emptyPositions[randomIndex][0];
        int targetCol = emptyPositions[randomIndex][1];

        this.gameBoard[targetRow][targetCol] = new Block(entity); // place the new block at the coordinates
    }

    // Sets the current team playin
    public void SetCurrTeam(int team) {
        if (team == 1) {
            currTeam = 1;
        } else {
            currTeam = 2;
        }
    }

    // getter method, returns current team playing
    public int getCurrTeam() {
        return currTeam;
    }

    // Switches player for second round of the turn
    public void SwitchPlayer() {
        if (currTeam == 1) {
            currTeam = 2;
        } else {
            currTeam = 1;
        }
    }

    public boolean CheckPlayer1Win() {
        boolean team2Lose = true;
        for (int i = 0; i < this.gameBoard.length; i++) {
            for (int j = 0; j < this.gameBoard[i].length; j++) {
                if (this.gameBoard[i][j].getEntity().getObject() == 1) {
                    Character c = (Character) this.gameBoard[i][j].getEntity();
                    if ( c.getTeam() == 2 && c.getIsAlive() ) {
                        team2Lose = false;
                    }
                }
            }
        }
        return team2Lose;
    }

    public boolean CheckPlayer2Win() {
        boolean team1Lose = true;
        for (int i = 0; i < this.gameBoard.length; i++) {
            for (int j = 0; j < this.gameBoard[i].length; j++) {
                if (this.gameBoard[i][j].getEntity().getObject() == 1) {
                    Character c = (Character) this.gameBoard[i][j].getEntity();
                    if ( c.getTeam() == 1 && c.getIsAlive() ) {
                        team1Lose = false;
                    }
                }
            }
        }
        return team1Lose;
    }

    public void GenRandObstacles() {
        int block = (int) (Math.random() * gameBoard.length * gameBoard[0].length);
        Block current = this.gameBoard[block / gameBoard[0].length][block % gameBoard[0].length];
        if (current != null && current.getEntity() != null && current.getEntity().getObject() == 0) {
            int rand = (int) (Math.random() * 10);
            if (rand < 2) {
                this.gameBoard[block / gameBoard[0].length][block % gameBoard[0].length] = new Block(new Obstacle());
            }
        }
    }
}
