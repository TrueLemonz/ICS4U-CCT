public class Displayer {
    private static final int CELL_WIDTH = 15; // Define a constant for cell width
    private static final int CELL_PADDING_Y = 1; // Adds a line that many times on both sides of the cell content

    public Displayer() {}

    private void printLine(int colsPerCell, int rowLength) {
        int totalDashes = rowLength * (colsPerCell * (CELL_WIDTH + 1)) + 1;
        for (int i = 0; i < totalDashes; i++) {
            System.out.print("-");
        }
        System.out.print("\n");
    }

    private static String centerString(String text, int width) {
        if (text == null) {
            text = "";
        }
        if (text.length() >= width) {
            return text;
        }
        int padding = width - text.length();
        int leftSpaces = padding / 2;
        int rightSpaces = padding - leftSpaces;
        return " ".repeat(leftSpaces) + text + " ".repeat(rightSpaces);
    }

    public static void centerPrint(String text, int width) {
        System.out.println(centerString(text, width));
    }

    public void PrintGrid(Block[][] grid) {
        if (grid == null || grid.length == 0) {
            return;
        }

        int colsPerCell = 3;
        int rowLength = grid[0].length;
        int totalWidth = rowLength * (colsPerCell * (CELL_WIDTH + 1)) + 1;

        centerPrint("MAP:", totalWidth); 
        System.out.print("\n");
        printLine(colsPerCell, rowLength);
        
        for (int i = 0; i < grid.length; i++) {
            Block[] row = grid[i];

            // Top padding for the entire row
            for (int k = 0; k < CELL_PADDING_Y; k++) {
                for (int j = 0; j < row.length; j++) {
                    for (int c = 0; c < colsPerCell; c++) {
                        System.out.print("|" + centerString("", CELL_WIDTH));
                    }
                }
                System.out.print("|\n");
            }

            // Print row content
            for (int j = 0; j < row.length; j++) {
                Block b = row[j];
                String formattedClass = "";
                String formattedInfo = "";
                String formattedName = "";

                if (b != null && b.getEntity() != null) {
                    formattedClass = String.valueOf(b.getEntity().GetName()) + " Team: " + String.valueOf(b.getEntity().GetTeam());
                    formattedInfo = "Team: " + String.valueOf(b.getEntity().GetTeam());
                    int objectType = b.getEntity().GetObject();
                    if (objectType == 1) {
                        formattedName = b.getEntity().GetFullName();
                    }
                }
                System.out.print("|" + centerString(formattedName, CELL_WIDTH));
                System.out.print("|" + centerString(formattedClass, CELL_WIDTH));
                System.out.print("|" + centerString(formattedInfo, CELL_WIDTH));
            }
            System.out.print("|\n");

            // Bottom padding for the entire row
            for (int k = 0; k < CELL_PADDING_Y; k++) {
                for (int j = 0; j < row.length; j++) {
                    for (int c = 0; c < colsPerCell; c++) {
                        System.out.print("|" + centerString("", CELL_WIDTH));
                    }
                }
                System.out.print("|\n");
            }

            printLine(colsPerCell, row.length);
        }
    }

    public boolean PrintInitialStats(Character[] characters) {
        System.out.println("            INITIAL STATS            ");
        for (int i = 0; i < characters.length; i++) {
            Character c = characters[i];
            if (c == null) {
                return false;
            }
            System.out.println("------------------------------------");
            System.out.println("Character   : " + c.GetName());
            System.out.println("Team        : " + c.GetTeam());
            System.out.println("Speed       : " + c.GetRawStats()[c.SPDPOS]);
            System.out.println("Intelligence: " + c.GetRawStats()[c.INTLPOS]);
            System.out.println("Attack      : " + c.GetRawStats()[c.ATKPOS]);
            System.out.println("Spirit      : " + c.GetRawStats()[c.SPRPOS]);
            System.out.println("Health      : " + c.GetRawStats()[c.HLTPOS]);
            System.out.println("Spell Power : " + c.GetRawStats()[c.SPPPOS]);
            System.out.println("------------------------------------");
        }
        return false;
    }

    public boolean PrintStats(Character[] characters) {
        System.out.println("            CURRENT STATS            ");
        for (int i = 0; i < characters.length; i++) {
            Character c = characters[i];
            if (c.GetName() == null) {
                return false;
            }
            System.out.println("------------------------------------");
            System.out.println("Character   : " + c.GetName());
            System.out.println("Team        : " + c.GetTeam());
            System.out.println("Health      : " + c.GetCurrHealth() + " / " + c.GetCalculatedStats()[c.HLTPOS]);
            System.out.println("------------------------------------");
        }
        return false;
    }
}