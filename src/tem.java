
public class tem {
	public static void main(String args[]) {
		/*
		String dataPath = "/home/mdy/workspace/embkm/dataset/em_data.txt";
		int iniIter = 1;
		boolean isCov1 = false;
		int maxIter = 20;
		*/
		
		String dataPath = args[0];
		int iniIter = Integer.parseInt(args[1]);
		boolean isCov1 = Boolean.parseBoolean(args[2]);
		int maxIter = Integer.parseInt(args[3]);
		
		gaussianEM gem = new gaussianEM(dataPath, iniIter, isCov1, maxIter);
		System.out.println("log likelihood/10000:");
		System.out.println(gem.loglike/10000);
		System.out.println("centers:");
		System.out.println(gem.mu_k[0]);
		System.out.println(gem.mu_k[1]);
		System.out.println(gem.mu_k[2]);
		System.out.println("variance:");
		System.out.println(gem.cov_k[0]);
		System.out.println(gem.cov_k[1]);
		System.out.println(gem.cov_k[2]);
		System.out.println("assiginment ration of each clusters:");
		System.out.println(gem.pi_k[0]);
		System.out.println(gem.pi_k[1]);
		System.out.println(gem.pi_k[2]);
	}

}
