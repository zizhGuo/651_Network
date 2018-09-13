import java.awt.image.Raster;
import java.io.*;
import java.lang.Integer;
import java.lang.reflect.Array;
import java.util.Scanner;

import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.*;
import java.math.*;
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
					
					TCPHeader tcpHeader = new TCPHeader(rawData, hexdata);
					tcpHeader.PrintResult();
					break;
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
class EthenetHeader{
	byte[] rawdata = null;
	String[] hexdata = null;
	String[] dstmac = new String[6];
	String[] srcmac = new String[6];
	String[] eth_type;
	
	public EthenetHeader(byte[] rawdataHeader, String[] HexdataHeader) {
		
		this.rawdata = rawdataHeader;
		hexdata = HexdataHeader;
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
	String[] hexdata = null;
	
	int version;
	int hl;
	String tos;
	boolean delay = false;
	boolean throughout = false;
	boolean reliability = false;
	int tl;
	int id;
	boolean df = false;
	boolean mf = false;
	String flag;
	int fragOffset;
	int ttl;
	int protocol;
	String hc;
	String sa;
	String da;
	
	public IPHeader(byte[] rawdataHeader, String[] HexdataHeader) {
		
		this.rawdata = rawdataHeader;
		this.hexdata = HexdataHeader;
		this.version = (rawdataHeader[14]&0xF0) >> 4;
		this.hl = (rawdataHeader[14] & 0x0F) * 4;
		this.tos = HexdataHeader[15];
		int sev = (rawdataHeader[15] & 0x1F) >> 1;
		switch (sev) {
			case 8:
				this.delay = true;
				this.throughout = false;
				this.reliability = false;
				break;
			case 4:
				this.delay = false;
				this.throughout = true;
				this.reliability = false;
				break;
			case 2:
				this.delay = false;
				this.throughout = false;
				this.reliability = true;
				break;
		}
		this.tl = BytetoInt(rawdataHeader, 16);
		this.id = BytetoInt(rawdataHeader, 18);
		this.flag = HexdataHeader[20]; // Need to delete last 4 bits
		int flags = (rawdataHeader[20] & 0xe0) >>5;
		switch (flags) {
		case 0:
			this.df = false;
			this.mf = false;
			break;
		case 1:
			this.df = false;
			this.mf = true;
			break;
		case 2:
			this.df = true;
			this.mf = false;
			break;
		case 3:
			this.df = true;
			this.mf = true;
			break;
		}
		this.fragOffset = BytetoInt2(rawdataHeader, 20);
		this.ttl = rawdataHeader[22] & 0xff;
		this.protocol = rawdataHeader[23] & 0xff;
		this.hc = HexdataHeader[24]+HexdataHeader[25];
		this.sa = String.valueOf((rawdataHeader[26] & 0xff)) + "." + String.valueOf((rawdataHeader[27] & 0xff)) + "." + String.valueOf((rawdataHeader[28]  & 0xff))+ "." + String.valueOf((rawdataHeader[29] & 0xff));
		this.da = String.valueOf((rawdataHeader[30] & 0xff)) + "." + String.valueOf((rawdataHeader[31] & 0xff)) + "." + String.valueOf((rawdataHeader[32]  & 0xff))+ "." + String.valueOf((rawdataHeader[33] & 0xff));
		
	}
	private int BytetoInt(byte[] bt, int offset) {
		return (int)((bt[offset] & 0xFF) << 8) | (bt[offset + 1] & 0xFF);
		//return bt[offset] & 0xFF;
	}
	private int BytetoInt2(byte[] bt, int offset) {
		return (int)((bt[offset] & 0x1F) << 8) | (bt[offset + 1] & 0xFF);
		//return bt[offset] & 0xFF;
	}
	
	public void PrintResult() {
		System.out.println("IP: ----- IP Header ----- ");
		System.out.println("IP:                          ");
		System.out.println("IP:  Version = " + version );
		System.out.println("IP:  Header Length = " +hl + " bytes");
		System.out.println("IP:  Type of Service = 0x" + tos);
		System.out.println("IP:        xxx. .... = 0x00");
		if (delay && !throughout && !reliability) {
			System.out.println("IP:        ...1 .... = delay activated");
			System.out.println("IP:        .... 0... = normal throughout");
			System.out.println("IP:        .... .0.. = normal reliability");
		}
		else if (!delay && throughout && !reliability) {
			System.out.println("IP:        ...0 .... = normal delay");
			System.out.println("IP:        .... 1... = throughout activated");
			System.out.println("IP:        .... .0.. = normal reliability");
		}
		else if (!delay && !throughout && reliability) {
			System.out.println("IP:        ...0 .... = normal delay");
			System.out.println("IP:        .... 0... = normal throught");
			System.out.println("IP:        .... .1.. = reliability activated");
		}
		else if (!delay && !throughout && !reliability){
			System.out.println("IP:        ...0 .... = normal delay");
			System.out.println("IP:        .... 0... = normal throught");
			System.out.println("IP:        .... .0.. = normal reliability");
		}
		System.out.println("IP:  Total Length  = " + tl + " bytes");
		System.out.println("IP:  Identification = " + id);
		System.out.println("IP:  Flags = 0x" + hexdata[20]);
		if (!df && !mf) {
			System.out.println("IP:        .0.. .... = Ok to fragment");
			System.out.println("IP:        ..0. .... = last fragment");
		}
		else if (!df && mf) {
			System.out.println("IP:        .0.. .... = Ok to fragment");
			System.out.println("IP:        ..1. .... = ???Unknown???");
		}
		else if (df && !mf) {
			System.out.println("IP:        .1.. .... = do not fragment");
			System.out.println("IP:        ..0. .... = last fragment");
		}
		else {
			System.out.println("IP:        .1.. .... = do not fragment");
			System.out.println("IP:        ..1. .... = ???Unknown???");
		}
		System.out.println("IP:  Framment Offset = " + fragOffset + " bytes");
		System.out.println("IP:  Time to live = " + ttl + " seconds/hops");
		switch (protocol) {
			case 6:
				System.out.println("IP:  Protocol = " + protocol + " (TCP)");
				break;
			case 17:
				System.out.println("IP:  Protocol = " + protocol + " (UDP)");
				break;
			case 1:
				System.out.println("IP:  Protocol = " + protocol + " (ICMP)");
		}
		System.out.println("IP:  Header Checksum = 0x" + hc);
		System.out.println("IP:  Source address = " + sa);
		System.out.println("IP:  Destination address = " + da);		
		//System.out.println("IP:");
	}
}
class TCPHeader{
	byte[] rawdata = null;
	String[] hexdata = null;
	int sp;
	int dp;
	int sn;
	int an;
	int dataOffset;
	String Flags;
	boolean urg = false;
	boolean ack = false;
	boolean psh = false;
	boolean rst = false;
	boolean syn = false;
	boolean fin = false;
	int window;
	String checksum;
	String[] data;
	int up;
	
