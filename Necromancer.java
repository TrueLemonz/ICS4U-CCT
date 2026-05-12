public class Necromancer extends Character {
    public Necromancer(Character character, int team) {
        super();
        this.team = team;
        this.spdMod = -1;
        this.intlMod = 3;
        this.atkMod = 1;
        this.sprMod = 2;
        this.hltMod = 1;
        this.sppMod = 2;
        this.spd = character.spd + this.spdMod ;
        this.intl = character.intl + this.intlMod;
        this.atk = character.atk + this.atkMod;
        this.spr = character.spr + this.sprMod;
        this.hlt = character.hlt + this.hltMod;
        this.spp = character.spp + this.sppMod;
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
        if (context.GetTarget().GetObject() == 1) {
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
        if (context.GetGrid()[context.getPosX()][context.getPosY()].getEntity().GetObject() == 1) {
            return true;
        }
        else {
            return false;
        }
    }
}
