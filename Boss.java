public class Boss extends Adventurer{
  int power, maxPower;

  public Boss(){
    this("King");
  }

  public Boss(String name){
    super(name, 100); // 100 hp
    power = 0; // starting power 0
    maxPower = 10; // max power 10
  }

  public String getSpecialName(){
    return "power";
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

  // shoots lightning. deals damage that is 0-40% of opponent's health for at least 3 damage
  public String attack(Adventurer other){
    double percentDamage = 40 * Math.random();
    int damage = (int)(other.getHP() * percentDamage / 100);
    if (damage < 3){damage = 3;}

    other.applyDamage(damage);

    return (getName() + " shot lightning and hit " + other.getName() + " for " + damage + " damage!");
  }

  // shoots a massive lightning bolt. takes half of opponent's health for at least 5 damage
  public String specialAttack(Adventurer other){
    if (power < 10){return (getName() + " doesn't have enough energy for a special attack!");}
    else{power -= 10;}

    int damage = (int)(other.getHP() * 50 / 100);

    other.applyDamage(damage);

    return (getName() + " struck " + other.getName() + " with a huge lightning bolt and dealt " + damage + " damage!");
  }

  // takes a nap. gains 1-10 health and energy
  public String support(){
    int heal = (int)(1 + 10 * Math.random());
    int power = (int)(1 + 10 * Math.random());
    applyHeal(heal);
    restoreSpecial(power);
    return (getName() + " took a nap and gained " + heal + " health and " + power + " energy!");
  }

  public String support(Adventurer other){
    return support();
  }

}
