import java.util.*;

public class Game{
  private static final int WIDTH = 80;
  private static final int HEIGHT = 30;
  private static final int BORDER_COLOR = Text.BLACK;
  private static final int BORDER_BACKGROUND = Text.WHITE + Text.BACKGROUND;

  public static void main(String[] args) {
    run();
  }

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
    return;
  }

  //Display a line of text starting at
  //(columns and rows start at 1 (not zero) in the terminal)
  //use this method in your other text drawing methods to make things simpler.
  public static void drawText(String s,int startRow, int startCol){
    Text.go(startRow, startCol);
    System.out.print(s);
    Text.go(29,4);
    return;
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

    //return a random adventurer (choose between all available subclasses)
    //feel free to overload this method to allow specific names/stats.
  public static Adventurer createRandomAdventurer(){
    return new CodeWarrior("Bob"+(int)(Math.random()*100));
  }

    /*Display a List of 2-4 adventurers on the rows row through row+3 (4 rows max)
    *Should include Name HP and Special on 3 separate lines.
    *Note there is one blank row reserved for your use if you choose.
    *Format:
    *Bob          Amy        Jun
    *HP: 10       HP: 15     HP:19
    *Caffeine: 20 Mana: 10   Snark: 1
    * ***THIS ROW INTENTIONALLY LEFT BLANK***
    */
  public static void drawParty(ArrayList<Adventurer> party,int startRow){
    String names = "", HPs = "", specials = "";
    for (int i = 0; i < party.size(); i++){
      Adventurer p = party.get(i);
      TextBox(startRow, 3+20*i, 15, 1, p.getName());
      TextBox(startRow+1, 3+20*i, 15, 1, "HP: " + p.getHP() + "/" + p.getmaxHP());
      TextBox(startRow+2, 3+20*i, 15, 1, p.getSpecialName() + ": " + p.getSpecial() + "/" + p.getSpecialMax());
    }
  }

  //Use this to create a colorized number string based on the % compared to the max value.
  public static String colorByPercent(int hp, int maxHP){
    String output = String.format("%2s", hp+"")+"/"+String.format("%2s", maxHP+"");
    //COLORIZE THE OUTPUT IF HIGH/LOW:
    // under 25% : red
    // under 75% : yellow
    // otherwise : white
    return output;
  }

  //Display the party and enemies
  //Do not write over the blank areas where text will appear.
  //Place the cursor at the place where the user will by typing their input at the end of this method.
  public static void drawScreen(ArrayList<Adventurer> party, ArrayList<Adventurer> enemies){
    drawBackground();
    drawParty(party, 22);
    drawParty(enemies, 3);
    return;
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
  }

  public static void run(){
    //Clear and initialize
    Text.hideCursor();
    Text.clear();

    boolean partyTurn = true;
    int whichPlayer = 0;
    int whichOpponent = 0;
    int turn = 0;
    String input = "";//blank to get into the main loop.
    Scanner in = new Scanner(System.in);

        //Adventurers you control:
        //Make an ArrayList of Adventurers and add 2-4 Adventurers to it.
    ArrayList<Adventurer> party = new ArrayList<Adventurer>();
    party.add(new SwordWarrior("Nick"));
    party.add(new CodeWarrior("Rick"));
    party.add(new SwordWarrior("Mick"));
    party.add(new CodeWarrior("Wick", 27));

        //Things to attack:
        //Make an ArrayList of Adventurers and add 1-3 enemies to it.
        //If only 1 enemy is added it should be the boss class.
        //start with 1 boss and modify the code to allow 2-3 adventurers later.
    ArrayList<Adventurer>enemies = new ArrayList<Adventurer>();
    enemies.add(new Boss());

    drawScreen(party, enemies); //initial state.

    //Main loop
    //display this prompt at the start of the game.
    String preprompt = "Enter command for " + party.get(whichPlayer) + ": attack/special/quit";
    TextBox(27,2,78,1,preprompt);

    while(!(input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit"))){

      //debug statment
      TextBox(26,2,78,1,("input: "+input+" partyTurn:"+partyTurn+ " whichPlayer="+whichPlayer+ " whichOpp="+whichOpponent));

      //Read user input
      input = userInput(in);

      //display event based on last turn's input
      if(partyTurn){
        //Process user input for the last Adventurer:
        String s = null;
        Adventurer p = party.get(whichPlayer);
        if(input.equals("attack") || input.equals("a")){
          s = p.attack(enemies.get(0));
          TextBox(16,20,40,3,s);
        }
        else if(input.equals("special") || input.equals("sp")){
          s = p.specialAttack(enemies.get(0));
          TextBox(16,20,40,3,s);
        }
        else if(input.startsWith("su") || input.startsWith("support")){
          String num = input.replaceAll("[^1-9]","");
          int player;
          if (num.length() != 0){player = Integer.parseInt(input);}
          else{player = whichPlayer;}
          if (player == whichPlayer){s = p.support();}
          else if (player == 0){s = p.support(party.get(0));}
          else if (player == 1){s = p.support(party.get(1));}
          else if (player == 2){s = p.support(party.get(2));}
          else if (player == 3){s = p.support(party.get(3));}
          else {s = p.support();}
          TextBox(16,20,40,3,s);
        }

        //You should decide when you want to re-ask for user input
        //If no errors:
        whichPlayer++;

        if(whichPlayer < party.size()){
          //This is a player turn.
          //Decide where to draw the following prompt:
          String prompt = "Enter command for " + party.get(whichPlayer) + ": attack/special/quit";
          TextBox(27,2,78,1,prompt);
        }
        else{
          //This is after the player's turn, and allows the user to see the enemy turn
          //Decide where to draw the following prompt:
          String prompt = "press enter to see monster's turn";
          TextBox(27,2,78,1,prompt);

          partyTurn = false;
          whichOpponent = 0;
        }
        //done with one party member
      }
      else{
        //not the party turn!

        //enemy attacks a randomly chosen person with a randomly chosen attack.

        if (enemies.size() == 1){ // what a single boss would do
          Adventurer enemy = enemies.get(whichOpponent);
          Adventurer randHero = party.get((int)(4 * Math.random()));
          int randAttack = (int)(3 * Math.random());
          String enemyS;
          if (randAttack == 0){enemyS = enemy.attack(randHero);}
          else if (randAttack == 1){enemyS = enemy.support();}
          else{enemyS = enemy.specialAttack(randHero);}
          TextBox(16,20,40,3,enemyS);
        }


        //Decide where to draw the following prompt:
        String prompt = "press enter to see next turn";
        TextBox(27,2,78,1,prompt);

        whichOpponent++;
      }//end of one enemy.

      //modify this if statement.
      if(!partyTurn && whichOpponent >= enemies.size()){
        //THIS BLOCK IS TO END THE ENEMY TURN
        //It only triggers after the last enemy goes.
        whichPlayer = 0;
        turn++;
        partyTurn=true;
        //display this prompt before player's turn
        String prompt = "Enter command for " + party.get(whichPlayer)+": attack/special/quit";
        TextBox(27,2,78,1,prompt);
      }

      //display the updated screen after input has been processed.
      drawScreen(party, enemies);
    }//end of main game loop

    //After quit reset things:
    quit();
  }

}
