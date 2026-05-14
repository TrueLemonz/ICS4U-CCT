import java.util.Scanner;

public class RPGMain {
    public static void main(String[] args) {
        GameSystem gs = new GameSystem();
        Displayer ds = new Displayer();
        Scanner Input = new Scanner(System.in);
        boolean GameRunning = true;
        int PlayerChoice;
        

        while (GameRunning) {
            System.out.println("Welcome to the RPG Game!");
            System.out.println("What would you like to do? \n1. Start Game \n2. Hall of fame\n3. Exit");
            PlayerChoice = Input.nextInt();
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
                            System.out.println("Which class would you like your character to be? (can back out) \n1. Necromancer \n2. Healer \n3. Crusader \n4. Barbarian \n5. Paladin \n6. Guardian");
                            int Choice = Input.nextInt();
                            if (Choice == 1) {
                                System.out.println("Necromancer \nAn intelligent and powerful mage who can summon minions to fight.\nAbilities: \nSpecial: Summon Minion - Summons a weak minion to fight. ");
                                System.out.println("Ability 1: Buff Minion - Buffs an adjacent minion \nAbility 2: Meat Sheild - Sacrafice an adjacent minion to tank a hit.");
                                System.out.println("Stat buffs: \n-1 speed\n+3 intelligence\n+1 attack\n+2 spirit\n+1 health\n+2 spellpower");
                                System.out.println("Would you like to select this class? (y/N)");
                                String confirm = Input.next();
                                if (confirm.equalsIgnoreCase("Y")) {
                                    SelectedClass = 1;
                                    HasSelected = true;
                                }
                                else {
                                    System.out.println("Returning to selection.");
                                }
                            } 
                            else if (Choice == 2) {
                                System.out.println("Healer \nAn intelligent and resilient mage who can heal allies and provide buffs.\nAbilities: \nSpecial: Prayer - Heals allies, and damages enemies. ");
                                System.out.println("Ability 1: Praise - Grants a +4 magic and +4 stamina buff to an ally. \nAbility 2: Strike - Weak attack that may stun.");
                                System.out.println("Stat buffs: \n+0 speed\n+3 intelligence\n-1 attack\n+3 spirit\n+2 health\n+1 spellpower");
                                System.out.println("Would you like to select this class? (y/N)");
                                String confirm = Input.next();
                                if (confirm.equalsIgnoreCase("Y")) {
                                    SelectedClass = 2;
                                    HasSelected = true;
                                }
                                else {
                                    System.out.println("Returning to selection.");
                                }
                            } 
                            else if (Choice == 3) { //TODO make this accurate
                                System.out.println("Crusader \nA sturdy warrior who can protect allies and deal significant damage.\nAbilities: \nSpecial: Divine Shield - Creates a protective barrier around an adjacent ally. ");
                                System.out.println("Ability 1: Holy Light - Heals an adjacent ally. \nAbility 2: Condemn - Deals damage to an enemy.");
                                System.out.println("Stat buffs: \n+0 speed\n+2 intelligence\n+1 attack\n+1 spirit\n+3 health\n+1 spellpower");
                                System.out.println("Would you like to select this class? (y/N)");
                                String confirm = Input.next();
                                if (confirm.equalsIgnoreCase("Y")) {
                                    SelectedClass = 3;
                                    HasSelected = true;
                                }
                                else {
                                    System.out.println("Returning to selection.");
                                }
                            } 
                            else if (Choice == 4) { //TODO make this accurate
                                System.out.println("Healer \nAn intelligent and resilient mage who can heal allies and provide buffs.\nAbilities: \nSpecial: Prayer - Heals allies, and damages enemies. ");
                                System.out.println("Ability 1: Praise - Grants a +4 magic and +4 stamina buff to an ally. \nAbility 2: Strike - Weak attack that may stun.");
                                System.out.println("Stat buffs: \n+0 speed\n+3 intelligence\n-1 attack\n+3 spirit\n+2 health\n+1 spellpower");
                                System.out.println("Would you like to select this class? (y/N)");
                                String confirm = Input.next();
                                if (confirm.equalsIgnoreCase("Y")) {
                                    SelectedClass = 4;
                                    HasSelected = true;
                                }
                                else {
                                    System.out.println("Returning to selection.");
                                }
                            } 
                            else if (Choice == 5) { //TODO make this accurate
                                System.out.println("Healer \nAn intelligent and resilient mage who can heal allies and provide buffs.\nAbilities: \nSpecial: Prayer - Heals allies, and damages enemies. ");
                                System.out.println("Ability 1: Praise - Grants a +4 magic and +4 stamina buff to an ally. \nAbility 2: Strike - Weak attack that may stun.");
                                System.out.println("Stat buffs: \n+0 speed\n+3 intelligence\n-1 attack\n+3 spirit\n+2 health\n+1 spellpower");
                                System.out.println("Would you like to select this class? (y/N)");
                                String confirm = Input.next();
                                if (confirm.equalsIgnoreCase("Y")) {
                                    SelectedClass = 5;
                                    HasSelected = true;
                                }
                                else {
                                    System.out.println("Returning to selection.");
                                }
                            } 
                            else if (Choice == 6) { //TODO make this accurate
                                System.out.println("Healer \nAn intelligent and resilient mage who can heal allies and provide buffs.\nAbilities: \nSpecial: Prayer - Heals allies, and damages enemies. ");
                                System.out.println("Ability 1: Praise - Grants a +4 magic and +4 stamina buff to an ally. \nAbility 2: Strike - Weak attack that may stun.");
                                System.out.println("Stat buffs: \n+0 speed\n+3 intelligence\n-1 attack\n+3 spirit\n+2 health\n+1 spellpower");
                                System.out.println("Would you like to select this class? (y/N)");
                                String confirm = Input.next();
                                if (confirm.equalsIgnoreCase("Y")) {
                                    SelectedClass = 6;
                                    HasSelected = true;
                                }
                                else {
                                    System.out.println("Returning to selection.");
                                }
                            } 
                            else {
                                System.out.println("Invalid Choice, please try again.");
                            }
                        }
                        if (SelectedClass == 1) {
                            if (i == 1) {
                                gs.Player1.PlayerTeam[j] = new Necromancer(ClasslesCharacter, i);
                            }
                            else {
                                gs.Player2.PlayerTeam[j] = new Necromancer(ClasslesCharacter, i);
                            }
                        }
                        else if (SelectedClass == 2) {
                            if (i == 1) {
                                gs.Player1.PlayerTeam[j] = new Healer(ClasslesCharacter, i);
                            }
                            else {
                                gs.Player2.PlayerTeam[j] = new Healer(ClasslesCharacter, i);
                            }
                        }
                        else if (SelectedClass == 3) {
                             if (i == 1) {
                                gs.Player1.PlayerTeam[j] = new Crusader(ClasslesCharacter, i);
                             }
                            else {
                                gs.Player2.PlayerTeam[j] = new Crusader(ClasslesCharacter, i);
                             }
                        }
                        else if (SelectedClass == 4) {
                            if (i == 1) {
                                gs.Player1.PlayerTeam[j] = new Barbarian(ClasslesCharacter, i);
                            }
                            else {
                                gs.Player2.PlayerTeam[j] = new Barbarian(ClasslesCharacter, i);
                            }
                        }
                        else if (SelectedClass == 5) {
                            // if (i == 1) {
                            //     gs.Player1.PlayerTeam[j] = new Paladin(ClasslesCharacter, i);
                            // }
                            // else {
                            //     gs.Player2.PlayerTeam[j] = new Paladin(ClasslesCharacter, i);
                            // }
                        }
                        else if (SelectedClass == 6) {
                             if (i == 1) {
                                gs.Player1.PlayerTeam[j] = new Guardian(ClasslesCharacter, i);
                             }
                             else {
                                 gs.Player2.PlayerTeam[j] = new Guardian(ClasslesCharacter, i);
                             }
                        }
                    } // End of generating 3 characters
                    System.out.println("PLAYER " + i + " TEAM:");
                    // added an else statement which somehow made it correctly display 3 character info plates
                    // was making it display 2 without the else statement
                    if ( i == 1) {
                    ds.PrintInitialStats(gs.Player1.PlayerTeam);
                    }
                    else {
                        ds.PrintInitialStats(gs.Player2.PlayerTeam);
                    }
                    if (i == 1) {
                        System.out.println("Now initializing next player's team. Please hand over the computer. Press [ENTER] when ready.");
                    }
                    else {
                        System.out.println("Teams initialized. Press [ENTER] to continue.");
                    }
                    Input.nextLine();
                    Input.nextLine(); // Must use twice, first one consumes leftover enter from previous Input, second one waits for actual enter key press.
                } //End of generating 2 teams
                gs.refreshGameBoard();
                gs.GameBoard[0][0] = new Block(gs.Player1.PlayerTeam[0]);
                gs.GameBoard[1][0] = new Block(gs.Player1.PlayerTeam[1]);
                gs.GameBoard[0][1] = new Block(gs.Player1.PlayerTeam[2]);
                gs.GameBoard[7][7] = new Block(gs.Player2.PlayerTeam[0]);
                gs.GameBoard[6][7] = new Block(gs.Player2.PlayerTeam[1]);
                gs.GameBoard[7][6] = new Block(gs.Player2.PlayerTeam[2]);
                gs.PopulateGameBoard(7, 3);
                ds.PrintGrid(gs.GameBoard);
            } //TODO add the other two options
        } //STOP RUNNING
        Input.close();
        // gs.refreshGameBoard();
        // gs.PopulateGameBoard(5, 5);
        // ds.PrintGrid(gs.GameBoard);
    }
}