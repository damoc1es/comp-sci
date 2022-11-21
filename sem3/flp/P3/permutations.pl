/*
candidate(N) = | N
               | candidate(N-1) , N > 1

appears(E, l1 l2 .. ln) = | False              , n = 0
                          | True               , l1 = E
                          | appears(E, l2 .. ln) , altfel

permutations_W(N, M) = permutations(N, M, 1, [candidate(N)])

permutations(N, M, LG, c1 c2 .. ck) = | c1 c2 .. ck
                                      | permutations(N, M, LG+1, I ⊕ c1 c2 .. ck) , k > 0 ∧ abs(c1-I) >= M ∧ !appears(I, c1 .. ck)
                                        (unde I = candidate(N))

IN: permutations_W(3, 1, E)
OUT: [1, 2, 3] [2, 1, 3] [1, 3, 2] [3, 1, 2] [2, 3, 1] [3, 2, 1] (permutarile lui [1, 2, 3])

IN: permutations_W(3, 2, E)
OUT: -

IN: permutations_W(4, 2, E)
OUT: [2, 4, 1, 3] [3, 1, 4, 2]

IN: permutations_W(4, 3, E)
OUT: -
*/

% candidate(N: integer, I: integer)
% model de flux: (i, o) - nedeterminist
% N - numarul maxim candidat
% I - candidat generat intre 1 si N
candidate(N, N).

candidate(N, I) :-
    N > 1,
    N1 is N-1,
    candidate(N1, I).


% permutations_W(N: integer, M: integer, Rez: list)
% model de flux: (i, i, o) - nedeterminist
% N - numarul maxim din lista [1, 2 .. N]
% M - minimul diferentei in modul a elementelor consecutive ale rezolvarii 
% Rez - permutare solutie, lista cu numerele de la 1 la N a.î. intre orice 2 numere pe pozitii consecutive diferenta in modul sa fie >=M
permutations_W(N, M, Rez):-
    candidate(N, I),
    permutations(N, M, Rez, 1, [I]).


% appears(E: integer, L: list) (verifica daca apare numarul in lista)
% model de flux: (i, i) - determinist
% E - numarul cautat in lista
% L - lista in care se cauta numarul
appears(H, [H|_]) :- !.

appears(E, [_|T]) :-
    appears(E, T).


% permutations(N: integer, M: integer, L: list, LG: integer, Col: list)
% model de flux: (i, i, o, i, i) - nedeterminist
% N - numarul maxim din lista [1, 2 .. N]
% M - minimul diferentei in modul a elementelor consecutive ale rezolvarii 
% L - lista rezultatului
% LG - lungimea listei colectoare
% Col - lista colectoare (de lungime LG)
permutations(N, _, Col, N, Col) :- !.

permutations(N, M, L, LG, [H|T]):-
    candidate(N, I),
    abs(H-I) >= M,
    not(appears(I, [H|T])),
    LG1 is LG+1,
    permutations(N, M, L, LG1, [I | [H|T]]).