import java.util.*;
public class HuffmanCode{
  
  private LinkedList<HuffmanNode> list;
  private HuffmanNode root;
  
  //Same implementation for constructors as HuffmanList, except the bottom few lines
  public HuffmanCode(byte[] b){
    
    list = new LinkedList<HuffmanNode>();
    
    ByteCounter analyze = new ByteCounter(b);
    
    analyze.setOrder("countInc");
    
    byte[] byteValue = analyze.getElements();
    
    int[] count = analyze.getCount(byteValue);
    
    for(int i = 0; i < byteValue.length; i++){
      if (count[i]>0)
        list.add(new HuffmanNode(byteValue[i], count[i]));
    }
    
    //Instead of stopping, we collapse them to trees here
    letsMakeTheTree();
    //And define the root
    root = list.getFirst();
    //And find coding for everyone
    root.code = new boolean[0];
    code(root); 
  }
  
  //Same implementation as the other constructor
  public HuffmanCode(String s) throws java.io.IOException{
    
    list = new LinkedList<HuffmanNode>();
    
    ByteCounter analyze = new ByteCounter(s);
    
    analyze.setOrder("countInc");
    
    byte[] byteValue = analyze.getElements();
    
    int[] count = analyze.getCount(byteValue);
    
    for(int i = 0; i < byteValue.length; i++){
      if (count[i]>0)
        list.add(new HuffmanNode(byteValue[i], count[i]));
    }
    letsMakeTheTree();
    root = list.getFirst();
    root.code = new boolean[0];
    code(root); 
  }
  
  //Allowed unordered list, or not forced ordered list, to be encoded
  public HuffmanCode(byte[] b, int[] count) throws IllegalArgumentException{
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
    letsMakeTheTree();
    root = list.getFirst();
    root.code = new boolean[0];
    code(root); 
  }
  
  private HuffmanNode letsMakeTheTree(){
    while (list.size() > 1){
      HuffmanNode insert = merge();
      int i = 0;
      while (i < list.size()){
        if ((list.get(i).count) < (insert.count))
          i++;
        else{
          //find someone that's larger or equal
          list.add(i,insert);
          break;
        }
      }
      if (i == list.size())
        list.add(insert);
    }
    return list.getFirst();
  }
  
  private HuffmanNode merge(){
    //must have two nodes or more
    HuffmanNode left = list.pollFirst();
    HuffmanNode right = list.pollFirst();
    HuffmanNode node = new HuffmanNode((byte)0,left.count+right.count);
    node.left = left;
    node.right = right;
    node.isLeaf = false;
    return node;
  }
  
  public Iterator<HuffmanNode> iterator(){
    return this.list.iterator();
  }
  
  
  //These two methods, find the node by traversing the tree, and then read its pre-determined array values.
  public boolean[] code(byte b) throws IllegalArgumentException{
    return find(b,root);
  }
  
  
  private boolean[] find(byte b, HuffmanNode node) throws IllegalArgumentException{
    if ((node.left == null) && (node.right == null)){
      if (node.b == b)
        return node.code;
    }else{
      if (find(b,node.left) != null)
        return find(b,node.left);
      if (find(b,node.right) != null)
        return find(b,node.right);
    }
    if (node == root)
      throw new IllegalArgumentException();
    return null;
  }
  
  //This is the initial process that determines the boolean coding for each leave nodes
  private void code(HuffmanNode node){
    if ((node.left == null) && (node.right == null)){   
      return;
    }else{     
      node.left.code = new boolean[node.code.length+1];
      node.right.code = new boolean[node.code.length+1];
      //copy parents
      for (int i = 0; i < node.code.length; i++){
        node.left.code[i] = node.code[i];
        node.right.code[i] = node.code[i];
      }
      node.left.code[node.code.length] = false;
      node.left.code[node.code.length] = true;
      code(node.left);
      code(node.right);
    }
  }
  
  
  //Implemented by recursively finding the byte and toString them
  public String codeString(byte b){
    StringBuilder so = new StringBuilder();
    boolean[] ref = code(b);
    for(int i = 0; i < ref.length; i++)
      if (ref[i])
      so.append(""+1);
    else
      so.append(""+0);
    return so.toString();
  }
  
  public String toString(){
    return dig(root);
  }
  
  //Dig: a helper method to find the bytes
  private String dig(HuffmanNode node){
    StringBuilder so = new StringBuilder();
    if ((node.left == null) && (node.right == null)){
      return ""+(int)node.b+": "+codeString(node.b)+("\n");
    }else{
      so.append(dig(node.left));
      so.append(dig(node.right));
    }
    return so.toString();
  }
  
  
}