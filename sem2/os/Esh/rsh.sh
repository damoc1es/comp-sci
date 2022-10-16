#!/bin/bash

# EN:  Implement a UNIX Shell script that calculates the total number of
# EN:  words in all the files given as command line arguments. All the
# EN:  command line arguments that are not files will be ignored.
# RO:  Scrieti un script Shell UNIX care calculeaza suma numerelor de cuvinte a
# RO:  argumentelor din linia de comanda care sunt fisiere. Toate celelalte
# RO:  argumente (ne-fisiere) vor fi ignorate.

if [ $# -eq 0 ] ; then
    echo No argument given
    exit 1
fi

sum=0
for F in $@ ; do
    if [ -f "$F" ] ; then
        cnt=`wc -w $F | awk '{print $1}'`
        sum=`expr $sum + $cnt`
    fi
done