	public TCPHeader(byte[] rawdataHeader, String[] HexdataHeader) {
		this.rawdata = rawdataHeader;
		this.hexdata = HexdataHeader;
		this.sp = BytestoInt16(rawdataHeader, 34);
		this.dp = BytestoInt16(rawdataHeader, 36);
		this.sn = BytestoInt32(rawdataHeader, 38);
		this.an = BytestoInt32(rawdataHeader, 42);
		this.dataOffset = (rawdataHeader[46] & 0xf0 >> 4);
		this.Flags = HexdataHeader[47];
		int flags = rawdataHeader[47] & 0x3f;
		switch(flags) {
			case 24:
				this.urg = false;
				this.ack = true;
				this.psh = true;
				this.rst = false;
				this.syn = false;
				this.fin = false;
				break;
		}
		this.window = BytestoInt16(rawdataHeader, 48);
		this.checksum = HexdataHeader[50]+HexdataHeader[51];
		this.up = BytestoInt16(rawdataHeader, 52);
		this.data = new String[64];
		for (int i = 0; i < 64; i+=2) {
			this.data[i] = HexdataHeader[34 + i] + HexdataHeader[i+1+34];
		}
		
		
	}
	public int BytetoInt8(byte[] bt, int offset) {
		return bt[offset] & 0xff;
	}
	public int BytestoInt16(byte[] bt, int offset) {
		return (int)(((bt[offset] & 0xFF) << 8) | (bt[offset+1] & 0xFF));
	}
	public int BytestoInt32(byte[] bt, int offset) {
		return (int)(((bt[offset] & 0xff) << 24) | ((bt[offset+1] & 0xff) << 16) |((bt[offset+2] & 0xff) << 8) |(bt[offset+3] & 0xff)) ;
	}
	public void PrintResult() {
		System.out.println("TCP: ----- TCP Header ----- ");
		System.out.println("TCP:                          ");
		System.out.println("TCP:  Source port = " + sp );
		System.out.println("TCP:  Destination port = " + dp );
		System.out.println("TCP:  Sequence number = " + sn);
		System.out.println("TCP:  Acknowledgement number = " + an );
		System.out.println("TCP:  Data offset = " + dataOffset + " bytes");
		System.out.println("TCP:  Flags = 0x" + Flags);
		if (!urg && ack && psh && !rst && !syn && !fin) {
			System.out.println("TCP:        ..0. .... = No urgent pointer");
			System.out.println("TCP:        ...1 .... = Acknowledgement");
			System.out.println("TCP:        .... 1... = Push");
			System.out.println("TCP:        .... .0.. = No reset");
			System.out.println("TCP:        .... ..0. = No Syn");
			System.out.println("TCP:        .... ...0 = No Fin");
		}
		System.out.println("TCP:  Window = " + window);
		System.out.println("TCP:  Checksum = 0x" + checksum);
		System.out.println("TCP:  Urgent pointer" + up);
		System.out.println("TCP:  No options");
		System.out.println("");
		System.out.println("TCP:  Data: (first 64 bytes)");
		System.out.print("TCP:  ");
		for (int j = 0; j < 16; j+=2) {
			System.out.print(data[j]+" ");
		}
		System.out.print("           ");
		for (int j = 0; j < 16; j++) {
			System.out.print((char)BytetoInt8(rawdata, 36+j));
		}
		System.out.println("");
		System.out.print("TCP:  ");
		for (int j = 16; j < 32; j+=2) {
			System.out.print(data[j]+" ");
		}
		System.out.print("           ");
		for (int j = 16; j < 32; j++) {
			System.out.print((char)BytetoInt8(rawdata, 52+j));
		}
		System.out.println("");
		System.out.print("TCP:  ");
		for (int j = 32; j < 48; j+=2) {
			System.out.print(data[j]+" ");
		}
		System.out.print("           ");
		for (int j = 32; j < 48; j++) {
			System.out.print((char)BytetoInt8(rawdata, 68+j));
		}
		System.out.println("");
		System.out.print("TCP:  ");
		for (int j = 48; j < 64; j+=2) {
			System.out.print(data[j]+" ");
		}
		System.out.print("           ");
		for (int j = 48; j < 64; j++) {
			System.out.print((char)BytetoInt8(rawdata, 84+j));
		}			
	}
}
