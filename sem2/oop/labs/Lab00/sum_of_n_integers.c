/**
 * Scrieti un program care citeste n numere de la tastatura si tipareste suma lor.
 */

#include <stdio.h>

int main()
{
    int n, s=0, nr;
    printf("n = ");
    scanf_s("%i", &n);
    for(int i=1; i<=n; i++)
    {
        scanf_s("%i", &nr);
        s += nr;
    }
    printf("sum = %i", s);
    return 0;
}