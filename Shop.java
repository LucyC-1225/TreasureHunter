import java.util.Scanner;

public class Shop
{
  // constants
  private static final int WATER_COST = 2;
  private static final int ROPE_COST = 4;
  private static final int MACHETE_COST = 6;
  private static final int HORSE_COST = 12;
  private static final int BOAT_COST = 20;
  
  // instance variables
  private double markdown;
  private Hunter customer;
  //Lucy
  private boolean cheatShop;
  //Constructor
  public Shop(double markdown, boolean cheatMode)
  {
    this.markdown = markdown;
    customer = null;
    //Lucy - sets cheatShop to true if cheatMode is true
    if (cheatMode){
      cheatShop = true;
    }
  }
  
  /** method for entering the shop
   * @param hunter  the Hunter entering the shop
   * @param buyOrSell  String that determines if hunter is "B"uying or "S"elling
  */
  public void enter(Hunter hunter, String buyOrSell)
  {
    customer = hunter;
    
    Scanner scanner = new Scanner(System.in);
    if (buyOrSell.equals("B") || buyOrSell.equals("b"))
    {
      System.out.println("Welcome to the shop! We have the finest wares in town.");
      System.out.println("Currently we have the following items:");
      System.out.println(inventory());
      System.out.print("What're you lookin' to buy? ");
      String item = scanner.nextLine();
      //added - Lucy
      if (item.equalsIgnoreCase("w")){
        item = "water";
      }
      if (item.equalsIgnoreCase("r")){
        item = "rope";
      }
      if (item.equalsIgnoreCase("m")){
        item = "machete";
      }
      if (item.equalsIgnoreCase("h")){
        item = "horse";
      }
      if (item.equalsIgnoreCase("b")){
        item = "boat";
      }
      //added - Lucy
      int cost = checkMarketPrice(item, true);
      if (cost == -1)
      {
        System.out.println("We ain't got none of those.");
      }
      else
      {
        System.out.print("It'll cost you " + cost + " gold. Buy it (y/n)? ");
        String option = scanner.nextLine();
        
        if (option.equals("y") || option.equals("Y"))
        {
          buyItem(item);
        }
      }
    }
    else
    {
      System.out.println("You currently have the following items: " + customer.getInventory());
      System.out.print("What're you lookin' to sell? ");
      String item = scanner.nextLine();
      //added - Lucy
      item = item.toLowerCase();
      //added - Lucy
      int cost = checkMarketPrice(item, false);
      if (cost == -1)
      {
        System.out.println("We don't want none of those.");
      }
      else
      {
        System.out.print("It'll get you " + cost + " gold. Sell it (y/n)? ");
        String option = scanner.nextLine();
        
        if (option.equals("y") || option.equals("Y"))
        {
          sellItem(item);
        }
      }
    }
  }
  
  /** A method that returns a string showing the items available in the shop (all shops sell the same items)
  *
  * @return  the string representing the shop's items available for purchase and their prices 
  */
  public String inventory()
  {
    String str = "";
    str += "    --------------------------\n";
    str += "   /                          \\\n";
    str += "  /                            \\\n";
    str += " /                              \\\n";
    str += "/                                \\\n";
    str += "---------------Shop---------------\n";
    str += "| Name                      Cost |\n";
    str += "|                                |\n";
    str += "| (W)ater                 " + getCostOfItem("water") + " gold |\n";
    str += "| (R)ope                  " + getCostOfItem("rope") + " gold |\n";
    str += "| (M)achete               " + getCostOfItem("machete") + " gold |\n";
    if (cheatShop){
      str += "| (H)orse                 " + getCostOfItem("horse") + " gold |\n";
      str += "| (B)oat                  " + getCostOfItem("boat") + " gold |\n";
      str += "__________________________________\n";
    } else {
      str += "| (H)orse                " + getCostOfItem("horse") + " gold |\n";
      str += "| (B)oat                 " + getCostOfItem("boat") + " gold |\n";
      str += "__________________________________\n";
    }
    return str;
  }

  /**
  * A method that lets the customer (a Hunter) buy an item.
  * @param item The item being bought.
  */
  public void buyItem(String item)
  {
    int costOfItem = checkMarketPrice(item, true);
    if (customer.buyItem(item, costOfItem))
    {
      System.out.println("Ye' got yerself a " + item + ". Come again soon.");
    }
    else
    {        
      System.out.println("Hmm, either you don't have enough gold or you've already got one of those!");
    }
  }
   
  /**
  * A pathway method that lets the Hunter sell an item.
  * @param item The item being sold.
  */
  public void sellItem(String item)
  {
    int buyBackPrice = checkMarketPrice(item, false);
    if (customer.sellItem(item, buyBackPrice))
    {
      System.out.println("Pleasure doin' business with you.");
    }
    else
    {
      System.out.println("Stop stringin' me along!");
    }
  }
  
  /**
  * Determines and returns the cost of buying or selling an item.
  * @param item The item in question.
  * @param isBuying Whether the item is being bought or sold.
  * @return The cost of buying or selling the item based on the isBuying parameter.
  */
  public int checkMarketPrice(String item, boolean isBuying)
  {
    if (isBuying)
    {
      return getCostOfItem(item);
    }
    else
    {
      return getBuyBackCost(item);
    }
  }
   
  /**
  * Checks the item entered against the costs listed in the static variables.
  * 
  * @param item The item being checked for cost.
  * @return The cost of the item or 0 if the item is not found.
  */
  //Lucy - if cheatShop is true, every item costs 1 gold
  public int getCostOfItem(String item)
  {
    if (item.equals("water"))
    {
      if (cheatShop){
        return 1;
      }
      return WATER_COST;
    }
    else if (item.equals("rope"))
    {
      if (cheatShop){
        return 1;
      }
      return ROPE_COST;
    }
    else if (item.equals("machete"))
    {
      if (cheatShop){
        return 1;
      }
      return MACHETE_COST;
    }    
    else if (item.equals("horse"))
    {
      if (cheatShop){
        return 1;
      }
      return HORSE_COST;
    }
    else if (item.equals("boat"))
    {
      if (cheatShop){
        return 1;
      }
      return BOAT_COST;
    }
    else
    {        
      return -1;
    }
  }
   
  /**
  * Checks the cost of an item and applies the markdown.
  * 
  * @param item The item being sold.
  * @return The sell price of the item.
  */
  public int getBuyBackCost(String item)
  {
    int cost = (int)(getCostOfItem(item) * markdown);
    return cost;
  }
}
