import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;
 

public class KMeans {
    public static void main(String [] args){
	if (args.length < 3){
	    System.out.println("Usage: Kmeans <input-image> <k> <output-image>");
	    return;
	}
	try{
	    BufferedImage originalImage = ImageIO.read(new File(args[0]));
	    int k=Integer.parseInt(args[1]);
	    BufferedImage kmeansJpg = kmeans_helper(originalImage,k);
	    ImageIO.write(kmeansJpg, "jpg", new File(args[2])); 
	    
	}catch(IOException e){
	    System.out.println(e.getMessage());
	}	
    }
    
    private static BufferedImage kmeans_helper(BufferedImage originalImage, int k){
	int w=originalImage.getWidth();
	int h=originalImage.getHeight();
	BufferedImage kmeansImage = new BufferedImage(w,h,originalImage.getType());
	Graphics2D g = kmeansImage.createGraphics();
	g.drawImage(originalImage, 0, 0, w,h , null);
	// Read rgb values from the image
	int[] rgb=new int[w*h];
	System.out.println("length of color vector: "+rgb.length);
	int count=0;
	for(int i=0;i<w;i++){
	    for(int j=0;j<h;j++){
		rgb[count++]=kmeansImage.getRGB(i,j);
	    }
	}
	// Call kmeans algorithm: update the rgb values
	kmeans(rgb,k);

	// Write the new rgb values to the image
	count=0;
	for(int i=0;i<w;i++){
	    for(int j=0;j<h;j++){
		kmeansImage.setRGB(i,j,rgb[count++]);
	    }
	}
	return kmeansImage;
    }

    // Your k-means code goes here
    // Update the array rgb by assigning each entry in the rgb array to its cluster center
    private static void kmeans(int[] rgb, int k){
    	//store the cluster center and assignments
    	int[] mu_k = new int[k];
    	int[] r_nk = new int[rgb.length];
    	//initial index
    	int inicnt = 0;
    	ArrayList<Integer> ini_ind = new ArrayList<Integer>();
    	Random rdm = new Random();
    	while (inicnt<k) {
    		int ini_num1 = rdm.nextInt(rgb.length);
    		if (!ini_ind.contains(ini_num1)) {
    			ini_ind.add(ini_num1);
    			inicnt += 1;
    		}
    	}
    	for (int ii=0; ii<k; ii++) {
    		mu_k[ii] = rgb[ini_ind.get(ii)];
    	}
    	//initial assignments
    	for (int n=0; n<rgb.length; n++) {
    		int[] nk_dis = new int[k];
    		int min_dis_ind = 0;
    		for (int ik=0; ik<k; ik++) {
    			int abm = Math.abs(rgb[n] - mu_k[ik]);
    			nk_dis[ik] = abm;
    			if (nk_dis[min_dis_ind] > nk_dis[ik]) {
    				min_dis_ind = ik;
    			}
    		}
    		r_nk[n] = min_dis_ind;
    	}
    	//initial J
    	double Jnow = 0;
    	for (int n=0; n<rgb.length; n++) {
    		double en = Math.pow((rgb[n]-mu_k[r_nk[n]]), 2);
    		Jnow += en;
    	}
    	Jnow = Math.log(Jnow);
    	//EM steps
    	int iter_num_cnt = 0;//store the iteration count
    	while (true) {
    		//update mu_k, E
    		int[] former_mu_k = mu_k.clone();
    		int[] kcluster_cnt = new int[k];
    		long[] kcluster_sum = new long[k];
    		for (int n=0; n<rgb.length; n++) {
    			kcluster_cnt[r_nk[n]] += 1;
    			kcluster_sum[r_nk[n]] += rgb[n];
    		}
    		for (int ik=0; ik<k; ik++) {
    			if (kcluster_cnt[ik]==0) {
    				kcluster_cnt[ik] = 1;
    			}
    			mu_k[ik] = (int) (kcluster_sum[ik] / kcluster_cnt[ik]);
    		}
    		//store the former assignment
    		int[] former_r_nk = r_nk.clone();
    		//update r_nk, M
    		for (int n=0; n<rgb.length; n++) {
        		int[] nk_dis = new int[k];
        		int min_dis_ind = 0;
        		for (int ik=0; ik<k; ik++) {
        			int abm = Math.abs(rgb[n] - mu_k[ik]);
        			nk_dis[ik] = abm;
        			if (nk_dis[min_dis_ind] > nk_dis[ik]) {
        				min_dis_ind = ik;
        			}
        		}
        		r_nk[n] = min_dis_ind;
        	}
    		//update Error, J
    		for (int n=0; n<rgb.length; n++) {
        		double en = Math.pow((rgb[n]-mu_k[r_nk[n]]), 2);
        		Jnow += en;
        	}
        	Jnow = Math.log(Jnow);
    		iter_num_cnt += 1;
    		//if assignment dose not change, break out
    		if (Arrays.equals(former_r_nk, r_nk) && Arrays.equals(former_mu_k, mu_k)) {
    			break;
    		}
    	}
    	//mu_k, store the centroid
    	//r_nk, store the assignments;
    	for (int ii=0; ii<rgb.length; ii++) {
    		rgb[ii] = mu_k[r_nk[ii]];
    	}
    }

}
