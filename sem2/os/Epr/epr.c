/*
Scrieti un program C care creeaza un proces fiu. Procesul parinte
genereaza aleator un numar intreg X intre 1000 si 10000, iar fiul
trebuie sa ghiceasca acest numar. Dupa ce parintele primeste un
numar de la fiu, trebuie sa ii raspunda acestuia daca numarul primit
este mai mare (folosind semnul ">") sau mai mic (folosind semnul "<")
decat X. Jocul se termina atunci cand procesul fiu ghiceste numarul X
adica primeste semnul "=" de la parinte dupa ce a trimis un numar.
Comunicarea intre procese trebuie realizata prin PIPE.

Exemplu:
X = 2000;
Fiul trimite parintelui 5000, iar parintele trimite inapoi fiului semnul "<"
Fiul trimite parintelui 1000, iar parintele trimite inapoi fiului semnul ">"
Fiul trimite parintelui 2500, iar parintele trimite inapoi fiului semnul "<"
Fiul trimite parintelui 1700, iar parintele trimite inapoi fiului semnul ">"
...........
Fiul trimite parintelui 2000, iar parintele trimite inapoi fiului semnul "<".
Fiul isi termina executia.
Parintele isi termina executia.

Time de lucru: 40 minute
*/

#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <time.h>

int main(int argc, char** argv) {
    int p2c[2], c2p[2], left=1000, right=10000;

    pipe(p2c); pipe(c2p);

    if(fork() == 0) { // child
        int n=5500;
        char sign;

        close(p2c[1]); close(c2p[0]);
        while(1) {
            n = (left+right)/2;
            printf("Child: Maybe %d?\n", n);
            write(c2p[1], &n, sizeof(int));

            if(read(p2c[0], &sign, sizeof(char)) <= 0) {
                break;
            }
            if(sign == '=')
                break;
            if(sign == '>') {
                left = n;
            }
            if(sign == '<') {
                right = n;
            }
        }
        close(p2c[0]); close(c2p[1]);
        exit(0);
    }
    
    // parent
    srand(time(0));
    int n = 0, real = rand()%(right-left) + left;
    char sign;

    close(p2c[0]); close(c2p[1]);
    while(1) { 
        if(read(c2p[0], &n, sizeof(int)) <= 0) {
            break;
        }
        if(n == real) {
            printf("Parent: Child guessed correctly. %d was the right number.\n", n);
            break;
        }
        if(n < real) {
            printf("Parent: More! (%d) >\n", n);
            sign = '>';
        }
        if(n > real) {
            printf("Parent: Less! (%d) <\n", n);
            sign = '<';
        }
        write(p2c[1], &sign, sizeof(char));
    }
    
    close(p2c[1]); close(c2p[0]);
    wait(0);
    return 0;
}