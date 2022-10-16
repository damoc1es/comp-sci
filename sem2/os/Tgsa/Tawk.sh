#!/bin/bash

# Folosind comanda awk, numarati cate linii dintr-un fisier incep cu o vocala si se termina cu o consoana
# si cate linii incep cu o consoana si se termina cu o vocala.

awk 'BEGIN{cnt=0; cnt2=0} {if($0 ~ /^[aeiouAEIOU].*[^aeiouAEIOU]$/) cnt++; if($0 ~ /^[^aeiouAEIOU].*[aeiouAEIOU]$/) cnt2++} END{print "Start with a vowel, end with a consonant: " cnt; print "Start with a consonant, end with a vowel: " cnt2}' file.txt
