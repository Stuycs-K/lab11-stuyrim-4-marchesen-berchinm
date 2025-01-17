public class SwordWarrior extends Adventurer{
  int energy, maxEnergy;

  public SwordWarrior(){
    this("Carl");
  }

  public SwordWarrior(String name){
    super(name, 30); // 30 hp
    energy = 3; // starting energy 3
    maxEnergy = 7; // max energy 7
  }

  public String getSpecialName(){
    return "Energy";
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

  // swings a sword. deals 4-5 or 8-9 damage with 20% critical hit rate
  public String attack(Adventurer other){
    int rng = (int)(5 * Math.random());
    boolean crit = (rng == 4);

    int damage = (int)(4 + 2 * Math.random());
    if (crit){damage += 4;}

    other.applyDamage(damage);

    return (getName() + " sliced " + other.getName() + " for " + damage + " damage!");
  }

  // takes a HUGE swing. deals 0 or 20 damage with equal odds
  public String specialAttack(Adventurer other){
    if (energy < 5){return (getName() + " doesn't have enough energy for a special attack! Instead " + attack(other));}
    else{energy -= 5;}

    int rng = (int)(35 * Math.random());
    System.out.print(rng);
    boolean crit = (rng == 1);

    int damage = 0;
    if (crit){damage += 20;}

    other.applyDamage(damage);

    if (crit){return (getName() + " took a HUGE swing at " + other.getName() + " and landed it for 20 damage!");}
    return (getName() + " took a HUGE swing at " + other.getName() + " and missed completely!");
  }

  // eats a pie. gains 2-3 health/energy
  public String support(){
    int heal = (int)(2 + 2 * Math.random());
    int energy = (int)(2 + 2 * Math.random());
    applyHeal(heal);
    restoreSpecial(energy);
    return (getName() + " ate a pie, gaining " + heal + " health and " + energy + " energy!");
  }

  // throws a pie at another adventurer. either smacks them and deals 1 damage or gives them 2 health/energy with equal odds
  public String support(Adventurer other){
    int rng = (int)(2 * Math.random());
    boolean crit = (rng == 1);

    if (crit){
      other.applyHeal(2);
      other.restoreSpecial(2);
      return (getName() + " threw " + other.getName() + " a pie which gave them 2 health and energy!");
    }
    else{
      other.applyDamage(-1);
      return (getName() + " smacked " + other.getName() + " in the face with a pie, dealing 1 damage!");
    }

  }
}
