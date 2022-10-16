#include <stdio.h>

// Programare multi-modul (asm+C) / pb. 18: Se citeste de la tastatura un sir de numere in baza 10 fara semn. Sa se determine valoarea minima din sir si sa se afiseze in fisierul min.txt (fisierul va fi creat) valoarea minima, in baza 16

/*
    cd /d D:\ubb\sem1\asc\lab\Lab12
    nasm min_of_string_module_for_c.asm -fwin32 -o min_of_string_module_for_c.obj
    cl lab12_p18.c /link min_of_string_module_for_c.obj
*/

int min_of_string(int sir[], int n);

int main()
{
    unsigned int n, v[10000], minim;
    printf("Lungimea sirului de numere unsigned (in baza 10): ");
    scanf("%u", &n);
    
    for(int i=0; i<n; i++)
        scanf("%u", &v[i]);
    // n = 5000;
    // for(int i=0; i<n; i++)
    //     v[i] = n-i;
    
    FILE *desc = fopen("min.txt", "w");
    if (desc == NULL)
    {
        printf("EROARE");
        return 0;
    }
    
    minim = min_of_string(v, n);
    fprintf(desc, "%x" , minim);
    
    fclose(desc);
    printf("%u (%x in baza 16)", minim, minim); // temporar, pentru a se verifica rapid rezultatul
    return 0;
}