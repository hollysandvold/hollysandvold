/**
 * Holly Sandvold 260788799
 * A program to simulate a simplified version of Conway's Game of Life
 */
import java.util.Arrays;
public class GameOfLife {
  public static void main(String[] args) { 
  }

  
  // a method to determine the validity of a universe 
  public static boolean isValidUniverse(int[][] universe) { 
    // check that the universe is rectangular 
    for(int i = 0; i<universe.length-1; i++){
      if(universe[i].length==universe[i+1].length) {
        continue;
      } else {
        return false; 
      }
    }   
    // check that all the elements of the subarrays are 0 or 1
    int counter = 0;
    int length = 0;
    for(int j = 0; j<universe.length; j++){
      for(int k = 0; k<universe[j].length; k++){
        if(universe[j][k] == 0 || universe[j][k] == 1){
          counter++;
        }
        length++;
      }
    }
    if(length == counter)
      return true;
    return false;
  }
  
  // a method to display the universe
  public static void displayUniverse(int[][] universe){
    int length = universe.length + 2;
    int width = universe[0].length + 2;
      
    for(int i = 0; i<length; i++){
      for(int j = 0; j<width; j++){
        if(i == 0 || i == length-1) {
          if(j==0 || j==width-1) {
            System.out.print("+");
          } else {
            System.out.print("-");
          }
        } else if (i>0 && i<length-1 && j>0 && j<width-1) {
          if(j == 1)
            System.out.print("|");  
          if(universe[i-1][j-1] == 1){
            System.out.print("*");
          } else if (universe[i-1][j-1] == 0) {
            System.out.print(" ");
          }
          if(j == width-2)
            System.out.print("|");
        } 
      }
      System.out.println("");
    }
  }
  
  // a method to get the cell from the next generation
  public static int getNextGenCell(int[][] universe, int x, int y){
    // set up range so that there are no out of bounds exceptions
    int rangeXStart = x-1;
    int rangeXStop = x+1;
    int rangeYStart = y-1;
    int rangeYStop = y+1;
    if(x==0){
      rangeXStart = x;
    } else if (x==universe.length-1){
      rangeXStop = x;
    } 
    if (y==0){
      rangeYStart=y; 
    } else if (y==universe[0].length-1){
      rangeYStop=y;
    }
    
    // count number of neighbors
    int neighbors = 0;
    for(int i = rangeXStart; i<=rangeXStop; i++){
      for(int j = rangeYStart; j<=rangeYStop; j++){
        if(i==x && j==y) {
          continue;
        } else if(universe[i][j]==1) {
          neighbors++; 
        }
      }
    }
    
    //check number of neighbors for both alive and dead squares
    if(universe[x][y]==1 && neighbors<2 || neighbors>3){
      return 0;
    } else if(universe[x][y]==1 && neighbors>1 && neighbors<4){
      return 1;
    } else if(universe[x][y]==0 && neighbors==3){
      return 1;
    } else {
      return 0;
    }
  }
  
  // a method to get the next generation universe
  public static int[][] getNextGenUniverse(int[][] universe){
    int[][] newGenUniverse = new int[universe.length][universe[0].length];
    for(int i = 0; i<universe.length; i++){
      for(int j = 0; j<universe[i].length; j++){
        newGenUniverse[i][j] = getNextGenCell(universe,i,j);
      }
    }
    return newGenUniverse;
  }
  
  // a method to display a given number of generations
  public static void simulateNGenerations(int[][] universe, int n){
    // check if the input is valid
    if(!isValidUniverse(universe))
      throw new IllegalArgumentException("Please use a valid universe");
    
    // display original seed
    System.out.println("Orginal seed");
    displayUniverse(universe);
    
    // make new array to hold universe
    int[][] updatedUniverse = new int[universe.length][universe[0].length];
    for(int i = 0; i<universe.length; i++){
      for(int j = 0; j<universe[0].length;j++){
        updatedUniverse[i][j] = universe[i][j];
      }
    }
    // print n generations
    for(int i = 1; i <= n; i++){
      System.out.println("Generation "+i);
      updatedUniverse = getNextGenUniverse(updatedUniverse);
      displayUniverse(updatedUniverse);
    }
  }
}
