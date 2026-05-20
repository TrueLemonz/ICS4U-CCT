public class GameSystem {
    public Block[][] gameBoard = new Block[GAMEHEIGHT][GAMEWIDTH];
    public static int GAMEHEIGHT = 8;
    public static int GAMEWIDTH = 8;
    private int currTeam;
    Player player1 = new Player();
    Player player2 = new Player();

    public GameSystem() {
        RefreshGameBoard();
    }

    public void RegenerateCharacters(Character[] team) {
        for (int i = 0; i < team.length-1; i++) {
            if(team[i].GetCurrMagic() < team[i].GetCalculatedStats()[Character.MAXMAGICPOS]) {
                team[i].SetCurrMagic(team[i].GetCurrMagic()+1);
            }
            if(team[i].GetCurrHealth() < team[i].GetCalculatedStats()[Character.MAXHEALTHPOS]) {
                team[i].SetCurrHealth(team[i].GetCurrHealth()+1);
            }
        }
    }

    public boolean Move(Character character, int posX, int posY) {
        int nullY = character.GetPosition()[0]; // [0] = y
        int nullX = character.GetPosition()[1]; // [1] = x

        if (posX < 0 || posY < 0 || posY >= GAMEHEIGHT || posX >= GAMEWIDTH || Math.abs(posX - nullX) > (character.GetCalculatedStats()[Character.SPEEDPOS])
                || Math.abs(posY - nullY) > ((int)( 1 + character.GetCalculatedStats()[Character.SPEEDPOS]) ) / 2) {
            return false;
        }

        if (this.gameBoard[posY][posX] != null && character.CheckConditions(0)
                && this.gameBoard[posY][posX].getEntity().GetObject() == Entity.NONE) {
            this.gameBoard[posY][posX].SetEntity(character);

            character.GetPosition()[0] = posY; // [0] = y
            character.GetPosition()[1] = posX; // [1] = x

            this.gameBoard[nullY][nullX] = new Block(new Entity("", false, false, false, false));

            return true;
        }
        return false;
    }

    public void RefreshGameBoard() {
        for (int i = 0; i < GAMEHEIGHT; i++) {
            for (int j = 0; j < GAMEWIDTH; j++) {
                this.gameBoard[i][j] = new Block(new Entity(
                        "",
                        false,
                        false,
                        false,
                        false));
            }
        }
    }

    public void PopulateGameBoard(int obstacleCount, int foodCount) {
        for (int i = 0; i < obstacleCount; i++) {
            RandBlock(new Obstacle());
        }
        for (int i = 0; i < foodCount; i++) {
            RandBlock(new Food());
        }
    }

    public void RandBlock(Entity entity) {
        int emptyCount = 0;
        for (int i = 0; i < this.gameBoard.length; i++) {
            for (int j = 0; j < this.gameBoard[i].length; j++) {
                Block block = this.gameBoard[i][j];
                if (block == null || block.getEntity() == null) {
                    continue;
                }
                if (block.getEntity().GetObject() == Entity.NONE) {
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
                if (block.getEntity().GetObject() == Entity.NONE) {
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
    public int GetCurrTeam() {
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

   

    public int GetWinningTeam() {
        boolean team1Alive = false;
        boolean team2Alive = false;
        for (int i = 0; i < this.gameBoard.length; i++) {
            for (int j = 0; j < this.gameBoard[i].length; j++) {
                if (this.gameBoard[i][j] != null && this.gameBoard[i][j].getEntity() != null
                        && this.gameBoard[i][j].getEntity().GetObject() == Entity.CHARACTER) {
                    Character c = (Character) this.gameBoard[i][j].getEntity();
                    if (c.GetTeam() == 1 && c.GetCurrHealth() > 0) {
                        team1Alive = true;
                    } else if (c.GetTeam() == 2 && c.GetCurrHealth() > 0) {
                        team2Alive = true;
                    }
                }
            }
        }
        if (!team1Alive && team2Alive) {
            return 2;
        } else if (!team2Alive && team1Alive) {
            return 1;
        }
        return 0;
    }

    public boolean CheckWin() {
        return GetWinningTeam() != 0;
    }
    
  
    public void GenRandObstacles() {
        int block = (int) (Math.random() * gameBoard.length * gameBoard[0].length);
        Block current = this.gameBoard[block / gameBoard[0].length][block % gameBoard[0].length];
        if (current != null && current.getEntity() != null && current.getEntity().GetObject() == Entity.NONE) {
            int rand = (int) (Math.random() * 10);
            if (rand < 2) {
                this.gameBoard[block / gameBoard[0].length][block % gameBoard[0].length] = new Block(new Obstacle());
            }
        }
    }
}
