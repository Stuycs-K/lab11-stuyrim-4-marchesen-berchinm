public class CrossbowWarrior extends Adventurer{
  int focus, maxFocus;

  public CrossbowWarrior() {
    this("Marlene");
  }

  public CrossbowWarrior(String name) {
    super(name);
  }

  public String getSpecialName(){
    return "focus";
  }

  public int getSpecial(){
    return focus;
  }

  public void setSpecial(int n){
    if (n >= 0) {
      focus = n;
    }
    else {
      focus = maxFocus / 2;
    }
  }

  public int getSpecialMax(){
    return maxFocus;
  }

  public String attack(Adventurer other) {
    String event = getName() + " shot at " + other.getName() + " and ";
    boolean hitTarget = yesNo(10);
    if (hitTarget) {
      int damage = (int) Math.random() * 3 + 4;
      return event + "dealt " + damage + " damage!";
    }
    //else
    return event + "missed completely, dealing 0 damage!";
  }

  public String specialAttack(Adventurer other) {}

  public String support() {}

  public String support(Adventurer other) {}

  //Helper function
  private boolean yesNo(int chance) {
    int binBool = (int) Math.random() * chance;
    return binBool == 0;
  }

}
