# L1. Programare recursivă în Lisp (1)

#### a) Să se intercaleze un element pe poziția a n-a a unei liste liniare.

$$
\text{intercal}(l_1 \, l_2 \dots l_n, elem, N) =
\begin{cases}
    \varnothing, & N\neq1 \wedge n = 0 \\
    elem \oplus l_1 \, l_2 \dots l_n , & N=1 \\
    l_1 \oplus \text{intercal}(l_2 \dots l_n, elem, N-1), & N > 1 \\
    l_1 \, l_2 \dots l_n, & \text{altfel}
\end{cases}
$$


#### b) Să se construiască o funcție care întoarce suma atomilor numerici dintr-o listă, de la orice nivel.

$$
\text{sum⎽atoms}(l_1 \, l_2 \dots l_n) =
\begin{cases}
    0, & n = 0 \\
    \text{sum⎽atoms}(l_1) + \text{sum⎽atoms}(l_2 \dots l_n) , & l_1\text{ - list} \\
    l_1 + \text{sum⎽atoms}(l_2 \dots l_n), & l_1 \text{ - number} \\
    \text{sum⎽atoms}(l_2 \dots l_n), & \text{altfel}
\end{cases}
$$


#### c) Să se scrie o funcție care întoarce mulțimea tuturor sublistelor unei liste date. `ex: ((1 2 3) ((4 5) 6)) => ((1 2 3) (4 5) ((4 5) 6))`

$$
\text{sublists}(l_1 \, l_2 \dots l_n) =
\begin{cases}
    \varnothing, & n = 0 \\
    l_1 \oplus \text{sublists}(l_1) \oplus \text{sublists}(l_2 \dots l_n) , & \text{$l_1$ - list} \\
    \text{sublists}(l_2 \dots l_n), & \text{altfel}
\end{cases}
$$


#### d) Să se scrie o funcție care testează egalitatea a două mulțimi, fără să se facă apel la diferența a două mulțimi.

$$ \text{equal⎽sets⎽W}(L, R) = \text{equal⎽sets}(L, R, L) $$

$$
\text{equal⎽sets}(l_1 \, l_2 \dots l_n, r_1 \, r_2 \dots r_m, C) =
\begin{cases}
    True, & n = 0 \wedge m = 0\\
    \text{equal⎽sets}(\varnothing, r_2 \dots r_m, C) , & n = 0 \wedge \text{appears}(C, r_1) \\
    \text{equal⎽sets}(l_2 \dots l_n, r_1 \, r_2 \dots r_m, C), & \text{appears}(r_1 \, r_2 \dots r_m, l_1) \\
    False, & \text{altfel}
\end{cases}
$$

$$
\text{appears}(l_1 \, l_2 \dots l_n, elem) =
\begin{cases}
    False, & n = 0 \\
    True, & l_1 = elem \\
    \text{appears}(l_2 \dots l_n, elem), & \text{altfel}
\end{cases}
$$
