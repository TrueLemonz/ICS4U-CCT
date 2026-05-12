import java.util.Scanner;

public class RPGMain {
    public static void main(String[] args) {
        GameSystem gs = new GameSystem();
        Displayer ds = new Displayer();
        Scanner scanner = new Scanner(System.in);
        boolean gameRunning = true;
        int PlayerChoice;
        

        while (gameRunning) {
            System.out.println("Welcome to the RPG Game!");
            System.out.println("What would you like to do? \n1. Start Game \n2. Hall of fame\n3. Exit");
            PlayerChoice = scanner.nextInt();
            if (PlayerChoice == 1) {
                for (int i = 1; i <= 2; i++) {
                    System.out.println("Initializing Player " + i + "'s team...");
                    for (int j = 0; j < 3; j++) {
                        boolean HasSelected = false;
                        int SelectedClass = -1;
                        Character ClasslesCharacter = new Character().GenerateCharacter();
                        ClasslesCharacter.SetTeam(i);
                        ClasslesCharacter.SetName("John RPG");
                        while (!HasSelected) {
                            System.out.println("Character " + (j + 1) + ":");
                            ds.PrintInitialStats(new Character[]{ClasslesCharacter});
                            ClasslesCharacter.SetTeam(1);
                            System.out.println("Which class would you like your character to be? (can back out) \n1. Necromancer \n2. Healer \n3. Crusader \n4. Barbarian \n5. Paladin \n6. Guardian");
                            int choice = scanner.nextInt();
                            if (choice == 1) {
                                System.out.println("Necromancer \nAn intelligent and powerful mage who can summon minions to fight.\nAbilities: \nSpecial: Summon Minion - Summons a weak minion to fight. ");
                                System.out.println("Ability 1: Buff Minion - Buffs an adjacent minion \nAbility 2: Meat Sheild - Sacrafice an adjacent minion to tank a hit.");
                                System.out.println("Stat buffs: \n-1 speed\n+3 intelligence\n+1 attack\n+2 spirit\n+1 health\n+2 spellpower");
                                System.out.println("Would you like to select this class? (y/N)");
                                String confirm = scanner.next();
                                if (confirm.equalsIgnoreCase("Y")) {
                                    SelectedClass = 1;
                                    HasSelected = true;
                                }
                                else {
                                    System.out.println("Returning to selection.");
                                }
                            } 
                            else if (choice == 2) {
                                System.out.println("Healer \nAn intelligent and resilient mage who can heal allies and provide buffs.\nAbilities: \nSpecial: Prayer - Heals allies, and damages enemies. ");
                                System.out.println("Ability 1: Praise - Grants a +4 magic and +4 stamina buff to an ally. \nAbility 2: Strike - Weak attack that may stun.");
                                System.out.println("Stat buffs: \n+0 speed\n+3 intelligence\n-1 attack\n+3 spirit\n+2 health\n+1 spellpower");
                                System.out.println("Would you like to select this class? (y/N)");
                                String confirm = scanner.next();
                                if (confirm.equalsIgnoreCase("Y")) {
                                    SelectedClass = 2;
                                    HasSelected = true;
                                }
                                else {
                                    System.out.println("Returning to selection.");
                                }
                            } 
                            else if (choice == 3) { //TODO make this accurate
                                System.out.println("Crusader \nA sturdy warrior who can protect allies and deal significant damage.\nAbilities: \nSpecial: Divine Shield - Creates a protective barrier around an adjacent ally. ");
                                System.out.println("Ability 1: Holy Light - Heals an adjacent ally. \nAbility 2: Condemn - Deals damage to an enemy.");
                                System.out.println("Stat buffs: \n+2 speed\n+2 intelligence\n+2 attack\n+1 spirit\n+3 health\n+1 spellpower");
                                System.out.println("Would you like to select this class? (y/N)");
                                String confirm = scanner.next();
                                if (confirm.equalsIgnoreCase("Y")) {
                                    SelectedClass = 3;
                                    HasSelected = true;
                                }
                                else {
                                    System.out.println("Returning to selection.");
                                }
                            } 
                            else if (choice == 4) { //TODO make this accurate
                                System.out.println("Healer \nAn intelligent and resilient mage who can heal allies and provide buffs.\nAbilities: \nSpecial: Prayer - Heals allies, and damages enemies. ");
                                System.out.println("Ability 1: Praise - Grants a +4 magic and +4 stamina buff to an ally. \nAbility 2: Strike - Weak attack that may stun.");
                                System.out.println("Stat buffs: \n+0 speed\n+3 intelligence\n-1 attack\n+3 spirit\n+2 health\n+1 spellpower");
                                System.out.println("Would you like to select this class? (y/N)");
                                String confirm = scanner.next();
                                if (confirm.equalsIgnoreCase("Y")) {
                                    SelectedClass = 4;
                                    HasSelected = true;
                                }
                                else {
                                    System.out.println("Returning to selection.");
                                }
                            } 
                            else if (choice == 5) { //TODO make this accurate
                                System.out.println("Healer \nAn intelligent and resilient mage who can heal allies and provide buffs.\nAbilities: \nSpecial: Prayer - Heals allies, and damages enemies. ");
                                System.out.println("Ability 1: Praise - Grants a +4 magic and +4 stamina buff to an ally. \nAbility 2: Strike - Weak attack that may stun.");
                                System.out.println("Stat buffs: \n+0 speed\n+3 intelligence\n-1 attack\n+3 spirit\n+2 health\n+1 spellpower");
                                System.out.println("Would you like to select this class? (y/N)");
                                String confirm = scanner.next();
                                if (confirm.equalsIgnoreCase("Y")) {
                                    SelectedClass = 5;
                                    HasSelected = true;
                                }
                                else {
                                    System.out.println("Returning to selection.");
                                }
                            } 
                            else if (choice == 6) { //TODO make this accurate
                                System.out.println("Healer \nAn intelligent and resilient mage who can heal allies and provide buffs.\nAbilities: \nSpecial: Prayer - Heals allies, and damages enemies. ");
                                System.out.println("Ability 1: Praise - Grants a +4 magic and +4 stamina buff to an ally. \nAbility 2: Strike - Weak attack that may stun.");
                                System.out.println("Stat buffs: \n+0 speed\n+3 intelligence\n-1 attack\n+3 spirit\n+2 health\n+1 spellpower");
                                System.out.println("Would you like to select this class? (y/N)");
                                String confirm = scanner.next();
                                if (confirm.equalsIgnoreCase("Y")) {
                                    SelectedClass = 6;
                                    HasSelected = true;
                                }
                                else {
                                    System.out.println("Returning to selection.");
                                }
                            } 
                            else {
                                System.out.println("Invalid choice, please try again.");
                            }
                        }
                        if (SelectedClass == 1) {
                            gs.Player1.PlayerTeam[i] = new Necromancer(ClasslesCharacter);
                        }
                        else if (SelectedClass == 2) {
                            gs.Player1.PlayerTeam[i] = new Healer(ClasslesCharacter);
                        }
                        else if (SelectedClass == 3) {
                            //gs.Player1.PlayerTeam[i] = new Crusader(ClasslesCharacter);
                        }
                        else if (SelectedClass == 4) {
                            gs.Player1.PlayerTeam[i] = new Barbarian(ClasslesCharacter);
                        }
                        else if (SelectedClass == 5) {
                            //gs.Player1.PlayerTeam[i] = new Paladin(ClasslesCharacter);
                        }
                        else if (SelectedClass == 6) {
                            //gs.Player1.PlayerTeam[i] = new Guardian(ClasslesCharacter);
                        }
                    } // End of generating 3 characters
                    System.out.println("PLAYER " + i + " TEAM:");
                    for (int k = 0; k < 3; k++) {
                        ds.PrintInitialStats(new Character[]{gs.Player1.PlayerTeam[k]});
                    }
                    System.out.println("Now initializing next player's team. Please hand over the computer. Press enter when ready.");
                    scanner.nextLine();
                } //End of generating 2 teams
            } //TODO add the other two options
        }
        // gs.refreshGameBoard();
        // gs.populateGameBoard(5, 5);
        // ds.PrintGrid(gs.gameBoard);
    }
}