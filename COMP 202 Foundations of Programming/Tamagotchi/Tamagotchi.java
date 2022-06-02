/*
 * Holly Sandvold 260788799
 */

import java.io.IOException; 
import java.util.ArrayList; 
import java.util.Random; 
import java.text.DecimalFormat;
 
public class Tamagotchi { 
  public static void main(String[] args){
  }
  
    // static attributes 
    private static final double MAX_ENERGY = 10; 
    private static Random r = new Random(); 
    
    // additional attributes 
    private String name;
    private int level;
    private double energy;
    private int experience;
    private int numOfMeals;
    private ArrayList<Toy> toys;
    
    // first constructor
    public Tamagotchi(String name, int level, double energy,
                      int experience,int numOfMeals, ArrayList<Toy> toys){
      // initialize immutable reference types
      this.name = name;
      this.level = level;
      this.energy = energy;
      this.experience = experience;
      this.numOfMeals = numOfMeals;
      
      // create a new ArrayList
      ArrayList<Toy> newToys = new ArrayList<Toy>();
      for(int i = 0; i<toys.size(); i++){
        newToys.add(toys.get(i));
      }
      this.toys = newToys;
    }
    
    // second contructor: takes only the name of the tamagotchi
    public Tamagotchi(String name){
      this.name = name;
      this.level = 1;
      this.energy = MAX_ENERGY;
      this.experience=0;
      this.numOfMeals=0;
      ArrayList<Toy> generated = new ArrayList<Toy>();
      Toy randomToy = new Toy();
      generated.add(randomToy);
      this.toys = generated;
    }
    
    //get methods
    public String getName(){
      return this.name;
    }
    
    public int getLevel(){
      return this.level;
    }
    
    public double getEnergy(){
      return this.energy;
    }
    
    public int getXp(){
      return this.experience;
    }
    
    public int getNumOfMeals(){
      return this.numOfMeals;
    }
    
    public ArrayList<Toy> getToys(){
       ArrayList<Toy> tempToys = new ArrayList<Toy>();
      for(int i = 0; i<this.toys.size(); i++){
        tempToys.add(this.toys.get(i));
      }
      return tempToys;
    }
 
    private void levelUp(){
      if(this.experience>=50*this.level*(this.level+1)/2){
        this.level++;
        this.numOfMeals=0;
        Toy additional = new Toy();
        this.toys.add(additional);
        System.out.println("Your tamagotchi has leveled up to level "+this.level+"!");
        System.out.println(additional+" has joined you!");
      }
    }
    
    // a play method to earn experience
    public void play(int mode){
      // set up a temp toy
      Toy toy = new Toy();
      // if there are no toys or if the tamagotchi doesn't have enough energy,
      // throw an exception
      if(toys==null || toys.size()==0 || energy<2){
        throw new IllegalStateException("Your tamagotchi is unable to play");
      } 
      
      // assign a toy depending on the mode
      else if (mode == 1) {
        toy = Toy.findBestToy(toys);
      } else if (mode == 2){
        toy = toys.get(r.nextInt(toys.size()));
      } else {
        return;
      }
      
      // update the experience
      int toyXp = toy.getXp();
      this.experience = toyXp + this.experience;
      
      // update the energy
      double random = toyXp/20.0 + ((toyXp/20.0+0.5)-toyXp/20.0) * r.nextDouble();
      this.energy = energy - random;

      // print statements 
      DecimalFormat df = new DecimalFormat("#0.00");
      System.out.println(this.name+" played with "+ toy+" and earned "+toyXp+" xp.");
      System.out.println(this.name+" now has "+this.experience+" xp, and "+
                         df.format(this.energy) +" energy.");
      levelUp();
    }
    
    // a method to feed the tamagotchi
    public void feed(){
      if(this.energy<1 || numOfMeals>=2*this.level){
        throw new IllegalStateException(this.name + " cannot eat right now.");
      }
      double energyGained = r.nextDouble() * 0.5;
      int experienceGained = r.nextInt(3) +1 ;
      this.energy = this.energy + energyGained;
      this.experience = this.experience+experienceGained;
      System.out.println("Nom nom nom");
      DecimalFormat df = new DecimalFormat("#0.00");
      System.out.println(this.name + " now has "+this.experience+" xp and " + df.format(this.energy)+" energy.");
      this.numOfMeals++;
      this.levelUp();
    }
    
    // a method to put the tamagotchi to sleep and reset its energy
    public void sleep(){
      System.out.println("Goodnight "+this.name+"!");
      this.energy = MAX_ENERGY;
    }
    
    // print method 
    public String toString(){
      DecimalFormat df = new DecimalFormat("#0.00");
      return ("Name: "+this.name+"\nLevel: "+this.level+"\nEnergy: "+df.format(this.energy)+
              "\nXP: " + this.experience+"\nMeals: "+this.numOfMeals+"\nToys: "+this.toys);
    }
} 
