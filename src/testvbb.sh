#!/bin/sh

#specify weka.jar path bellow:
export CLASSPATH=$CLASSPATH:/usr/share/java/weka.jar

#specify .arff path, numiterations, maxiterations, weightthreshold bellow:
sa=/home/mdy/workspace/embkm/dataset/hungarian.arff
sb=/home/mdy/workspace/embkm/dataset/diabetes.arff
sc=/home/mdy/workspace/embkm/dataset/ionosphere.arff
s1=150
s2=150
s3=100000

cdir=$(pwd)
cd $cdir
javac obbcv.java
java obbcv $sc $s1 $s2 $s3
rm *.class