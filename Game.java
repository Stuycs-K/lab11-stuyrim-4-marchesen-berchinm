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
    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    //YOUR CODE HERE
    /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
  }

  //Display a line of text starting at
  //(columns and rows start at 1 (not zero) in the terminal)
  //use this method in your other text drawing methods to make things simpler.
  public static void drawText(String s,int startRow, int startCol){
    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    //YOUR CODE HERE
    /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
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
    Text.go(29,1);
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
        names += party.get(i) + "\t";
        HPs += "HP: " + party.get(i).getHP() + "\t";
        specials += party.get(i).getSpecialName() + ": " + party.get(i).getSpecial();
      }
      TextBox(startRow, 3, 30, 1, names);
      TextBox(startRow+1, 3, 30, 1, HPs);
      TextBox(startRow+2, 3, 30, 1, specials);
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
  public static void drawScreen(){

    drawBackground();

    // draw party

    //draw enemy party

  }

  public static String userInput(Scanner in){
      //Move cursor to prompt location

      //show cursor

      String input = in.nextLine();

      //clear the text that was written

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


        //Things to attack:
        //Make an ArrayList of Adventurers and add 1-3 enemies to it.
        //If only 1 enemy is added it should be the boss class.
        //start with 1 boss and modify the code to allow 2-3 adventurers later.
    ArrayList<Adventurer>enemies = new ArrayList<Adventurer>();

    Adventurer boss = new SwordWarrior("boss");


        //Adventurers you control:
        //Make an ArrayList of Adventurers and add 2-4 Adventurers to it.
    ArrayList<Adventurer> party = new ArrayList<Adventurer>();
    party.add(new SwordWarrior("Nick"));
    party.add(new CodeWarrior("Rick"));


    //Draw the window border

    //You can add parameters to draw screen!

    drawScreen(); //initial state.
    drawParty(party, 5);

    //Main loop

    //display this prompt at the start of the game.
    // String preprompt = "Enter command for " + party.get(whichPlayer) + ": attack/special/quit";

    while(! (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit"))){
      //Read user input
      input = userInput(in);

      //example debug statment
      TextBox(1,1,80,2,("input: "+input+" partyTurn:"+partyTurn+ " whichPlayer="+whichPlayer+ " whichOpp="+whichOpponent));

      //display event based on last turn's input
      if(partyTurn){

        // Adventurer current = party.get(whichPlayer);

        //Process user input for the last Adventurer:
        if(input.equals("attack") || input.equals("a")){

        }
        else if(input.equals("special") || input.equals("sp")){

        }
        else if(input.startsWith("su ") || input.startsWith("support ")){

        }

        //You should decide when you want to re-ask for user input
        //If no errors:
        whichPlayer++;


        if(whichPlayer < party.size()){
          //This is a player turn.
          //Decide where to draw the following prompt:
          String prompt = "Enter command for " + party.get(whichPlayer) + ": attack/special/quit";
        }

          else{
          //This is after the player's turn, and allows the user to see the enemy turn
          //Decide where to draw the following prompt:
          String prompt = "press enter to see monster's turn";

          partyTurn = false;
          whichOpponent = 0;
        }
        //done with one party member
      }else{
        //not the party turn!


        //enemy attacks a randomly chosen person with a randomly chosen attack.z`
        //Enemy action choices go here!
        /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
        //YOUR CODE HERE
        /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/


        //Decide where to draw the following prompt:
        String prompt = "press enter to see next turn";

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
        // String prompt = "Enter command for "+party.get(whichPlayer)+": attack/special/quit";
      }

      //display the updated screen after input has been processed.
      drawScreen();


    }//end of main game loop


    //After quit reset things:
    quit();
  }
}
