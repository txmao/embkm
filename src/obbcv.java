import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.Bagging;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.core.Instances;

/**
 * original classifier, bagging version, boosting version, and cross validation
 */
public class obbcv {
	public static void main (String[] args) {
		/*
		String arrp1 = "/home/mdy/workspace/embkm/dataset/hungarian.arff";
		String arrp2 = "/home/mdy/workspace/embkm/dataset/diabetes.arff";
		String arrp3 = "/home/mdy/workspace/embkm/dataset/ionosphere.arff";
		//100 & 150 goes latter
		int numiterations = 30;
		int maxiterations = 30;
		int weightthreshold = 100000;
		*/
		
		String arrp1 = args[0];
		int numiterations = Integer.parseInt(args[1]);
		int maxiterations = Integer.parseInt(args[2]);
		int weightthreshold = Integer.parseInt(args[3]);
		
		try {
			BufferedReader rdr = new BufferedReader(
					new FileReader(arrp1));
			
			Instances myins = new Instances(rdr);
			myins.setClassIndex(myins.numAttributes()-1);
			rdr.close();
			/*
			 * evaluation goes here
			 */
			Evaluation myeval = new Evaluation(myins);
			
			
			//j48
			J48 my48 = new J48();
			my48.setUnpruned(false);//use pruned
			myeval.crossValidateModel(my48, myins, 10, new Random(1));
			System.out.println("J48 Error Rates: "+Double.toString(myeval.pctIncorrect()));
			//Logistic Regression
			Logistic mylr = new Logistic();
			myeval.crossValidateModel(mylr, myins, 10, new Random(1));
			System.out.println("Logisitic Regression Error Rates: "+Double.toString(myeval.pctIncorrect()));
			//Decision Stumps
			DecisionStump myds = new DecisionStump();
			myeval.crossValidateModel(myds, myins, 10, new Random(1));
			System.out.println("Decision Stump Error Rates: "+Double.toString(myeval.pctIncorrect()));
			
			
			//bagging j48
			Bagging mybg1 = new Bagging();
			mybg1.setClassifier(my48);
			mybg1.setNumIterations(numiterations);
			myeval.crossValidateModel(mybg1, myins, 10, new Random(1));
			System.out.println("Bagging J48 Error Rates: "+Double.toString(myeval.pctIncorrect()));
			//bagging Logistic Regression
			Bagging mybg2 = new Bagging();
			mybg2.setClassifier(mylr);
			mybg2.setNumIterations(numiterations);
			myeval.crossValidateModel(mybg2, myins, 10, new Random(1));
			System.out.println("Bagging Logisitic Regression Error Rates: "+Double.toString(myeval.pctIncorrect()));
			//bagging Decision Stump
			Bagging mybg3 = new Bagging();
			mybg3.setClassifier(myds);
			mybg3.setNumIterations(numiterations);
			myeval.crossValidateModel(mybg3, myins, 10, new Random(1));
			System.out.println("Bagging Decision Stump Error Rates: "+Double.toString(myeval.pctIncorrect()));
			
			
			//boosting j48
			AdaBoostM1 bs1 = new AdaBoostM1();
			bs1.setClassifier(my48);
			bs1.setNumIterations(maxiterations);
			bs1.setWeightThreshold(weightthreshold);
			myeval.crossValidateModel(bs1, myins, 10, new Random(1));
			System.out.println("Boosting J48 Error Rates: "+Double.toString(myeval.pctIncorrect()));
			//boosting logistic regression
			AdaBoostM1 bs2 = new AdaBoostM1();
			bs2.setClassifier(mylr);
			bs2.setNumIterations(maxiterations);
			bs2.setWeightThreshold(weightthreshold);
			myeval.crossValidateModel(bs2, myins, 10, new Random(1));
			System.out.println("Boosting Logisitic Regression Error Rates: "+Double.toString(myeval.pctIncorrect()));
			//boosting decision stump
			AdaBoostM1 bs3 = new AdaBoostM1();
			bs3.setClassifier(myds);
			bs3.setNumIterations(maxiterations);
			bs3.setWeightThreshold(weightthreshold);
			myeval.crossValidateModel(bs3, myins, 10, new Random(1));
			System.out.println("Boosting Decision Stump Error Rates: "+Double.toString(myeval.pctIncorrect()));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
