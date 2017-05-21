#!/bin/sh

#specify weka.jar path bellow:
export CLASSPATH=$CLASSPATH:/usr/share/java/weka.jar

#specify dataset path, initialization max iteration (using k-means for initialization), variance=1 or not, maximum iteration number bellow:
s0=/home/mdy/workspace/embkm/dataset/em_data.txt
s1=0
s2=true
s3=20

cdir=$(pwd)
cd $cdir
javac tem.java
java tem $s0 $s1 $s2 $s3
rm *.class