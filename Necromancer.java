public class Necromancer extends Character {
    public Necromancer(Character character, int team) {
        super();
        this.SetName("Necromancer");
        this.SetFullName(character.GetFullName());
        this.SetTeam(team);
        this.spdMod = -1;
        this.intlMod = 3;
        this.atkMod = 1;
        this.sprMod = 2;
        this.hltMod = 1;
        this.sppMod = 2;
        this.spd = character.spd + this.spdMod ;
        if ( this.spd + this.spdMod < 0 ) {
            this.spd = 1;
        }
        this.intl = character.intl + this.intlMod;
        if ( this.intl + this.intlMod < 0 ) {
            this.intl = 1;
        }
        this.atk = character.atk + this.atkMod;
        if ( this.atk + this.atkMod < 0 ) {
            this.atk = 1;
        }
        this.spr = character.spr + this.sprMod;
        if ( this.spr + this.sprMod < 0 ) {
            this.spr = 1;
        }
        this.hlt = character.hlt + this.hltMod;
        if ( this.hlt + this.hltMod < 0 ) {
            this.hlt = 1;
        }
        this.spp = character.spp + this.sppMod;
        if ( this.spp + this.sppMod < 0 ) {
            this.spp = 1;
        }
        ScaleStats();
    }
    public String getName() {
        return "Necromancer";
    }

    public boolean Special(ActionContext context) {
        if (context.GetGrid()[context.getPosX()][context.getPosY()].getEntity().GetObject() == 0) {
            context.GetGrid()[context.getPosX()][context.getPosY()] = new Block(new Minion(this.team));
            return true;
        }
        else {
            return false;
        }
    }
    public boolean Ability1(ActionContext context) {
        Entity entity = new Entity();
        if (context.GetTarget().GetObject() == entity.CHARACTER) {
            if (!context.GetTarget().IsMinion()) {
                context.GetTarget().AddTurn();
                return true;
            }
            Minion target = (Minion) context.GetTarget();
            target.AddTurn();
            return true;
        }
        else {
            return false;
        }
    }
    public boolean Ability2(ActionContext context) {
        Entity entity = new Entity();
        if (context.GetGrid()[context.getPosX()][context.getPosY()].getEntity().GetObject() == entity.CHARACTER) {
            return true;
        }
        else {
            return false;
        }
    }
}
