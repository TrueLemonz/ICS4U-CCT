public class Minion extends Character {
    private int turns = 1;
    public Minion(int team) {
        super();
        this.SetName("Minion");
        this.SetFullName("Mini");
        this.team = team;
        this.spdMod = 1;
        this.intlMod = 1;
        this.atkMod = 1;
        this.sprMod = 1;
        this.hltMod = 1;
        this.sppMod = 1;
        this.spd += this.spdMod ;
        if ( this.spd + this.spdMod < 0 ) {
            this.spd = 0;
        }
        this.intl += this.intlMod;
        if ( this.intl + this.intlMod < 0 ) {
            this.intl = 0;
        }
        this.atk += this.atkMod;
        if ( this.atk + this.atkMod < 0 ) {
            this.atk = 0;
        }
        this.spr += this.sprMod;
        if ( this.spr + this.sprMod < 0 ) {
            this.spr = 0;
        }
        this.hlt += this.hltMod;
        if ( this.hlt + this.hltMod < 0 ) {
            this.hlt = 0;
        }
        this.spp += this.sppMod;
        if ( this.spp + this.sppMod < 0 ) {
            this.spp = 0;
        }
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
