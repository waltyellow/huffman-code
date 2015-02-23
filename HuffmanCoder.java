import java.nio.file.*;
import java.util.*;
import java.io.IOException;
import java.io.FileNotFoundException;

public class HuffmanCoder{
  HuffmanCode coder;
  byte[] raw;
  String outFile;
  
  //The constructor
  public HuffmanCoder(String s1, String s2) throws IOException{
    coder = new HuffmanCode(s1);
    Path path = FileSystems.getDefault().getPath(s1);
    byte[] input = new byte[0];
    try{
      input = Files.readAllBytes(path);
    }
    catch (java.io.IOException e){
    }
    finally 
    {     
    }
    raw = input;
    outFile = s2;
  }
  
  //The process of compression
  public void compress() throws IOException{
    BinaryWriter writer = new BinaryWriter(outFile);
    for (int i = 0; i < raw.length; i++){
      writer.writeBinaryArray(coder.code(raw[i]));
    }
    writer.close();  
  }
}