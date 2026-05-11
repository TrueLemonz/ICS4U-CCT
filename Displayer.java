public class Displayer {
    public Displayer() {}

    private void PrintLine(int length) {
        for (int i = 0; i < length*GameSystem.GAMEWIDTH + GameSystem.GAMEWIDTH + 1; i++) {
            System.out.print("-");
        }
        System.out.print("\n");
    }

    public void PrintGrid( Block[][] grid ) {
        System.out.println("MAP:");
        System.out.print("\n");
        PrintLine(10);
        for (int i = 0; i < grid.length; i++) {
            Block[] row = grid[i];
            for (int j = 0; j < row.length; j++) {
                Block b = row[j];

                String val = String.valueOf(b.getEntity().getName());
                if (b.getEntity().getObject() == 1) {
                    System.out.print("|" + String.format("%10s", "{" + val + "}"));
                } 
                if (b.getEntity().getObject() == 2) {
                    System.out.print("|" + String.format("%10s", "[" + val + "]"));
                } 
                if (b.getEntity().getObject() == 3) {
                    System.out.print("|" + String.format("%10s", "(" + val + ")"));
                } 
                else if (b.getEntity().getObject() == 0){
                    System.out.print("|" + String.format("%10s", val));
                }
            
            }
            System.out.print("|");
            System.out.print("\n");
            PrintLine(10);
        }
    }
}