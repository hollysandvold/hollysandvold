/*
 * Holly Sandvold 260788799
 * a program that draws a polynomial curve
 */
import java.util.Arrays;
public class PolynomialCurves {
  
  
  public static void main(String[] args) { 
    
  }
  
  // a method that determines whether or not a given point appears on a curve 
  // with a specified thickness
  public static boolean onCurve(int[] coordinates, double[] coefficients, 
                             double thickness){
    double total = 0;
    int exponent = coefficients.length-1;
    for(int i = 0; i<coefficients.length; i++){
      total = total + coefficients[i]*Math.pow(coordinates[0], exponent);
      exponent--;
    }
    if(coordinates[1]-thickness<total && total<coordinates[1]+thickness)
      return true;
    return false;
  }
  
  // a method to verify the inputs
  public static void verifyInput(double[] polynomialCoeff, double thickness){
    if(polynomialCoeff.length<1){
      throw new IllegalArgumentException("Please use at least 1 element in your array.");
    } else if (thickness<=0) {
      throw new IllegalArgumentException("Please use a positive thickness.");
    } 
  }
  
  // a method that draws the graph of a polynomial curve
  public static void drawCurve(double[] coefficient, double thickness, char symbol){
    // verify input
    verifyInput(coefficient, thickness);
    // create int array to hold coordinates
    int[] coord = new int[2];
    // find y int and add length if necessary
    int yAxisPositive = 10;
    int yAxisNegative = 10;
    int yInt = (int)(coefficient[coefficient.length-1]);
    if (yInt<-5){
      yAxisNegative = yAxisNegative - yInt - 5;
    }
    if (yInt>5){
      yAxisPositive = yAxisPositive + yInt - 5;
    }
    int yAxis = yAxisPositive + yAxisNegative;
      
    // create graph
    for(int i = yAxisPositive; i >= -yAxisNegative; i--){
      for(int j = -10; j <= 10; j++){ 
        // set the coordinates
        coord[0]=j;
        coord[1]=i;
        // print the curve
        if(onCurve(coord, coefficient, thickness)){
          System.out.print(symbol);
        } 
        // print the graph 
        else {
          // x axis
          if(i==0){
            if(j==0){
              System.out.print("+");
            }  else if (j==10){
              System.out.print(">");
            } else {
              System.out.print("-");
            } 
          }      
          // y axis
          else {
            if (i==10 && j==0) {
              System.out.print("^");
            } else if (j==0 && i!=0) {
              System.out.print("|");
            } else if (i!=0) {
              System.out.print(" ");
            }
          }
        }
      }
      System.out.println();
    }
  }
         
}
