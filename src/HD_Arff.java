import java.io.File;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVSaver;

/**
 * create instances for Weka
 * & write to .arff format
 */
public class HD_Arff {
	//weka instances, can be used in implementation
	public Instances hdinstances;
	
	public HD_Arff (String rdpath, String wtpath) {
		//read from HD_Reader and write to .arff
		HD_Reader hdr = new HD_Reader(rdpath);
		double[][] rd_data = hdr.data2.clone();
		/*
		 * 11 attributes used, including 1 classAttribute
		 */
		Attribute age = new Attribute("age");
		Attribute sex = new Attribute("sex");
		Attribute cp = new Attribute("cp");
		Attribute thresbps = new Attribute("thresbps");
		Attribute chol = new Attribute("chol");
		Attribute fbs = new Attribute("fbs");
		Attribute restecg = new Attribute("restecg");
		Attribute thalach = new Attribute("thalach");
		Attribute exang = new Attribute("exang");
		Attribute oldpead = new Attribute("oldpeak");
		//class
		FastVector cfv = new FastVector(2);
		cfv.addElement("0");
		cfv.addElement("1");
		Attribute num = new Attribute("num", cfv);
		//total
		FastVector attrList = new FastVector(11);
		attrList.addElement(age);
		attrList.addElement(sex);
		attrList.addElement(cp);
		attrList.addElement(thresbps);
		attrList.addElement(chol);
		attrList.addElement(fbs);
		attrList.addElement(restecg);
		attrList.addElement(thalach);
		attrList.addElement(exang);
		attrList.addElement(oldpead);
		attrList.addElement(num);
		//
		Instances myIns = new Instances("hungarian-heart-disease", attrList, rd_data.length);
		myIns.setClassIndex(10);
		//populate
		for (int i=0; i<rd_data.length; i++) {
			Instance InsAtI = new Instance(11);
			for (int j=0; j<rd_data[i].length; j++) {
				if (j!=rd_data[i].length-1) {
					InsAtI.setValue((Attribute)attrList.elementAt(j), rd_data[i][j]);
				} else {
					if (rd_data[i][j]==0) {
						InsAtI.setValue((Attribute)attrList.elementAt(j), rd_data[i][j]);
					} else {
						InsAtI.setValue((Attribute)attrList.elementAt(j), rd_data[i][j]);
					}
				}
				//InsAtI.setValue((Attribute)attrList.elementAt(j), rd_data[i][j]);
			}
			myIns.add(InsAtI);
		}
		/*
		 * write to .arff
		 */
		
		ArffSaver afs = new ArffSaver();
		afs.setInstances(myIns);
		try {
			afs.setFile(new File(wtpath));
			afs.writeBatch();
		} catch (Exception e) {
			System.out.println("conert heart disease data to .arff file error");
			e.printStackTrace();
		}
		
		this.hdinstances = myIns;
		/*
		 * write to csv
		 */
		CSVSaver css = new CSVSaver();
		css.setInstances(myIns);
		try {
			String csvp = wtpath.substring(0, wtpath.length()-4)+"csv";
			css.setFile(new File(csvp));
			css.writeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		 * second version
		 */
		Attribute a1 = new Attribute("age");
		
		FastVector fv2 = new FastVector(2);
		fv2.addElement("1");
		fv2.addElement("0");
		Attribute a2 = new Attribute("sex", fv2);
		
		FastVector fv3 = new FastVector(5);
		fv3.addElement("4");
		fv3.addElement("3");
		fv3.addElement("2");
		fv3.addElement("1");
		fv3.addElement("0");// wcnmb, 0 stands for no pain
		Attribute a3 = new Attribute("cp", fv3);
		
		Attribute a4 = new Attribute("threstbps");
		
		Attribute a5 = new Attribute("chol");
		
		FastVector fv6 = new FastVector(2);
		fv6.addElement("1");
		fv6.addElement("0");
		Attribute a6 = new Attribute("fbs", fv6);
		
		FastVector fv7 = new FastVector(3);
		fv7.addElement("2");
		fv7.addElement("1");
		fv7.addElement("0");
		Attribute a7 = new Attribute("restecg", fv7);
		
		Attribute a8 = new Attribute("thalach");
		
		FastVector fv9 = new FastVector(2);
		fv9.addElement("1");
		fv9.addElement("0");
		Attribute a9 = new Attribute("exang", fv9);
		
		Attribute a10 = new Attribute("oldpeak");
		
		FastVector fv14 = new FastVector(2);
		fv14.addElement("1");
		fv14.addElement("0");
		Attribute a14 = new Attribute("num", fv14);
		
		FastVector tt2 = new FastVector(11);
		tt2.addElement(a1);
		tt2.addElement(a2);
		tt2.addElement(a3);
		tt2.addElement(a4);
		tt2.addElement(a5);
		tt2.addElement(a6);
		tt2.addElement(a7);
		tt2.addElement(a8);
		tt2.addElement(a9);
		tt2.addElement(a10);
		tt2.addElement(a14);
		
		Instances inss2 = new Instances("hungarian-heart-disease-2", tt2, rd_data.length);
		for (int i=0; i<rd_data.length; i++) {
			Instance insi = new Instance(11);
			for (int j=0; j<rd_data[i].length; j++) {
				insi.setValue((Attribute)tt2.elementAt(j), rd_data[i][j]);
			}
			inss2.add(insi);
		}
		
		ArffSaver sv2 = new ArffSaver();
		sv2.setInstances(inss2);
		try {
			sv2.setFile(new File(wtpath));
			sv2.writeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
