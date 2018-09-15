/* CSCI- 651 Computing Network
 * 
 * Project 1
 * 
 * Author: Zizhun Guo
 * 
 * Part 1/5 (main method)
 * */
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
				
		while (true) {                                                                          // Keep running program until gets input from user
			if (args[0] != null) {
				try {
					byte[] rawData = new byte[2000];											// An byte array extracted from the Binary File
					rawData = ReadFile(args[0]);												  
					
					String[] hexdata = new String[2000];
					hexdata = BytetoString(rawData);											// A sting array contains hex format of bytes translated from the raw data array (Used for printing)
					
					EthenetHeader eHeader = new EthenetHeader(rawData, hexdata);                // Ethernet header class instance
					eHeader.PrintResult();														// Print out the Ethernet Header
					
					IPHeader ipHeader = new IPHeader(rawData, hexdata);							// IP Header class
					ipHeader.PrintResult();														// Print out the IP Header
			
					if ((rawData[23]&0xff) == 6) {												// Based on Protocol section from IP header to conclude the type of next header
						
						TCPHeader tcpHeader = new TCPHeader(rawData, hexdata);					// TCP Header class instance
						tcpHeader.PrintResult();												// Print out the TCP Header
						break;
					}
					else if((rawData[23]&0xff) == 17) {
						UDPHeader udpHeader = new UDPHeader(rawData, hexdata);					// UDP Header class instance
						udpHeader.PrintResult();												// Print out the UDP Header
						break;
					}
					else if ((rawData[23]&0xff) == 1){
						ICMPHeader icmpHeader = new ICMPHeader(rawData, hexdata);				// ICMP Header class instance
						icmpHeader.PrintResult();												// Print out the ICMP Header
						break;
					}

				}
				catch (Exception e) {
					System.out.println(e);					
				}				
			}		
		}
	}
		/*
	   * This method inputs a string of file's name.
	   * @return The byte array of data.
	   * @exception IOException Unknown source of file
	   * @see IOException
	   	*/
	
	public static byte[] ReadFile(String fileName) {											// Function for save the data from bin file
		File file = new File(fileName);
		FileInputStream in = null;
		//System.out.println(file.length());
		byte[] content = new byte[(int)file.length()];											//
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
	
	/*
	   * This method inputs a single byte.
	   * @return a transfered unsigned integer.
	   	*/
	public static int UnsignedBytetoInt(byte bt) {		
		return bt & 0xFF;
	}
	
	/*
	   * This method inputs a single integer.
	   * @return a transfered string to represent Hex.
	   	*/
	public static String InttoHex(int num) {		
		return Integer.toHexString(num);
	}
	
	/*
	   * This method inputs an array of byte.
	   * @return an array of String, each element represents the hex format of a single byte.
	   	*/
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


