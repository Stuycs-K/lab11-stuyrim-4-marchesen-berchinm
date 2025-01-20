import java.util.*;

public class Game{

  //-----------------------------------------------------FORMATTING-VARIABLES-----------------------------------------------------//

  private static final int WIDTH = 80;
  private static final int HEIGHT = 30;
  private static final int BORDER_COLOR = Text.BLACK;
  private static final int BORDER_BACKGROUND = Text.WHITE + Text.BACKGROUND;

  //-----------------------------------------------------MAIN-----------------------------------------------------//

  public static void main(String[] args) {

    //<<-----VARIABLES----->>//

    boolean partyTurn = true;
    int whichPlayer = 0; //player from party whose turn it is
    int whichOpponent = 0; //opponent whose turn it is
    String input = "";//blank to get into the main loop.
    Scanner in = new Scanner(System.in);

	  //<<-----TEAM-SETUP----->>//

    ArrayList<Adventurer> party = new ArrayList<Adventurer>();
    setupParty(party);
    ArrayList<Adventurer>enemies = new ArrayList<Adventurer>();
    setupEnemies(enemies);

	  //<<-----SCREEN-SETUP----->>//

	Text.hideCursor();
    Text.clear();
    drawScreen(party, enemies); //initial state.

	  //<<-----MAIN-LOOP-SETUP----->>//

    String preprompt = "Enter command for " + party.get(whichPlayer) + ": attack/special/support/support other)/quit";
    displayPrompt(preprompt);

	  //<<-----MAIN-LOOP----->>//

    while(party.size() > 0 && enemies.size() > 0){ // if a team is defeated, leave loop

      //Read user input
      input = userInput(in);
	  if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) quit();
      String s; // this holds the event of a turn

	    //<<-----PARTY-TURN----->>//

	    if (partyTurn) {

        Adventurer p = party.get(whichPlayer); //whose turn it is
		s = playerAction(input, p, party, enemies);
		TextBox(10,10,s); //record the event

        whichPlayer++;
        partyTurn = updatePartyTurn(whichPlayer, party);
		if (!partyTurn) whichPlayer = 0;
        // end of one party member's turn

      }

	    //<<-----ENEMY-TURN----->>//

      else {

        //enemy attacks a randomly chosen person with a randomly chosen attack.
        Adventurer enemy = enemies.get(whichOpponent);
        s = chooseAction(enemy, enemies, party);
        TextBox(10,10,s);

        //Decide where to draw the following prompt:
        String prompt = "Press enter to see the enemy's turn or q to quit.";
        displayPrompt(prompt);

        whichOpponent++;
		partyTurn = updateEnemyTurn(whichOpponent, party, enemies);
		if (partyTurn) whichOpponent = 0;
        // end of one enemy's turn

      }

	    //<<-----AFTER-A-TURN----->>//

      drawScreen(party, enemies); //display the updated screen after input has been processed.

    } // end of main game loop


    //<<-----AFTER-GAME----->>//

