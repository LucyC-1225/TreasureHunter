public class Animations{
  Main main = new Main();
  public  void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
    final String CYAN = "\u001B[36m";
    final String RED = "\u001B[31m";
    final String YELLOW = "\u001B[33m";
    final String WHITE = "\u001B[37m";
    final String BLACK = "\u001B[30m";
    final String ANSI_GREEN = "\u001B[32m";
    final String BLUE = "\033[0;34m";
    final String BLUE_BRIGHT = "\033[0;94m";
    final String PURPLE = "\033[0;35m";
    
    private String name;
     
    private String color;

    public void brawl()throws InterruptedException{ 
      String spaces="          ";
        
    for (int i=0; i<=spaces.length(); i++){
      spaces= spaces.substring(i);
        System.out.println(RED+ spaces+ " ⏜⏜⏜" +BLUE+ spaces+    "      ⏜⏜⏜");
        System.out.println(RED+ spaces+ "|   |" + BLUE+ spaces+   "    |   |");
        System.out.println(RED+ spaces+ "|O O|" + BLUE+spaces+"    |O O|");
        System.out.println(RED+ spaces+ "| ) |" + BLUE+spaces+"    | ( |");
        System.out.println(RED+ spaces+ "| __|" + BLUE+spaces+"    | __|");
        System.out.println(RED+ spaces+ "  ||  OO" + BLUE+spaces+  "OO ||");
        System.out.println(RED+ spaces+ "| \\\\ |//" + BLUE+spaces+" \\\\| //|");
        System.out.println(RED+ spaces+ "|  \\\\|//" + BLUE+spaces+"  \\\\//|");
        System.out.println(RED+ spaces+ "|   |" + BLUE+spaces+"     |   |");
        System.out.println(RED+ spaces+ " | |" + BLUE+spaces+"       | |");
        System.out.println(RED+ spaces+ " | |" + BLUE+spaces+"       | |");
        System.out.println(RED+ spaces+ " ****" + BLUE+spaces+"      ****");
        backgroundFloor();
        Thread.sleep(800);
        clearScreen();
    }
    }
    private void backgroundFloor(){
      
       System.out.println(color()+"----------------------------------------------");
         System.out.println(color()+"--------        ---------------        -----");
        System.out.println(color()+"         Welcome to "+ name+ "                      ");
         System.out.println(color()+"--------        ---------------        -----");
         System.out.println(color()+"----------------------------------------------"+WHITE);
    }
    public String color(){
      if (main.game.getTerrainName().equalsIgnoreCase("Mountains")){
        name="Mountains";
        color=CYAN;
      }
      if (main.game.getTerrainName().equalsIgnoreCase("Ocean")){
        name="Ocean";
        color=BLUE_BRIGHT;
      }
      if (main.game.getTerrainName().equalsIgnoreCase("Plains")){
         name="Plains";
         color=PURPLE;
        }
      if (main.game.getTerrainName().equalsIgnoreCase("Desert")){
        name="Desert";
        color=YELLOW;
      }
        
      if (main.game.getTerrainName().equalsIgnoreCase("Jungle")){
      name="Jungle";
      color=ANSI_GREEN;
      }
      return color;
    }
}
