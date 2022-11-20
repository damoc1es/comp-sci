/*
candidat(N) = | N
              | candidat(N-1) , N > 1

apare(E, l1 l2 .. ln) = | False              , n = 0
                        | True               , l1 = E
                        | apare(E, l2 .. ln) , altfel

permutari_W(N, M) = permutari(N, M, 1, [candidat(N)])

permutari(N, M, LG, c1 c2 .. ck) = | c1 c2 .. ck
                                   | permutari(N, M, LG+1, I ⊕ c1 c2 .. ck) , abs(c1-I) >= M /\ !apare(I, c1 .. ck)
                                    (unde I = candidat(N))

DI: permutari_W(3, 1, E)
DE: [1, 2, 3] [2, 1, 3] [1, 3, 2] [3, 1, 2] [2, 3, 1] [3, 2, 1] (permutarile lui [1, 2, 3])

DI: permutari_W(3, 2, E)
DE: -

DI: permutari_W(4, 2, E)
DE: [2, 4, 1, 3] [3, 1, 4, 2]

DI: permutari_W(4, 3, E)
DE: -
*/

% candidat(N: integer, I: integer)
% model de flux: (i, o) - nedeterminist
% N - numarul maxim candidat
% I - candidat generat intre 1 si N
candidat(N, N).

candidat(N, I) :-
    N > 1,
    N1 is N-1,
    candidat(N1, I).


% permutari_W(N: integer, M: integer, Rez: list)
% model de flux: (i, i, o) - nedeterminist
% N - numarul maxim din lista [1, 2 .. N]
% M - minimul diferentei in modul a elementelor consecutive ale rezolvarii 
% Rez - permutare solutie, lista cu numerele de la 1 la N a.î. intre orice 2 numere pe pozitii consecutive diferenta in modul sa fie >=M
permutari_W(N, M, Rez):-
    candidat(N, I),
    permutari(N, M, Rez, 1, [I]).


% apare(E: integer, L: list) (verifica daca apare numarul in lista)
% model de flux: (i, i) - determinist
% E - numarul cautat in lista
% L - lista in care se cauta numarul
apare(H, [H|_]) :- !.

apare(E, [_|T]) :-
    apare(E, T).


% permutari(N: integer, M: integer, L: list, LG: integer, Col: list)
% model de flux: (i, i, o, i, i) - nedeterminist
% N - numarul maxim din lista [1, 2 .. N]
% M - minimul diferentei in modul a elementelor consecutive ale rezolvarii 
% L - lista rezultatului
% LG - lungimea listei colectoare
% Col - lista colectoare (de lungime LG)
permutari(N, _, Col, N, Col) :- !.

permutari(N, M, L, LG, [H|T]):-
    candidat(N, I),
    abs(H-I) >= M,
    not(apare(I, [H|T])),
    LG1 is LG+1,
    permutari(N, M, L, LG1, [I | [H|T]]).