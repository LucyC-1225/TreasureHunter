public class Town
{
  //instance variables
  private Hunter hunter;
  private Shop shop;
  private Terrain terrain;
  private String printMessage;
  private boolean toughTown;
  // added- Apramjot
  private boolean easyTown; 
  //added - Lucy
  private boolean cheatTown;
  private String treasure;
  private static int numTreasureFound;
  private boolean searchedTreasure;
  final String YELLOW = "\u001B[33m";
  final String WHITE = "\u001B[37m";
  //added - Lucy

  //Constructor
  /**
  * The Town Constructor takes in a shop and the surrounding terrain, but leaves the hunter as null until one arrives.
  * @param s The town's shoppe.
  * @param t The surrounding terrain.
  * @param e The hunter's level choice //added by Apramjot
  */
  //Lucy - added another parameter, cheatMode
  public Town(Shop shop, double toughness, boolean easyMode, boolean cheatMode)//- Added by Apramjot- Since an update had been done to the constructor signature to create an instance in  TreasureHunter a new boolean parameter had to be added 
  { 
    easyTown = easyMode;
    this.shop = shop;
    this.terrain = getNewTerrain();
    
    // the hunter gets set using the hunterArrives method, which
    // gets called from a client class
    hunter = null;
    
    printMessage = "";
    //Added -Apramjot
    if (easyMode){ // if easyMode is choosen then finding the boolean value is not needed for toughTown as it is always set to false
      toughTown = false;
    }
    //Lucy - setting cheatTown to true if cheatMode is true
    else if (cheatMode){
      toughTown = false;
      cheatTown = true;
    }
    else{
      // higher toughness = more likely to be a tough town
      toughTown = (Math.random() < toughness);
    }
    //added - Lucy
    treasure = "";

    searchedTreasure = false;
    //added - Lucy
  }
  
  public String getLatestNews()
  {
    return printMessage;
  }
   
  /**
  * Assigns an object to the Hunter in town.
  * @param h The arriving Hunter.
  */
  public void hunterArrives(Hunter hunter)
  {
    //added - Lucy
    generateTreasure();
    //added - Lucy
    this.hunter = hunter;
    printMessage = "Welcome to town, " + hunter.getHunterName() + ".";
      
    if (toughTown)
    {
      printMessage += "\nIt's pretty rough around here, so watch yourself.";
    }
    else
    {
      printMessage += "\nWe're just a sleepy little town with mild mannered folk.";
    }
  }
   
  /**
  * Handles the action of the Hunter leaving the town.
  * @return true if the Hunter was able to leave town.
  */
  public boolean leaveTown()
  {
    boolean canLeaveTown = terrain.canCrossTerrain(hunter);
    if (canLeaveTown)
    {
      String item = terrain.getNeededItem();
      printMessage = "You used your " + item + " to cross the " + terrain.getTerrainName() + ".";
      if (checkItemBreak())
      {
        hunter.removeItemFromKit(item);
        printMessage += "\nUnfortunately, your " + item + " broke.";
      }
            
      return true;
    }
      
    printMessage = "You can't leave town, " + hunter.getHunterName() + ". You don't have a " + terrain.getNeededItem() + ".";
    return false;
  }
  
  public void enterShop(String choice)
  {
    shop.enter(hunter, choice);
  }
  
  /**
  * Gives the hunter a chance to fight for some gold.<p>
  * The chances of finding a fight and winning the gold are based on the toughness of the town.<p>
  * The tougher the town, the easier it is to find a fight, and the harder it is to win one.
  */
  Animations animations2= new Animations();
  public void lookForTrouble()throws InterruptedException
  {
    double possiblity = 0;//Added-Apramjot. help increasing the chances of getting in a fight in easyMode and getting more wins. 
    double noTroubleChance;//Added-Apramjot. The variable noTroubleChance either increases or decreases the chance of winning in a brawl or not. In order for easy Mode to allow majority wins noTroubleChance has to be set to a lower number so the randomly generated num will mostly be greater than the variable.  
    if (easyTown){
      noTroubleChance = 0.23;
    }
    else if (toughTown)
    {
      noTroubleChance = 0.66; 
    }
    else
    {
      noTroubleChance = 0.33;
    }
    if (easyTown){
      possiblity = 4; 
    }
    else{
       possiblity = 1;
    }
    if (Math.random() / possiblity > noTroubleChance && cheatTown == false) //Added-Apramjot. dividing will lower the chances in easy mode because it is set to 10 will any other mode the variable is set to 1.
    {
      printMessage = "You couldn't find any trouble";
    }
    else
    { 
      int goldDiff = 0;
      printMessage = "You want trouble, stranger!  You got it!\nOof! Umph! Ow!\n";
       animations2.brawl(); //added Apramjot
      //Lucy - added how much gold you would get in cheatTown
      if (cheatTown){
        goldDiff = 100;
      }
      else if (easyTown){
         goldDiff = (int)(Math.random() * 20) + 5;//Added-Apramjot. the variable goldDiff would have to increase for easyMode.  rannge 5-19. 
      }
      else{
        goldDiff = (int)(Math.random() * 10) + 1;
      }
      //Lucy - made it so that in cheatTown, the user always wins
      if (Math.random() > noTroubleChance || cheatTown == true)
      {
        printMessage += "Okay, stranger! You proved yer mettle. Here, take my gold.";
        printMessage += "\nYou won the brawl and receive " +  goldDiff + " gold.";
        hunter.changeGold(goldDiff);
      }
      else
      {
        printMessage += "That'll teach you to go lookin' fer trouble in MY town! Now pay up!";
        printMessage += "\nYou lost the brawl and pay " +  goldDiff + " gold.";
        hunter.changeGold(-1 * goldDiff);
      }
    }
  }
   
  public String toString()
  {
    return "This nice little town is surrounded by " + terrain.getTerrainName() + ".";
  }
  
  /**
  * Determines the surrounding terrain for a town, and the item needed in order to cross that terrain.
  * 
  * @return A Terrain object.
  */
  private Terrain getNewTerrain()
  {
    double rnd = Math.random();
    if (rnd < .2)
    {
      return new Terrain("Mountains", "Rope");
    }
    else if (rnd < .4)
    {
      return new Terrain("Ocean", "Boat");
    }
    else if (rnd < .6)
    {
      return new Terrain("Plains", "Horse");
    }
    else if (rnd < .8)
    {
      return new Terrain("Desert", "Water");
    }
    else
    {
      return new Terrain("Jungle", "Machete");
    }
  }
  
  /**
  * Determines whether or not a used item has broken.
  * @return true if the item broke.
  */
  private boolean checkItemBreak()
  {
    double rand = Math.random();
    return (rand < 0.5);
  }

  //added - Lucy
  /**
  * Chooses a random treasure out of the three treasure options and sets the generated treasure to instance variable treasure
  */
  private void generateTreasure(){
    if (!searchedTreasure){
      int random = (int) (Math.random() * 3 + 1);
      if (random == 1){
        treasure = YELLOW + "diamonds" + WHITE;
      } else if (random == 2){
        treasure = YELLOW + "crystals" + WHITE;
      } else if (random == 3){
        treasure = YELLOW + "gems" + WHITE;
      }
    }
  }
  /**
  * Determines the result of the user trying to find treasure. There is a 50 percent chance the user does not find any treasure and a 50 percent chance the user finds treasure. When the user finds treasure but already has the treasure, the treasure is discarded
  */
  public void findTreasure(){
    if (searchedTreasure == false){
      int random = (int) (Math.random() * 2 + 1);
      if (random == 1){
        printMessage = "You found no treasure :(";
        searchedTreasure = true;
      } else {
        if (hunter.hasItemInKit(treasure)){
          printMessage = "Oh no, you already have this treasure. Please discard it.";
          searchedTreasure = true;
        } else {
          printMessage = "Congratulations, you have found a unique treasure! You found " + treasure + "!";
          searchedTreasure = true;
          hunter.addItem(treasure);
          numTreasureFound++;
        }
      }
    } else {
      printMessage = "You have already searched for treasure in this town. Please go to the next town if you want to find more treasures.";
    }
  }
  /**
  * Determines when the game ends.
  * @return true if the hunter has 0 gold or found 3 unique treasures. false if netiher of these conditions are true.
  */
  public boolean endGame(){
    if (hunter.getGold() == 0){
      printMessage = "You have ran out of money and lost game!";
      return true;
    } else if (numTreasureFound == 3){
      printMessage = "You have found all 3 unique treasures! You won the game!!";
      return true;
    } else {
      return false;
    }
  }
  //added - Lucy
   public String getTerrainName(){
    return terrain.getTerrainName();
  }
}
