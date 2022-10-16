#!/bin/bash

# 1. Media marimilor/dim tuturor fisierelor text
# 2. Nr total de comentarii din fisierele sursa C .c .C
# se vor scrie in rezultate.txt

if [ ! -d "$1" ] ; then
    echo "Directory not valid"
    exit 1
fi

# 1
Alltxt=`find -type f -iname '*.txt'`
sizesum=0
cnt=0
for F in $Alltxt ; do
    size=`du -k --apparent-size $F | awk '{print $1}'`
    # echo $size $F
    sizesum=`expr $sizesum + $size`
    cnt=`expr $cnt + 1`
done

echo Average size of all text files: `expr $sizesum / $cnt` > results.txt

# 2
Allc=`find -type f -iname '*.c'`
com=0
for F in $Allc ; do
    comF=`grep -c '//' $F`
    com=`expr $com + $comF`
done

echo Total number of C line comments: $com >> results.txt