/* CSCI- 651 Computing Network
 * 
 * Project 1
 * 
 * Author: Zizhun Guo
 * 
 *  Part 2/5 (Ethenet Header)
 * */

import java.util.Arrays;

class EthenetHeader{
	byte[] rawdata = null;
	String[] hexdata = null;
	String[] dstmac = new String[6];          // Destination
	String[] srcmac = new String[6];          // Source
	String[] eth_type;                        // 
	
	/*
	   * This method inputs an array of bytes, and an array of String.
	   * As of the construtor.
	   	*/
	public EthenetHeader(byte[] rawdataHeader, String[] HexdataHeader) {
		
		this.rawdata = rawdataHeader;
		hexdata = HexdataHeader;
		this.dstmac = Arrays.copyOfRange(HexdataHeader, 0, 6);
		this.srcmac = Arrays.copyOfRange(HexdataHeader, 6, 12);
		this.eth_type = Arrays.copyOfRange(HexdataHeader, 12, 14);
	}
	/*
	   * This method is for printing out the info
	   * 
	   	*/
	public void PrintResult() {
		System.out.println("ETHER: ----- Ether Header ----- ");
		System.out.println("ETHER:                          ");
		System.out.println("ETHER:  Packet size = " + rawdata.length + " bytes");
		System.out.println("ETHER:  Destination = " + dstmac[0] + ":"+ dstmac[1] + ":"+ dstmac[2] + ":"+ dstmac[3] + ":"+ dstmac[4] + ":"+ dstmac[5] + ",");
		System.out.println("ETHER:  Source      = " + srcmac[0] + ":"+ srcmac[1] + ":"+ srcmac[2] + ":"+ srcmac[3] + ":"+ srcmac[4] + ":"+ srcmac[5] + ",");
		System.out.println("ETHER:  Ethertype = " + eth_type[0] + eth_type[1]);
		System.out.println("ETHER:");
	}	
}