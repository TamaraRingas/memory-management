/** The class OS1Assignment converts virtual memory addresses to physical memory addresses using a page table.
 * It reads in a text file with a sequence of binary virtual addresses and converts them to binary physical addresses. 
 * It then converts these to hexadecimal format and writes them to a file called output-OS1.txt.
 * @author Tamara Ringas (RNGTAM002).
*/
 
import java.util.*;
import java.io.*;
import java.lang.Math;

public class OS1Assignment {
   //Page-to-frame mapping 
   static int[] mapping = new int[]{2,4,1,7,3,5,6};
   static ArrayList<String> stringArray = new ArrayList<String>();
   
   /**  
    * Traverse the input file with an InputStream and load each line into an Arraylist as a binary String.
    * @param filename the input sequence file to be read.
    */
   public static void readFile(String filename) {
      byte[] bytes = new byte[8];
      try {
         InputStream inputStream = new FileInputStream(filename);
         //While there are still entries in the file, traverse file and read 8 bytes into a byte array each time
         while (inputStream.read(bytes) != -1) { 
            //Convert the byte array to a binary String using a StringBuilder.   
            StringBuilder sb = new StringBuilder(8);
            String binaryString;
            String pageBitsToAdd = "0000";  
            
            for( int i = 0; i < 8; i++ ) {
               sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
            }
            binaryString = sb.toString();
            //Concatenate with 0's so that address has 12 bits.
            String binaryAddress = pageBitsToAdd.concat(binaryString);
            stringArray.add(binaryAddress);
            //System.out.println(binaryAddress);
            
         }
        inputStream.close();
      }
      catch (IOException e) { //Catch input exceptions. 
         e.printStackTrace();
      }
   }
   
   /**
   public int binaryToDecimal(long binary) {
      //Variable to store converted number.
      int decimal = 0; 
      int x = 0;
      
      while (binary > 0) {
            //Get digits by taking remainder on dividing by 10 and multiplying by increasing integral powers of 2
            decimal += Math.pow(2, x++) * (binary % 10);
            //Remove the last digit on division by 10
            binary /= 10; 
      }
      return decimal;
   }*/
   
   public static int binaryStringtoDecimal(String binary) {
         int decimal = Integer.parseInt(binary, 2); 
         return decimal;
   }
   
   public static String decimalToBinaryString(int decimal) {
      return Integer.toBinaryString(decimal);
   }
   
   //public long getOffset(long virtualAddress) {
   
   //}
   
   //public int getPhysicalAddress(int virtualAddress) {
   
   //}
   
   
   public static String decimalToHex(int decimal) {
        //Convert the integer to a hex string using toHexString() method
        String hex = Integer.toHexString(decimal);
        hex = hex.toUpperCase();
        return hex;
    }
   
   public static void main(String [] args){
      readFile("OS1testsequence");
      for (int y =0; y < stringArray.size(); y++) {
         String virtualAddress = stringArray.get(y);
         String pageBits = (stringArray.get(y)).substring(0,6);
         String offset = (stringArray.get(y)).substring(5);
         //System.out.println(offset);
         int pageNumber = binaryStringtoDecimal(pageBits);
         int frameNumber = mapping[pageNumber];
         String physical = decimalToBinaryString(frameNumber);
         
      }
      
   }
}