/* CSCI- 651 Computing Network
 * 
 * Project 1
 * 
 * Author: Zizhun Guo
 * 
 *  Part 3/5 (ICMP Header)
 * */
import java.awt.RadialGradientPaint;

public class ICMPHeader {
	byte[] rawdata = null;
	String[] hexdata = null;
	int typeIndex;							// Type
	String typeText;						// Type text for printing out
	int code;								// Code
	String checksum;						// Checksum
	
	/*
	   * This method inputs an array of bytes, and an array of String.
	   * As of the construtor.
	   	*/
	public ICMPHeader(byte[] rawdataHeader, String[] HexdataHeader) {
		this.rawdata = rawdataHeader;
		this.hexdata = HexdataHeader;
		this.typeIndex = (rawdataHeader[34]& 0xff);
		switch (typeIndex){
			case 0:
				typeText = "Echo answer";
				break;
			case 8:
				typeText = "Echo request";
				break;
			case 11:
				typeText = "time out";
				break;
			case 3:
				typeText = "Unreachable goal";
				break;
		}
		this.code = rawdataHeader[35] & 0xff;
		this.checksum = HexdataHeader[36] + HexdataHeader[37];	
	}
	/*
	   * This method is for printing out the info
	   * 
	   	*/
	public void PrintResult() {
		System.out.println("ICMP: ----- ICMP Header ----- ");
		System.out.println("ICMP:                          ");
		System.out.println("ICMP:  Type = " + typeIndex + " (" + typeText + ")");
		System.out.println("ICMP:  Code = " + code);
		System.out.println("ICMP:  Checksum =  0x" + checksum);
		System.out.println("ICMP:");
	}
}
