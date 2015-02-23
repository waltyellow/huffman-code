import java.nio.file.*;
import java.util.*;

public class ByteCounter{
  
  //The count value
  private Integer[] count;
  
  //The byte's order, use this to rank the order of bytes, stored inside is the order of their "integer equivalence"
  //"integer equivalance" is by converting bytes to positive, 0-127 stays, -128 to -1 are mapped to 128-255
  private Integer[] byteOrder;
  
  //Two comparators 
  private decCompare dec;
  private incCompare inc;
  
  //Count of existing elements
  private int nonZeroCountGrandTotal;
  
  //Takes one input
  public ByteCounter(byte[] input){
    
    //initialize all the fields
    nonZeroCountGrandTotal = 0;
    count = new Integer[256];
    byteOrder = new Integer[256];
    dec = new decCompare();
    inc = new incCompare();
    for (Integer i = 0; i < 256; i++){
      count[i] = 0;
      byteOrder[i] = i;
    }
    
    //count the occurance of each bytes, like hashing
    
    for(int i = 0; i < input.length; i++){
      count[byteToInt(input[i])]++; 
    }
    
    //Count the amout of existing elements
    for (int i = 0; i < 256; i++){
      if (count[byteOrder[i]] > 0)
        nonZeroCountGrandTotal++;
    }
    
  }
  
  public ByteCounter(String inputFile) throws java.io.IOException{
    //Read the file
    
    Path path = FileSystems.getDefault().getPath(inputFile);
    byte[] input = new byte[0];
    input = Files.readAllBytes(path);
    
    //Same implementation of previous, using bytes
    nonZeroCountGrandTotal = 0;
    count = new Integer[256];
    byteOrder = new Integer[256];
    dec = new decCompare();
    inc = new incCompare();
    for (Integer i = 0; i < 256; i++){
      count[i] = 0;
      byteOrder[i] = i;
    }
    
    //count
    
    for(int i = 0; i < input.length; i++){
      count[byteToInt(input[i])]++; 
    }
    
    for (int i = 0; i < 256; i++){
      if (count[byteOrder[i]] > 0)
        nonZeroCountGrandTotal++;
    } 
  }
  
  
  
  //retrieve count of bytes, by transfering them to integer and find their spots in array
  public int getCount(byte b){
    return (int)count[byteToInt(b)];
  }
  
  //getCount for each
  public int[] getCount(byte[] b){
    int[] output = new int[b.length];
    int i = 0;
    while (i < b.length){
      output[i] = getCount(b[i]);
      i++;
    }
    return output;
  }
  
  //Set order: implemented by defining different comparators
  public void setOrder(String order) throws IllegalArgumentException{
    
    if (order.equals("byte")){
      java.util.Arrays.sort(byteOrder,null);
      return;
    }
    
    if (order.equals("countInc")){
      java.util.Arrays.sort(byteOrder,(Comparator<Integer>)inc);
      return;
    }
    
    if (order.equals("countDec")){
      java.util.Arrays.sort(byteOrder,(Comparator<Integer>)dec);
      return;
    }
    
    throw new IllegalArgumentException();
  }
  
  //get all non zero implements, use a full array first, then shrink it down
  public byte[] getElements(){
    Integer[] nonZeroIndex = new Integer[256];
    int nonZeroCount = 0;
    for (int i = 0; i < 256; i++){
      if (count[byteOrder[i]] > 0){
        nonZeroIndex[nonZeroCount] = byteOrder[i];
        nonZeroCount++;
      }
    }
    
    byte[] output = new byte[nonZeroCount];
    for (int i = 0; i < nonZeroCount; i++){
      output[i] = (byte)(int)nonZeroIndex[i];
    }
    return output;
  }
  
  //transfer such to string
  public String toString(){
    StringBuilder so = new StringBuilder();
    int nonZeroCount = 0;
    for (int i = 0; i < 256; i++){
      if (count[byteOrder[i]] > 0){
        so.append(""+(int)byteOrder[i]+":"+count[byteOrder[i]]);
        nonZeroCount++;
        if (nonZeroCount < nonZeroCountGrandTotal)
          so.append(" ");
      }
    }
    return so.toString();
  }
  
  //formatted string
  public String toString(String format){
    String s;
    s = "char";
    if (format.equals(s)){
      StringBuilder so = new StringBuilder();
      int nonZeroCount = 0;
      for (int i = 0; i < 256; i++){
        if (count[byteOrder[i]] > 0){
          so.append(""+(int)byteOrder[i]+":"+count[byteOrder[i]]);
          nonZeroCount++;
          if (nonZeroCount < nonZeroCountGrandTotal)
            so.append(" ");
        }
      }
      return so.toString();
    }
    else
      return this.toString();
  }
  //-------------
  //helper methods
  public static Integer byteToInt(byte b){
    return (b+256)%256;
  }
  
  public static byte intToByte(Integer i){
    if (i > 127) 
      return (byte)(int)(i-256);
    return (byte)(int)i;
  }
  
//helpers, comparator class, to decide the increasing order
  
  
  public class incCompare implements Comparator<Integer>{
    
    public int compare(Integer o1, Integer o2){
      if (count[o1]>count[o2])
        return 1;
      if (count[o1]<count[o2])
        return -1;
      if (count[o1] == count[o2])
        if (o1 < o2)
        return -1;
      else
        if (o1 > o2)
        return 1;
      return 0;
    }
    
    public boolean equal(Integer o1, Integer o2){
      return false;
    }
  }
  
  //helpers, comparator class, to decide the decending order 
  
  public class decCompare implements Comparator<Integer>{
    
    public int compare(Integer o1, Integer o2){
      if (count[o1]<count[o2])
        return 1;
      if (count[o1]>count[o2])
        return -1;
      if (count[o1] == count[o2])
        if (o1 < o2)
        return -1;
      else
        if (o1 > o2)
        return 1;
      return 0;
    }
    
    public boolean equal(Integer o1, Integer o2){
      return false;
    }
    
  }
  
}