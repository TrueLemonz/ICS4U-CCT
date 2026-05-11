public class GameSystem {
    public Block[][] gameBoard = new Block[GAMEHEIGHT][GAMEWIDTH];
    public static int GAMEHEIGHT = 8;
    public static int GAMEWIDTH = 8;
    private int currTeam;

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

   public void DistributeRNGStats() {
    for (int i = 0; i < this.gameBoard.length; i++) {
            for (int j = 0; j < this.gameBoard[i].length; j++) {
                if (this.gameBoard[i][j].getEntity().GetObject() == 1) {
                    Character c = (Character) this.gameBoard[i][j].getEntity();
                    for ( int k = 0; k < 20; k++ ) {
                        int rand = (int) (Math.random() * 6);
                        if (rand == 0 && c.spd <= 8) {
                            c.spd++;
                        } 
                        else if (rand == 1 && c.intl <= 8) {
                            c.intl++;
                        }
                        else if (rand == 2 && c.atk <= 8) {
                            c.atk++;
                        } 
                        else if (rand == 3 && c.spr <= 8) {
                            c.spr++;
                        }
                        else if (rand == 4 && c.hlt <= 8) {
                            c.hlt++;
                        } 
                        else if (rand == 5 && c.spp <= 8) {
                            c.spp++;
                        }
                        else {
                            k--;
                        }
                    }
                }
           }
       }
   }
}