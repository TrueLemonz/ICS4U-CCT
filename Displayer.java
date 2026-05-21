/****************************************************
 * Displayer
 * 
 * Holds a handful of helpful frontend functions that-
 * help spread the code appropriately.
 * Main function is to print the Game System's grid each turn.
 * Also handles plenty of other functions for streamlining the ui.
 * 
 * Author: Leo & Lucas
 * Date: 20/05/26
 * **************************************************/
public class Displayer {
    private static final int CELLWIDTH = 15; // Maintains a narrow width to prevent wrapping
    private static final int CELLPADDINGY = 1; 

    public Displayer() {}

    /*
    * Prints a horizontal line of dashes to separate rows in the grid.
    *
    * @param rowLength          - The number of cells in the row to determine how long the line should be.
     */
    private void printLine(int rowLength) {
        int totalDashes = rowLength * (CELLWIDTH + 1) + 1;
        for (int i = 0; i < totalDashes; i++) {
            System.out.print("-");
        }
        System.out.print("\n");
    }

    /*
     * Centers a string within a given width.
     *
     * @param text              - The string to center.
     * @param widt              - The width to center it within.
     * @return - The centered string.
     */
    private String centerString(String text, int width) {
        if (text == null) {
            text = "";
        }
        if (text.length() >= width) {
            return text.substring(0, width);
        }
        
        int padding = width - text.length();
        int leftSpaces = padding / 2;
        
        // make the left padding part
        String leftPadded = String.format("%" + (leftSpaces + text.length()) + "s", text);
        
        // add the right padding part so that its in the center altogether
        return String.format("%-" + width + "s", leftPadded);
    }

    /*
     * Prints a string centered within a given width.
     *
     * @param text              - The string to print.
     * @param width             - The width to center it within.
     */
    private void centerPrint(String text, int width) {
        System.out.println(centerString(text, width));
    }
    
    /*
     * Prints the game grid.
     *
     * @param grid              - The 2D array of blocks representing the game board.
     */
    public void PrintGrid(Block[][] grid) {
        if (grid == null || grid.length == 0) {
            return;
        }

        int rowLength = grid[0].length;
        int totalWidth = rowLength * ((CELLWIDTH + 1)) + 1;

        centerPrint("MAP:", totalWidth); 
        System.out.print("\n");
        System.out.printf("    %-16s%-16s%-16s%-16s%-16s%-16s%-16s%-16s", "CELL X = 0", "CELL X = 1", "CELL X = 2", "CELL X = 3", "CELL X = 4", "CELL X = 5", "CELL X = 6", "CELL X = 7");
        System.out.print("\n");
        printLine(rowLength);
        
        for (int i = 0; i < grid.length; i++) {
            Block[] row = grid[i];

            // Top padding 
            for (int k = 0; k < CELLPADDINGY; k++) {
                for (int j = 0; j < row.length; j++) {
                    System.out.print("|" + centerString("", CELLWIDTH));
                }
                System.out.print("|\n");
            }

            // Print Row Content: First vertical line (Names)
            for (int j = 0; j < row.length; j++) {
                Block b = row[j];
                String formattedName = "";

                if (b != null && b.GetEntity() != null) {
                    if (b.GetEntity().GetObject() == Entity.CHARACTER) {
                        formattedName = b.GetEntity().GetFullName();
                    }
                }
                System.out.print("|" + centerString(formattedName, CELLWIDTH));
            }
            System.out.print("|\n");

            // Print Row Content: Second vertical line (Classes)
            for (int j = 0; j < row.length; j++) {
                Block b = row[j];
                String formattedClass = "";

                if (b != null && b.GetEntity() != null) {
                    formattedClass = String.valueOf(b.GetEntity().GetName());
                }
                System.out.print("|" + centerString(formattedClass, CELLWIDTH));
            }
            System.out.print("| CELL Y = " + i + "\n"); 

            // Print Row Content: Third vertical line (Teams)
            for (int j = 0; j < row.length; j++) {
                Block b = row[j];
                String formattedInfo = "";

                if (b != null && b.GetEntity() != null) {
                    if (b.GetEntity().GetObject() == Entity.CHARACTER || b.GetEntity().GetObject() == Entity.MINION) {
                        formattedInfo = "Team: " + String.valueOf(b.GetEntity().GetTeam());
                    }
                }
                System.out.print("|" + centerString(formattedInfo, CELLWIDTH));
            }
            System.out.print("|\n");

            // Bottom padding 
            for (int k = 0; k < CELLPADDINGY; k++) {
                for (int j = 0; j < row.length; j++) {
                    System.out.print("|" + centerString("", CELLWIDTH));
                }
                System.out.print("|\n");
            }

            printLine(row.length);
            
        }
        System.out.printf("    %-16s%-16s%-16s%-16s%-16s%-16s%-16s%-16s", "CELL X = 0", "CELL X = 1", "CELL X = 2", "CELL X = 3", "CELL X = 4", "CELL X = 5", "CELL X = 6", "CELL X = 7");
        System.out.print("\n");
    }

