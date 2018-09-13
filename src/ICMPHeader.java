import java.awt.RadialGradientPaint;

public class ICMPHeader {
	byte[] rawdata = null;
	String[] hexdata = null;
	int typeIndex;
	String typeText;
	int code;
	String checksum;
	
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
	public void PrintResult() {
		System.out.println("ICMP: ----- ICMP Header ----- ");
		System.out.println("ICMP:                          ");
		System.out.println("ICMP:  Type = " + typeIndex + " (" + typeText + ")");
		System.out.println("ICMP:  Code = " + code);
		System.out.println("ICMP:  Checksum =  0x" + checksum);
		System.out.println("ICMP:");
	}
}
