# L3. Programare în Lisp folosind funcții MAP

#### Definiți o funcție care substituie un element prin altul la toate nivelurile unei liste date.

$$
\text{substitutex}(X, e, c) =
\begin{cases}
    c, & X = e \\
    X, & X \neq e \land X - \text{atom} \\
    \bigcup^{n}_{i=1}\text{substitutex}(x_i) , & X \neq e \land X - \text{list, } X = x_1 x_2 \dots x_n
\end{cases}
$$
