public class CrossbowWarrior extends Adventurer{
  int focus, maxFocus;

  public CrossbowWarrior() {
    this("Marlene");
  }

  public CrossbowWarrior(String name) {
    super(name);
	focus = 2;
	maxFocus = 8;
  }

  public String getSpecialName(){
    return "focus";
  }

  public int getSpecial(){
    return focus;
  }

  public void setSpecial(int n){
	if (n > getSpecialMax()) {
	  n = getSpecialMax();
	}
    if (n >= 0) {
      focus = n;
    }
    else {
      focus = getSpecialMax() / 2;
    }
  }

  public int getSpecialMax(){
    return maxFocus;
  }

  public String attack(Adventurer other) {
    String event = getName() + " shot at " + other.getName() + " and ";
    boolean hitTarget = yesNo(10);
    if (hitTarget) {
      int damage = (int) (Math.random() * 4 + 4);
	  other.applyDamage(damage);
      return event + "dealt " + damage + " damage!";
    }
    //else
    return event + "missed completely, dealing 0 damage!";
  }
  
  

  public String specialAttack(Adventurer other) {
    if (getSpecial() < 5) return "Not enough focus!";
	
	setSpecial(getSpecial() - 5);
	String event = getName() + " focused, shot at " + other.getName() + "'s head, and ";
	boolean hitTarget = yesNo(5);
	
	if (hitTarget) {
	  int damage = (int) (Math.random() * 7 + 12);
	  other.applyDamage(damage);
	  return event + "dealt " + damage + " damage!";
	}
	//else
	return event + "missed completely, dealing 0 damage!";
  }

  public String support() {
	return "";
  }

  public String support(Adventurer other) {
	return "";
  }
  
  

  //Helper functions
  
  private static boolean yesNo(int chance) {
    int binBool = (int) (Math.random() * chance);
    return binBool != 0;
  }

}
