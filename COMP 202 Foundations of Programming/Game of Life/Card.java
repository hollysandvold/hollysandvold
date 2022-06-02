/*
 * Holly Sandvold 260788799
 */

public class Card {
  // attributes
  private int value;
  private String suit;
  
  // constructor method
  public Card (int value, String suit){
    verifyInputs(value, suit);
    this.value = value;
    this.suit = suit;
  }
  
  // method to verify inputs
  private static void verifyInputs(int value, String suit){
    // verify the value and suit of the cards
    if(value<14 && value>0 && (suit.equalsIgnoreCase("spades") ||
        suit.equalsIgnoreCase("diamonds") ||
        suit.equalsIgnoreCase("hearts") ||
        suit.equalsIgnoreCase("clubs"))) {
    } 
    // throw an exception otherwise
    else {
      throw new IllegalArgumentException("Please use valid inputs");
    }
  }
  
  // a method to get the value of a card
  public int getValue(){
    return this.value;
  }
  // a method to get the suit of a card
  public String getSuit(){
    return this.suit;
  }
  
}