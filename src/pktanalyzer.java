import java.awt.image.Raster;
import java.io.*;
import java.lang.Integer;
import java.lang.reflect.Array;
import java.util.Scanner;

import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.*;
import java.math.*;
import java.net.ProtocolException;
import java.util.Arrays;

public class pktanalyzer {
	private static String hexStr =  "0123456789abcdef"; 
	
	public static void main(String[] args) {
				
		while (true) {
			if (args[0] != null) {
				try {
					byte[] rawData = new byte[2000];
					rawData = ReadFile(args[0]);
					
					String[] hexdata = new String[2000];
					hexdata = BytetoString(rawData);
					
					EthenetHeader eHeader = new EthenetHeader(rawData, hexdata);
					eHeader.PrintResult();
					
					IPHeader ipHeader = new IPHeader(rawData, hexdata);
					ipHeader.PrintResult();
			
					if ((rawData[23]&0xff) == 6) {						
						TCPHeader tcpHeader = new TCPHeader(rawData, hexdata);
						tcpHeader.PrintResult();
						break;
					}
					else if((rawData[23]&0xff) == 17) {
						UDPHeader udpHeader = new UDPHeader(rawData, hexdata);
						udpHeader.PrintResult();
						break;
					}
					else if ((rawData[23]&0xff) == 1){
						ICMPHeader icmpHeader = new ICMPHeader(rawData, hexdata);
						icmpHeader.PrintResult();
						break;
					}

				}
				catch (Exception e) {
					System.out.println(e);					
				}				
			}		
		}
	}

	
	public static byte[] ReadFile(String fileName) {
		File file = new File(fileName);
		FileInputStream in = null;
		//System.out.println(file.length());
		byte[] content = new byte[(int)file.length()];
		try {
			in = new FileInputStream(file);
			in.read(content);			
			in.close();
			return content;
		}		
		catch (IOException e){
			e.printStackTrace();
			return null;
		}
	}
	public static int UnsignedBytetoInt(byte bt) {		
		return bt & 0xFF;
	}
	public static String InttoHex(int num) {		
		return Integer.toHexString(num);
	}
	
	public static String[] BytetoString(byte[] btarr) {
		String[] strarr = new String[btarr.length];
		String hex = "";
		for (int i = 0; i < btarr.length; i++) {
			hex = String.valueOf(hexStr.charAt((btarr[i]&0xF0)>>4));
			hex += String.valueOf(hexStr.charAt(btarr[i]&0x0F));
			strarr[i] = hex;
		}
		return strarr;
	}
}


