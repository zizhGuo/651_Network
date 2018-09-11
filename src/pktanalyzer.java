import java.io.*;
import java.util.Scanner;
import java.util.*;
import java.math.*;
public class pktanalyzer {

	public static void main(String[] args) {
		//Scanner scanner = new Scanner(System.in);
		//String fileName = scanner.nextLine();
		//scanner.close();
		String fileLocation = "C:\\Users\\William\\eclipse-workspace\\651_Network\\bin\\new_tcp_packet1.bin";
		//System.out.println(fileName);
		ReadFile2(fileLocation);
		
		
//		try {
//			byte[] content = new byte[2];
//			content = readFileToByteArray(fileLocation);
//			String info = content.toString();
//			System.out.println(info);
//			}
//		catch (IOException e) {
//			e.printStackTrace();		
//		}
		//ReadFile(fileName);

	}
	public static void ReadFile2(String fileName) {
		File file = new File(fileName);
		FileInputStream in = null;
		DataInputStream din = null;
		byte[] content = new byte[1024];
		try {
			in = new FileInputStream(file);
			in.read(content);
			//din = new DataInputStream(in);
			//din.read(content);
            for (int i = 0; i < content.length; i++) {
            	System.out.println((content[i]&0xF0)>>4);
            	System.out.println(content[i]&0x0F); 
            }           
		}
		
		catch (IOException e){
			e.printStackTrace();	
		}		
		
	}
	
	public static void ReadFile(String fileName) {
		File file = new File(fileName);
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		FileInputStream in = null;
		try {                    
            in = new FileInputStream(file);            
            in.read(filecontent);
            for (int i = 0; i < filecontent.length; i++) {
            	System.out.println(filecontent[i]);            	
            }
            
			//InputStreamReader read = new InputStreamReader();            
            //BufferedReader buff = new BufferedReader(read);
                        
            //byte[] content = new byte[1024];           
            //in.read(content);
            //while ()
            String[] fileContentArr = new String(filecontent).split("\r\n");
            System.out.println(fileContentArr);
//            int tempbyte;
//            while ((tempbyte = in.read()) != -1) {
//            	System.out.println(tempbyte);
//            }
            //}            
            //System.out.write(content);
            in.close();
		}
		catch (IOException e){
			e.printStackTrace();	
		}			
	}
	public static void copy(InputStream input, 
	        OutputStream output) throws IOException{
	    byte[] buf = new byte[4096];
	    int bytesRead = 0; 
	    while((bytesRead = input.read(buf))!=-1){
	        output.write(buf, 0, bytesRead);
	    }
	}
	public static byte[] readFileToByteArray(String fileName) throws IOException{
	    InputStream input = new FileInputStream(fileName);
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    try{
	        copy(input, output);
	        return output.toByteArray();
	    }finally{
	        input.close();
	    }
	}

}
