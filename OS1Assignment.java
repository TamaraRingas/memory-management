/** The class OS1Assignment converts virtual memory addresses to physical memory addresses using a page table.
 * It reads in a text file with a sequence of binary virtual addresses and converts them to binary physical addresses. 
 * It then converts these to hexadecimal format and writes them to a file called output-OS1.txt.
 * @author Tamara Ringas (RNGTAM002).
*/
 
import java.util.*;
import java.io.*;
import java.lang.Math;

public class OS1Assignment {
   //Page-to-frame mapping.
   static int[] mapping = new int[]{2,4,1,7,3,5,6};
    
   //ArrayList to store addresses. 
   static ArrayList<String> stringArray = new ArrayList<String>();
   
   /**  
    * Traverse the input file with an InputStream and load each line into an Arraylist as a binary String.
    * @param filename - the input sequence file to be read.
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
            String pageBitsToAdd = "0000";  //Check here!!!!!!
            
            for( int i = 0; i < 8; i++ ) {
               sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
            }
            binaryString = sb.toString();
            //Concatenate with 0's so that address is 12 bits.
            String binaryAddress = pageBitsToAdd.concat(binaryString);
            //Add each address to an ArrayList to traverse later.
            stringArray.add(binaryAddress);      
         }
        inputStream.close();
      }
      //Catch input & output exceptions. 
      catch (IOException e) { 
         e.printStackTrace();
      }
   }
   
   /**
    *Convert binary String to an integer using the Integer parseInt method.
    *@param binary - The binary string to be converted. 
    *@return the int representation of the binary String. 
    */
   public static int binaryStringtoDecimal(String binary) {
         int decimal = Integer.parseInt(binary, 2); 
         return decimal;
   }
   
   /**
    *Convert int to a binary String using the Integer toBinaryString method.
    *@param decimal - The int to be converted. 
    *@return the binary String representation of the int value. 
    */
   public static String decimalToBinaryString(int decimal) {
      return Integer.toBinaryString(decimal);
   }
  
   /**
    *Convert int to a hexadecimal String using the Integer toHexString method.
    *@param decimal - The int to be converted. 
    *@return the hexadecimal String representation of the int value. 
    */
   public static String decimalToHex(int decimal) {
        String hex = Integer.toHexString(decimal);
        hex = hex.toUpperCase();
        return hex;
    }
    
   
   /**
    *Driver code runs the readFile method, performs translations on its output ArrayList and writes physical addresses to the output file. 
    */
   public static void main(String [] args){
      try {  
         //PrintWrite to write physical addresses to the output file. 
         PrintWriter outputStream = new PrintWriter(new FileWriter("output-OS1test.txt"));
         
         readFile("OS1testsequence");
         //Traverse the ArrayList of virtual address entries. 
         for (int y =0; y < stringArray.size(); y++) {
            //Get virtual address. 
            String virtualAddress = stringArray.get(y);
            
            //First 5 bits are the page bits.
            String pageBits = (stringArray.get(y)).substring(0,5);
            
            //Last 7 address bits represent the page offset. 
            String offset = (stringArray.get(y)).substring(5);
            
            //Get decimal value of page bits. 
            int pageNumber = binaryStringtoDecimal(pageBits);
            
            //Use page-to-frame mapping from Page Table to get frame number. 
            int frameNumber = mapping[pageNumber];
            
            //Get binary representation of frame.
            String physicalBinary = decimalToBinaryString(frameNumber);
            
            //Convert to a 5 bit String.
            if (physicalBinary.length() == 2) {
               physicalBinary = "000" + physicalBinary;
            }
            else {
               physicalBinary = "00" + physicalBinary;
            }
            
            //Add 5 bit String to beginning of offset to get physical address.
            String physicalAddress = physicalBinary.concat(offset);
            
            //Get decimal value of physical address. 
            int output = binaryStringtoDecimal(physicalAddress);
            
            //Convert to hexadecimal String for output. 
            String hex = decimalToHex(output);
            
            //Write output to file. 
            outputStream.println("0x" + "00000" + hex);
         }
         //Close the PrintWriter when finished. 
         outputStream.close();
      }
      //Catch input and output exceptions.
      catch(IOException e) {
         e.printStackTrace();
      }
   }
}