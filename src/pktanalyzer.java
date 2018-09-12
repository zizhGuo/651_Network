import java.io.*;
import java.lang.Integer;
import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.*;
import java.math.*;
import java.util.Arrays;

public class pktanalyzer {
	private static String hexStr =  "0123456789abcdef"; 
	
	public static void main(String[] args) {
		//Scanner scanner = new Scanner(System.in);
		//String fileName = scanner.nextLine();
		//scanner.close();
		
		String fileLocation = "C:\\Users\\William\\eclipse-workspace\\651_Network\\bin\\new_udp_packet1.bin";
		
		byte[] rawData = new byte[2000];
		rawData = ReadFile(fileLocation);
		
		String[] hexdata = new String[2000];
		hexdata = BytetoString(rawData);
		
		EthenetHeader eHeader = new EthenetHeader(rawData, hexdata);
		eHeader.PrintResult();

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
class EthenetHeader{
	byte[] rawdata = null;
	String[] dstmac = new String[6];
	String[] srcmac = new String[6];
	String[] eth_type;
	
	public EthenetHeader(byte[] rawdataHeader, String[] HexdataHeader) {
		
		this.rawdata = rawdataHeader;
		this.dstmac = Arrays.copyOfRange(HexdataHeader, 0, 6);
		this.srcmac = Arrays.copyOfRange(HexdataHeader, 6, 12);
		this.eth_type = Arrays.copyOfRange(HexdataHeader, 12, 14);
	}
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
class IPHeader{
	byte[] rawdata = null;
	String[] dstmac = new String[6];
	String[] srcmac = new String[6];
	String[] eth_type;
	
	public IPHeader(byte[] rawdataHeader, String[] HexdataHeader) {
		
		this.rawdata = rawdataHeader;
		this.dstmac = Arrays.copyOfRange(HexdataHeader, 0, 6);
		this.srcmac = Arrays.copyOfRange(HexdataHeader, 6, 12);
		this.eth_type = Arrays.copyOfRange(HexdataHeader, 12, 14);
	}
	public void PrintResult() {
		System.out.println("ETHER: ----- Ether Header ----- ");
		System.out.println("ETHER:                          ");
		System.out.println("ETHER:  Packet size = " + rawdata.length + "bytes");
		System.out.println("ETHER:  Destination = " + dstmac[0] + ":"+ dstmac[1] + ":"+ dstmac[2] + ":"+ dstmac[3] + ":"+ dstmac[4] + ":"+ dstmac[5] + ",");
		System.out.println("ETHER:  Source      = " + srcmac[0] + ":"+ srcmac[1] + ":"+ srcmac[2] + ":"+ srcmac[3] + ":"+ srcmac[4] + ":"+ srcmac[5] + ",");
		System.out.println("ETHER:  Ethertype = " + eth_type[0] + eth_type[1]);
		System.out.println("ETHER:");
	}
}
