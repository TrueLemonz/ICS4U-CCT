public class Displayer {
    private static final int CELL_WIDTH = 15; // Define a constant for cell width
    private static final int CELL_PADDING_Y = 1; // Adds a line that many times on both sides of the cell content

    public Displayer() {}

    private void printLine(int length) {
        for (int i = 0; i < length*GameSystem.GAMEWIDTH + GameSystem.GAMEWIDTH + 1; i++) {
            System.out.print("-");
        }
        System.out.print("\n");
    }

    private static String centerString(String text, int width) {
        if (text.length() >= width) {
            return text;
        }
        int padding = width - text.length();
        int leftSpaces = padding / 2;
        int rightSpaces = padding - leftSpaces;
        return String.format("%" + leftSpaces + "s%s%" + rightSpaces + "s", "", text, "");
    }

    // print 
    public static void centerPrint(String text, int width) {
        System.out.println(centerString(text, width));
    }

    public void PrintGrid(Block[][] grid) {
        centerPrint("MAP:", CELL_WIDTH * GameSystem.GAMEWIDTH + GameSystem.GAMEWIDTH + 1); 
        System.out.print("\n");
        printLine(CELL_WIDTH);
        
        for (int i = 0; i < grid.length; i++) {
            Block[] row = grid[i];

            // top padding for the entire row
            for (int k = 0; k < CELL_PADDING_Y; k++) {
                for (int j = 0; j < row.length; j++) {
                    System.out.print("|" + centerString("", CELL_WIDTH));
                }
                System.out.print("|\n");
            }

            // print row content
            for (int j = 0; j < row.length; j++) {
                Block b = row[j];

                String val = String.valueOf(b.getEntity().GetName());
                String formattedVal = "";
                
                if (b.getEntity().GetObject() == 1) {
                    formattedVal = "{" + val + "}";
                } else if (b.getEntity().GetObject() == 2) {
                    formattedVal = "[" + val + "]";
                } else if (b.getEntity().GetObject() == 3) {
                    formattedVal = "(" + val + ")";
                } else if (b.getEntity().GetObject() == 0) {
                    formattedVal = val;
                }

                System.out.print("|" + centerString(formattedVal, CELL_WIDTH));
            }
            System.out.print("|\n");

            // bottom padding for the entire row
            for (int k = 0; k < CELL_PADDING_Y; k++) {
                for (int j = 0; j < row.length; j++) {
                    System.out.print("|" + centerString("", CELL_WIDTH));
                }
                System.out.print("|\n");
            }

            printLine(CELL_WIDTH);
        }
    }
}