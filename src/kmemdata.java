
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import weka.core.*;
import weka.core.converters.ArffSaver;
import weka.clusterers.SimpleKMeans;

public class kmemdata {
	/**
	 * use k-means for initialization for EM algorithm
	 */
	public ArrayList<Double> emdt = new ArrayList<Double>();
	public int num_iter = 0;
	public double[] d_center = new double[3];
	public double[] d_variance = new double[3];
	public int[] c_size = new int[3];
	//records the results
	public ArrayList<Integer> c0_index = new ArrayList<Integer>();
	public ArrayList<Integer> c1_index = new ArrayList<Integer>();
	public ArrayList<Integer> c2_index = new ArrayList<Integer>();
	public int[] assigns;
	
	public kmemdata(String dtpath, int setIternum) {
		try {
			emdatard emd = new emdatard(dtpath);
			this.emdt = (ArrayList<Double>) emd.emdata.clone();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.num_iter = setIternum;
		this.InstancesAndClustering();
	}
	
	public void InstancesAndClustering() {
		//attributes
		Attribute a0 = new Attribute("em_data");
		FastVector fv = new FastVector(1);
		fv.addElement(a0);
		//empty set
		Instances emdis = new Instances("EM_Data_set", fv, this.emdt.size());
		
		//populate
		for (int i=0; i<this.emdt.size(); i++) {
			Instance datapoint = new Instance(1);
			datapoint.setValue((Attribute)fv.elementAt(0), this.emdt.get(i));
			emdis.add(datapoint);
		}
		// ## .arff file writer gose here ##
		/*
		ArffSaver afs = new ArffSaver();
		afs.setInstances(emdis);
		try {
			afs.setFile(new File("/home/mdy/workspace/embkm/dataset/em_data.arff"));
			afs.writeBatch();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("save arff failed!");
			e1.printStackTrace();
		}
		*/
		SimpleKMeans mykmeans = new SimpleKMeans();
		mykmeans.setSeed(30);
		mykmeans.setPreserveInstancesOrder(true);
		try {
			mykmeans.setNumClusters(3);
			mykmeans.setMaxIterations(this.num_iter);
			mykmeans.buildClusterer(emdis);
			//get statistics
			this.d_center[0] = mykmeans.getClusterCentroids().instance(0).value(0);
			this.d_center[1] = mykmeans.getClusterCentroids().instance(1).value(0);
			this.d_center[2] = mykmeans.getClusterCentroids().instance(2).value(0);
			int[] assigns = mykmeans.getAssignments();
			this.assigns = assigns.clone();
			double varisum0 = 0;
			double varisum1 = 0;
			double varisum2 = 0;
			for (int i=0; i<assigns.length; i++) {
				if (assigns[i]==0) {
					this.c0_index.add(i);
					varisum0 += (this.emdt.get(i) - this.d_center[0]) * (this.emdt.get(i) - this.d_center[0]);
				}
				if (assigns[i]==1) {
					this.c1_index.add(i);
					varisum1 += (this.emdt.get(i) - this.d_center[1]) * (this.emdt.get(i) - this.d_center[1]);
				}
				if (assigns[i]==2) {
					this.c2_index.add(i);
					varisum2 += (this.emdt.get(i) - this.d_center[2]) * (this.emdt.get(i) - this.d_center[2]);
				}
			}
			int[] csize = mykmeans.getClusterSizes();
			this.c_size = csize.clone();
			this.d_variance[0] = varisum0 / csize[0];
			this.d_variance[1] = varisum1 / csize[1];
			this.d_variance[2] = varisum2 / csize[2];
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("k-means cluster building failed");
			e.printStackTrace();
		}
	}

}
