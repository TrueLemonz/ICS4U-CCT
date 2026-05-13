public class Minion extends Character {
    private int turns = 1;
    public Minion(int team) {
        super();
        this.team = team;
        this.spdMod = 1;
        this.intlMod = 1;
        this.atkMod = 1;
        this.sprMod = 1;
        this.hltMod = 1;
        this.sppMod = 1;
        this.spd += this.spdMod ;
        this.intl += this.intlMod;
        this.atk += this.atkMod;
        this.spr += this.sprMod;
        this.hlt += this.hltMod;
        this.spp += this.sppMod;
    }
    public String getName() {
        return "Minion";
    }
    public boolean Special(ActionContext context) {
        return false;
    }
    public boolean Ability1(ActionContext context) {
        return false;
    }
    public boolean Ability2(ActionContext context) {
        return false;
    }
}
