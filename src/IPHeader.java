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
