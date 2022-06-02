/*
 * Holly Sandvold 260788799
 */
import java.util.Random;

public class Deck {
  // attributes
  private Card[] cards;
  private int numOfCards;
  private static Random numberGenerator = new Random(123);

  // constructor
  public Deck () {
    // initialize deck and set value for each card
    this.cards = new Card[52];
    String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
    int i = 0;
    for(int suit = 0; suit<4; suit++) {
      for (int value = 13; value >=1; value--) {
        this.cards[i] = new Card(value, suits[suit]);
        i++;
      }
    }
    // set numOfCards at 52
    this.numOfCards = 52;
  }
  
  // a method to get the number of cards
  public int getNumOfCards(){
    return this.numOfCards;
  }
  
  // a method to get the cards
  public Card[] getCards(){
    Card[] tempCards = new Card[numOfCards];
    for(int i = 0; i<=numOfCards-1; i++){
      tempCards[i] = this.cards[i];
    }
    return tempCards;
  }

  // a method to display all the cards left in the deck
  public void showCards(){
    // print all remaining cards
    for(int i = numOfCards-1; i>=0; i--){
      System.out.print(cards[i].getValue()+" of "+cards[i].getSuit());
      if(i>0){
        System.out.print(", ");
      } else {
        System.out.print(".");
      }
    } 
    System.out.println("");
  }
  
  // a method to shuffle the remaining cards
  public void shuffle(){
    // choose two random numbers from the deck and swap them
    for(int i = 0; i<1000; i++){
      int randomOne = numberGenerator.nextInt(numOfCards);
      int randomTwo = numberGenerator.nextInt(numOfCards);
      Card tempCard = cards[randomOne];
      cards[randomOne] = cards[randomTwo];
      cards[randomTwo] = tempCard;
    }
  }
  
  // a method to deal out the top card
  public Card deal(){
    // return null if there are no cards remaining
    if(numOfCards<0){
      return null;
    }
    // deal top card and reduce numOfCards by 1
    int topCard = numOfCards-1;
    numOfCards--;
    return cards[topCard];
  }
  
  // a method to choose a card from the deck
  public Card pickACard(int position){
    // flip the position 
    int reversePosition = numOfCards - 1 - position;
    // if the card does not exist, return null
    if(reversePosition<0){
      Card invalid = null;
      return invalid;
    }
    // save the chosen card's value and then reassign values to remaining cards
    Card end = cards[reversePosition];
    Card hold;
    for(int i = reversePosition; i<numOfCards-1; i++){
      cards[i] = cards[i+1];
    }
    numOfCards--;
    return end;
  }
  
  // a method to reset the deck to its original form
  public void restockDeck(){
    // reset all the values of the card deck
    this.cards = new Card[52];
    String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
    int i = 0;
    for(int suit = 0; suit<4; suit++) {
      for (int value = 13; value >=1; value--) {
        this.cards[i] = new Card(value, suits[suit]);
        i++;
      }
    }
    // reset numOfCards
    this.numOfCards = 52;
  }
}