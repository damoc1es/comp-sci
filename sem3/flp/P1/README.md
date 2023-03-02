# P1. Liste în Prolog (1)

### a) Să se scrie un predicat care substituie într-o listă un element printr-o altă listă.

$$
\text{substitute\_elem}(l_1 \, l_2 \dots l_n, K, r_1 \, r_2 \dots r_m) =
\begin{cases}
    \varnothing, & n = 0 \\
    \text{my\_append}(r_1 \, r_2 \dots r_m, \text{substitute\_elem}(l_2 \dots l_n, K, r_1 \, r_2 \dots r_m)) , & l_1 = K \\
    l_1 \oplus \text{substitute\_elem}(l_2 \dots l_n, K, r_1 \, r_2 \dots r_m), & \text{altfel}
\end{cases}
$$

$$
\text{my\_append}(l_1 \, l_2 \dots l_n, r_1 \, r_2 \dots r_m) =
\begin{cases}
    r_1 \, r_2 \dots r_m, & n = 0 \\
    l_1 \oplus \text{my\_append}(l_2 \dots l_n, r_1 \, r_2 \dots r_m), & \text{altfel}
\end{cases}
$$


### b) Să se elimine elementul de pe poziția a n-a a unei liste liniare.

$$
\text{remove\_nth}(l_1 \, l_2 \dots l_n, N) =
\begin{cases}
    \varnothing, & n = 0 \\
    l_2 \dots l_n, & N = 1 \\
    l_1 \oplus \text{remove\_nth}(l_2 \dots l_n, N-1), & \text{altfel}
\end{cases}
$$
