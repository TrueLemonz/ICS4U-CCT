public class Guardian extends Character {
    public Guardian() {
        this.spdMod -= 2;
        this.intlMod = 4;
        this.atkMod = 0;
        this.sprMod = 1;
        this.hltMod = 5;
        this.sppMod = 0;
        this.spd += this.spdMod ;
        this.intl += this.intlMod;
        this.atk += this.atkMod;
        this.spr += this.sprMod;
        this.hlt += this.hltMod;
        this.spp += this.sppMod;
    }


    public boolean Special(ActionContext context) {
        if ( !this.GetIsAlive() || this.GetIsStunned() || this.GetCurrMagic() - 2 < 0) {
            return false;
        }
        if ( context.GetGrid()[context.getPosX()][context.getPosY()].getEntity().GetObject() == 0) {
            context.GetGrid()[context.getPosX()][context.getPosY()] = new Block(new Obstacle());
            this.SetCurrMagic( this.GetCurrMagic() - 2);
            return true;
        }
        else {
            return false;
        }
    }
    // had to change from void to boolean because I simplified the methods earlier
    public boolean Ability1(ActionContext context) {
        if ( !this.GetIsAlive() || this.GetIsStunned() || this.GetCurrMagic()- 1 < 0 ) {
            return false;
        }
        this.SetHlt ( this.GetRawStats()[HLTPOS] + 1);
        this.SetIntl ( this.GetRawStats()[INTLPOS] + 1);
        this.SetCurrMagic ( this.GetCurrMagic() - 1);
        return true;
    }

    public boolean Ability2(ActionContext context) {
        if ( !this.GetIsAlive() || this.GetIsStunned() || this.GetCurrMagic() - 1 < 0 ) {
            return false;
        }
        if (1.1 * this.GetCurrHealth() <= this.health ) {
            this.SetCurrHealth(this.GetCurrHealth() * 1.1); // Sorry if this broke it, tried to make everything encapsulated
            this.SetCurrMagic( this.GetCurrMagic() - 1);
            return true;
        }
        else {
            return false;
        }
    }
}