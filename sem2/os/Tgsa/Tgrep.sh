#!/bin/bash

# Folosid comanda grep, afisati toate liniile din /etc/passwd care contin un nume de utilizator (este primul de pe linie)
# (a nu se confunda cu numele complet al studentului) al carei lu ngime este mai mare de 8 caractere.

grep -E "^[^:]{9,}:" /etc/passwd
