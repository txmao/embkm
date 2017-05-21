#!/bin/sh
cdir=$(pwd)
cd $cdir
#specify source image path, cluster number, output image path
s0=/home/mdy/workspace/embkm/dataset/Koala.jpg
s1=20
s2=/home/mdy/Desktop/img.jpg
javac KMeans.java
java KMeans $s0 $s1 $s2
rm *.class