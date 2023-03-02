# P3. Utilizarea facilității de backtracking în Prolog

### Se dau două numere naturale n și m. Se cere să se afișeze în toate modurile posibile toate numerele de la 1 la n, astfel încât între orice două numere afișate pe poziții consecutive, diferența în modul să fie ≥ m.

$$\text{candidate}(N) =
\begin{cases}
    1. \,\, N \\
    2. \,\, candidate(N-1), & N > 1
\end{cases}
$$

$$
\text{appears}(E, l_1 \, l_2 \dots l_n) =
\begin{cases}
    False, & n = 0 \\
    True, & l_1 = E \\
    \text{appears}(E, l_2 \dots l_n), & \text{altfel}
\end{cases}
$$

$$
\text{permutations}(N, M, LG, c_1 \, c_2 \dots c_k) =
\begin{cases}
    1. \,\, c_1 \, c_2 \dots c_k \\
    2. \,\, \text{permutations}(N, M, LG+1, I \oplus c_1 \, c_2 \dots c_k), & k > 0 \wedge \text{abs}(c_1-I) \ge M \wedge \lnot \, \text{appears}(I, l_1 \, l_2 \dots l_n) \\
    \text{unde I = candidate}(N)
\end{cases}
$$

$$
\text{permutations⎽W}(N, M) = \text{permutations}(N, M, 1, [\text{candidate}(N)])
$$
