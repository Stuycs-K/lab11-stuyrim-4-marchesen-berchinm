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

  public String attack(Adventurer other){

  }

  public String specialAttack(Adventurer other){

  }

  public String support(){

  }

  public String support(Adventurer other){
    
  }




}
