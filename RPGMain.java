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

                        while (!HasSelected) {
                            System.out.println("Character " + (j + 1) + ":");
                            ds.PrintInitialStats(new Character[]{ClasslesCharacter});
                            System.out.println("Which class would you like your character to be? (can back out) \n1. Necromancer \n2. Healer \n3. Crusader \n4. Barbarian \n5. Paladin \n6. Guardian");
                            int Choice = Input.nextInt();
                            if (Choice == 1) {
                                System.out.println("Necromancer \nAn intelligent and powerful mage who can summon minions to fight.\nAbilities: \nAbility1: Summon Minion - Summons a weak minion to fight. ");
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
                                System.out.println("Healer \nAn intelligent and resilient mage who can heal allies and provide buffs.\nAbilities: \nAbility1: Prayer - Heals allies, and damages enemies. ");
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
                                System.out.println("Crusader \nA sturdy warrior who can protect allies and deal significant damage.\nAbilities: \nAbility1: Divine Shield - Creates a protective barrier around an adjacent ally. ");
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
                                System.out.println("Healer \nAn intelligent and resilient mage who can heal allies and provide buffs.\nAbilities: \nAbility1: Prayer - Heals allies, and damages enemies. ");
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
                                System.out.println("Healer \nAn intelligent and resilient mage who can heal allies and provide buffs.\nAbilities: \nAbility1: Prayer - Heals allies, and damages enemies. ");
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
                                System.out.println("Healer \nAn intelligent and resilient mage who can heal allies and provide buffs.\nAbilities: \nAbility1: Prayer - Heals allies, and damages enemies. ");
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
                            if (i == 1) {
                                gs.Player1.PlayerTeam[j] = new Paladin(ClasslesCharacter, i);
                            }
                            else {
                                gs.Player2.PlayerTeam[j] = new Paladin(ClasslesCharacter, i);
                            }
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
                    if (i == 1) {
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
                gs.Player1.PlayerTeam[0].SetPosition(new int[]{0, 0}, gs.GameBoard); //Updated all of the lines because movement
                gs.Player1.PlayerTeam[1].SetPosition(new int[]{1, 0}, gs.GameBoard); //did not work when setting them manually.
                gs.Player1.PlayerTeam[2].SetPosition(new int[]{0, 1}, gs.GameBoard);
                gs.Player2.PlayerTeam[0].SetPosition(new int[]{7, 7}, gs.GameBoard);
                gs.Player2.PlayerTeam[1].SetPosition(new int[]{6, 7}, gs.GameBoard);
                gs.Player2.PlayerTeam[2].SetPosition(new int[]{7, 6}, gs.GameBoard);
                gs.PopulateGameBoard(7, 3);
                ds.PrintGrid(gs.GameBoard);
                CombatManager combatManager = new CombatManager();
                
                while (!gs.CheckWin()) {
                    // get turn order
                    Character[] turnOrder = combatManager.calculateTurnOrder(gs);
                    
                    // handle the iteration and prompting
                    for (int i = 0; i < turnOrder.length; i++) {
                        Character currentCharacter = turnOrder[i];
                        
                        // Skip nulls or dead characters
                        if (currentCharacter == null || !currentCharacter.GetIsAlive() || currentCharacter.GetIsStunned() || gs.CheckWin()) {
                            continue;
                        }

                        System.out.println("\n------------------------------------");
                        System.out.println(currentCharacter.GetFullName() + " the " + currentCharacter.GetName() + "'s turn. (Team: " + currentCharacter.GetTeam() + ")");
                        
                        // handle movement input here
                        System.out.print("Choose x-coordinate to move: ");
                        int moveX = Input.nextInt();
                        System.out.print("Choose y-coordinate to move: ");
                        int moveY = Input.nextInt();
                        
                        // Pass movement to backend to handle
                        gs.Move(currentCharacter, moveX, moveY);
                        
                        // handle ability input
                        String[] abilities = currentCharacter.getAbilityMenu();
                        System.out.println("\nAvailable Abilities:");
                        for (int j = 0; j < abilities.length; j++) {
                            System.out.println((j + 1) + ". " + abilities[j]);
                        }
                        System.out.println("0. Skip turn");
                        
                        System.out.print("Choose ability: ");
                        int choice = Input.nextInt();
                        
                        if (choice > 0) {
                            System.out.print("Choose target x-coordinate: ");
                            int targetX = Input.nextInt();
                            System.out.print("Choose target y-coordinate: ");
                            int targetY = Input.nextInt();
                            //TODO i think this needs to be changed to be more flexible
                            // Pass ALL collected ability data to the Backend for processing
                            combatManager.executeAction(currentCharacter, choice, targetX, targetY, gs);
                        }
                        
                        // Update visual state
                        //gs.refreshGameBoard(); Just breaks the game 😏
                        ds.PrintGrid(gs.GameBoard);
                    }
                }
                
                // Victory Condition Reached
                int winner = gs.GetWinningTeam();
                System.out.println("\n====================================");
                System.out.println("          PLAYER " + winner + " WINS!          ");
                System.out.println("====================================");
                GameRunning = false;
                
            } else if (PlayerChoice == 2) {
                System.out.println("NOT FINISHED YET.");
            } else if (PlayerChoice == 3) {
                System.out.println("Exiting application.");
                GameRunning = false;
            } else {
                System.out.println("Invalid selection.");
            }
        }
        
        Input.close();
    }
}