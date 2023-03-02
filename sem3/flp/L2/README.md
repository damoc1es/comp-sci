# L2. Programare recursivă în Lisp (2)

#### Se dă un arbore de tipul (1). Să se precizeze numărul de niveluri din arbore.

(nod nr-subarbori lista-subarbore-1 lista-subarbore-2 ...)
(A 2 B 0 C 2 D 0 E 0)

$$
\text{visit}(l_1 \, l_2 \dots l_n , nv, nm, type, col) =
\begin{cases}
    col, & n = 0 \\
    col, & nv = 1 + nm \land tip = 0 \\
    arb, & nv = 1 + nm \land tip = 1 \\
    \text{visit}(l_3 \dots l_n, nv+1, nm+l_2, type, col \oplus l_1 \oplus l_2), & \text{altfel}
\end{cases}
$$

$$
\text{left}(l_1 \, l_2 \dots l_n) = \text{visit}(l_3 \dots l_n, 0, 0, 0, \varnothing)
$$

$$
\text{right}(l_1 \, l_2 \dots l_n) = \text{visit}(l_3 \dots l_n, 0, 0, 1, \varnothing)
$$

$$
\text{height}(L) =
\begin{cases}
    0, & L = \varnothing \\
    1 + \max(\text{height}(\text{left}(L)), \text{height}(\text{right}(L))), & \text{altfel}
\end{cases}
$$
