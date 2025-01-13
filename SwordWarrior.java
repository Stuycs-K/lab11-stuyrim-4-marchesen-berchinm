public class SwordWarrior extends Adventurer{
  int energy, maxEnergy;

  public SwordWarrior(){
    this("Carl");
  }

  public SwordWarrior(String name){
    super(name, 30); // 30 hp
    energy = 3; // starting energy 3
    energyMax = 7; // max energy 7
  }

  public String getSpecialName(){
    return "energy";
  }

  public int getSpecial(){
    return energy;
  }

  public void setSpecial(int n){
    energy = n;
  }

  public int getSpecialMax(){
    return maxEnergy;
  }

  // deals 4-5 or 8-9 damage with 20% critical hit rate
  public String attack(Adventurer other){
    int rng = (int)(5 * Math.random());
    boolean crit = false;
    if (rng == 4) crit = true;

    int damage = (int)(4 + Math.random());
    if (crit = true){damage += 5;}

    other.applyDamage(damage);

    return (getName() + " sliced " + other.getName() + " for " + damage + " damage!");
  }

  // deals 0 or 20 damage with equal odds
  public String specialAttack(Adventurer other){
    if (energy < 5){return (getName() + " doesn't have enough energy for the special attack!");}

    int rng = (int)(2 * Math.random());
    boolean crit = false;
    if (rng == 1) crit = true;

    int damage = 0;
    if (crit = true){damage += 20;}

    other.applyDamage(damage);

    if (crit){return (getName() + " took a HUGE swing at " + other.getName() + " and landed it for 20 damage!");}
    return (getName() + " took a HUGE swing at " + other.getName() + " and missed completely!")

  }

  public String support(){

  }

  public String support(Adventurer other){

  }




}
