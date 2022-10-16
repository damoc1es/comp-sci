#!/bin/bash

# Folosind comanda sed, stergeti toate literele dintr-un cuvant care sunt urmate de un grup de 2 cifre.
# Ex: abcd12 abcde1234 => 12 abcde1234

sed -E 's/\b[a-zA-Z]+([0-9]{2})\b/\1/g' file.txt
