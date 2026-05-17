public class CombatManager {

    public CombatManager() {}

    // Pure backend logic: No printing, no scanners. Just data sorting.
    public Character[] calculateTurnOrder(GameSystem gs) {
        Character[] turnOrder = new Character[6];
        int count = 0;

        for (int i = 0; i < 3; i++) {
            if (gs.Player1.PlayerTeam[i] != null) {
                turnOrder[count++] = gs.Player1.PlayerTeam[i];
            }
            if (gs.Player2.PlayerTeam[i] != null) {
                turnOrder[count++] = gs.Player2.PlayerTeam[i];
            }
        }

        // Bubble sort by speed (dont know if we are allowed to use bubble sort)
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                if (turnOrder[j].GetSpd() < turnOrder[j + 1].GetSpd()) {
                    Character temp = turnOrder[j];
                    turnOrder[j] = turnOrder[j + 1];
                    turnOrder[j + 1] = temp;
                }
            }
        }
        return turnOrder;
    }

    // Receives coordinates, determines targets, applies abilities
    public void executeAction(Character activeChar, int choice, int targetX, int targetY, GameSystem gs) {
        Block targetBlock = gs.GameBoard[targetX][targetY];
        ActionContext context;

        // make context
        if (targetBlock != null && targetBlock.GetEntity() != null && targetBlock.GetEntity().GetObject() == 1) {
            Character targetCharacter = (Character) targetBlock.GetEntity();
            context = new ActionContext(targetX, targetY, targetCharacter, gs.GameBoard);
        } else {
            context = new ActionContext(targetX, targetY, gs.GameBoard);
        }

        // Execute the ability
        activeChar.executeAbility(choice, context);
    }
}