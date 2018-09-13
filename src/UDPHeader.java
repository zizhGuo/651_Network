import javax.naming.spi.DirStateFactory.Result;

class UDPHeader {
	byte[] rawdata = null;
	String[] hexdata = null;
	int sp;
	int dp;
	int length;
	String checksum;
	String[] data;
	
	public UDPHeader(byte[] rawdataHeader, String[] HexdataHeader) {
		this.rawdata = rawdataHeader;
		this.hexdata = HexdataHeader;
		this.sp = BytestoInt16(rawdataHeader, 34);
		this.dp = BytestoInt16(rawdataHeader, 36);
		this.length = BytestoInt16(rawdataHeader, 38);
		this.checksum = HexdataHeader[40] + HexdataHeader[41];
		this.data = new String[64];
		for (int i = 0; i < 64; i+=2) {
			this.data[i] = HexdataHeader[34 + i] + HexdataHeader[i+1+34];
		}
		
	}
	int BytestoInt16(byte[] bt, int offset) {
		return (int)(((bt[offset] & 0xFF) << 8) | (bt[offset+1] & 0xFF));
	}
	int BytetoInt8(byte[] bt, int offset) {
		return bt[offset] & 0xff;
	}
	public void PrintResult(){
		System.out.println("UDP: ----- UDP Header ----- ");
		System.out.println("UDP:                          ");
		System.out.println("UDP:  Source port = " + sp );
		System.out.println("UDP:  Destination port = " + dp );
		System.out.println("UDP:  Length = " + length);
		System.out.println("UDP:  Checksum = 0x" + checksum);
		System.out.println("UDP:  Data: (first 64 bytes)");
		System.out.print("UDP:  ");
		for (int j = 0; j < 16; j+=2) {
			System.out.print(data[j]+" ");
		}
		System.out.print("           ");
		for (int j = 0; j < 16; j++) {
			System.out.print((char)BytetoInt8(rawdata, 36+j));
		}
		System.out.println("");
		System.out.print("UDP:  ");
		for (int j = 16; j < 32; j+=2) {
			System.out.print(data[j]+" ");
		}
		System.out.print("           ");
		for (int j = 16; j < 32; j++) {
			System.out.print((char)BytetoInt8(rawdata, 52+j));
		}
		System.out.println("");
		System.out.print("UDP:  ");
		for (int j = 32; j < 48; j+=2) {
			System.out.print(data[j]+" ");
		}
		System.out.print("           ");
		for (int j = 32; j < 48; j++) {
			System.out.print((char)BytetoInt8(rawdata, 68+j));
		}
		System.out.println("");
		System.out.print("UDP:  ");
		for (int j = 48; j < 64; j+=2) {
			System.out.print(data[j]+" ");
		}
		System.out.print("           ");
		for (int j = 48; j < 64; j++) {
			System.out.print((char)BytetoInt8(rawdata, 84+j));
		}			
	}
}
