public class GameSystem {
    public Block[][] gameBoard = new Block[GAMEHEIGHT][GAMEWIDTH];
    public static int GAMEHEIGHT = 8;
    public static int GAMEWIDTH = 8;
    private int currTeam;
    Player Player1 = new Player();
    Player Player2 = new Player();

    public GameSystem(){};

    public void refreshGameBoard(){
        for (int i = 0; i < GAMEHEIGHT; i++) {
            for (int j = 0; j < GAMEWIDTH; j++) {
                this.gameBoard[i][j] = new Block(new Entity(
                    "",
                    false,
                    false,
                    false
                ));
            }
        }
    }

    public void populateGameBoard(int obstacleCount, int foodCount) {
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
                if (this.gameBoard[i][j].getEntity().GetObject() == 0) {
                    emptyCount++; //count number of empty blocks in the grid
                }
            }
        }
        if (emptyCount == 0) { //cancel if grid is full
            return;
        }
        int[][] emptyPositions = new int[emptyCount][2];
        int index = 0;
        
        for (int i = 0; i < this.gameBoard.length; i++) {
            for (int j = 0; j < this.gameBoard[i].length; j++) {
                if (this.gameBoard[i][j].getEntity().GetObject() == 0) {
                    emptyPositions[index][0] = i; //stores empty row coordinate
                    emptyPositions[index][1] = j; //stores empty column coordinate
                    index++;
                }
            }
        }
        //randomly pick one of the empty coordinates
        int randomIndex = (int) (Math.random() * emptyCount);
        int targetRow = emptyPositions[randomIndex][0];
        int targetCol = emptyPositions[randomIndex][1];

        this.gameBoard[targetRow][targetCol] = new Block(entity); //place the new block at the coordinates
    }
    
    // Sets the current team playin
    public void SetCurrTeam(int team) {
      if (team == 1) {
         currTeam = 1;
      } 
      else {
         currTeam = 2;
      }
   }
   // Getter method, returns current team playing
   public int GetCurrTeam() {
      return currTeam;
   }
   // Switches player for second round of the turn
   public void SwitchPlayer() {
      if (currTeam == 1) {
         currTeam = 2;
      } 
      else {
         currTeam = 1;
      }
   }

   public boolean CheckWin() {
    boolean Team1Lose = true;
    boolean Team2Lose = true;
      for (int i = 0; i < this.gameBoard.length; i++) {
         for (int j = 0; j < this.gameBoard[i].length; j++) {
            if (this.gameBoard[i][j].getEntity().GetObject() == 1) {
                Character c = (Character) this.gameBoard[i][j].getEntity();
                if (c.GetTeam() == 1 && !c.GetIsAlive()) {
                    Team1Lose = false;
                } 
                else if (c.GetTeam() == 2 && !c.GetIsAlive()) {
                    Team2Lose = false;
                }
            }
         }
      }
      if (Team1Lose) {
         return true;
      } 
      else if (Team2Lose) {
         return true;
      }
      return false;
   }

   public void GenRandObstacles() {
    int block = (int) (Math.random() * gameBoard.length * gameBoard[0].length);
     if (this.gameBoard[block / gameBoard[0].length][block % gameBoard[0].length].getEntity().GetObject() == 0) {
        int rand = (int) (Math.random() * 10); 
            if (rand < 2) { 
                this.gameBoard[block / gameBoard[0].length][block % gameBoard[0].length] = new Block(new Obstacle());
            }
        }
    }
}

                 
         