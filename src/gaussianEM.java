
import java.util.ArrayList;
import java.lang.Math;

public class gaussianEM {
	/**
	 * implementation of EM algorithm
	 */
	public double[] mu_k = new double[3];
	public double[] cov_k = new double[3];
	public double[] pi_k = new double[3];
	
	public boolean isCov_1;
	public int MaxIter;
	public ArrayList<Double> emdata = new ArrayList<Double>();
	
	public double[][] gama_nk;
	public int[] Assignments;
	public double loglike;
	
	public gaussianEM(String dataPath, int iniIter, boolean isCov1, int maxIter) {
		this.isCov_1 = isCov1;
		this.MaxIter = maxIter;
		kmemdata KMini = new kmemdata(dataPath, iniIter);
		this.emdata = (ArrayList<Double>) KMini.emdt.clone();
		this.gama_nk = new double[KMini.emdt.size()][3];
		this.Assignments = new int[KMini.emdt.size()];
		this.mu_k = KMini.d_center.clone();
		this.pi_k[0] = (double)KMini.c_size[0] / KMini.emdt.size();
		this.pi_k[1] = (double)KMini.c_size[1] / KMini.emdt.size();
		this.pi_k[2] = (double)KMini.c_size[2] / KMini.emdt.size();
		if (this.isCov_1) {
			this.cov_k[0] = 1;
			this.cov_k[1] = 1;
			this.cov_k[2] = 1;
		} else {
			this.cov_k = KMini.d_variance.clone();
		}
		this.runEM1();
	}
	
	public void runEM1 () {
		/**
		 * 6000 data points
		 * mean, variance information
		 */
		int itercnt = 0;
		while (itercnt < this.MaxIter) {
			//E step
			this.getGAMA();
			//M step
			double[] NK = this.getNk();
			for (int k=0; k<NK.length; k++) {
				double mu_k_new = 0;
				for (int n=0; n<this.gama_nk.length; n++) {
					mu_k_new += this.gama_nk[n][k] * this.emdata.get(n);
				}
				mu_k_new = mu_k_new / NK[k];
				this.mu_k[k] = mu_k_new;
				double cov_k_new = 0;
				for (int n=0; n<this.gama_nk.length; n++) {
					cov_k_new += this.gama_nk[n][k] * Math.pow( (this.emdata.get(n) - mu_k_new) , 2);
				}
				cov_k_new = cov_k_new / NK[k];
				if (!this.isCov_1) {
					this.cov_k[k] = cov_k_new;
				}
				this.pi_k[k] = NK[k] / this.gama_nk.length;
			}
			itercnt += 1;
		}
		this.updataINFO();
	}
	
	public void getGAMA () {
		double[] sum_gama_n = new double[this.gama_nk.length];
		for (int n=0; n<this.gama_nk.length; n++) {
			double xn = this.emdata.get(n);
			for (int k=0; k<this.gama_nk[n].length; k++) {
				double d1 = 1 / Math.sqrt(2 * Math.PI * this.cov_k[k]);
				double d2 = Math.pow((xn - this.mu_k[k]), 2) / (2 * this.cov_k[k]);
				double d3 = this.pi_k[k] * d1 * Math.exp(-d2);
				this.gama_nk[n][k] = d3;
				sum_gama_n[n] += d3;
			}
		}
		//normalize
		for (int n=0; n<this.gama_nk.length; n++) {
			for (int k=0; k<this.gama_nk[n].length; k++) {
				this.gama_nk[n][k] = this.gama_nk[n][k] / sum_gama_n[n];
			}
		}
	}
	
	public double[] getNk () {
		double[] NK = new double[3];
		for (int k=0; k<3; k++) {
			for (int n=0; n<this.gama_nk.length; n++) {
				NK[k] += this.gama_nk[n][k];
			}
		}
		return NK;
	}
	
	public void updataINFO() {
		double lglk = 0;
		for (int n=0; n<this.gama_nk.length; n++) {
			double lglk_n = 0;
			int nmaxind = 0;
			for (int k=0; k<this.gama_nk[n].length; k++) {
				if (this.gama_nk[n][nmaxind] < this.gama_nk[n][k]) {
					nmaxind = k;
				}
				double d1 = 1 / Math.sqrt(2 * Math.PI * this.cov_k[k]);
				double d2 = Math.pow((this.emdata.get(n) - this.mu_k[k]), 2) / (2 * this.cov_k[k]);
				double d3 = this.pi_k[k] * d1 * Math.exp(-d2);
				//double d4 = Math.log(d3);
				lglk_n += d3;
			}
			lglk += Math.log(lglk_n);
			this.Assignments[n] = nmaxind;
		}
		this.loglike = lglk;
	}

}
