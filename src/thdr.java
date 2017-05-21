import java.util.Arrays;

public class thdr {
	public static void main (String args[]) {
		String fp = "/home/mdy/workspace/embkm/dataset/processed.hungarian.data";
		HD_Reader hdr = new HD_Reader(fp);
		System.out.println(hdr.DataSize);
		System.out.println(Arrays.toString(hdr.noneCnt));
		System.out.println(hdr.delInd);
		System.out.println(Arrays.toString(hdr.noneCnt1));
		System.out.println(Arrays.toString(hdr.classcnt));
		System.out.println(Arrays.toString(hdr.data1[109]));
		System.out.println(Arrays.toString(hdr.data2[109]));
	}

}
