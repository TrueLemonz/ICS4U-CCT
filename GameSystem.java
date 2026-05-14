public class GameSystem {
    public Block[][] GameBoard = new Block[GAMEHEIGHT][GAMEWIDTH];
    public static int GAMEHEIGHT = 8;
    public static int GAMEWIDTH = 8;
    private int currTeam;
    Player Player1 = new Player();
    Player Player2 = new Player();

    public GameSystem() {
        refreshGameBoard();
    }


    public boolean Move( Character character, int posX, int posY) {   
        int nullX = character.GetPosition()[0];
        int nullY = character.GetPosition()[1];
        if ( this.gameBoard[posX][posY] == null && character.CheckConditions(0) ) {
            this.gameBoard[posX][posY].SetEntity(character);
            this.gameBoard[nullX][nullY] = null;
            return true;
        }
        return false;
    }

    public void refreshGameBoard(){
        for (int i = 0; i < GAMEHEIGHT; i++) {
            for (int j = 0; j < GAMEWIDTH; j++) {
                this.GameBoard[i][j] = new Block(new Entity(
                    "",
                    false,
                    false,
                    false
                ));
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
        for (int i = 0; i < this.GameBoard.length; i++) {
            for (int j = 0; j < this.GameBoard[i].length; j++) {
                Block block = this.GameBoard[i][j];
                if (block == null || block.getEntity() == null) {
                   continue;
                }
                if (block.getEntity().GetObject() == 0) {
                    emptyCount++; //count number of empty blocks in the grid
                }
            }
        }
        if (emptyCount == 0) { //cancel if grid is full
            return;
        }
        int[][] emptyPositions = new int[emptyCount][2];
        int index = 0;
        
        for (int i = 0; i < this.GameBoard.length; i++) {
            for (int j = 0; j < this.GameBoard[i].length; j++) {
                Block block = this.GameBoard[i][j];
                if (block == null || block.getEntity() == null) {
                    continue;
                }
                if (block.getEntity().GetObject() == 0) {
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

        this.GameBoard[targetRow][targetCol] = new Block(entity); //place the new block at the coordinates
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
    Entity entity = new Entity();
    boolean Team1Lose = true;
    boolean Team2Lose = true;
      for (int i = 0; i < this.GameBoard.length; i++) {
         for (int j = 0; j < this.GameBoard[i].length; j++) {
            if (this.GameBoard[i][j].getEntity().GetObject() == 1) {
                Character c = (Character) this.GameBoard[i][j].getEntity();
                if (c.GetTeam() == 1 && !c.GetIsAlive()) {
                    Team1Lose = false;
                } 
                else if (c.getTeam() == 2 && !c.GetIsAlive()) {
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
    int block = (int) (Math.random() * GameBoard.length * GameBoard[0].length);
    Block current = this.GameBoard[block / GameBoard[0].length][block % GameBoard[0].length];
    if (current != null && current.getEntity() != null && current.getEntity().GetObject() == 0) {
        int rand = (int) (Math.random() * 10); 
        if (rand < 2) { 
            this.GameBoard[block / GameBoard[0].length][block % GameBoard[0].length] = new Block(new Obstacle());
        }
    }
    }
}

                 
         