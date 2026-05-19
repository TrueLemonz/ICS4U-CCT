public class Player {
    public Player() {}
    Character[] PlayerTeam = new Character[3];

    public int getLivingAllies() {
        int LivingAllies = 0;
        for (int i = 0; i < this.PlayerTeam.length; i++) {
            if (this.PlayerTeam[i].getIsAlive()) {
                LivingAllies++;
            }
        }
        return LivingAllies;
    }
}


