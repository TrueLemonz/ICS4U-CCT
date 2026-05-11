public class RPGMain {
    public static void main(String[] args) {
        GameSystem gs = new GameSystem();
        Displayer ds = new Displayer();
        gs.refreshGameBoard();
        gs.populateGameBoard(5, 5);
        ds.PrintGrid(gs.gameBoard);
    }
}