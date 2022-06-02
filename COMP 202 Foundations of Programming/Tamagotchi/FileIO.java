/*
 * Holly Sandvold 260788799
 */
import java.io.*;
import java.util.ArrayList;

public class FileIO{
  
  public static void main(String[] args){
  }
  
  // a method to get an arraylist of toys given a filename
  private static ArrayList<Toy> loadToys(String filename) throws IOException {
        // go through the file of toys and add each toy to the ArrayList
    // until the reader reaches a null line
    try {
      FileReader frToys = new FileReader(filename);
      BufferedReader brToys = new BufferedReader(frToys);
      ArrayList<Toy> toyFile = new ArrayList<Toy>();
      String toyInput = brToys.readLine();
      while(toyInput!=null){
        Toy temp = Toy.createToy(toyInput);
        toyFile.add(temp);
        toyInput = brToys.readLine();
      }
      brToys.close();
      frToys.close();
      return toyFile;
    } catch (IllegalArgumentException e) {
      System.out.println("The format of your file is incorrect");
      ArrayList<Toy> empty = new ArrayList<Toy>();
      return empty;
    }
  }
  
  // a method to make a new file of toys
  private static boolean saveToys(ArrayList<Toy> toys, String filename) {
    try {
      FileWriter fwToys = new FileWriter(filename);
      BufferedWriter bwToys = new BufferedWriter(fwToys);
      ArrayList<Toy> toyWriter = toys;
      for(int i = 0; i<toyWriter.size(); i++) {
        Toy save = toyWriter.get(i);
        bwToys.write(save.getName()+"\t" + save.getColor() + "\t" + 
                     save.getType()+"\t" + save.getXp()+"\t\n");
      }
      bwToys.close();
      fwToys.close();
      return true;
    } catch (IOException e){
      System.out.println("Please use a valid filename.");
      return false;
    }
  }
  
  // a method that uses a file to create a tamagotchi
  public static Tamagotchi loadTamagotchi(String filename) throws IOException {
    // read given file and assign values to be used for the tamagotchi
    FileReader fr = new FileReader(filename);
    BufferedReader br = new BufferedReader(fr);
    String name = br.readLine();
    int level = Integer.parseInt(br.readLine());
    double energy = Double.parseDouble(br.readLine());
    int experience = Integer.parseInt(br.readLine());
    int numOfMeals = Integer.parseInt(br.readLine());
    String fileOfToys = br.readLine();
    
    ArrayList<Toy> toyFile = loadToys(fileOfToys);
    
    // create tamagotchi
    Tamagotchi load = new Tamagotchi(name, level, energy, experience, numOfMeals, toyFile);
    
    // return completed tamagotchi
    return load;
  }
  
  public static boolean saveTamagotchi(Tamagotchi tama, String filenameTama,
                                    String filenameToys){
    try {
      // in a new file, save the tamagotchi's attributes
      FileWriter fw = new FileWriter(filenameTama);
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write(tama.getName()+"\n");
      bw.write(tama.getLevel()+"\n");
      bw.write(Double.toString(tama.getEnergy())+"\n");
      bw.write(tama.getXp()+"\n");
      bw.write(tama.getNumOfMeals()+"\n");
      bw.write(filenameToys+"\n");
      bw.close();
      fw.close();
      
      // use the saveToys method to save the toys in a seperate file
      if (!saveToys(tama.getToys(), filenameToys)) {
        return false;
      }
      return true;
    } catch (IOException e) {
      System.out.println("Something went wrong with your file.");
      return false;
    }
        
      
  }
}