import java.util.*;
public class HuffmanList{
  
  private LinkedList<HuffmanNode> list;
  
  //Basic implementation
  public HuffmanList(byte[] b){
    
    list = new LinkedList<HuffmanNode>();
    //use a byte counter to analyse the data
    ByteCounter analyze = new ByteCounter(b);
    
    analyze.setOrder("countInc");
    
    byte[] byteValue = analyze.getElements();
    
    int[] count = analyze.getCount(byteValue);
    
    //Transfer the result to our linked list
    for(int i = 0; i < byteValue.length; i++){
      if (count[i]>0)
        list.add(new HuffmanNode(byteValue[i], count[i]));
    }
  }
  
  public HuffmanList(String s) throws java.io.IOException{
    
    list = new LinkedList<HuffmanNode>();
    
    ByteCounter analyze = new ByteCounter(s);
    //--- same implementation below this line, as bytecounter behaves the same
    analyze.setOrder("countInc");
    
    byte[] byteValue = analyze.getElements();
    
    int[] count = analyze.getCount(byteValue);
    
    for(int i = 0; i < byteValue.length; i++){
      if (count[i]>0)
        list.add(new HuffmanNode(byteValue[i], count[i]));
    }
  }
  
  //Allowed unordered linked list to exist
  public HuffmanList(byte[] b, int[] count) throws IllegalArgumentException{
    boolean[] existed = new boolean[256];
    int zero = 0;
    list = new LinkedList<HuffmanNode>();
    
    for(int i = 0; i < 256; i++){
      existed[i] = false;
    }   
    
    for(int i = 0; i < count.length; i++){
      if ((existed[ByteCounter.byteToInt(b[i])]) || ((count[i] < 0)))
        throw new IllegalArgumentException();
      else{
        existed[ByteCounter.byteToInt(b[i])] = true;
        if (count[i]>0)
          list.add(new HuffmanNode(b[i], count[i]));
        if (count[i] == 0)
          zero++;
      }
    } 
  }
  
  
  public Iterator<HuffmanNode> iterator(){
    return this.list.iterator();
  }
  
}