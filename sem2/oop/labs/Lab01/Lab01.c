/**
 * 2. Genereaza primele n (n natural dat) numere prime.
 * 3. Determina toate reprezentarile posibile a unui numar natural ca suma de numere naturale consecutive.
 */

#include <stdio.h>

/**
 * @brief Verifica daca numarul dat este prim.
 * @param nr reprezinta numarul ce este verificat
 * @pre-conditii nr = numar natural
 * @post-conditii returneaza 1 daca este prim, respectiv 0 in caz contrar
 */
int isPrime(unsigned int nr) {
    if(nr <= 1)
        return 0;
    for(int i=2; i*i <= nr; i++) {
        if(nr%i == 0)
            return 0;
    }
    return 1;
}

/**
 * @brief Tipareste primele n numere prime.
 * @param n cate nr. prime sa se tipareasca
 * @pre-conditii n = numar natural
 * @post-conditii se tiparesc pe ecran primele n numere prime
 */
void print_n_primes(unsigned int n) {
    unsigned int k=0;
    for(unsigned int i=2; k != n; i++) {
        if(isPrime(i)) {
            printf("%i ", i);    
            k++;
        }
    }
}

/**
 * @brief Gaseste si tipareste numerele naturale consecutive care au ca suma un numar dat n
 * @param n suma finala
 * @pre-conditii n = numar natural
 * @post-conditii returneaza cate sume s-au gasit si le tipareste pe ecran 
 */
int consecutive_sum(unsigned int n) {
    int k=0;
    for(int i=1; i<=n/2; i++) {
        unsigned int sum = i;
        for(int j=i+1; j<=n; j++) {
            sum += j;
            if(sum > n)
                break;
            if(sum == n) {
                unsigned int sum2 = 0;
                while(sum2 != n) {
                    sum2 += i;
                    printf("%u ", i);
                    i++;
                }
                printf("\n");
                k++;
            }
        }
    }
    return k;
}

/**
 * @brief Tipareste meniul.
 * 
 */
void print_menu() {
    printf("0 - Exit\n");
    printf("1 - Genereaza primele n numere prime\n");
    printf("2 - Determina toate reprezentarile posibile a unui nr. natural ca suma de numere naturale consecutive\n");
}
/**
 * @brief Executa o comanda existenta dintr-o lista definita in print_menu
 * 
 * @param c comanda
 * @post-conditii returneaza 0 daca c este 0, 1 in caz contrar 
 */
int read_cmd(int c) {
    unsigned int n;
    switch (c) {
        case 0:
            return 0;
            break;
        case 1:
            printf("Numarul de numere prime generate, n=");
            scanf_s("%i", &n);
            printf("\n");
            print_n_primes(n);
            printf("\n");
            break;
        case 2:
            printf("Numarul a carui sume sa se gaseasca, n=");
            scanf_s("%i", &n);
            printf("\n");
            if(consecutive_sum(n) == 0)
                printf("Nu exista nicio suma.\n");
            break;
        default:
            printf("Nu exista aceasta comanda\n");
            break;
    }
    return 1;
}

/**
 * @brief main starting function
 * @return 0
 */
int main() {
    unsigned int K, ok=1;
    while(ok) {
        print_menu();
        printf("Introdu comanda: ");
        scanf_s("%i", &K);
        ok = read_cmd(K);
        printf("\n");
    }
    return 0;
}