/*
 * Holly Sandvold 260788799
 * a program that generates a random 4 digit number and 
 * has a player guess the answer while providing hints
 */
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class BullsAndCows {
  
  
  public static void main(String[] args) { 
    int random = (int)(Math.random()*100);
    System.out.println(Arrays.toString(generateSecretDigits(random)));
    playBullsAndCows(random); 
  }
  
  // a method that checks if an element is in a given array
  public static boolean contains(int[] arr, int num){
    // loop through all elements of arr and see if any equals num
    for(int i = 0; i<arr.length; i++){
      if(arr[i]==num){
       return true; 
      }
    }
    return false;
  }
  
  // a method that generates an array containing the 4 secret numbers 
  public static int[] generateSecretDigits(int seed){
    // create an array to store the digits
    int[] secretDigits = new int[4];
    // set up the random generator with the seed
    Random generate = new Random(seed);
    // loop through all elements of secretDigits and initialize them with a 
    // new int each time 
    for(int i=0; i<secretDigits.length; i++){
      int random = generate.nextInt(10);
      // generate a new number if it is the same as another element
      for(int j = i; j>0; j--){
        if(random == secretDigits[i-j]) {
          random = generate.nextInt(10);
        }
      }
      secretDigits[i]=random;
    }
    return secretDigits;
  }
  
  // a number that isolates the digits of a given number
  public static int[] extractDigits(int given){
    
    // change int to String in order to find the number of digits
    String number = Integer.toString(given);
    
    // create array to hold the isolated digits
    int[] extractedDigits;  
    if(number.length()<4){
      extractedDigits = new int[4];
    } else {
      extractedDigits = new int[number.length()];
    }
    
   // create array for numbers less than 4 digits
    if (number.length() < 4) {
      for(int i = 0; i<number.length(); i++) {
        if(number.length()-3>i) {
          extractedDigits[i]=0;
        } else { 
          int shift = 4-number.length();
          extractedDigits[i+shift]=Integer.parseInt(String.valueOf(number.charAt(i)));
        }
      }
    } 
    
    // create an array for numbers with 4 or more digits
    else {
      for(int i = 0; i<number.length(); i++) {    
        extractedDigits[i]=Integer.parseInt(String.valueOf(number.charAt(i)));
      }
    }
    return extractedDigits;
  }
  
  // a method to get the numbers of Bulls from a guess
  public static int getNumOfBulls(int[] secret, int[] guess){
    // throw an exception if the guess and the secret num are not the same lenght
    if(secret.length != guess.length)
      throw new IllegalArgumentException("Your guess is not the correct number " +
    
                                         "of digits");
    // check to see how many bulls there are    
    int counter = 0;
    for(int i = 0; i < secret.length; i++){
      if(secret[i]==guess[i])
        counter++;
    }
    return counter;
  }
  
  // a method to get the number of cows in a guess
  public static int getNumOfCows(int[] secret, int[] guess){
    // throw an exception if the guess and the secret num are not the same lenght
    if(secret.length != guess.length)
      throw new IllegalArgumentException("Your guess is not the correct number " +
                                           "of digits");
    int counter = 0;
    for(int i = 0; i<secret.length; i++){
      for(int j = 0; j<secret.length; j++){
        if(i==j) {
          continue;
        } else if (secret[i]==guess[j] && secret[i]!=guess[i] 
                   && secret[j]!=guess[j]) {
          counter++;
          break;
        }
      }
    }
    return counter;
  }
  
  // a method to play a game of Bulls and Cows
  public static void playBullsAndCows(int seed){
    int[] secretNumber = generateSecretDigits(seed);
    // welcome statement and set up Scanner
    System.out.println("Welcome to Bulls and Cows! Try to figure out the secret 4 digit number!");
    Scanner input = new Scanner(System.in);
    for(int i = 1; ;i++) {
      // for guesses after 5
      if(i>4){
        input.nextLine();
        System.out.print("Might be time for you to give up. Do you want to quit? (y/n)");
        String yesNo = input.nextLine();
        // if they want to quit, exit game
        if(yesNo.equals("y")) {
          System.out.println("You've decided to quit after playing " + i 
                               + " rounds :( Play again soon!");
          break;
        }
      } 
      // check guesses
      System.out.print("Guess #" + i + ". Enter a four digit number: ");
      int guess = 0;
      guess = input.nextInt();
      int[] guessArray = extractDigits(guess);
      // send message for negative guesses or guesses with too many digits
      if (guess>9999 || guess<0) {
        System.out.println("Please try again using a positive number" +
                           " with no more than 4 digits!");
      } 
      // winning guess
      else if (Arrays.equals(secretNumber, guessArray)) {
        System.out.println("Congratualtions! You guessed the secret number in only "+
                           i + " tries! Play again soon!");
        break;   
      } 
      // all other gueses 
      else {
        System.out.println("You guessed " + guess);
        System.out.println("Bulls: " + getNumOfBulls(secretNumber, guessArray) +
                           ", Cows: " + getNumOfCows(secretNumber, guessArray));
      }
    }
  }
}