    quit();

  }

  //-----------------------------------------------------DISPLAY-----------------------------------------------------//


  //<<-----'MAIN'----->>//

  //Display the party and enemies
  //Do not write over the blank areas where text will appear.
  //Place the cursor at the place where the user will by typing their input at the end of this method.
  public static void drawScreen(ArrayList<Adventurer> party, ArrayList<Adventurer> enemies){
    drawBackground();
    drawParty(party, 22);
    drawParty(enemies, 3);
  }

  //<<-----BACKGROUND----->>//

  //Display the borders of your screen that will not change.
  //Do not write over the blank areas where text will appear or parties will appear.
  public static void drawBackground(){
    Text.go(1,1);
    System.out.print("╭");
    for (int i = 2; i <= 79; i++){System.out.print("─");}
    System.out.print("╮");
    for (int i = 2; i <= 29; i++){
      if (i == 6 || i == 20 || i == 25){ // horizontals
        Text.go(i,1);
        System.out.print("├");
        for (int j = 2; j <= 79; j++){System.out.print("─");}
        System.out.print("┤");
      }
      else{
        Text.go(i,1);
        System.out.print("│");
        Text.go(i,80);
        System.out.print("│");
      }
    }
    Text.go(30,1);
    System.out.print("╰");
    for (int i = 0; i < 78; i++){System.out.print("─");}
    System.out.print("╯");
    Text.go(29,2);
    System.out.print("> ");
  }

  //<<-----TEXT----->>//

  // Display a List of 2-4 adventurers on the rows row through row+3 (4 rows max)

  public static void drawParty(ArrayList<Adventurer> party, int startRow){
    for (int i = 0; i < party.size(); i++){
      Adventurer p = party.get(i);
      drawText(p.getName(),startRow, 3+20*i);
      drawText(colorByPercent(p.getHP(), p.getmaxHP()),startRow+1, 3+20*i);
	  Text.reset(); //in case of colorized HP
      drawText(p.getSpecialName() + ": " + p.getSpecial() + "/" + p.getSpecialMax(),startRow+2, 3+20*i);
    }
  }
  
  //Display a line of text starting at startRow, startCol
  public static void drawText(String s, int startRow, int startCol){
    Text.go(startRow, startCol);
    System.out.print(s);
    Text.go(29,4);
    return;
  }
  
  public static void TextBox(int row, int col, String str) { //used to display actions/events
	eraseBoard(7, 20, 2, 80);
	//format non-death-message lines
	int lineLength = 80 - col*2; //this is how long one line of text from str can be
	while (str.length() > 0) { 
	  //thisLine = whole string; will change unless this is the last line
	  String thisLine = str;
	  if (str.length() > lineLength) { //for all except the last line
	    thisLine = str.substring(0, lineLength);
	    thisLine = thisLine.substring(0, thisLine.lastIndexOf(" ")); //make sure only full words go into a line
		str = str.substring(thisLine.length() + 1); //cut out this line plus the whitespace after it
	  }
	  else { //last line
		str = "";
	  }
	  int paddingToCenter = (80 - col*2 - thisLine.length()) / 2; //used to center the last line
	  Text.go(row, col+paddingToCenter);
	  System.out.print(thisLine);
	  row++;
	}
	//go back to input
	Text.go(29,4);
	return;
  }
  
  public static void displayPrompt(String prompt) {
	eraseBoard(27, 28, 2, 80);
	Text.go(27, 2);
	System.out.print(prompt);
	Text.go(29,4);
  }
  
  public static void clearInput() {
	eraseBoard(29, 30, 2, 80);
  }
  
  public static void eraseBoard(int startRow, int endRow, int startCol, int endCol) { //erases all text in width * height square. Ends not inclusive
    String row = "";
	for (int i = 0; i < endCol - startCol; i++) {
	  row += " ";
	}
	for (int i = startRow; i < endRow; i++) {
	  Text.go(i, startCol);
	  System.out.print(row);
	}
	Text.go(29,4);
  }

  //Use this to create a colorized number string based on the % compared to the max value.
  public static String colorByPercent(int hp, int maxHP){
    String output = "HP: " + String.format("%2s", hp+"")+"/"+String.format("%2s", maxHP+"");
  	int percent = hp * 100 / maxHP;
    if (percent < 25){output = Text.colorize(output, Text.RED);}
    else if (percent < 75){output = Text.colorize(output, Text.YELLOW);}
    return output;
  }

  //-----------------------------------------------------GENERAL-HELPERS-----------------------------------------------------//

  //return a random adventurer (choose between all available subclasses)
  public static Adventurer createRandomAdventurer(){
    return new CodeWarrior("Bob"+(int)(Math.random()*100));
  }

  public static String userInput(Scanner in){
    Text.go(29,4);
    Text.showCursor();
    String input = in.nextLine();
    clearInput();
    return input;
  }

  public static String checkPulse(Adventurer a, ArrayList<Adventurer> team) {
  	if (a.getHP() <= 0) {
  	  team.remove(a);
  	  Text.clear(); //screen will be drawn automatically later
  	  return Text.colorize(" " + a.getName() + " has fallen!", Text.MAGENTA);
  	}
  	return "";
  }

  public static void quit(){
    Text.reset();
    Text.showCursor();
    Text.go(32,1);
	System.exit(0);
  }

  //-----------------------------------------------------PARTY-----------------------------------------------------//

  //Adventurers you control:
  //Make an ArrayList of Adventurers and add 2-4 Adventurers to it.
  public static void setupParty(ArrayList<Adventurer> party) {
	party.add(new SwordWarrior("Nick"));
    party.add(new CrossbowWarrior("Rick"));
    party.add(new CodeWarrior("Wick"));
  }

  public static String playerAction(String input, Adventurer p, ArrayList<Adventurer> party, ArrayList<Adventurer> enemies) {
  	Adventurer target = enemies.get((int) (Math.random() * enemies.size()));
  	//ATTACK
    if (input.equals("attack") || input.equals("a")){
      return p.attack(target) + checkPulse(target, enemies);
    }
  	//SPECIAL ATTACK
  	else if (input.equals("special") || input.equals("sp")){
  	  return p.specialAttack(target) + checkPulse(target, enemies);
  	}
  	//SUPPORT
  	else if (input.startsWith("su") || input.startsWith("support")){
  	  for (Adventurer other : party) {
  		if (input.toLowerCase().contains(other.getName().toLowerCase()) && other != p) {
  		  return p.support(other);
  		}
  	  }
  	  //support self if there was a typo or if the input was of an adventurer not in the party or if there was no other name in input
  	  return p.support();
  	}
  	//INVALID INPUT (else)
  	TextBox(10,10,"Invalid input! Try again."); //let the user know the input was invalid
  	Scanner in = new Scanner(System.in);
  	input = userInput(in);
  	return playerAction(input, p, party, enemies);
  }

  public static boolean updatePartyTurn(int whichPlayer, ArrayList<Adventurer> party) {
  	String prompt;
  	boolean partyTurn = true;
  	if (whichPlayer < party.size()) {
  	  //This is a player turn.
  	  //Decide where to draw the following prompt:
  	  prompt = "Enter command for " + party.get(whichPlayer) + ": attack/special/support/support other)/quit";
  	}
  	else {
  	  //This is after the player's turn, and allows the user to see the enemy turn
  	  //Decide where to draw the following prompt:
  	  prompt = "Press enter to see the enemy's turn or q to quit.";
  	  partyTurn = false;
  	}
  	displayPrompt(prompt);
  	return partyTurn;
  }

  //-----------------------------------------------------ENEMY-----------------------------------------------------//

  //Things to attack:
  //Make an ArrayList of Adventurers and add 1-3 enemies to it.
  //If only 1 enemy is added it should be the boss class.
  //start with 1 boss and modify the code to allow 2-3 adventurers later.

  public static void setupEnemies(ArrayList<Adventurer> enemies) {
  	int numEnemies = (int) (Math.random() * 3 + 1);
  	if (numEnemies == 1) {
      enemies.add(new Boss());
    }
  	else { //add 2-3 random adventurers
      ArrayList<String> names = new ArrayList<String>();
    	names.add("Benny");
    	names.add("Jazz");
    	names.add("Pat");
    	names.add("Sammy");
    	names.add("Brian");
      for (int i = 0; i < numEnemies; i++) {
    		int nameIndex = (int)(Math.random() * names.size());
    		String name = names.remove(nameIndex);
    		Adventurer newEnemy;
    		int typeEnemy = (int) (Math.random() * 3);
    		if (typeEnemy == 0) newEnemy = new CodeWarrior(name);
    		else if (typeEnemy == 1) newEnemy = new SwordWarrior(name);
    		else newEnemy = new CrossbowWarrior(name);
    		enemies.add(newEnemy);
  	  }
  	}
  }

  public static String chooseAction(Adventurer enemy, ArrayList<Adventurer> enemies, ArrayList<Adventurer> party) {

  	Adventurer target = chooseTarget(party);

  	//HEAL
  	int healChance = (int) (enemy.getmaxHP() - enemy.getHP()) / 2;
  	int useHeal = (int) (Math.random() * 100);
  	if (useHeal <= healChance) {
  	  return enemy.support();
  	}

  	//SPECIAL ATTACK
  	int specialChance = (int) (enemy.getSpecial() * 100 / enemy.getSpecialMax());
  	int useSpecial = (int) (Math.random() * 100);
  	if (useSpecial <= specialChance) {
  	  return enemy.specialAttack(target) + checkPulse(target, party);
  	}

  	//HEAL TEAMMATE
    for (Adventurer other : enemies) {
      if (other != enemy) { //comparing references is fine here.
        int helpChance = (int) (other.getmaxHP() - other.getHP()) / 2;
  		  int help = (int) (Math.random() * 100);
  		  if (help <= helpChance) {
          return enemy.support(other);
  		  }
  	  }
  	}
    
    // REGULAR ATTACK
    return enemy.attack(target) + checkPulse(target, party);

  }

  public static Adventurer chooseTarget(ArrayList<Adventurer> party) {
    return party.get( (int) (Math.random() * party.size()));
  }

  public static boolean updateEnemyTurn(int whichOpponent, ArrayList<Adventurer> party, ArrayList<Adventurer> enemies) {
  	boolean partyTurn = false;
  	if (whichOpponent >= enemies.size()){
  	  //THIS BLOCK IS TO END THE ENEMY TURN
  	  partyTurn=true;
  	  //display this prompt before player's turn
  	  String prompt = "Enter command for " + party.get(0)+": attack/special/support/support other)/quit";
  	  displayPrompt(prompt);
  	}
  	return partyTurn;
  }

}
