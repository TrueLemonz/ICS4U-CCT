/* Main frontend class, handles all Input and Output between the user and the backend.
 * All of the printing (aside from Displayer.java) takes place here.
 * This is also the file that should be run in order to properly play the game. */
import java.util.Scanner;

public class RPGMain {

    /*
     * Helper method to reduce amount of code needed per loop.
     * Handles several conveneint functions but mainly the movement of a selected
     * character.
     *
     * @param character - The character selected to move
     * 
     * @param input - The scanner used to detect user input
     * 
     * @param gs - The game system that stores the coordinates and what inhabits
     * them.
     * 
     * @param ds - The displayer class containing a handful of vital methods.
     */
    private void promptMove(Character character, Scanner input, GameSystem gs, Displayer ds) {
        System.out.println(character.GetFullName() + " the " + character.GetName() + "'s turn.");
        ds.PrintStats(new Character[] { character });
        System.out.println("Move " + character.GetFullName() + " the " + character.GetName() + "!");
        System.out.print("Choose x-coordinate: ");
        int x = input.nextInt();
        System.out.print("Choose y-coordinate: ");
        int y = input.nextInt();
        boolean moved = gs.Move(character, x, y);
        if (!moved) {
            System.out.println("Move failed — tile is out of range or occupied.");
        }
        ds.PrintGrid(gs.GameBoard);
    }

