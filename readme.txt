$ EM algorithm, Bagging and Boosting, K-means $

# file description #
 All source code are in embkm/src directory
 -gaussianEM.java, core class for EM algorithm
 -obbcv.java, core class for Vanilla, Bagging, and Boosting
 -KMeans.java, core class for K-means image compression
 -emdatard.java, class for reading the em_data.txt
 -HD_Reader.java and HD_Arff.java, read processed.hungarian.data file and write to hungarian.arff
 -tem.java, thda.java, thdr.java, used for test EM algorithm, test HD_Arff.java and HD_Reader.java, generate .arff file
 $ bash script for running:
 -testem.sh, used for running EM algorithm
 -testvbb.sh, used for running vanilla, bagging, boosting test
 -testkm.sh, used for running K-means image compression
 
# how to compile & run #
 1. cd to embkm/src
 2. modify your arguments in testem.sh, testvbb.sh, or testkm.sh
 3. type sh [which_one].sh

# references #
 https://weka.wikispaces.com/Programmatic+Use
 https://weka.wikispaces.com/Generating+classifier+evaluation+output+manually
 http://stackoverflow.com/questions/20019765/setting-up-the-number-of-cross-validation-in-j48-tree-weka
 https://archive.ics.uci.edu/ml/datasets/Pima+Indians+Diabetes
 https://archive.ics.uci.edu/ml/datasets/Heart+Disease
 https://archive.ics.uci.edu/ml/datasets/Statlog+(Heart)
 https://archive.ics.uci.edu/ml/datasets/Ionosphere