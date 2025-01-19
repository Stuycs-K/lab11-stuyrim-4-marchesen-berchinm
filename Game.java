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
	
    //display this prompt at the start of the game.
    String preprompt = "Enter command for " + party.get(whichPlayer) + ": attack/special/support/support other)/quit";
    TextBox(27,2,78,1,preprompt);

    while(!(input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit"))){

      //debug statment
      TextBox(26,2,78,1,("input: "+input+" partyTurn:"+partyTurn+ " whichPlayer="+whichPlayer+ " whichOpp="+whichOpponent));

      //Read user input
      input = userInput(in);
	  
	  if(partyTurn){
		
        String s; //the event of this turn, which will be defined and printed later
        Adventurer p = party.get(whichPlayer); //whose turn it is
		s = playerAction(input, p, party, enemies);
		TextBox(16,20,40,3,s); //record the event
		//You should decide when you want to re-ask for user input.
		
		//If no errors:
        whichPlayer++;
        partyTurn = updatePartyTurn(whichPlayer, party);
		if (! partyTurn) whichPlayer = 0;
        //END OF ONE PARTY MEMEBER'S TURN.
		
      }
	  
	  else { 

        //enemy attacks a randomly chosen person with a randomly chosen attack.

        Adventurer enemy = enemies.get(whichOpponent);
        String enemyS = chooseAction(enemy, enemies, party);
        TextBox(16,20,40,3,enemyS);

        //Decide where to draw the following prompt:
        String prompt = "Press enter to see the enemy's turn.";
        TextBox(27,2,78,1,prompt);

        whichOpponent++;
		partyTurn = updateEnemyTurn(whichOpponent, party, enemies);
		if (partyTurn) whichOpponent = 0;
		//END OF ONE ENEMY'S TURN.
		
      }
	  
	  //display the updated screen after input has been processed.
      drawScreen(party, enemies);
	  
	  //END OF MAIN GAME LOOP
    }

    //After quit reset things:
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
  
  /*Display a List of 2-4 adventurers on the rows row through row+3 (4 rows max)
    *Should include Name HP and Special on 3 separate lines.
    *Note there is one blank row reserved for your use if you choose.
    *Format:
    *Bob          Amy        Jun
    *HP: 10       HP: 15     HP:19
    *Caffeine: 20 Mana: 10   Snark: 1
    * ***THIS ROW INTENTIONALLY LEFT BLANK***
    */
  public static void drawParty(ArrayList<Adventurer> party, int startRow){
    for (int i = 0; i < party.size(); i++){
      Adventurer p = party.get(i);
      TextBox(startRow, 3+20*i, 15, 1, p.getName());
      TextBox(startRow+1, 3+20*i, 15, 1, colorByPercent(p.getHP(), p.getmaxHP()));
	  Text.reset(); //in case of colorized HP
      TextBox(startRow+2, 3+20*i, 15, 1, p.getSpecialName() + ": " + p.getSpecial() + "/" + p.getSpecialMax());
    }
  }
  
  public static void TextBox(int row, int col, int width, int height, String str){
    int charsPrinted = 0;
    int rowNum = 0;
    String text = str;
    for (int i = 0; i < width * height - str.length(); i++){
      text += " "; // make it a perfect rectangular string (spaces at end to replace)
    }
    if (text.length() > width * height){text = text.substring(0, width * height);} // cut off if box is too small
    while (charsPrinted < text.length()){
      Text.go(row+rowNum,col); // keep going to next row after printing width chars
      if (charsPrinted + width < text.length()) {System.out.print(text.substring(charsPrinted,charsPrinted+width));}
      else {System.out.print(text.substring(charsPrinted));} // watch for out-of-range error
      charsPrinted += width; // increment
      rowNum++;
    }
    Text.go(29,4);
    return;
  }
  
  //Display a line of text starting at
  //(columns and rows start at 1 (not zero) in the terminal)
  //use this method in your other text drawing methods to make things simpler.
  public static void drawText(String s, int startRow, int startCol){
    Text.go(startRow, startCol);
    System.out.print(s);
    Text.go(29,4);
    return;
  }
  
  //Use this to create a colorized number string based on the % compared to the max value.
  public static String colorByPercent(int hp, int maxHP){
    String output = "HP: " + String.format("%2s", hp+"")+"/"+String.format("%2s", maxHP+"");
    //COLORIZE THE OUTPUT IF HIGH/LOW:
    // under 25% : red
    // under 75% : yellow
    // otherwise : white
	int percent = hp * 100 / maxHP;
	if (percent < 25) output = Text.colorize(output, Text.RED);
	else if (percent < 75) output = Text.colorize(output, Text.YELLOW);
    return output;
  }



  //-----------------------------------------------------GENERAL-HELPERS-----------------------------------------------------//
  
  //return a random adventurer (choose between all available subclasses)
  //feel free to overload this method to allow specific names/stats.
  public static Adventurer createRandomAdventurer(){
    return new CodeWarrior("Bob"+(int)(Math.random()*100));
  }

  public static String userInput(Scanner in){
    Text.go(29,4);
    Text.showCursor();
    String input = in.nextLine();
    TextBox(29,2,78,1,"");
    return input;
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
    party.add(new SwordWarrior("Mick"));
    party.add(new CodeWarrior("Wick", 27));
  }
  
  public static String playerAction(String input, Adventurer p, ArrayList<Adventurer> party, ArrayList<Adventurer> enemies) {
	//QUIT
	if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) quit();
	//ATTACK
    else if (input.equals("attack") || input.equals("a")){
      return p.attack(enemies.get(0));
    }	
	//SPECIAL ATTACK
	else if (input.equals("special") || input.equals("sp")){
	  return p.specialAttack(enemies.get(0));
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
	TextBox(16,20,40,3,"Invalid input! Try again."); //let the user know the input was invalid
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
	  prompt = "Press enter to see the enemy's turn.";
	  partyTurn = false;
	}
	TextBox(27,2,78,1,prompt);
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
	else {
	  //add 2-3 random adventurers
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
	  return enemy.specialAttack(chooseTarget(party));
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
	
	//REGULAR ATTACK
	return enemy.attack(chooseTarget(party));
	
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
	  TextBox(27,2,78,1,prompt);
	}
	return partyTurn;
  }
  
}
