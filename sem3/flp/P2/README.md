# P2. Liste în Prolog (2)

### a) Să se adauge după fiecare element dintr-o listă divizorii elementului.

$$
\text{element\_divisors}(N, K) =
\begin{cases}
    \varnothing, & K \ge N \\
    K \oplus \text{element\_divisors}(N, K+1) , & N \bmod K = 0 \\
    \text{element\_divisors}(N, K+1), & \text{altfel}
\end{cases}
$$

$$
\text{element\_divisors\_W}(N) =
\begin{cases}
    \text{element\_divisors}(-N, 2), & N < 0 \\
    \text{element\_divisors}(N, 2), & \text{altfel}
\end{cases}
$$

$$
\text{list\_divisors}(l_1 \, l_2 \dots l_n) =
\begin{cases}
    \varnothing, & n = 0 \\
    l_1 \oplus \text{my\_append}(\text{element\_divisors\_W}(l_1), \text{list\_divisors}(l_2 \dots l_n)), & \text{altfel}
\end{cases}
$$


### b) Se dă o listă eterogenă, formată din numere întregi și liste de numere întregi. Se cere ca în fiecare sublistă să se adauge după fiecare element divizorii elementului.
ex.: \
`[1, [2, 5, 7], 4, 5, [1, 4], 3, 2, [6, 2, 1], 4, [7, 2, 8, 1], 2]` => \
`[1, [2, 5, 7], 4, 5, [1, 4, 2], 3, 2, [6, 2, 3, 2, 1], 4, [7, 2, 8, 2, 4, 1], 2]`.

$$
\text{sublist\_divisors}(l_1 \, l_2 \dots l_n) =
\begin{cases}
    \varnothing, & n = 0 \\
    \text{list\_divisors}(l_1) \oplus \text{sublist\_divisors}(l_2 \dots l_n), & l_1 - \text{list} \\
    l_1 \oplus \text{sublist\_divisors}(l_2 \dots l_n), & \text{altfel}
\end{cases}
$$
