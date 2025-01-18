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
      int damage = chooseNum(4, 4);
	  other.applyDamage(damage);
      event += "dealt " + damage + " damage!";
    }
    else {
      event += "missed completely, dealing 0 damage!";
	}
	restoreSpecial(2);
	return event + " They then nibbled on a baby carrot.";
  }
  
  

  public String specialAttack(Adventurer other) {
    if (getSpecial() < 5) return "Not enough focus!";
	
	setSpecial(getSpecial() - 5);
	String event = getName() + " focused, shot at " + other.getName() + "'s head, and ";
	boolean hitTarget = yesNo(5);
	
	if (hitTarget) {
	  int damage = chooseNum(7, 12);
	  other.applyDamage(damage);
	  return event + "dealt " + damage + " damage!";
	}
	//else
	return event + "missed completely, dealing 0 damage!";
  }

  public String support() {
	if (getHP() == getmaxHP() && getSpecial() == getSpecialMax()) {
      return getName() + " ate a carrot, but they were already at full health and " + getSpecialName() + "!";
    }
	
	int addHP = chooseNum(3, 1);
	if (addHP + getHP() > getmaxHP()) addHP = getmaxHP() - getHP(); 
    applyHeal(addHP);
	
	restoreSpecial(chooseNum(2, 3));
	
    return getName() + " ate a carrot! They are now at " +
		getHP() + "/" + getmaxHP() + " health and " + getSpecial() + "/" + getSpecialMax() + " " + getSpecialName() + "!";
  }

  public String support(Adventurer other) {
	if (other.getHP() == other.getmaxHP() && other.getSpecial() == other.getSpecialMax()) {
      return getName() + " gave a carrot to " + other.getName() + ", but they were already at full health and " + other.getSpecialName() + "!";
    }
	
	int addHP = chooseNum(2, 1);
	if (addHP + other.getHP() > other.getmaxHP()) addHP = other.getmaxHP() - other.getHP(); 
    other.applyHeal(addHP);
	
	other.restoreSpecial(chooseNum(2, 3));
	
    return getName() + " gave a carrot to " + other.getName() + "! " + 
		other.getName() + " is now at " + other.getHP() + "/" + other.getmaxHP() + " health and " + other.getSpecial() + "/" + other.getSpecialMax() + " " + other.getSpecialName() + "!";
  }
  
  

  //Helper functions
  
  private static int chooseNum(int range, int min) {
	return (int) (Math.random() * range + min);
  }
  
  private static boolean yesNo(int chance) {
    int binBool = chooseNum(chance, 0);
    return binBool != 0;
  }

}
