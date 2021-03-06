/* CSCI- 651 Computing Network
 * 
 * Project 1
 * 
 * Author: Zizhun Guo
 * 
 *  Part 4/5 (TCP Header)
 * */
class TCPHeader {
	byte[] rawdata = null;
	String[] hexdata = null;
	int sp;								// Source Port
	int dp;								// Destination Port
	int sn;								// Sequence Number
	int an;								// Acknowledgement Number
	int dataOffset;						// Data Offset
	String Flags;						// Flag for 6 digits
	boolean urg = false;				// Urgent Pointer
	boolean ack = false;				// Acknoledgement
	boolean psh = false;				// Push
	boolean rst = false;				// Push
	boolean syn = false;				// Syn
	boolean fin = false;				// Fin
	int window;							// Window
	String checksum;					// Chescksum
	String[] data;
	int up;								// Urgent Pointer
	
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
	/*
	   * This method inputs an array of bytes, and the starting offset index.
	   * @returnthe 8-bit integer transfered from the inputs bytes
	   	*/
	public int BytetoInt8(byte[] bt, int offset) {
		return bt[offset] & 0xff;
	}
	
	/*
	   * This method inputs an array of bytes, and the starting offset index.
	   * @returnthe 16-bit integer transfered from the inputs bytes
	   	*/
	public int BytestoInt16(byte[] bt, int offset) {
		return (int)(((bt[offset] & 0xFF) << 8) | (bt[offset+1] & 0xFF));
	}
	
	/*
	   * This method inputs an array of bytes, and the starting offset index.
	   * @returnthe 32-bit integer transfered from the inputs bytes
	   	*/
	public int BytestoInt32(byte[] bt, int offset) {
		return (int)(((bt[offset] & 0xff) << 24) | ((bt[offset+1] & 0xff) << 16) |((bt[offset+2] & 0xff) << 8) |(bt[offset+3] & 0xff)) ;
	}
	
	/*
	   * This method is for printing out the info
	   * 
	   	*/
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
