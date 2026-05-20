/* Main frontend class, handles all Input and Output between the user and the backend.
 * All of the printing (aside from Displayer.java) takes place here. 
 * This is also the file that should be ran in order to properly play the game.*/
import java.util.Scanner;

public class RPGMain {

    /* Helper method to reduce amount of code needed per loop.
     * Handles several conveneint functions but mainly the movement of a selected character.
     *
     * @param character - The character selected to move
     * @param input     - The scanner used to detect user input
     * @param gs        - The game system that stores the coordinates and what inhabits them.
     * @param ds        - The displayer class containing a handful of vital methods.
     */
    private void promptMove(Character character, Scanner input, GameSystem gs, Displayer ds) {
        System.out.println(character.GetFullName() + " the " + character.GetName() + "'s turn.");
        ds.PrintStats(new Character[]{character});
        System.out.println("Move " + character.GetFullName() + " the " + character.GetName() + "!");
        System.out.print("Choose x-coordinate: ");
        int x = input.nextInt();
        System.out.print("Choose y-coordinate: ");
        int y = input.nextInt();
        gs.Move(character, x, y);
        ds.PrintGrid(gs.gameBoard);
    }


    /* Helper method to streamline prompting a barbarian character. 
     * Ideally we would place this in each individual class, but that would very much break frontend backend. 
     * The system prompts an integer between 0 and 3 and then selects an attack based off the user's input.
     * 0    :Skip move, don't do anything.
     * 1    :Flip a character to the opposite side, dealing damage.
     * 2    :Kickpunch an enemy, doing massive amounts of damage.
     * 3    :Infect yourself with lupus, dealing self-damage and increasing power output.
     * 
     * @param character     - The character being prompted, contains ability functions and it's own stats.
     * @param input         - The scanner used to detect user input.
     * @param gs            - The game system that stores the coordinates and what inhabits them.
     * @param ds            - The displayer class containing a handful of vital methods. 
     */
    private void promptBarbarian(Character character, Scanner input, GameSystem gs, Displayer ds) {
        System.out.println(character.GetFullName() + " the " + character.GetName() + "'s turn.");
        if (character.CheckAbility1Possible(gs)) {
            System.out.println("Ability 1 - Flip");
        }
        if (character.CheckAbility2Possible(gs)) {
            System.out.println("Ability 2 - Kickpunch");
        }
        if (character.CheckAbility3Possible(gs)) {
            System.out.println("Ability 3 - Lupus");
        }
        System.out.print("Choose ability (0 if skipping turn): ");
        int abilityChoice = input.nextInt();
        if (abilityChoice == 1) {
            System.out.print("Choose x-coordinate of enemy you would like to flip: ");
            int x = input.nextInt();
            // TODO check errors and requirements
            System.out.print("Choose y-coordinate of enemy you would like to flip: ");
            int y = input.nextInt();
            // TODO check errors and requirements
            Character target = gs.gameBoard[y][x].getEntity().GetCharacter();
            ActionContext barbAbility1 = new ActionContext(target, gs.gameBoard);
            boolean success = character.Ability1(barbAbility1);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName() + " flips "
                        + target.GetFullName() + " the " + target.GetName() + "!");
                ds.PrintGrid(gs.gameBoard);
            }
        } else if (abilityChoice == 2) {
            System.out.print("Choose x-coordinate of enemy you would like to strike: ");
            int x = input.nextInt();
            // TODO check errors and requirements
            System.out.print("Choose y-coordinate of enemy you would like to strike: ");
            int y = input.nextInt();
            // TODO check errors and requirements
            Character target = gs.gameBoard[y][x].getEntity().GetCharacter();
            ActionContext barbAbility2 = new ActionContext(target, gs.gameBoard);
            boolean success = character.Ability2(barbAbility2);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName() + " strikes "
                        + target.GetFullName() + " the " + target.GetName() + "!");
            }
        } else if (abilityChoice == 3) {
            if (character.CheckAbility3Possible(gs)) {
                System.out.println(character.GetFullName() + " the " + character.GetName() + " casts Lupus!!");
            }
        } else if (abilityChoice == 0) {
            System.out.println("wow");
        }

    }

    /* Helper method to streamline prompting a barbarian character. 
     * Ideally we would place this in each individual class, but that would very much break frontend backend. 
     * The system prompts an integer between 0 and 3 and then selects an attack based off the user's input.
     * 0    :Skip move, don't do anything.
     * 1    :Pray for a character, dealing damage to enemies and healing allies.
     * 2    :Praise an ally, granting stat buffs.
     * 3    :Strike an enemy down with lightning, possibly stunning them and dealing small damage.
     * 
     * @param character     - The character being prompted, contains ability functions and it's own stats.
     * @param input         - The scanner used to detect user input.
     * @param gs            - The game system that stores the coordinates and what inhabits them.
     * @param ds            - The displayer class containing a handful of vital methods. 
     */
    private void promptHealer(Character character, Scanner input, GameSystem gs, Displayer ds) {
        ActionContext ac = new ActionContext();
        System.out.println(character.GetFullName() + " the " + character.GetName() + "'s turn.");

        if (character.CheckAbility1Possible(gs)) {
            System.out.println("Ability 1 - Prayer");
        }
        if (character.CheckAbility2Possible(gs)) {
            System.out.println("Ability 2 - Praise");
        }
        if (character.CheckAbility3Possible(gs)) {
            System.out.println("Ability 3 - Strike");
        }
        System.out.print("Choose ability: ");
        int abilityChoice = input.nextInt();
        if (abilityChoice == 1 && character.CheckAbility1Possible(gs)) {
            character.Ability1(ac);
            System.out.println(character.GetFullName() + " the " + character.GetName() + " casts Prayer.");
        } else if (abilityChoice == 1 && !character.CheckAbility1Possible(gs)) {
            System.out.println(character.GetFullName() + " the " + character.GetName() + " fails to cast Prayer.");
        }
        if (abilityChoice == 2 && character.CheckAbility2Possible(gs)) {
            System.out.print("Enter the x-coordinate of the ally you would like to buff: ");
            int x = input.nextInt();
            System.out.print("Enter the y-coordinate of the ally you would like to buff: ");
            int y = input.nextInt();
            Character target = gs.gameBoard[y][x].getEntity().GetCharacter();
            ActionContext healerAbility2 = new ActionContext(target, gs.gameBoard);
            character.Ability2(healerAbility2);
            System.out.println(character.GetFullName() + " the " + character.GetName() + " buffs "
                    + target.GetFullName() + " the " + target.GetName());
        } else if (abilityChoice == 2 && !character.CheckAbility2Possible(gs)) {
            System.out.println(character.GetFullName() + " the " + character.GetName() + " is unable to cast Praise ");
        } else if (abilityChoice == 3 && character.CheckAbility3Possible(gs)) {
            System.out.print("Enter the x-coordinate of the enemy you would like to attack: ");
            int x = input.nextInt();
            System.out.print("Enter the y-coordinate of the enemy you would like to attack: ");
            int y = input.nextInt();
            Character target = gs.gameBoard[y][x].getEntity().GetCharacter();
            ActionContext healerAbility3 = new ActionContext(target, gs.gameBoard);
            boolean success = character.Ability3(healerAbility3);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName() + " casts energy bolt on "
                        + target.GetFullName() + " the " + target.GetName());
            } else {
                System.out.println("noob");
            }
        } else if (abilityChoice == 3 && !character.CheckAbility3Possible(gs)) {
            System.out.println(
                    character.GetFullName() + " the " + character.GetName() + " is unable to cast Energy Bolt. ");
        } else {
            System.out.println("s");
        }
    }

    /* Helper method to streamline prompting a barbarian character. 
     * Ideally we would place this in each individual class, but that would very much break frontend backend. 
     * The system prompts an integer between 0 and 3 and then selects an attack based off the user's input.
     * 0    :Skip move, don't do anything.
     * 1    : TODO ADD THIS
     * 2    :Summon a ray of holy light on an ally, healing them and granting a speed buff.
     * 3    :Bash an enemy with your sheild for medium damage.
     * 
     * @param character     - The character being prompted, contains ability functions and it's own stats.
     * @param input         - The scanner used to detect user input.
     * @param gs            - The game system that stores the coordinates and what inhabits them.
     * @param ds            - The displayer class containing a handful of vital methods. 
     */
    private void promptCrusader(Character character, Scanner input, GameSystem gs, Displayer ds) {
        if (character.CheckAbility1Possible(gs)) {
            System.out.println("Ability 1 - Divine Shield");
        }
        if (character.CheckAbility2Possible(gs)) {
            System.out.println("Ability 2 - Holy Light");
        }
        if (character.CheckAbility3Possible(gs)) {
            System.out.println("Ability 3 - Shield Bash");
        }
        System.out.print("Choose ability: ");
        int abilityChoice = input.nextInt();
        if (abilityChoice == 1 && character.CheckAbility1Possible(gs)) {
            System.out.print("Chooose x-coordinate of the ally you would like to shield: ");
            int x = input.nextInt();
            System.out.print("Chooose y-coordinate of the ally you would like to shield: ");
            int y = input.nextInt();
            Character target = gs.gameBoard[y][x].getEntity().GetCharacter();
            ActionContext crusaderAbility1 = new ActionContext(target, gs.gameBoard);
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

    /* Helper method to streamline prompting a barbarian character. 
     * Ideally we would place this in each individual class, but that would very much break frontend backend. 
     * The system prompts an integer between 0 and 3 and then selects an attack based off the user's input.
     * 0    :Skip move, don't do anything.
     * 1    :Create an obsticle at a specified coordinate.
     * 2    :Grant yourself a massive health buff.
     * 3    :Heal yourself.
     * 
     * @param character     - The character being prompted, contains ability functions and it's own stats.
     * @param input         - The scanner used to detect user input.
     * @param gs            - The game system that stores the coordinates and what inhabits them.
     * @param ds            - The displayer class containing a handful of vital methods. 
     */
    private void promptGuardian(Character character, Scanner input, GameSystem gs, Displayer ds) {

        ActionContext ac = new ActionContext();

        if (character.CheckAbility1Possible(gs)) {
            System.out.println("Ability 1 - Obstruct");
        }
        if (character.CheckAbility2Possible(gs)) {
            System.out.println("Ability 2 - Fortify");
        }
        if (character.CheckAbility3Possible(gs)) {
            System.out.println("Ability 3 - Heal");
        }
        System.out.print("Choose ability: ");
        int abilityChoice = input.nextInt();
        if (abilityChoice == 1) {
            System.out.print("Choose x-coordinate of the obstacle you would like to create: ");
            int x = input.nextInt();
            System.out.print("Choose y-coordinate of the obstacle you would like to create:");
            int y = input.nextInt();
            ActionContext GuardAbility1 = new ActionContext(x, y, gs.gameBoard);
            boolean success = character.Ability1(GuardAbility1);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName() + " places an obstacle!");
                ds.PrintGrid(gs.gameBoard);
            } else {

                System.out
                        .println(character.GetFullName() + " the " + character.GetName() + " fails to place an obstacle");

                System.out.println(character.GetFullName() + " the " + character.GetName() + " fails to place an obstacle");

            }
        } else if (abilityChoice == 2) {
            if (character.CheckAbility2Possible(gs)) {
                character.Ability2(ac);
                System.out.println(character.GetFullName() + " the " + character.GetName() + " fortifies himself!");
            } else {

                System.out
                        .println(character.GetFullName() + " the " + character.GetName() + " fails to fortify himself!");
                System.out.println(character.GetFullName() + " the " + character.GetName() + " fails to fortify himself!");

            }
        } else if (abilityChoice == 3) {
            if (character.CheckAbility3Possible(gs)) {
                character.Ability3(ac);
                System.out.println(character.GetFullName() + " the " + character.GetName() + " heals himself!");
            } else {
                System.out.println(character.GetFullName() + " the " + character.GetName() + " fails to heal himself!");
            }
        } else {
            System.out.println("s");
        }
    }

    /* Helper method to streamline prompting a barbarian character. 
     * Ideally we would place this in each individual class, but that would very much break frontend backend. 
     * The system prompts an integer between 0 and 3 and then selects an attack based off the user's input.
     * 0    :Skip move, don't do anything.
     * 1    :Summon a minion anywhere on the board.
     * 2    :Buff an adjacent minion, granting it more damage and health.
     * 3    :Sacrafice a minion to damage an enemy.
     * 
     * @param character     - The character being prompted, contains ability functions and it's own stats.
     * @param input         - The scanner used to detect user input.
     * @param gs            - The game system that stores the coordinates and what inhabits them.
     * @param ds            - The displayer class containing a handful of vital methods. 
     */
    private void promptNecromancer(Character character, Scanner input, GameSystem gs, Displayer ds) {
        if (character.CheckAbility1Possible(gs)) {
            System.out.println("Ability 1 - Ressurect");
        }
        if (character.CheckAbility2Possible(gs)) {
            System.out.println("Ability 2 - Strengthen");
        }
        if (character.CheckAbility3Possible(gs)) {
            System.out.println("Ability 3 - Meat spike");
        }
        System.out.print("Choose ability: ");
        int abilityChoice = input.nextInt();
        if (abilityChoice == 1) {
            System.out.print("Choose x-coordinate of the minion you would like to create: ");
            int x = input.nextInt();
            System.out.print("Choose y-coordinate of the minion you would like to create:");
            int y = input.nextInt();
            ActionContext SummonMinionAbility1 = new ActionContext(x, y, gs.gameBoard);
            boolean success = character.Ability1(SummonMinionAbility1);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName() + " creates a minion!");
                ds.PrintGrid(gs.gameBoard);
            } else {
                System.out.println(character.GetFullName() + " the " + character.GetName() + " fails to create a minion");
            }
        } else if (abilityChoice == 2) {
            System.out.print("Choose x-coordinate of the minion you would like to buff: ");
            int x = input.nextInt();
            System.out.print("Choose y-coordinate of the minion you would like to buff:");
            int y = input.nextInt();
            Entity target = gs.gameBoard[y][x].getEntity();
            ActionContext BuffMinionAbility2 = new ActionContext(target);
            boolean success = character.Ability2(BuffMinionAbility2);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName() + " buffs a minion!");
            } else {
                System.out.println(character.GetFullName() + " the " + character.GetName() + " fails to buff a minion!");
            }
        } else if (abilityChoice == 3) {
            //TODO uhhhhhhhh
        } else {
            System.out.println("s");
        }
    }

    /* Helper method to streamline prompting a barbarian character. 
     * Ideally we would place this in each individual class, but that would very much break frontend backend. 
     * The system prompts an integer between 0 and 3 and then selects an attack based off the user's input.
     * 0    :Skip move, don't do anything.
     * 1    :Attack that buffs your intelligence.
     * 2    :Heal self.
     * 3    :Close range strong melee attack.
     * 
     * @param character     - The character being prompted, contains ability functions and it's own stats.
     * @param input         - The scanner used to detect user input.
     * @param gs            - The game system that stores the coordinates and what inhabits them.
     * @param ds            - The displayer class containing a handful of vital methods. 
     */
    private void promptPaladin(Character character, Scanner input, GameSystem gs, Displayer ds) {
        if (character.CheckAbility1Possible(gs)) {
            System.out.println("Ability 1 - Verdict");
        }
        if (character.CheckAbility2Possible(gs)) {
            System.out.println("Ability 2 - Heal");
        }
        if (character.CheckAbility3Possible(gs)) {
            System.out.println("Ability 3 - Strike");
        }
        System.out.print("Choose ability: ");
        int abilityChoice = input.nextInt();
        if (abilityChoice == 1) {
            System.out.print("Enter the x-coordinate of the enemy you would like to attack: ");
            int x = input.nextInt();
            System.out.print("Enter the y-coordinate of the enemy you would like to attack:");
            int y = input.nextInt();
            ActionContext VerdictAbility1 = new ActionContext(x, y, gs.gameBoard);
            boolean success = character.Ability1(VerdictAbility1);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName() + " attacks!");
                ds.PrintGrid(gs.gameBoard);
            } else {
                System.out.println(character.GetFullName() + " the " + character.GetName() + " fails to attack");
            }
        } else if (abilityChoice == 2) {
            ActionContext HealAbility2 = new ActionContext(character);
            boolean success = character.Ability2(HealAbility2);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName() + " heals!");
            } else {
                System.out.println(character.GetFullName() + " the " + character.GetName() + " fails to heal!");
            }
        } else if (abilityChoice == 3) {
            System.out.print("Enter the x-coordinate of the enemy you would like to attack: ");
            int x = input.nextInt();
            System.out.print("Enter the y-coordinate of the enemy you would like to attack:");
            int y = input.nextInt();
            ActionContext StrikeAbility3 = new ActionContext(x, y, gs.gameBoard);
            boolean success = character.Ability3(StrikeAbility3);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName() + " attacks!");
                ds.PrintGrid(gs.gameBoard);
            } else {
                System.out.println(character.GetFullName() + " the " + character.GetName() + " fails to attack");
            }
        } else {
            System.out.println("s");
        }
    }


    public static void main(String[] args) {
        GameSystem gs = new GameSystem();
        Displayer ds = new Displayer();
        Scanner input = new Scanner(System.in);
        Character c = new Character();
        RPGMain rpg = new RPGMain();
        boolean gameRunning = true;
        int playerChoice;

        while (gameRunning) {
            System.out.println("Welcome to the RPG Game!");
            System.out.println("What would you like to do? \n1. Start Game \n2. Hall of fame\n3. Exit");
            playerChoice = input.nextInt();
            if (playerChoice == 1) {
                for (int i = 1; i <= 2; i++) {
                    System.out.println("Initializing Player " + i + "'s team...");
                    for (int j = 0; j < 3; j++) {
                        boolean hasSelected = false;
                        int selectedClass = -1;
                        Character classlessCharacter = new Character().GenerateCharacter();
                        classlessCharacter.SetTeam(i);

                        while (!hasSelected) {
                            System.out.println("Character " + (j + 1) + ":");
                            ds.PrintInitialStats(new Character[] { classlessCharacter });
                            System.out.println(
                                    "Which class would you like your character to be? (can back out) \n1. Necromancer \n2. Healer \n3. Crusader \n4. Barbarian \n5. Paladin \n6. Guardian");
                            int Choice = input.nextInt();
                            if (Choice == 1) {
                                System.out.println(
                                        "Necromancer \nA disturbed warlock who conjures and manipulates the dead. \nAbilities: \nAbility 1: Summon Minion - Summons a weak minion to fight. ");
                                System.out.println(
                                        "Ability 2: Buff Minion - Buffs an adjacent minion \nAbility \3: Meat Shield - Sacrafice an adjacent minion to tank a hit.");
                                System.out.println(
                                        "Stat buffs: \n-1 speed\n+3 intelligence\n+1 attack\n+2 spirit\n+1 health\n+2 spellpower");
                                System.out.println("Would you like to select this class? (y/N)");
                                String confirm = input.next();
                                if (confirm.equalsIgnoreCase("Y")) {
                                    selectedClass = 1;
                                    hasSelected = true;
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
                                String confirm = input.next();
                                if (confirm.equalsIgnoreCase("Y")) {
                                    selectedClass = 2;
                                    hasSelected = true;
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
                                String confirm = input.next();
                                if (confirm.equalsIgnoreCase("Y")) {
                                    selectedClass = 3;
                                    hasSelected = true;
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
                                String confirm = input.next();
                                if (confirm.equalsIgnoreCase("Y")) {
                                    selectedClass = 4;
                                    hasSelected = true;
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
                                String confirm = input.next();
                                if (confirm.equalsIgnoreCase("Y")) {
                                    selectedClass = 5;
                                    hasSelected = true;
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
                                String confirm = input.next();
                                if (confirm.equalsIgnoreCase("Y")) {
                                    selectedClass = 6;
                                    hasSelected = true;
                                } else {
                                    System.out.println("Returning to selection.");
                                }
                            } else {
                                System.out.println("Invalid Choice, please try again.");
                            }
                        }
                        if (selectedClass == 1) {
                            if (i == 1) {
                                gs.player1.PlayerTeam[j] = new Necromancer(classlessCharacter, i);
                            } else {
                                gs.player2.PlayerTeam[j] = new Necromancer(classlessCharacter, i);
                            }
                        } else if (selectedClass == 2) {
                            if (i == 1) {
                                gs.player1.PlayerTeam[j] = new Healer(classlessCharacter, i);
                            } else {
                                gs.player2.PlayerTeam[j] = new Healer(classlessCharacter, i);
                            }
                        } else if (selectedClass == 3) {
                            if (i == 1) {
                                gs.player1.PlayerTeam[j] = new Crusader(classlessCharacter, i);
                            } else {
                                gs.player2.PlayerTeam[j] = new Crusader(classlessCharacter, i);
                            }
                        } else if (selectedClass == 4) {
                            if (i == 1) {
                                gs.player1.PlayerTeam[j] = new Barbarian(classlessCharacter, i);
                            } else {
                                gs.player2.PlayerTeam[j] = new Barbarian(classlessCharacter, i);
                            }
                        } else if (selectedClass == 5) {
                            if (i == 1) {
                                gs.player1.PlayerTeam[j] = new Paladin(classlessCharacter, i);
                            } else {
                                gs.player2.PlayerTeam[j] = new Paladin(classlessCharacter, i);
                            }
                        } else if (selectedClass == 6) {
                            if (i == 1) {
                                gs.player1.PlayerTeam[j] = new Guardian(classlessCharacter, i);
                            } else {
                                gs.player2.PlayerTeam[j] = new Guardian(classlessCharacter, i);
                            }
                        }
                    } // End of generating 3 characters
                    System.out.println("PLAYER " + i + " TEAM:");
                    if (i == 1) {
                        ds.PrintInitialStats(gs.player1.PlayerTeam);
                    } else {
                        ds.PrintInitialStats(gs.player2.PlayerTeam);
                    }
                    if (i == 1) {
                        System.out.println(
                                "Now initializing next player's team. Please hand over the computer. Press [ENTER] when ready.");
                    } else {
                        System.out.println("Teams initialized. Press [ENTER] to continue.");
                    }
                    input.nextLine();
                    input.nextLine();
                } // End of generating 2 teams
                gs.player1.PlayerTeam[0].SetPosition(new int[] { 0, 0 }, gs.gameBoard);
                gs.player1.PlayerTeam[1].SetPosition(new int[] { 1, 0 }, gs.gameBoard);
                gs.player1.PlayerTeam[2].SetPosition(new int[] { 0, 1 }, gs.gameBoard);
                gs.player2.PlayerTeam[0].SetPosition(new int[] { 7, 7 }, gs.gameBoard);
                gs.player2.PlayerTeam[1].SetPosition(new int[] { 6, 7 }, gs.gameBoard);
                gs.player2.PlayerTeam[2].SetPosition(new int[] { 7, 6 }, gs.gameBoard);
                gs.PopulateGameBoard(7, 7);
                ds.PrintGrid(gs.gameBoard);
                while (!gs.CheckWin()) {
                    int p1TotalSpeed = 0;
                    int p2TotalSpeed = 0;
                    for (int i = 0; i < 3; i++) {
                        p1TotalSpeed += gs.player1.PlayerTeam[i].GetSpd();
                        p2TotalSpeed += gs.player2.PlayerTeam[i].GetSpd();
                    }
                    if (p1TotalSpeed >= p2TotalSpeed) {
                        // --- PLAYER 1 GOES FIRST ---
                        System.out.println("Player 1 speed: " + p1TotalSpeed + "\nPlayer 2 speed: " + p2TotalSpeed);
                        int maxSpdIndexP1 = c.GetMaxSpeedIndex(gs.player1.PlayerTeam);
                        int medSpdIndexP1 = c.GetMedianSpeedIndex(maxSpdIndexP1, gs.player1.PlayerTeam);
                        int lowSpdIndexP1 = 0;
                        for (int i = 0; i < 3; i++) {
                            if (i != maxSpdIndexP1 && i != medSpdIndexP1) {
                                lowSpdIndexP1 = i;
                            }
                        }
                        Character maxSpdCharacter = gs.player1.PlayerTeam[maxSpdIndexP1];
                        Character medSpdCharacter = gs.player1.PlayerTeam[medSpdIndexP1];
                        Character lowSpdCharacter = gs.player1.PlayerTeam[lowSpdIndexP1];
                        System.out.println("Player 1 starts! Press [ENTER] to continue.");
                        input.nextLine();
                        if (maxSpdCharacter.GetIsAlive() && !maxSpdCharacter.GetIsStunned()) {
                            rpg.promptMove(maxSpdCharacter, input, gs, ds);
                            if (maxSpdCharacter.CheckAbility1Possible(gs) || maxSpdCharacter.CheckAbility2Possible(gs)
                                    || maxSpdCharacter.CheckAbility3Possible(gs)) {
                                if (maxSpdCharacter instanceof Barbarian) {
                                    rpg.promptBarbarian(maxSpdCharacter, input, gs, ds);
                                } else if (maxSpdCharacter instanceof Crusader) {
                                    rpg.promptCrusader(maxSpdCharacter, input, gs, ds);
                                } else if (maxSpdCharacter instanceof Guardian) {
                                    rpg.promptGuardian(maxSpdCharacter, input, gs, ds);
                                } else if (maxSpdCharacter instanceof Healer) {
                                    rpg.promptHealer(maxSpdCharacter, input, gs, ds);
                                } else if (maxSpdCharacter instanceof Necromancer) {
                                    rpg.promptNecromancer(maxSpdCharacter, input, gs, ds);
                                } else {
                                    rpg.promptPaladin(maxSpdCharacter, input, gs, ds);
                                }
                            } else {
                                System.out.println(maxSpdCharacter.GetFullName() + " the " + maxSpdCharacter.GetName()
                                        + " cannot cast any abilities this round.");
                            }
                        }
                        if (medSpdCharacter.GetIsAlive() && !medSpdCharacter.GetIsStunned()) {
                            rpg.promptMove(medSpdCharacter, input, gs, ds);
                            if (medSpdCharacter.CheckAbility1Possible(gs) || medSpdCharacter.CheckAbility2Possible(gs)
                                    || medSpdCharacter.CheckAbility3Possible(gs)) {
                                if (medSpdCharacter instanceof Barbarian) {
                                    rpg.promptBarbarian(medSpdCharacter, input, gs, ds);
                                } else if (medSpdCharacter instanceof Crusader) {
                                    rpg.promptCrusader(medSpdCharacter, input, gs, ds);
                                } else if (medSpdCharacter instanceof Guardian) {
                                    rpg.promptGuardian(medSpdCharacter, input, gs, ds);
                                } else if (medSpdCharacter instanceof Healer) {
                                    rpg.promptHealer(medSpdCharacter, input, gs, ds);
                                } else if (medSpdCharacter instanceof Necromancer) {
                                    rpg.promptNecromancer(medSpdCharacter, input, gs, ds);
                                } else {
                                    rpg.promptPaladin(medSpdCharacter, input, gs, ds);
                                }
                            } else {
                                System.out.println(medSpdCharacter.GetFullName() + " the " + medSpdCharacter.GetName()
                                        + " cannot cast any abilities this round.");
                            }
                        }
                        if (lowSpdCharacter.GetIsAlive() && !lowSpdCharacter.GetIsStunned()) {
                            rpg.promptMove(lowSpdCharacter, input, gs, ds);
                            if (lowSpdCharacter.CheckAbility1Possible(gs) || lowSpdCharacter.CheckAbility2Possible(gs)
                                    || lowSpdCharacter.CheckAbility3Possible(gs)) {
                                if (lowSpdCharacter instanceof Barbarian) {
                                    rpg.promptBarbarian(lowSpdCharacter, input, gs, ds);
                                } else if (lowSpdCharacter instanceof Crusader) {
                                    rpg.promptCrusader(lowSpdCharacter, input, gs, ds);
                                } else if (lowSpdCharacter instanceof Guardian) {
                                    rpg.promptGuardian(lowSpdCharacter, input, gs, ds);
                                } else if (lowSpdCharacter instanceof Healer) {
                                    rpg.promptHealer(lowSpdCharacter, input, gs, ds);
                                } else if (lowSpdCharacter instanceof Necromancer) {
                                    rpg.promptNecromancer(lowSpdCharacter, input, gs, ds);
                                } else {
                                    rpg.promptPaladin(lowSpdCharacter, input, gs, ds);
                                }
                            } else {
                                System.out.println(lowSpdCharacter.GetFullName() + " the " + lowSpdCharacter.GetName()
                                        + " cannot cast any abilities this round.");
                            }
                        }
                        // --- PLAYER 2 GOES SECOND ---
                        System.out.println("Player 2's turn. Press [ENTER] to continue.");
                        input.nextLine();
                        int maxSpdIndexP2 = c.GetMaxSpeedIndex(gs.player2.PlayerTeam);
                        int medSpdIndexP2 = c.GetMedianSpeedIndex(maxSpdIndexP2, gs.player2.PlayerTeam);
                        int lowSpdIndexP2 = 0;
                        for (int i = 0; i < 3; i++) {
                            if (i != maxSpdIndexP2 && i != medSpdIndexP2) {
                                lowSpdIndexP2 = i;
                            }
                        }
                        maxSpdCharacter = gs.player2.PlayerTeam[maxSpdIndexP2];
                        medSpdCharacter = gs.player2.PlayerTeam[medSpdIndexP2];
                        lowSpdCharacter = gs.player2.PlayerTeam[lowSpdIndexP2];
                        if (maxSpdCharacter.GetIsAlive() && !maxSpdCharacter.GetIsStunned()) {
                            rpg.promptMove(maxSpdCharacter, input, gs, ds);
                            if (maxSpdCharacter.CheckAbility1Possible(gs) || maxSpdCharacter.CheckAbility2Possible(gs)
                                    || maxSpdCharacter.CheckAbility3Possible(gs)) {
                                if (maxSpdCharacter instanceof Barbarian) {
                                    rpg.promptBarbarian(maxSpdCharacter, input, gs, ds);
                                } else if (maxSpdCharacter instanceof Crusader) {
                                    rpg.promptCrusader(maxSpdCharacter, input, gs, ds);
                                } else if (maxSpdCharacter instanceof Guardian) {
                                    rpg.promptGuardian(maxSpdCharacter, input, gs, ds);
                                } else if (maxSpdCharacter instanceof Healer) {
                                    rpg.promptHealer(maxSpdCharacter, input, gs, ds);
                                } else if (maxSpdCharacter instanceof Necromancer) {
                                    rpg.promptNecromancer(maxSpdCharacter, input, gs, ds);
                                } else {
                                    rpg.promptPaladin(maxSpdCharacter, input, gs, ds);
                                }
                            } else {
                                System.out.println(maxSpdCharacter.GetFullName() + " the " + maxSpdCharacter.GetName()
                                        + " cannot cast any abilities this round.");
                            }
                        }
                        if (medSpdCharacter.GetIsAlive() && !maxSpdCharacter.GetIsStunned()) {
                            rpg.promptMove(medSpdCharacter, input, gs, ds);
                            if (medSpdCharacter.CheckAbility1Possible(gs) || medSpdCharacter.CheckAbility2Possible(gs)
                                    || medSpdCharacter.CheckAbility3Possible(gs)) {
                                if (medSpdCharacter instanceof Barbarian) {
                                    rpg.promptBarbarian(medSpdCharacter, input, gs, ds);
                                } else if (medSpdCharacter instanceof Crusader) {
                                    rpg.promptCrusader(medSpdCharacter, input, gs, ds);
                                } else if (medSpdCharacter instanceof Guardian) {
                                    rpg.promptGuardian(medSpdCharacter, input, gs, ds);
                                } else if (medSpdCharacter instanceof Healer) {
                                    rpg.promptHealer(medSpdCharacter, input, gs, ds);
                                } else if (medSpdCharacter instanceof Necromancer) {
                                    rpg.promptNecromancer(medSpdCharacter, input, gs, ds);
                                } else {
                                    rpg.promptPaladin(medSpdCharacter, input, gs, ds);
                                }
                            } else {
                                System.out.println(medSpdCharacter.GetFullName() + " the " + medSpdCharacter.GetName()
                                        + " cannot cast any abilities this round.");
                            }
                        }
                        if (lowSpdCharacter.GetIsAlive() && !maxSpdCharacter.GetIsStunned()) {
                            rpg.promptMove(lowSpdCharacter, input, gs, ds);
                            if (lowSpdCharacter.CheckAbility1Possible(gs) || lowSpdCharacter.CheckAbility2Possible(gs)
                                    || lowSpdCharacter.CheckAbility3Possible(gs)) {
                                if (lowSpdCharacter instanceof Barbarian) {
                                    rpg.promptBarbarian(lowSpdCharacter, input, gs, ds);
                                } else if (lowSpdCharacter instanceof Crusader) {
                                    rpg.promptCrusader(lowSpdCharacter, input, gs, ds);
                                } else if (lowSpdCharacter instanceof Guardian) {
                                    rpg.promptGuardian(lowSpdCharacter, input, gs, ds);
                                } else if (lowSpdCharacter instanceof Healer) {
                                    rpg.promptHealer(lowSpdCharacter, input, gs, ds);
                                } else if (lowSpdCharacter instanceof Necromancer) {
                                    rpg.promptNecromancer(lowSpdCharacter, input, gs, ds);
                                } else {
                                    rpg.promptPaladin(lowSpdCharacter, input, gs, ds);
                                }
                            } else {
                                System.out.println(lowSpdCharacter.GetFullName() + " the " + lowSpdCharacter.GetName()
                                        + " cannot cast any abilities this round.");
                            }
                        }
                    } else {
                        // --- PLAYER 2 GOES FIRST ---
                        System.out.println("Player 2 speed: " + p2TotalSpeed + "\nPlayer 1 speed: " + p1TotalSpeed);
                        int maxSpdIndexP2 = c.GetMaxSpeedIndex(gs.player2.PlayerTeam);
                        int medSpdIndexP2 = c.GetMedianSpeedIndex(maxSpdIndexP2, gs.player2.PlayerTeam);
                        int lowSpdIndexP2 = 0;
                        for (int i = 0; i < 3; i++) {
                            if (i != maxSpdIndexP2 && i != medSpdIndexP2) {
                                lowSpdIndexP2 = i;
                            }
                        }
                        Character maxSpdCharacter = gs.player2.PlayerTeam[maxSpdIndexP2];
                        Character medSpdCharacter = gs.player2.PlayerTeam[medSpdIndexP2];
                        Character lowSpdCharacter = gs.player2.PlayerTeam[lowSpdIndexP2];
                        System.out.println("Player 2 starts! Press [ENTER] to continue.");
                        input.nextLine();
                        if (maxSpdCharacter.GetIsAlive() && !maxSpdCharacter.GetIsStunned()) {
                            rpg.promptMove(maxSpdCharacter, input, gs, ds);
                            if (maxSpdCharacter.CheckAbility1Possible(gs) || maxSpdCharacter.CheckAbility2Possible(gs)
                                    || maxSpdCharacter.CheckAbility3Possible(gs)) {
                                if (maxSpdCharacter instanceof Barbarian) {
                                    rpg.promptBarbarian(maxSpdCharacter, input, gs, ds);
                                } else if (maxSpdCharacter instanceof Crusader) {
                                    rpg.promptCrusader(maxSpdCharacter, input, gs, ds);
                                } else if (maxSpdCharacter instanceof Guardian) {
                                    rpg.promptGuardian(maxSpdCharacter, input, gs, ds);
                                } else if (maxSpdCharacter instanceof Healer) {
                                    rpg.promptHealer(maxSpdCharacter, input, gs, ds);
                                } else if (maxSpdCharacter instanceof Necromancer) {
                                    rpg.promptNecromancer(maxSpdCharacter, input, gs, ds);
                                } else {
                                    rpg.promptPaladin(maxSpdCharacter, input, gs, ds);
                                }
                            } else {
                                System.out.println(maxSpdCharacter.GetFullName() + " the " + maxSpdCharacter.GetName()
                                        + " cannot cast any abilities this round.");
                            }
                        }
                        if (medSpdCharacter.GetIsAlive() && !medSpdCharacter.GetIsStunned()) {
                            rpg.promptMove(medSpdCharacter, input, gs, ds);
                            if (medSpdCharacter.CheckAbility1Possible(gs) || medSpdCharacter.CheckAbility2Possible(gs)
                                    || medSpdCharacter.CheckAbility3Possible(gs)) {
                                if (medSpdCharacter instanceof Barbarian) {
                                    rpg.promptBarbarian(medSpdCharacter, input, gs, ds);
                                } else if (medSpdCharacter instanceof Crusader) {
                                    rpg.promptCrusader(medSpdCharacter, input, gs, ds);
                                } else if (medSpdCharacter instanceof Guardian) {
                                    rpg.promptGuardian(medSpdCharacter, input, gs, ds);
                                } else if (medSpdCharacter instanceof Healer) {
                                    rpg.promptHealer(medSpdCharacter, input, gs, ds);
                                } else if (medSpdCharacter instanceof Necromancer) {
                                    rpg.promptNecromancer(medSpdCharacter, input, gs, ds);
                                } else {
                                    rpg.promptPaladin(medSpdCharacter, input, gs, ds);
                                }
                            } else {
                                System.out.println(medSpdCharacter.GetFullName() + " the " + medSpdCharacter.GetName()
                                        + " cannot cast any abilities this round.");
                            }
                        }
                        if (lowSpdCharacter.GetIsAlive() && !lowSpdCharacter.GetIsStunned()) {
                            rpg.promptMove(lowSpdCharacter, input, gs, ds);
                            if (lowSpdCharacter.CheckAbility1Possible(gs) || lowSpdCharacter.CheckAbility2Possible(gs)
                                    || lowSpdCharacter.CheckAbility3Possible(gs)) {
                                if (lowSpdCharacter instanceof Barbarian) {
                                    rpg.promptBarbarian(lowSpdCharacter, input, gs, ds);
                                } else if (lowSpdCharacter instanceof Crusader) {
                                    rpg.promptCrusader(lowSpdCharacter, input, gs, ds);
                                } else if (lowSpdCharacter instanceof Guardian) {
                                    rpg.promptGuardian(lowSpdCharacter, input, gs, ds);
                                } else if (lowSpdCharacter instanceof Healer) {
                                    rpg.promptHealer(lowSpdCharacter, input, gs, ds);
                                } else if (lowSpdCharacter instanceof Necromancer) {
                                    rpg.promptNecromancer(lowSpdCharacter, input, gs, ds);
                                } else {
                                    rpg.promptPaladin(lowSpdCharacter, input, gs, ds);
                                }
                            } else {
                                System.out.println(lowSpdCharacter.GetFullName() + " the " + lowSpdCharacter.GetName()
                                        + " cannot cast any abilities this round.");
                            }
                        }
                        // --- PLAYER 1 GOES SECOND ---
                        System.out.println("Player 1's turn. Press [ENTER] to continue.");
                        input.nextLine();
                        int maxSpdIndexP1 = c.GetMaxSpeedIndex(gs.player1.PlayerTeam);
                        int medSpdIndexP1 = c.GetMedianSpeedIndex(maxSpdIndexP1, gs.player1.PlayerTeam);
                        int lowSpdIndexP1 = 0;
                        for (int i = 0; i < 3; i++) {
                            if (i != maxSpdIndexP1 && i != medSpdIndexP1) {
                                lowSpdIndexP1 = i;
                            }
                        }
                        maxSpdCharacter = gs.player1.PlayerTeam[maxSpdIndexP1];
                        medSpdCharacter = gs.player1.PlayerTeam[medSpdIndexP1];
                        lowSpdCharacter = gs.player1.PlayerTeam[lowSpdIndexP1];
                        if (maxSpdCharacter.GetIsAlive() && !maxSpdCharacter.GetIsStunned()) {
                            rpg.promptMove(maxSpdCharacter, input, gs, ds);
                            if (maxSpdCharacter.CheckAbility1Possible(gs) || maxSpdCharacter.CheckAbility2Possible(gs)
                                    || maxSpdCharacter.CheckAbility3Possible(gs)) {
                                if (maxSpdCharacter instanceof Barbarian) {
                                    rpg.promptBarbarian(maxSpdCharacter, input, gs, ds);
                                } else if (maxSpdCharacter instanceof Crusader) {
                                    rpg.promptCrusader(maxSpdCharacter, input, gs, ds);
                                } else if (maxSpdCharacter instanceof Guardian) {
                                    rpg.promptGuardian(maxSpdCharacter, input, gs, ds);
                                } else if (maxSpdCharacter instanceof Healer) {
                                    rpg.promptHealer(maxSpdCharacter, input, gs, ds);
                                } else if (maxSpdCharacter instanceof Necromancer) {
                                    rpg.promptNecromancer(maxSpdCharacter, input, gs, ds);
                                } else {
                                    rpg.promptPaladin(maxSpdCharacter, input, gs, ds);
                                }
                            } else {
                                System.out.println(maxSpdCharacter.GetFullName() + " the " + maxSpdCharacter.GetName()
                                        + " cannot cast any abilities this round.");
                            }
                        }
                        if (medSpdCharacter.GetIsAlive() && !maxSpdCharacter.GetIsStunned()) {
                            rpg.promptMove(medSpdCharacter, input, gs, ds);
                            if (medSpdCharacter.CheckAbility1Possible(gs) || medSpdCharacter.CheckAbility2Possible(gs)
                                    || medSpdCharacter.CheckAbility3Possible(gs)) {
                                if (medSpdCharacter instanceof Barbarian) {
                                    rpg.promptBarbarian(medSpdCharacter, input, gs, ds);
                                } else if (medSpdCharacter instanceof Crusader) {
                                    rpg.promptCrusader(medSpdCharacter, input, gs, ds);
                                } else if (medSpdCharacter instanceof Guardian) {
                                    rpg.promptGuardian(medSpdCharacter, input, gs, ds);
                                } else if (medSpdCharacter instanceof Healer) {
                                    rpg.promptHealer(medSpdCharacter, input, gs, ds);
                                } else if (medSpdCharacter instanceof Necromancer) {
                                    rpg.promptNecromancer(medSpdCharacter, input, gs, ds);
                                } else {
                                    rpg.promptPaladin(medSpdCharacter, input, gs, ds);
                                }
                            } else {
                                System.out.println(medSpdCharacter.GetFullName() + " the " + medSpdCharacter.GetName()
                                        + " cannot cast any abilities this round.");
                            }
                        }
                        if (lowSpdCharacter.GetIsAlive() && !maxSpdCharacter.GetIsStunned()) {
                            rpg.promptMove(lowSpdCharacter, input, gs, ds);
                            if (lowSpdCharacter.CheckAbility1Possible(gs) || lowSpdCharacter.CheckAbility2Possible(gs)
                                    || lowSpdCharacter.CheckAbility3Possible(gs)) {
                                if (lowSpdCharacter instanceof Barbarian) {
                                    rpg.promptBarbarian(lowSpdCharacter, input, gs, ds);
                                } else if (lowSpdCharacter instanceof Crusader) {
                                    rpg.promptCrusader(lowSpdCharacter, input, gs, ds);
                                } else if (lowSpdCharacter instanceof Guardian) {
                                    rpg.promptGuardian(lowSpdCharacter, input, gs, ds);
                                } else if (lowSpdCharacter instanceof Healer) {
                                    rpg.promptHealer(lowSpdCharacter, input, gs, ds);
                                } else if (lowSpdCharacter instanceof Necromancer) {
                                    rpg.promptNecromancer(lowSpdCharacter, input, gs, ds);
                                } else {
                                    rpg.promptPaladin(lowSpdCharacter, input, gs, ds);
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
                    gameRunning = false;
                }
                gs.RegenerateCharacters(gs.player1.PlayerTeam);
                gs.RegenerateCharacters(gs.player2.PlayerTeam);
            } // TODO add the other two options
            else if (playerChoice == 2) {
                
            }
        } // STOP RUNNING
        input.close();
    }
}