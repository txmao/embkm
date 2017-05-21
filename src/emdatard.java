
import java.util.ArrayList;
import java.io.*;

public class emdatard {
	/**
	 * read the em_data.txt to a ArrayList<Double>
	 */
	public ArrayList<Double> emdata = new ArrayList<Double>();
	
	public emdatard(String dtpath) throws IOException {
		//read data from path
		try {
			BufferedReader br = new BufferedReader(new FileReader(dtpath));
			String ln;
			while ( (ln = br.readLine()) != null ) {
				this.emdata.add(Double.parseDouble(ln));
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("read em_data.txt error!");
			e.printStackTrace();
		}
	}

}
