public class GameSystem {
    public Block[][] gameBoard;
    public static int GAMEHEIGHT = 8;
    public static int GAMEWIDTH = 8;

    public void refreshGameBoard(){
        for (int i = 0; i < GAMEHEIGHT; i++) {
            for (int j = 0; j < GAMEWIDTH; j++) {
                this.gameBoard[i][j] = null;
            }
        }
    }
    public void RandBlock(Entity entity) {
        int emptyCount = 0;
        for (int i = 0; i < this.gameBoard.length; i++) {
            for (int j = 0; j < this.gameBoard[i].length; j++) {
                if (this.gameBoard[i][j] == null) {
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
                if (this.gameBoard[i][j] == null) {
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


}