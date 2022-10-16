/*
Sa se scrie un program care creeaza un numar N de threaduri dat ca
argument in linia de comanda. Fiecare thread primeste ca argument
indexul I la care a fost creat (de la 0 la N-1). Fiecare thread
actioneaza pe un array global cu dimensiunea N*2 care poate contine
doar numere intregi si efectueaza urmatoarele operatii in ordine,
dupa care isi incheie executia:

1. adauga numere intregi random pe pozitia I si N+I (I - indexul threadului)
2. calculeaza S = suma tuturor numerelor din array din acel moment.
3. afiseaza aceasta suma S impreuna cu array-ul din acel moment.

Initial, arrayul cu dimensiune N*2 are toate valorile 0.
Limitati numarul de thread-uri care opereaza simultan asupra array-ului
la maximum 3. Folositi mecanismele de sincronizare necesare.
*/

// gcc -Wall -g -o eth eth.c -pthread

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <time.h>

int N;
int* arr;
sem_t sem;
pthread_mutex_t m;
pthread_rwlock_t rwl;

void *f(void* a) {
    int I = *(int*)a;
    sem_wait(&sem);

    arr[I] = rand()%100;
    arr[N+I] = rand()%100;

    pthread_mutex_lock(&m);
    int S=0;
    for(int i=0; i<2*N; i++) {
        S += arr[i];
    }
    
    printf("S=%d:", S);
    for(int i=0; i<2*N; i++) {
        printf(" %d", arr[i]);
    }
    printf("\n");
    pthread_mutex_unlock(&m);

    sem_post(&sem);
    return NULL;
}

int main(int argc, char* argv[]) {
    if(argc == 1) {
        printf("Thread number not given.\n");
        return 1;
    }
    N = atoi(argv[1]);
    if(N == 0) {
        printf("Invalid number.\n");
        return 1;
    }

    printf("%d threads:\n", N);

    arr = (int*)malloc(2*N * sizeof(int));
    for(int i=0; i<2*N; i++) {
        arr[i] = 0;
    }

    pthread_t* threads = (pthread_t*)malloc(N * sizeof(pthread_t));
    int* nr = (int*)malloc(N * sizeof(int));
    
    pthread_mutex_init(&m, NULL);
    sem_init(&sem, 0, 3);

    srand(time(0));
    
    for(int i=0; i<N; i++) {
        nr[i] = i;
        pthread_create(&threads[i], NULL, f, &nr[i]);
    }

    for(int i=0; i<N; i++) {
        pthread_join(threads[i], NULL);
    }

    pthread_mutex_destroy(&m);
    sem_destroy(&sem);
    free(threads);
    free(arr); free(nr);
    return 0;
}