public class Boss extends Adventurer{
  int power, maxPower;

  public Boss(){
    this("King");
  }

  public Boss(String name){
    super(name, 100); // 100 hp
    power = 0; // starting power 0
    maxPower = 100; // max power 100
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

  // shoots lightning. deals damage that is 0-25% of opponent's health, but does at least 3 damage
  public String attack(Adventurer other){
    double percentDamage = 25 * Math.random();
    int damage = (int)(other.getHP() * percentDamage / 100);

    other.applyDamage(damage);

    return (getName() + " shot lightning and hit " + other.getName() + " for " + damage + " damage!");
  }
}
