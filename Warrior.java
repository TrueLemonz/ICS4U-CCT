public abstract class Warrior extends Character{
    public abstract boolean Special(Character target, Block[][] grid);
    public abstract boolean Special(Character target);
    public abstract void Special();
    public abstract boolean Ability1(Character target, Block[][] grid);
    public abstract boolean Ability1(Character target);
    public abstract void Ability1();
    public abstract boolean Ability2(Character target, Block[][] grid);
    public abstract boolean Ability2(Character target);
    public abstract boolean Ability2();
    public Warrior() {}
}