    /*
     * Prints the initial statistics for a list of characters.
     *
     * @param characters        - The array of characters to display stats for.
     * @return                  - True if successful, False otherwise.
     */
    public boolean PrintInitialStats(Character[] characters) {
        System.out.println("            INITIAL STATS            ");
        for (int i = 0; i < characters.length; i++) {
            Character c = characters[i];
            if (c == null) {
                return false;
            }
            System.out.println("------------------------------------");
            System.out.println("Name           : " + c.GetFullName());
            System.out.println("Team           : " + c.GetTeam());
            System.out.println("Speed          : " + c.GetRawStats()[Character.SPDPOS]);
            System.out.println("Intelligence   : " + c.GetRawStats()[Character.INTLPOS]);
            System.out.println("Attack         : " + c.GetRawStats()[Character.ATKPOS]);
            System.out.println("Spirit         : " + c.GetRawStats()[Character.MGCPOS]);
            System.out.println("Health         : " + c.GetRawStats()[Character.HLTPOS]);
            System.out.println("Spell Power    : " + c.GetRawStats()[Character.SPPPOS]);
            System.out.println("------------------------------------");
        }
        return false;
    }

    /*
     * Prints the current statistics for a list of characters.
     *
     * @param characters        - The array of characters to display stats for.
     * @return                  - True if successful, False otherwise.
     */
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
            System.out.println("Health      : " + c.GetCurrHealth() + " / " + c.GetCalculatedStats()[Character.HLTPOS]);
            System.out.println("Magic       : " + c.GetCurrMagic() + " / " + c.GetCalculatedStats()[Character.MGCPOS]);
            System.out.println("Steps       : 2 places");
            System.out.println("------------------------------------");
        }
        return false;
    }

    /*
     * Prints the game rules.
     */
    public void PrintRules() {
        System.out.println("\n=================== GAMEPLAY RULES ===================");
        System.out.println("1. MOVEMENT & GRID RULES:");
        System.out.println("   - The board is an 8x8 grid of blocks.");
        System.out.println("   - You can only land on tiles with no object types.");
        System.out.println("   - Eating food restores 15 HP up to your Max Health.");
        System.out.println("\n2. STAT SCALING MATHEMATICS:");
        System.out.println("   - Intelligence -> scaled by 2.5x to calculate true spell power metrics.");
        System.out.println("   - Attack       -> scaled by 9.5x to determine total melee strike damage.");
        System.out.println("   - Spirit/Magic -> multiplied by 2x to establish your maximum mana pool.");
        System.out.println("   - Health       -> scaled by 9.0x to calculate maximum total life pool.");
        System.out.println("\n3. COMBAT TURNS & ACTION ROUNDS:");
        System.out.println("   - Team priority goes to whichever group has the highest total Speed stat.");
        System.out.println("   - Characters execute actions in sequence matching internal speed indexes.");
        System.out.println("   - Stunned characters are completely blocked from movement or casting skill paths.");
        System.out.println("   - All alive units regenerate 1 Mana and 1 Health at the end of every round cycle.");
        System.out.println("======================================================\n");
    }
}


//Does this work