    /*
     * Helper method to streamline prompting a barbarian character.
     * Ideally we would place this in each individual class, but that would very
     * much break frontend backend.
     * The system prompts an integer between 0 and 3 and then selects an attack
     * based off the user's input.
     * 0 :Skip move, don't do anything.
     * 1 :Flip a character to the opposite side, dealing damage.
     * 2 :Kickpunch an enemy, doing massive amounts of damage.
     * 3 :Infect yourself with lupus, dealing self-damage and increasing power
     * output.
     * 
     * @param character - The character being prompted, contains ability functions
     * and it's own stats.
     * 
     * @param input - The scanner used to detect user input.
     * 
     * @param gs - The game system that stores the coordinates and what inhabits
     * them.
     * 
     * @param ds - The displayer class containing a handful of vital methods.
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
        System.out.print("Choose ability (0 to skip): ");
        int abilityChoice = input.nextInt();

        if (abilityChoice == 1) {
            System.out.print("Choose x-coordinate of enemy to flip: ");
            int x = input.nextInt();
            System.out.print("Choose y-coordinate of enemy to flip: ");
            int y = input.nextInt();
            if (y < 0 || y >= GameSystem.GAMEHEIGHT || x < 0 || x >= GameSystem.GAMEWIDTH) {
                System.out.println("Coordinates out of bounds.");
                return;
            }
            Character target = gs.GameBoard[y][x].GetEntity().GetCharacter();
            if (target == null) {
                System.out.println("No character at that location.");
                return;
            }
            ActionContext barbAbility1 = new ActionContext(target, gs.GameBoard);
            boolean success = character.Ability1(barbAbility1);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName()
                        + " flips " + target.GetFullName() + " the " + target.GetName() + "!");
                ds.PrintGrid(gs.GameBoard);
            } else {
                System.out.println("Flip failed.");
            }

        } else if (abilityChoice == 2) {
            System.out.print("Choose x-coordinate of enemy to strike: ");
            int x = input.nextInt();
            System.out.print("Choose y-coordinate of enemy to strike: ");
            int y = input.nextInt();
            if (y < 0 || y >= GameSystem.GAMEHEIGHT || x < 0 || x >= GameSystem.GAMEWIDTH) {
                System.out.println("Coordinates out of bounds.");
                return;
            }
            Character target = gs.GameBoard[y][x].GetEntity().GetCharacter();
            if (target == null) {
                System.out.println("No character at that location.");
                return;
            }
            ActionContext barbAbility2 = new ActionContext(target, gs.GameBoard);
            boolean success = character.Ability2(barbAbility2);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName()
                        + " strikes " + target.GetFullName() + " the " + target.GetName() + "!");
            } else {
                System.out.println("Kickpunch failed.");
            }

        } else if (abilityChoice == 3) {
            ActionContext barbAbility3 = new ActionContext(character);
            boolean success = character.Ability3(barbAbility3);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName()
                        + " rages with Lupus! Attack permanently increased.");
            } else {
                System.out.println("Lupus failed — insufficient health or magic.");
            }

        } else if (abilityChoice == 0) {
            System.out.println(character.GetFullName() + " skips their ability.");
        } else {
            System.out.println("Invalid choice.");
        }
    }
/*
     * Helper method to streamline prompting a healer character.
     * Ideally we would place this in each individual class, but that would very
     * much break frontend backend.
     * The system prompts an integer between 0 and 3 and then selects an attack
     * based off the user's input.
     * 0 :Skip move, don't do anything.
     * 1 :Pray for a character, dealing damage to enemies and healing allies.
     * 2 :Praise an ally, granting stat buffs.
     * 3 :Strike an enemy down with lightning, possibly stunning them and dealing
     * small damage.
     * 
     * @param character - The character being prompted, contains ability functions
     * and it's own stats.
     * 
     * @param input - The scanner used to detect user input.
     * 
     * @param gs - The game system that stores the coordinates and what inhabits
     * them.
     * 
     * @param ds - The displayer class containing a handful of vital methods.
     */
    private void promptHealer(Character character, Scanner input, GameSystem gs, Displayer ds) {
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
        System.out.print("Choose ability (0 to skip): ");
        int abilityChoice = input.nextInt();

        if (abilityChoice == 1) {
            ActionContext healerAbility1 = new ActionContext(0, 0, gs.GameBoard);
            boolean success = character.Ability1(healerAbility1);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName()
                        + " casts Prayer.");
            } else {
                System.out.println("Prayer failed — no valid targets in range.");
            }

        } else if (abilityChoice == 2) {
            System.out.print("Enter x-coordinate of ally to buff: ");
            int x = input.nextInt();
            System.out.print("Enter y-coordinate of ally to buff: ");
            int y = input.nextInt();
            if (y < 0 || y >= GameSystem.GAMEHEIGHT || x < 0 || x >= GameSystem.GAMEWIDTH) {
                System.out.println("Coordinates out of bounds.");
                return;
            }
            Character target = gs.GameBoard[y][x].GetEntity().GetCharacter();
            if (target == null) {
                System.out.println("No character at that location.");
                return;
            }
            ActionContext healerAbility2 = new ActionContext(target, gs.GameBoard);
            boolean success = character.Ability2(healerAbility2);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName()
                        + " blesses " + target.GetFullName() + " the " + target.GetName() + ".");
            } else {
                System.out.println("Praise failed.");
            }

        } else if (abilityChoice == 3) {
            System.out.print("Enter x-coordinate of enemy to strike: ");
            int x = input.nextInt();
            System.out.print("Enter y-coordinate of enemy to strike: ");
            int y = input.nextInt();
            if (y < 0 || y >= GameSystem.GAMEHEIGHT || x < 0 || x >= GameSystem.GAMEWIDTH) {
                System.out.println("Coordinates out of bounds.");
                return;
            }
            Character target = gs.GameBoard[y][x].GetEntity().GetCharacter();
            if (target == null) {
                System.out.println("No character at that location.");
                return;
            }
            ActionContext healerAbility3 = new ActionContext(target, gs.GameBoard);
            boolean success = character.Ability3(healerAbility3);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName()
                        + " strikes " + target.GetFullName() + " the " + target.GetName() + ".");
            } else {
                System.out.println("Strike failed.");
            }

        } else if (abilityChoice == 0) {
            System.out.println(character.GetFullName() + " skips their ability.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    /*
     * Helper method to streamline prompting a crusader character.
     * Ideally we would place this in each individual class, but that would very
     * much break frontend backend.
     * The system prompts an integer between 0 and 3 and then selects an attack
     * based off the user's input.
     * 0 :Skip move, don't do anything.
     * 1 :Massive buff.
     * 2 :Summon a ray of holy light on an ally, healing them and granting a speed
     * buff.
     * 3 :Bash an enemy with your sheild for medium damage.
     * 
     * @param character - The character being prompted, contains ability functions
     * and it's own stats.
     * 
     * @param input - The scanner used to detect user input.
     * 
     * @param gs - The game system that stores the coordinates and what inhabits
     * them.
     * 
     * @param ds - The displayer class containing a handful of vital methods.
     */
    private void promptCrusader(Character character, Scanner input, GameSystem gs, Displayer ds) {
        System.out.println(character.GetFullName() + " the " + character.GetName() + "'s turn.");
        if (character.CheckAbility1Possible(gs)) {
            System.out.println("Ability 1 - Holy Divinity");
        }
        if (character.CheckAbility2Possible(gs)) {
            System.out.println("Ability 2 - Holy Light");
        }
        if (character.CheckAbility3Possible(gs)) {
            System.out.println("Ability 3 - Shield Bash");
        }
        System.out.print("Choose ability (0 to skip): ");
        int abilityChoice = input.nextInt();

        if (abilityChoice == 1) {
            // Self-targeted buff — no coordinates needed
            ActionContext crusaderAbility1 = new ActionContext(character);
            boolean success = character.Ability1(crusaderAbility1);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName()
                        + " channels Holy Divinity!");
            } else {
                System.out.println("Holy Divinity failed.");
            }

        } else if (abilityChoice == 2) {
            System.out.print("Choose x-coordinate of ally to bless: ");
            int x = input.nextInt();
            System.out.print("Choose y-coordinate of ally to bless: ");
            int y = input.nextInt();
            if (y < 0 || y >= GameSystem.GAMEHEIGHT || x < 0 || x >= GameSystem.GAMEWIDTH) {
                System.out.println("Coordinates out of bounds.");
                return;
            }
            Character target = gs.GameBoard[y][x].GetEntity().GetCharacter();
            if (target == null) {
                System.out.println("No character at that location.");
                return;
            }
            ActionContext crusaderAbility2 = new ActionContext(target, gs.GameBoard);
            boolean success = character.Ability2(crusaderAbility2);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName()
                        + " blesses " + target.GetFullName() + " the " + target.GetName() + "!");
            } else {
                System.out.println("Holy Light failed.");
            }

        } else if (abilityChoice == 3) {
            System.out.print("Enter x-coordinate of enemy to bash: ");
            int x = input.nextInt();
            System.out.print("Enter y-coordinate of enemy to bash: ");
            int y = input.nextInt();
            if (y < 0 || y >= GameSystem.GAMEHEIGHT || x < 0 || x >= GameSystem.GAMEWIDTH) {
                System.out.println("Coordinates out of bounds.");
                return;
            }
            Character target = gs.GameBoard[y][x].GetEntity().GetCharacter();
            if (target == null) {
                System.out.println("No character at that location.");
                return;
            }
            ActionContext crusaderAbility3 = new ActionContext(target, gs.GameBoard);
            boolean success = character.Ability3(crusaderAbility3);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName()
                        + " bashes " + target.GetFullName() + " the " + target.GetName() + "!");
            } else {
                System.out.println("Shield Bash failed.");
            }

        } else if (abilityChoice == 0) {
            System.out.println(character.GetFullName() + " skips their ability.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    /*
     * Helper method to streamline prompting a guardian character.
     * Ideally we would place this in each individual class, but that would very
     * much break frontend backend.
     * The system prompts an integer between 0 and 3 and then selects an attack
     * based off the user's input.
     * 0 :Skip move, don't do anything.
     * 1 :Create an obsticle at a specified coordinate.
     * 2 :Grant yourself a massive health buff.
     * 3 :Slam an enemy, massive damage 50% stun chance.
     * 
     * @param character - The character being prompted, contains ability functions
     * and it's own stats.
     * 
     * @param input - The scanner used to detect user input.
     * 
     * @param gs - The game system that stores the coordinates and what inhabits
     * them.
     * 
     * @param ds - The displayer class containing a handful of vital methods.
     */
    private void promptGuardian(Character character, Scanner input, GameSystem gs, Displayer ds) {
        System.out.println(character.GetFullName() + " the " + character.GetName() + "'s turn.");
        if (character.CheckAbility1Possible(gs)) {
            System.out.println("Ability 1 - Obstruct");
        }
        if (character.CheckAbility2Possible(gs)) {
            System.out.println("Ability 2 - Fortify");
        }
        if (character.CheckAbility3Possible(gs)) {
            System.out.println("Ability 3 - Slam");
        }
        System.out.print("Choose ability (0 to skip): ");
        int abilityChoice = input.nextInt();

        if (abilityChoice == 1) {
            System.out.print("Choose x-coordinate for the obstacle: ");
            int x = input.nextInt();
            System.out.print("Choose y-coordinate for the obstacle: ");
            int y = input.nextInt();
            ActionContext guardAbility1 = new ActionContext(x, y, gs.GameBoard);
            boolean success = character.Ability1(guardAbility1);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName()+ " places an obstacle!");
                ds.PrintGrid(gs.GameBoard);
            } else {
                System.out.println("Obstruct failed — tile is out of range, occupied, or out of bounds.");
            }

        } else if (abilityChoice == 2) {
            ActionContext guardAbility2 = new ActionContext(character);
            boolean success = character.Ability2(guardAbility2);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName()
                        + " fortifies!");
            } else {
                System.out.println("Fortify failed.");
            }

        } else if (abilityChoice == 3) {
            System.out.print("Enter x-coordinate of enemy to slam: ");
            int x = input.nextInt();
            System.out.print("Enter y-coordinate of enemy to slam: ");
            int y = input.nextInt();
            if (y < 0 || y >= GameSystem.GAMEHEIGHT || x < 0 || x >= GameSystem.GAMEWIDTH) {
                System.out.println("Coordinates out of bounds.");
                return;
            }
            Character target = gs.GameBoard[y][x].GetEntity().GetCharacter();
            if (target == null) {
                System.out.println("No character at that location.");
                return;
            }
            ActionContext guardAbility3 = new ActionContext(target, gs.GameBoard);
            boolean success = character.Ability3(guardAbility3);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName()+ " slams " + target.GetFullName() + " the " + target.GetName() + "!");
            } else {
                System.out.println("Slam failed.");
            }

        } else if (abilityChoice == 0) {
            System.out.println(character.GetFullName() + " skips their ability.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    /*
     * Helper method to streamline prompting a necromancer character.
     * Ideally we would place this in each individual class, but that would very
     * much break frontend backend.
     * The system prompts an integer between 0 and 3 and then selects an attack
     * based off the user's input.
     * 0 :Skip move, don't do anything.
     * 1 :Summon a minion anywhere on the board.
     * 2 :Buff an adjacent minion, granting it more damage and health.
     * 3 :Sacrafice a minion to damage an enemy.
     * 
     * @param character - The character being prompted, contains ability functions
     * and it's own stats.
     * 
     * @param input - The scanner used to detect user input.
     * 
     * @param gs - The game system that stores the coordinates and what inhabits
     * them.
     * 
     * @param ds - The displayer class containing a handful of vital methods.
     */
    private void promptNecromancer(Character character, Scanner input, GameSystem gs, Displayer ds) {
        System.out.println(character.GetFullName() + " the " + character.GetName() + "'s turn.");
        if (character.CheckAbility1Possible(gs)) {
            System.out.println("Ability 1 - Resurrect");
        }
        if (character.CheckAbility2Possible(gs)) {
            System.out.println("Ability 2 - Strengthen");
        }
        if (character.CheckAbility3Possible(gs)) {
            System.out.println("Ability 3 - Meat Spike");
        }
        System.out.print("Choose ability (0 to skip): ");
        int abilityChoice = input.nextInt();

        if (abilityChoice == 1) {
            System.out.print("Choose x-coordinate for the new minion: ");
            int x = input.nextInt();
            System.out.print("Choose y-coordinate for the new minion: ");
            int y = input.nextInt();
            ActionContext necroAbility1 = new ActionContext(x, y, gs.GameBoard);
            boolean success = character.Ability1(necroAbility1);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName()
                        + " raises a minion!");
                ds.PrintGrid(gs.GameBoard);
            } else {
                System.out.println("Resurrect failed.");
            }

        } else if (abilityChoice == 2) {
            System.out.print("Choose x-coordinate of minion to buff: ");
            int x = input.nextInt();
            System.out.print("Choose y-coordinate of minion to buff: ");
            int y = input.nextInt();
            if (y < 0 || y >= GameSystem.GAMEHEIGHT || x < 0 || x >= GameSystem.GAMEWIDTH) {
                System.out.println("Coordinates out of bounds.");
                return;
            }
            Entity targetEntity = gs.GameBoard[y][x].GetEntity();
            ActionContext necroAbility2 = new ActionContext(targetEntity);
            boolean success = character.Ability2(necroAbility2);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName() + " strengthens a minion!");
            } else {
                System.out.println("Strengthen failed (no minion found).");
            }

        } else if (abilityChoice == 3) {
            System.out.print("Choose x-coordinate of the enemy to attack: ");
            int enemyX = input.nextInt();
            System.out.print("Choose y-coordinate of the enemy to attack: ");
            int enemyY = input.nextInt();
            if (enemyY < 0 || enemyY >= GameSystem.GAMEHEIGHT || enemyX < 0 || enemyX >= GameSystem.GAMEWIDTH) {
                System.out.println("Coordinates out of bounds.");
                return;
            }
            Character enemy = gs.GameBoard[enemyY][enemyX].GetEntity().GetCharacter();
            if (enemy == null) {
                System.out.println("Error; no enemy found at that location.");
                return;
            }
            // Pass the enemy target; Ability3 finds and sacrifices the nearest minion itself
            ActionContext necroAbility3 = new ActionContext(enemy, gs.GameBoard);
            boolean success = character.Ability3(necroAbility3);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName()
                        + " detonates a Meat Spike on " + enemy.GetFullName()
                        + " the " + enemy.GetName() + "!");
            } else {
                System.out.println("Meat Spike failed.");
            }

        } else if (abilityChoice == 0) {
            System.out.println(character.GetFullName() + " skips their turn.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    /*
     * Helper method to streamline prompting a paladin character.
     * Ideally we would place this in each individual class, but that would very
     * much break frontend backend.
     * The system prompts an integer between 0 and 3 and then selects an attack
     * based off the user's input.
     * 0 :Skip move, don't do anything.
     * 1 :Attack that buffs your intelligence.
     * 2 :Heal self.
     * 3 :Close range strong melee attack.
     * 
     * @param character - The character being prompted, contains ability functions and it's own stats.
     * 
     * @param input - The scanner used to detect user input.
     * 
     * @param gs - The game system that stores the coordinates and what inhabits them.
     * 
     * @param ds - The displayer class containing a handful of vital methods.
     */
    private void promptPaladin(Character character, Scanner input, GameSystem gs, Displayer ds) {
        System.out.println(character.GetFullName() + " the " + character.GetName() + "'s turn.");
        if (character.CheckAbility1Possible(gs)) {
            System.out.println("Ability 1 - Verdict");
        }
        if (character.CheckAbility2Possible(gs)) {
            System.out.println("Ability 2 - Heal");
        }
        if (character.CheckAbility3Possible(gs)) {
            System.out.println("Ability 3 - Strike");
        }
        System.out.print("Choose ability (0 to skip): ");
        int abilityChoice = input.nextInt();

        if (abilityChoice == 1) {
            System.out.print("Enter x-coordinate of enemy to attack: ");
            int x = input.nextInt();
            System.out.print("Enter y-coordinate of enemy to attack: ");
            int y = input.nextInt();
            if (y < 0 || y >= GameSystem.GAMEHEIGHT || x < 0 || x >= GameSystem.GAMEWIDTH) {
                System.out.println("Coordinates out of bounds.");
                return;
            }
            Character target = gs.GameBoard[y][x].GetEntity().GetCharacter();
            if (target == null) {
                System.out.println("No character at that location.");
                return;
            }
            ActionContext paladinAbility1 = new ActionContext(target, gs.GameBoard);
            boolean success = character.Ability1(paladinAbility1);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName() + " delivers Verdict on " + target.GetFullName() + "!");
                ds.PrintGrid(gs.GameBoard);
            } else {
                System.out.println("Verdict failed.");
            }

        } else if (abilityChoice == 2) {
            // Self-targeted — no coordinates needed
            ActionContext paladinAbility2 = new ActionContext(character);
            boolean success = character.Ability2(paladinAbility2);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName() + " heals themselves!");
            } else {
                System.out.println("Heal failed — already at full health or insufficient magic.");
            }

        } else if (abilityChoice == 3) {
            System.out.print("Enter x-coordinate of enemy to strike: ");
            int x = input.nextInt();
            System.out.print("Enter y-coordinate of enemy to strike: ");
            int y = input.nextInt();
            if (y < 0 || y >= GameSystem.GAMEHEIGHT || x < 0 || x >= GameSystem.GAMEWIDTH) {
                System.out.println("Coordinates out of bounds.");
                return;
            }
            Character target = gs.GameBoard[y][x].GetEntity().GetCharacter();
            if (target == null) {
                System.out.println("No character at that location.");
                return;
            }
            ActionContext paladinAbility3 = new ActionContext(target, gs.GameBoard);
            boolean success = character.Ability3(paladinAbility3);
            if (success) {
                System.out.println(character.GetFullName() + " the " + character.GetName()
                        + " strikes " + target.GetFullName() + " the " + target.GetName() + "!");
                ds.PrintGrid(gs.GameBoard);
            } else {
                System.out.println("Strike failed.");
            }

        } else if (abilityChoice == 0) {
            System.out.println(character.GetFullName() + " skips their ability.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    /*
     * Prompts a character for their respective abilities
     *
     * @param character - The character whose turn it is.
     * @param input     - The active Scanner.
     * @param gs        - The game system.
     * @param ds        - The displayer.
     */
    private void promptAbilities(Character character, Scanner input, GameSystem gs, Displayer ds) {
        if (character instanceof Barbarian) {
            promptBarbarian(character, input, gs, ds);
        } else if (character instanceof Crusader) {
            promptCrusader(character, input, gs, ds);
        } else if (character instanceof Guardian) {
            promptGuardian(character, input, gs, ds);
        } else if (character instanceof Healer) {
            promptHealer(character, input, gs, ds);
        } else if (character instanceof Necromancer) {
            promptNecromancer(character, input, gs, ds);
        } else if (character instanceof Paladin) {
            promptPaladin(character, input, gs, ds);
        }
    }

    /*
     * Executes a character's full turn
     * Prompts move
     * Then asks for ability choice ( can skip )
     *
     * @param character - The character taking their turn.
     * @param input     - The active Scanner.
     * @param gs        - The game system.
     * @param ds        - The displayer.
     */
    private boolean commenceTurn(Character character, Scanner input, GameSystem gs, Displayer ds) {
        if (!character.GetIsAlive() || character.GetIsStunned()) {
            if (character.GetIsStunned()) {
                System.out.println(character.GetFullName() + " the " + character.GetName()
                        + " is stunned and cannot act!");
                character.SetIsStunned(false); // stun lasts one turn
            }
            gs.RemoveDeadCharacters();
            return true;
        }

        promptMove(character, input, gs, ds);

        boolean anyPossible = character.CheckAbility1Possible(gs)
                || character.CheckAbility2Possible(gs)
                || character.CheckAbility3Possible(gs);

        if (anyPossible) {
            promptAbilities(character, input, gs, ds);
        } else {
            System.out.println(character.GetFullName() + " the " + character.GetName()
                    + " cannot cast any abilities this round.");
        }
        return true;
    }

    /*
     * Runs one team's full round in descending orders of speed
     *
     * @param team  - The array of characters for this team.
     * @param input - The active Scanner.
     * @param gs    - The game system.
     * @param ds    - The displayer.
     * @param c     - A Character instance used to access speed-ordering utilities.
     */
    private boolean doTeamTurn(Character[] team, Scanner input, GameSystem gs, Displayer ds, Character c) {
        int maxIdx = c.GetMaxSpeedIndex(team);
        int medIdx = c.GetMedianSpeedIndex(maxIdx, team);
        int lowIdx = 0;
        for (int i = 0; i < 3; i++) {
            if (i != maxIdx && i != medIdx) {
                lowIdx = i;
            }
        }
        commenceTurn(team[maxIdx], input, gs, ds);
        if (gs.CheckWin()) {
            return false;
        }
        commenceTurn(team[medIdx], input, gs, ds);
        if (gs.CheckWin()) {
            return false;
        }
        commenceTurn(team[lowIdx], input, gs, ds);
        return true;
    }

    public static void main(String[] args) {
        GameSystem gs  = new GameSystem();
        Displayer  ds  = new Displayer();
        Scanner    input  = new Scanner(System.in);
        Character  c   = new Character();
        RPGMain    rpg = new RPGMain();
        boolean gameRunning = true;

        while (gameRunning) {
            System.out.println("Welcome to the RPG Game!");
            System.out.println("What would you like to do?\n1. Start Game\n2. Rules\n3. Exit");
            int playerChoice = input.nextInt();

            if (playerChoice == 1) {
                // ---- TEAM SETUP ----
                for (int i = 1; i <= 2; i++) {
                    System.out.println("Initializing Player " + i + "'s team...");
                    for (int j = 0; j < 3; j++) {
                        boolean hasSelected = false;
                        int selectedClass   = -1;
                        Character classlessCharacter = new Character().GenerateCharacter();
                        classlessCharacter.SetTeam(i);

                        while (!hasSelected) {
                            System.out.println("Character " + (j + 1) + ":");
                            ds.PrintInitialStats(new Character[] { classlessCharacter });
                            System.out.println("Choose a class:\n1. Necromancer\n2. Healer\n3. Crusader\n4. Barbarian\n5. Paladin\n6. Guardian");
                            int choice = input.nextInt();

                            if (choice == 1) {
                                System.out.println(
                                        "Necromancer \nA disturbed warlock who conjures and manipulates the dead. \nAbilities: \nAbility 1: Resurrect - Summons a weak minion to fight. ");
                                System.out.println(
                                        "Ability 2: Strengthen - Buffs an adjacent minion \nAbility 3: Meat Shield - Sacrafice an adjacent minion to tank a hit.");
                                System.out.println(
                                        "Stat buffs: \n-1 speed\n+3 intelligence\n+1 attack\n+2 spirit\n+1 health\n+2 spellpower");
                                System.out.println("Confirm? (y/N)");          
                                if (input.next().equalsIgnoreCase("y")) { 
                                    selectedClass = 1; 
                                    hasSelected = true;
                                 }
                                else { System.out.println("Returning to selection."); }

                            } else if (choice == 2) {
                                System.out.println(
                                        "Healer \n A benevolent soul who specialises in the mending of allies. \nAbilities: \nAbility 1: Prayer - Heals allies, and damages enemies. ");
                                System.out.println(
                                        "Ability 2: Praise - Grants an ally +4 intl and +2 spirit. \nAbility 3: Strike - Weak attack that may stun.");
                                System.out.println(
                                        "Stat buffs: \n+0 speed\n+3 intelligence\n-1 attack\n+3 spirit\n+2 health\n+1 spellpower");
                                System.out.println("Confirm? (y/N)");         
                                if (input.next().equalsIgnoreCase("y")) { 
                                    selectedClass = 2; 
                                    hasSelected = true; 
                                }
                                else { System.out.println("Returning to selection."); }

                            } else if (choice == 3) {
                                System.out.println(
                                        "Crusader \nA highly poised warrior who both safeguards allies and threatens moderate damage.\nAbilities: \nAbility 1: Divine Shield - Creates a protective barrier around an adjacent ally. ");
                                System.out.println(
                                        "Ability 1: Holy Light - Heals an adjacent ally. \nAbility 2: Condemn - Deals damage to an enemy.");
                                System.out.println(
                                        "\nAbility 3: Condemn - Deals damage to an enemy.");
                                System.out.println(
                                        "Stat buffs: \n+0 speed\n+2 intelligence\n+1 attack\n+1 spirit\n+3 health\n+1 spellpower");
                                System.out.println("Confirm? (y/N)");         
                                if (input.next().equalsIgnoreCase("y")) { 
                                    selectedClass = 3; 
                                    hasSelected = true;
                                }
                                else { System.out.println("Returning to selection."); }

                            } else if (choice == 4) {
                                System.out.println(
                                        "Barbarian \nA glass cannon who outputs heavy damage. What doesn't kill him makes him stronger.\nAbilities: \nAbility 1: Flip - Throws an enemy target opposite to the barbarian. ");
                                System.out.println(
                                        "Ability 2: Rage Strike - Melee attack dealing massive damage. \nAbility 3: Lupus - The barbarian damages himself and gains a permanent damage increase.");
                                System.out.println(
                                        "Stat buffs: \n+2 speed\n-1 intelligence\n+8 attack\n+1 spirit\n-2 health\n+0 spellpower");
                                System.out.println("Confirm? (y/N)");         
                                if (input.next().equalsIgnoreCase("y")) { 
                                    selectedClass = 4;
                                    hasSelected = true;
                                 }
                                else { System.out.println("Returning to selection."); }

                            } else if (choice == 5) {
                                System.out.println(
                                        "Paladin \nA bastion of light who defends and heals allies.\nAbilities: \nAbility 1: Aura - Adjacent allies take half damage for 1 turn. ");
                                System.out.println(
                                        "Ability 2: Heal - Heals self for 20 health points. \nAbility 3: Divine Strike - Weak attack that restores magic.");
                                System.out.println(
                                        "Stat buffs: \n+0 speed\n+2 intelligence\n+0 attack\n+1 spirit\n+5 health\n+1 spellpower");
                                System.out.println("Confirm? (y/N)");         
                                if (input.next().equalsIgnoreCase("y")) { 
                                    selectedClass = 5;
                                     hasSelected = true;
                                    }
                                else { System.out.println("Returning to selection."); }

                            } else if (choice == 6) {
                                System.out.println(
                                        "Guardian \nProtector of lands who creates blockades. \nAbilities: \nAbility 1: Obstruct - Creates an obstacle");
                                System.out.println(
                                        "Ability 2: Fortify - Grants +1 hlt and +1 intl to the guardian. \nAbility 3: Slam - Weak attack with a chance to stun.");
                                System.out.println(
                                        "Stat buffs: \n+0 speed\n+3 intelligence\n-1 attack\n+3 spirit\n+2 health\n+1 spellpower");
                                System.out.println("Confirm? (y/N)");
                                if (input.next().equalsIgnoreCase("y")) { 
                                     selectedClass = 6;
                                     hasSelected = true; 
                                    }
                                else { System.out.println("Returning to selection."); }

                            } else {
                                System.out.println("Invalid choice, please try again.");
                            }
                        }

                        Character newCharacter = null;
                        if (selectedClass == 1) {
                            newCharacter = new Necromancer(classlessCharacter, i);
                        } else if (selectedClass == 2) {
                            newCharacter = new Healer(classlessCharacter, i);
                        } else if (selectedClass == 3) {
                            newCharacter = new Crusader(classlessCharacter, i);
                        } else if (selectedClass == 4) {
                            newCharacter = new Barbarian(classlessCharacter, i);
                        } else if (selectedClass == 5) {
                            newCharacter = new Paladin(classlessCharacter, i);
                        } else {
                            newCharacter = new Guardian(classlessCharacter, i);
                        }

                        if (i == 1) {
                            gs.player1.PlayerTeam[j] = newCharacter;
                        } else {
                            gs.player2.PlayerTeam[j] = newCharacter;
                        }
                    }

                    System.out.println("PLAYER " + i + " TEAM:");
                    if (i == 1) {
                        ds.PrintInitialStats(gs.player1.PlayerTeam);
                        System.out.println("Hand over to Player 2. Press [ENTER] when ready.");
                    } else {
                        ds.PrintInitialStats(gs.player2.PlayerTeam);
                        System.out.println("Teams initialized. Press [ENTER] to continue.");
                    }
                    input.nextLine();
                    input.nextLine();
                }

                // ---- PLACE CHARACTERS ----
                // SetPosition takes [x, y] where x=col, y=row
                gs.player1.PlayerTeam[0].SetPosition(new int[] { 0, 0 }, gs.GameBoard);
                gs.player1.PlayerTeam[1].SetPosition(new int[] { 1, 0 }, gs.GameBoard);
                gs.player1.PlayerTeam[2].SetPosition(new int[] { 0, 1 }, gs.GameBoard);
                gs.player2.PlayerTeam[0].SetPosition(new int[] { 4, 3 }, gs.GameBoard);
                gs.player2.PlayerTeam[1].SetPosition(new int[] { 4, 4 }, gs.GameBoard);
                gs.player2.PlayerTeam[2].SetPosition(new int[] { 3, 4 }, gs.GameBoard);

                gs.PopulateGameBoard(7, 7);
                ds.PrintGrid(gs.GameBoard);

                // ---- GAME LOOP ----
                while (!gs.CheckWin()) {
                    int p1Speed = 0;
                    int p2Speed = 0;
                    for (int i = 0; i < 3; i++) {
                        p1Speed += gs.player1.PlayerTeam[i].GetRawStats()[Character.SPDPOS];
                        p2Speed += gs.player2.PlayerTeam[i].GetRawStats()[Character.SPDPOS];
                    }

                    if (p1Speed >= p2Speed) {
                        System.out.println("Player 1 speed: " + p1Speed + " vs Player 2 speed: " + p2Speed);
                        System.out.println("Player 1 goes first! Press [ENTER] to continue.");
                        input.nextLine();
                        rpg.doTeamTurn(gs.player1.PlayerTeam, input, gs, ds, c);

                        if (!gs.CheckWin()) {
                            System.out.println("Player 2's turn. Press [ENTER] to continue.");
                            input.nextLine();
                            rpg.doTeamTurn(gs.player2.PlayerTeam, input, gs, ds, c);
                        }
                    } else {
                        System.out.println("Player 2 speed: " + p2Speed + " vs Player 1 speed: " + p1Speed);
                        System.out.println("Player 2 goes first! Press [ENTER] to continue.");
                        input.nextLine();
                        rpg.doTeamTurn(gs.player2.PlayerTeam, input, gs, ds, c);

                        if (!gs.CheckWin()) {
                            System.out.println("Player 1's turn. Press [ENTER] to continue.");
                            input.nextLine();
                            rpg.doTeamTurn(gs.player1.PlayerTeam, input, gs, ds, c);
                        }
                          gs.RemoveDeadCharacters();
                    }
                    // Regenerate at the end of each full round (only if game is still running, or else redundant)
                    if (!gs.CheckWin()) {
                        gs.RegenerateCharacters(gs.player1.PlayerTeam);
                        gs.RegenerateCharacters(gs.player2.PlayerTeam);
                    }
                }

                int winner = gs.GetWinningTeam();
                System.out.println("=== Player " + winner + " wins! ===");
                gameRunning = false;

            } else if (playerChoice == 2) {
                ds.PrintRules();
            } else if (playerChoice == 3) {
                gameRunning = false;
            } else {
                System.out.println("Invalid choice.");
            }
        }

        input.close();
    }
}