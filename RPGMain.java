import java.util.Scanner;

public class RPGMain {
    private void promptBarbarian(Character character, Scanner Input, GameSystem gs, Displayer ds) {
        System.out.print(character.GetFullName() + " the " + character.GetName() + "'s turn.");
        if (character.CheckAbility1Possible()) {
            System.out.println("Ability 1 - Flip");
        }
        if (character.CheckAbility2Possible()) {
            System.out.println("Ability 2 - Kickpunch");
        }
        if (character.CheckAbility3Possible()) {
            System.out.println("Ability 3 - Lupus");
        }
        System.out.print("Choose ability: ");
        int abilityChoice = Input.nextInt();
        if (abilityChoice == 1) {
            System.out.print("Choose x-coordinate of enemy you would like to flip: ");
            int x = Input.nextInt();
            // TODO check errors and requirements
            System.out.print("Choose y-coordinate of enemy you would like to flip: ");
            int y = Input.nextInt();
            // TODO check errors and requirements
            Character target = gs.GameBoard[y][x].GetEntity().GetCharacter();
            ActionContext barbAbility1 = new ActionContext(target, gs.GameBoard);
            boolean success = character.Ability1(barbAbility1);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName() + " flips "
                        + target.GetFullName() + " the " + target.GetName() + "!");
                ds.PrintGrid(gs.GameBoard);
            }
        } else if (abilityChoice == 2) {
            System.out.print("Choose x-coordinate of enemy you would like to flip: ");
            int x = Input.nextInt();
            // TODO check errors and requirements
            System.out.print("Choose y-coordinate of enemy you would like to flip: ");
            int y = Input.nextInt();
            // TODO check errors and requirements
            Character target = gs.GameBoard[y][x].GetEntity().GetCharacter();
            ActionContext barbAbility2 = new ActionContext(target, gs.GameBoard);
            boolean success = character.Ability2(barbAbility2);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName() + " flips "
                        + target.GetFullName() + " the " + target.GetName() + "!");
            }
        } else if (abilityChoice == 3) {
            if (character.CheckAbility3Possible()) {
                System.out.println(character.GetFullName() + " the " + character.GetName() + " casts Lupus!!");
            }
        } else {
            System.out.println("s");
        }

    }

    private void promptHealer(Character character, Scanner Input, GameSystem gs, Displayer ds) {
        ActionContext ac = new ActionContext();
        System.out.print(character.GetFullName() + " the " + character.GetName() + "'s turn.");

        if (character.CheckAbility1Possible()) {
            System.out.println("Ability 1 - Prayer");
        }
        if (character.CheckAbility2Possible()) {
            System.out.println("Ability 2 - Praise");
        }
        if (character.CheckAbility3Possible()) {
            System.out.println("Ability 3 - Strike");
        }
        System.out.print("Choose ability: ");
        int abilityChoice = Input.nextInt();
        if (abilityChoice == 1 && character.CheckAbility1Possible()) {
            character.Ability1(ac);
            System.out.println(character.GetFullName() + " the " + character.GetName() + " casts Prayer.");
        } else if (abilityChoice == 1 && !character.CheckAbility1Possible()) {
            System.out.println(character.GetFullName() + " the " + character.GetName() + " fails to cast Prayer.");
        }
        if (abilityChoice == 2 && character.CheckAbility2Possible()) {
            System.out.print("Enter the x-coordinate of the ally you would like to buff: ");
            int x = Input.nextInt();
            System.out.print("Enter the y-coordinate of the ally you would like to buff: ");
            int y = Input.nextInt();
            Character target = gs.GameBoard[y][x].GetEntity().GetCharacter();
            ActionContext healerAbility2 = new ActionContext(target, gs.GameBoard);
            character.Ability2(healerAbility2);
            System.out.println(character.GetFullName() + " the " + character.GetName() + " buffs "
                    + target.GetFullName() + " the " + target.GetName());
        } else if (abilityChoice == 2 && !character.CheckAbility2Possible()) {
            System.out.print(character.GetFullName() + " the " + character.GetName() + " is unable to cast Praise ");
        } else if (abilityChoice == 3 && character.CheckAbility3Possible()) {
            System.out.print("Enter the x-coordinate of the enemy you would like to attack: ");
            int x = Input.nextInt();
            System.out.print("Enter the x-coordinate of the enemy you would like to attack: ");
            int y = Input.nextInt();
            Character target = gs.GameBoard[y][x].GetEntity().GetCharacter();
            ActionContext healerAbility3 = new ActionContext(target, gs.GameBoard);
            boolean success = character.Ability3(healerAbility3);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName() + " casts energy bolt on "
                        + target.GetFullName() + " the " + target.GetName());
            } else {
                System.out.println("noob");
            }
        } else if (abilityChoice == 3 && !character.CheckAbility3Possible()) {
            System.out.print(
                    character.GetFullName() + " the " + character.GetName() + " is unable to cast Energy Bolt. ");
        } else {
            System.out.println("s");
        }
    }

    private void promptCrusader(Character character, Scanner Input, GameSystem gs, Displayer ds) {

        if (character.CheckAbility1Possible()) {
            System.out.println("Ability 1 - Divine Shield");
        }
        if (character.CheckAbility2Possible()) {
            System.out.println("Ability 2 - Holy Light");
        }
        if (character.CheckAbility3Possible()) {
            System.out.println("Ability 3 - Shield Bash");
        }
        System.out.print("Choose ability: ");
        int abilityChoice = Input.nextInt();
        if (abilityChoice == 1 && character.CheckAbility1Possible()) {
            System.out.print("Chooose x-coordinate of the ally you would like to shield: ");
            int x = Input.nextInt();
            System.out.print("Chooose x-coordinate of the ally you would like to shield: ");
            int y = Input.nextInt();
            Character target = gs.GameBoard[y][x].GetEntity().GetCharacter();
            ActionContext crusaderAbility1 = new ActionContext(target, gs.GameBoard);
            boolean success = character.Ability1(crusaderAbility1);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName() + " shields "
                        + target.GetFullName() + " the " + target.GetName() + "!");
            } else {
                System.out.println("noob");
            }
        } else {
            System.out.println("s");
        }
    }

    private void promptGuardian(Character character, Scanner Input, GameSystem gs, Displayer ds) {

        ActionContext ac = new ActionContext();

        if (character.CheckAbility1Possible()) {
            System.out.println("Ability 1 - Obstruct");
        }
        if (character.CheckAbility2Possible()) {
            System.out.println("Ability 2 - Fortify");
        }
        if (character.CheckAbility3Possible()) {
            System.out.println("Ability 3 - Heal");
        }
        System.out.print("Choose ability: ");
        int abilityChoice = Input.nextInt();
        if (abilityChoice == 1) {
            System.out.print("Choose x-coordinate of the obstacle you would like to create: ");
            int x = Input.nextInt();
            System.out.print("Choose y-coordinate of the obstacle you would like to create:");
            int y = Input.nextInt();
            ActionContext GuardAbility1 = new ActionContext(x, y, gs.GameBoard);
            boolean success = character.Ability1(GuardAbility1);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName() + " places an obstacle!");
                ds.PrintGrid(gs.GameBoard);
            } else {
                System.out
                        .print(character.GetFullName() + " the " + character.GetName() + " fails to place an obstacle");
            }
        } else if (abilityChoice == 2) {
            if (character.CheckAbility2Possible()) {
                character.Ability2(ac);
                System.out.println(character.GetFullName() + " the " + character.GetName() + " fortifies himself!");
            } else {
                System.out
                        .print(character.GetFullName() + " the " + character.GetName() + " fails to fortify himself!");
            }
        } else if (abilityChoice == 3) {
            if (character.CheckAbility3Possible()) {
                character.Ability3(ac);
                System.out.print(character.GetFullName() + " the " + character.GetName() + " heals himself!");
            } else {
                System.out.print(character.GetFullName() + " the " + character.GetName() + " fails to heal himself!");
            }
        } else {
            System.out.println("s");
        }
    }

    public static void main(String[] args) {
        GameSystem gs = new GameSystem();
        Displayer ds = new Displayer();
        Scanner Input = new Scanner(System.in);
        Character c = new Character();
        RPGMain rpg = new RPGMain();
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
                            ds.PrintInitialStats(new Character[] { ClasslesCharacter });
                            System.out.println(
                                    "Which class would you like your character to be? (can back out) \n1. Necromancer \n2. Healer \n3. Crusader \n4. Barbarian \n5. Paladin \n6. Guardian");
                            int Choice = Input.nextInt();
                            if (Choice == 1) {
                                System.out.println(
                                        "Necromancer \nA disturbed warlock who conjures and manipulates the dead. \nAbilities: \nAbility 1: Summon Minion - Summons a weak minion to fight. ");
                                System.out.println(
                                        "Ability 2: Buff Minion - Buffs an adjacent minion \nAbility \3: Meat Shield - Sacrafice an adjacent minion to tank a hit.");
                                System.out.println(
                                        "Stat buffs: \n-1 speed\n+3 intelligence\n+1 attack\n+2 spirit\n+1 health\n+2 spellpower");
                                System.out.println("Would you like to select this class? (y/N)");
                                String confirm = Input.next();
                                if (confirm.equalsIgnoreCase("Y")) {
                                    SelectedClass = 1;
                                    HasSelected = true;
                                } else {
                                    System.out.println("Returning to selection.");
                                }
                            } else if (Choice == 2) {
                                System.out.println(
                                        "Healer \n A benevolent soul who specialises in the mending of allies. \nAbilities: \nAbility 1: Prayer - Heals allies, and damages enemies. ");
                                System.out.println(
                                        "Ability 2: Praise - Grants an ally +4 intl and +2 spirit. \nAbility 3: Strike - Weak attack that may stun.");
                                System.out.println(
                                        "Stat buffs: \n+0 speed\n+3 intelligence\n-1 attack\n+3 spirit\n+2 health\n+1 spellpower");
                                System.out.println("Would you like to select this class? (y/N)");
                                String confirm = Input.next();
                                if (confirm.equalsIgnoreCase("Y")) {
                                    SelectedClass = 2;
                                    HasSelected = true;
                                } else {
                                    System.out.println("Returning to selection.");
                                }
                            } else if (Choice == 3) {
                                System.out.println(
                                        "Crusader \nA highly poised warrior who both safeguards allies and threatens moderate damage.\nAbilities: \nAbility 1: Divine Shield - Creates a protective barrier around an adjacent ally. ");
                                System.out.println(
                                        "Ability 1: Holy Light - Heals an adjacent ally. \nAbility 2: Condemn - Deals damage to an enemy.");
                                System.out.println(
                                        "Ability 2: Holy Light - Heals an adjacent ally. \nAbility 3: Condemn - Deals damage to an enemy.");
                                System.out.println(
                                        "Stat buffs: \n+0 speed\n+2 intelligence\n+1 attack\n+1 spirit\n+3 health\n+1 spellpower");
                                System.out.println("Would you like to select this class? (y/N)");
                                String confirm = Input.next();
                                if (confirm.equalsIgnoreCase("Y")) {
                                    SelectedClass = 3;
                                    HasSelected = true;
                                } else {
                                    System.out.println("Returning to selection.");
                                }
                            } else if (Choice == 4) {
                                System.out.println(
                                        "Barbarian \nA glass cannon who outputs heavy damage. What doesn't kill him makes him stronger.\nAbilities: \nAbility 1: Flip - Throws an enemy target opposite to the barbarian. ");
                                System.out.println(
                                        "Ability 2: Rage Strike - Melee attack dealing massive damage. \nAbility 3: Lupus - The barbarian damages himself and gains a permanent damage increase.");
                                System.out.println(
                                        "Stat buffs: \n+2 speed\n-1 intelligence\n+8 attack\n+1 spirit\n-2 health\n+0 spellpower");
                                System.out.println("Would you like to select this class? (y/N)");
                                String confirm = Input.next();
                                if (confirm.equalsIgnoreCase("Y")) {
                                    SelectedClass = 4;
                                    HasSelected = true;
                                } else {
                                    System.out.println("Returning to selection.");
                                }
                            } else if (Choice == 5) {
                                System.out.println(
                                        "Paladin \nA bastion of light who defends and heals allies.\nAbilities: \nSpecial: Aura - Adjacent allies take half damage for 1 turn. ");
                                System.out.println(
                                        "Ability 1: Rally - Grants +2 spd to all allies for 2 turns. \nAbility 2: Shield Bash - Weak attack that restores magic.");
                                System.out.println(
                                        "Stat buffs: \n+0 speed\n+2 intelligence\n+0 attack\n+1 spirit\n+5 health\n+1 spellpower");
                                System.out.println("Would you like to select this class? (y/N)");
                                String confirm = Input.next();
                                if (confirm.equalsIgnoreCase("Y")) {
                                    SelectedClass = 5;
                                    HasSelected = true;
                                } else {
                                    System.out.println("Returning to selection.");
                                }
                            } else if (Choice == 6) {
                                System.out.println(
                                        "Guardian \nProtector of lands who creates blockades. \nAbilities: \nSpecial: Blockade - Creates an obstacle");
                                System.out.println(
                                        "Ability 1: Fortify - Grants +1 hlt but -1 spd to the guardian. \nAbility 2: Heal - Heals for a portion of max helath.");
                                System.out.println(
                                        "Stat buffs: \n+0 speed\n+3 intelligence\n-1 attack\n+3 spirit\n+2 health\n+1 spellpower");
                                System.out.println("Would you like to select this class? (y/N)");
                                String confirm = Input.next();
                                if (confirm.equalsIgnoreCase("Y")) {
                                    SelectedClass = 6;
                                    HasSelected = true;
                                } else {
                                    System.out.println("Returning to selection.");
                                }
                            } else {
                                System.out.println("Invalid Choice, please try again.");
                            }
                        }
                        if (SelectedClass == 1) {
                            if (i == 1) {
                                gs.Player1.PlayerTeam[j] = new Necromancer(ClasslesCharacter, i);
                            } else {
                                gs.Player2.PlayerTeam[j] = new Necromancer(ClasslesCharacter, i);
                            }
                        } else if (SelectedClass == 2) {
                            if (i == 1) {
                                gs.Player1.PlayerTeam[j] = new Healer(ClasslesCharacter, i);
                            } else {
                                gs.Player2.PlayerTeam[j] = new Healer(ClasslesCharacter, i);
                            }
                        } else if (SelectedClass == 3) {
                            if (i == 1) {
                                gs.Player1.PlayerTeam[j] = new Crusader(ClasslesCharacter, i);
                            } else {
                                gs.Player2.PlayerTeam[j] = new Crusader(ClasslesCharacter, i);
                            }
                        } else if (SelectedClass == 4) {
                            if (i == 1) {
                                gs.Player1.PlayerTeam[j] = new Barbarian(ClasslesCharacter, i);
                            } else {
                                gs.Player2.PlayerTeam[j] = new Barbarian(ClasslesCharacter, i);
                            }
                        } else if (SelectedClass == 5) {
                            if (i == 1) {
                                gs.Player1.PlayerTeam[j] = new Paladin(ClasslesCharacter, i);
                            } else {
                                gs.Player2.PlayerTeam[j] = new Paladin(ClasslesCharacter, i);
                            }
                        } else if (SelectedClass == 6) {
                            if (i == 1) {
                                gs.Player1.PlayerTeam[j] = new Guardian(ClasslesCharacter, i);
                            } else {
                                gs.Player2.PlayerTeam[j] = new Guardian(ClasslesCharacter, i);
                            }
                        }
                    } // End of generating 3 characters
                    System.out.println("PLAYER " + i + " TEAM:");
                    if (i == 1) {
                        ds.PrintInitialStats(gs.Player1.PlayerTeam);
                    } else {
                        ds.PrintInitialStats(gs.Player2.PlayerTeam);
                    }
                    if (i == 1) {
                        System.out.println(
                                "Now initializing next player's team. Please hand over the computer. Press [ENTER] when ready.");
                    } else {
                        System.out.println("Teams initialized. Press [ENTER] to continue.");
                    }
                    Input.nextLine();
                    Input.nextLine();
                } // End of generating 2 teams
                gs.Player1.PlayerTeam[0].SetPosition(new int[] { 0, 0 }, gs.GameBoard);
                gs.Player1.PlayerTeam[1].SetPosition(new int[] { 1, 0 }, gs.GameBoard);
                gs.Player1.PlayerTeam[2].SetPosition(new int[] { 0, 1 }, gs.GameBoard);
                gs.Player2.PlayerTeam[0].SetPosition(new int[] { 7, 7 }, gs.GameBoard);
                gs.Player2.PlayerTeam[1].SetPosition(new int[] { 6, 7 }, gs.GameBoard);
                gs.Player2.PlayerTeam[2].SetPosition(new int[] { 7, 6 }, gs.GameBoard);
                gs.PopulateGameBoard(7, 3);
                ds.PrintGrid(gs.GameBoard);
                while (!gs.CheckWin()) {
                    int p1TotalSpeed = 0;
                    int p2TotalSpeed = 0;
                    for (int i = 0; i < 3; i++) {
                        p1TotalSpeed += gs.Player1.PlayerTeam[i].GetSpd();
                        p2TotalSpeed += gs.Player2.PlayerTeam[i].GetSpd();
                    }
                    if (p1TotalSpeed >= p2TotalSpeed) {
                        // --- PLAYER 1 GOES FIRST ---
                        System.out.println("Player 1 speed: " + p1TotalSpeed + "\nPlayer 2 speed: " + p2TotalSpeed);
                        int maxSpdIndexP1 = c.GetMaxSpeedIndex(gs.Player1.PlayerTeam);
                        int medSpdIndexP1 = c.getMedianSpeedIndex(maxSpdIndexP1, gs.Player1.PlayerTeam);
                        int lowSpdIndexP1 = 0;
                        for (int i = 0; i < 3; i++) {
                            if (i != maxSpdIndexP1 && i != medSpdIndexP1) {
                                lowSpdIndexP1 = i;
                            }
                        }
                        Character maxSpdCharacter = gs.Player1.PlayerTeam[maxSpdIndexP1];
                        Character medSpdCharacter = gs.Player1.PlayerTeam[medSpdIndexP1];
                        Character lowSpdCharacter = gs.Player1.PlayerTeam[lowSpdIndexP1];
                        System.out.println("Player 1 starts! Press [ENTER] to continue.");
                        Input.nextLine();
                        int x = 0;
                        int y = 0;
                        if (maxSpdCharacter.GetIsAlive() && !maxSpdCharacter.GetIsStunned()) {
                            System.out.println(
                                    maxSpdCharacter.GetFullName() + " the " + maxSpdCharacter.GetName() + "'s turn.");
                            System.out.println("Move " + maxSpdCharacter.GetFullName() + " the "
                                    + maxSpdCharacter.GetName() + "!");
                            System.out.print("Choose x-coordinate: ");
                            x = Input.nextInt();
                            System.out.print("Choose y-coordinate: ");
                            y = Input.nextInt();
                            gs.Move(maxSpdCharacter, x, y);
                            ds.PrintGrid(gs.GameBoard);
                            if (maxSpdCharacter.CheckAbility1Possible() || maxSpdCharacter.CheckAbility2Possible()
                                    || maxSpdCharacter.CheckAbility3Possible()) {
                                if (maxSpdCharacter instanceof Barbarian) {
                                    rpg.promptBarbarian(maxSpdCharacter, Input, gs, ds);
                                } else if (maxSpdCharacter instanceof Crusader) {
                                    rpg.promptCrusader(maxSpdCharacter, Input, gs, ds);
                                } else if (maxSpdCharacter instanceof Guardian) {
                                    rpg.promptGuardian(maxSpdCharacter, Input, gs, ds);
                                } else if (maxSpdCharacter instanceof Healer) {
                                    rpg.promptHealer(maxSpdCharacter, Input, gs, ds);
                                } else if (maxSpdCharacter instanceof Necromancer) {
                                } else {
                                }
                            } else {
                                System.out.println(maxSpdCharacter.GetFullName() + " the " + maxSpdCharacter.GetName()
                                        + " cannot cast any abilities this round.");
                            }
                        }
                        if (medSpdCharacter.GetIsAlive() && !medSpdCharacter.GetIsStunned()) {
                            System.out.println(
                                    medSpdCharacter.GetFullName() + " the " + medSpdCharacter.GetName() + "'s turn.");
                            System.out.println("Move " + medSpdCharacter.GetFullName() + " the "
                                    + medSpdCharacter.GetName() + "!");
                            System.out.print("Choose x-coordinate: ");
                            x = Input.nextInt();
                            System.out.print("Choose y-coordinate: ");
                            y = Input.nextInt();
                            gs.Move(medSpdCharacter, x, y);
                            ds.PrintGrid(gs.GameBoard);
                            if (medSpdCharacter.CheckAbility1Possible() || medSpdCharacter.CheckAbility2Possible()
                                    || medSpdCharacter.CheckAbility3Possible()) {
                                if (medSpdCharacter instanceof Barbarian) {
                                    rpg.promptBarbarian(medSpdCharacter, Input, gs, ds);
                                } else if (medSpdCharacter instanceof Crusader) {
                                    rpg.promptCrusader(medSpdCharacter, Input, gs, ds);
                                } else if (medSpdCharacter instanceof Guardian) {
                                    rpg.promptGuardian(medSpdCharacter, Input, gs, ds);
                                } else if (medSpdCharacter instanceof Healer) {
                                    rpg.promptHealer(medSpdCharacter, Input, gs, ds);
                                } else if (medSpdCharacter instanceof Necromancer) {
                                } else {
                                }
                            } else {
                                System.out.println(medSpdCharacter.GetFullName() + " the " + medSpdCharacter.GetName()
                                        + " cannot cast any abilities this round.");
                            }
                        }
                        if (lowSpdCharacter.GetIsAlive() && !lowSpdCharacter.GetIsStunned()) {
                            System.out.println(
                                    lowSpdCharacter.GetFullName() + " the " + lowSpdCharacter.GetName() + "'s turn.");
                            System.out.println("Move " + lowSpdCharacter.GetFullName() + " the "
                                    + lowSpdCharacter.GetName() + "!");
                            System.out.print("Choose x-coordinate: ");
                            x = Input.nextInt();
                            System.out.print("Choose y-coordinate: ");
                            y = Input.nextInt();
                            gs.Move(lowSpdCharacter, x, y);
                            ds.PrintGrid(gs.GameBoard);
                            if (lowSpdCharacter.CheckAbility1Possible() || lowSpdCharacter.CheckAbility2Possible()
                                    || lowSpdCharacter.CheckAbility3Possible()) {
                                if (lowSpdCharacter instanceof Barbarian) {
                                    rpg.promptBarbarian(lowSpdCharacter, Input, gs, ds);
                                } else if (lowSpdCharacter instanceof Crusader) {
                                    rpg.promptCrusader(lowSpdCharacter, Input, gs, ds);
                                } else if (lowSpdCharacter instanceof Guardian) {
                                    rpg.promptGuardian(lowSpdCharacter, Input, gs, ds);
                                } else if (lowSpdCharacter instanceof Healer) {
                                    rpg.promptHealer(lowSpdCharacter, Input, gs, ds);
                                } else if (lowSpdCharacter instanceof Necromancer) {
                                } else {
                                }
                            } else {
                                System.out.println(lowSpdCharacter.GetFullName() + " the " + lowSpdCharacter.GetName()
                                        + " cannot cast any abilities this round.");
                            }
                        }
                        // --- PLAYER 2 GOES SECOND ---
                        System.out.println("Player 2's turn. Press [ENTER] to continue.");
                        Input.nextLine();
                        int maxSpdIndexP2 = c.GetMaxSpeedIndex(gs.Player2.PlayerTeam);
                        int medSpdIndexP2 = c.getMedianSpeedIndex(maxSpdIndexP2, gs.Player2.PlayerTeam);
                        int lowSpdIndexP2 = 0;
                        for (int i = 0; i < 3; i++) {
                            if (i != maxSpdIndexP2 && i != medSpdIndexP2) {
                                lowSpdIndexP2 = i;
                            }
                        }
                        maxSpdCharacter = gs.Player2.PlayerTeam[maxSpdIndexP2];
                        medSpdCharacter = gs.Player2.PlayerTeam[medSpdIndexP2];
                        lowSpdCharacter = gs.Player2.PlayerTeam[lowSpdIndexP2];
                        x = 0;
                        y = 0;
                        if (maxSpdCharacter.GetIsAlive() && !maxSpdCharacter.GetIsStunned()) {
                            System.out.println(
                                    maxSpdCharacter.GetFullName() + " the " + maxSpdCharacter.GetName() + "'s turn.");
                            System.out.println("Move " + maxSpdCharacter.GetFullName() + " the "
                                    + maxSpdCharacter.GetName() + "!");
                            System.out.print("Choose x-coordinate: ");
                            x = Input.nextInt();
                            System.out.print("Choose y-coordinate: ");
                            y = Input.nextInt();
                            gs.Move(maxSpdCharacter, x, y);
                            ds.PrintGrid(gs.GameBoard);
                            if (maxSpdCharacter.CheckAbility1Possible() || maxSpdCharacter.CheckAbility2Possible()
                                    || maxSpdCharacter.CheckAbility3Possible()) {
                                if (maxSpdCharacter instanceof Barbarian) {
                                    rpg.promptBarbarian(maxSpdCharacter, Input, gs, ds);
                                } else if (maxSpdCharacter instanceof Crusader) {
                                    rpg.promptCrusader(maxSpdCharacter, Input, gs, ds);
                                } else if (maxSpdCharacter instanceof Guardian) {
                                    rpg.promptGuardian(maxSpdCharacter, Input, gs, ds);
                                } else if (maxSpdCharacter instanceof Healer) {
                                    rpg.promptHealer(maxSpdCharacter, Input, gs, ds);
                                } else if (maxSpdCharacter instanceof Necromancer) {
                                } else {
                                }
                            } else {
                                System.out.println(maxSpdCharacter.GetFullName() + " the " + maxSpdCharacter.GetName()
                                        + " cannot cast any abilities this round.");
                            }
                        }
                        if (medSpdCharacter.GetIsAlive() && !maxSpdCharacter.GetIsStunned()) {
                            System.out.println(
                                    medSpdCharacter.GetFullName() + " the " + medSpdCharacter.GetName() + "'s turn.");
                            System.out.println("Move " + medSpdCharacter.GetFullName() + " the "
                                    + medSpdCharacter.GetName() + "!");
                            System.out.print("Choose x-coordinate: ");
                            x = Input.nextInt();
                            System.out.print("Choose y-coordinate: ");
                            y = Input.nextInt();
                            gs.Move(medSpdCharacter, x, y);
                            ds.PrintGrid(gs.GameBoard);
                            if (medSpdCharacter.CheckAbility1Possible() || medSpdCharacter.CheckAbility2Possible()
                                    || medSpdCharacter.CheckAbility3Possible()) {
                                if (medSpdCharacter instanceof Barbarian) {
                                    rpg.promptBarbarian(medSpdCharacter, Input, gs, ds);
                                } else if (medSpdCharacter instanceof Crusader) {
                                    rpg.promptCrusader(medSpdCharacter, Input, gs, ds);
                                } else if (medSpdCharacter instanceof Guardian) {
                                    rpg.promptGuardian(medSpdCharacter, Input, gs, ds);
                                } else if (medSpdCharacter instanceof Healer) {
                                    rpg.promptHealer(medSpdCharacter, Input, gs, ds);
                                } else if (medSpdCharacter instanceof Necromancer) {
                                } else {
                                }
                            } else {
                                System.out.println(medSpdCharacter.GetFullName() + " the " + medSpdCharacter.GetName()
                                        + " cannot cast any abilities this round.");
                            }
                        }
                        if (lowSpdCharacter.GetIsAlive() && !maxSpdCharacter.GetIsStunned()) {
                            System.out.println(
                                    lowSpdCharacter.GetFullName() + " the " + lowSpdCharacter.GetName() + "'s turn.");
                            System.out.println("Move " + lowSpdCharacter.GetFullName() + " the "
                                    + lowSpdCharacter.GetName() + "!");
                            System.out.print("Choose x-coordinate: ");
                            x = Input.nextInt();
                            System.out.print("Choose y-coordinate: ");
                            y = Input.nextInt();
                            gs.Move(lowSpdCharacter, x, y);
                            ds.PrintGrid(gs.GameBoard);
                            if (lowSpdCharacter.CheckAbility1Possible() || lowSpdCharacter.CheckAbility2Possible()
                                    || lowSpdCharacter.CheckAbility3Possible()) {
                                if (lowSpdCharacter instanceof Barbarian) {
                                    rpg.promptBarbarian(lowSpdCharacter, Input, gs, ds);
                                } else if (lowSpdCharacter instanceof Crusader) {
                                    rpg.promptCrusader(lowSpdCharacter, Input, gs, ds);
                                } else if (lowSpdCharacter instanceof Guardian) {
                                    rpg.promptGuardian(lowSpdCharacter, Input, gs, ds);
                                } else if (lowSpdCharacter instanceof Healer) {
                                    rpg.promptHealer(medSpdCharacter, Input, gs, ds);
                                } else if (lowSpdCharacter instanceof Necromancer) {
                                    // promptNecromancer
                                } else {
                                    // promptPaladin
                                }
                            } else {
                                System.out.println(lowSpdCharacter.GetFullName() + " the " + lowSpdCharacter.GetName()
                                        + " cannot cast any abilities this round.");
                            }
                        }
                    } else {
                        // --- PLAYER 2 GOES FIRST ---
                        System.out.println("Player 2 speed: " + p2TotalSpeed + "\nPlayer 1 speed: " + p1TotalSpeed);
                        int maxSpdIndexP2 = c.GetMaxSpeedIndex(gs.Player2.PlayerTeam);
                        int medSpdIndexP2 = c.getMedianSpeedIndex(maxSpdIndexP2, gs.Player2.PlayerTeam);
                        int lowSpdIndexP2 = 0;
                        for (int i = 0; i < 3; i++) {
                            if (i != maxSpdIndexP2 && i != medSpdIndexP2) {
                                lowSpdIndexP2 = i;
                            }
                        }
                        Character maxSpdCharacter = gs.Player2.PlayerTeam[maxSpdIndexP2];
                        Character medSpdCharacter = gs.Player2.PlayerTeam[medSpdIndexP2];
                        Character lowSpdCharacter = gs.Player2.PlayerTeam[lowSpdIndexP2];
                        System.out.println("Player 2 starts! Press [ENTER] to continue.");
                        Input.nextLine();
                        int x = 0;
                        int y = 0;
                        if (maxSpdCharacter.GetIsAlive() && !maxSpdCharacter.GetIsStunned()) {
                            System.out.println(
                                    maxSpdCharacter.GetFullName() + " the " + maxSpdCharacter.GetName() + "'s turn.");
                            System.out.println("Move " + maxSpdCharacter.GetFullName() + " the "
                                    + maxSpdCharacter.GetName() + "!");
                            System.out.print("Choose x-coordinate: ");
                            x = Input.nextInt();
                            System.out.print("Choose y-coordinate: ");
                            y = Input.nextInt();
                            gs.Move(maxSpdCharacter, x, y);
                            ds.PrintGrid(gs.GameBoard);
                            if (maxSpdCharacter.CheckAbility1Possible() || maxSpdCharacter.CheckAbility2Possible()
                                    || maxSpdCharacter.CheckAbility3Possible()) {
                                if (maxSpdCharacter instanceof Barbarian) {
                                    rpg.promptBarbarian(maxSpdCharacter, Input, gs, ds);
                                } else if (maxSpdCharacter instanceof Crusader) {
                                    rpg.promptCrusader(maxSpdCharacter, Input, gs, ds);
                                } else if (maxSpdCharacter instanceof Guardian) {
                                    rpg.promptGuardian(maxSpdCharacter, Input, gs, ds);
                                } else if (maxSpdCharacter instanceof Healer) {
                                    rpg.promptHealer(maxSpdCharacter, Input, gs, ds);
                                } else if (maxSpdCharacter instanceof Necromancer) {
                                } else {
                                }
                            } else {
                                System.out.println(maxSpdCharacter.GetFullName() + " the " + maxSpdCharacter.GetName()
                                        + " cannot cast any abilities this round.");
                            }
                        }
                        if (medSpdCharacter.GetIsAlive() && !medSpdCharacter.GetIsStunned()) {
                            System.out.println(
                                    medSpdCharacter.GetFullName() + " the " + medSpdCharacter.GetName() + "'s turn.");
                            System.out.println("Move " + medSpdCharacter.GetFullName() + " the "
                                    + medSpdCharacter.GetName() + "!");
                            System.out.print("Choose x-coordinate: ");
                            x = Input.nextInt();
                            System.out.print("Choose y-coordinate: ");
                            y = Input.nextInt();
                            gs.Move(medSpdCharacter, x, y);
                            ds.PrintGrid(gs.GameBoard);
                            if (medSpdCharacter.CheckAbility1Possible() || medSpdCharacter.CheckAbility2Possible()
                                    || medSpdCharacter.CheckAbility3Possible()) {
                                if (medSpdCharacter instanceof Barbarian) {
                                    rpg.promptBarbarian(medSpdCharacter, Input, gs, ds);
                                } else if (medSpdCharacter instanceof Crusader) {
                                    rpg.promptCrusader(medSpdCharacter, Input, gs, ds);
                                } else if (medSpdCharacter instanceof Guardian) {
                                    rpg.promptGuardian(medSpdCharacter, Input, gs, ds);
                                } else if (medSpdCharacter instanceof Healer) {
                                    rpg.promptHealer(medSpdCharacter, Input, gs, ds);
                                } else if (medSpdCharacter instanceof Necromancer) {
                                } else {
                                }
                            } else {
                                System.out.println(medSpdCharacter.GetFullName() + " the " + medSpdCharacter.GetName()
                                        + " cannot cast any abilities this round.");
                            }
                        }
                        if (lowSpdCharacter.GetIsAlive() && !lowSpdCharacter.GetIsStunned()) {
                            System.out.println(
                                    lowSpdCharacter.GetFullName() + " the " + lowSpdCharacter.GetName() + "'s turn.");
                            System.out.println("Move " + lowSpdCharacter.GetFullName() + " the "
                                    + lowSpdCharacter.GetName() + "!");
                            System.out.print("Choose x-coordinate: ");
                            x = Input.nextInt();
                            System.out.print("Choose y-coordinate: ");
                            y = Input.nextInt();
                            gs.Move(lowSpdCharacter, x, y);
                            ds.PrintGrid(gs.GameBoard);
                            if (lowSpdCharacter.CheckAbility1Possible() || lowSpdCharacter.CheckAbility2Possible()
                                    || lowSpdCharacter.CheckAbility3Possible()) {
                                if (lowSpdCharacter instanceof Barbarian) {
                                    rpg.promptBarbarian(lowSpdCharacter, Input, gs, ds);
                                } else if (lowSpdCharacter instanceof Crusader) {
                                    rpg.promptCrusader(lowSpdCharacter, Input, gs, ds);
                                } else if (lowSpdCharacter instanceof Guardian) {
                                    rpg.promptGuardian(lowSpdCharacter, Input, gs, ds);
                                } else if (lowSpdCharacter instanceof Healer) {
                                    rpg.promptHealer(lowSpdCharacter, Input, gs, ds);
                                } else if (lowSpdCharacter instanceof Necromancer) {
                                } else {
                                }
                            } else {
                                System.out.println(lowSpdCharacter.GetFullName() + " the " + lowSpdCharacter.GetName()
                                        + " cannot cast any abilities this round.");
                            }
                        }
                        // --- PLAYER 1 GOES SECOND ---
                        System.out.println("Player 1's turn. Press [ENTER] to continue.");
                        Input.nextLine();
                        int maxSpdIndexP1 = c.GetMaxSpeedIndex(gs.Player1.PlayerTeam);
                        int medSpdIndexP1 = c.getMedianSpeedIndex(maxSpdIndexP1, gs.Player1.PlayerTeam);
                        int lowSpdIndexP1 = 0;
                        for (int i = 0; i < 3; i++) {
                            if (i != maxSpdIndexP1 && i != medSpdIndexP1) {
                                lowSpdIndexP1 = i;
                            }
                        }
                        maxSpdCharacter = gs.Player1.PlayerTeam[maxSpdIndexP1];
                        medSpdCharacter = gs.Player1.PlayerTeam[medSpdIndexP1];
                        lowSpdCharacter = gs.Player1.PlayerTeam[lowSpdIndexP1];
                        x = 0;
                        y = 0;
                        if (maxSpdCharacter.GetIsAlive() && !maxSpdCharacter.GetIsStunned()) {
                            System.out.println(
                                    maxSpdCharacter.GetFullName() + " the " + maxSpdCharacter.GetName() + "'s turn.");
                            System.out.println("Move " + maxSpdCharacter.GetFullName() + " the "
                                    + maxSpdCharacter.GetName() + "!");
                            System.out.print("Choose x-coordinate: ");
                            x = Input.nextInt();
                            System.out.print("Choose y-coordinate: ");
                            y = Input.nextInt();
                            gs.Move(maxSpdCharacter, x, y);
                            ds.PrintGrid(gs.GameBoard);
                            if (maxSpdCharacter.CheckAbility1Possible() || maxSpdCharacter.CheckAbility2Possible()
                                    || maxSpdCharacter.CheckAbility3Possible()) {
                                if (maxSpdCharacter instanceof Barbarian) {
                                    rpg.promptBarbarian(maxSpdCharacter, Input, gs, ds);
                                } else if (maxSpdCharacter instanceof Crusader) {
                                    rpg.promptCrusader(maxSpdCharacter, Input, gs, ds);
                                } else if (maxSpdCharacter instanceof Guardian) {
                                    rpg.promptGuardian(maxSpdCharacter, Input, gs, ds);
                                } else if (maxSpdCharacter instanceof Healer) {
                                    rpg.promptHealer(maxSpdCharacter, Input, gs, ds);
                                } else if (maxSpdCharacter instanceof Necromancer) {
                                } else {
                                }
                            } else {
                                System.out.println(maxSpdCharacter.GetFullName() + " the " + maxSpdCharacter.GetName()
                                        + " cannot cast any abilities this round.");
                            }
                        }
                        if (medSpdCharacter.GetIsAlive() && !maxSpdCharacter.GetIsStunned()) {
                            System.out.println(
                                    medSpdCharacter.GetFullName() + " the " + medSpdCharacter.GetName() + "'s turn.");
                            System.out.println("Move " + medSpdCharacter.GetFullName() + " the "
                                    + medSpdCharacter.GetName() + "!");
                            System.out.print("Choose x-coordinate: ");
                            x = Input.nextInt();
                            System.out.print("Choose y-coordinate: ");
                            y = Input.nextInt();
                            gs.Move(medSpdCharacter, x, y);
                            ds.PrintGrid(gs.GameBoard);
                            if (medSpdCharacter.CheckAbility1Possible() || medSpdCharacter.CheckAbility2Possible()
                                    || medSpdCharacter.CheckAbility3Possible()) {
                                if (medSpdCharacter instanceof Barbarian) {
                                    rpg.promptBarbarian(medSpdCharacter, Input, gs, ds);
                                } else if (medSpdCharacter instanceof Crusader) {
                                    rpg.promptCrusader(medSpdCharacter, Input, gs, ds);
                                } else if (medSpdCharacter instanceof Guardian) {
                                    rpg.promptGuardian(medSpdCharacter, Input, gs, ds);
                                } else if (medSpdCharacter instanceof Healer) {
                                    rpg.promptHealer(medSpdCharacter, Input, gs, ds);
                                } else if (medSpdCharacter instanceof Necromancer) {
                                } else {
                                }
                            } else {
                                System.out.println(medSpdCharacter.GetFullName() + " the " + medSpdCharacter.GetName()
                                        + " cannot cast any abilities this round.");
                            }
                        }
                        if (lowSpdCharacter.GetIsAlive() && !maxSpdCharacter.GetIsStunned()) {
                            System.out.println(
                                    lowSpdCharacter.GetFullName() + " the " + lowSpdCharacter.GetName() + "'s turn.");
                            System.out.println("Move " + lowSpdCharacter.GetFullName() + " the "
                                    + lowSpdCharacter.GetName() + "!");
                            System.out.print("Choose x-coordinate: ");
                            x = Input.nextInt();
                            System.out.print("Choose y-coordinate: ");
                            y = Input.nextInt();
                            gs.Move(lowSpdCharacter, x, y);
                            ds.PrintGrid(gs.GameBoard);
                            if (lowSpdCharacter.CheckAbility1Possible() || lowSpdCharacter.CheckAbility2Possible()
                                    || lowSpdCharacter.CheckAbility3Possible()) {
                                if (lowSpdCharacter instanceof Barbarian) {
                                    rpg.promptBarbarian(lowSpdCharacter, Input, gs, ds);
                                } else if (lowSpdCharacter instanceof Crusader) {
                                    rpg.promptCrusader(lowSpdCharacter, Input, gs, ds);
                                } else if (lowSpdCharacter instanceof Guardian) {
                                    rpg.promptGuardian(lowSpdCharacter, Input, gs, ds);
                                } else if (lowSpdCharacter instanceof Healer) {
                                    rpg.promptHealer(medSpdCharacter, Input, gs, ds);
                                } else if (lowSpdCharacter instanceof Necromancer) {
                                    // promptNecromancer
                                } else {
                                    // promptPaladin
                                }
                            } else {
                                System.out.println(lowSpdCharacter.GetFullName() + " the " + lowSpdCharacter.GetName()
                                        + " cannot cast any abilities this round.");
                            }
                        }
                    }
                }
                if (gs.CheckWin()) {
                    int winner = gs.GetWinningTeam();
                    System.out.println("Player " + winner + " wins!");
                    GameRunning = false;
                }
            } // TODO add the other two options
        } // STOP RUNNING
        Input.close();
    }
}
