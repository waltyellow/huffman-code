public class HuffmanNode{
  public byte b;
  public int count;
  public boolean[] code;
  public HuffmanNode left;
  public HuffmanNode right;
  public boolean isLeaf;
  
  public HuffmanNode(byte b, int c){
    this.left = null;
    this.right = null;
    this.count = c;
    this.b = b;
    this.isLeaf = true;
  }
}