public class Boss extends Adventurer{
  int power, maxPower, specialAttackMin;

  public Boss(){
    this("King");
  }

  public Boss(String name){
    super(name, 100); // 100 hp
    power = 0; // starting power 0
    maxPower = 10; // max power 10
    specialAttackMin = 10; // costs 10 to do special attack
  }

  public String getSpecialName(){
    return "Power";
  }

  public int getSpecial(){
    return power;
  }

  public void setSpecial(int n){
    power = n;
  }

  public int getSpecialMax(){
    return maxPower;
  }

  // shoots lightning. deals 50% of opponent's health, for at least 5 damage
  public String attack(Adventurer other){
    int damage = other.getHP() * 50 / 100;
    if (damage < 3){damage = 3;}

    other.applyDamage(damage);

    return (getName() + " shot lightning and hit " + other.getName() + " for " + damage + " damage!");
  }

  // shoots a massive lightning bolt. opponent goes to 1 hp automatically
  public String specialAttack(Adventurer other){
    if (power < 10){return (getName() + " doesn't have enough power for a special attack! Instead " + attack(other));}
    else{power -= 10;}

    int damage = other.getHP() - 1;

    other.applyDamage(damage);

    return (getName() + " struck " + other.getName() + " with a huge lightning bolt and dealt " + damage + " damage!");
  }

  // takes a nap. gains 10 health and energy
  public String support(){
    applyHeal(10);
    restoreSpecial(10);
    return (getName() + " took a nap, gaining 10 health and 10 power!");
  }

  public String support(Adventurer other){
    return support();
  }

}
