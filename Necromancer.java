public class Necromancer extends Character {
    public Necromancer(Character character) {
        super();
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
        if (context.getGrid()[context.getPosX()][context.getPosY()].getEntity().GetObject() == 0) {
            context.getGrid()[context.getPosX()][context.getPosY()] = new Block(new Minion());
            return true;
        }
        else {
            return false;
        }
    }
    public boolean Ability1(ActionContext context) {
        if (context.getTarget().GetObject() == 1) {
            if (!context.getTarget().IsMinion()) {
                context.getTarget().AddTurn();
                return true;
            }
            Minion target = (Minion) context.getTarget();
            target.AddTurn();
            return true;
        }
        else {
            return false;
        }
    }
    public boolean Ability2(ActionContext context) {
        if (context.getGrid()[context.getPosX()][context.getPosY()].getEntity().GetObject() == 1) {
            return true;
        }
        else {
            return false;
        }
    }
}
